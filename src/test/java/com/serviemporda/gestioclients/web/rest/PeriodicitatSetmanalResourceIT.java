package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.PeriodicitatSetmanal;
import com.serviemporda.gestioclients.repository.PeriodicitatSetmanalRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.serviemporda.gestioclients.domain.enumeration.Dia;
/**
 * Integration tests for the {@link PeriodicitatSetmanalResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PeriodicitatSetmanalResourceIT {

    private static final Dia DEFAULT_DIA_SETMANA = Dia.DILLUNS;
    private static final Dia UPDATED_DIA_SETMANA = Dia.DIMARTS;

    @Autowired
    private PeriodicitatSetmanalRepository periodicitatSetmanalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodicitatSetmanalMockMvc;

    private PeriodicitatSetmanal periodicitatSetmanal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodicitatSetmanal createEntity(EntityManager em) {
        PeriodicitatSetmanal periodicitatSetmanal = new PeriodicitatSetmanal()
            .diaSetmana(DEFAULT_DIA_SETMANA);
        return periodicitatSetmanal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodicitatSetmanal createUpdatedEntity(EntityManager em) {
        PeriodicitatSetmanal periodicitatSetmanal = new PeriodicitatSetmanal()
            .diaSetmana(UPDATED_DIA_SETMANA);
        return periodicitatSetmanal;
    }

    @BeforeEach
    public void initTest() {
        periodicitatSetmanal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodicitatSetmanal() throws Exception {
        int databaseSizeBeforeCreate = periodicitatSetmanalRepository.findAll().size();

        // Create the PeriodicitatSetmanal
        restPeriodicitatSetmanalMockMvc.perform(post("/api/periodicitat-setmanals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodicitatSetmanal)))
            .andExpect(status().isCreated());

        // Validate the PeriodicitatSetmanal in the database
        List<PeriodicitatSetmanal> periodicitatSetmanalList = periodicitatSetmanalRepository.findAll();
        assertThat(periodicitatSetmanalList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodicitatSetmanal testPeriodicitatSetmanal = periodicitatSetmanalList.get(periodicitatSetmanalList.size() - 1);
        assertThat(testPeriodicitatSetmanal.getDiaSetmana()).isEqualTo(DEFAULT_DIA_SETMANA);
    }

    @Test
    @Transactional
    public void createPeriodicitatSetmanalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodicitatSetmanalRepository.findAll().size();

        // Create the PeriodicitatSetmanal with an existing ID
        periodicitatSetmanal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodicitatSetmanalMockMvc.perform(post("/api/periodicitat-setmanals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodicitatSetmanal)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodicitatSetmanal in the database
        List<PeriodicitatSetmanal> periodicitatSetmanalList = periodicitatSetmanalRepository.findAll();
        assertThat(periodicitatSetmanalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPeriodicitatSetmanals() throws Exception {
        // Initialize the database
        periodicitatSetmanalRepository.saveAndFlush(periodicitatSetmanal);

        // Get all the periodicitatSetmanalList
        restPeriodicitatSetmanalMockMvc.perform(get("/api/periodicitat-setmanals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodicitatSetmanal.getId().intValue())))
            .andExpect(jsonPath("$.[*].diaSetmana").value(hasItem(DEFAULT_DIA_SETMANA.toString())));
    }
    
    @Test
    @Transactional
    public void getPeriodicitatSetmanal() throws Exception {
        // Initialize the database
        periodicitatSetmanalRepository.saveAndFlush(periodicitatSetmanal);

        // Get the periodicitatSetmanal
        restPeriodicitatSetmanalMockMvc.perform(get("/api/periodicitat-setmanals/{id}", periodicitatSetmanal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodicitatSetmanal.getId().intValue()))
            .andExpect(jsonPath("$.diaSetmana").value(DEFAULT_DIA_SETMANA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodicitatSetmanal() throws Exception {
        // Get the periodicitatSetmanal
        restPeriodicitatSetmanalMockMvc.perform(get("/api/periodicitat-setmanals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodicitatSetmanal() throws Exception {
        // Initialize the database
        periodicitatSetmanalRepository.saveAndFlush(periodicitatSetmanal);

        int databaseSizeBeforeUpdate = periodicitatSetmanalRepository.findAll().size();

        // Update the periodicitatSetmanal
        PeriodicitatSetmanal updatedPeriodicitatSetmanal = periodicitatSetmanalRepository.findById(periodicitatSetmanal.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodicitatSetmanal are not directly saved in db
        em.detach(updatedPeriodicitatSetmanal);
        updatedPeriodicitatSetmanal
            .diaSetmana(UPDATED_DIA_SETMANA);

        restPeriodicitatSetmanalMockMvc.perform(put("/api/periodicitat-setmanals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeriodicitatSetmanal)))
            .andExpect(status().isOk());

        // Validate the PeriodicitatSetmanal in the database
        List<PeriodicitatSetmanal> periodicitatSetmanalList = periodicitatSetmanalRepository.findAll();
        assertThat(periodicitatSetmanalList).hasSize(databaseSizeBeforeUpdate);
        PeriodicitatSetmanal testPeriodicitatSetmanal = periodicitatSetmanalList.get(periodicitatSetmanalList.size() - 1);
        assertThat(testPeriodicitatSetmanal.getDiaSetmana()).isEqualTo(UPDATED_DIA_SETMANA);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodicitatSetmanal() throws Exception {
        int databaseSizeBeforeUpdate = periodicitatSetmanalRepository.findAll().size();

        // Create the PeriodicitatSetmanal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodicitatSetmanalMockMvc.perform(put("/api/periodicitat-setmanals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodicitatSetmanal)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodicitatSetmanal in the database
        List<PeriodicitatSetmanal> periodicitatSetmanalList = periodicitatSetmanalRepository.findAll();
        assertThat(periodicitatSetmanalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodicitatSetmanal() throws Exception {
        // Initialize the database
        periodicitatSetmanalRepository.saveAndFlush(periodicitatSetmanal);

        int databaseSizeBeforeDelete = periodicitatSetmanalRepository.findAll().size();

        // Delete the periodicitatSetmanal
        restPeriodicitatSetmanalMockMvc.perform(delete("/api/periodicitat-setmanals/{id}", periodicitatSetmanal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PeriodicitatSetmanal> periodicitatSetmanalList = periodicitatSetmanalRepository.findAll();
        assertThat(periodicitatSetmanalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
