import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from './treballador.service';
import { TreballadorDeleteDialogComponent } from './treballador-delete-dialog.component';

@Component({
  selector: 'jhi-treballador',
  templateUrl: './treballador.component.html'
})
export class TreballadorComponent implements OnInit, OnDestroy {
  treballadors: ITreballador[];
  eventSubscriber: Subscription;

  constructor(
    protected treballadorService: TreballadorService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.treballadorService.query().subscribe((res: HttpResponse<ITreballador[]>) => {
      this.treballadors = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTreballadors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITreballador) {
    return item.id;
  }

  registerChangeInTreballadors() {
    this.eventSubscriber = this.eventManager.subscribe('treballadorListModification', () => this.loadAll());
  }

  delete(treballador: ITreballador) {
    const modalRef = this.modalService.open(TreballadorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.treballador = treballador;
  }
}
