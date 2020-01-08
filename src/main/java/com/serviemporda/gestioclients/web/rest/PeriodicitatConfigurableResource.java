package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.PeriodicitatConfigurable;
import com.serviemporda.gestioclients.repository.PeriodicitatConfigurableRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.PeriodicitatConfigurable}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PeriodicitatConfigurableResource {

    private final Logger log = LoggerFactory.getLogger(PeriodicitatConfigurableResource.class);

    private static final String ENTITY_NAME = "periodicitatConfigurable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodicitatConfigurableRepository periodicitatConfigurableRepository;

    public PeriodicitatConfigurableResource(PeriodicitatConfigurableRepository periodicitatConfigurableRepository) {
        this.periodicitatConfigurableRepository = periodicitatConfigurableRepository;
    }

    /**
     * {@code POST  /periodicitat-configurables} : Create a new periodicitatConfigurable.
     *
     * @param periodicitatConfigurable the periodicitatConfigurable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodicitatConfigurable, or with status {@code 400 (Bad Request)} if the periodicitatConfigurable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodicitat-configurables")
    public ResponseEntity<PeriodicitatConfigurable> createPeriodicitatConfigurable(@RequestBody PeriodicitatConfigurable periodicitatConfigurable) throws URISyntaxException {
        log.debug("REST request to save PeriodicitatConfigurable : {}", periodicitatConfigurable);
        if (periodicitatConfigurable.getId() != null) {
            throw new BadRequestAlertException("A new periodicitatConfigurable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodicitatConfigurable result = periodicitatConfigurableRepository.save(periodicitatConfigurable);
        return ResponseEntity.created(new URI("/api/periodicitat-configurables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodicitat-configurables} : Updates an existing periodicitatConfigurable.
     *
     * @param periodicitatConfigurable the periodicitatConfigurable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodicitatConfigurable,
     * or with status {@code 400 (Bad Request)} if the periodicitatConfigurable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodicitatConfigurable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodicitat-configurables")
    public ResponseEntity<PeriodicitatConfigurable> updatePeriodicitatConfigurable(@RequestBody PeriodicitatConfigurable periodicitatConfigurable) throws URISyntaxException {
        log.debug("REST request to update PeriodicitatConfigurable : {}", periodicitatConfigurable);
        if (periodicitatConfigurable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodicitatConfigurable result = periodicitatConfigurableRepository.save(periodicitatConfigurable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodicitatConfigurable.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /periodicitat-configurables} : get all the periodicitatConfigurables.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodicitatConfigurables in body.
     */
    @GetMapping("/periodicitat-configurables")
    public List<PeriodicitatConfigurable> getAllPeriodicitatConfigurables(@RequestParam(required = false) String filter) {
        if ("plantilla-is-null".equals(filter)) {
            log.debug("REST request to get all PeriodicitatConfigurables where plantilla is null");
            return StreamSupport
                .stream(periodicitatConfigurableRepository.findAll().spliterator(), false)
                .filter(periodicitatConfigurable -> periodicitatConfigurable.getPlantilla() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PeriodicitatConfigurables");
        return periodicitatConfigurableRepository.findAll();
    }

    /**
     * {@code GET  /periodicitat-configurables/:id} : get the "id" periodicitatConfigurable.
     *
     * @param id the id of the periodicitatConfigurable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodicitatConfigurable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodicitat-configurables/{id}")
    public ResponseEntity<PeriodicitatConfigurable> getPeriodicitatConfigurable(@PathVariable Long id) {
        log.debug("REST request to get PeriodicitatConfigurable : {}", id);
        Optional<PeriodicitatConfigurable> periodicitatConfigurable = periodicitatConfigurableRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(periodicitatConfigurable);
    }

    /**
     * {@code DELETE  /periodicitat-configurables/:id} : delete the "id" periodicitatConfigurable.
     *
     * @param id the id of the periodicitatConfigurable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodicitat-configurables/{id}")
    public ResponseEntity<Void> deletePeriodicitatConfigurable(@PathVariable Long id) {
        log.debug("REST request to delete PeriodicitatConfigurable : {}", id);
        periodicitatConfigurableRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
