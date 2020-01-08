import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { UbicacioComponent } from 'app/entities/ubicacio/ubicacio.component';
import { UbicacioService } from 'app/entities/ubicacio/ubicacio.service';
import { Ubicacio } from 'app/shared/model/ubicacio.model';

describe('Component Tests', () => {
  describe('Ubicacio Management Component', () => {
    let comp: UbicacioComponent;
    let fixture: ComponentFixture<UbicacioComponent>;
    let service: UbicacioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [UbicacioComponent],
        providers: []
      })
        .overrideTemplate(UbicacioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UbicacioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UbicacioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ubicacio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ubicacios && comp.ubicacios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
