import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ControlService } from 'app/entities/control/control.service';
import { IControl, Control } from 'app/shared/model/control.model';

describe('Service Tests', () => {
  describe('Control Service', () => {
    let injector: TestBed;
    let service: ControlService;
    let httpMock: HttpTestingController;
    let elemDefault: IControl;
    let expectedResult: IControl | IControl[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ControlService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Control(0, 0, currentDate, 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            setmana: currentDate.format(DATE_FORMAT),
            dataRevisio: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Control', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            setmana: currentDate.format(DATE_FORMAT),
            dataRevisio: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            setmana: currentDate,
            dataRevisio: currentDate
          },
          returnedFromService
        );
        service
          .create(new Control())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Control', () => {
        const returnedFromService = Object.assign(
          {
            numero: 1,
            setmana: currentDate.format(DATE_FORMAT),
            causa: 'BBBBBB',
            dataRevisio: currentDate.format(DATE_TIME_FORMAT),
            comentaris: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            setmana: currentDate,
            dataRevisio: currentDate
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

      it('should return a list of Control', () => {
        const returnedFromService = Object.assign(
          {
            numero: 1,
            setmana: currentDate.format(DATE_FORMAT),
            causa: 'BBBBBB',
            dataRevisio: currentDate.format(DATE_TIME_FORMAT),
            comentaris: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            setmana: currentDate,
            dataRevisio: currentDate
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

      it('should delete a Control', () => {
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
