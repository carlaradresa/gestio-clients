import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMarcatge } from 'app/shared/model/marcatge.model';
import { MarcatgeService } from './marcatge.service';

@Component({
  templateUrl: './marcatge-delete-dialog.component.html'
})
export class MarcatgeDeleteDialogComponent {
  marcatge?: IMarcatge;

  constructor(protected marcatgeService: MarcatgeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.marcatgeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('marcatgeListModification');
      this.activeModal.close();
    });
  }
}
