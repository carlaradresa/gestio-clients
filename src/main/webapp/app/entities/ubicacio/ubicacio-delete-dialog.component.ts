import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from './ubicacio.service';

@Component({
  templateUrl: './ubicacio-delete-dialog.component.html'
})
export class UbicacioDeleteDialogComponent {
  ubicacio?: IUbicacio;

  constructor(protected ubicacioService: UbicacioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ubicacioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ubicacioListModification');
      this.activeModal.close();
    });
  }
}
