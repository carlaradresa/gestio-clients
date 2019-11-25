import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { ControlComponent } from './control.component';
import { ControlDetailComponent } from './control-detail.component';
import { ControlUpdateComponent } from './control-update.component';
import { ControlDeleteDialogComponent } from './control-delete-dialog.component';
import { controlRoute } from './control.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(controlRoute)],
  declarations: [ControlComponent, ControlDetailComponent, ControlUpdateComponent, ControlDeleteDialogComponent],
  entryComponents: [ControlDeleteDialogComponent]
})
export class GestioClientsControlModule {}
