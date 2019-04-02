export interface IEmpleados {
    id?: number;
    nombre?: string;
    apellido?: string;
    dni?: string;
}

export class Empleados implements IEmpleados {
    constructor(public id?: number, public nombre?: string, public apellido?: string, public dni?: string) {}
}
