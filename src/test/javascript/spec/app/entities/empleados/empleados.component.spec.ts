/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EmpresaEmpleadosTestModule } from '../../../test.module';
import { EmpleadosComponent } from 'app/entities/empleados/empleados.component';
import { EmpleadosService } from 'app/entities/empleados/empleados.service';
import { Empleados } from 'app/shared/model/empleados.model';

describe('Component Tests', () => {
    describe('Empleados Management Component', () => {
        let comp: EmpleadosComponent;
        let fixture: ComponentFixture<EmpleadosComponent>;
        let service: EmpleadosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EmpresaEmpleadosTestModule],
                declarations: [EmpleadosComponent],
                providers: []
            })
                .overrideTemplate(EmpleadosComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmpleadosComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmpleadosService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Empleados(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.empleados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
