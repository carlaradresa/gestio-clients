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
  treballadors?: ITreballador[];
  eventSubscriber?: Subscription;

  constructor(
    protected treballadorService: TreballadorService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.treballadorService.query().subscribe((res: HttpResponse<ITreballador[]>) => {
      this.treballadors = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTreballadors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITreballador): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTreballadors(): void {
    this.eventSubscriber = this.eventManager.subscribe('treballadorListModification', () => this.loadAll());
  }

  delete(treballador: ITreballador): void {
    const modalRef = this.modalService.open(TreballadorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.treballador = treballador;
  }
}
