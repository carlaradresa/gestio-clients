import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPeriodicitatConfigurable } from 'app/shared/model/periodicitat-configurable.model';

type EntityResponseType = HttpResponse<IPeriodicitatConfigurable>;
type EntityArrayResponseType = HttpResponse<IPeriodicitatConfigurable[]>;

@Injectable({ providedIn: 'root' })
export class PeriodicitatConfigurableService {
  public resourceUrl = SERVER_API_URL + 'api/periodicitat-configurables';

  constructor(protected http: HttpClient) {}

  create(periodicitatConfigurable: IPeriodicitatConfigurable): Observable<EntityResponseType> {
    return this.http.post<IPeriodicitatConfigurable>(this.resourceUrl, periodicitatConfigurable, { observe: 'response' });
  }

  update(periodicitatConfigurable: IPeriodicitatConfigurable): Observable<EntityResponseType> {
    return this.http.put<IPeriodicitatConfigurable>(this.resourceUrl, periodicitatConfigurable, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPeriodicitatConfigurable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPeriodicitatConfigurable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
