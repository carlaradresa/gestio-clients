package com.serviemporda.gestioclients.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Control.
 */
@Entity
@Table(name = "control")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Control implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "setmana")
    private LocalDate setmana;

    @Column(name = "causa")
    private String causa;

    @Column(name = "data_revisio")
    private ZonedDateTime dataRevisio;

    @Column(name = "comentaris")
    private String comentaris;

    @ManyToOne
    @JsonIgnoreProperties("controls")
    private Treballador revisor;

    @ManyToOne
    @JsonIgnoreProperties("controls")
    private Feina feina;

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

    public Control numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getSetmana() {
        return setmana;
    }

    public Control setmana(LocalDate setmana) {
        this.setmana = setmana;
        return this;
    }

    public void setSetmana(LocalDate setmana) {
        this.setmana = setmana;
    }

    public String getCausa() {
        return causa;
    }

    public Control causa(String causa) {
        this.causa = causa;
        return this;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public ZonedDateTime getDataRevisio() {
        return dataRevisio;
    }

    public Control dataRevisio(ZonedDateTime dataRevisio) {
        this.dataRevisio = dataRevisio;
        return this;
    }

    public void setDataRevisio(ZonedDateTime dataRevisio) {
        this.dataRevisio = dataRevisio;
    }

    public String getComentaris() {
        return comentaris;
    }

    public Control comentaris(String comentaris) {
        this.comentaris = comentaris;
        return this;
    }

    public void setComentaris(String comentaris) {
        this.comentaris = comentaris;
    }

    public Treballador getRevisor() {
        return revisor;
    }

    public Control revisor(Treballador treballador) {
        this.revisor = treballador;
        return this;
    }

    public void setRevisor(Treballador treballador) {
        this.revisor = treballador;
    }

    public Feina getFeina() {
        return feina;
    }

    public Control feina(Feina feina) {
        this.feina = feina;
        return this;
    }

    public void setFeina(Feina feina) {
        this.feina = feina;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Control)) {
            return false;
        }
        return id != null && id.equals(((Control) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Control{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", setmana='" + getSetmana() + "'" +
            ", causa='" + getCausa() + "'" +
            ", dataRevisio='" + getDataRevisio() + "'" +
            ", comentaris='" + getComentaris() + "'" +
            "}";
    }
}
