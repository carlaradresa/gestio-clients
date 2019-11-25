import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { VenedorComponent } from './venedor.component';
import { VenedorDetailComponent } from './venedor-detail.component';
import { VenedorUpdateComponent } from './venedor-update.component';
import { VenedorDeleteDialogComponent } from './venedor-delete-dialog.component';
import { venedorRoute } from './venedor.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(venedorRoute)],
  declarations: [VenedorComponent, VenedorDetailComponent, VenedorUpdateComponent, VenedorDeleteDialogComponent],
  entryComponents: [VenedorDeleteDialogComponent]
})
export class GestioClientsVenedorModule {}
