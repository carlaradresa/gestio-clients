import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from './plantilla-feina.service';

@Component({
  templateUrl: './plantilla-feina-delete-dialog.component.html'
})
export class PlantillaFeinaDeleteDialogComponent {
  plantillaFeina: IPlantillaFeina;

  constructor(
    protected plantillaFeinaService: PlantillaFeinaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.plantillaFeinaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'plantillaFeinaListModification',
        content: 'Deleted an plantillaFeina'
      });
      this.activeModal.dismiss(true);
    });
  }
}
