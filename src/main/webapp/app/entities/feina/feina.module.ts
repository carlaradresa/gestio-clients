import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { FeinaComponent } from './feina.component';
import { FeinaDetailComponent } from './feina-detail.component';
import { FeinaUpdateComponent } from './feina-update.component';
import { FeinaDeleteDialogComponent } from './feina-delete-dialog.component';
import { feinaRoute } from './feina.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(feinaRoute)],
  declarations: [FeinaComponent, FeinaDetailComponent, FeinaUpdateComponent, FeinaDeleteDialogComponent],
  entryComponents: [FeinaDeleteDialogComponent]
})
export class GestioClientsFeinaModule {}
