package entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "realisateur", schema = "imdb", catalog = "")
public class Realisateur {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rea_id")
    private Integer reaId;
    @Basic
    @Column(name = "rea_identite",length = 50)
    private String reaIdentite;
    @Basic
    @Column(name = "rea_url")
    private String reaUrl;

    @ManyToMany(mappedBy = "realisateurs")
    private Set<Cinema> cinemas = new HashSet<>();

    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public Integer getReaId() {
        return reaId;
    }

    public void setReaId(Integer realisateurId) {
        this.reaId = realisateurId;
    }

    public String getReaIdentite() {
        return reaIdentite;
    }

    public void setReaIdentite(String realisateurIdentite) {
        this.reaIdentite = realisateurIdentite;
    }

    public String getReaUrl() {
        return reaUrl;
    }

    public void setReaUrl(String reaUrl) {
        this.reaUrl = reaUrl;
    }

    @Override
    public String toString() {
        return "Realisateur{" +
                "reaId=" + reaId +
                ", reaIdentite='" + reaIdentite + '\'' +
                ", reaUrl='" + reaUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Realisateur that = (Realisateur) o;
        return reaId == that.reaId && Objects.equals(reaIdentite, that.reaIdentite) && Objects.equals(reaUrl, that.reaUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reaId, reaIdentite, reaUrl);
    }
}
