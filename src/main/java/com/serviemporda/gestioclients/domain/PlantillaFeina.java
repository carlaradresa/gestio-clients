package com.serviemporda.gestioclients.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Duration;

import com.serviemporda.gestioclients.domain.enumeration.Dia;

import com.serviemporda.gestioclients.domain.enumeration.Periodicitat;

/**
 * A PlantillaFeina.
 */
@Entity
@Table(name = "plantilla_feina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlantillaFeina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia")
    private Dia dia;

    @Column(name = "hora_inici")
    private Instant horaInici;

    @Column(name = "hora_final")
    private Instant horaFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicitat")
    private Periodicitat periodicitat;

    @Column(name = "temps_previst")
    private Duration tempsPrevist;

    @Column(name = "facturacio_automatica")
    private Boolean facturacioAutomatica;

    @Column(name = "observacions")
    private String observacions;

    @Column(name = "setmana_inicial")
    private LocalDate setmanaInicial;

    @Column(name = "setmana_final")
    private LocalDate setmanaFinal;

    @Column(name = "numero_control")
    private Integer numeroControl;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dia getDia() {
        return dia;
    }

    public PlantillaFeina dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Instant getHoraInici() {
        return horaInici;
    }

    public PlantillaFeina horaInici(Instant horaInici) {
        this.horaInici = horaInici;
        return this;
    }

    public void setHoraInici(Instant horaInici) {
        this.horaInici = horaInici;
    }

    public Instant getHoraFinal() {
        return horaFinal;
    }

    public PlantillaFeina horaFinal(Instant horaFinal) {
        this.horaFinal = horaFinal;
        return this;
    }

    public void setHoraFinal(Instant horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Periodicitat getPeriodicitat() {
        return periodicitat;
    }

    public PlantillaFeina periodicitat(Periodicitat periodicitat) {
        this.periodicitat = periodicitat;
        return this;
    }

    public void setPeriodicitat(Periodicitat periodicitat) {
        this.periodicitat = periodicitat;
    }

    public Duration getTempsPrevist() {
        return tempsPrevist;
    }

    public PlantillaFeina tempsPrevist(Duration tempsPrevist) {
        this.tempsPrevist = tempsPrevist;
        return this;
    }

    public void setTempsPrevist(Duration tempsPrevist) {
        this.tempsPrevist = tempsPrevist;
    }

    public Boolean isFacturacioAutomatica() {
        return facturacioAutomatica;
    }

    public PlantillaFeina facturacioAutomatica(Boolean facturacioAutomatica) {
        this.facturacioAutomatica = facturacioAutomatica;
        return this;
    }

    public void setFacturacioAutomatica(Boolean facturacioAutomatica) {
        this.facturacioAutomatica = facturacioAutomatica;
    }

    public String getObservacions() {
        return observacions;
    }

    public PlantillaFeina observacions(String observacions) {
        this.observacions = observacions;
        return this;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public LocalDate getSetmanaInicial() {
        return setmanaInicial;
    }

    public PlantillaFeina setmanaInicial(LocalDate setmanaInicial) {
        this.setmanaInicial = setmanaInicial;
        return this;
    }

    public void setSetmanaInicial(LocalDate setmanaInicial) {
        this.setmanaInicial = setmanaInicial;
    }

    public LocalDate getSetmanaFinal() {
        return setmanaFinal;
    }

    public PlantillaFeina setmanaFinal(LocalDate setmanaFinal) {
        this.setmanaFinal = setmanaFinal;
        return this;
    }

    public void setSetmanaFinal(LocalDate setmanaFinal) {
        this.setmanaFinal = setmanaFinal;
    }

    public Integer getNumeroControl() {
        return numeroControl;
    }

    public PlantillaFeina numeroControl(Integer numeroControl) {
        this.numeroControl = numeroControl;
        return this;
    }

    public void setNumeroControl(Integer numeroControl) {
        this.numeroControl = numeroControl;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlantillaFeina)) {
            return false;
        }
        return id != null && id.equals(((PlantillaFeina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlantillaFeina{" +
            "id=" + getId() +
            ", dia='" + getDia() + "'" +
            ", horaInici='" + getHoraInici() + "'" +
            ", horaFinal='" + getHoraFinal() + "'" +
            ", periodicitat='" + getPeriodicitat() + "'" +
            ", tempsPrevist='" + getTempsPrevist() + "'" +
            ", facturacioAutomatica='" + isFacturacioAutomatica() + "'" +
            ", observacions='" + getObservacions() + "'" +
            ", setmanaInicial='" + getSetmanaInicial() + "'" +
            ", setmanaFinal='" + getSetmanaFinal() + "'" +
            ", numeroControl=" + getNumeroControl() +
            "}";
    }
}
