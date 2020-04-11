import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { PeriodicitatSetmanalUpdateComponent } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal-update.component';
import { PeriodicitatSetmanalService } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal.service';
import { PeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';

describe('Component Tests', () => {
  describe('PeriodicitatSetmanal Management Update Component', () => {
    let comp: PeriodicitatSetmanalUpdateComponent;
    let fixture: ComponentFixture<PeriodicitatSetmanalUpdateComponent>;
    let service: PeriodicitatSetmanalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PeriodicitatSetmanalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PeriodicitatSetmanalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodicitatSetmanalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodicitatSetmanalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PeriodicitatSetmanal(123);
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
        const entity = new PeriodicitatSetmanal();
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
