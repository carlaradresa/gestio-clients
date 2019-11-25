import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';

@Component({
  templateUrl: './categoria-delete-dialog.component.html'
})
export class CategoriaDeleteDialogComponent {
  categoria: ICategoria;

  constructor(protected categoriaService: CategoriaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.categoriaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'categoriaListModification',
        content: 'Deleted an categoria'
      });
      this.activeModal.dismiss(true);
    });
  }
}
