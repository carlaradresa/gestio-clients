import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IMarcatge, Marcatge } from 'app/shared/model/marcatge.model';
import { MarcatgeService } from './marcatge.service';
import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from 'app/entities/feina/feina.service';
import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from 'app/entities/treballador/treballador.service';

@Component({
  selector: 'jhi-marcatge-update',
  templateUrl: './marcatge-update.component.html'
})
export class MarcatgeUpdateComponent implements OnInit {
  isSaving: boolean;

  feinas: IFeina[];

  treballadors: ITreballador[];

  editForm = this.fb.group({
    id: [],
    horaEntrada: [],
    horaSortida: [],
    desviacio: [],
    feina: [],
    treballador: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected marcatgeService: MarcatgeService,
    protected feinaService: FeinaService,
    protected treballadorService: TreballadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ marcatge }) => {
      this.updateForm(marcatge);
    });
    this.feinaService
      .query()
      .subscribe((res: HttpResponse<IFeina[]>) => (this.feinas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.treballadorService
      .query()
      .subscribe(
        (res: HttpResponse<ITreballador[]>) => (this.treballadors = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(marcatge: IMarcatge) {
    this.editForm.patchValue({
      id: marcatge.id,
      horaEntrada: marcatge.horaEntrada != null ? marcatge.horaEntrada.format(DATE_TIME_FORMAT) : null,
      horaSortida: marcatge.horaSortida != null ? marcatge.horaSortida.format(DATE_TIME_FORMAT) : null,
      desviacio: marcatge.desviacio,
      feina: marcatge.feina,
      treballador: marcatge.treballador
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const marcatge = this.createFromForm();
    if (marcatge.id !== undefined) {
      this.subscribeToSaveResponse(this.marcatgeService.update(marcatge));
    } else {
      this.subscribeToSaveResponse(this.marcatgeService.create(marcatge));
    }
  }

  private createFromForm(): IMarcatge {
    return {
      ...new Marcatge(),
      id: this.editForm.get(['id']).value,
      horaEntrada:
        this.editForm.get(['horaEntrada']).value != null ? moment(this.editForm.get(['horaEntrada']).value, DATE_TIME_FORMAT) : undefined,
      horaSortida:
        this.editForm.get(['horaSortida']).value != null ? moment(this.editForm.get(['horaSortida']).value, DATE_TIME_FORMAT) : undefined,
      desviacio: this.editForm.get(['desviacio']).value,
      feina: this.editForm.get(['feina']).value,
      treballador: this.editForm.get(['treballador']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarcatge>>) {
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

  trackTreballadorById(index: number, item: ITreballador) {
    return item.id;
  }
}
