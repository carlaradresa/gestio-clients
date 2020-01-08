import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMarcatge, Marcatge } from 'app/shared/model/marcatge.model';
import { MarcatgeService } from './marcatge.service';
import { MarcatgeComponent } from './marcatge.component';
import { MarcatgeDetailComponent } from './marcatge-detail.component';
import { MarcatgeUpdateComponent } from './marcatge-update.component';

@Injectable({ providedIn: 'root' })
export class MarcatgeResolve implements Resolve<IMarcatge> {
  constructor(private service: MarcatgeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMarcatge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((marcatge: HttpResponse<Marcatge>) => {
          if (marcatge.body) {
            return of(marcatge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
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
