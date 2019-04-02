package com.empresa.app.service;

import com.empresa.app.domain.Empleados;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Empleados.
 */
public interface EmpleadosService {

    /**
     * Save a empleados.
     *
     * @param empleados the entity to save
     * @return the persisted entity
     */
    Empleados save(Empleados empleados);

    /**
     * Get all the empleados.
     *
     * @return the list of entities
     */
    List<Empleados> findAll();


    /**
     * Get the "id" empleados.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Empleados> findOne(Long id);

    /**
     * Delete the "id" empleados.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
