import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { PeriodicitatConfigurableUpdateComponent } from 'app/entities/periodicitat-configurable/periodicitat-configurable-update.component';
import { PeriodicitatConfigurableService } from 'app/entities/periodicitat-configurable/periodicitat-configurable.service';
import { PeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';

describe('Component Tests', () => {
  describe('PeriodicitatConfigurable Management Update Component', () => {
    let comp: PeriodicitatConfigurableUpdateComponent;
    let fixture: ComponentFixture<PeriodicitatConfigurableUpdateComponent>;
    let service: PeriodicitatConfigurableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PeriodicitatConfigurableUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PeriodicitatConfigurableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodicitatConfigurableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodicitatConfigurableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PeriodicitatConfigurable(123);
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
        const entity = new PeriodicitatConfigurable();
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
