import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { UbicacioComponent } from './ubicacio.component';
import { UbicacioDetailComponent } from './ubicacio-detail.component';
import { UbicacioUpdateComponent } from './ubicacio-update.component';
import { UbicacioDeleteDialogComponent } from './ubicacio-delete-dialog.component';
import { ubicacioRoute } from './ubicacio.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(ubicacioRoute)],
  declarations: [UbicacioComponent, UbicacioDetailComponent, UbicacioUpdateComponent, UbicacioDeleteDialogComponent],
  entryComponents: [UbicacioDeleteDialogComponent]
})
export class GestioClientsUbicacioModule {}
