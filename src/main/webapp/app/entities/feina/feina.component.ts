import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from './feina.service';
import { FeinaDeleteDialogComponent } from './feina-delete-dialog.component';

@Component({
  selector: 'jhi-feina',
  templateUrl: './feina.component.html'
})
export class FeinaComponent implements OnInit, OnDestroy {
  feinas: IFeina[];
  eventSubscriber: Subscription;

  constructor(protected feinaService: FeinaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.feinaService.query().subscribe((res: HttpResponse<IFeina[]>) => {
      this.feinas = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInFeinas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFeina) {
    return item.id;
  }

  registerChangeInFeinas() {
    this.eventSubscriber = this.eventManager.subscribe('feinaListModification', () => this.loadAll());
  }

  delete(feina: IFeina) {
    const modalRef = this.modalService.open(FeinaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.feina = feina;
  }
}
