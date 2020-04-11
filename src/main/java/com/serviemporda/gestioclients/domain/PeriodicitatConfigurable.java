package com.serviemporda.gestioclients.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.serviemporda.gestioclients.domain.enumeration.Periodicitat;

/**
 * A PeriodicitatConfigurable.
 */
@Entity
@Table(name = "periodicitat_configurable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PeriodicitatConfigurable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frequencia")
    private Integer frequencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicitat")
    private Periodicitat periodicitat;

    @Column(name = "observacions")
    private String observacions;

    @OneToOne(mappedBy = "periodicitatConfigurable")
    @JsonIgnore
    private PlantillaFeina plantilla;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public PeriodicitatConfigurable frequencia(Integer frequencia) {
        this.frequencia = frequencia;
        return this;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Periodicitat getPeriodicitat() {
        return periodicitat;
    }

    public PeriodicitatConfigurable periodicitat(Periodicitat periodicitat) {
        this.periodicitat = periodicitat;
        return this;
    }

    public void setPeriodicitat(Periodicitat periodicitat) {
        this.periodicitat = periodicitat;
    }

    public String getObservacions() {
        return observacions;
    }

    public PeriodicitatConfigurable observacions(String observacions) {
        this.observacions = observacions;
        return this;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public PlantillaFeina getPlantilla() {
        return plantilla;
    }

    public PeriodicitatConfigurable plantilla(PlantillaFeina plantillaFeina) {
        this.plantilla = plantillaFeina;
        return this;
    }

    public void setPlantilla(PlantillaFeina plantillaFeina) {
        this.plantilla = plantillaFeina;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodicitatConfigurable)) {
            return false;
        }
        return id != null && id.equals(((PeriodicitatConfigurable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PeriodicitatConfigurable{" +
            "id=" + getId() +
            ", frequencia=" + getFrequencia() +
            ", periodicitat='" + getPeriodicitat() + "'" +
            ", observacions='" + getObservacions() + "'" +
            "}";
    }
}
