import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GestioClientsSharedModule } from 'app/shared/shared.module';
import { PlantillaFeinaComponent } from './plantilla-feina.component';
import { PlantillaFeinaDetailComponent } from './plantilla-feina-detail.component';
import { PlantillaFeinaUpdateComponent } from './plantilla-feina-update.component';
import { PlantillaFeinaDeleteDialogComponent } from './plantilla-feina-delete-dialog.component';
import { plantillaFeinaRoute } from './plantilla-feina.route';

@NgModule({
  imports: [GestioClientsSharedModule, RouterModule.forChild(plantillaFeinaRoute)],
  declarations: [
    PlantillaFeinaComponent,
    PlantillaFeinaDetailComponent,
    PlantillaFeinaUpdateComponent,
    PlantillaFeinaDeleteDialogComponent
  ],
  entryComponents: [PlantillaFeinaDeleteDialogComponent]
})
export class GestioClientsPlantillaFeinaModule {}
