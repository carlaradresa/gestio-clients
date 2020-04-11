package com.serviemporda.gestioclients.repository;

import com.serviemporda.gestioclients.domain.PeriodicitatSetmanal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PeriodicitatSetmanal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodicitatSetmanalRepository extends JpaRepository<PeriodicitatSetmanal, Long> {
}
