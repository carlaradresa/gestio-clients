import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { PeriodicitatConfigurableDetailComponent } from 'app/entities/periodicitat-configurable/periodicitat-configurable-detail.component';
import { PeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';

describe('Component Tests', () => {
  describe('PeriodicitatConfigurable Management Detail Component', () => {
    let comp: PeriodicitatConfigurableDetailComponent;
    let fixture: ComponentFixture<PeriodicitatConfigurableDetailComponent>;
    const route = ({ data: of({ periodicitatConfigurable: new PeriodicitatConfigurable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PeriodicitatConfigurableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PeriodicitatConfigurableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeriodicitatConfigurableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load periodicitatConfigurable on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.periodicitatConfigurable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
