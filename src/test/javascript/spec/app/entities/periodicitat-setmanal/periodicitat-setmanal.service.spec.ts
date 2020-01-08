import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { PeriodicitatSetmanalService } from 'app/entities/periodicitat-setmanal/periodicitat-setmanal.service';
import { IPeriodicitatSetmanal, PeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';
import { Dia } from 'app/shared/model/enumerations/dia.model';

describe('Service Tests', () => {
  describe('PeriodicitatSetmanal Service', () => {
    let injector: TestBed;
    let service: PeriodicitatSetmanalService;
    let httpMock: HttpTestingController;
    let elemDefault: IPeriodicitatSetmanal;
    let expectedResult: IPeriodicitatSetmanal | IPeriodicitatSetmanal[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PeriodicitatSetmanalService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PeriodicitatSetmanal(0, Dia.DILLUNS);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PeriodicitatSetmanal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PeriodicitatSetmanal())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PeriodicitatSetmanal', () => {
        const returnedFromService = Object.assign(
          {
            diaSetmana: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PeriodicitatSetmanal', () => {
        const returnedFromService = Object.assign(
          {
            diaSetmana: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a PeriodicitatSetmanal', () => {
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
