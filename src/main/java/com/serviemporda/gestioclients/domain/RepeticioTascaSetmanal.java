package com.serviemporda.gestioclients.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.serviemporda.gestioclients.domain.enumeration.Dia;

/**
 * A RepeticioTascaSetmanal.
 */
@Entity
@Table(name = "repeticio_tasca_setmanal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RepeticioTascaSetmanal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia")
    private Dia dia;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JsonIgnoreProperties("repeticioTascaSetmanals")
    private Feina feina;

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

    public RepeticioTascaSetmanal dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Boolean isActivo() {
        return activo;
    }

    public RepeticioTascaSetmanal activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Feina getFeina() {
        return feina;
    }

    public RepeticioTascaSetmanal feina(Feina feina) {
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
        if (!(o instanceof RepeticioTascaSetmanal)) {
            return false;
        }
        return id != null && id.equals(((RepeticioTascaSetmanal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RepeticioTascaSetmanal{" +
            "id=" + getId() +
            ", dia='" + getDia() + "'" +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
