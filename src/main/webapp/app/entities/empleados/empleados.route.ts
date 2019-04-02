import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Empleados } from 'app/shared/model/empleados.model';
import { EmpleadosService } from './empleados.service';
import { EmpleadosComponent } from './empleados.component';
import { EmpleadosDetailComponent } from './empleados-detail.component';
import { EmpleadosUpdateComponent } from './empleados-update.component';
import { EmpleadosDeletePopupComponent } from './empleados-delete-dialog.component';
import { IEmpleados } from 'app/shared/model/empleados.model';

@Injectable({ providedIn: 'root' })
export class EmpleadosResolve implements Resolve<IEmpleados> {
    constructor(private service: EmpleadosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Empleados> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Empleados>) => response.ok),
                map((empleados: HttpResponse<Empleados>) => empleados.body)
            );
        }
        return of(new Empleados());
    }
}

export const empleadosRoute: Routes = [
    {
        path: 'empleados',
        component: EmpleadosComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'empresaEmpleadosApp.empleados.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empleados/:id/view',
        component: EmpleadosDetailComponent,
        resolve: {
            empleados: EmpleadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'empresaEmpleadosApp.empleados.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empleados/new',
        component: EmpleadosUpdateComponent,
        resolve: {
            empleados: EmpleadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'empresaEmpleadosApp.empleados.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'empleados/:id/edit',
        component: EmpleadosUpdateComponent,
        resolve: {
            empleados: EmpleadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'empresaEmpleadosApp.empleados.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const empleadosPopupRoute: Routes = [
    {
        path: 'empleados/:id/delete',
        component: EmpleadosDeletePopupComponent,
        resolve: {
            empleados: EmpleadosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'empresaEmpleadosApp.empleados.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
