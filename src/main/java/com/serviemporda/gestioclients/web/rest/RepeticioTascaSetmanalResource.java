package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.RepeticioTascaSetmanal;
import com.serviemporda.gestioclients.repository.RepeticioTascaSetmanalRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.RepeticioTascaSetmanal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RepeticioTascaSetmanalResource {

    private final Logger log = LoggerFactory.getLogger(RepeticioTascaSetmanalResource.class);

    private static final String ENTITY_NAME = "repeticioTascaSetmanal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepeticioTascaSetmanalRepository repeticioTascaSetmanalRepository;

    public RepeticioTascaSetmanalResource(RepeticioTascaSetmanalRepository repeticioTascaSetmanalRepository) {
        this.repeticioTascaSetmanalRepository = repeticioTascaSetmanalRepository;
    }

    /**
     * {@code POST  /repeticio-tasca-setmanals} : Create a new repeticioTascaSetmanal.
     *
     * @param repeticioTascaSetmanal the repeticioTascaSetmanal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repeticioTascaSetmanal, or with status {@code 400 (Bad Request)} if the repeticioTascaSetmanal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/repeticio-tasca-setmanals")
    public ResponseEntity<RepeticioTascaSetmanal> createRepeticioTascaSetmanal(@RequestBody RepeticioTascaSetmanal repeticioTascaSetmanal) throws URISyntaxException {
        log.debug("REST request to save RepeticioTascaSetmanal : {}", repeticioTascaSetmanal);
        if (repeticioTascaSetmanal.getId() != null) {
            throw new BadRequestAlertException("A new repeticioTascaSetmanal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepeticioTascaSetmanal result = repeticioTascaSetmanalRepository.save(repeticioTascaSetmanal);
        return ResponseEntity.created(new URI("/api/repeticio-tasca-setmanals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /repeticio-tasca-setmanals} : Updates an existing repeticioTascaSetmanal.
     *
     * @param repeticioTascaSetmanal the repeticioTascaSetmanal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repeticioTascaSetmanal,
     * or with status {@code 400 (Bad Request)} if the repeticioTascaSetmanal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repeticioTascaSetmanal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/repeticio-tasca-setmanals")
    public ResponseEntity<RepeticioTascaSetmanal> updateRepeticioTascaSetmanal(@RequestBody RepeticioTascaSetmanal repeticioTascaSetmanal) throws URISyntaxException {
        log.debug("REST request to update RepeticioTascaSetmanal : {}", repeticioTascaSetmanal);
        if (repeticioTascaSetmanal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RepeticioTascaSetmanal result = repeticioTascaSetmanalRepository.save(repeticioTascaSetmanal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, repeticioTascaSetmanal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /repeticio-tasca-setmanals} : get all the repeticioTascaSetmanals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repeticioTascaSetmanals in body.
     */
    @GetMapping("/repeticio-tasca-setmanals")
    public List<RepeticioTascaSetmanal> getAllRepeticioTascaSetmanals() {
        log.debug("REST request to get all RepeticioTascaSetmanals");
        return repeticioTascaSetmanalRepository.findAll();
    }

    /**
     * {@code GET  /repeticio-tasca-setmanals/:id} : get the "id" repeticioTascaSetmanal.
     *
     * @param id the id of the repeticioTascaSetmanal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repeticioTascaSetmanal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/repeticio-tasca-setmanals/{id}")
    public ResponseEntity<RepeticioTascaSetmanal> getRepeticioTascaSetmanal(@PathVariable Long id) {
        log.debug("REST request to get RepeticioTascaSetmanal : {}", id);
        Optional<RepeticioTascaSetmanal> repeticioTascaSetmanal = repeticioTascaSetmanalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(repeticioTascaSetmanal);
    }

    /**
     * {@code DELETE  /repeticio-tasca-setmanals/:id} : delete the "id" repeticioTascaSetmanal.
     *
     * @param id the id of the repeticioTascaSetmanal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/repeticio-tasca-setmanals/{id}")
    public ResponseEntity<Void> deleteRepeticioTascaSetmanal(@PathVariable Long id) {
        log.debug("REST request to delete RepeticioTascaSetmanal : {}", id);
        repeticioTascaSetmanalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
