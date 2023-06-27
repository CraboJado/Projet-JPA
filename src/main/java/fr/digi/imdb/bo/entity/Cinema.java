package fr.digi.imdb.bo.entity;

import fr.digi.imdb.bo.classEmbeddable.LieuTournage;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cinema", schema = "imdb", catalog = "")
public class Cinema {

    @Id
    @Column(name = "cine_id", length = 20)
    private String cineId;
    @Basic
    @Column(name = "cine_nom", length = 100)
    private String cineNom;
    @Basic
    @Column(name = "cine_url")
    private String cineUrl;
    @Basic
    @Column(name = "cine_plot")
    private String cinePlot;
    @Basic
    @Column(name = "cine_langue", length = 30)
    private String cineLangue;
    /* @Basic
     @Column(name = "cine_anneeSortie")
     private Date cineAnneeSortie;
     public Date getCineAnneeSortie() {
         return cineAnneeSortie;
     }

     public void setCineAnneeSortie(Date cineAnneeSortie) {
         this.cineAnneeSortie = cineAnneeSortie;
     }*/
    @Embedded
    private LieuTournage lieuTournage;


    @ManyToOne(targetEntity = AnneeSortie.class)
    @JoinColumn(name = "anneeSortie", referencedColumnName = "annee")
    private AnneeSortie anneeSortie;

    @ManyToOne(targetEntity = Pays.class)
    @JoinColumn(name = "pays_id", referencedColumnName = "pays_id")
    private Pays pays;

    @ManyToMany(targetEntity = Genres.class)
    @JoinTable(name = "sys_cinema_genre",
            joinColumns = {@JoinColumn(name = "cinima_id", referencedColumnName = "cine_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "gen_id")})
    private Set<Genres> genres = new HashSet<>();

    @ManyToMany(targetEntity = Realisateur.class)
    @JoinTable(name = "sys_cinema_rea",
            joinColumns = {@JoinColumn(name = "cinima_id", referencedColumnName = "cine_id")},
            inverseJoinColumns = {@JoinColumn(name = "realisateur_id", referencedColumnName = "rea_id")})
    private Set<Realisateur> realisateurs = new HashSet<>();

    @ManyToMany(targetEntity = Acteur.class)
    @JoinTable(name = "sys_cinema_acteur",
            joinColumns = {@JoinColumn(name = "cinima_id", referencedColumnName = "cine_id")},
            inverseJoinColumns = {@JoinColumn(name = "acteur_id", referencedColumnName = "act_id")})
    private Set<Acteur> acteurs = new HashSet<>();

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "sys_cinema_role",
            joinColumns = {@JoinColumn(name = "cinima_id", referencedColumnName = "cine_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public AnneeSortie getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(AnneeSortie anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Acteur> getActeurs() {
        return acteurs;
    }

    public void setActeurs(Set<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public Set<Realisateur> getRealisateurs() {
        return realisateurs;
    }

    public void setRealisateurs(Set<Realisateur> realisateurs) {
        this.realisateurs = realisateurs;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public LieuTournage getLieuTournage() {
        return lieuTournage;
    }

    public void setLieuTournage(LieuTournage lieuTournage) {
        this.lieuTournage = lieuTournage;
    }

    public String getCineId() {
        return cineId;
    }

    public void setCineId(String cineId) {
        this.cineId = cineId;
    }

    public String getCineNom() {
        return cineNom;
    }

    public void setCineNom(String cineNom) {
        this.cineNom = cineNom;
    }

    public String getCineUrl() {
        return cineUrl;
    }

    public void setCineUrl(String cineUrl) {
        this.cineUrl = cineUrl;
    }

    public String getCinePlot() {
        return cinePlot;
    }

    public void setCinePlot(String cinePlot) {
        this.cinePlot = cinePlot;
    }

    public String getCineLangue() {
        return cineLangue;
    }

    public void setCineLangue(String cineLangue) {
        this.cineLangue = cineLangue;
    }

    @Override
    public String toString() {
        return " CineNom='" + cineNom + '\'' +
                "CineId='" + "( " + cineId + '\'' + " )" +
                " AnneeSortie='" + anneeSortie.getAnnee() + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema that = (Cinema) o;
        return Objects.equals(cineId, that.cineId) && Objects.equals(cineNom, that.cineNom) && Objects.equals(cineUrl, that.cineUrl) && Objects.equals(cinePlot, that.cinePlot) && Objects.equals(cineLangue, that.cineLangue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cineId, cineNom, cineUrl, cinePlot, cineLangue);
    }
}
