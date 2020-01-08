package com.serviemporda.gestioclients.repository;

import com.serviemporda.gestioclients.domain.PeriodicitatConfigurable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PeriodicitatConfigurable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodicitatConfigurableRepository extends JpaRepository<PeriodicitatConfigurable, Long> {

}
