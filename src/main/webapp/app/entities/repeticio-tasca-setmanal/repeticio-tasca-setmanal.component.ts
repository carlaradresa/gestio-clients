import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRepeticioTascaSetmanal } from 'app/shared/model/repeticio-tasca-setmanal.model';
import { RepeticioTascaSetmanalService } from './repeticio-tasca-setmanal.service';
import { RepeticioTascaSetmanalDeleteDialogComponent } from './repeticio-tasca-setmanal-delete-dialog.component';

@Component({
  selector: 'jhi-repeticio-tasca-setmanal',
  templateUrl: './repeticio-tasca-setmanal.component.html'
})
export class RepeticioTascaSetmanalComponent implements OnInit, OnDestroy {
  repeticioTascaSetmanals: IRepeticioTascaSetmanal[];
  eventSubscriber: Subscription;

  constructor(
    protected repeticioTascaSetmanalService: RepeticioTascaSetmanalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.repeticioTascaSetmanalService.query().subscribe((res: HttpResponse<IRepeticioTascaSetmanal[]>) => {
      this.repeticioTascaSetmanals = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInRepeticioTascaSetmanals();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRepeticioTascaSetmanal) {
    return item.id;
  }

  registerChangeInRepeticioTascaSetmanals() {
    this.eventSubscriber = this.eventManager.subscribe('repeticioTascaSetmanalListModification', () => this.loadAll());
  }

  delete(repeticioTascaSetmanal: IRepeticioTascaSetmanal) {
    const modalRef = this.modalService.open(RepeticioTascaSetmanalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.repeticioTascaSetmanal = repeticioTascaSetmanal;
  }
}
