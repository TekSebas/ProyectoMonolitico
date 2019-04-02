import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmpleados } from 'app/shared/model/empleados.model';
import { Principal } from 'app/core';
import { EmpleadosService } from './empleados.service';

@Component({
    selector: 'jhi-empleados',
    templateUrl: './empleados.component.html'
})
export class EmpleadosComponent implements OnInit, OnDestroy {
    empleados: IEmpleados[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private empleadosService: EmpleadosService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.empleadosService.query().subscribe(
            (res: HttpResponse<IEmpleados[]>) => {
                this.empleados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmpleados();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmpleados) {
        return item.id;
    }

    registerChangeInEmpleados() {
        this.eventSubscriber = this.eventManager.subscribe('empleadosListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
