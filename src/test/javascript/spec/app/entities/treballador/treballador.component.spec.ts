import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { TreballadorComponent } from 'app/entities/treballador/treballador.component';
import { TreballadorService } from 'app/entities/treballador/treballador.service';
import { Treballador } from 'app/shared/model/treballador.model';

describe('Component Tests', () => {
  describe('Treballador Management Component', () => {
    let comp: TreballadorComponent;
    let fixture: ComponentFixture<TreballadorComponent>;
    let service: TreballadorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [TreballadorComponent],
        providers: []
      })
        .overrideTemplate(TreballadorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TreballadorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TreballadorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Treballador(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.treballadors && comp.treballadors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
