package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.PlantillaFeina;
import com.serviemporda.gestioclients.repository.PlantillaFeinaRepository;

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

/**
 * Integration tests for the {@link PlantillaFeinaResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlantillaFeinaResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_HORA_INICI = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICI = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TEMPS_PREVIST = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEMPS_PREVIST = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FACTURACIO_AUTOMATICA = false;
    private static final Boolean UPDATED_FACTURACIO_AUTOMATICA = true;

    private static final String DEFAULT_OBSERVACIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SETMANA_INICIAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SETMANA_INICIAL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SETMANA_FINAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SETMANA_FINAL = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMERO_CONTROL = 1;
    private static final Integer UPDATED_NUMERO_CONTROL = 2;

    @Autowired
    private PlantillaFeinaRepository plantillaFeinaRepository;

    @Mock
    private PlantillaFeinaRepository plantillaFeinaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantillaFeinaMockMvc;

    private PlantillaFeina plantillaFeina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantillaFeina createEntity(EntityManager em) {
        PlantillaFeina plantillaFeina = new PlantillaFeina()
            .nom(DEFAULT_NOM)
            .horaInici(DEFAULT_HORA_INICI)
            .horaFinal(DEFAULT_HORA_FINAL)
            .tempsPrevist(DEFAULT_TEMPS_PREVIST)
            .facturacioAutomatica(DEFAULT_FACTURACIO_AUTOMATICA)
            .observacions(DEFAULT_OBSERVACIONS)
            .setmanaInicial(DEFAULT_SETMANA_INICIAL)
            .setmanaFinal(DEFAULT_SETMANA_FINAL)
            .numeroControl(DEFAULT_NUMERO_CONTROL);
        return plantillaFeina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantillaFeina createUpdatedEntity(EntityManager em) {
        PlantillaFeina plantillaFeina = new PlantillaFeina()
            .nom(UPDATED_NOM)
            .horaInici(UPDATED_HORA_INICI)
            .horaFinal(UPDATED_HORA_FINAL)
            .tempsPrevist(UPDATED_TEMPS_PREVIST)
            .facturacioAutomatica(UPDATED_FACTURACIO_AUTOMATICA)
            .observacions(UPDATED_OBSERVACIONS)
            .setmanaInicial(UPDATED_SETMANA_INICIAL)
            .setmanaFinal(UPDATED_SETMANA_FINAL)
            .numeroControl(UPDATED_NUMERO_CONTROL);
        return plantillaFeina;
    }

    @BeforeEach
    public void initTest() {
        plantillaFeina = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlantillaFeina() throws Exception {
        int databaseSizeBeforeCreate = plantillaFeinaRepository.findAll().size();

        // Create the PlantillaFeina
        restPlantillaFeinaMockMvc.perform(post("/api/plantilla-feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaFeina)))
            .andExpect(status().isCreated());

        // Validate the PlantillaFeina in the database
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeCreate + 1);
        PlantillaFeina testPlantillaFeina = plantillaFeinaList.get(plantillaFeinaList.size() - 1);
        assertThat(testPlantillaFeina.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPlantillaFeina.getHoraInici()).isEqualTo(DEFAULT_HORA_INICI);
        assertThat(testPlantillaFeina.getHoraFinal()).isEqualTo(DEFAULT_HORA_FINAL);
        assertThat(testPlantillaFeina.getTempsPrevist()).isEqualTo(DEFAULT_TEMPS_PREVIST);
        assertThat(testPlantillaFeina.isFacturacioAutomatica()).isEqualTo(DEFAULT_FACTURACIO_AUTOMATICA);
        assertThat(testPlantillaFeina.getObservacions()).isEqualTo(DEFAULT_OBSERVACIONS);
        assertThat(testPlantillaFeina.getSetmanaInicial()).isEqualTo(DEFAULT_SETMANA_INICIAL);
        assertThat(testPlantillaFeina.getSetmanaFinal()).isEqualTo(DEFAULT_SETMANA_FINAL);
        assertThat(testPlantillaFeina.getNumeroControl()).isEqualTo(DEFAULT_NUMERO_CONTROL);
    }

    @Test
    @Transactional
    public void createPlantillaFeinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plantillaFeinaRepository.findAll().size();

        // Create the PlantillaFeina with an existing ID
        plantillaFeina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantillaFeinaMockMvc.perform(post("/api/plantilla-feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaFeina)))
            .andExpect(status().isBadRequest());

        // Validate the PlantillaFeina in the database
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlantillaFeinas() throws Exception {
        // Initialize the database
        plantillaFeinaRepository.saveAndFlush(plantillaFeina);

        // Get all the plantillaFeinaList
        restPlantillaFeinaMockMvc.perform(get("/api/plantilla-feinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantillaFeina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].horaInici").value(hasItem(DEFAULT_HORA_INICI.toString())))
            .andExpect(jsonPath("$.[*].horaFinal").value(hasItem(DEFAULT_HORA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].tempsPrevist").value(hasItem(DEFAULT_TEMPS_PREVIST.toString())))
            .andExpect(jsonPath("$.[*].facturacioAutomatica").value(hasItem(DEFAULT_FACTURACIO_AUTOMATICA.booleanValue())))
            .andExpect(jsonPath("$.[*].observacions").value(hasItem(DEFAULT_OBSERVACIONS)))
            .andExpect(jsonPath("$.[*].setmanaInicial").value(hasItem(DEFAULT_SETMANA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].setmanaFinal").value(hasItem(DEFAULT_SETMANA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].numeroControl").value(hasItem(DEFAULT_NUMERO_CONTROL)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPlantillaFeinasWithEagerRelationshipsIsEnabled() throws Exception {
        PlantillaFeinaResource plantillaFeinaResource = new PlantillaFeinaResource(plantillaFeinaRepositoryMock);
        when(plantillaFeinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlantillaFeinaMockMvc.perform(get("/api/plantilla-feinas?eagerload=true"))
            .andExpect(status().isOk());

        verify(plantillaFeinaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlantillaFeinasWithEagerRelationshipsIsNotEnabled() throws Exception {
        PlantillaFeinaResource plantillaFeinaResource = new PlantillaFeinaResource(plantillaFeinaRepositoryMock);
        when(plantillaFeinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlantillaFeinaMockMvc.perform(get("/api/plantilla-feinas?eagerload=true"))
            .andExpect(status().isOk());

        verify(plantillaFeinaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPlantillaFeina() throws Exception {
        // Initialize the database
        plantillaFeinaRepository.saveAndFlush(plantillaFeina);

        // Get the plantillaFeina
        restPlantillaFeinaMockMvc.perform(get("/api/plantilla-feinas/{id}", plantillaFeina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plantillaFeina.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.horaInici").value(DEFAULT_HORA_INICI.toString()))
            .andExpect(jsonPath("$.horaFinal").value(DEFAULT_HORA_FINAL.toString()))
            .andExpect(jsonPath("$.tempsPrevist").value(DEFAULT_TEMPS_PREVIST.toString()))
            .andExpect(jsonPath("$.facturacioAutomatica").value(DEFAULT_FACTURACIO_AUTOMATICA.booleanValue()))
            .andExpect(jsonPath("$.observacions").value(DEFAULT_OBSERVACIONS))
            .andExpect(jsonPath("$.setmanaInicial").value(DEFAULT_SETMANA_INICIAL.toString()))
            .andExpect(jsonPath("$.setmanaFinal").value(DEFAULT_SETMANA_FINAL.toString()))
            .andExpect(jsonPath("$.numeroControl").value(DEFAULT_NUMERO_CONTROL));
    }

    @Test
    @Transactional
    public void getNonExistingPlantillaFeina() throws Exception {
        // Get the plantillaFeina
        restPlantillaFeinaMockMvc.perform(get("/api/plantilla-feinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantillaFeina() throws Exception {
        // Initialize the database
        plantillaFeinaRepository.saveAndFlush(plantillaFeina);

        int databaseSizeBeforeUpdate = plantillaFeinaRepository.findAll().size();

        // Update the plantillaFeina
        PlantillaFeina updatedPlantillaFeina = plantillaFeinaRepository.findById(plantillaFeina.getId()).get();
        // Disconnect from session so that the updates on updatedPlantillaFeina are not directly saved in db
        em.detach(updatedPlantillaFeina);
        updatedPlantillaFeina
            .nom(UPDATED_NOM)
            .horaInici(UPDATED_HORA_INICI)
            .horaFinal(UPDATED_HORA_FINAL)
            .tempsPrevist(UPDATED_TEMPS_PREVIST)
            .facturacioAutomatica(UPDATED_FACTURACIO_AUTOMATICA)
            .observacions(UPDATED_OBSERVACIONS)
            .setmanaInicial(UPDATED_SETMANA_INICIAL)
            .setmanaFinal(UPDATED_SETMANA_FINAL)
            .numeroControl(UPDATED_NUMERO_CONTROL);

        restPlantillaFeinaMockMvc.perform(put("/api/plantilla-feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlantillaFeina)))
            .andExpect(status().isOk());

        // Validate the PlantillaFeina in the database
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeUpdate);
        PlantillaFeina testPlantillaFeina = plantillaFeinaList.get(plantillaFeinaList.size() - 1);
        assertThat(testPlantillaFeina.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPlantillaFeina.getHoraInici()).isEqualTo(UPDATED_HORA_INICI);
        assertThat(testPlantillaFeina.getHoraFinal()).isEqualTo(UPDATED_HORA_FINAL);
        assertThat(testPlantillaFeina.getTempsPrevist()).isEqualTo(UPDATED_TEMPS_PREVIST);
        assertThat(testPlantillaFeina.isFacturacioAutomatica()).isEqualTo(UPDATED_FACTURACIO_AUTOMATICA);
        assertThat(testPlantillaFeina.getObservacions()).isEqualTo(UPDATED_OBSERVACIONS);
        assertThat(testPlantillaFeina.getSetmanaInicial()).isEqualTo(UPDATED_SETMANA_INICIAL);
        assertThat(testPlantillaFeina.getSetmanaFinal()).isEqualTo(UPDATED_SETMANA_FINAL);
        assertThat(testPlantillaFeina.getNumeroControl()).isEqualTo(UPDATED_NUMERO_CONTROL);
    }

    @Test
    @Transactional
    public void updateNonExistingPlantillaFeina() throws Exception {
        int databaseSizeBeforeUpdate = plantillaFeinaRepository.findAll().size();

        // Create the PlantillaFeina

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantillaFeinaMockMvc.perform(put("/api/plantilla-feinas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plantillaFeina)))
            .andExpect(status().isBadRequest());

        // Validate the PlantillaFeina in the database
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlantillaFeina() throws Exception {
        // Initialize the database
        plantillaFeinaRepository.saveAndFlush(plantillaFeina);

        int databaseSizeBeforeDelete = plantillaFeinaRepository.findAll().size();

        // Delete the plantillaFeina
        restPlantillaFeinaMockMvc.perform(delete("/api/plantilla-feinas/{id}", plantillaFeina.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
