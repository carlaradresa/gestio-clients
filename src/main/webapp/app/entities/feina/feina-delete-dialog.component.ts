import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from './feina.service';

@Component({
  templateUrl: './feina-delete-dialog.component.html'
})
export class FeinaDeleteDialogComponent {
  feina?: IFeina;

  constructor(protected feinaService: FeinaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feinaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('feinaListModification');
      this.activeModal.close();
    });
  }
}
