import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IControl } from 'app/shared/model/control.model';

@Component({
  selector: 'jhi-control-detail',
  templateUrl: './control-detail.component.html'
})
export class ControlDetailComponent implements OnInit {
  control: IControl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ control }) => {
      this.control = control;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
