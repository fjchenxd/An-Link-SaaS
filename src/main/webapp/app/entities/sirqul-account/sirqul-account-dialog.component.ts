import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SirqulAccount } from './sirqul-account.model';
import { SirqulAccountPopupService } from './sirqul-account-popup.service';
import { SirqulAccountService } from './sirqul-account.service';
@Component({
    selector: 'jhi-sirqul-account-dialog',
    templateUrl: './sirqul-account-dialog.component.html'
})
export class SirqulAccountDialogComponent implements OnInit {

    sirqulAccount: SirqulAccount;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private sirqulAccountService: SirqulAccountService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['sirqulAccount']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.sirqulAccount.id !== undefined) {
            this.sirqulAccountService.update(this.sirqulAccount)
                .subscribe((res: SirqulAccount) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.sirqulAccountService.create(this.sirqulAccount)
                .subscribe((res: SirqulAccount) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: SirqulAccount) {
        this.eventManager.broadcast({ name: 'sirqulAccountListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-sirqul-account-popup',
    template: ''
})
export class SirqulAccountPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sirqulAccountPopupService: SirqulAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.sirqulAccountPopupService
                    .open(SirqulAccountDialogComponent, params['id']);
            } else {
                this.modalRef = this.sirqulAccountPopupService
                    .open(SirqulAccountDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
