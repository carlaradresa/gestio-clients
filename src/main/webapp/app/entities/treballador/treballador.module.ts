import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { TreballadorComponent } from './treballador.component';
import { TreballadorDetailComponent } from './treballador-detail.component';
import { TreballadorUpdateComponent } from './treballador-update.component';
import { TreballadorDeleteDialogComponent } from './treballador-delete-dialog.component';
import { treballadorRoute } from './treballador.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(treballadorRoute)],
  declarations: [TreballadorComponent, TreballadorDetailComponent, TreballadorUpdateComponent, TreballadorDeleteDialogComponent],
  entryComponents: [TreballadorDeleteDialogComponent]
})
export class GestioClientsTreballadorModule {}
