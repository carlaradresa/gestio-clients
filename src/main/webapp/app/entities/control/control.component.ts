import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IControl } from 'app/shared/model/control.model';
import { ControlService } from './control.service';
import { ControlDeleteDialogComponent } from './control-delete-dialog.component';

@Component({
  selector: 'jhi-control',
  templateUrl: './control.component.html'
})
export class ControlComponent implements OnInit, OnDestroy {
  controls?: IControl[];
  eventSubscriber?: Subscription;

  constructor(protected controlService: ControlService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.controlService.query().subscribe((res: HttpResponse<IControl[]>) => {
      this.controls = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInControls();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IControl): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInControls(): void {
    this.eventSubscriber = this.eventManager.subscribe('controlListModification', () => this.loadAll());
  }

  delete(control: IControl): void {
    const modalRef = this.modalService.open(ControlDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.control = control;
  }
}
