import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVenedor } from 'app/shared/model/venedor.model';
import { VenedorService } from './venedor.service';

@Component({
  templateUrl: './venedor-delete-dialog.component.html'
})
export class VenedorDeleteDialogComponent {
  venedor?: IVenedor;

  constructor(protected venedorService: VenedorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.venedorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('venedorListModification');
      this.activeModal.close();
    });
  }
}
