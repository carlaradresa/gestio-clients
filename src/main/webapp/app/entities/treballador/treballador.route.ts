import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITreballador, Treballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from './treballador.service';
import { TreballadorComponent } from './treballador.component';
import { TreballadorDetailComponent } from './treballador-detail.component';
import { TreballadorUpdateComponent } from './treballador-update.component';

@Injectable({ providedIn: 'root' })
export class TreballadorResolve implements Resolve<ITreballador> {
  constructor(private service: TreballadorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITreballador> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((treballador: HttpResponse<Treballador>) => {
          if (treballador.body) {
            return of(treballador.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Treballador());
  }
}

export const treballadorRoute: Routes = [
  {
    path: '',
    component: TreballadorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.treballador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TreballadorDetailComponent,
    resolve: {
      treballador: TreballadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.treballador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TreballadorUpdateComponent,
    resolve: {
      treballador: TreballadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.treballador.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TreballadorUpdateComponent,
    resolve: {
      treballador: TreballadorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.treballador.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
