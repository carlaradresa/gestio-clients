import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVenedor, Venedor } from 'app/shared/model/venedor.model';
import { VenedorService } from './venedor.service';
import { VenedorComponent } from './venedor.component';
import { VenedorDetailComponent } from './venedor-detail.component';
import { VenedorUpdateComponent } from './venedor-update.component';

@Injectable({ providedIn: 'root' })
export class VenedorResolve implements Resolve<IVenedor> {
  constructor(private service: VenedorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVenedor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((venedor: HttpResponse<Venedor>) => {
          if (venedor.body) {
            return of(venedor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
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
