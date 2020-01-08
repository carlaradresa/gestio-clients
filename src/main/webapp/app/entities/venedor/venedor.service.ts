import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVenedor } from 'app/shared/model/venedor.model';

type EntityResponseType = HttpResponse<IVenedor>;
type EntityArrayResponseType = HttpResponse<IVenedor[]>;

@Injectable({ providedIn: 'root' })
export class VenedorService {
  public resourceUrl = SERVER_API_URL + 'api/venedors';

  constructor(protected http: HttpClient) {}

  create(venedor: IVenedor): Observable<EntityResponseType> {
    return this.http.post<IVenedor>(this.resourceUrl, venedor, { observe: 'response' });
  }

  update(venedor: IVenedor): Observable<EntityResponseType> {
    return this.http.put<IVenedor>(this.resourceUrl, venedor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVenedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVenedor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
