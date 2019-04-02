/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EmpresaEmpleadosTestModule } from '../../../test.module';
import { EmpleadosDeleteDialogComponent } from 'app/entities/empleados/empleados-delete-dialog.component';
import { EmpleadosService } from 'app/entities/empleados/empleados.service';

describe('Component Tests', () => {
    describe('Empleados Management Delete Component', () => {
        let comp: EmpleadosDeleteDialogComponent;
        let fixture: ComponentFixture<EmpleadosDeleteDialogComponent>;
        let service: EmpleadosService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EmpresaEmpleadosTestModule],
                declarations: [EmpleadosDeleteDialogComponent]
            })
                .overrideTemplate(EmpleadosDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmpleadosDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmpleadosService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
