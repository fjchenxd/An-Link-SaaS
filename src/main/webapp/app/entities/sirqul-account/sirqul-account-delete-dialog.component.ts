import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { SirqulAccount } from './sirqul-account.model';
import { SirqulAccountPopupService } from './sirqul-account-popup.service';
import { SirqulAccountService } from './sirqul-account.service';

@Component({
    selector: 'jhi-sirqul-account-delete-dialog',
    templateUrl: './sirqul-account-delete-dialog.component.html'
})
export class SirqulAccountDeleteDialogComponent {

    sirqulAccount: SirqulAccount;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sirqulAccountService: SirqulAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['sirqulAccount']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.sirqulAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sirqulAccountListModification',
                content: 'Deleted an sirqulAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sirqul-account-delete-popup',
    template: ''
})
export class SirqulAccountDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sirqulAccountPopupService: SirqulAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.sirqulAccountPopupService
                .open(SirqulAccountDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
