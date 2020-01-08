import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITreballador } from 'app/shared/model/treballador.model';

@Component({
  selector: 'jhi-treballador-detail',
  templateUrl: './treballador-detail.component.html'
})
export class TreballadorDetailComponent implements OnInit {
  treballador: ITreballador | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ treballador }) => {
      this.treballador = treballador;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
