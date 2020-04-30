package com.serviemporda.gestioclients.service;

import com.serviemporda.gestioclients.domain.PlantillaFeina;
import com.serviemporda.gestioclients.repository.AuthorityRepository;
import com.serviemporda.gestioclients.repository.FeinaRepository;
import com.serviemporda.gestioclients.repository.PlantillaFeinaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class PlantillaFeinaService {

    private final Logger log = LoggerFactory.getLogger(FeinaService.class);

    private final PlantillaFeinaRepository plantillaFeinaRepository;

    private FeinaRepository feinaRepository;

    private final CacheManager cacheManager;

    private final AuthorityRepository authorityRepository;

    public PlantillaFeinaService(PlantillaFeinaRepository plantillaFeinaRepository, FeinaRepository feinaRepository, CacheManager cacheManager, AuthorityRepository authorityRepository) {
        this.plantillaFeinaRepository = plantillaFeinaRepository;
        this.feinaRepository = feinaRepository;
        this.cacheManager = cacheManager;
        this.authorityRepository = authorityRepository;
    }




    //DE MOMENT NO SERVEIX PER RES!!!!!!!!!!!!!




}
