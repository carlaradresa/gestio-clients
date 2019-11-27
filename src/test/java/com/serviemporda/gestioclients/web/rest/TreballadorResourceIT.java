package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Treballador;
import com.serviemporda.gestioclients.repository.TreballadorRepository;
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
import java.time.Duration;
import java.util.List;

import static com.serviemporda.gestioclients.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TreballadorResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
public class TreballadorResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Duration DEFAULT_CARREGA_HORES = Duration.ofHours(6);
    private static final Duration UPDATED_CARREGA_HORES = Duration.ofHours(12);

    private static final Boolean DEFAULT_ACTIU = false;
    private static final Boolean UPDATED_ACTIU = true;

    private static final Boolean DEFAULT_CONTROL_QUALITAT = false;
    private static final Boolean UPDATED_CONTROL_QUALITAT = true;

    @Autowired
    private TreballadorRepository treballadorRepository;

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

    private MockMvc restTreballadorMockMvc;

    private Treballador treballador;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreballadorResource treballadorResource = new TreballadorResource(treballadorRepository);
        this.restTreballadorMockMvc = MockMvcBuilders.standaloneSetup(treballadorResource)
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
    public static Treballador createEntity(EntityManager em) {
        Treballador treballador = new Treballador()
            .nom(DEFAULT_NOM)
            .carregaHores(DEFAULT_CARREGA_HORES)
            .actiu(DEFAULT_ACTIU)
            .controlQualitat(DEFAULT_CONTROL_QUALITAT);
        return treballador;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treballador createUpdatedEntity(EntityManager em) {
        Treballador treballador = new Treballador()
            .nom(UPDATED_NOM)
            .carregaHores(UPDATED_CARREGA_HORES)
            .actiu(UPDATED_ACTIU)
            .controlQualitat(UPDATED_CONTROL_QUALITAT);
        return treballador;
    }

    @BeforeEach
    public void initTest() {
        treballador = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreballador() throws Exception {
        int databaseSizeBeforeCreate = treballadorRepository.findAll().size();

        // Create the Treballador
        restTreballadorMockMvc.perform(post("/api/treballadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treballador)))
            .andExpect(status().isCreated());

        // Validate the Treballador in the database
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeCreate + 1);
        Treballador testTreballador = treballadorList.get(treballadorList.size() - 1);
        assertThat(testTreballador.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTreballador.getCarregaHores()).isEqualTo(DEFAULT_CARREGA_HORES);
        assertThat(testTreballador.isActiu()).isEqualTo(DEFAULT_ACTIU);
        assertThat(testTreballador.isControlQualitat()).isEqualTo(DEFAULT_CONTROL_QUALITAT);
    }

    @Test
    @Transactional
    public void createTreballadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treballadorRepository.findAll().size();

        // Create the Treballador with an existing ID
        treballador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreballadorMockMvc.perform(post("/api/treballadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treballador)))
            .andExpect(status().isBadRequest());

        // Validate the Treballador in the database
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTreballadors() throws Exception {
        // Initialize the database
        treballadorRepository.saveAndFlush(treballador);

        // Get all the treballadorList
        restTreballadorMockMvc.perform(get("/api/treballadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treballador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].carregaHores").value(hasItem(DEFAULT_CARREGA_HORES.toString())))
            .andExpect(jsonPath("$.[*].actiu").value(hasItem(DEFAULT_ACTIU.booleanValue())))
            .andExpect(jsonPath("$.[*].controlQualitat").value(hasItem(DEFAULT_CONTROL_QUALITAT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTreballador() throws Exception {
        // Initialize the database
        treballadorRepository.saveAndFlush(treballador);

        // Get the treballador
        restTreballadorMockMvc.perform(get("/api/treballadors/{id}", treballador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treballador.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.carregaHores").value(DEFAULT_CARREGA_HORES.toString()))
            .andExpect(jsonPath("$.actiu").value(DEFAULT_ACTIU.booleanValue()))
            .andExpect(jsonPath("$.controlQualitat").value(DEFAULT_CONTROL_QUALITAT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTreballador() throws Exception {
        // Get the treballador
        restTreballadorMockMvc.perform(get("/api/treballadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreballador() throws Exception {
        // Initialize the database
        treballadorRepository.saveAndFlush(treballador);

        int databaseSizeBeforeUpdate = treballadorRepository.findAll().size();

        // Update the treballador
        Treballador updatedTreballador = treballadorRepository.findById(treballador.getId()).get();
        // Disconnect from session so that the updates on updatedTreballador are not directly saved in db
        em.detach(updatedTreballador);
        updatedTreballador
            .nom(UPDATED_NOM)
            .carregaHores(UPDATED_CARREGA_HORES)
            .actiu(UPDATED_ACTIU)
            .controlQualitat(UPDATED_CONTROL_QUALITAT);

        restTreballadorMockMvc.perform(put("/api/treballadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreballador)))
            .andExpect(status().isOk());

        // Validate the Treballador in the database
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeUpdate);
        Treballador testTreballador = treballadorList.get(treballadorList.size() - 1);
        assertThat(testTreballador.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTreballador.getCarregaHores()).isEqualTo(UPDATED_CARREGA_HORES);
        assertThat(testTreballador.isActiu()).isEqualTo(UPDATED_ACTIU);
        assertThat(testTreballador.isControlQualitat()).isEqualTo(UPDATED_CONTROL_QUALITAT);
    }

    @Test
    @Transactional
    public void updateNonExistingTreballador() throws Exception {
        int databaseSizeBeforeUpdate = treballadorRepository.findAll().size();

        // Create the Treballador

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreballadorMockMvc.perform(put("/api/treballadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treballador)))
            .andExpect(status().isBadRequest());

        // Validate the Treballador in the database
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreballador() throws Exception {
        // Initialize the database
        treballadorRepository.saveAndFlush(treballador);

        int databaseSizeBeforeDelete = treballadorRepository.findAll().size();

        // Delete the treballador
        restTreballadorMockMvc.perform(delete("/api/treballadors/{id}", treballador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
