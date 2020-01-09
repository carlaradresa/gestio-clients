import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUbicacio, Ubicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from './ubicacio.service';
import { UbicacioComponent } from './ubicacio.component';
import { UbicacioDetailComponent } from './ubicacio-detail.component';
import { UbicacioUpdateComponent } from './ubicacio-update.component';

@Injectable({ providedIn: 'root' })
export class UbicacioResolve implements Resolve<IUbicacio> {
  constructor(private service: UbicacioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUbicacio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ubicacio: HttpResponse<Ubicacio>) => {
          if (ubicacio.body) {
            return of(ubicacio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ubicacio());
  }
}

export const ubicacioRoute: Routes = [
  {
    path: '',
    component: UbicacioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.ubicacio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UbicacioDetailComponent,
    resolve: {
      ubicacio: UbicacioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.ubicacio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UbicacioUpdateComponent,
    resolve: {
      ubicacio: UbicacioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.ubicacio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UbicacioUpdateComponent,
    resolve: {
      ubicacio: UbicacioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.ubicacio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
