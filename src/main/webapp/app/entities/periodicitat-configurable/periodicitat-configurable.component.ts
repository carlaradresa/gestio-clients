import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';
import { PeriodicitatConfigurableService } from './periodicitat-configurable.service';
import { PeriodicitatConfigurableDeleteDialogComponent } from './periodicitat-configurable-delete-dialog.component';

@Component({
  selector: 'jhi-periodicitat-configurable',
  templateUrl: './periodicitat-configurable.component.html'
})
export class PeriodicitatConfigurableComponent implements OnInit, OnDestroy {
  periodicitatConfigurables?: IPeriodicitatConfigurable[];
  eventSubscriber?: Subscription;

  constructor(
    protected periodicitatConfigurableService: PeriodicitatConfigurableService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.periodicitatConfigurableService.query().subscribe((res: HttpResponse<IPeriodicitatConfigurable[]>) => {
      this.periodicitatConfigurables = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPeriodicitatConfigurables();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPeriodicitatConfigurable): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPeriodicitatConfigurables(): void {
    this.eventSubscriber = this.eventManager.subscribe('periodicitatConfigurableListModification', () => this.loadAll());
  }

  delete(periodicitatConfigurable: IPeriodicitatConfigurable): void {
    const modalRef = this.modalService.open(PeriodicitatConfigurableDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.periodicitatConfigurable = periodicitatConfigurable;
  }
}
