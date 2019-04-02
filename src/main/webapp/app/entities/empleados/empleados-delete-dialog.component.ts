import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpleados } from 'app/shared/model/empleados.model';
import { EmpleadosService } from './empleados.service';

@Component({
    selector: 'jhi-empleados-delete-dialog',
    templateUrl: './empleados-delete-dialog.component.html'
})
export class EmpleadosDeleteDialogComponent {
    empleados: IEmpleados;

    constructor(private empleadosService: EmpleadosService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.empleadosService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'empleadosListModification',
                content: 'Deleted an empleados'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-empleados-delete-popup',
    template: ''
})
export class EmpleadosDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ empleados }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmpleadosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.empleados = empleados;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
