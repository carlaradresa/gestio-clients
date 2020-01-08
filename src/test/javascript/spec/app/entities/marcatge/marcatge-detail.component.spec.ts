import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestioClientsTestModule } from '../../../test.module';
import { MarcatgeDetailComponent } from 'app/entities/marcatge/marcatge-detail.component';
import { Marcatge } from 'app/shared/model/marcatge.model';

describe('Component Tests', () => {
  describe('Marcatge Management Detail Component', () => {
    let comp: MarcatgeDetailComponent;
    let fixture: ComponentFixture<MarcatgeDetailComponent>;
    const route = ({ data: of({ marcatge: new Marcatge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [MarcatgeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MarcatgeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MarcatgeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load marcatge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.marcatge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
