import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from './treballador.service';

@Component({
  templateUrl: './treballador-delete-dialog.component.html'
})
export class TreballadorDeleteDialogComponent {
  treballador: ITreballador;

  constructor(
    protected treballadorService: TreballadorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.treballadorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'treballadorListModification',
        content: 'Deleted an treballador'
      });
      this.activeModal.dismiss(true);
    });
  }
}
