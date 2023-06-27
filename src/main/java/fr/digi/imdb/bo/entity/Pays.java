package fr.digi.imdb.bo.entity;

import fr.digi.imdb.utils.ISetAttribute;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pays", schema = "imdb", catalog = "")
public class Pays implements ISetAttribute {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "pays_id")
    private int paysId;
    @Basic
    @Column(name = "pays_nom", length = 20, unique = true)
    private String paysNom;
    @Basic
    @Column(name = "pays_url")
    private String paysUrl;
    @OneToMany(mappedBy = "pays")
    private Set<Cinema> cinemas = new HashSet<>();



    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public int getPaysId() {
        return paysId;
    }

    public void setPaysId(int paysId) {
        this.paysId = paysId;
    }

    public String getPaysNom() {
        return paysNom;
    }

    public void setPaysNom(String paysNom) {
        this.paysNom = paysNom;
    }

    public String getPaysUrl() {
        return paysUrl;
    }

    public void setPaysUrl(String paysUrl) {
        this.paysUrl = paysUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pays that = (Pays) o;
        return paysId == that.paysId && Objects.equals(paysNom, that.paysNom) && Objects.equals(paysUrl, that.paysUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paysId, paysNom, paysUrl);
    }

    @Override
    public <T> void setGenericAttribute(String key, T value) {
        switch (key){
            case "nom" -> setPaysNom((String) value);
            case "url" -> setPaysUrl((String) value);
            default -> throw new IllegalStateException("Invalid key: " + key);
        }
    }

    @Override
    public String toString() {
        return "Pays{" +
                "paysId=" + paysId +
                ", paysNom='" + paysNom + '\'' +
                ", paysUrl='" + paysUrl + '\'' +
                '}';
    }
}
