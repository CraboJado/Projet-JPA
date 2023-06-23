package entity;

import classEmbeddable.Naissance;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "acteur", schema = "imdb", catalog = "")
public class Acteur {

    @Id
    @Column(name = "act_id", length = 20)
    private String actId;
    @Basic
    @Column(name = "act_identite", length = 50)
    private String actIdentite;
    @Basic
    @Column(name = "act_url")
    private String actUrl;
    @Basic
    @Column(name = "act_height", length = 10)
    private String actHeight;
    @Embedded
    private Naissance naissance;
    @ManyToMany(mappedBy = "acteurs")
    private Set<Cinema> cinemas = new HashSet<>();

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "sys_acteur_role",
            joinColumns = {@JoinColumn(name = "acteur_id", referencedColumnName = "act_id")},
            inverseJoinColumns ={@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public Naissance getNaissance() {
        return naissance;
    }

    public void setNaissance(Naissance naissance) {
        this.naissance = naissance;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActIdentite() {
        return actIdentite;
    }

    public void setActIdentite(String actIdentite) {
        this.actIdentite = actIdentite;
    }

    public String getActUrl() {
        return actUrl;
    }

    public void setActUrl(String actUrl) {
        this.actUrl = actUrl;
    }

    public String getActHeight() {
        return actHeight;
    }

    public void setActHeight(String actHeight) {
        this.actHeight = actHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acteur that = (Acteur) o;
        return Objects.equals(actId, that.actId) && Objects.equals(actIdentite, that.actIdentite) && Objects.equals(actUrl, that.actUrl) && Objects.equals(actHeight, that.actHeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actId, actIdentite, actUrl, actHeight);
    }
}
