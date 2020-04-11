import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FeinaService } from 'app/entities/feina/feina.service';
import { IFeina, Feina } from 'app/shared/model/feina.model';
import { Estat } from 'app/shared/model/enumerations/estat.model';

describe('Service Tests', () => {
  describe('Feina Service', () => {
    let injector: TestBed;
    let service: FeinaService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeina;
    let expectedResult: IFeina | IFeina[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FeinaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Feina(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, currentDate, Estat.ACTIU, 0, false, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            setmana: currentDate.format(DATE_FORMAT),
            tempsPrevist: currentDate.format(DATE_TIME_FORMAT),
            tempsReal: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Feina', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            setmana: currentDate.format(DATE_FORMAT),
            tempsPrevist: currentDate.format(DATE_TIME_FORMAT),
            tempsReal: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            setmana: currentDate,
            tempsPrevist: currentDate,
            tempsReal: currentDate
          },
          returnedFromService
        );

        service.create(new Feina()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Feina', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            descripcio: 'BBBBBB',
            setmana: currentDate.format(DATE_FORMAT),
            tempsPrevist: currentDate.format(DATE_TIME_FORMAT),
            tempsReal: currentDate.format(DATE_TIME_FORMAT),
            estat: 'BBBBBB',
            intervalControl: 1,
            facturacioAutomatica: true,
            observacions: 'BBBBBB',
            comentarisTreballador: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            setmana: currentDate,
            tempsPrevist: currentDate,
            tempsReal: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Feina', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            descripcio: 'BBBBBB',
            setmana: currentDate.format(DATE_FORMAT),
            tempsPrevist: currentDate.format(DATE_TIME_FORMAT),
            tempsReal: currentDate.format(DATE_TIME_FORMAT),
            estat: 'BBBBBB',
            intervalControl: 1,
            facturacioAutomatica: true,
            observacions: 'BBBBBB',
            comentarisTreballador: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            setmana: currentDate,
            tempsPrevist: currentDate,
            tempsReal: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Feina', () => {
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
