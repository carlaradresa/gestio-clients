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

import com.serviemporda.gestioclients.domain.enumeration.Estat;

/**
 * A Feina.
 */
@Entity
@Table(name = "feina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "setmana")
    private LocalDate setmana;

    @Column(name = "temps_previst")
    private Instant tempsPrevist;

    @Column(name = "temps_real")
    private Instant tempsReal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estat")
    private Estat estat;

    @Column(name = "interval_control")
    private Integer intervalControl;

    @Column(name = "facturacio_automatica")
    private Boolean facturacioAutomatica;

    @Column(name = "observacions")
    private String observacions;

    @Column(name = "comentaris_treballador")
    private String comentarisTreballador;

    @ManyToOne
    @JsonIgnoreProperties("feinas")
    private PlantillaFeina plantillaFeina;

    @ManyToOne
    @JsonIgnoreProperties("feinas")
    private Categoria categoria;

    @ManyToOne
    @JsonIgnoreProperties("feinas")
    private Client client;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "feina_treballador",
               joinColumns = @JoinColumn(name = "feina_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "treballador_id", referencedColumnName = "id"))
    private Set<Treballador> treballadors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "feina_ubicacio",
               joinColumns = @JoinColumn(name = "feina_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ubicacio_id", referencedColumnName = "id"))
    private Set<Ubicacio> ubicacios = new HashSet<>();

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

    public Feina nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public Feina descripcio(String descripcio) {
        this.descripcio = descripcio;
        return this;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public LocalDate getSetmana() {
        return setmana;
    }

    public Feina setmana(LocalDate setmana) {
        this.setmana = setmana;
        return this;
    }

    public void setSetmana(LocalDate setmana) {
        this.setmana = setmana;
    }

    public Instant getTempsPrevist() {
        return tempsPrevist;
    }

    public Feina tempsPrevist(Instant tempsPrevist) {
        this.tempsPrevist = tempsPrevist;
        return this;
    }

    public void setTempsPrevist(Instant tempsPrevist) {
        this.tempsPrevist = tempsPrevist;
    }

    public Instant getTempsReal() {
        return tempsReal;
    }

    public Feina tempsReal(Instant tempsReal) {
        this.tempsReal = tempsReal;
        return this;
    }

    public void setTempsReal(Instant tempsReal) {
        this.tempsReal = tempsReal;
    }

    public Estat getEstat() {
        return estat;
    }

    public Feina estat(Estat estat) {
        this.estat = estat;
        return this;
    }

    public void setEstat(Estat estat) {
        this.estat = estat;
    }

    public Integer getIntervalControl() {
        return intervalControl;
    }

    public Feina intervalControl(Integer intervalControl) {
        this.intervalControl = intervalControl;
        return this;
    }

    public void setIntervalControl(Integer intervalControl) {
        this.intervalControl = intervalControl;
    }

    public Boolean isFacturacioAutomatica() {
        return facturacioAutomatica;
    }

    public Feina facturacioAutomatica(Boolean facturacioAutomatica) {
        this.facturacioAutomatica = facturacioAutomatica;
        return this;
    }

    public void setFacturacioAutomatica(Boolean facturacioAutomatica) {
        this.facturacioAutomatica = facturacioAutomatica;
    }

    public String getObservacions() {
        return observacions;
    }

    public Feina observacions(String observacions) {
        this.observacions = observacions;
        return this;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public String getComentarisTreballador() {
        return comentarisTreballador;
    }

    public Feina comentarisTreballador(String comentarisTreballador) {
        this.comentarisTreballador = comentarisTreballador;
        return this;
    }

    public void setComentarisTreballador(String comentarisTreballador) {
        this.comentarisTreballador = comentarisTreballador;
    }

    public PlantillaFeina getPlantillaFeina() {
        return plantillaFeina;
    }

    public Feina plantillaFeina(PlantillaFeina plantillaFeina) {
        this.plantillaFeina = plantillaFeina;
        return this;
    }

    public void setPlantillaFeina(PlantillaFeina plantillaFeina) {
        this.plantillaFeina = plantillaFeina;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Feina categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Client getClient() {
        return client;
    }

    public Feina client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Treballador> getTreballadors() {
        return treballadors;
    }

    public Feina treballadors(Set<Treballador> treballadors) {
        this.treballadors = treballadors;
        return this;
    }

    public Feina addTreballador(Treballador treballador) {
        this.treballadors.add(treballador);
        treballador.getFeinas().add(this);
        return this;
    }

    public Feina removeTreballador(Treballador treballador) {
        this.treballadors.remove(treballador);
        treballador.getFeinas().remove(this);
        return this;
    }

    public void setTreballadors(Set<Treballador> treballadors) {
        this.treballadors = treballadors;
    }

    public Set<Ubicacio> getUbicacios() {
        return ubicacios;
    }

    public Feina ubicacios(Set<Ubicacio> ubicacios) {
        this.ubicacios = ubicacios;
        return this;
    }

    public Feina addUbicacio(Ubicacio ubicacio) {
        this.ubicacios.add(ubicacio);
        ubicacio.getFeinas().add(this);
        return this;
    }

    public Feina removeUbicacio(Ubicacio ubicacio) {
        this.ubicacios.remove(ubicacio);
        ubicacio.getFeinas().remove(this);
        return this;
    }

    public void setUbicacios(Set<Ubicacio> ubicacios) {
        this.ubicacios = ubicacios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feina)) {
            return false;
        }
        return id != null && id.equals(((Feina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Feina{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", descripcio='" + getDescripcio() + "'" +
            ", setmana='" + getSetmana() + "'" +
            ", tempsPrevist='" + getTempsPrevist() + "'" +
            ", tempsReal='" + getTempsReal() + "'" +
            ", estat='" + getEstat() + "'" +
            ", intervalControl=" + getIntervalControl() +
            ", facturacioAutomatica='" + isFacturacioAutomatica() + "'" +
            ", observacions='" + getObservacions() + "'" +
            ", comentarisTreballador='" + getComentarisTreballador() + "'" +
            "}";
    }
}
