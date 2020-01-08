import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITreballador } from 'app/shared/model/treballador.model';

type EntityResponseType = HttpResponse<ITreballador>;
type EntityArrayResponseType = HttpResponse<ITreballador[]>;

@Injectable({ providedIn: 'root' })
export class TreballadorService {
  public resourceUrl = SERVER_API_URL + 'api/treballadors';

  constructor(protected http: HttpClient) {}

  create(treballador: ITreballador): Observable<EntityResponseType> {
    return this.http.post<ITreballador>(this.resourceUrl, treballador, { observe: 'response' });
  }

  update(treballador: ITreballador): Observable<EntityResponseType> {
    return this.http.put<ITreballador>(this.resourceUrl, treballador, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITreballador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITreballador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
