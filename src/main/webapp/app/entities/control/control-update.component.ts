import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IControl, Control } from 'app/shared/model/control.model';
import { ControlService } from './control.service';
import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from 'app/entities/treballador/treballador.service';
import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from 'app/entities/feina/feina.service';

type SelectableEntity = ITreballador | IFeina;

@Component({
  selector: 'jhi-control-update',
  templateUrl: './control-update.component.html'
})
export class ControlUpdateComponent implements OnInit {
  isSaving = false;

  treballadors: ITreballador[] = [];

  feinas: IFeina[] = [];
  setmanaDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    setmana: [],
    causa: [],
    dataRevisio: [],
    comentaris: [],
    revisor: [],
    feina: []
  });

  constructor(
    protected controlService: ControlService,
    protected treballadorService: TreballadorService,
    protected feinaService: FeinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ control }) => {
      this.updateForm(control);

      this.treballadorService
        .query()
        .pipe(
          map((res: HttpResponse<ITreballador[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITreballador[]) => (this.treballadors = resBody));

      this.feinaService
        .query()
        .pipe(
          map((res: HttpResponse<IFeina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IFeina[]) => (this.feinas = resBody));
    });
  }

  updateForm(control: IControl): void {
    this.editForm.patchValue({
      id: control.id,
      numero: control.numero,
      setmana: control.setmana,
      causa: control.causa,
      dataRevisio: control.dataRevisio != null ? control.dataRevisio.format(DATE_TIME_FORMAT) : null,
      comentaris: control.comentaris,
      revisor: control.revisor,
      feina: control.feina
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const control = this.createFromForm();
    if (control.id !== undefined) {
      this.subscribeToSaveResponse(this.controlService.update(control));
    } else {
      this.subscribeToSaveResponse(this.controlService.create(control));
    }
  }

  private createFromForm(): IControl {
    return {
      ...new Control(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      setmana: this.editForm.get(['setmana'])!.value,
      causa: this.editForm.get(['causa'])!.value,
      dataRevisio:
        this.editForm.get(['dataRevisio'])!.value != null ? moment(this.editForm.get(['dataRevisio'])!.value, DATE_TIME_FORMAT) : undefined,
      comentaris: this.editForm.get(['comentaris'])!.value,
      revisor: this.editForm.get(['revisor'])!.value,
      feina: this.editForm.get(['feina'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControl>>): void {
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
