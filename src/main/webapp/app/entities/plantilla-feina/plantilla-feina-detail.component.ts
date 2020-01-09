import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';

@Component({
  selector: 'jhi-plantilla-feina-detail',
  templateUrl: './plantilla-feina-detail.component.html'
})
export class PlantillaFeinaDetailComponent implements OnInit {
  plantillaFeina: IPlantillaFeina | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantillaFeina }) => {
      this.plantillaFeina = plantillaFeina;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
