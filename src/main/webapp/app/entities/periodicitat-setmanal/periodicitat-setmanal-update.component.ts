import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPeriodicitatSetmanal, PeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';
import { PeriodicitatSetmanalService } from './periodicitat-setmanal.service';

@Component({
  selector: 'jhi-periodicitat-setmanal-update',
  templateUrl: './periodicitat-setmanal-update.component.html'
})
export class PeriodicitatSetmanalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    diaSetmana: []
  });

  constructor(
    protected periodicitatSetmanalService: PeriodicitatSetmanalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodicitatSetmanal }) => {
      this.updateForm(periodicitatSetmanal);
    });
  }

  updateForm(periodicitatSetmanal: IPeriodicitatSetmanal): void {
    this.editForm.patchValue({
      id: periodicitatSetmanal.id,
      diaSetmana: periodicitatSetmanal.diaSetmana
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const periodicitatSetmanal = this.createFromForm();
    if (periodicitatSetmanal.id !== undefined) {
      this.subscribeToSaveResponse(this.periodicitatSetmanalService.update(periodicitatSetmanal));
    } else {
      this.subscribeToSaveResponse(this.periodicitatSetmanalService.create(periodicitatSetmanal));
    }
  }

  private createFromForm(): IPeriodicitatSetmanal {
    return {
      ...new PeriodicitatSetmanal(),
      id: this.editForm.get(['id'])!.value,
      diaSetmana: this.editForm.get(['diaSetmana'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodicitatSetmanal>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
