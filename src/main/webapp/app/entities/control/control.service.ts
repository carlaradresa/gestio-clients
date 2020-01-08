import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IControl } from 'app/shared/model/control.model';

type EntityResponseType = HttpResponse<IControl>;
type EntityArrayResponseType = HttpResponse<IControl[]>;

@Injectable({ providedIn: 'root' })
export class ControlService {
  public resourceUrl = SERVER_API_URL + 'api/controls';

  constructor(protected http: HttpClient) {}

  create(control: IControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(control);
    return this.http
      .post<IControl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(control: IControl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(control);
    return this.http
      .put<IControl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IControl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IControl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(control: IControl): IControl {
    const copy: IControl = Object.assign({}, control, {
      setmana: control.setmana && control.setmana.isValid() ? control.setmana.format(DATE_FORMAT) : undefined,
      dataRevisio: control.dataRevisio && control.dataRevisio.isValid() ? control.dataRevisio.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.setmana = res.body.setmana ? moment(res.body.setmana) : undefined;
      res.body.dataRevisio = res.body.dataRevisio ? moment(res.body.dataRevisio) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((control: IControl) => {
        control.setmana = control.setmana ? moment(control.setmana) : undefined;
        control.dataRevisio = control.dataRevisio ? moment(control.dataRevisio) : undefined;
      });
    }
    return res;
  }
}
