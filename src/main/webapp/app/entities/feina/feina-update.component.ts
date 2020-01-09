import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IFeina, Feina } from 'app/shared/model/feina.model';
import { FeinaService } from './feina.service';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';
import { IPlantillaFeina } from 'app/shared/model/plantilla-feina.model';
import { PlantillaFeinaService } from 'app/entities/plantilla-feina/plantilla-feina.service';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria/categoria.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { ITreballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from 'app/entities/treballador/treballador.service';

type SelectableEntity = IUbicacio | IPlantillaFeina | ICategoria | IClient | ITreballador;

@Component({
  selector: 'jhi-feina-update',
  templateUrl: './feina-update.component.html'
})
export class FeinaUpdateComponent implements OnInit {
  isSaving = false;

  ubicacios: IUbicacio[] = [];

  plantillafeinas: IPlantillaFeina[] = [];

  categorias: ICategoria[] = [];

  clients: IClient[] = [];

  treballadors: ITreballador[] = [];
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
    ubicacio: [],
    ubicacio: [],
    plantillaFeina: [],
    categoria: [],
    client: [],
    treballadors: []
  });

  constructor(
    protected feinaService: FeinaService,
    protected ubicacioService: UbicacioService,
    protected plantillaFeinaService: PlantillaFeinaService,
    protected categoriaService: CategoriaService,
    protected clientService: ClientService,
    protected treballadorService: TreballadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feina }) => {
      this.updateForm(feina);

      this.ubicacioService
        .query({ filter: 'venedor-is-null' })
        .pipe(
          map((res: HttpResponse<IUbicacio[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUbicacio[]) => {
          if (!feina.ubicacio || !feina.ubicacio.id) {
            this.ubicacios = resBody;
          } else {
            this.ubicacioService
              .find(feina.ubicacio.id)
              .pipe(
                map((subRes: HttpResponse<IUbicacio>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IUbicacio[]) => {
                this.ubicacios = concatRes;
              });
          }
        });

      this.plantillaFeinaService
        .query()
        .pipe(
          map((res: HttpResponse<IPlantillaFeina[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPlantillaFeina[]) => (this.plantillafeinas = resBody));

      this.categoriaService
        .query()
        .pipe(
          map((res: HttpResponse<ICategoria[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICategoria[]) => (this.categorias = resBody));

      this.clientService
        .query()
        .pipe(
          map((res: HttpResponse<IClient[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IClient[]) => (this.clients = resBody));

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

  updateForm(feina: IFeina): void {
    this.editForm.patchValue({
      id: feina.id,
      nom: feina.nom,
      descripcio: feina.descripcio,
      setmana: feina.setmana,
      tempsPrevist: feina.tempsPrevist,
      tempsReal: feina.tempsReal,
      estat: feina.estat,
      intervalControl: feina.intervalControl,
      facturacioAutomatica: feina.facturacioAutomatica,
      observacions: feina.observacions,
      comentarisTreballador: feina.comentarisTreballador,
      ubicacio: feina.ubicacio,
      ubicacio: feina.ubicacio,
      plantillaFeina: feina.plantillaFeina,
      categoria: feina.categoria,
      client: feina.client,
      treballadors: feina.treballadors
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
      tempsPrevist: this.editForm.get(['tempsPrevist'])!.value,
      tempsReal: this.editForm.get(['tempsReal'])!.value,
      estat: this.editForm.get(['estat'])!.value,
      intervalControl: this.editForm.get(['intervalControl'])!.value,
      facturacioAutomatica: this.editForm.get(['facturacioAutomatica'])!.value,
      observacions: this.editForm.get(['observacions'])!.value,
      comentarisTreballador: this.editForm.get(['comentarisTreballador'])!.value,
      ubicacio: this.editForm.get(['ubicacio'])!.value,
      ubicacio: this.editForm.get(['ubicacio'])!.value,
      plantillaFeina: this.editForm.get(['plantillaFeina'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      client: this.editForm.get(['client'])!.value,
      treballadors: this.editForm.get(['treballadors'])!.value
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

  getSelected(selectedVals: ITreballador[], option: ITreballador): ITreballador {
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
