import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';
import { CategoriaDeleteDialogComponent } from './categoria-delete-dialog.component';

@Component({
  selector: 'jhi-categoria',
  templateUrl: './categoria.component.html'
})
export class CategoriaComponent implements OnInit, OnDestroy {
  categorias: ICategoria[];
  eventSubscriber: Subscription;

  constructor(protected categoriaService: CategoriaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.categoriaService.query().subscribe((res: HttpResponse<ICategoria[]>) => {
      this.categorias = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCategorias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICategoria) {
    return item.id;
  }

  registerChangeInCategorias() {
    this.eventSubscriber = this.eventManager.subscribe('categoriaListModification', () => this.loadAll());
  }

  delete(categoria: ICategoria) {
    const modalRef = this.modalService.open(CategoriaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categoria = categoria;
  }
}
