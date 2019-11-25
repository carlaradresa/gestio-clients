import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IRepeticioTascaSetmanal, RepeticioTascaSetmanal } from 'app/shared/model/repeticio-tasca-setmanal.model';
import { RepeticioTascaSetmanalService } from './repeticio-tasca-setmanal.service';
import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from 'app/entities/feina/feina.service';

@Component({
  selector: 'jhi-repeticio-tasca-setmanal-update',
  templateUrl: './repeticio-tasca-setmanal-update.component.html'
})
export class RepeticioTascaSetmanalUpdateComponent implements OnInit {
  isSaving: boolean;

  feinas: IFeina[];

  editForm = this.fb.group({
    id: [],
    dia: [],
    activo: [],
    feina: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected repeticioTascaSetmanalService: RepeticioTascaSetmanalService,
    protected feinaService: FeinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ repeticioTascaSetmanal }) => {
      this.updateForm(repeticioTascaSetmanal);
    });
    this.feinaService
      .query()
      .subscribe((res: HttpResponse<IFeina[]>) => (this.feinas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(repeticioTascaSetmanal: IRepeticioTascaSetmanal) {
    this.editForm.patchValue({
      id: repeticioTascaSetmanal.id,
      dia: repeticioTascaSetmanal.dia,
      activo: repeticioTascaSetmanal.activo,
      feina: repeticioTascaSetmanal.feina
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const repeticioTascaSetmanal = this.createFromForm();
    if (repeticioTascaSetmanal.id !== undefined) {
      this.subscribeToSaveResponse(this.repeticioTascaSetmanalService.update(repeticioTascaSetmanal));
    } else {
      this.subscribeToSaveResponse(this.repeticioTascaSetmanalService.create(repeticioTascaSetmanal));
    }
  }

  private createFromForm(): IRepeticioTascaSetmanal {
    return {
      ...new RepeticioTascaSetmanal(),
      id: this.editForm.get(['id']).value,
      dia: this.editForm.get(['dia']).value,
      activo: this.editForm.get(['activo']).value,
      feina: this.editForm.get(['feina']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRepeticioTascaSetmanal>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackFeinaById(index: number, item: IFeina) {
    return item.id;
  }
}
