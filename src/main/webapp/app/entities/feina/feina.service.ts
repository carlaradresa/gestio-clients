import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFeina } from 'app/shared/model/feina.model';

type EntityResponseType = HttpResponse<IFeina>;
type EntityArrayResponseType = HttpResponse<IFeina[]>;

@Injectable({ providedIn: 'root' })
export class FeinaService {
  public resourceUrl = SERVER_API_URL + 'api/feinas';

  constructor(protected http: HttpClient) {}

  create(feina: IFeina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feina);
    return this.http
      .post<IFeina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(feina: IFeina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feina);
    return this.http
      .put<IFeina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFeina>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFeina[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(feina: IFeina): IFeina {
    const copy: IFeina = Object.assign({}, feina, {
      setmana: feina.setmana && feina.setmana.isValid() ? feina.setmana.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.setmana = res.body.setmana ? moment(res.body.setmana) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((feina: IFeina) => {
        feina.setmana = feina.setmana ? moment(feina.setmana) : undefined;
      });
    }
    return res;
  }
}
