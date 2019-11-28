import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ITreballador, Treballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from './treballador.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IFeina } from 'app/shared/model/feina.model';
import { FeinaService } from 'app/entities/feina/feina.service';

@Component({
  selector: 'jhi-treballador-update',
  templateUrl: './treballador-update.component.html'
})
export class TreballadorUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  feinas: IFeina[];

  editForm = this.fb.group({
    id: [],
    nom: [],
    carregaHores: [],
    actiu: [],
    controlQualitat: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected treballadorService: TreballadorService,
    protected userService: UserService,
    protected feinaService: FeinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ treballador }) => {
      this.updateForm(treballador);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.feinaService
      .query()
      .subscribe((res: HttpResponse<IFeina[]>) => (this.feinas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(treballador: ITreballador) {
    this.editForm.patchValue({
      id: treballador.id,
      nom: treballador.nom,
      carregaHores: treballador.carregaHores,
      actiu: treballador.actiu,
      controlQualitat: treballador.controlQualitat,
      user: treballador.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const treballador = this.createFromForm();
    if (treballador.id !== undefined) {
      this.subscribeToSaveResponse(this.treballadorService.update(treballador));
    } else {
      this.subscribeToSaveResponse(this.treballadorService.create(treballador));
    }
  }

  private createFromForm(): ITreballador {
    return {
      ...new Treballador(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      carregaHores: this.editForm.get(['carregaHores']).value,
      actiu: this.editForm.get(['actiu']).value,
      controlQualitat: this.editForm.get(['controlQualitat']).value,
      user: this.editForm.get(['user']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITreballador>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackFeinaById(index: number, item: IFeina) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
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
