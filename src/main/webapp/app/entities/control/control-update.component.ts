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
import { IControl, Control } from 'app/shared/model/control.model';
import { ControlService } from './control.service';
import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from 'app/entities/treballador/treballador.service';
import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from 'app/entities/feina/feina.service';

@Component({
  selector: 'jhi-control-update',
  templateUrl: './control-update.component.html'
})
export class ControlUpdateComponent implements OnInit {
  isSaving: boolean;

  treballadors: ITreballador[];

  feinas: IFeina[];
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
    protected jhiAlertService: JhiAlertService,
    protected controlService: ControlService,
    protected treballadorService: TreballadorService,
    protected feinaService: FeinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ control }) => {
      this.updateForm(control);
    });
    this.treballadorService
      .query()
      .subscribe(
        (res: HttpResponse<ITreballador[]>) => (this.treballadors = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.feinaService
      .query()
      .subscribe((res: HttpResponse<IFeina[]>) => (this.feinas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(control: IControl) {
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

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      numero: this.editForm.get(['numero']).value,
      setmana: this.editForm.get(['setmana']).value,
      causa: this.editForm.get(['causa']).value,
      dataRevisio:
        this.editForm.get(['dataRevisio']).value != null ? moment(this.editForm.get(['dataRevisio']).value, DATE_TIME_FORMAT) : undefined,
      comentaris: this.editForm.get(['comentaris']).value,
      revisor: this.editForm.get(['revisor']).value,
      feina: this.editForm.get(['feina']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControl>>) {
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

  trackTreballadorById(index: number, item: ITreballador) {
    return item.id;
  }

  trackFeinaById(index: number, item: IFeina) {
    return item.id;
  }
}
