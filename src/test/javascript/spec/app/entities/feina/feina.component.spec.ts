import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { FeinaComponent } from 'app/entities/feina/feina.component';
import { FeinaService } from 'app/entities/feina/feina.service';
import { Feina } from 'app/shared/model/feina.model';

describe('Component Tests', () => {
  describe('Feina Management Component', () => {
    let comp: FeinaComponent;
    let fixture: ComponentFixture<FeinaComponent>;
    let service: FeinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [FeinaComponent],
        providers: []
      })
        .overrideTemplate(FeinaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeinaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeinaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Feina(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.feinas && comp.feinas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
