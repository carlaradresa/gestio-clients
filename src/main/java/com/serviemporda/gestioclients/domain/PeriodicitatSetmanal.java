package com.serviemporda.gestioclients.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.serviemporda.gestioclients.domain.enumeration.Dia;

/**
 * A PeriodicitatSetmanal.
 */
@Entity
@Table(name = "periodicitat_setmanal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PeriodicitatSetmanal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_setmana")
    private Dia diaSetmana;

    @ManyToMany(mappedBy = "periodicitatSetmanals")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<PlantillaFeina> plantillas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dia getDiaSetmana() {
        return diaSetmana;
    }

    public PeriodicitatSetmanal diaSetmana(Dia diaSetmana) {
        this.diaSetmana = diaSetmana;
        return this;
    }

    public void setDiaSetmana(Dia diaSetmana) {
        this.diaSetmana = diaSetmana;
    }

    public Set<PlantillaFeina> getPlantillas() {
        return plantillas;
    }

    public PeriodicitatSetmanal plantillas(Set<PlantillaFeina> plantillaFeinas) {
        this.plantillas = plantillaFeinas;
        return this;
    }

    public PeriodicitatSetmanal addPlantilla(PlantillaFeina plantillaFeina) {
        this.plantillas.add(plantillaFeina);
        plantillaFeina.getPeriodicitatSetmanals().add(this);
        return this;
    }

    public PeriodicitatSetmanal removePlantilla(PlantillaFeina plantillaFeina) {
        this.plantillas.remove(plantillaFeina);
        plantillaFeina.getPeriodicitatSetmanals().remove(this);
        return this;
    }

    public void setPlantillas(Set<PlantillaFeina> plantillaFeinas) {
        this.plantillas = plantillaFeinas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodicitatSetmanal)) {
            return false;
        }
        return id != null && id.equals(((PeriodicitatSetmanal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PeriodicitatSetmanal{" +
            "id=" + getId() +
            ", diaSetmana='" + getDiaSetmana() + "'" +
            "}";
    }
}
