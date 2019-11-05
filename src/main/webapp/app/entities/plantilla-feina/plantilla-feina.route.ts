import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from './plantilla-feina.service';
import { PlantillaFeinaComponent } from './plantilla-feina.component';
import { PlantillaFeinaDetailComponent } from './plantilla-feina-detail.component';
import { PlantillaFeinaUpdateComponent } from './plantilla-feina-update.component';
import { PlantillaFeinaDeletePopupComponent } from './plantilla-feina-delete-dialog.component';
import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';

@Injectable({ providedIn: 'root' })
export class PlantillaFeinaResolve implements Resolve<IPlantillaFeina> {
  constructor(private service: PlantillaFeinaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPlantillaFeina> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PlantillaFeina>) => response.ok),
        map((plantillaFeina: HttpResponse<PlantillaFeina>) => plantillaFeina.body)
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

export const plantillaFeinaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PlantillaFeinaDeletePopupComponent,
    resolve: {
      plantillaFeina: PlantillaFeinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gestioClientsApp.plantillaFeina.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];