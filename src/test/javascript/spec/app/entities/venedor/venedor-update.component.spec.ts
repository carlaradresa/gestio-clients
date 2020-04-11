import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { VenedorUpdateComponent } from 'app/entities/venedor/venedor-update.component';
import { VenedorService } from 'app/entities/venedor/venedor.service';
import { Venedor } from 'app/shared/model/venedor.model';

describe('Component Tests', () => {
  describe('Venedor Management Update Component', () => {
    let comp: VenedorUpdateComponent;
    let fixture: ComponentFixture<VenedorUpdateComponent>;
    let service: VenedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [VenedorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VenedorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VenedorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VenedorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Venedor(123);
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
        const entity = new Venedor();
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
