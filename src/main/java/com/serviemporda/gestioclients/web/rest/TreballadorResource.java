package com.serviemporda.gestioclients.web.rest;

import com.serviemporda.gestioclients.domain.Treballador;
import com.serviemporda.gestioclients.repository.TreballadorRepository;
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
 * REST controller for managing {@link com.serviemporda.gestioclients.domain.Treballador}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TreballadorResource {

    private final Logger log = LoggerFactory.getLogger(TreballadorResource.class);

    private static final String ENTITY_NAME = "treballador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TreballadorRepository treballadorRepository;

    public TreballadorResource(TreballadorRepository treballadorRepository) {
        this.treballadorRepository = treballadorRepository;
    }

    /**
     * {@code POST  /treballadors} : Create a new treballador.
     *
     * @param treballador the treballador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new treballador, or with status {@code 400 (Bad Request)} if the treballador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/treballadors")
    public ResponseEntity<Treballador> createTreballador(@RequestBody Treballador treballador) throws URISyntaxException {
        log.debug("REST request to save Treballador : {}", treballador);
        if (treballador.getId() != null) {
            throw new BadRequestAlertException("A new treballador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Treballador result = treballadorRepository.save(treballador);
        return ResponseEntity.created(new URI("/api/treballadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /treballadors} : Updates an existing treballador.
     *
     * @param treballador the treballador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated treballador,
     * or with status {@code 400 (Bad Request)} if the treballador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the treballador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/treballadors")
    public ResponseEntity<Treballador> updateTreballador(@RequestBody Treballador treballador) throws URISyntaxException {
        log.debug("REST request to update Treballador : {}", treballador);
        if (treballador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Treballador result = treballadorRepository.save(treballador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, treballador.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /treballadors} : get all the treballadors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of treballadors in body.
     */
    @GetMapping("/treballadors")
    public List<Treballador> getAllTreballadors() {
        log.debug("REST request to get all Treballadors");
        return treballadorRepository.findAll();
    }

    /**
     * {@code GET  /treballadors/:id} : get the "id" treballador.
     *
     * @param id the id of the treballador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the treballador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/treballadors/{id}")
    public ResponseEntity<Treballador> getTreballador(@PathVariable Long id) {
        log.debug("REST request to get Treballador : {}", id);
        Optional<Treballador> treballador = treballadorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(treballador);
    }

    /**
     * {@code DELETE  /treballadors/:id} : delete the "id" treballador.
     *
     * @param id the id of the treballador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/treballadors/{id}")
    public ResponseEntity<Void> deleteTreballador(@PathVariable Long id) {
        log.debug("REST request to delete Treballador : {}", id);
        treballadorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
