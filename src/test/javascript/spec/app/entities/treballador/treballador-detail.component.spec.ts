import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { TreballadorDetailComponent } from 'app/entities/treballador/treballador-detail.component';
import { Treballador } from 'app/shared/model/treballador.model';

describe('Component Tests', () => {
  describe('Treballador Management Detail Component', () => {
    let comp: TreballadorDetailComponent;
    let fixture: ComponentFixture<TreballadorDetailComponent>;
    const route = ({ data: of({ treballador: new Treballador(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [TreballadorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TreballadorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TreballadorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load treballador on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.treballador).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
