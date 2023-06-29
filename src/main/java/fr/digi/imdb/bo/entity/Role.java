package fr.digi.imdb.bo.entity;

import fr.digi.imdb.utils.ISetAttribute;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role", schema = "imdb", catalog = "")
public class Role implements ISetAttribute {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_id")
    private Integer roleId;
    @Basic
    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(targetEntity = Acteur.class)
    @JoinTable(name = "sys_acteur_role",
        joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")},
        inverseJoinColumns ={@JoinColumn(name = "acteur_id",referencedColumnName = "act_id")})
    private Set<Acteur> acteurs = new HashSet<>();
    @ManyToMany(mappedBy = "roles")
    private Set<Cinema> cinemas = new HashSet<>();

    public Set<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(Set<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public Set<Acteur> getActeurs() {
        return acteurs;
    }

    public void setActeurs(Set<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return roleId == that.roleId && Objects.equals(roleName, that.roleName) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
    }

    @Override
    public <T> void setGenericAttribute(String key, T value) {
        switch (key){
            case "characterName" -> setRoleName((String) value);
            case "acteur" -> getActeurs().add((Acteur) value);
            default -> throw new IllegalStateException("Invalid key: " + key);
        }

    }
}
