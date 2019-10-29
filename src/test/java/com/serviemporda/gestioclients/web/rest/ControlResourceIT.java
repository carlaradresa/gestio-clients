package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Control;
import com.serviemporda.gestioclients.repository.ControlRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.serviemporda.gestioclients.web.rest.TestUtil.sameInstant;
import static com.serviemporda.gestioclients.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ControlResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
public class ControlResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final LocalDate DEFAULT_SETMANA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SETMANA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CAUSA = "AAAAAAAAAA";
    private static final String UPDATED_CAUSA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_REVISIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_REVISIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMENTARIS = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIS = "BBBBBBBBBB";

    @Autowired
    private ControlRepository controlRepository;

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

    private MockMvc restControlMockMvc;

    private Control control;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ControlResource controlResource = new ControlResource(controlRepository);
        this.restControlMockMvc = MockMvcBuilders.standaloneSetup(controlResource)
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
    public static Control createEntity(EntityManager em) {
        Control control = new Control()
            .numero(DEFAULT_NUMERO)
            .setmana(DEFAULT_SETMANA)
            .causa(DEFAULT_CAUSA)
            .dataRevisio(DEFAULT_DATA_REVISIO)
            .comentaris(DEFAULT_COMENTARIS);
        return control;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Control createUpdatedEntity(EntityManager em) {
        Control control = new Control()
            .numero(UPDATED_NUMERO)
            .setmana(UPDATED_SETMANA)
            .causa(UPDATED_CAUSA)
            .dataRevisio(UPDATED_DATA_REVISIO)
            .comentaris(UPDATED_COMENTARIS);
        return control;
    }

    @BeforeEach
    public void initTest() {
        control = createEntity(em);
    }

    @Test
    @Transactional
    public void createControl() throws Exception {
        int databaseSizeBeforeCreate = controlRepository.findAll().size();

        // Create the Control
        restControlMockMvc.perform(post("/api/controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(control)))
            .andExpect(status().isCreated());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeCreate + 1);
        Control testControl = controlList.get(controlList.size() - 1);
        assertThat(testControl.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testControl.getSetmana()).isEqualTo(DEFAULT_SETMANA);
        assertThat(testControl.getCausa()).isEqualTo(DEFAULT_CAUSA);
        assertThat(testControl.getDataRevisio()).isEqualTo(DEFAULT_DATA_REVISIO);
        assertThat(testControl.getComentaris()).isEqualTo(DEFAULT_COMENTARIS);
    }

    @Test
    @Transactional
    public void createControlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controlRepository.findAll().size();

        // Create the Control with an existing ID
        control.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlMockMvc.perform(post("/api/controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(control)))
            .andExpect(status().isBadRequest());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllControls() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        // Get all the controlList
        restControlMockMvc.perform(get("/api/controls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(control.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].setmana").value(hasItem(DEFAULT_SETMANA.toString())))
            .andExpect(jsonPath("$.[*].causa").value(hasItem(DEFAULT_CAUSA)))
            .andExpect(jsonPath("$.[*].dataRevisio").value(hasItem(sameInstant(DEFAULT_DATA_REVISIO))))
            .andExpect(jsonPath("$.[*].comentaris").value(hasItem(DEFAULT_COMENTARIS)));
    }
    
    @Test
    @Transactional
    public void getControl() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        // Get the control
        restControlMockMvc.perform(get("/api/controls/{id}", control.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(control.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.setmana").value(DEFAULT_SETMANA.toString()))
            .andExpect(jsonPath("$.causa").value(DEFAULT_CAUSA))
            .andExpect(jsonPath("$.dataRevisio").value(sameInstant(DEFAULT_DATA_REVISIO)))
            .andExpect(jsonPath("$.comentaris").value(DEFAULT_COMENTARIS));
    }

    @Test
    @Transactional
    public void getNonExistingControl() throws Exception {
        // Get the control
        restControlMockMvc.perform(get("/api/controls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControl() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        int databaseSizeBeforeUpdate = controlRepository.findAll().size();

        // Update the control
        Control updatedControl = controlRepository.findById(control.getId()).get();
        // Disconnect from session so that the updates on updatedControl are not directly saved in db
        em.detach(updatedControl);
        updatedControl
            .numero(UPDATED_NUMERO)
            .setmana(UPDATED_SETMANA)
            .causa(UPDATED_CAUSA)
            .dataRevisio(UPDATED_DATA_REVISIO)
            .comentaris(UPDATED_COMENTARIS);

        restControlMockMvc.perform(put("/api/controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedControl)))
            .andExpect(status().isOk());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
        Control testControl = controlList.get(controlList.size() - 1);
        assertThat(testControl.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testControl.getSetmana()).isEqualTo(UPDATED_SETMANA);
        assertThat(testControl.getCausa()).isEqualTo(UPDATED_CAUSA);
        assertThat(testControl.getDataRevisio()).isEqualTo(UPDATED_DATA_REVISIO);
        assertThat(testControl.getComentaris()).isEqualTo(UPDATED_COMENTARIS);
    }

    @Test
    @Transactional
    public void updateNonExistingControl() throws Exception {
        int databaseSizeBeforeUpdate = controlRepository.findAll().size();

        // Create the Control

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlMockMvc.perform(put("/api/controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(control)))
            .andExpect(status().isBadRequest());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteControl() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        int databaseSizeBeforeDelete = controlRepository.findAll().size();

        // Delete the control
        restControlMockMvc.perform(delete("/api/controls/{id}", control.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Control.class);
        Control control1 = new Control();
        control1.setId(1L);
        Control control2 = new Control();
        control2.setId(control1.getId());
        assertThat(control1).isEqualTo(control2);
        control2.setId(2L);
        assertThat(control1).isNotEqualTo(control2);
        control1.setId(null);
        assertThat(control1).isNotEqualTo(control2);
    }
}
