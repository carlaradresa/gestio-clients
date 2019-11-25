package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.Marcatge;
import com.serviemporda.gestioclients.repository.MarcatgeRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.Marcatge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MarcatgeResource {

    private final Logger log = LoggerFactory.getLogger(MarcatgeResource.class);

    private static final String ENTITY_NAME = "marcatge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarcatgeRepository marcatgeRepository;

    public MarcatgeResource(MarcatgeRepository marcatgeRepository) {
        this.marcatgeRepository = marcatgeRepository;
    }

    /**
     * {@code POST  /marcatges} : Create a new marcatge.
     *
     * @param marcatge the marcatge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marcatge, or with status {@code 400 (Bad Request)} if the marcatge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marcatges")
    public ResponseEntity<Marcatge> createMarcatge(@RequestBody Marcatge marcatge) throws URISyntaxException {
        log.debug("REST request to save Marcatge : {}", marcatge);
        if (marcatge.getId() != null) {
            throw new BadRequestAlertException("A new marcatge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Marcatge result = marcatgeRepository.save(marcatge);
        return ResponseEntity.created(new URI("/api/marcatges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marcatges} : Updates an existing marcatge.
     *
     * @param marcatge the marcatge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marcatge,
     * or with status {@code 400 (Bad Request)} if the marcatge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marcatge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marcatges")
    public ResponseEntity<Marcatge> updateMarcatge(@RequestBody Marcatge marcatge) throws URISyntaxException {
        log.debug("REST request to update Marcatge : {}", marcatge);
        if (marcatge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Marcatge result = marcatgeRepository.save(marcatge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, marcatge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /marcatges} : get all the marcatges.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marcatges in body.
     */
    @GetMapping("/marcatges")
    public List<Marcatge> getAllMarcatges() {
        log.debug("REST request to get all Marcatges");
        return marcatgeRepository.findAll();
    }

    /**
     * {@code GET  /marcatges/:id} : get the "id" marcatge.
     *
     * @param id the id of the marcatge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marcatge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marcatges/{id}")
    public ResponseEntity<Marcatge> getMarcatge(@PathVariable Long id) {
        log.debug("REST request to get Marcatge : {}", id);
        Optional<Marcatge> marcatge = marcatgeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(marcatge);
    }

    /**
     * {@code DELETE  /marcatges/:id} : delete the "id" marcatge.
     *
     * @param id the id of the marcatge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marcatges/{id}")
    public ResponseEntity<Void> deleteMarcatge(@PathVariable Long id) {
        log.debug("REST request to delete Marcatge : {}", id);
        marcatgeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
