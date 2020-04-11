package com.serviemporda.gestioclients.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "nom")
    private String nom;

    @Column(name = "hora_inici")
    private Instant horaInici;

    @Column(name = "hora_final")
    private Instant horaFinal;

    @Column(name = "temps_previst")
    private Instant tempsPrevist;

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

    @OneToOne
    @JoinColumn(unique = true)
    private PeriodicitatConfigurable periodicitatConfigurable;

    @ManyToOne
    @JsonIgnoreProperties("plantillaFeinas")
    private Client client;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "plantilla_feina_periodicitat_setmanal",
               joinColumns = @JoinColumn(name = "plantilla_feina_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "periodicitat_setmanal_id", referencedColumnName = "id"))
    private Set<PeriodicitatSetmanal> periodicitatSetmanals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "plantilla_feina_treballador",
               joinColumns = @JoinColumn(name = "plantilla_feina_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "treballador_id", referencedColumnName = "id"))
    private Set<Treballador> treballadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public PlantillaFeina nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public Instant getTempsPrevist() {
        return tempsPrevist;
    }

    public PlantillaFeina tempsPrevist(Instant tempsPrevist) {
        this.tempsPrevist = tempsPrevist;
        return this;
    }

    public void setTempsPrevist(Instant tempsPrevist) {
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

    public PeriodicitatConfigurable getPeriodicitatConfigurable() {
        return periodicitatConfigurable;
    }

    public PlantillaFeina periodicitatConfigurable(PeriodicitatConfigurable periodicitatConfigurable) {
        this.periodicitatConfigurable = periodicitatConfigurable;
        return this;
    }

    public void setPeriodicitatConfigurable(PeriodicitatConfigurable periodicitatConfigurable) {
        this.periodicitatConfigurable = periodicitatConfigurable;
    }

    public Client getClient() {
        return client;
    }

    public PlantillaFeina client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<PeriodicitatSetmanal> getPeriodicitatSetmanals() {
        return periodicitatSetmanals;
    }

    public PlantillaFeina periodicitatSetmanals(Set<PeriodicitatSetmanal> periodicitatSetmanals) {
        this.periodicitatSetmanals = periodicitatSetmanals;
        return this;
    }

    public PlantillaFeina addPeriodicitatSetmanal(PeriodicitatSetmanal periodicitatSetmanal) {
        this.periodicitatSetmanals.add(periodicitatSetmanal);
        periodicitatSetmanal.getPlantillas().add(this);
        return this;
    }

    public PlantillaFeina removePeriodicitatSetmanal(PeriodicitatSetmanal periodicitatSetmanal) {
        this.periodicitatSetmanals.remove(periodicitatSetmanal);
        periodicitatSetmanal.getPlantillas().remove(this);
        return this;
    }

    public void setPeriodicitatSetmanals(Set<PeriodicitatSetmanal> periodicitatSetmanals) {
        this.periodicitatSetmanals = periodicitatSetmanals;
    }

    public Set<Treballador> getTreballadors() {
        return treballadors;
    }

    public PlantillaFeina treballadors(Set<Treballador> treballadors) {
        this.treballadors = treballadors;
        return this;
    }

    public PlantillaFeina addTreballador(Treballador treballador) {
        this.treballadors.add(treballador);
        treballador.getPlantillafeinas().add(this);
        return this;
    }

    public PlantillaFeina removeTreballador(Treballador treballador) {
        this.treballadors.remove(treballador);
        treballador.getPlantillafeinas().remove(this);
        return this;
    }

    public void setTreballadors(Set<Treballador> treballadors) {
        this.treballadors = treballadors;
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
            ", nom='" + getNom() + "'" +
            ", horaInici='" + getHoraInici() + "'" +
            ", horaFinal='" + getHoraFinal() + "'" +
            ", tempsPrevist='" + getTempsPrevist() + "'" +
            ", facturacioAutomatica='" + isFacturacioAutomatica() + "'" +
            ", observacions='" + getObservacions() + "'" +
            ", setmanaInicial='" + getSetmanaInicial() + "'" +
            ", setmanaFinal='" + getSetmanaFinal() + "'" +
            ", numeroControl=" + getNumeroControl() +
            "}";
    }
}
