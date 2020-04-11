package com.serviemporda.gestioclients.repository;

import com.serviemporda.gestioclients.domain.Feina;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Feina entity.
 */
@Repository
public interface FeinaRepository extends JpaRepository<Feina, Long> {

    @Query(value = "select distinct feina from Feina feina left join fetch feina.treballadors left join fetch feina.ubicacios",
        countQuery = "select count(distinct feina) from Feina feina")
    Page<Feina> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct feina from Feina feina left join fetch feina.treballadors left join fetch feina.ubicacios")
    List<Feina> findAllWithEagerRelationships();

    @Query("select feina from Feina feina left join fetch feina.treballadors left join fetch feina.ubicacios where feina.id =:id")
    Optional<Feina> findOneWithEagerRelationships(@Param("id") Long id);
}
