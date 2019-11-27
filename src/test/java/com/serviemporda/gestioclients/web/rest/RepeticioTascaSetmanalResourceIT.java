package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.RepeticioTascaSetmanal;
import com.serviemporda.gestioclients.repository.RepeticioTascaSetmanalRepository;
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

import com.serviemporda.gestioclients.domain.enumeration.Dia;
/**
 * Integration tests for the {@link RepeticioTascaSetmanalResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
public class RepeticioTascaSetmanalResourceIT {

    private static final Dia DEFAULT_DIA = Dia.DILLUNS;
    private static final Dia UPDATED_DIA = Dia.DIMARTS;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private RepeticioTascaSetmanalRepository repeticioTascaSetmanalRepository;

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

    private MockMvc restRepeticioTascaSetmanalMockMvc;

    private RepeticioTascaSetmanal repeticioTascaSetmanal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RepeticioTascaSetmanalResource repeticioTascaSetmanalResource = new RepeticioTascaSetmanalResource(repeticioTascaSetmanalRepository);
        this.restRepeticioTascaSetmanalMockMvc = MockMvcBuilders.standaloneSetup(repeticioTascaSetmanalResource)
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
    public static RepeticioTascaSetmanal createEntity(EntityManager em) {
        RepeticioTascaSetmanal repeticioTascaSetmanal = new RepeticioTascaSetmanal()
            .dia(DEFAULT_DIA)
            .activo(DEFAULT_ACTIVO);
        return repeticioTascaSetmanal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepeticioTascaSetmanal createUpdatedEntity(EntityManager em) {
        RepeticioTascaSetmanal repeticioTascaSetmanal = new RepeticioTascaSetmanal()
            .dia(UPDATED_DIA)
            .activo(UPDATED_ACTIVO);
        return repeticioTascaSetmanal;
    }

    @BeforeEach
    public void initTest() {
        repeticioTascaSetmanal = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepeticioTascaSetmanal() throws Exception {
        int databaseSizeBeforeCreate = repeticioTascaSetmanalRepository.findAll().size();

        // Create the RepeticioTascaSetmanal
        restRepeticioTascaSetmanalMockMvc.perform(post("/api/repeticio-tasca-setmanals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeticioTascaSetmanal)))
            .andExpect(status().isCreated());

        // Validate the RepeticioTascaSetmanal in the database
        List<RepeticioTascaSetmanal> repeticioTascaSetmanalList = repeticioTascaSetmanalRepository.findAll();
        assertThat(repeticioTascaSetmanalList).hasSize(databaseSizeBeforeCreate + 1);
        RepeticioTascaSetmanal testRepeticioTascaSetmanal = repeticioTascaSetmanalList.get(repeticioTascaSetmanalList.size() - 1);
        assertThat(testRepeticioTascaSetmanal.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testRepeticioTascaSetmanal.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createRepeticioTascaSetmanalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repeticioTascaSetmanalRepository.findAll().size();

        // Create the RepeticioTascaSetmanal with an existing ID
        repeticioTascaSetmanal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepeticioTascaSetmanalMockMvc.perform(post("/api/repeticio-tasca-setmanals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeticioTascaSetmanal)))
            .andExpect(status().isBadRequest());

        // Validate the RepeticioTascaSetmanal in the database
        List<RepeticioTascaSetmanal> repeticioTascaSetmanalList = repeticioTascaSetmanalRepository.findAll();
        assertThat(repeticioTascaSetmanalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRepeticioTascaSetmanals() throws Exception {
        // Initialize the database
        repeticioTascaSetmanalRepository.saveAndFlush(repeticioTascaSetmanal);

        // Get all the repeticioTascaSetmanalList
        restRepeticioTascaSetmanalMockMvc.perform(get("/api/repeticio-tasca-setmanals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeticioTascaSetmanal.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRepeticioTascaSetmanal() throws Exception {
        // Initialize the database
        repeticioTascaSetmanalRepository.saveAndFlush(repeticioTascaSetmanal);

        // Get the repeticioTascaSetmanal
        restRepeticioTascaSetmanalMockMvc.perform(get("/api/repeticio-tasca-setmanals/{id}", repeticioTascaSetmanal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(repeticioTascaSetmanal.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRepeticioTascaSetmanal() throws Exception {
        // Get the repeticioTascaSetmanal
        restRepeticioTascaSetmanalMockMvc.perform(get("/api/repeticio-tasca-setmanals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepeticioTascaSetmanal() throws Exception {
        // Initialize the database
        repeticioTascaSetmanalRepository.saveAndFlush(repeticioTascaSetmanal);

        int databaseSizeBeforeUpdate = repeticioTascaSetmanalRepository.findAll().size();

        // Update the repeticioTascaSetmanal
        RepeticioTascaSetmanal updatedRepeticioTascaSetmanal = repeticioTascaSetmanalRepository.findById(repeticioTascaSetmanal.getId()).get();
        // Disconnect from session so that the updates on updatedRepeticioTascaSetmanal are not directly saved in db
        em.detach(updatedRepeticioTascaSetmanal);
        updatedRepeticioTascaSetmanal
            .dia(UPDATED_DIA)
            .activo(UPDATED_ACTIVO);

        restRepeticioTascaSetmanalMockMvc.perform(put("/api/repeticio-tasca-setmanals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRepeticioTascaSetmanal)))
            .andExpect(status().isOk());

        // Validate the RepeticioTascaSetmanal in the database
        List<RepeticioTascaSetmanal> repeticioTascaSetmanalList = repeticioTascaSetmanalRepository.findAll();
        assertThat(repeticioTascaSetmanalList).hasSize(databaseSizeBeforeUpdate);
        RepeticioTascaSetmanal testRepeticioTascaSetmanal = repeticioTascaSetmanalList.get(repeticioTascaSetmanalList.size() - 1);
        assertThat(testRepeticioTascaSetmanal.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testRepeticioTascaSetmanal.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingRepeticioTascaSetmanal() throws Exception {
        int databaseSizeBeforeUpdate = repeticioTascaSetmanalRepository.findAll().size();

        // Create the RepeticioTascaSetmanal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepeticioTascaSetmanalMockMvc.perform(put("/api/repeticio-tasca-setmanals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeticioTascaSetmanal)))
            .andExpect(status().isBadRequest());

        // Validate the RepeticioTascaSetmanal in the database
        List<RepeticioTascaSetmanal> repeticioTascaSetmanalList = repeticioTascaSetmanalRepository.findAll();
        assertThat(repeticioTascaSetmanalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRepeticioTascaSetmanal() throws Exception {
        // Initialize the database
        repeticioTascaSetmanalRepository.saveAndFlush(repeticioTascaSetmanal);

        int databaseSizeBeforeDelete = repeticioTascaSetmanalRepository.findAll().size();

        // Delete the repeticioTascaSetmanal
        restRepeticioTascaSetmanalMockMvc.perform(delete("/api/repeticio-tasca-setmanals/{id}", repeticioTascaSetmanal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RepeticioTascaSetmanal> repeticioTascaSetmanalList = repeticioTascaSetmanalRepository.findAll();
        assertThat(repeticioTascaSetmanalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
