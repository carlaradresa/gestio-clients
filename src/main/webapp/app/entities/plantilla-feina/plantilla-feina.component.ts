import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from './plantilla-feina.service';
import { PlantillaFeinaDeleteDialogComponent } from './plantilla-feina-delete-dialog.component';

@Component({
  selector: 'jhi-plantilla-feina',
  templateUrl: './plantilla-feina.component.html'
})
export class PlantillaFeinaComponent implements OnInit, OnDestroy {
  plantillaFeinas: IPlantillaFeina[];
  eventSubscriber: Subscription;

  constructor(
    protected plantillaFeinaService: PlantillaFeinaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.plantillaFeinaService.query().subscribe((res: HttpResponse<IPlantillaFeina[]>) => {
      this.plantillaFeinas = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPlantillaFeinas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPlantillaFeina) {
    return item.id;
  }

  registerChangeInPlantillaFeinas() {
    this.eventSubscriber = this.eventManager.subscribe('plantillaFeinaListModification', () => this.loadAll());
  }

  delete(plantillaFeina: IPlantillaFeina) {
    const modalRef = this.modalService.open(PlantillaFeinaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plantillaFeina = plantillaFeina;
  }
}
