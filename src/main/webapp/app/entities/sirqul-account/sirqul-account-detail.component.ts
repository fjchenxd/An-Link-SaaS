import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { SirqulAccount } from './sirqul-account.model';
import { SirqulAccountService } from './sirqul-account.service';

@Component({
    selector: 'jhi-sirqul-account-detail',
    templateUrl: './sirqul-account-detail.component.html'
})
export class SirqulAccountDetailComponent implements OnInit, OnDestroy {

    sirqulAccount: SirqulAccount;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sirqulAccountService: SirqulAccountService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['sirqulAccount']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.sirqulAccountService.find(id).subscribe(sirqulAccount => {
            this.sirqulAccount = sirqulAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
