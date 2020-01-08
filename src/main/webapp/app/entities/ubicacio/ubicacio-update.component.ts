import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUbicacio, Ubicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from './ubicacio.service';

@Component({
  selector: 'jhi-ubicacio-update',
  templateUrl: './ubicacio-update.component.html'
})
export class UbicacioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    longitud: [],
    latitud: [],
    ubicacio: []
  });

  constructor(protected ubicacioService: UbicacioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ubicacio }) => {
      this.updateForm(ubicacio);
    });
  }

  updateForm(ubicacio: IUbicacio): void {
    this.editForm.patchValue({
      id: ubicacio.id,
      longitud: ubicacio.longitud,
      latitud: ubicacio.latitud,
      ubicacio: ubicacio.ubicacio
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ubicacio = this.createFromForm();
    if (ubicacio.id !== undefined) {
      this.subscribeToSaveResponse(this.ubicacioService.update(ubicacio));
    } else {
      this.subscribeToSaveResponse(this.ubicacioService.create(ubicacio));
    }
  }

  private createFromForm(): IUbicacio {
    return {
      ...new Ubicacio(),
      id: this.editForm.get(['id'])!.value,
      longitud: this.editForm.get(['longitud'])!.value,
      latitud: this.editForm.get(['latitud'])!.value,
      ubicacio: this.editForm.get(['ubicacio'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUbicacio>>): void {
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
