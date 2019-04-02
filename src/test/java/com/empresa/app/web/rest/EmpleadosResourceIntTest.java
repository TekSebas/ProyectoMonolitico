package com.empresa.app.web.rest;

import com.empresa.app.EmpresaEmpleadosApp;

import com.empresa.app.domain.Empleados;
import com.empresa.app.repository.EmpleadosRepository;
import com.empresa.app.service.EmpleadosService;
import com.empresa.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.empresa.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmpleadosResource REST controller.
 *
 * @see EmpleadosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmpresaEmpleadosApp.class)
public class EmpleadosResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @Autowired
    private EmpleadosService empleadosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmpleadosMockMvc;

    private Empleados empleados;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpleadosResource empleadosResource = new EmpleadosResource(empleadosService);
        this.restEmpleadosMockMvc = MockMvcBuilders.standaloneSetup(empleadosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empleados createEntity(EntityManager em) {
        Empleados empleados = new Empleados()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .dni(DEFAULT_DNI);
        return empleados;
    }

    @Before
    public void initTest() {
        empleados = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpleados() throws Exception {
        int databaseSizeBeforeCreate = empleadosRepository.findAll().size();

        // Create the Empleados
        restEmpleadosMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleados)))
            .andExpect(status().isCreated());

        // Validate the Empleados in the database
        List<Empleados> empleadosList = empleadosRepository.findAll();
        assertThat(empleadosList).hasSize(databaseSizeBeforeCreate + 1);
        Empleados testEmpleados = empleadosList.get(empleadosList.size() - 1);
        assertThat(testEmpleados.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEmpleados.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testEmpleados.getDni()).isEqualTo(DEFAULT_DNI);
    }

    @Test
    @Transactional
    public void createEmpleadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empleadosRepository.findAll().size();

        // Create the Empleados with an existing ID
        empleados.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpleadosMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleados)))
            .andExpect(status().isBadRequest());

        // Validate the Empleados in the database
        List<Empleados> empleadosList = empleadosRepository.findAll();
        assertThat(empleadosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmpleados() throws Exception {
        // Initialize the database
        empleadosRepository.saveAndFlush(empleados);

        // Get all the empleadosList
        restEmpleadosMockMvc.perform(get("/api/empleados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpleados() throws Exception {
        // Initialize the database
        empleadosRepository.saveAndFlush(empleados);

        // Get the empleados
        restEmpleadosMockMvc.perform(get("/api/empleados/{id}", empleados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empleados.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpleados() throws Exception {
        // Get the empleados
        restEmpleadosMockMvc.perform(get("/api/empleados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpleados() throws Exception {
        // Initialize the database
        empleadosService.save(empleados);

        int databaseSizeBeforeUpdate = empleadosRepository.findAll().size();

        // Update the empleados
        Empleados updatedEmpleados = empleadosRepository.findById(empleados.getId()).get();
        // Disconnect from session so that the updates on updatedEmpleados are not directly saved in db
        em.detach(updatedEmpleados);
        updatedEmpleados
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .dni(UPDATED_DNI);

        restEmpleadosMockMvc.perform(put("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpleados)))
            .andExpect(status().isOk());

        // Validate the Empleados in the database
        List<Empleados> empleadosList = empleadosRepository.findAll();
        assertThat(empleadosList).hasSize(databaseSizeBeforeUpdate);
        Empleados testEmpleados = empleadosList.get(empleadosList.size() - 1);
        assertThat(testEmpleados.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmpleados.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testEmpleados.getDni()).isEqualTo(UPDATED_DNI);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpleados() throws Exception {
        int databaseSizeBeforeUpdate = empleadosRepository.findAll().size();

        // Create the Empleados

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadosMockMvc.perform(put("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleados)))
            .andExpect(status().isBadRequest());

        // Validate the Empleados in the database
        List<Empleados> empleadosList = empleadosRepository.findAll();
        assertThat(empleadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpleados() throws Exception {
        // Initialize the database
        empleadosService.save(empleados);

        int databaseSizeBeforeDelete = empleadosRepository.findAll().size();

        // Get the empleados
        restEmpleadosMockMvc.perform(delete("/api/empleados/{id}", empleados.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Empleados> empleadosList = empleadosRepository.findAll();
        assertThat(empleadosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empleados.class);
        Empleados empleados1 = new Empleados();
        empleados1.setId(1L);
        Empleados empleados2 = new Empleados();
        empleados2.setId(empleados1.getId());
        assertThat(empleados1).isEqualTo(empleados2);
        empleados2.setId(2L);
        assertThat(empleados1).isNotEqualTo(empleados2);
        empleados1.setId(null);
        assertThat(empleados1).isNotEqualTo(empleados2);
    }
}
