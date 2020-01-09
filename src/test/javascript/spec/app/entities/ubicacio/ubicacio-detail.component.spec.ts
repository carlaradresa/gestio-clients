import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { UbicacioDetailComponent } from 'app/entities/ubicacio/ubicacio-detail.component';
import { Ubicacio } from 'app/shared/model/ubicacio.model';

describe('Component Tests', () => {
  describe('Ubicacio Management Detail Component', () => {
    let comp: UbicacioDetailComponent;
    let fixture: ComponentFixture<UbicacioDetailComponent>;
    const route = ({ data: of({ ubicacio: new Ubicacio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [UbicacioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UbicacioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UbicacioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ubicacio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ubicacio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
