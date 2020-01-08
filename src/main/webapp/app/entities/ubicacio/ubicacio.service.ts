import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUbicacio } from 'app/shared/model/ubicacio.model';

type EntityResponseType = HttpResponse<IUbicacio>;
type EntityArrayResponseType = HttpResponse<IUbicacio[]>;

@Injectable({ providedIn: 'root' })
export class UbicacioService {
  public resourceUrl = SERVER_API_URL + 'api/ubicacios';

  constructor(protected http: HttpClient) {}

  create(ubicacio: IUbicacio): Observable<EntityResponseType> {
    return this.http.post<IUbicacio>(this.resourceUrl, ubicacio, { observe: 'response' });
  }

  update(ubicacio: IUbicacio): Observable<EntityResponseType> {
    return this.http.put<IUbicacio>(this.resourceUrl, ubicacio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUbicacio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUbicacio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
