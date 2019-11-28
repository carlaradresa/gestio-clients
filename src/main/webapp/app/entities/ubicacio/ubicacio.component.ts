import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from './ubicacio.service';
import { UbicacioDeleteDialogComponent } from './ubicacio-delete-dialog.component';

@Component({
  selector: 'jhi-ubicacio',
  templateUrl: './ubicacio.component.html'
})
export class UbicacioComponent implements OnInit, OnDestroy {
  ubicacios: IUbicacio[];
  eventSubscriber: Subscription;

  constructor(protected ubicacioService: UbicacioService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.ubicacioService.query().subscribe((res: HttpResponse<IUbicacio[]>) => {
      this.ubicacios = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInUbicacios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUbicacio) {
    return item.id;
  }

  registerChangeInUbicacios() {
    this.eventSubscriber = this.eventManager.subscribe('ubicacioListModification', () => this.loadAll());
  }

  delete(ubicacio: IUbicacio) {
    const modalRef = this.modalService.open(UbicacioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ubicacio = ubicacio;
  }
}
