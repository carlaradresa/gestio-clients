import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { PlantillaFeinaComponent } from 'app/entities/plantilla-feina/plantilla-feina.component';
import { PlantillaFeinaService } from 'app/entities/plantilla-feina/plantilla-feina.service';
import { PlantillaFeina } from 'app/shared/model/plantilla-feina.model';

describe('Component Tests', () => {
  describe('PlantillaFeina Management Component', () => {
    let comp: PlantillaFeinaComponent;
    let fixture: ComponentFixture<PlantillaFeinaComponent>;
    let service: PlantillaFeinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PlantillaFeinaComponent]
      })
        .overrideTemplate(PlantillaFeinaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlantillaFeinaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlantillaFeinaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlantillaFeina(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.plantillaFeinas && comp.plantillaFeinas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
