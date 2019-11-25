package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Marcatge;
import com.serviemporda.gestioclients.repository.MarcatgeRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.serviemporda.gestioclients.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MarcatgeResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
public class MarcatgeResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Instant DEFAULT_HORA_ENTRADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_ENTRADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_SORTIDA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_SORTIDA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DESVIACIO = false;
    private static final Boolean UPDATED_DESVIACIO = true;

    @Autowired
    private MarcatgeRepository marcatgeRepository;

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

    private MockMvc restMarcatgeMockMvc;

    private Marcatge marcatge;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarcatgeResource marcatgeResource = new MarcatgeResource(marcatgeRepository);
        this.restMarcatgeMockMvc = MockMvcBuilders.standaloneSetup(marcatgeResource)
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
    public static Marcatge createEntity(EntityManager em) {
        Marcatge marcatge = new Marcatge()
            .numero(DEFAULT_NUMERO)
            .horaEntrada(DEFAULT_HORA_ENTRADA)
            .horaSortida(DEFAULT_HORA_SORTIDA)
            .desviacio(DEFAULT_DESVIACIO);
        return marcatge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marcatge createUpdatedEntity(EntityManager em) {
        Marcatge marcatge = new Marcatge()
            .numero(UPDATED_NUMERO)
            .horaEntrada(UPDATED_HORA_ENTRADA)
            .horaSortida(UPDATED_HORA_SORTIDA)
            .desviacio(UPDATED_DESVIACIO);
        return marcatge;
    }

    @BeforeEach
    public void initTest() {
        marcatge = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarcatge() throws Exception {
        int databaseSizeBeforeCreate = marcatgeRepository.findAll().size();

        // Create the Marcatge
        restMarcatgeMockMvc.perform(post("/api/marcatges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcatge)))
            .andExpect(status().isCreated());

        // Validate the Marcatge in the database
        List<Marcatge> marcatgeList = marcatgeRepository.findAll();
        assertThat(marcatgeList).hasSize(databaseSizeBeforeCreate + 1);
        Marcatge testMarcatge = marcatgeList.get(marcatgeList.size() - 1);
        assertThat(testMarcatge.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testMarcatge.getHoraEntrada()).isEqualTo(DEFAULT_HORA_ENTRADA);
        assertThat(testMarcatge.getHoraSortida()).isEqualTo(DEFAULT_HORA_SORTIDA);
        assertThat(testMarcatge.isDesviacio()).isEqualTo(DEFAULT_DESVIACIO);
    }

    @Test
    @Transactional
    public void createMarcatgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marcatgeRepository.findAll().size();

        // Create the Marcatge with an existing ID
        marcatge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarcatgeMockMvc.perform(post("/api/marcatges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcatge)))
            .andExpect(status().isBadRequest());

        // Validate the Marcatge in the database
        List<Marcatge> marcatgeList = marcatgeRepository.findAll();
        assertThat(marcatgeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMarcatges() throws Exception {
        // Initialize the database
        marcatgeRepository.saveAndFlush(marcatge);

        // Get all the marcatgeList
        restMarcatgeMockMvc.perform(get("/api/marcatges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marcatge.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].horaEntrada").value(hasItem(DEFAULT_HORA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].horaSortida").value(hasItem(DEFAULT_HORA_SORTIDA.toString())))
            .andExpect(jsonPath("$.[*].desviacio").value(hasItem(DEFAULT_DESVIACIO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMarcatge() throws Exception {
        // Initialize the database
        marcatgeRepository.saveAndFlush(marcatge);

        // Get the marcatge
        restMarcatgeMockMvc.perform(get("/api/marcatges/{id}", marcatge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marcatge.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.horaEntrada").value(DEFAULT_HORA_ENTRADA.toString()))
            .andExpect(jsonPath("$.horaSortida").value(DEFAULT_HORA_SORTIDA.toString()))
            .andExpect(jsonPath("$.desviacio").value(DEFAULT_DESVIACIO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMarcatge() throws Exception {
        // Get the marcatge
        restMarcatgeMockMvc.perform(get("/api/marcatges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarcatge() throws Exception {
        // Initialize the database
        marcatgeRepository.saveAndFlush(marcatge);

        int databaseSizeBeforeUpdate = marcatgeRepository.findAll().size();

        // Update the marcatge
        Marcatge updatedMarcatge = marcatgeRepository.findById(marcatge.getId()).get();
        // Disconnect from session so that the updates on updatedMarcatge are not directly saved in db
        em.detach(updatedMarcatge);
        updatedMarcatge
            .numero(UPDATED_NUMERO)
            .horaEntrada(UPDATED_HORA_ENTRADA)
            .horaSortida(UPDATED_HORA_SORTIDA)
            .desviacio(UPDATED_DESVIACIO);

        restMarcatgeMockMvc.perform(put("/api/marcatges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarcatge)))
            .andExpect(status().isOk());

        // Validate the Marcatge in the database
        List<Marcatge> marcatgeList = marcatgeRepository.findAll();
        assertThat(marcatgeList).hasSize(databaseSizeBeforeUpdate);
        Marcatge testMarcatge = marcatgeList.get(marcatgeList.size() - 1);
        assertThat(testMarcatge.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testMarcatge.getHoraEntrada()).isEqualTo(UPDATED_HORA_ENTRADA);
        assertThat(testMarcatge.getHoraSortida()).isEqualTo(UPDATED_HORA_SORTIDA);
        assertThat(testMarcatge.isDesviacio()).isEqualTo(UPDATED_DESVIACIO);
    }

    @Test
    @Transactional
    public void updateNonExistingMarcatge() throws Exception {
        int databaseSizeBeforeUpdate = marcatgeRepository.findAll().size();

        // Create the Marcatge

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarcatgeMockMvc.perform(put("/api/marcatges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcatge)))
            .andExpect(status().isBadRequest());

        // Validate the Marcatge in the database
        List<Marcatge> marcatgeList = marcatgeRepository.findAll();
        assertThat(marcatgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMarcatge() throws Exception {
        // Initialize the database
        marcatgeRepository.saveAndFlush(marcatge);

        int databaseSizeBeforeDelete = marcatgeRepository.findAll().size();

        // Delete the marcatge
        restMarcatgeMockMvc.perform(delete("/api/marcatges/{id}", marcatge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Marcatge> marcatgeList = marcatgeRepository.findAll();
        assertThat(marcatgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
