import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SirqulAccountComponent } from './sirqul-account.component';
import { SirqulAccountDetailComponent } from './sirqul-account-detail.component';
import { SirqulAccountPopupComponent } from './sirqul-account-dialog.component';
import { SirqulAccountDeletePopupComponent } from './sirqul-account-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SirqulAccountResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const sirqulAccountRoute: Routes = [
  {
    path: 'sirqul-account',
    component: SirqulAccountComponent,
    resolve: {
      'pagingParams': SirqulAccountResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'saasApp.sirqulAccount.home.title'
    }
  }, {
    path: 'sirqul-account/:id',
    component: SirqulAccountDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'saasApp.sirqulAccount.home.title'
    }
  }
];

export const sirqulAccountPopupRoute: Routes = [
  {
    path: 'sirqul-account-new',
    component: SirqulAccountPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'saasApp.sirqulAccount.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sirqul-account/:id/edit',
    component: SirqulAccountPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'saasApp.sirqulAccount.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sirqul-account/:id/delete',
    component: SirqulAccountDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'saasApp.sirqulAccount.home.title'
    },
    outlet: 'popup'
  }
];
