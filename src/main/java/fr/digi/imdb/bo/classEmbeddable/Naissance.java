package fr.digi.imdb.bo.classEmbeddable;

import fr.digi.imdb.utils.ISetAttribute;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Embeddable
public class Naissance implements ISetAttribute {

    private String dateNaissance;
    private String lieuNaissance;

    public Naissance() {
        this.dateNaissance = null;
        this.lieuNaissance = "";
    }

    public Naissance(String dateNaissance, String lieuNaissance) {
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
    }

    /**
     * 获取
     * @return dateNaissance
     */
    public String getDateNaissance() {
        return dateNaissance;
    }

    /**
     * 设置
     * @param dateNaissance
     */
    public void setDateNaissance(String dateNaissance) {
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

    @Override
    public <T> void setGenericAttribute(String key, T value) {
        switch (key){
            case "dateNaissance" -> setDateNaissance((String) value);
            case "lieuNaissance" -> setLieuNaissance((String) value);
            default -> throw new IllegalStateException("Invalid key: " + key);
        }
    }
}
