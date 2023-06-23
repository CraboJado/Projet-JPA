package fr.digi.imdb.bo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role", schema = "imdb", catalog = "")
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_id")
    private Integer roleId;
    @Basic
    @Column(name = "role_name",length = 20)
    private String roleName;
    @Basic
    @Column(name = "role_height",length = 10)
    private String roleHeight;
    @Basic
    @Column(name = "role_url")
    private String roleUrl;
    @ManyToMany(mappedBy = "roles")
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

    public String getRoleHeight() {
        return roleHeight;
    }

    public void setRoleHeight(String roleHeight) {
        this.roleHeight = roleHeight;
    }

    public String getRoleUrl() {
        return roleUrl;
    }

    public void setRoleUrl(String roleUrl) {
        this.roleUrl = roleUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return roleId == that.roleId && Objects.equals(roleName, that.roleName) && Objects.equals(roleHeight, that.roleHeight) && Objects.equals(roleUrl, that.roleUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, roleHeight, roleUrl);
    }
}
