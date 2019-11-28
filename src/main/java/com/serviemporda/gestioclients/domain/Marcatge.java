package com.serviemporda.gestioclients.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Marcatge.
 */
@Entity
@Table(name = "marcatge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Marcatge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hora_entrada")
    private Instant horaEntrada;

    @Column(name = "hora_sortida")
    private Instant horaSortida;

    @Column(name = "desviacio")
    private Boolean desviacio;

    @ManyToOne
    @JsonIgnoreProperties("marcatges")
    private Feina feina;

    @ManyToOne
    @JsonIgnoreProperties("marcatges")
    private Treballador treballador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoraEntrada() {
        return horaEntrada;
    }

    public Marcatge horaEntrada(Instant horaEntrada) {
        this.horaEntrada = horaEntrada;
        return this;
    }

    public void setHoraEntrada(Instant horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Instant getHoraSortida() {
        return horaSortida;
    }

    public Marcatge horaSortida(Instant horaSortida) {
        this.horaSortida = horaSortida;
        return this;
    }

    public void setHoraSortida(Instant horaSortida) {
        this.horaSortida = horaSortida;
    }

    public Boolean isDesviacio() {
        return desviacio;
    }

    public Marcatge desviacio(Boolean desviacio) {
        this.desviacio = desviacio;
        return this;
    }

    public void setDesviacio(Boolean desviacio) {
        this.desviacio = desviacio;
    }

    public Feina getFeina() {
        return feina;
    }

    public Marcatge feina(Feina feina) {
        this.feina = feina;
        return this;
    }

    public void setFeina(Feina feina) {
        this.feina = feina;
    }

    public Treballador getTreballador() {
        return treballador;
    }

    public Marcatge treballador(Treballador treballador) {
        this.treballador = treballador;
        return this;
    }

    public void setTreballador(Treballador treballador) {
        this.treballador = treballador;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Marcatge)) {
            return false;
        }
        return id != null && id.equals(((Marcatge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Marcatge{" +
            "id=" + getId() +
            ", horaEntrada='" + getHoraEntrada() + "'" +
            ", horaSortida='" + getHoraSortida() + "'" +
            ", desviacio='" + isDesviacio() + "'" +
            "}";
    }
}
