import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeina } from 'app/shared/model/feina.model';

@Component({
  selector: 'jhi-feina-detail',
  templateUrl: './feina-detail.component.html'
})
export class FeinaDetailComponent implements OnInit {
  feina: IFeina;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ feina }) => {
      this.feina = feina;
    });
  }

  previousState() {
    window.history.back();
  }
}
