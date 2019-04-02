package com.empresa.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.empresa.app.domain.Empleados;
import com.empresa.app.service.EmpleadosService;
import com.empresa.app.web.rest.errors.BadRequestAlertException;
import com.empresa.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Empleados.
 */
@RestController
@RequestMapping("/api")
public class EmpleadosResource {

    private final Logger log = LoggerFactory.getLogger(EmpleadosResource.class);

    private static final String ENTITY_NAME = "empleados";

    private final EmpleadosService empleadosService;

    public EmpleadosResource(EmpleadosService empleadosService) {
        this.empleadosService = empleadosService;
    }

    /**
     * POST  /empleados : Create a new empleados.
     *
     * @param empleados the empleados to create
     * @return the ResponseEntity with status 201 (Created) and with body the new empleados, or with status 400 (Bad Request) if the empleados has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/empleados")
    @Timed
    public ResponseEntity<Empleados> createEmpleados(@RequestBody Empleados empleados) throws URISyntaxException {
        log.debug("REST request to save Empleados : {}", empleados);
        if (empleados.getId() != null) {
            throw new BadRequestAlertException("A new empleados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Empleados result = empleadosService.save(empleados);
        return ResponseEntity.created(new URI("/api/empleados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /empleados : Updates an existing empleados.
     *
     * @param empleados the empleados to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated empleados,
     * or with status 400 (Bad Request) if the empleados is not valid,
     * or with status 500 (Internal Server Error) if the empleados couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/empleados")
    @Timed
    public ResponseEntity<Empleados> updateEmpleados(@RequestBody Empleados empleados) throws URISyntaxException {
        log.debug("REST request to update Empleados : {}", empleados);
        if (empleados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Empleados result = empleadosService.save(empleados);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, empleados.getId().toString()))
            .body(result);
    }

    /**
     * GET  /empleados : get all the empleados.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of empleados in body
     */
    @GetMapping("/empleados")
    @Timed
    public List<Empleados> getAllEmpleados() {
        log.debug("REST request to get all Empleados");
        return empleadosService.findAll();
    }

    /**
     * GET  /empleados/:id : get the "id" empleados.
     *
     * @param id the id of the empleados to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the empleados, or with status 404 (Not Found)
     */
    @GetMapping("/empleados/{id}")
    @Timed
    public ResponseEntity<Empleados> getEmpleados(@PathVariable Long id) {
        log.debug("REST request to get Empleados : {}", id);
        Optional<Empleados> empleados = empleadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empleados);
    }

    /**
     * DELETE  /empleados/:id : delete the "id" empleados.
     *
     * @param id the id of the empleados to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/empleados/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmpleados(@PathVariable Long id) {
        log.debug("REST request to delete Empleados : {}", id);
        empleadosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
