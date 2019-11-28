package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.Control;
import com.serviemporda.gestioclients.repository.ControlRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.Control}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ControlResource {

    private final Logger log = LoggerFactory.getLogger(ControlResource.class);

    private static final String ENTITY_NAME = "control";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControlRepository controlRepository;

    public ControlResource(ControlRepository controlRepository) {
        this.controlRepository = controlRepository;
    }

    /**
     * {@code POST  /controls} : Create a new control.
     *
     * @param control the control to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new control, or with status {@code 400 (Bad Request)} if the control has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/controls")
    public ResponseEntity<Control> createControl(@RequestBody Control control) throws URISyntaxException {
        log.debug("REST request to save Control : {}", control);
        if (control.getId() != null) {
            throw new BadRequestAlertException("A new control cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Control result = controlRepository.save(control);
        return ResponseEntity.created(new URI("/api/controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controls} : Updates an existing control.
     *
     * @param control the control to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated control,
     * or with status {@code 400 (Bad Request)} if the control is not valid,
     * or with status {@code 500 (Internal Server Error)} if the control couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/controls")
    public ResponseEntity<Control> updateControl(@RequestBody Control control) throws URISyntaxException {
        log.debug("REST request to update Control : {}", control);
        if (control.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Control result = controlRepository.save(control);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, control.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /controls} : get all the controls.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controls in body.
     */
    @GetMapping("/controls")
    public List<Control> getAllControls() {
        log.debug("REST request to get all Controls");
        return controlRepository.findAll();
    }

    /**
     * {@code GET  /controls/:id} : get the "id" control.
     *
     * @param id the id of the control to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the control, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/controls/{id}")
    public ResponseEntity<Control> getControl(@PathVariable Long id) {
        log.debug("REST request to get Control : {}", id);
        Optional<Control> control = controlRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(control);
    }

    /**
     * {@code DELETE  /controls/:id} : delete the "id" control.
     *
     * @param id the id of the control to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/controls/{id}")
    public ResponseEntity<Void> deleteControl(@PathVariable Long id) {
        log.debug("REST request to delete Control : {}", id);
        controlRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
