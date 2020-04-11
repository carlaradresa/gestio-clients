import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { MarcatgeUpdateComponent } from 'app/entities/marcatge/marcatge-update.component';
import { MarcatgeService } from 'app/entities/marcatge/marcatge.service';
import { Marcatge } from 'app/shared/model/marcatge.model';

describe('Component Tests', () => {
  describe('Marcatge Management Update Component', () => {
    let comp: MarcatgeUpdateComponent;
    let fixture: ComponentFixture<MarcatgeUpdateComponent>;
    let service: MarcatgeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [MarcatgeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MarcatgeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MarcatgeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MarcatgeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Marcatge(123);
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
        const entity = new Marcatge();
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
