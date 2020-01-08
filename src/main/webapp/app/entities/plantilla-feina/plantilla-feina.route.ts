import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlantillaFeina, PlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from './plantilla-feina.service';
import { PlantillaFeinaComponent } from './plantilla-feina.component';
import { PlantillaFeinaDetailComponent } from './plantilla-feina-detail.component';
import { PlantillaFeinaUpdateComponent } from './plantilla-feina-update.component';

@Injectable({ providedIn: 'root' })
export class PlantillaFeinaResolve implements Resolve<IPlantillaFeina> {
  constructor(private service: PlantillaFeinaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlantillaFeina> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plantillaFeina: HttpResponse<PlantillaFeina>) => {
          if (plantillaFeina.body) {
            return of(plantillaFeina.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlantillaFeina());
  }
}

export const plantillaFeinaRoute: Routes = [
  {
    path: '',
    component: PlantillaFeinaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.plantillaFeina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlantillaFeinaDetailComponent,
    resolve: {
      plantillaFeina: PlantillaFeinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.plantillaFeina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlantillaFeinaUpdateComponent,
    resolve: {
      plantillaFeina: PlantillaFeinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.plantillaFeina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlantillaFeinaUpdateComponent,
    resolve: {
      plantillaFeina: PlantillaFeinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.plantillaFeina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
