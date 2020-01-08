import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMarcatge, Marcatge } from 'app/shared/model/marcatge.model';
import { MarcatgeService } from './marcatge.service';
import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from 'app/entities/feina/feina.service';
import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from 'app/entities/treballador/treballador.service';

type SelectableEntity = IFeina | ITreballador;

@Component({
  selector: 'jhi-marcatge-update',
  templateUrl: './marcatge-update.component.html'
})
export class MarcatgeUpdateComponent implements OnInit {
  isSaving = false;

  feinas: IFeina[] = [];

  treballadors: ITreballador[] = [];

  editForm = this.fb.group({
    id: [],
    horaEntrada: [],
    horaSortida: [],
    desviacio: [],
    feina: [],
    treballador: []
  });

  constructor(
    protected marcatgeService: MarcatgeService,
    protected feinaService: FeinaService,
    protected treballadorService: TreballadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ marcatge }) => {
      this.updateForm(marcatge);

      this.feinaService
        .query()
        .pipe(
          map((res: HttpResponse<IFeina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IFeina[]) => (this.feinas = resBody));

      this.treballadorService
        .query()
        .pipe(
          map((res: HttpResponse<ITreballador[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITreballador[]) => (this.treballadors = resBody));
    });
  }

  updateForm(marcatge: IMarcatge): void {
    this.editForm.patchValue({
      id: marcatge.id,
      horaEntrada: marcatge.horaEntrada != null ? marcatge.horaEntrada.format(DATE_TIME_FORMAT) : null,
      horaSortida: marcatge.horaSortida != null ? marcatge.horaSortida.format(DATE_TIME_FORMAT) : null,
      desviacio: marcatge.desviacio,
      feina: marcatge.feina,
      treballador: marcatge.treballador
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      horaEntrada:
        this.editForm.get(['horaEntrada'])!.value != null ? moment(this.editForm.get(['horaEntrada'])!.value, DATE_TIME_FORMAT) : undefined,
      horaSortida:
        this.editForm.get(['horaSortida'])!.value != null ? moment(this.editForm.get(['horaSortida'])!.value, DATE_TIME_FORMAT) : undefined,
      desviacio: this.editForm.get(['desviacio'])!.value,
      feina: this.editForm.get(['feina'])!.value,
      treballador: this.editForm.get(['treballador'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarcatge>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
