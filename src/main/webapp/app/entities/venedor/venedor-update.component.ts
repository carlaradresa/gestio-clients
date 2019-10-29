import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVenedor, Venedor } from 'app/shared/model/venedor.model';
import { VenedorService } from './venedor.service';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';

@Component({
  selector: 'jhi-venedor-update',
  templateUrl: './venedor-update.component.html'
})
export class VenedorUpdateComponent implements OnInit {
  isSaving: boolean;

  ubicacios: IUbicacio[];

  editForm = this.fb.group({
    id: [],
    numero: [],
    nom: [],
    telefon: [],
    email: [],
    observacions: [],
    ubicacio: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected venedorService: VenedorService,
    protected ubicacioService: UbicacioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ venedor }) => {
      this.updateForm(venedor);
    });
    this.ubicacioService
      .query({ filter: 'venedor-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IUbicacio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUbicacio[]>) => response.body)
      )
      .subscribe(
        (res: IUbicacio[]) => {
          if (!this.editForm.get('ubicacio').value || !this.editForm.get('ubicacio').value.id) {
            this.ubicacios = res;
          } else {
            this.ubicacioService
              .find(this.editForm.get('ubicacio').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IUbicacio>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IUbicacio>) => subResponse.body)
              )
              .subscribe(
                (subRes: IUbicacio) => (this.ubicacios = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(venedor: IVenedor) {
    this.editForm.patchValue({
      id: venedor.id,
      numero: venedor.numero,
      nom: venedor.nom,
      telefon: venedor.telefon,
      email: venedor.email,
      observacions: venedor.observacions,
      ubicacio: venedor.ubicacio
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      numero: this.editForm.get(['numero']).value,
      nom: this.editForm.get(['nom']).value,
      telefon: this.editForm.get(['telefon']).value,
      email: this.editForm.get(['email']).value,
      observacions: this.editForm.get(['observacions']).value,
      ubicacio: this.editForm.get(['ubicacio']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenedor>>) {
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

  trackUbicacioById(index: number, item: IUbicacio) {
    return item.id;
  }
}
