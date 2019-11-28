import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Venedor } from 'app/shared/model/venedor.model';
import { VenedorService } from './venedor.service';
import { VenedorComponent } from './venedor.component';
import { VenedorDetailComponent } from './venedor-detail.component';
import { VenedorUpdateComponent } from './venedor-update.component';
import { IVenedor } from 'app/shared/model/venedor.model';

@Injectable({ providedIn: 'root' })
export class VenedorResolve implements Resolve<IVenedor> {
  constructor(private service: VenedorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVenedor> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((venedor: HttpResponse<Venedor>) => venedor.body));
    }
    return of(new Venedor());
  }
}

export const venedorRoute: Routes = [
  {
    path: '',
    component: VenedorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.venedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VenedorDetailComponent,
    resolve: {
      venedor: VenedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.venedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VenedorUpdateComponent,
    resolve: {
      venedor: VenedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.venedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VenedorUpdateComponent,
    resolve: {
      venedor: VenedorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.venedor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
