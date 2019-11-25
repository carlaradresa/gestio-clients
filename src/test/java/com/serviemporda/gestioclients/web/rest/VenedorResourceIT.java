package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Venedor;
import com.serviemporda.gestioclients.repository.VenedorRepository;
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
 * Integration tests for the {@link VenedorResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
public class VenedorResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONS = "BBBBBBBBBB";

    @Autowired
    private VenedorRepository venedorRepository;

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

    private MockMvc restVenedorMockMvc;

    private Venedor venedor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VenedorResource venedorResource = new VenedorResource(venedorRepository);
        this.restVenedorMockMvc = MockMvcBuilders.standaloneSetup(venedorResource)
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
    public static Venedor createEntity(EntityManager em) {
        Venedor venedor = new Venedor()
            .numero(DEFAULT_NUMERO)
            .nom(DEFAULT_NOM)
            .telefon(DEFAULT_TELEFON)
            .email(DEFAULT_EMAIL)
            .observacions(DEFAULT_OBSERVACIONS);
        return venedor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venedor createUpdatedEntity(EntityManager em) {
        Venedor venedor = new Venedor()
            .numero(UPDATED_NUMERO)
            .nom(UPDATED_NOM)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .observacions(UPDATED_OBSERVACIONS);
        return venedor;
    }

    @BeforeEach
    public void initTest() {
        venedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenedor() throws Exception {
        int databaseSizeBeforeCreate = venedorRepository.findAll().size();

        // Create the Venedor
        restVenedorMockMvc.perform(post("/api/venedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venedor)))
            .andExpect(status().isCreated());

        // Validate the Venedor in the database
        List<Venedor> venedorList = venedorRepository.findAll();
        assertThat(venedorList).hasSize(databaseSizeBeforeCreate + 1);
        Venedor testVenedor = venedorList.get(venedorList.size() - 1);
        assertThat(testVenedor.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testVenedor.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVenedor.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testVenedor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVenedor.getObservacions()).isEqualTo(DEFAULT_OBSERVACIONS);
    }

    @Test
    @Transactional
    public void createVenedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = venedorRepository.findAll().size();

        // Create the Venedor with an existing ID
        venedor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVenedorMockMvc.perform(post("/api/venedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venedor)))
            .andExpect(status().isBadRequest());

        // Validate the Venedor in the database
        List<Venedor> venedorList = venedorRepository.findAll();
        assertThat(venedorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVenedors() throws Exception {
        // Initialize the database
        venedorRepository.saveAndFlush(venedor);

        // Get all the venedorList
        restVenedorMockMvc.perform(get("/api/venedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].observacions").value(hasItem(DEFAULT_OBSERVACIONS)));
    }
    
    @Test
    @Transactional
    public void getVenedor() throws Exception {
        // Initialize the database
        venedorRepository.saveAndFlush(venedor);

        // Get the venedor
        restVenedorMockMvc.perform(get("/api/venedors/{id}", venedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venedor.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.observacions").value(DEFAULT_OBSERVACIONS));
    }

    @Test
    @Transactional
    public void getNonExistingVenedor() throws Exception {
        // Get the venedor
        restVenedorMockMvc.perform(get("/api/venedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenedor() throws Exception {
        // Initialize the database
        venedorRepository.saveAndFlush(venedor);

        int databaseSizeBeforeUpdate = venedorRepository.findAll().size();

        // Update the venedor
        Venedor updatedVenedor = venedorRepository.findById(venedor.getId()).get();
        // Disconnect from session so that the updates on updatedVenedor are not directly saved in db
        em.detach(updatedVenedor);
        updatedVenedor
            .numero(UPDATED_NUMERO)
            .nom(UPDATED_NOM)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .observacions(UPDATED_OBSERVACIONS);

        restVenedorMockMvc.perform(put("/api/venedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVenedor)))
            .andExpect(status().isOk());

        // Validate the Venedor in the database
        List<Venedor> venedorList = venedorRepository.findAll();
        assertThat(venedorList).hasSize(databaseSizeBeforeUpdate);
        Venedor testVenedor = venedorList.get(venedorList.size() - 1);
        assertThat(testVenedor.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testVenedor.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVenedor.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testVenedor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVenedor.getObservacions()).isEqualTo(UPDATED_OBSERVACIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingVenedor() throws Exception {
        int databaseSizeBeforeUpdate = venedorRepository.findAll().size();

        // Create the Venedor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenedorMockMvc.perform(put("/api/venedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venedor)))
            .andExpect(status().isBadRequest());

        // Validate the Venedor in the database
        List<Venedor> venedorList = venedorRepository.findAll();
        assertThat(venedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenedor() throws Exception {
        // Initialize the database
        venedorRepository.saveAndFlush(venedor);

        int databaseSizeBeforeDelete = venedorRepository.findAll().size();

        // Delete the venedor
        restVenedorMockMvc.perform(delete("/api/venedors/{id}", venedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venedor> venedorList = venedorRepository.findAll();
        assertThat(venedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
