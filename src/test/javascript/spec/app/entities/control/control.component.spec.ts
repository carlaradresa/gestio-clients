import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GestioClientsTestModule } from '../../../test.module';
import { ControlComponent } from 'app/entities/control/control.component';
import { ControlService } from 'app/entities/control/control.service';
import { Control } from 'app/shared/model/control.model';

describe('Component Tests', () => {
  describe('Control Management Component', () => {
    let comp: ControlComponent;
    let fixture: ComponentFixture<ControlComponent>;
    let service: ControlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [ControlComponent],
        providers: []
      })
        .overrideTemplate(ControlComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ControlComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ControlService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Control(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.controls && comp.controls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
