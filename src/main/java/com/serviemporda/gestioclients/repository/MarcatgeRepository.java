package com.serviemporda.gestioclients.repository;

import com.serviemporda.gestioclients.domain.Marcatge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Marcatge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarcatgeRepository extends JpaRepository<Marcatge, Long> {

}
