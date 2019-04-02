/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmpresaEmpleadosTestModule } from '../../../test.module';
import { EmpleadosDetailComponent } from 'app/entities/empleados/empleados-detail.component';
import { Empleados } from 'app/shared/model/empleados.model';

describe('Component Tests', () => {
    describe('Empleados Management Detail Component', () => {
        let comp: EmpleadosDetailComponent;
        let fixture: ComponentFixture<EmpleadosDetailComponent>;
        const route = ({ data: of({ empleados: new Empleados(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EmpresaEmpleadosTestModule],
                declarations: [EmpleadosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EmpleadosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmpleadosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.empleados).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
