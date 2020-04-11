package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Treballador;
import com.serviemporda.gestioclients.repository.TreballadorRepository;

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
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.serviemporda.gestioclients.domain.enumeration.Estat;
/**
 * Integration tests for the {@link TreballadorResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TreballadorResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Duration DEFAULT_CARREGA_HORES = Duration.ofHours(6);
    private static final Duration UPDATED_CARREGA_HORES = Duration.ofHours(12);

    private static final Estat DEFAULT_ESTAT = Estat.ACTIU;
    private static final Estat UPDATED_ESTAT = Estat.PAUSAT;

    private static final Boolean DEFAULT_CONTROL_QUALITAT = false;
    private static final Boolean UPDATED_CONTROL_QUALITAT = true;

    @Autowired
    private TreballadorRepository treballadorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTreballadorMockMvc;

    private Treballador treballador;

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
            .estat(DEFAULT_ESTAT)
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
            .estat(UPDATED_ESTAT)
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
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treballador)))
            .andExpect(status().isCreated());

        // Validate the Treballador in the database
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeCreate + 1);
        Treballador testTreballador = treballadorList.get(treballadorList.size() - 1);
        assertThat(testTreballador.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTreballador.getCarregaHores()).isEqualTo(DEFAULT_CARREGA_HORES);
        assertThat(testTreballador.getEstat()).isEqualTo(DEFAULT_ESTAT);
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treballador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].carregaHores").value(hasItem(DEFAULT_CARREGA_HORES.toString())))
            .andExpect(jsonPath("$.[*].estat").value(hasItem(DEFAULT_ESTAT.toString())))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(treballador.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.carregaHores").value(DEFAULT_CARREGA_HORES.toString()))
            .andExpect(jsonPath("$.estat").value(DEFAULT_ESTAT.toString()))
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
            .estat(UPDATED_ESTAT)
            .controlQualitat(UPDATED_CONTROL_QUALITAT);

        restTreballadorMockMvc.perform(put("/api/treballadors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreballador)))
            .andExpect(status().isOk());

        // Validate the Treballador in the database
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeUpdate);
        Treballador testTreballador = treballadorList.get(treballadorList.size() - 1);
        assertThat(testTreballador.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTreballador.getCarregaHores()).isEqualTo(UPDATED_CARREGA_HORES);
        assertThat(testTreballador.getEstat()).isEqualTo(UPDATED_ESTAT);
        assertThat(testTreballador.isControlQualitat()).isEqualTo(UPDATED_CONTROL_QUALITAT);
    }

    @Test
    @Transactional
    public void updateNonExistingTreballador() throws Exception {
        int databaseSizeBeforeUpdate = treballadorRepository.findAll().size();

        // Create the Treballador

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreballadorMockMvc.perform(put("/api/treballadors")
            .contentType(MediaType.APPLICATION_JSON)
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
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Treballador> treballadorList = treballadorRepository.findAll();
        assertThat(treballadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
