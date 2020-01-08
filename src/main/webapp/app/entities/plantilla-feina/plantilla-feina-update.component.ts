import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlantillaFeina, PlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from './plantilla-feina.service';
import { IPeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';
import { PeriodicitatConfigurableService } from 'app/entities/periodicitat-configurable/periodicitat-configurable.service';
import { IPeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';
import { PeriodicitatSetmanalService } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal.service';

type SelectableEntity = IPeriodicitatConfigurable | IPeriodicitatSetmanal;

@Component({
  selector: 'jhi-plantilla-feina-update',
  templateUrl: './plantilla-feina-update.component.html'
})
export class PlantillaFeinaUpdateComponent implements OnInit {
  isSaving = false;

  periodicitatconfigurables: IPeriodicitatConfigurable[] = [];

  periodicitatsetmanals: IPeriodicitatSetmanal[] = [];
  setmanaInicialDp: any;
  setmanaFinalDp: any;

  editForm = this.fb.group({
    id: [],
    horaInici: [],
    horaFinal: [],
    tempsPrevist: [],
    facturacioAutomatica: [],
    observacions: [],
    setmanaInicial: [],
    setmanaFinal: [],
    numeroControl: [],
    periodicitatConfigurable: [],
    periodicitatSetmanals: []
  });

  constructor(
    protected plantillaFeinaService: PlantillaFeinaService,
    protected periodicitatConfigurableService: PeriodicitatConfigurableService,
    protected periodicitatSetmanalService: PeriodicitatSetmanalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plantillaFeina }) => {
      this.updateForm(plantillaFeina);

      this.periodicitatConfigurableService
        .query({ filter: 'plantilla-is-null' })
        .pipe(
          map((res: HttpResponse<IPeriodicitatConfigurable[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPeriodicitatConfigurable[]) => {
          if (!plantillaFeina.periodicitatConfigurable || !plantillaFeina.periodicitatConfigurable.id) {
            this.periodicitatconfigurables = resBody;
          } else {
            this.periodicitatConfigurableService
              .find(plantillaFeina.periodicitatConfigurable.id)
              .pipe(
                map((subRes: HttpResponse<IPeriodicitatConfigurable>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPeriodicitatConfigurable[]) => {
                this.periodicitatconfigurables = concatRes;
              });
          }
        });

      this.periodicitatSetmanalService
        .query()
        .pipe(
          map((res: HttpResponse<IPeriodicitatSetmanal[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPeriodicitatSetmanal[]) => (this.periodicitatsetmanals = resBody));
    });
  }

  updateForm(plantillaFeina: IPlantillaFeina): void {
    this.editForm.patchValue({
      id: plantillaFeina.id,
      horaInici: plantillaFeina.horaInici != null ? plantillaFeina.horaInici.format(DATE_TIME_FORMAT) : null,
      horaFinal: plantillaFeina.horaFinal != null ? plantillaFeina.horaFinal.format(DATE_TIME_FORMAT) : null,
      tempsPrevist: plantillaFeina.tempsPrevist,
      facturacioAutomatica: plantillaFeina.facturacioAutomatica,
      observacions: plantillaFeina.observacions,
      setmanaInicial: plantillaFeina.setmanaInicial,
      setmanaFinal: plantillaFeina.setmanaFinal,
      numeroControl: plantillaFeina.numeroControl,
      periodicitatConfigurable: plantillaFeina.periodicitatConfigurable,
      periodicitatSetmanals: plantillaFeina.periodicitatSetmanals
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      horaInici:
        this.editForm.get(['horaInici'])!.value != null ? moment(this.editForm.get(['horaInici'])!.value, DATE_TIME_FORMAT) : undefined,
      horaFinal:
        this.editForm.get(['horaFinal'])!.value != null ? moment(this.editForm.get(['horaFinal'])!.value, DATE_TIME_FORMAT) : undefined,
      tempsPrevist: this.editForm.get(['tempsPrevist'])!.value,
      facturacioAutomatica: this.editForm.get(['facturacioAutomatica'])!.value,
      observacions: this.editForm.get(['observacions'])!.value,
      setmanaInicial: this.editForm.get(['setmanaInicial'])!.value,
      setmanaFinal: this.editForm.get(['setmanaFinal'])!.value,
      numeroControl: this.editForm.get(['numeroControl'])!.value,
      periodicitatConfigurable: this.editForm.get(['periodicitatConfigurable'])!.value,
      periodicitatSetmanals: this.editForm.get(['periodicitatSetmanals'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlantillaFeina>>): void {
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

  getSelected(selectedVals: IPeriodicitatSetmanal[], option: IPeriodicitatSetmanal): IPeriodicitatSetmanal {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
