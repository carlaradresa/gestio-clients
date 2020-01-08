package com.serviemporda.gestioclients.repository;

import com.serviemporda.gestioclients.domain.Ubicacio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ubicacio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UbicacioRepository extends JpaRepository<Ubicacio, Long> {

}
