package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Ubicacio;
import com.serviemporda.gestioclients.repository.UbicacioRepository;
import com.serviemporda.gestioclients.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.serviemporda.gestioclients.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UbicacioResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
public class UbicacioResourceIT {

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;

    private static final String DEFAULT_UBICACIO = "AAAAAAAAAA";
    private static final String UPDATED_UBICACIO = "BBBBBBBBBB";

    @Autowired
    private UbicacioRepository ubicacioRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restUbicacioMockMvc;

    private Ubicacio ubicacio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UbicacioResource ubicacioResource = new UbicacioResource(ubicacioRepository);
        this.restUbicacioMockMvc = MockMvcBuilders.standaloneSetup(ubicacioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ubicacio createEntity(EntityManager em) {
        Ubicacio ubicacio = new Ubicacio()
            .longitud(DEFAULT_LONGITUD)
            .latitud(DEFAULT_LATITUD)
            .ubicacio(DEFAULT_UBICACIO);
        return ubicacio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ubicacio createUpdatedEntity(EntityManager em) {
        Ubicacio ubicacio = new Ubicacio()
            .longitud(UPDATED_LONGITUD)
            .latitud(UPDATED_LATITUD)
            .ubicacio(UPDATED_UBICACIO);
        return ubicacio;
    }

    @BeforeEach
    public void initTest() {
        ubicacio = createEntity(em);
    }

    @Test
    @Transactional
    public void createUbicacio() throws Exception {
        int databaseSizeBeforeCreate = ubicacioRepository.findAll().size();

        // Create the Ubicacio
        restUbicacioMockMvc.perform(post("/api/ubicacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubicacio)))
            .andExpect(status().isCreated());

        // Validate the Ubicacio in the database
        List<Ubicacio> ubicacioList = ubicacioRepository.findAll();
        assertThat(ubicacioList).hasSize(databaseSizeBeforeCreate + 1);
        Ubicacio testUbicacio = ubicacioList.get(ubicacioList.size() - 1);
        assertThat(testUbicacio.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testUbicacio.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testUbicacio.getUbicacio()).isEqualTo(DEFAULT_UBICACIO);
    }

    @Test
    @Transactional
    public void createUbicacioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ubicacioRepository.findAll().size();

        // Create the Ubicacio with an existing ID
        ubicacio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUbicacioMockMvc.perform(post("/api/ubicacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubicacio)))
            .andExpect(status().isBadRequest());

        // Validate the Ubicacio in the database
        List<Ubicacio> ubicacioList = ubicacioRepository.findAll();
        assertThat(ubicacioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUbicacios() throws Exception {
        // Initialize the database
        ubicacioRepository.saveAndFlush(ubicacio);

        // Get all the ubicacioList
        restUbicacioMockMvc.perform(get("/api/ubicacios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ubicacio.getId().intValue())))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())))
            .andExpect(jsonPath("$.[*].ubicacio").value(hasItem(DEFAULT_UBICACIO)));
    }
    
    @Test
    @Transactional
    public void getUbicacio() throws Exception {
        // Initialize the database
        ubicacioRepository.saveAndFlush(ubicacio);

        // Get the ubicacio
        restUbicacioMockMvc.perform(get("/api/ubicacios/{id}", ubicacio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ubicacio.getId().intValue()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.doubleValue()))
            .andExpect(jsonPath("$.ubicacio").value(DEFAULT_UBICACIO));
    }

    @Test
    @Transactional
    public void getNonExistingUbicacio() throws Exception {
        // Get the ubicacio
        restUbicacioMockMvc.perform(get("/api/ubicacios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUbicacio() throws Exception {
        // Initialize the database
        ubicacioRepository.saveAndFlush(ubicacio);

        int databaseSizeBeforeUpdate = ubicacioRepository.findAll().size();

        // Update the ubicacio
        Ubicacio updatedUbicacio = ubicacioRepository.findById(ubicacio.getId()).get();
        // Disconnect from session so that the updates on updatedUbicacio are not directly saved in db
        em.detach(updatedUbicacio);
        updatedUbicacio
            .longitud(UPDATED_LONGITUD)
            .latitud(UPDATED_LATITUD)
            .ubicacio(UPDATED_UBICACIO);

        restUbicacioMockMvc.perform(put("/api/ubicacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUbicacio)))
            .andExpect(status().isOk());

        // Validate the Ubicacio in the database
        List<Ubicacio> ubicacioList = ubicacioRepository.findAll();
        assertThat(ubicacioList).hasSize(databaseSizeBeforeUpdate);
        Ubicacio testUbicacio = ubicacioList.get(ubicacioList.size() - 1);
        assertThat(testUbicacio.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testUbicacio.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testUbicacio.getUbicacio()).isEqualTo(UPDATED_UBICACIO);
    }

    @Test
    @Transactional
    public void updateNonExistingUbicacio() throws Exception {
        int databaseSizeBeforeUpdate = ubicacioRepository.findAll().size();

        // Create the Ubicacio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUbicacioMockMvc.perform(put("/api/ubicacios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ubicacio)))
            .andExpect(status().isBadRequest());

        // Validate the Ubicacio in the database
        List<Ubicacio> ubicacioList = ubicacioRepository.findAll();
        assertThat(ubicacioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUbicacio() throws Exception {
        // Initialize the database
        ubicacioRepository.saveAndFlush(ubicacio);

        int databaseSizeBeforeDelete = ubicacioRepository.findAll().size();

        // Delete the ubicacio
        restUbicacioMockMvc.perform(delete("/api/ubicacios/{id}", ubicacio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ubicacio> ubicacioList = ubicacioRepository.findAll();
        assertThat(ubicacioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
