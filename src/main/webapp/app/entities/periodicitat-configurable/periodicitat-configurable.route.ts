import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPeriodicitatConfigurable, PeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';
import { PeriodicitatConfigurableService } from './periodicitat-configurable.service';
import { PeriodicitatConfigurableComponent } from './periodicitat-configurable.component';
import { PeriodicitatConfigurableDetailComponent } from './periodicitat-configurable-detail.component';
import { PeriodicitatConfigurableUpdateComponent } from './periodicitat-configurable-update.component';

@Injectable({ providedIn: 'root' })
export class PeriodicitatConfigurableResolve implements Resolve<IPeriodicitatConfigurable> {
  constructor(private service: PeriodicitatConfigurableService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriodicitatConfigurable> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((periodicitatConfigurable: HttpResponse<PeriodicitatConfigurable>) => {
          if (periodicitatConfigurable.body) {
            return of(periodicitatConfigurable.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PeriodicitatConfigurable());
  }
}

export const periodicitatConfigurableRoute: Routes = [
  {
    path: '',
    component: PeriodicitatConfigurableComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.periodicitatConfigurable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PeriodicitatConfigurableDetailComponent,
    resolve: {
      periodicitatConfigurable: PeriodicitatConfigurableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.periodicitatConfigurable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PeriodicitatConfigurableUpdateComponent,
    resolve: {
      periodicitatConfigurable: PeriodicitatConfigurableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.periodicitatConfigurable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PeriodicitatConfigurableUpdateComponent,
    resolve: {
      periodicitatConfigurable: PeriodicitatConfigurableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.periodicitatConfigurable.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
