import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClient } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { ClientDeleteDialogComponent } from './client-delete-dialog.component';

@Component({
  selector: 'jhi-client',
  templateUrl: './client.component.html'
})
export class ClientComponent implements OnInit, OnDestroy {
  clients: IClient[];
  eventSubscriber: Subscription;

  constructor(protected clientService: ClientService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => {
      this.clients = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInClients();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IClient) {
    return item.id;
  }

  registerChangeInClients() {
    this.eventSubscriber = this.eventManager.subscribe('clientListModification', () => this.loadAll());
  }

  delete(client: IClient) {
    const modalRef = this.modalService.open(ClientDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.client = client;
  }
}
