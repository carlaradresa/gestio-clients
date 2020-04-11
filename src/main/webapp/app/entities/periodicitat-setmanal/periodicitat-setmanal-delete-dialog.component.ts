import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';
import { PeriodicitatSetmanalService } from './periodicitat-setmanal.service';

@Component({
  templateUrl: './periodicitat-setmanal-delete-dialog.component.html'
})
export class PeriodicitatSetmanalDeleteDialogComponent {
  periodicitatSetmanal?: IPeriodicitatSetmanal;

  constructor(
    protected periodicitatSetmanalService: PeriodicitatSetmanalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodicitatSetmanalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('periodicitatSetmanalListModification');
      this.activeModal.close();
    });
  }
}
