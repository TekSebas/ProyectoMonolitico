import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEmpleados } from 'app/shared/model/empleados.model';
import { EmpleadosService } from './empleados.service';

@Component({
    selector: 'jhi-empleados-update',
    templateUrl: './empleados-update.component.html'
})
export class EmpleadosUpdateComponent implements OnInit {
    empleados: IEmpleados;
    isSaving: boolean;

    constructor(private empleadosService: EmpleadosService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ empleados }) => {
            this.empleados = empleados;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.empleados.id !== undefined) {
            this.subscribeToSaveResponse(this.empleadosService.update(this.empleados));
        } else {
            this.subscribeToSaveResponse(this.empleadosService.create(this.empleados));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleados>>) {
        result.subscribe((res: HttpResponse<IEmpleados>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
