import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMarcatge } from 'app/shared/model/marcatge.model';

@Component({
  selector: 'jhi-marcatge-detail',
  templateUrl: './marcatge-detail.component.html'
})
export class MarcatgeDetailComponent implements OnInit {
  marcatge: IMarcatge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ marcatge }) => {
      this.marcatge = marcatge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
