import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUbicacio } from 'app/shared/model/ubicacio.model';

@Component({
  selector: 'jhi-ubicacio-detail',
  templateUrl: './ubicacio-detail.component.html'
})
export class UbicacioDetailComponent implements OnInit {
  ubicacio: IUbicacio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ubicacio }) => {
      this.ubicacio = ubicacio;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
