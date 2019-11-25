package com.serviemporda.gestioclients.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * A Treballador.
 */
@Entity
@Table(name = "treballador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Treballador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "nom")
    private String nom;

    @Column(name = "carrega_hores")
    private Duration carregaHores;

    @Column(name = "actiu")
    private Boolean actiu;

    @Column(name = "control_qualitat")
    private Boolean controlQualitat;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany(mappedBy = "treballadors")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Feina> feinas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Treballador numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public Treballador nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Duration getCarregaHores() {
        return carregaHores;
    }

    public Treballador carregaHores(Duration carregaHores) {
        this.carregaHores = carregaHores;
        return this;
    }

    public void setCarregaHores(Duration carregaHores) {
        this.carregaHores = carregaHores;
    }

    public Boolean isActiu() {
        return actiu;
    }

    public Treballador actiu(Boolean actiu) {
        this.actiu = actiu;
        return this;
    }

    public void setActiu(Boolean actiu) {
        this.actiu = actiu;
    }

    public Boolean isControlQualitat() {
        return controlQualitat;
    }

    public Treballador controlQualitat(Boolean controlQualitat) {
        this.controlQualitat = controlQualitat;
        return this;
    }

    public void setControlQualitat(Boolean controlQualitat) {
        this.controlQualitat = controlQualitat;
    }

    public User getUser() {
        return user;
    }

    public Treballador user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Feina> getFeinas() {
        return feinas;
    }

    public Treballador feinas(Set<Feina> feinas) {
        this.feinas = feinas;
        return this;
    }

    public Treballador addFeina(Feina feina) {
        this.feinas.add(feina);
        feina.getTreballadors().add(this);
        return this;
    }

    public Treballador removeFeina(Feina feina) {
        this.feinas.remove(feina);
        feina.getTreballadors().remove(this);
        return this;
    }

    public void setFeinas(Set<Feina> feinas) {
        this.feinas = feinas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Treballador)) {
            return false;
        }
        return id != null && id.equals(((Treballador) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Treballador{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", nom='" + getNom() + "'" +
            ", carregaHores='" + getCarregaHores() + "'" +
            ", actiu='" + isActiu() + "'" +
            ", controlQualitat='" + isControlQualitat() + "'" +
            "}";
    }
}
