import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { MarcatgeComponent } from './marcatge.component';
import { MarcatgeDetailComponent } from './marcatge-detail.component';
import { MarcatgeUpdateComponent } from './marcatge-update.component';
import { MarcatgeDeleteDialogComponent } from './marcatge-delete-dialog.component';
import { marcatgeRoute } from './marcatge.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(marcatgeRoute)],
  declarations: [MarcatgeComponent, MarcatgeDetailComponent, MarcatgeUpdateComponent, MarcatgeDeleteDialogComponent],
  entryComponents: [MarcatgeDeleteDialogComponent]
})
export class GestioClientsMarcatgeModule {}
