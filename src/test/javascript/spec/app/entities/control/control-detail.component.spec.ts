import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { ControlDetailComponent } from 'app/entities/control/control-detail.component';
import { Control } from 'app/shared/model/control.model';

describe('Component Tests', () => {
  describe('Control Management Detail Component', () => {
    let comp: ControlDetailComponent;
    let fixture: ComponentFixture<ControlDetailComponent>;
    const route = ({ data: of({ control: new Control(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [ControlDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ControlDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ControlDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load control on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.control).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
