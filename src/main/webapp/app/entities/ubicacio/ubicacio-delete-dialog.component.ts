import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from './ubicacio.service';

@Component({
  templateUrl: './ubicacio-delete-dialog.component.html'
})
export class UbicacioDeleteDialogComponent {
  ubicacio: IUbicacio;

  constructor(protected ubicacioService: UbicacioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ubicacioService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'ubicacioListModification',
        content: 'Deleted an ubicacio'
      });
      this.activeModal.dismiss(true);
    });
  }
}
