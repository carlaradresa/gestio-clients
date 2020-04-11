import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { PlantillaFeinaUpdateComponent } from 'app/entities/plantilla-feina/plantilla-feina-update.component';
import { PlantillaFeinaService } from 'app/entities/plantilla-feina/plantilla-feina.service';
import { PlantillaFeina } from 'app/shared/model/plantilla-feina.model';

describe('Component Tests', () => {
  describe('PlantillaFeina Management Update Component', () => {
    let comp: PlantillaFeinaUpdateComponent;
    let fixture: ComponentFixture<PlantillaFeinaUpdateComponent>;
    let service: PlantillaFeinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PlantillaFeinaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlantillaFeinaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlantillaFeinaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlantillaFeinaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlantillaFeina(123);
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
        const entity = new PlantillaFeina();
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
