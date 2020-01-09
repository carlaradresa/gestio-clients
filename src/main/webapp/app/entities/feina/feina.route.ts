import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFeina, Feina } from 'app/shared/model/feina.model';
import { FeinaService } from './feina.service';
import { FeinaComponent } from './feina.component';
import { FeinaDetailComponent } from './feina-detail.component';
import { FeinaUpdateComponent } from './feina-update.component';

@Injectable({ providedIn: 'root' })
export class FeinaResolve implements Resolve<IFeina> {
  constructor(private service: FeinaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeina> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((feina: HttpResponse<Feina>) => {
          if (feina.body) {
            return of(feina.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Feina());
  }
}

export const feinaRoute: Routes = [
  {
    path: '',
    component: FeinaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.feina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FeinaDetailComponent,
    resolve: {
      feina: FeinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.feina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FeinaUpdateComponent,
    resolve: {
      feina: FeinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.feina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FeinaUpdateComponent,
    resolve: {
      feina: FeinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.feina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
