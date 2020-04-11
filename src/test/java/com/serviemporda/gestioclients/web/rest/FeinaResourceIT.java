package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.Feina;
import com.serviemporda.gestioclients.repository.FeinaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.serviemporda.gestioclients.domain.enumeration.Estat;
/**
 * Integration tests for the {@link FeinaResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeinaResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCIO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SETMANA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SETMANA = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_TEMPS_PREVIST = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEMPS_PREVIST = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TEMPS_REAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEMPS_REAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Estat DEFAULT_ESTAT = Estat.ACTIU;
    private static final Estat UPDATED_ESTAT = Estat.PAUSAT;

    private static final Integer DEFAULT_INTERVAL_CONTROL = 1;
    private static final Integer UPDATED_INTERVAL_CONTROL = 2;

    private static final Boolean DEFAULT_FACTURACIO_AUTOMATICA = false;
    private static final Boolean UPDATED_FACTURACIO_AUTOMATICA = true;

    private static final String DEFAULT_OBSERVACIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONS = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIS_TREBALLADOR = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIS_TREBALLADOR = "BBBBBBBBBB";

    @Autowired
    private FeinaRepository feinaRepository;

    @Mock
    private FeinaRepository feinaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeinaMockMvc;

    private Feina feina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feina createEntity(EntityManager em) {
        Feina feina = new Feina()
            .nom(DEFAULT_NOM)
            .descripcio(DEFAULT_DESCRIPCIO)
            .setmana(DEFAULT_SETMANA)
            .tempsPrevist(DEFAULT_TEMPS_PREVIST)
            .tempsReal(DEFAULT_TEMPS_REAL)
            .estat(DEFAULT_ESTAT)
            .intervalControl(DEFAULT_INTERVAL_CONTROL)
            .facturacioAutomatica(DEFAULT_FACTURACIO_AUTOMATICA)
            .observacions(DEFAULT_OBSERVACIONS)
            .comentarisTreballador(DEFAULT_COMENTARIS_TREBALLADOR);
        return feina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feina createUpdatedEntity(EntityManager em) {
        Feina feina = new Feina()
            .nom(UPDATED_NOM)
            .descripcio(UPDATED_DESCRIPCIO)
            .setmana(UPDATED_SETMANA)
            .tempsPrevist(UPDATED_TEMPS_PREVIST)
            .tempsReal(UPDATED_TEMPS_REAL)
            .estat(UPDATED_ESTAT)
            .intervalControl(UPDATED_INTERVAL_CONTROL)
            .facturacioAutomatica(UPDATED_FACTURACIO_AUTOMATICA)
            .observacions(UPDATED_OBSERVACIONS)
            .comentarisTreballador(UPDATED_COMENTARIS_TREBALLADOR);
        return feina;
    }

    @BeforeEach
    public void initTest() {
        feina = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeina() throws Exception {
        int databaseSizeBeforeCreate = feinaRepository.findAll().size();

        // Create the Feina
        restFeinaMockMvc.perform(post("/api/feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feina)))
            .andExpect(status().isCreated());

        // Validate the Feina in the database
        List<Feina> feinaList = feinaRepository.findAll();
        assertThat(feinaList).hasSize(databaseSizeBeforeCreate + 1);
        Feina testFeina = feinaList.get(feinaList.size() - 1);
        assertThat(testFeina.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFeina.getDescripcio()).isEqualTo(DEFAULT_DESCRIPCIO);
        assertThat(testFeina.getSetmana()).isEqualTo(DEFAULT_SETMANA);
        assertThat(testFeina.getTempsPrevist()).isEqualTo(DEFAULT_TEMPS_PREVIST);
        assertThat(testFeina.getTempsReal()).isEqualTo(DEFAULT_TEMPS_REAL);
        assertThat(testFeina.getEstat()).isEqualTo(DEFAULT_ESTAT);
        assertThat(testFeina.getIntervalControl()).isEqualTo(DEFAULT_INTERVAL_CONTROL);
        assertThat(testFeina.isFacturacioAutomatica()).isEqualTo(DEFAULT_FACTURACIO_AUTOMATICA);
        assertThat(testFeina.getObservacions()).isEqualTo(DEFAULT_OBSERVACIONS);
        assertThat(testFeina.getComentarisTreballador()).isEqualTo(DEFAULT_COMENTARIS_TREBALLADOR);
    }

    @Test
    @Transactional
    public void createFeinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feinaRepository.findAll().size();

        // Create the Feina with an existing ID
        feina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeinaMockMvc.perform(post("/api/feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feina)))
            .andExpect(status().isBadRequest());

        // Validate the Feina in the database
        List<Feina> feinaList = feinaRepository.findAll();
        assertThat(feinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFeinas() throws Exception {
        // Initialize the database
        feinaRepository.saveAndFlush(feina);

        // Get all the feinaList
        restFeinaMockMvc.perform(get("/api/feinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].descripcio").value(hasItem(DEFAULT_DESCRIPCIO)))
            .andExpect(jsonPath("$.[*].setmana").value(hasItem(DEFAULT_SETMANA.toString())))
            .andExpect(jsonPath("$.[*].tempsPrevist").value(hasItem(DEFAULT_TEMPS_PREVIST.toString())))
            .andExpect(jsonPath("$.[*].tempsReal").value(hasItem(DEFAULT_TEMPS_REAL.toString())))
            .andExpect(jsonPath("$.[*].estat").value(hasItem(DEFAULT_ESTAT.toString())))
            .andExpect(jsonPath("$.[*].intervalControl").value(hasItem(DEFAULT_INTERVAL_CONTROL)))
            .andExpect(jsonPath("$.[*].facturacioAutomatica").value(hasItem(DEFAULT_FACTURACIO_AUTOMATICA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacions").value(hasItem(DEFAULT_OBSERVACIONS)))
            .andExpect(jsonPath("$.[*].comentarisTreballador").value(hasItem(DEFAULT_COMENTARIS_TREBALLADOR)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFeinasWithEagerRelationshipsIsEnabled() throws Exception {
        FeinaResource feinaResource = new FeinaResource(feinaRepositoryMock);
        when(feinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeinaMockMvc.perform(get("/api/feinas?eagerload=true"))
            .andExpect(status().isOk());

        verify(feinaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFeinasWithEagerRelationshipsIsNotEnabled() throws Exception {
        FeinaResource feinaResource = new FeinaResource(feinaRepositoryMock);
        when(feinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFeinaMockMvc.perform(get("/api/feinas?eagerload=true"))
            .andExpect(status().isOk());

        verify(feinaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFeina() throws Exception {
        // Initialize the database
        feinaRepository.saveAndFlush(feina);

        // Get the feina
        restFeinaMockMvc.perform(get("/api/feinas/{id}", feina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feina.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.descripcio").value(DEFAULT_DESCRIPCIO))
            .andExpect(jsonPath("$.setmana").value(DEFAULT_SETMANA.toString()))
            .andExpect(jsonPath("$.tempsPrevist").value(DEFAULT_TEMPS_PREVIST.toString()))
            .andExpect(jsonPath("$.tempsReal").value(DEFAULT_TEMPS_REAL.toString()))
            .andExpect(jsonPath("$.estat").value(DEFAULT_ESTAT.toString()))
            .andExpect(jsonPath("$.intervalControl").value(DEFAULT_INTERVAL_CONTROL))
            .andExpect(jsonPath("$.facturacioAutomatica").value(DEFAULT_FACTURACIO_AUTOMATICA.booleanValue()))
            .andExpect(jsonPath("$.observacions").value(DEFAULT_OBSERVACIONS))
            .andExpect(jsonPath("$.comentarisTreballador").value(DEFAULT_COMENTARIS_TREBALLADOR));
    }

    @Test
    @Transactional
    public void getNonExistingFeina() throws Exception {
        // Get the feina
        restFeinaMockMvc.perform(get("/api/feinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeina() throws Exception {
        // Initialize the database
        feinaRepository.saveAndFlush(feina);

        int databaseSizeBeforeUpdate = feinaRepository.findAll().size();

        // Update the feina
        Feina updatedFeina = feinaRepository.findById(feina.getId()).get();
        // Disconnect from session so that the updates on updatedFeina are not directly saved in db
        em.detach(updatedFeina);
        updatedFeina
            .nom(UPDATED_NOM)
            .descripcio(UPDATED_DESCRIPCIO)
            .setmana(UPDATED_SETMANA)
            .tempsPrevist(UPDATED_TEMPS_PREVIST)
            .tempsReal(UPDATED_TEMPS_REAL)
            .estat(UPDATED_ESTAT)
            .intervalControl(UPDATED_INTERVAL_CONTROL)
            .facturacioAutomatica(UPDATED_FACTURACIO_AUTOMATICA)
            .observacions(UPDATED_OBSERVACIONS)
            .comentarisTreballador(UPDATED_COMENTARIS_TREBALLADOR);

        restFeinaMockMvc.perform(put("/api/feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeina)))
            .andExpect(status().isOk());

        // Validate the Feina in the database
        List<Feina> feinaList = feinaRepository.findAll();
        assertThat(feinaList).hasSize(databaseSizeBeforeUpdate);
        Feina testFeina = feinaList.get(feinaList.size() - 1);
        assertThat(testFeina.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFeina.getDescripcio()).isEqualTo(UPDATED_DESCRIPCIO);
        assertThat(testFeina.getSetmana()).isEqualTo(UPDATED_SETMANA);
        assertThat(testFeina.getTempsPrevist()).isEqualTo(UPDATED_TEMPS_PREVIST);
        assertThat(testFeina.getTempsReal()).isEqualTo(UPDATED_TEMPS_REAL);
        assertThat(testFeina.getEstat()).isEqualTo(UPDATED_ESTAT);
        assertThat(testFeina.getIntervalControl()).isEqualTo(UPDATED_INTERVAL_CONTROL);
        assertThat(testFeina.isFacturacioAutomatica()).isEqualTo(UPDATED_FACTURACIO_AUTOMATICA);
        assertThat(testFeina.getObservacions()).isEqualTo(UPDATED_OBSERVACIONS);
        assertThat(testFeina.getComentarisTreballador()).isEqualTo(UPDATED_COMENTARIS_TREBALLADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingFeina() throws Exception {
        int databaseSizeBeforeUpdate = feinaRepository.findAll().size();

        // Create the Feina

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeinaMockMvc.perform(put("/api/feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feina)))
            .andExpect(status().isBadRequest());

        // Validate the Feina in the database
        List<Feina> feinaList = feinaRepository.findAll();
        assertThat(feinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeina() throws Exception {
        // Initialize the database
        feinaRepository.saveAndFlush(feina);

        int databaseSizeBeforeDelete = feinaRepository.findAll().size();

        // Delete the feina
        restFeinaMockMvc.perform(delete("/api/feinas/{id}", feina.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feina> feinaList = feinaRepository.findAll();
        assertThat(feinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
