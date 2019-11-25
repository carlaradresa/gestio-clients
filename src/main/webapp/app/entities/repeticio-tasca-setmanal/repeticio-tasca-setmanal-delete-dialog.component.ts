import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRepeticioTascaSetmanal } from 'app/shared/model/repeticio-tasca-setmanal.model';
import { RepeticioTascaSetmanalService } from './repeticio-tasca-setmanal.service';

@Component({
  templateUrl: './repeticio-tasca-setmanal-delete-dialog.component.html'
})
export class RepeticioTascaSetmanalDeleteDialogComponent {
  repeticioTascaSetmanal: IRepeticioTascaSetmanal;

  constructor(
    protected repeticioTascaSetmanalService: RepeticioTascaSetmanalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.repeticioTascaSetmanalService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'repeticioTascaSetmanalListModification',
        content: 'Deleted an repeticioTascaSetmanal'
      });
      this.activeModal.dismiss(true);
    });
  }
}
