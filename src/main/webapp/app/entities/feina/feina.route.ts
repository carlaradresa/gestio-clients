import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Feina } from 'app/shared/model/feina.model';
import { FeinaService } from './feina.service';
import { FeinaComponent } from './feina.component';
import { FeinaDetailComponent } from './feina-detail.component';
import { FeinaUpdateComponent } from './feina-update.component';
import { IFeina } from 'app/shared/model/feina.model';

@Injectable({ providedIn: 'root' })
export class FeinaResolve implements Resolve<IFeina> {
  constructor(private service: FeinaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeina> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((feina: HttpResponse<Feina>) => feina.body));
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
