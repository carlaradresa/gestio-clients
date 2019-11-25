import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Ubicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from './ubicacio.service';
import { UbicacioComponent } from './ubicacio.component';
import { UbicacioDetailComponent } from './ubicacio-detail.component';
import { UbicacioUpdateComponent } from './ubicacio-update.component';
import { IUbicacio } from 'app/shared/model/ubicacio.model';

@Injectable({ providedIn: 'root' })
export class UbicacioResolve implements Resolve<IUbicacio> {
  constructor(private service: UbicacioService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUbicacio> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((ubicacio: HttpResponse<Ubicacio>) => ubicacio.body));
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
