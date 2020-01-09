import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMarcatge } from 'app/shared/model/marcatge.model';

type EntityResponseType = HttpResponse<IMarcatge>;
type EntityArrayResponseType = HttpResponse<IMarcatge[]>;

@Injectable({ providedIn: 'root' })
export class MarcatgeService {
  public resourceUrl = SERVER_API_URL + 'api/marcatges';

  constructor(protected http: HttpClient) {}

  create(marcatge: IMarcatge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(marcatge);
    return this.http
      .post<IMarcatge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(marcatge: IMarcatge): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(marcatge);
    return this.http
      .put<IMarcatge>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMarcatge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMarcatge[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(marcatge: IMarcatge): IMarcatge {
    const copy: IMarcatge = Object.assign({}, marcatge, {
      horaEntrada: marcatge.horaEntrada && marcatge.horaEntrada.isValid() ? marcatge.horaEntrada.toJSON() : undefined,
      horaSortida: marcatge.horaSortida && marcatge.horaSortida.isValid() ? marcatge.horaSortida.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.horaEntrada = res.body.horaEntrada ? moment(res.body.horaEntrada) : undefined;
      res.body.horaSortida = res.body.horaSortida ? moment(res.body.horaSortida) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((marcatge: IMarcatge) => {
        marcatge.horaEntrada = marcatge.horaEntrada ? moment(marcatge.horaEntrada) : undefined;
        marcatge.horaSortida = marcatge.horaSortida ? moment(marcatge.horaSortida) : undefined;
      });
    }
    return res;
  }
}
