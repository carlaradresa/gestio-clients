package com.serviemporda.gestioclients.service;

import com.serviemporda.gestioclients.domain.Feina;
import com.serviemporda.gestioclients.domain.PeriodicitatSetmanal;
import com.serviemporda.gestioclients.domain.PlantillaFeina;
import com.serviemporda.gestioclients.domain.enumeration.Estat;
import com.serviemporda.gestioclients.repository.AuthorityRepository;
import com.serviemporda.gestioclients.repository.FeinaRepository;
import com.serviemporda.gestioclients.repository.PlantillaFeinaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class FeinaService {

    private final Logger log = LoggerFactory.getLogger(FeinaService.class);

    private final FeinaRepository feinaRepository;

    private final PlantillaFeinaRepository plantillaFeinaRepository;

    private final CacheManager cacheManager;

    private final AuthorityRepository authorityRepository;

    ArrayList<Feina> feinesNoves = new ArrayList<>();                //si generem més d'una feina

    Duration duration;

    public FeinaService(FeinaRepository feinaRepository, PlantillaFeinaRepository plantillaFeinaRepository, CacheManager cacheManager, AuthorityRepository authorityRepository) {
        this.feinaRepository = feinaRepository;
        this.plantillaFeinaRepository = plantillaFeinaRepository;
        this.cacheManager = cacheManager;
        this.authorityRepository = authorityRepository;
    }

    public void deleteFeinesFromPf(Long id_plantillaFeina){

        List<Feina> feines;

        feines = feinaRepository.findAllWithEagerRelationships();

        for (int i = 0; i < feines.size(); i ++){
            if ((feines.get(i).getPlantillaFeina().getId().equals(id_plantillaFeina)) && !(feines.get(i).getEstat().equals("FINALITZAT"))){
                feinaRepository.delete(feines.get(i));
            }
        }
    }

    public ArrayList<Feina> createFeina(PlantillaFeina result) {

        Feina feinaNova = new Feina();

        //VALORS IGUALS PER TOTES LES FEINES
        LocalDate start = result.getSetmanaInicial();
        LocalDate end = result.getSetmanaFinal();

        Integer freqPeriod = result.getPeriodicitatConfigurable().getFrequencia();
        String period = result.getPeriodicitatConfigurable().getPeriodicitat().name();

        LocalTime hora_inici = result.getHoraInici();
        LocalTime hora_final = result.getHoraFinal();

        //Temps previst per la feina

        duration = Duration.between(hora_inici, hora_final);

        //Comprovem si només es 1 feina o més
        if (start.compareTo(end) == 0){
            saveFeina(result.getSetmanaInicial(),result,duration);
        }else{

            ArrayList<String> diesSetmana = new ArrayList<>();
            boolean isQualsevol = false;

            Set<PeriodicitatSetmanal> periodSet = result.getPeriodicitatSetmanals();
            Iterator<PeriodicitatSetmanal> it = periodSet.iterator();
            while (it.hasNext()){
                if (it.next().getDiaSetmana().toString().equalsIgnoreCase("QUALSEVOL")){
                    isQualsevol = true;
                }else{
                    String diaAdd = convertCatalaInEnglish(it.next().getDiaSetmana().toString());
                    diesSetmana.add(diaAdd);
                }
            }

            if ((diesSetmana.size() == 1) || isQualsevol) {
            //Només ha seleccionat 1 dia, per tant mirem periodconfiguracio

                boolean trobat = false;
                LocalDate dateToCompare;

                while (!trobat){
                    dateToCompare = start;

                    //Mirem si ha escollit qualsevol dia
                    if (isQualsevol){
/*
                    //CREA FEINES AMB QUALSEVOL DIA
                        creaFeinesDiaQualsevol(dateToCompare,result,duration);
                        saveFeina(dateToCompare,result,duration);
                        if (period.equalsIgnoreCase("SETMANA")){
                            dateToCompare = dateToCompare.plusDays(freqPeriod);

                        }
*/
                    }
                    if (dateToCompare.getDayOfWeek().toString().equals(diesSetmana.get(0))){
                        //mirem la periodicitat configurable i creem les feines
                        saveFeina(dateToCompare,result,duration);
                        if (period.equalsIgnoreCase("DIA")){
                            while ((dateToCompare.isBefore(end)) || (dateToCompare.compareTo(end) == 0)){
                                saveFeina(dateToCompare,result,duration);
                                dateToCompare = dateToCompare.plusDays(freqPeriod);
                            }
                        }else{

                            if(period.equalsIgnoreCase("SETMANA")){
                                dateToCompare = dateToCompare.plusWeeks(freqPeriod);
                                while ((dateToCompare.isBefore(end)) || (dateToCompare.compareTo(end) == 0)){
                                    saveFeina(dateToCompare,result,duration);
                                    dateToCompare = dateToCompare.plusWeeks(freqPeriod);
                                }
                            }else{
                                if (period.equalsIgnoreCase("QUINZENAL")){
                                    dateToCompare = dateToCompare.plusDays(15*freqPeriod);
                                    while ((dateToCompare.isBefore(end)) || (dateToCompare.compareTo(end) == 0)){
                                        saveFeina(dateToCompare,result,duration);
                                        dateToCompare = dateToCompare.plusDays(15*freqPeriod);
                                    }
                                }else {
                                    if (period.equalsIgnoreCase("MES")){
                                        dateToCompare = dateToCompare.plusMonths(freqPeriod);
                                        while ((dateToCompare.isBefore(end)) || (dateToCompare.compareTo(end) == 0)){
                                            saveFeina(dateToCompare,result,duration);
                                            dateToCompare = dateToCompare.plusMonths(freqPeriod);
                                        }
                                    }else{
                                        dateToCompare = dateToCompare.plusYears(freqPeriod);
                                        while ((dateToCompare.isBefore(end)) || (dateToCompare.compareTo(end) == 0)){
                                            saveFeina(dateToCompare,result,duration);
                                            dateToCompare = dateToCompare.plusYears(freqPeriod);
                                        }
                                    }
                                }
                            }
                        }
                        trobat = true;
                       // break;
                    }
                    dateToCompare = dateToCompare.plusDays(1);
                }
            }else{ //ha seleccionat més d'1 dia, per tant no fa falta mirar periodConfig

                LocalDate dateToCompare = start;

                //Mirem si ha escollit SETMANA si ha escollit 1 o més per sumar-lis.
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int weekNumber = dateToCompare.get(weekFields.weekOfWeekBasedYear());
                int weekNumber2 = 0;

                while ((dateToCompare.isBefore(end)) || (dateToCompare.compareTo(end) == 0)){
                    for (String dayOfWeek : diesSetmana) {
                        if (dateToCompare.getDayOfWeek().toString().equals(dayOfWeek)){
                            saveFeina(dateToCompare,result,duration);
                        }
                    }
                    dateToCompare = dateToCompare.plusDays(1);
                    weekFields = WeekFields.of(Locale.getDefault());
                    weekNumber2 = dateToCompare.get(weekFields.weekOfWeekBasedYear());

                    if (weekNumber<weekNumber2 && period.equalsIgnoreCase("SETMANA")){
                        dateToCompare = dateToCompare.plusWeeks(freqPeriod-1);
                        weekFields = WeekFields.of(Locale.getDefault());
                        weekNumber = dateToCompare.get(weekFields.weekOfWeekBasedYear());
                        weekNumber2 = weekNumber;
                    }

                }
            }
        }
       // this.clearUserCaches(user);
        log.debug("Created Information for Feina: {}", feinaNova);
        return feinesNoves;
    }

    private String convertCatalaInEnglish(String dayInEnglish) {
        String dayChanged = "";
        if (dayInEnglish.equalsIgnoreCase("DILLUNS")){
            dayChanged = "MONDAY";
        }else{
            if (dayInEnglish.equalsIgnoreCase("DIMARTS")){
                dayChanged = "TUESDAY";
            }else{
                if (dayInEnglish.equalsIgnoreCase("DIMECRES")){
                    dayChanged = "WEDNESDAY";
                }else{
                    if (dayInEnglish.equalsIgnoreCase("DIJOUS")){
                        dayChanged = "THURSDAY";
                    }else{
                        if (dayInEnglish.equalsIgnoreCase("DIVENDRES")){
                            dayChanged = "FRIDAY";
                        }else{
                            if (dayInEnglish.equalsIgnoreCase("DISSABTE")){
                                dayChanged = "SATURDAY";
                            }else{
                                if (dayInEnglish.equalsIgnoreCase("DIUMENGE")){
                                    dayChanged = "SUNDAY";
                                }
                            }
                        }
                    }
                }
            }
        }
        return dayChanged;
    }

    private void saveFeina( LocalDate start, PlantillaFeina result, Duration duration) {

        Feina feinaNova = new Feina();

        feinaNova.setNom(result.getObservacions());
        feinaNova.setDescripcio("");
        feinaNova.setSetmana(start);
        feinaNova.setTempsPrevist((int)duration.toHours());
        feinaNova.setIntervalControl(result.getNumeroControl());
        feinaNova.setFacturacioAutomatica(result.isFacturacioAutomatica());
        feinaNova.setPlantillaFeina(result);
        feinaNova.setObservacions(result.getObservacions());
        feinaNova.setEstat(Estat.INACTIU);
        feinaNova.setUbicacios(null);
        feinaNova.setCategoria(null);
        feinaNova.setTreballadors(null);
        feinaNova.setClient(null);

        feinesNoves.add(feinaNova);
        feinaRepository.save(feinaNova);
    }
}
