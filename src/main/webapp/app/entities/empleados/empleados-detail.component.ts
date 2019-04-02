import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpleados } from 'app/shared/model/empleados.model';

@Component({
    selector: 'jhi-empleados-detail',
    templateUrl: './empleados-detail.component.html'
})
export class EmpleadosDetailComponent implements OnInit {
    empleados: IEmpleados;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ empleados }) => {
            this.empleados = empleados;
        });
    }

    previousState() {
        window.history.back();
    }
}
