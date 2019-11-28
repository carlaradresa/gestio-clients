package com.serviemporda.gestioclients.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "direccio")
    private String direccio;

    @Column(name = "localitat")
    private String localitat;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    @Column(name = "nif")
    private String nif;

    @Column(name = "codi_ubicacio")
    private String codiUbicacio;

    @Column(name = "observacions")
    private String observacions;

    @OneToOne
    @JoinColumn(unique = true)
    private Ubicacio ubicacio;

    @ManyToOne
    @JsonIgnoreProperties("clients")
    private Venedor venedor;

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

    public Client nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDireccio() {
        return direccio;
    }

    public Client direccio(String direccio) {
        this.direccio = direccio;
        return this;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getLocalitat() {
        return localitat;
    }

    public Client localitat(String localitat) {
        this.localitat = localitat;
        return this;
    }

    public void setLocalitat(String localitat) {
        this.localitat = localitat;
    }

    public String getTelefon() {
        return telefon;
    }

    public Client telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public Client nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCodiUbicacio() {
        return codiUbicacio;
    }

    public Client codiUbicacio(String codiUbicacio) {
        this.codiUbicacio = codiUbicacio;
        return this;
    }

    public void setCodiUbicacio(String codiUbicacio) {
        this.codiUbicacio = codiUbicacio;
    }

    public String getObservacions() {
        return observacions;
    }

    public Client observacions(String observacions) {
        this.observacions = observacions;
        return this;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public Ubicacio getUbicacio() {
        return ubicacio;
    }

    public Client ubicacio(Ubicacio ubicacio) {
        this.ubicacio = ubicacio;
        return this;
    }

    public void setUbicacio(Ubicacio ubicacio) {
        this.ubicacio = ubicacio;
    }

    public Venedor getVenedor() {
        return venedor;
    }

    public Client venedor(Venedor venedor) {
        this.venedor = venedor;
        return this;
    }

    public void setVenedor(Venedor venedor) {
        this.venedor = venedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", direccio='" + getDireccio() + "'" +
            ", localitat='" + getLocalitat() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", email='" + getEmail() + "'" +
            ", nif='" + getNif() + "'" +
            ", codiUbicacio='" + getCodiUbicacio() + "'" +
            ", observacions='" + getObservacions() + "'" +
            "}";
    }
}
