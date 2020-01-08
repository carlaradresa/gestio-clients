import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';
import { IVenedor } from 'app/shared/model/venedor.model';
import { VenedorService } from 'app/entities/venedor/venedor.service';

type SelectableEntity = IUbicacio | IVenedor;

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html'
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;

  ubicacios: IUbicacio[] = [];

  venedors: IVenedor[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    direccio: [],
    localitat: [],
    telefon: [],
    email: [],
    nif: [],
    codiUbicacio: [],
    observacions: [],
    ubicacio: [],
    venedor: []
  });

  constructor(
    protected clientService: ClientService,
    protected ubicacioService: UbicacioService,
    protected venedorService: VenedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);

      this.ubicacioService
        .query({ filter: 'client-is-null' })
        .pipe(
          map((res: HttpResponse<IUbicacio[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUbicacio[]) => {
          if (!client.ubicacio || !client.ubicacio.id) {
            this.ubicacios = resBody;
          } else {
            this.ubicacioService
              .find(client.ubicacio.id)
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

      this.venedorService
        .query()
        .pipe(
          map((res: HttpResponse<IVenedor[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IVenedor[]) => (this.venedors = resBody));
    });
  }

  updateForm(client: IClient): void {
    this.editForm.patchValue({
      id: client.id,
      nom: client.nom,
      direccio: client.direccio,
      localitat: client.localitat,
      telefon: client.telefon,
      email: client.email,
      nif: client.nif,
      codiUbicacio: client.codiUbicacio,
      observacions: client.observacions,
      ubicacio: client.ubicacio,
      venedor: client.venedor
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const client = this.createFromForm();
    if (client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(client));
    }
  }

  private createFromForm(): IClient {
    return {
      ...new Client(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      direccio: this.editForm.get(['direccio'])!.value,
      localitat: this.editForm.get(['localitat'])!.value,
      telefon: this.editForm.get(['telefon'])!.value,
      email: this.editForm.get(['email'])!.value,
      nif: this.editForm.get(['nif'])!.value,
      codiUbicacio: this.editForm.get(['codiUbicacio'])!.value,
      observacions: this.editForm.get(['observacions'])!.value,
      ubicacio: this.editForm.get(['ubicacio'])!.value,
      venedor: this.editForm.get(['venedor'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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
