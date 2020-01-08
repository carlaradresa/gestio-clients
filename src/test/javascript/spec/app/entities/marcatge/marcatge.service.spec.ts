import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MarcatgeService } from 'app/entities/marcatge/marcatge.service';
import { IMarcatge, Marcatge } from 'app/shared/model/marcatge.model';

describe('Service Tests', () => {
  describe('Marcatge Service', () => {
    let injector: TestBed;
    let service: MarcatgeService;
    let httpMock: HttpTestingController;
    let elemDefault: IMarcatge;
    let expectedResult: IMarcatge | IMarcatge[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MarcatgeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Marcatge(0, currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            horaEntrada: currentDate.format(DATE_TIME_FORMAT),
            horaSortida: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Marcatge', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            horaEntrada: currentDate.format(DATE_TIME_FORMAT),
            horaSortida: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            horaEntrada: currentDate,
            horaSortida: currentDate
          },
          returnedFromService
        );
        service
          .create(new Marcatge())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Marcatge', () => {
        const returnedFromService = Object.assign(
          {
            horaEntrada: currentDate.format(DATE_TIME_FORMAT),
            horaSortida: currentDate.format(DATE_TIME_FORMAT),
            desviacio: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            horaEntrada: currentDate,
            horaSortida: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Marcatge', () => {
        const returnedFromService = Object.assign(
          {
            horaEntrada: currentDate.format(DATE_TIME_FORMAT),
            horaSortida: currentDate.format(DATE_TIME_FORMAT),
            desviacio: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            horaEntrada: currentDate,
            horaSortida: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Marcatge', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
