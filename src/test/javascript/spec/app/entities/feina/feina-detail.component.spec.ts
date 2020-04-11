import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { FeinaDetailComponent } from 'app/entities/feina/feina-detail.component';
import { Feina } from 'app/shared/model/feina.model';

describe('Component Tests', () => {
  describe('Feina Management Detail Component', () => {
    let comp: FeinaDetailComponent;
    let fixture: ComponentFixture<FeinaDetailComponent>;
    const route = ({ data: of({ feina: new Feina(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [FeinaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FeinaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeinaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feina on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feina).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
