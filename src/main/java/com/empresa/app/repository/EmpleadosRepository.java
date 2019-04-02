package com.empresa.app.repository;

import com.empresa.app.domain.Empleados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Empleados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadosRepository extends JpaRepository<Empleados, Long> {

}
