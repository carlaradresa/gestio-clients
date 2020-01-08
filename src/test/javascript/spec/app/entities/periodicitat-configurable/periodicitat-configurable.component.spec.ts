import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { PeriodicitatConfigurableComponent } from 'app/entities/periodicitat-configurable/periodicitat-configurable.component';
import { PeriodicitatConfigurableService } from 'app/entities/periodicitat-configurable/periodicitat-configurable.service';
import { PeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';

describe('Component Tests', () => {
  describe('PeriodicitatConfigurable Management Component', () => {
    let comp: PeriodicitatConfigurableComponent;
    let fixture: ComponentFixture<PeriodicitatConfigurableComponent>;
    let service: PeriodicitatConfigurableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PeriodicitatConfigurableComponent],
        providers: []
      })
        .overrideTemplate(PeriodicitatConfigurableComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodicitatConfigurableComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodicitatConfigurableService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PeriodicitatConfigurable(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.periodicitatConfigurables && comp.periodicitatConfigurables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
