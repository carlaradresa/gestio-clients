import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';

@Component({
  selector: 'jhi-periodicitat-configurable-detail',
  templateUrl: './periodicitat-configurable-detail.component.html'
})
export class PeriodicitatConfigurableDetailComponent implements OnInit {
  periodicitatConfigurable: IPeriodicitatConfigurable | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodicitatConfigurable }) => {
      this.periodicitatConfigurable = periodicitatConfigurable;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
