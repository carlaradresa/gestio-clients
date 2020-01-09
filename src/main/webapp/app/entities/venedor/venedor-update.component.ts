import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IVenedor, Venedor } from 'app/shared/model/venedor.model';
import { VenedorService } from './venedor.service';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';

@Component({
  selector: 'jhi-venedor-update',
  templateUrl: './venedor-update.component.html'
})
export class VenedorUpdateComponent implements OnInit {
  isSaving = false;

  ubicacios: IUbicacio[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    telefon: [],
    email: [],
    observacions: [],
    ubicacio: []
  });

  constructor(
    protected venedorService: VenedorService,
    protected ubicacioService: UbicacioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venedor }) => {
      this.updateForm(venedor);

      this.ubicacioService
        .query({ filter: 'venedor-is-null' })
        .pipe(
          map((res: HttpResponse<IUbicacio[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUbicacio[]) => {
          if (!venedor.ubicacio || !venedor.ubicacio.id) {
            this.ubicacios = resBody;
          } else {
            this.ubicacioService
              .find(venedor.ubicacio.id)
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
    });
  }

  updateForm(venedor: IVenedor): void {
    this.editForm.patchValue({
      id: venedor.id,
      nom: venedor.nom,
      telefon: venedor.telefon,
      email: venedor.email,
      observacions: venedor.observacions,
      ubicacio: venedor.ubicacio
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venedor = this.createFromForm();
    if (venedor.id !== undefined) {
      this.subscribeToSaveResponse(this.venedorService.update(venedor));
    } else {
      this.subscribeToSaveResponse(this.venedorService.create(venedor));
    }
  }

  private createFromForm(): IVenedor {
    return {
      ...new Venedor(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      telefon: this.editForm.get(['telefon'])!.value,
      email: this.editForm.get(['email'])!.value,
      observacions: this.editForm.get(['observacions'])!.value,
      ubicacio: this.editForm.get(['ubicacio'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenedor>>): void {
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

  trackById(index: number, item: IUbicacio): any {
    return item.id;
  }
}
