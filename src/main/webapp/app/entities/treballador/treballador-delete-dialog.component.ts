import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from './treballador.service';

@Component({
  templateUrl: './treballador-delete-dialog.component.html'
})
export class TreballadorDeleteDialogComponent {
  treballador?: ITreballador;

  constructor(
    protected treballadorService: TreballadorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.treballadorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('treballadorListModification');
      this.activeModal.close();
    });
  }
}
