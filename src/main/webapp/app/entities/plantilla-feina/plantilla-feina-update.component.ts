import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPlantillaFeina, PlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from './plantilla-feina.service';

@Component({
  selector: 'jhi-plantilla-feina-update',
  templateUrl: './plantilla-feina-update.component.html'
})
export class PlantillaFeinaUpdateComponent implements OnInit {
  isSaving: boolean;
  setmanaInicialDp: any;
  setmanaFinalDp: any;

  editForm = this.fb.group({
    id: [],
    dia: [],
    horaInici: [],
    horaFinal: [],
    periodicitat: [],
    tempsPrevist: [],
    facturacioAutomatica: [],
    observacions: [],
    setmanaInicial: [],
    setmanaFinal: [],
    numeroControl: []
  });

  constructor(protected plantillaFeinaService: PlantillaFeinaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ plantillaFeina }) => {
      this.updateForm(plantillaFeina);
    });
  }

  updateForm(plantillaFeina: IPlantillaFeina) {
    this.editForm.patchValue({
      id: plantillaFeina.id,
      dia: plantillaFeina.dia,
      horaInici: plantillaFeina.horaInici != null ? plantillaFeina.horaInici.format(DATE_TIME_FORMAT) : null,
      horaFinal: plantillaFeina.horaFinal != null ? plantillaFeina.horaFinal.format(DATE_TIME_FORMAT) : null,
      periodicitat: plantillaFeina.periodicitat,
      tempsPrevist: plantillaFeina.tempsPrevist,
      facturacioAutomatica: plantillaFeina.facturacioAutomatica,
      observacions: plantillaFeina.observacions,
      setmanaInicial: plantillaFeina.setmanaInicial,
      setmanaFinal: plantillaFeina.setmanaFinal,
      numeroControl: plantillaFeina.numeroControl
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const plantillaFeina = this.createFromForm();
    if (plantillaFeina.id !== undefined) {
      this.subscribeToSaveResponse(this.plantillaFeinaService.update(plantillaFeina));
    } else {
      this.subscribeToSaveResponse(this.plantillaFeinaService.create(plantillaFeina));
    }
  }

  private createFromForm(): IPlantillaFeina {
    return {
      ...new PlantillaFeina(),
      id: this.editForm.get(['id']).value,
      dia: this.editForm.get(['dia']).value,
      horaInici:
        this.editForm.get(['horaInici']).value != null ? moment(this.editForm.get(['horaInici']).value, DATE_TIME_FORMAT) : undefined,
      horaFinal:
        this.editForm.get(['horaFinal']).value != null ? moment(this.editForm.get(['horaFinal']).value, DATE_TIME_FORMAT) : undefined,
      periodicitat: this.editForm.get(['periodicitat']).value,
      tempsPrevist: this.editForm.get(['tempsPrevist']).value,
      facturacioAutomatica: this.editForm.get(['facturacioAutomatica']).value,
      observacions: this.editForm.get(['observacions']).value,
      setmanaInicial: this.editForm.get(['setmanaInicial']).value,
      setmanaFinal: this.editForm.get(['setmanaFinal']).value,
      numeroControl: this.editForm.get(['numeroControl']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlantillaFeina>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
