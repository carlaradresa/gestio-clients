import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPeriodicitatSetmanal } from 'app/shared/model/periodicitat-setmanal.model';

type EntityResponseType = HttpResponse<IPeriodicitatSetmanal>;
type EntityArrayResponseType = HttpResponse<IPeriodicitatSetmanal[]>;

@Injectable({ providedIn: 'root' })
export class PeriodicitatSetmanalService {
  public resourceUrl = SERVER_API_URL + 'api/periodicitat-setmanals';

  constructor(protected http: HttpClient) {}

  create(periodicitatSetmanal: IPeriodicitatSetmanal): Observable<EntityResponseType> {
    return this.http.post<IPeriodicitatSetmanal>(this.resourceUrl, periodicitatSetmanal, { observe: 'response' });
  }

  update(periodicitatSetmanal: IPeriodicitatSetmanal): Observable<EntityResponseType> {
    return this.http.put<IPeriodicitatSetmanal>(this.resourceUrl, periodicitatSetmanal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPeriodicitatSetmanal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPeriodicitatSetmanal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
