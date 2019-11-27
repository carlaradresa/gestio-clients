import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IUbicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';
import { IVenedor } from 'app/shared/model/venedor.model';
import { VenedorService } from 'app/entities/venedor/venedor.service';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html'
})
export class ClientUpdateComponent implements OnInit {
  isSaving: boolean;

  ubicacios: IUbicacio[];

  venedors: IVenedor[];

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
    protected jhiAlertService: JhiAlertService,
    protected clientService: ClientService,
    protected ubicacioService: UbicacioService,
    protected venedorService: VenedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);
    });
    this.ubicacioService.query({ filter: 'client-is-null' }).subscribe(
      (res: HttpResponse<IUbicacio[]>) => {
        if (!this.editForm.get('ubicacio').value || !this.editForm.get('ubicacio').value.id) {
          this.ubicacios = res.body;
        } else {
          this.ubicacioService
            .find(this.editForm.get('ubicacio').value.id)
            .subscribe(
              (subRes: HttpResponse<IUbicacio>) => (this.ubicacios = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.venedorService
      .query()
      .subscribe((res: HttpResponse<IVenedor[]>) => (this.venedors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(client: IClient) {
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

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      direccio: this.editForm.get(['direccio']).value,
      localitat: this.editForm.get(['localitat']).value,
      telefon: this.editForm.get(['telefon']).value,
      email: this.editForm.get(['email']).value,
      nif: this.editForm.get(['nif']).value,
      codiUbicacio: this.editForm.get(['codiUbicacio']).value,
      observacions: this.editForm.get(['observacions']).value,
      ubicacio: this.editForm.get(['ubicacio']).value,
      venedor: this.editForm.get(['venedor']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>) {
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

  trackVenedorById(index: number, item: IVenedor) {
    return item.id;
  }
}
