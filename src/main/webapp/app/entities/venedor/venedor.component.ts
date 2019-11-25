import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVenedor } from 'app/shared/model/venedor.model';
import { VenedorService } from './venedor.service';
import { VenedorDeleteDialogComponent } from './venedor-delete-dialog.component';

@Component({
  selector: 'jhi-venedor',
  templateUrl: './venedor.component.html'
})
export class VenedorComponent implements OnInit, OnDestroy {
  venedors: IVenedor[];
  eventSubscriber: Subscription;

  constructor(protected venedorService: VenedorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.venedorService.query().subscribe((res: HttpResponse<IVenedor[]>) => {
      this.venedors = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInVenedors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVenedor) {
    return item.id;
  }

  registerChangeInVenedors() {
    this.eventSubscriber = this.eventManager.subscribe('venedorListModification', () => this.loadAll());
  }

  delete(venedor: IVenedor) {
    const modalRef = this.modalService.open(VenedorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.venedor = venedor;
  }
}
