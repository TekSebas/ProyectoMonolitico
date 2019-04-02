package com.empresa.app.service.impl;

import com.empresa.app.service.EmpleadosService;
import com.empresa.app.domain.Empleados;
import com.empresa.app.repository.EmpleadosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Empleados.
 */
@Service
@Transactional
public class EmpleadosServiceImpl implements EmpleadosService {

    private final Logger log = LoggerFactory.getLogger(EmpleadosServiceImpl.class);

    private final EmpleadosRepository empleadosRepository;

    public EmpleadosServiceImpl(EmpleadosRepository empleadosRepository) {
        this.empleadosRepository = empleadosRepository;
    }

    /**
     * Save a empleados.
     *
     * @param empleados the entity to save
     * @return the persisted entity
     */
    @Override
    public Empleados save(Empleados empleados) {
        log.debug("Request to save Empleados : {}", empleados);
        return empleadosRepository.save(empleados);
    }

    /**
     * Get all the empleados.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Empleados> findAll() {
        log.debug("Request to get all Empleados");
        return empleadosRepository.findAll();
    }


    /**
     * Get one empleados by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Empleados> findOne(Long id) {
        log.debug("Request to get Empleados : {}", id);
        return empleadosRepository.findById(id);
    }

    /**
     * Delete the empleados by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Empleados : {}", id);
        empleadosRepository.deleteById(id);
    }
}
