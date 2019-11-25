import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMarcatge } from 'app/shared/model/marcatge.model';
import { MarcatgeService } from './marcatge.service';
import { MarcatgeDeleteDialogComponent } from './marcatge-delete-dialog.component';

@Component({
  selector: 'jhi-marcatge',
  templateUrl: './marcatge.component.html'
})
export class MarcatgeComponent implements OnInit, OnDestroy {
  marcatges: IMarcatge[];
  eventSubscriber: Subscription;

  constructor(protected marcatgeService: MarcatgeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.marcatgeService.query().subscribe((res: HttpResponse<IMarcatge[]>) => {
      this.marcatges = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMarcatges();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMarcatge) {
    return item.id;
  }

  registerChangeInMarcatges() {
    this.eventSubscriber = this.eventManager.subscribe('marcatgeListModification', () => this.loadAll());
  }

  delete(marcatge: IMarcatge) {
    const modalRef = this.modalService.open(MarcatgeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.marcatge = marcatge;
  }
}
