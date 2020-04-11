package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Control;
import com.serviemporda.gestioclients.repository.ControlRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.serviemporda.gestioclients.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ControlResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)

@AutoConfigureMockMvc
@WithMockUser
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
    private EntityManager em;

    @Autowired
    private MockMvc restControlMockMvc;

    private Control control;

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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
