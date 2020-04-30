package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.GestioClientsApp;
import com.serviemporda.gestioclients.domain.PlantillaFeina;
import com.serviemporda.gestioclients.repository.FeinaRepository;
import com.serviemporda.gestioclients.repository.PlantillaFeinaRepository;
import com.serviemporda.gestioclients.service.FeinaService;
import com.serviemporda.gestioclients.service.PlantillaFeinaService;
import com.serviemporda.gestioclients.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static com.serviemporda.gestioclients.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlantillaFeinaResource} REST controller.
 */
@SpringBootTest(classes = GestioClientsApp.class)
public class PlantillaFeinaResourceIT {

    private static final LocalTime DEFAULT_HORA_INICI = LocalTime.of(0,0);
    private static final LocalTime UPDATED_HORA_INICI = LocalTime.now();

    private static final LocalTime DEFAULT_HORA_FINAL = LocalTime.of(0,0);
    private static final LocalTime UPDATED_HORA_FINAL = LocalTime.now();

    private static final Integer DEFAULT_TEMPS_PREVIST = 1;
    private static final Integer UPDATED_TEMPS_PREVIST = 2;

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

    @Autowired
    private FeinaService feinaService;

    @Autowired
    private PlantillaFeinaService plantillaFeinaService;

    @Autowired
    private FeinaRepository feinaRepository;

    @Mock
    private PlantillaFeinaRepository plantillaFeinaRepositoryMock;

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

    private MockMvc restPlantillaFeinaMockMvc;

    private PlantillaFeina plantillaFeina;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlantillaFeinaResource plantillaFeinaResource = new PlantillaFeinaResource(feinaService, plantillaFeinaService, feinaRepository, plantillaFeinaRepository);
        this.restPlantillaFeinaMockMvc = MockMvcBuilders.standaloneSetup(plantillaFeinaResource)
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
    public static PlantillaFeina createEntity(EntityManager em) {
        PlantillaFeina plantillaFeina = new PlantillaFeina()
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(plantillaFeina)))
            .andExpect(status().isCreated());

        // Validate the PlantillaFeina in the database
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeCreate + 1);
        PlantillaFeina testPlantillaFeina = plantillaFeinaList.get(plantillaFeinaList.size() - 1);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantillaFeina.getId().intValue())))
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
        PlantillaFeinaResource plantillaFeinaResource = new PlantillaFeinaResource(feinaService, plantillaFeinaService, feinaRepository, plantillaFeinaRepositoryMock);
        when(plantillaFeinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPlantillaFeinaMockMvc = MockMvcBuilders.standaloneSetup(plantillaFeinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPlantillaFeinaMockMvc.perform(get("/api/plantilla-feinas?eagerload=true"))
        .andExpect(status().isOk());

        verify(plantillaFeinaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlantillaFeinasWithEagerRelationshipsIsNotEnabled() throws Exception {
        PlantillaFeinaResource plantillaFeinaResource = new PlantillaFeinaResource(feinaService, plantillaFeinaService, feinaRepository, plantillaFeinaRepositoryMock);
            when(plantillaFeinaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPlantillaFeinaMockMvc = MockMvcBuilders.standaloneSetup(plantillaFeinaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(plantillaFeina.getId().intValue()))
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
            .horaInici(UPDATED_HORA_INICI)
            .horaFinal(UPDATED_HORA_FINAL)
            .tempsPrevist(UPDATED_TEMPS_PREVIST)
            .facturacioAutomatica(UPDATED_FACTURACIO_AUTOMATICA)
            .observacions(UPDATED_OBSERVACIONS)
            .setmanaInicial(UPDATED_SETMANA_INICIAL)
            .setmanaFinal(UPDATED_SETMANA_FINAL)
            .numeroControl(UPDATED_NUMERO_CONTROL);

        restPlantillaFeinaMockMvc.perform(put("/api/plantilla-feinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlantillaFeina)))
            .andExpect(status().isOk());

        // Validate the PlantillaFeina in the database
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeUpdate);
        PlantillaFeina testPlantillaFeina = plantillaFeinaList.get(plantillaFeinaList.size() - 1);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlantillaFeina> plantillaFeinaList = plantillaFeinaRepository.findAll();
        assertThat(plantillaFeinaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
