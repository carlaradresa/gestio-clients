package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.PlantillaFeina;
import com.serviemporda.gestioclients.repository.PlantillaFeinaRepository;
import com.serviemporda.gestioclients.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.PlantillaFeina}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlantillaFeinaResource {

    private final Logger log = LoggerFactory.getLogger(PlantillaFeinaResource.class);

    private static final String ENTITY_NAME = "plantillaFeina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlantillaFeinaRepository plantillaFeinaRepository;

    public PlantillaFeinaResource(PlantillaFeinaRepository plantillaFeinaRepository) {
        this.plantillaFeinaRepository = plantillaFeinaRepository;
    }

    /**
     * {@code POST  /plantilla-feinas} : Create a new plantillaFeina.
     *
     * @param plantillaFeina the plantillaFeina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plantillaFeina, or with status {@code 400 (Bad Request)} if the plantillaFeina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plantilla-feinas")
    public ResponseEntity<PlantillaFeina> createPlantillaFeina(@RequestBody PlantillaFeina plantillaFeina) throws URISyntaxException {
        log.debug("REST request to save PlantillaFeina : {}", plantillaFeina);
        if (plantillaFeina.getId() != null) {
            throw new BadRequestAlertException("A new plantillaFeina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlantillaFeina result = plantillaFeinaRepository.save(plantillaFeina);
        return ResponseEntity.created(new URI("/api/plantilla-feinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plantilla-feinas} : Updates an existing plantillaFeina.
     *
     * @param plantillaFeina the plantillaFeina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plantillaFeina,
     * or with status {@code 400 (Bad Request)} if the plantillaFeina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plantillaFeina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plantilla-feinas")
    public ResponseEntity<PlantillaFeina> updatePlantillaFeina(@RequestBody PlantillaFeina plantillaFeina) throws URISyntaxException {
        log.debug("REST request to update PlantillaFeina : {}", plantillaFeina);
        if (plantillaFeina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlantillaFeina result = plantillaFeinaRepository.save(plantillaFeina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plantillaFeina.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plantilla-feinas} : get all the plantillaFeinas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plantillaFeinas in body.
     */
    @GetMapping("/plantilla-feinas")
    public List<PlantillaFeina> getAllPlantillaFeinas() {
        log.debug("REST request to get all PlantillaFeinas");
        return plantillaFeinaRepository.findAll();
    }

    /**
     * {@code GET  /plantilla-feinas/:id} : get the "id" plantillaFeina.
     *
     * @param id the id of the plantillaFeina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plantillaFeina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plantilla-feinas/{id}")
    public ResponseEntity<PlantillaFeina> getPlantillaFeina(@PathVariable Long id) {
        log.debug("REST request to get PlantillaFeina : {}", id);
        Optional<PlantillaFeina> plantillaFeina = plantillaFeinaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plantillaFeina);
    }

    /**
     * {@code DELETE  /plantilla-feinas/:id} : delete the "id" plantillaFeina.
     *
     * @param id the id of the plantillaFeina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plantilla-feinas/{id}")
    public ResponseEntity<Void> deletePlantillaFeina(@PathVariable Long id) {
        log.debug("REST request to delete PlantillaFeina : {}", id);
        plantillaFeinaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
