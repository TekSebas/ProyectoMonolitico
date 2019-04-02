import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmpresaEmpleadosSharedModule } from 'app/shared';
import {
    EmpleadosComponent,
    EmpleadosDetailComponent,
    EmpleadosUpdateComponent,
    EmpleadosDeletePopupComponent,
    EmpleadosDeleteDialogComponent,
    empleadosRoute,
    empleadosPopupRoute
} from './';

const ENTITY_STATES = [...empleadosRoute, ...empleadosPopupRoute];

@NgModule({
    imports: [EmpresaEmpleadosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmpleadosComponent,
        EmpleadosDetailComponent,
        EmpleadosUpdateComponent,
        EmpleadosDeleteDialogComponent,
        EmpleadosDeletePopupComponent
    ],
    entryComponents: [EmpleadosComponent, EmpleadosUpdateComponent, EmpleadosDeleteDialogComponent, EmpleadosDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmpresaEmpleadosEmpleadosModule {}
