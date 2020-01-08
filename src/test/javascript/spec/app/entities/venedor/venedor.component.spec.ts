import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { VenedorComponent } from 'app/entities/venedor/venedor.component';
import { VenedorService } from 'app/entities/venedor/venedor.service';
import { Venedor } from 'app/shared/model/venedor.model';

describe('Component Tests', () => {
  describe('Venedor Management Component', () => {
    let comp: VenedorComponent;
    let fixture: ComponentFixture<VenedorComponent>;
    let service: VenedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [VenedorComponent],
        providers: []
      })
        .overrideTemplate(VenedorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VenedorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VenedorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Venedor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.venedors && comp.venedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
