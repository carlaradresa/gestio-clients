import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFeina, Feina } from 'app/shared/model/feina.model';
import { FeinaService } from './feina.service';
import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from 'app/entities/plantilla-feina/plantilla-feina.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria/categoria.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from 'app/entities/treballador/treballador.service';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';

type SelectableEntity = IPlantillaFeina | ICategoria | IClient | ITreballador | IUbicacio;

type SelectableManyToManyEntity = ITreballador | IUbicacio;

@Component({
  selector: 'jhi-feina-update',
  templateUrl: './feina-update.component.html'
})
export class FeinaUpdateComponent implements OnInit {
  isSaving = false;
  plantillafeinas: IPlantillaFeina[] = [];
  categorias: ICategoria[] = [];
  clients: IClient[] = [];
  treballadors: ITreballador[] = [];
  ubicacios: IUbicacio[] = [];
  setmanaDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    descripcio: [],
    setmana: [],
    tempsPrevist: [],
    tempsReal: [],
    estat: [],
    intervalControl: [],
    facturacioAutomatica: [],
    observacions: [],
    comentarisTreballador: [],
    plantillaFeina: [],
    categoria: [],
    client: [],
    treballadors: [],
    ubicacios: []
  });

  constructor(
    protected feinaService: FeinaService,
    protected plantillaFeinaService: PlantillaFeinaService,
    protected categoriaService: CategoriaService,
    protected clientService: ClientService,
    protected treballadorService: TreballadorService,
    protected ubicacioService: UbicacioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feina }) => {
      if (!feina.id) {
        const today = moment().startOf('day');
        feina.tempsPrevist = today;
        feina.tempsReal = today;
      }

      this.updateForm(feina);

      this.plantillaFeinaService.query().subscribe((res: HttpResponse<IPlantillaFeina[]>) => (this.plantillafeinas = res.body || []));

      this.categoriaService.query().subscribe((res: HttpResponse<ICategoria[]>) => (this.categorias = res.body || []));

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));

      this.treballadorService.query().subscribe((res: HttpResponse<ITreballador[]>) => (this.treballadors = res.body || []));

      this.ubicacioService.query().subscribe((res: HttpResponse<IUbicacio[]>) => (this.ubicacios = res.body || []));
    });
  }

  updateForm(feina: IFeina): void {
    this.editForm.patchValue({
      id: feina.id,
      nom: feina.nom,
      descripcio: feina.descripcio,
      setmana: feina.setmana,
      tempsPrevist: feina.tempsPrevist ? feina.tempsPrevist.format(DATE_TIME_FORMAT) : null,
      tempsReal: feina.tempsReal ? feina.tempsReal.format(DATE_TIME_FORMAT) : null,
      estat: feina.estat,
      intervalControl: feina.intervalControl,
      facturacioAutomatica: feina.facturacioAutomatica,
      observacions: feina.observacions,
      comentarisTreballador: feina.comentarisTreballador,
      plantillaFeina: feina.plantillaFeina,
      categoria: feina.categoria,
      client: feina.client,
      treballadors: feina.treballadors,
      ubicacios: feina.ubicacios
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feina = this.createFromForm();
    if (feina.id !== undefined) {
      this.subscribeToSaveResponse(this.feinaService.update(feina));
    } else {
      this.subscribeToSaveResponse(this.feinaService.create(feina));
    }
  }

  private createFromForm(): IFeina {
    return {
      ...new Feina(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      descripcio: this.editForm.get(['descripcio'])!.value,
      setmana: this.editForm.get(['setmana'])!.value,
      tempsPrevist: this.editForm.get(['tempsPrevist'])!.value
        ? moment(this.editForm.get(['tempsPrevist'])!.value, DATE_TIME_FORMAT)
        : undefined,
      tempsReal: this.editForm.get(['tempsReal'])!.value ? moment(this.editForm.get(['tempsReal'])!.value, DATE_TIME_FORMAT) : undefined,
      estat: this.editForm.get(['estat'])!.value,
      intervalControl: this.editForm.get(['intervalControl'])!.value,
      facturacioAutomatica: this.editForm.get(['facturacioAutomatica'])!.value,
      observacions: this.editForm.get(['observacions'])!.value,
      comentarisTreballador: this.editForm.get(['comentarisTreballador'])!.value,
      plantillaFeina: this.editForm.get(['plantillaFeina'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      client: this.editForm.get(['client'])!.value,
      treballadors: this.editForm.get(['treballadors'])!.value,
      ubicacios: this.editForm.get(['ubicacios'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeina>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
