import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaasSharedModule } from '../../shared';

import {
    SirqulAccountService,
    SirqulAccountPopupService,
    SirqulAccountComponent,
    SirqulAccountDetailComponent,
    SirqulAccountDialogComponent,
    SirqulAccountPopupComponent,
    SirqulAccountDeletePopupComponent,
    SirqulAccountDeleteDialogComponent,
    sirqulAccountRoute,
    sirqulAccountPopupRoute,
    SirqulAccountResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...sirqulAccountRoute,
    ...sirqulAccountPopupRoute,
];

@NgModule({
    imports: [
        SaasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SirqulAccountComponent,
        SirqulAccountDetailComponent,
        SirqulAccountDialogComponent,
        SirqulAccountDeleteDialogComponent,
        SirqulAccountPopupComponent,
        SirqulAccountDeletePopupComponent,
    ],
    entryComponents: [
        SirqulAccountComponent,
        SirqulAccountDialogComponent,
        SirqulAccountPopupComponent,
        SirqulAccountDeleteDialogComponent,
        SirqulAccountDeletePopupComponent,
    ],
    providers: [
        SirqulAccountService,
        SirqulAccountPopupService,
        SirqulAccountResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaasSirqulAccountModule {}
