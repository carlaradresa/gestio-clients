package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.Ubicacio;
import com.serviemporda.gestioclients.repository.UbicacioRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.Ubicacio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UbicacioResource {

    private final Logger log = LoggerFactory.getLogger(UbicacioResource.class);

    private static final String ENTITY_NAME = "ubicacio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UbicacioRepository ubicacioRepository;

    public UbicacioResource(UbicacioRepository ubicacioRepository) {
        this.ubicacioRepository = ubicacioRepository;
    }

    /**
     * {@code POST  /ubicacios} : Create a new ubicacio.
     *
     * @param ubicacio the ubicacio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ubicacio, or with status {@code 400 (Bad Request)} if the ubicacio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ubicacios")
    public ResponseEntity<Ubicacio> createUbicacio(@RequestBody Ubicacio ubicacio) throws URISyntaxException {
        log.debug("REST request to save Ubicacio : {}", ubicacio);
        if (ubicacio.getId() != null) {
            throw new BadRequestAlertException("A new ubicacio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ubicacio result = ubicacioRepository.save(ubicacio);
        return ResponseEntity.created(new URI("/api/ubicacios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ubicacios} : Updates an existing ubicacio.
     *
     * @param ubicacio the ubicacio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ubicacio,
     * or with status {@code 400 (Bad Request)} if the ubicacio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ubicacio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ubicacios")
    public ResponseEntity<Ubicacio> updateUbicacio(@RequestBody Ubicacio ubicacio) throws URISyntaxException {
        log.debug("REST request to update Ubicacio : {}", ubicacio);
        if (ubicacio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ubicacio result = ubicacioRepository.save(ubicacio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ubicacio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ubicacios} : get all the ubicacios.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ubicacios in body.
     */
    @GetMapping("/ubicacios")
    public List<Ubicacio> getAllUbicacios(@RequestParam(required = false) String filter) {
        if ("client-is-null".equals(filter)) {
            log.debug("REST request to get all Ubicacios where client is null");
            return StreamSupport
                .stream(ubicacioRepository.findAll().spliterator(), false)
                .filter(ubicacio -> ubicacio.getClient() == null)
                .collect(Collectors.toList());
        }
        if ("venedor-is-null".equals(filter)) {
            log.debug("REST request to get all Ubicacios where venedor is null");
            return StreamSupport
                .stream(ubicacioRepository.findAll().spliterator(), false)
                .filter(ubicacio -> ubicacio.getVenedor() == null)
                .collect(Collectors.toList());
        }
        if ("venedor-is-null".equals(filter)) {
            log.debug("REST request to get all Ubicacios where venedor is null");
            return StreamSupport
                .stream(ubicacioRepository.findAll().spliterator(), false)
                .filter(ubicacio -> ubicacio.getVenedor() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Ubicacios");
        return ubicacioRepository.findAll();
    }

    /**
     * {@code GET  /ubicacios/:id} : get the "id" ubicacio.
     *
     * @param id the id of the ubicacio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ubicacio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ubicacios/{id}")
    public ResponseEntity<Ubicacio> getUbicacio(@PathVariable Long id) {
        log.debug("REST request to get Ubicacio : {}", id);
        Optional<Ubicacio> ubicacio = ubicacioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ubicacio);
    }

    /**
     * {@code DELETE  /ubicacios/:id} : delete the "id" ubicacio.
     *
     * @param id the id of the ubicacio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ubicacios/{id}")
    public ResponseEntity<Void> deleteUbicacio(@PathVariable Long id) {
        log.debug("REST request to delete Ubicacio : {}", id);
        ubicacioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
