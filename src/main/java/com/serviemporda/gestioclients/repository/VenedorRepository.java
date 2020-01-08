package com.serviemporda.gestioclients.repository;

import com.serviemporda.gestioclients.domain.Venedor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Venedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VenedorRepository extends JpaRepository<Venedor, Long> {

}
