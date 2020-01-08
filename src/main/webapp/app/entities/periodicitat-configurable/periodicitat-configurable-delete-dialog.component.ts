import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';
import { PeriodicitatConfigurableService } from './periodicitat-configurable.service';

@Component({
  templateUrl: './periodicitat-configurable-delete-dialog.component.html'
})
export class PeriodicitatConfigurableDeleteDialogComponent {
  periodicitatConfigurable?: IPeriodicitatConfigurable;

  constructor(
    protected periodicitatConfigurableService: PeriodicitatConfigurableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodicitatConfigurableService.delete(id).subscribe(() => {
      this.eventManager.broadcast('periodicitatConfigurableListModification');
      this.activeModal.close();
    });
  }
}
