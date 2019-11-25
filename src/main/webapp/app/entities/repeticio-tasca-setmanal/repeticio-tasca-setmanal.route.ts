import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RepeticioTascaSetmanal } from 'app/shared/model/repeticio-tasca-setmanal.model';
import { RepeticioTascaSetmanalService } from './repeticio-tasca-setmanal.service';
import { RepeticioTascaSetmanalComponent } from './repeticio-tasca-setmanal.component';
import { RepeticioTascaSetmanalDetailComponent } from './repeticio-tasca-setmanal-detail.component';
import { RepeticioTascaSetmanalUpdateComponent } from './repeticio-tasca-setmanal-update.component';
import { IRepeticioTascaSetmanal } from 'app/shared/model/repeticio-tasca-setmanal.model';

@Injectable({ providedIn: 'root' })
export class RepeticioTascaSetmanalResolve implements Resolve<IRepeticioTascaSetmanal> {
  constructor(private service: RepeticioTascaSetmanalService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRepeticioTascaSetmanal> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((repeticioTascaSetmanal: HttpResponse<RepeticioTascaSetmanal>) => repeticioTascaSetmanal.body));
    }
    return of(new RepeticioTascaSetmanal());
  }
}

export const repeticioTascaSetmanalRoute: Routes = [
  {
    path: '',
    component: RepeticioTascaSetmanalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.repeticioTascaSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RepeticioTascaSetmanalDetailComponent,
    resolve: {
      repeticioTascaSetmanal: RepeticioTascaSetmanalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.repeticioTascaSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RepeticioTascaSetmanalUpdateComponent,
    resolve: {
      repeticioTascaSetmanal: RepeticioTascaSetmanalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.repeticioTascaSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RepeticioTascaSetmanalUpdateComponent,
    resolve: {
      repeticioTascaSetmanal: RepeticioTascaSetmanalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.repeticioTascaSetmanal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
