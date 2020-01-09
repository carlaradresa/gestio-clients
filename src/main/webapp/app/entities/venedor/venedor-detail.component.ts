import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVenedor } from 'app/shared/model/venedor.model';

@Component({
  selector: 'jhi-venedor-detail',
  templateUrl: './venedor-detail.component.html'
})
export class VenedorDetailComponent implements OnInit {
  venedor: IVenedor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venedor }) => {
      this.venedor = venedor;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
