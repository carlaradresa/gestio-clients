import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { ControlUpdateComponent } from 'app/entities/control/control-update.component';
import { ControlService } from 'app/entities/control/control.service';
import { Control } from 'app/shared/model/control.model';

describe('Component Tests', () => {
  describe('Control Management Update Component', () => {
    let comp: ControlUpdateComponent;
    let fixture: ComponentFixture<ControlUpdateComponent>;
    let service: ControlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [ControlUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ControlUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ControlUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ControlService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Control(123);
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
        const entity = new Control();
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
