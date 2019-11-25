import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { RepeticioTascaSetmanalComponent } from './repeticio-tasca-setmanal.component';
import { RepeticioTascaSetmanalDetailComponent } from './repeticio-tasca-setmanal-detail.component';
import { RepeticioTascaSetmanalUpdateComponent } from './repeticio-tasca-setmanal-update.component';
import { RepeticioTascaSetmanalDeleteDialogComponent } from './repeticio-tasca-setmanal-delete-dialog.component';
import { repeticioTascaSetmanalRoute } from './repeticio-tasca-setmanal.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(repeticioTascaSetmanalRoute)],
  declarations: [
    RepeticioTascaSetmanalComponent,
    RepeticioTascaSetmanalDetailComponent,
    RepeticioTascaSetmanalUpdateComponent,
    RepeticioTascaSetmanalDeleteDialogComponent
  ],
  entryComponents: [RepeticioTascaSetmanalDeleteDialogComponent]
})
export class GestioClientsRepeticioTascaSetmanalModule {}
