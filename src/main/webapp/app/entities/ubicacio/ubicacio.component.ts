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
  ubicacios?: IUbicacio[];
  eventSubscriber?: Subscription;

  constructor(protected ubicacioService: UbicacioService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.ubicacioService.query().subscribe((res: HttpResponse<IUbicacio[]>) => {
      this.ubicacios = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUbicacios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUbicacio): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUbicacios(): void {
    this.eventSubscriber = this.eventManager.subscribe('ubicacioListModification', () => this.loadAll());
  }

  delete(ubicacio: IUbicacio): void {
    const modalRef = this.modalService.open(UbicacioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ubicacio = ubicacio;
  }
}
