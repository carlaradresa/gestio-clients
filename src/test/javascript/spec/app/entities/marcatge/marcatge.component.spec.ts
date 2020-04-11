import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { MarcatgeComponent } from 'app/entities/marcatge/marcatge.component';
import { MarcatgeService } from 'app/entities/marcatge/marcatge.service';
import { Marcatge } from 'app/shared/model/marcatge.model';

describe('Component Tests', () => {
  describe('Marcatge Management Component', () => {
    let comp: MarcatgeComponent;
    let fixture: ComponentFixture<MarcatgeComponent>;
    let service: MarcatgeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [MarcatgeComponent]
      })
        .overrideTemplate(MarcatgeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MarcatgeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MarcatgeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Marcatge(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.marcatges && comp.marcatges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
