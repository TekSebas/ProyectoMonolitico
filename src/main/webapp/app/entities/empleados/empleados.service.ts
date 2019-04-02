import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmpleados } from 'app/shared/model/empleados.model';

type EntityResponseType = HttpResponse<IEmpleados>;
type EntityArrayResponseType = HttpResponse<IEmpleados[]>;

@Injectable({ providedIn: 'root' })
export class EmpleadosService {
    public resourceUrl = SERVER_API_URL + 'api/empleados';

    constructor(private http: HttpClient) {}

    create(empleados: IEmpleados): Observable<EntityResponseType> {
        return this.http.post<IEmpleados>(this.resourceUrl, empleados, { observe: 'response' });
    }

    update(empleados: IEmpleados): Observable<EntityResponseType> {
        return this.http.put<IEmpleados>(this.resourceUrl, empleados, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEmpleados>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEmpleados[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
