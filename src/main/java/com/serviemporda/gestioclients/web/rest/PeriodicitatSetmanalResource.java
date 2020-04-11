package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.PeriodicitatSetmanal;
import com.serviemporda.gestioclients.repository.PeriodicitatSetmanalRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.PeriodicitatSetmanal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PeriodicitatSetmanalResource {

    private final Logger log = LoggerFactory.getLogger(PeriodicitatSetmanalResource.class);

    private static final String ENTITY_NAME = "periodicitatSetmanal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodicitatSetmanalRepository periodicitatSetmanalRepository;

    public PeriodicitatSetmanalResource(PeriodicitatSetmanalRepository periodicitatSetmanalRepository) {
        this.periodicitatSetmanalRepository = periodicitatSetmanalRepository;
    }

    /**
     * {@code POST  /periodicitat-setmanals} : Create a new periodicitatSetmanal.
     *
     * @param periodicitatSetmanal the periodicitatSetmanal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodicitatSetmanal, or with status {@code 400 (Bad Request)} if the periodicitatSetmanal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodicitat-setmanals")
    public ResponseEntity<PeriodicitatSetmanal> createPeriodicitatSetmanal(@RequestBody PeriodicitatSetmanal periodicitatSetmanal) throws URISyntaxException {
        log.debug("REST request to save PeriodicitatSetmanal : {}", periodicitatSetmanal);
        if (periodicitatSetmanal.getId() != null) {
            throw new BadRequestAlertException("A new periodicitatSetmanal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodicitatSetmanal result = periodicitatSetmanalRepository.save(periodicitatSetmanal);
        return ResponseEntity.created(new URI("/api/periodicitat-setmanals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodicitat-setmanals} : Updates an existing periodicitatSetmanal.
     *
     * @param periodicitatSetmanal the periodicitatSetmanal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodicitatSetmanal,
     * or with status {@code 400 (Bad Request)} if the periodicitatSetmanal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodicitatSetmanal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodicitat-setmanals")
    public ResponseEntity<PeriodicitatSetmanal> updatePeriodicitatSetmanal(@RequestBody PeriodicitatSetmanal periodicitatSetmanal) throws URISyntaxException {
        log.debug("REST request to update PeriodicitatSetmanal : {}", periodicitatSetmanal);
        if (periodicitatSetmanal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodicitatSetmanal result = periodicitatSetmanalRepository.save(periodicitatSetmanal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodicitatSetmanal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /periodicitat-setmanals} : get all the periodicitatSetmanals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodicitatSetmanals in body.
     */
    @GetMapping("/periodicitat-setmanals")
    public List<PeriodicitatSetmanal> getAllPeriodicitatSetmanals() {
        log.debug("REST request to get all PeriodicitatSetmanals");
        return periodicitatSetmanalRepository.findAll();
    }

    /**
     * {@code GET  /periodicitat-setmanals/:id} : get the "id" periodicitatSetmanal.
     *
     * @param id the id of the periodicitatSetmanal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodicitatSetmanal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodicitat-setmanals/{id}")
    public ResponseEntity<PeriodicitatSetmanal> getPeriodicitatSetmanal(@PathVariable Long id) {
        log.debug("REST request to get PeriodicitatSetmanal : {}", id);
        Optional<PeriodicitatSetmanal> periodicitatSetmanal = periodicitatSetmanalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periodicitatSetmanal);
    }

    /**
     * {@code DELETE  /periodicitat-setmanals/:id} : delete the "id" periodicitatSetmanal.
     *
     * @param id the id of the periodicitatSetmanal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodicitat-setmanals/{id}")
    public ResponseEntity<Void> deletePeriodicitatSetmanal(@PathVariable Long id) {
        log.debug("REST request to delete PeriodicitatSetmanal : {}", id);
        periodicitatSetmanalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
