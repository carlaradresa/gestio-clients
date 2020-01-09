package com.serviemporda.gestioclients.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Ubicacio.
 */
@Entity
@Table(name = "ubicacio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ubicacio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "ubicacio")
    private String ubicacio;

    @OneToOne(mappedBy = "ubicacio")
    @JsonIgnore
    private Client client;

    @OneToOne(mappedBy = "ubicacio")
    @JsonIgnore
    private Venedor venedor;

    @OneToOne(mappedBy = "ubicacio")
    @JsonIgnore
    private Feina venedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Ubicacio longitud(Double longitud) {
        this.longitud = longitud;
        return this;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Ubicacio latitud(Double latitud) {
        this.latitud = latitud;
        return this;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public Ubicacio ubicacio(String ubicacio) {
        this.ubicacio = ubicacio;
        return this;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public Client getClient() {
        return client;
    }

    public Ubicacio client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Venedor getVenedor() {
        return venedor;
    }

    public Ubicacio venedor(Venedor venedor) {
        this.venedor = venedor;
        return this;
    }

    public void setVenedor(Venedor venedor) {
        this.venedor = venedor;
    }

    public Feina getVenedor() {
        return venedor;
    }

    public Ubicacio venedor(Feina feina) {
        this.venedor = feina;
        return this;
    }

    public void setVenedor(Feina feina) {
        this.venedor = feina;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ubicacio)) {
            return false;
        }
        return id != null && id.equals(((Ubicacio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ubicacio{" +
            "id=" + getId() +
            ", longitud=" + getLongitud() +
            ", latitud=" + getLatitud() +
            ", ubicacio='" + getUbicacio() + "'" +
            "}";
    }
}
