import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { PlantillaFeinaDetailComponent } from 'app/entities/plantilla-feina/plantilla-feina-detail.component';
import { PlantillaFeina } from 'app/shared/model/plantilla-feina.model';

describe('Component Tests', () => {
  describe('PlantillaFeina Management Detail Component', () => {
    let comp: PlantillaFeinaDetailComponent;
    let fixture: ComponentFixture<PlantillaFeinaDetailComponent>;
    const route = ({ data: of({ plantillaFeina: new PlantillaFeina(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PlantillaFeinaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlantillaFeinaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlantillaFeinaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plantillaFeina on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plantillaFeina).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
