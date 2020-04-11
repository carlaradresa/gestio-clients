import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { UbicacioUpdateComponent } from 'app/entities/ubicacio/ubicacio-update.component';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';
import { Ubicacio } from 'app/shared/model/ubicacio.model';

describe('Component Tests', () => {
  describe('Ubicacio Management Update Component', () => {
    let comp: UbicacioUpdateComponent;
    let fixture: ComponentFixture<UbicacioUpdateComponent>;
    let service: UbicacioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [UbicacioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UbicacioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UbicacioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UbicacioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ubicacio(123);
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
        const entity = new Ubicacio();
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
