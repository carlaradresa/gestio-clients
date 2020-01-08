import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';

@Component({
  selector: 'jhi-periodicitat-setmanal-detail',
  templateUrl: './periodicitat-setmanal-detail.component.html'
})
export class PeriodicitatSetmanalDetailComponent implements OnInit {
  periodicitatSetmanal: IPeriodicitatSetmanal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodicitatSetmanal }) => {
      this.periodicitatSetmanal = periodicitatSetmanal;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
