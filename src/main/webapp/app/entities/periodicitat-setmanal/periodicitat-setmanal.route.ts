import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPeriodicitatSetmanal, PeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';
import { PeriodicitatSetmanalService } from './periodicitat-setmanal.service';
import { PeriodicitatSetmanalComponent } from './periodicitat-setmanal.component';
import { PeriodicitatSetmanalDetailComponent } from './periodicitat-setmanal-detail.component';
import { PeriodicitatSetmanalUpdateComponent } from './periodicitat-setmanal-update.component';

@Injectable({ providedIn: 'root' })
export class PeriodicitatSetmanalResolve implements Resolve<IPeriodicitatSetmanal> {
  constructor(private service: PeriodicitatSetmanalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriodicitatSetmanal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((periodicitatSetmanal: HttpResponse<PeriodicitatSetmanal>) => {
          if (periodicitatSetmanal.body) {
            return of(periodicitatSetmanal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PeriodicitatSetmanal());
  }
}

export const periodicitatSetmanalRoute: Routes = [
  {
    path: '',
    component: PeriodicitatSetmanalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestioClientsApp.periodicitatSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PeriodicitatSetmanalDetailComponent,
    resolve: {
      periodicitatSetmanal: PeriodicitatSetmanalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestioClientsApp.periodicitatSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PeriodicitatSetmanalUpdateComponent,
    resolve: {
      periodicitatSetmanal: PeriodicitatSetmanalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestioClientsApp.periodicitatSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PeriodicitatSetmanalUpdateComponent,
    resolve: {
      periodicitatSetmanal: PeriodicitatSetmanalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gestioClientsApp.periodicitatSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
