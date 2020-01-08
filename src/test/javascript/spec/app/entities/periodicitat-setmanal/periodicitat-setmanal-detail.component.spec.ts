import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { PeriodicitatSetmanalDetailComponent } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal-detail.component';
import { PeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';

describe('Component Tests', () => {
  describe('PeriodicitatSetmanal Management Detail Component', () => {
    let comp: PeriodicitatSetmanalDetailComponent;
    let fixture: ComponentFixture<PeriodicitatSetmanalDetailComponent>;
    const route = ({ data: of({ periodicitatSetmanal: new PeriodicitatSetmanal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PeriodicitatSetmanalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PeriodicitatSetmanalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeriodicitatSetmanalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load periodicitatSetmanal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.periodicitatSetmanal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
