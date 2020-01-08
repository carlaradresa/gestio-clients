import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITreballador, Treballador } from 'app/shared/model/treballador.model';
import { TreballadorService } from './treballador.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-treballador-update',
  templateUrl: './treballador-update.component.html'
})
export class TreballadorUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    carregaHores: [],
    actiu: [],
    controlQualitat: [],
    user: []
  });

  constructor(
    protected treballadorService: TreballadorService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ treballador }) => {
      this.updateForm(treballador);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(treballador: ITreballador): void {
    this.editForm.patchValue({
      id: treballador.id,
      nom: treballador.nom,
      carregaHores: treballador.carregaHores,
      actiu: treballador.actiu,
      controlQualitat: treballador.controlQualitat,
      user: treballador.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      carregaHores: this.editForm.get(['carregaHores'])!.value,
      actiu: this.editForm.get(['actiu'])!.value,
      controlQualitat: this.editForm.get(['controlQualitat'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITreballador>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
