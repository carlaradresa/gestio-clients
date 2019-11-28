package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.Venedor;
import com.serviemporda.gestioclients.repository.VenedorRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.Venedor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VenedorResource {

    private final Logger log = LoggerFactory.getLogger(VenedorResource.class);

    private static final String ENTITY_NAME = "venedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VenedorRepository venedorRepository;

    public VenedorResource(VenedorRepository venedorRepository) {
        this.venedorRepository = venedorRepository;
    }

    /**
     * {@code POST  /venedors} : Create a new venedor.
     *
     * @param venedor the venedor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venedor, or with status {@code 400 (Bad Request)} if the venedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venedors")
    public ResponseEntity<Venedor> createVenedor(@RequestBody Venedor venedor) throws URISyntaxException {
        log.debug("REST request to save Venedor : {}", venedor);
        if (venedor.getId() != null) {
            throw new BadRequestAlertException("A new venedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Venedor result = venedorRepository.save(venedor);
        return ResponseEntity.created(new URI("/api/venedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /venedors} : Updates an existing venedor.
     *
     * @param venedor the venedor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venedor,
     * or with status {@code 400 (Bad Request)} if the venedor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venedor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/venedors")
    public ResponseEntity<Venedor> updateVenedor(@RequestBody Venedor venedor) throws URISyntaxException {
        log.debug("REST request to update Venedor : {}", venedor);
        if (venedor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Venedor result = venedorRepository.save(venedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venedor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /venedors} : get all the venedors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of venedors in body.
     */
    @GetMapping("/venedors")
    public List<Venedor> getAllVenedors() {
        log.debug("REST request to get all Venedors");
        return venedorRepository.findAll();
    }

    /**
     * {@code GET  /venedors/:id} : get the "id" venedor.
     *
     * @param id the id of the venedor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venedor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/venedors/{id}")
    public ResponseEntity<Venedor> getVenedor(@PathVariable Long id) {
        log.debug("REST request to get Venedor : {}", id);
        Optional<Venedor> venedor = venedorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(venedor);
    }

    /**
     * {@code DELETE  /venedors/:id} : delete the "id" venedor.
     *
     * @param id the id of the venedor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/venedors/{id}")
    public ResponseEntity<Void> deleteVenedor(@PathVariable Long id) {
        log.debug("REST request to delete Venedor : {}", id);
        venedorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
