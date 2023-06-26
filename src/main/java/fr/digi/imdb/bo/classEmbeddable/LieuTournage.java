package fr.digi.imdb.bo.classEmbeddable;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class LieuTournage {
    private String ville;
    private String etatDept;
    private String pays;


    public LieuTournage() {
        this.ville="";
        this.pays="";
        this.etatDept="";
    }

    public LieuTournage(String ville, String etatDept, String pays) {
        this.ville = ville;
        this.etatDept = etatDept;
        this.pays = pays;
    }

    /**
     * 获取
     * @return ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * 设置
     * @param ville
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * 获取
     * @return etatDept
     */
    public String getEtatDept() {
        return etatDept;
    }

    /**
     * 设置
     * @param etatDept
     */
    public void setEtatDept(String etatDept) {
        this.etatDept = etatDept;
    }

    /**
     * 获取
     * @return pays
     */
    public String getPays() {
        return pays;
    }

    /**
     * 设置
     * @param pays
     */
    public void setPays(String pays) {
        this.pays = pays;
    }

    public String toString() {
        return "LieuTournage{ville = " + ville + ", etatDept = " + etatDept + ", pays = " + pays + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieuTournage that = (LieuTournage) o;
        return Objects.equals(ville, that.ville) && Objects.equals(etatDept, that.etatDept) && Objects.equals(pays, that.pays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ville, etatDept, pays);
    }

    public void setStringAttribute(String key, String value){
        switch (key){
            case "etatDept" -> setEtatDept(value);
            case "pays" -> setPays(value);
            case "ville" -> setVille(value);
            default -> throw new IllegalStateException("Invalid key: " + key);
        }
    }


}
