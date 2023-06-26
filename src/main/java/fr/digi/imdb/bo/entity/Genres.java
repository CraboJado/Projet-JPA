package fr.digi.imdb.bo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "genres", schema = "imdb", catalog = "")
public class Genres {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "gen_id")
    private Integer genId;
    @Basic
    @Column(name = "gen_name",length = 20)
    private String genName;
    @ManyToMany(mappedBy = "genres")
    private Set<Cinema> cinemas =new HashSet<>();

    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public int getGenId() {
        return genId;
    }

    public void setGenId(int id) {
        this.genId = id;
    }

    public String getGenName() {
        return genName;
    }

    public void setGenName(String genre) {
        this.genName = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genres that = (Genres) o;
        return genId == that.genId && Objects.equals(genName, that.genName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genId, genName);
    }

    @Override
    public String toString() {
        return "Genres{" +
                "genId=" + genId +
                ", genName='" + genName + '\'' +
                '}';
    }
}
