package com.serviemporda.gestioclients.repository;

import com.serviemporda.gestioclients.domain.Treballador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Treballador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreballadorRepository extends JpaRepository<Treballador, Long> {

}
