import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Marcatge } from 'app/shared/model/marcatge.model';
import { MarcatgeService } from './marcatge.service';
import { MarcatgeComponent } from './marcatge.component';
import { MarcatgeDetailComponent } from './marcatge-detail.component';
import { MarcatgeUpdateComponent } from './marcatge-update.component';
import { IMarcatge } from 'app/shared/model/marcatge.model';

@Injectable({ providedIn: 'root' })
export class MarcatgeResolve implements Resolve<IMarcatge> {
  constructor(private service: MarcatgeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMarcatge> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((marcatge: HttpResponse<Marcatge>) => marcatge.body));
    }
    return of(new Marcatge());
  }
}

export const marcatgeRoute: Routes = [
  {
    path: '',
    component: MarcatgeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.marcatge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MarcatgeDetailComponent,
    resolve: {
      marcatge: MarcatgeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.marcatge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MarcatgeUpdateComponent,
    resolve: {
      marcatge: MarcatgeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.marcatge.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MarcatgeUpdateComponent,
    resolve: {
      marcatge: MarcatgeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.marcatge.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
