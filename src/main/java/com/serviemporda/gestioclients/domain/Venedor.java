package com.serviemporda.gestioclients.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Venedor.
 */
@Entity
@Table(name = "venedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Venedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "nom")
    private String nom;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    @Column(name = "observacions")
    private String observacions;

    @OneToOne
    @JoinColumn(unique = true)
    private Ubicacio ubicacio;

    @OneToMany(mappedBy = "venedor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Client> clients = new HashSet<>();

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

    public Venedor numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public Venedor nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelefon() {
        return telefon;
    }

    public Venedor telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public Venedor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacions() {
        return observacions;
    }

    public Venedor observacions(String observacions) {
        this.observacions = observacions;
        return this;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public Ubicacio getUbicacio() {
        return ubicacio;
    }

    public Venedor ubicacio(Ubicacio ubicacio) {
        this.ubicacio = ubicacio;
        return this;
    }

    public void setUbicacio(Ubicacio ubicacio) {
        this.ubicacio = ubicacio;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Venedor clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public Venedor addClient(Client client) {
        this.clients.add(client);
        client.setVenedor(this);
        return this;
    }

    public Venedor removeClient(Client client) {
        this.clients.remove(client);
        client.setVenedor(null);
        return this;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venedor)) {
            return false;
        }
        return id != null && id.equals(((Venedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Venedor{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", nom='" + getNom() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", email='" + getEmail() + "'" +
            ", observacions='" + getObservacions() + "'" +
            "}";
    }
}
