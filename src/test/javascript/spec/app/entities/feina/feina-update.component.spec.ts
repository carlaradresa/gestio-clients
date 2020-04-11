import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { FeinaUpdateComponent } from 'app/entities/feina/feina-update.component';
import { FeinaService } from 'app/entities/feina/feina.service';
import { Feina } from 'app/shared/model/feina.model';

describe('Component Tests', () => {
  describe('Feina Management Update Component', () => {
    let comp: FeinaUpdateComponent;
    let fixture: ComponentFixture<FeinaUpdateComponent>;
    let service: FeinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [FeinaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FeinaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeinaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeinaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Feina(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Feina();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
