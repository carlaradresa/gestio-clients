import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IUbicacio, Ubicacio } from 'app/shared/model/ubicacio.model';
import { UbicacioService } from './ubicacio.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';
import { IVenedor } from 'app/shared/model/venedor.model';
import { VenedorService } from 'app/entities/venedor/venedor.service';

@Component({
  selector: 'jhi-ubicacio-update',
  templateUrl: './ubicacio-update.component.html'
})
export class UbicacioUpdateComponent implements OnInit {
  isSaving: boolean;

  clients: IClient[];

  venedors: IVenedor[];

  editForm = this.fb.group({
    id: [],
    longitud: [],
    latitud: [],
    ubicacio: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ubicacioService: UbicacioService,
    protected clientService: ClientService,
    protected venedorService: VenedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ubicacio }) => {
      this.updateForm(ubicacio);
    });
    this.clientService
      .query()
      .subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.venedorService
      .query()
      .subscribe((res: HttpResponse<IVenedor[]>) => (this.venedors = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ubicacio: IUbicacio) {
    this.editForm.patchValue({
      id: ubicacio.id,
      longitud: ubicacio.longitud,
      latitud: ubicacio.latitud,
      ubicacio: ubicacio.ubicacio
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ubicacio = this.createFromForm();
    if (ubicacio.id !== undefined) {
      this.subscribeToSaveResponse(this.ubicacioService.update(ubicacio));
    } else {
      this.subscribeToSaveResponse(this.ubicacioService.create(ubicacio));
    }
  }

  private createFromForm(): IUbicacio {
    return {
      ...new Ubicacio(),
      id: this.editForm.get(['id']).value,
      longitud: this.editForm.get(['longitud']).value,
      latitud: this.editForm.get(['latitud']).value,
      ubicacio: this.editForm.get(['ubicacio']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUbicacio>>) {
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

  trackClientById(index: number, item: IClient) {
    return item.id;
  }

  trackVenedorById(index: number, item: IVenedor) {
    return item.id;
  }
}
