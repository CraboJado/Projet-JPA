package fr.digi.imdb.bo.classEmbeddable;

import jakarta.persistence.Embeddable;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class Naissance {
    private Date dateNaissance;
    private String lieuNaissance;

    public Naissance() {
        this.dateNaissance = null;
        this.lieuNaissance = "";
    }

    public Naissance(Date dateNaissance, String lieuNaissance) {
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
    }

    /**
     * 获取
     * @return dateNaissance
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    /**
     * 设置
     * @param dateNaissance
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * 获取
     * @return lieuNaissance
     */
    public String getLieuNaissance() {
        return lieuNaissance;
    }

    /**
     * 设置
     * @param lieuNaissance
     */
    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String toString() {
        return "Naissance{dateNaissance = " + dateNaissance + ", lieuNaissance = " + lieuNaissance + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Naissance naissance = (Naissance) o;
        return Objects.equals(dateNaissance, naissance.dateNaissance) && Objects.equals(lieuNaissance, naissance.lieuNaissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateNaissance, lieuNaissance);
    }
}
