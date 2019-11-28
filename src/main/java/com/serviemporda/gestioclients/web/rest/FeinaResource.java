package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.Feina;
import com.serviemporda.gestioclients.repository.FeinaRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.Feina}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FeinaResource {

    private final Logger log = LoggerFactory.getLogger(FeinaResource.class);

    private static final String ENTITY_NAME = "feina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeinaRepository feinaRepository;

    public FeinaResource(FeinaRepository feinaRepository) {
        this.feinaRepository = feinaRepository;
    }

    /**
     * {@code POST  /feinas} : Create a new feina.
     *
     * @param feina the feina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feina, or with status {@code 400 (Bad Request)} if the feina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feinas")
    public ResponseEntity<Feina> createFeina(@RequestBody Feina feina) throws URISyntaxException {
        log.debug("REST request to save Feina : {}", feina);
        if (feina.getId() != null) {
            throw new BadRequestAlertException("A new feina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Feina result = feinaRepository.save(feina);
        return ResponseEntity.created(new URI("/api/feinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feinas} : Updates an existing feina.
     *
     * @param feina the feina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feina,
     * or with status {@code 400 (Bad Request)} if the feina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feinas")
    public ResponseEntity<Feina> updateFeina(@RequestBody Feina feina) throws URISyntaxException {
        log.debug("REST request to update Feina : {}", feina);
        if (feina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Feina result = feinaRepository.save(feina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feina.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feinas} : get all the feinas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feinas in body.
     */
    @GetMapping("/feinas")
    public List<Feina> getAllFeinas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Feinas");
        return feinaRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /feinas/:id} : get the "id" feina.
     *
     * @param id the id of the feina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feinas/{id}")
    public ResponseEntity<Feina> getFeina(@PathVariable Long id) {
        log.debug("REST request to get Feina : {}", id);
        Optional<Feina> feina = feinaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(feina);
    }

    /**
     * {@code DELETE  /feinas/:id} : delete the "id" feina.
     *
     * @param id the id of the feina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feinas/{id}")
    public ResponseEntity<Void> deleteFeina(@PathVariable Long id) {
        log.debug("REST request to delete Feina : {}", id);
        feinaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
