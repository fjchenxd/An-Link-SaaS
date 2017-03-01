import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SirqulAccount } from './sirqul-account.model';
import { SirqulAccountService } from './sirqul-account.service';
@Injectable()
export class SirqulAccountPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private sirqulAccountService: SirqulAccountService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sirqulAccountService.find(id).subscribe(sirqulAccount => {
                this.sirqulAccountModalRef(component, sirqulAccount);
            });
        } else {
            return this.sirqulAccountModalRef(component, new SirqulAccount());
        }
    }

    sirqulAccountModalRef(component: Component, sirqulAccount: SirqulAccount): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sirqulAccount = sirqulAccount;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
