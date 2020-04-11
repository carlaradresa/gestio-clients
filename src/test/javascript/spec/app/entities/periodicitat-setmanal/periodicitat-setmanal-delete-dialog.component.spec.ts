import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestioClientsTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { PeriodicitatSetmanalDeleteDialogComponent } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal-delete-dialog.component';
import { PeriodicitatSetmanalService } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal.service';

describe('Component Tests', () => {
  describe('PeriodicitatSetmanal Management Delete Component', () => {
    let comp: PeriodicitatSetmanalDeleteDialogComponent;
    let fixture: ComponentFixture<PeriodicitatSetmanalDeleteDialogComponent>;
    let service: PeriodicitatSetmanalService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GestioClientsTestModule],
        declarations: [PeriodicitatSetmanalDeleteDialogComponent]
      })
        .overrideTemplate(PeriodicitatSetmanalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PeriodicitatSetmanalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodicitatSetmanalService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
