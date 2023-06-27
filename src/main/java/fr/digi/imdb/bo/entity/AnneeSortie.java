package fr.digi.imdb.bo.entity;


import jakarta.persistence.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "anneeSortie")
public class AnneeSortie {
    @Id
    @Column(name = "annee")
    private Integer annee;

@OneToMany(mappedBy = "anneeSortie")
    Set<Cinema> cinemas = new HashSet<>();


    public AnneeSortie() {
    }

    public AnneeSortie(Integer annee, Set<Cinema> cinemas) {
        this.annee = annee;
        this.cinemas = cinemas;
    }

    public AnneeSortie(Integer year) {
        this.annee = year;
    }

    /**
     * 获取
     * @return annee
     */
    public Integer getAnnee() {
        return annee;
    }

    /**
     * 设置
     * @param annee
     */
    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    /**
     * 获取
     * @return cinemas
     */
    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    /**
     * 设置
     * @param cinemas
     */
    public void setCinemas(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public String toString() {
        return "AnneeSortie{annee = " + annee + ", cinemas = " + cinemas + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnneeSortie that = (AnneeSortie) o;
        return Objects.equals(annee, that.annee) && Objects.equals(cinemas, that.cinemas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annee, cinemas);
    }
}
