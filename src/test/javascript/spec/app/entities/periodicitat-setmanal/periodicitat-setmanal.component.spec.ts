import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { PeriodicitatSetmanalComponent } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal.component';
import { PeriodicitatSetmanalService } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal.service';
import { PeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';

describe('Component Tests', () => {
  describe('PeriodicitatSetmanal Management Component', () => {
    let comp: PeriodicitatSetmanalComponent;
    let fixture: ComponentFixture<PeriodicitatSetmanalComponent>;
    let service: PeriodicitatSetmanalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PeriodicitatSetmanalComponent],
        providers: []
      })
        .overrideTemplate(PeriodicitatSetmanalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodicitatSetmanalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodicitatSetmanalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PeriodicitatSetmanal(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.periodicitatSetmanals && comp.periodicitatSetmanals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
