import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { VenedorDetailComponent } from 'app/entities/venedor/venedor-detail.component';
import { Venedor } from 'app/shared/model/venedor.model';

describe('Component Tests', () => {
  describe('Venedor Management Detail Component', () => {
    let comp: VenedorDetailComponent;
    let fixture: ComponentFixture<VenedorDetailComponent>;
    const route = ({ data: of({ venedor: new Venedor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [VenedorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VenedorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VenedorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load venedor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.venedor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
