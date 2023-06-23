package utils;

import classEmbeddable.LieuTournage;
import classEmbeddable.Naissance;
import com.google.gson.JsonArray;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Acteur;
import entity.Cinema;
import entity.Pays;
import entity.Realisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;


import java.io.FileReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InitMysql {

    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        try (EntityManager em = JpaUtils.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();


            JsonArray films = (JsonArray) parser.parse(new FileReader("films.json"));
            for (int i = 0; i < films.size(); i++) {
                tx.begin();

                System.out.println(i + 1);


                Cinema cinema = new Cinema();
                JsonObject filmObj = films.get(i).getAsJsonObject();
                //添加正则表达式
                String id = filmObj.get("id").isJsonNull() ? "IdTmpl000" + String.valueOf(i) : filmObj.get("id").getAsString();
                cinema.setCineId(id);

                Pays pays = new Pays();
                if (!filmObj.get("pays").isJsonNull()) {
                    JsonObject paysJson = filmObj.get("pays").getAsJsonObject();
                    pays.setPaysNom(paysJson.get("nom").getAsString());
                    pays.setPaysUrl(paysJson.get("url").getAsString());
                    //判断数据库 录入 pays {nom:   ,  url:}
                    Query query = em.createQuery("select p from Pays p where p.paysNom = :name");
                    query.setParameter("name", pays.getPaysNom());
                    List<Pays> paysList = query.getResultList();
                    if (paysList.size() == 0) {
                        //插入数据
                        pays = em.merge(pays);
                    } else pays = paysList.get(0);
                } else pays = null;


                String nom = filmObj.get("nom").isJsonNull() ? "" : filmObj.get("nom").getAsString();
                cinema.setCineNom(nom);
                String url = filmObj.get("url").isJsonNull() ? "" : filmObj.get("url").getAsString();
                cinema.setCineUrl(url);
                String plot = filmObj.get("plot").isJsonNull() ? "" : filmObj.get("plot").getAsString();
                cinema.setCinePlot(plot);
                String langue = filmObj.get("langue").isJsonNull() ? "" : filmObj.get("langue").getAsString();
                cinema.setCineLangue(langue);

                LieuTournage lieuTournage = new LieuTournage();
                if (!filmObj.get("lieuTournage").isJsonNull()) {
                    JsonObject lieuTournageObj = filmObj.get("lieuTournage").getAsJsonObject();
                    // lieuTournage {etatDept ,pays ,ville }
                    String etatDept = lieuTournageObj.get("etatDept").isJsonNull() ? "" : lieuTournageObj.get("etatDept").getAsString();
                    lieuTournage.setEtatDept(etatDept);
                    String lieuPays = lieuTournageObj.get("pays").isJsonNull() ? "" : lieuTournageObj.get("pays").getAsString();
                    lieuTournage.setPays(lieuPays);
                    String ville = lieuTournageObj.get("ville").isJsonNull() ? "" : lieuTournageObj.get("ville").getAsString();
                    lieuTournage.setVille(ville);
                }
                cinema.setLieuTournage(lieuTournage);
                Set<Realisateur> realisateurSet = new HashSet<>();
                if (!filmObj.get("realisateurs").isJsonNull()) {
                    JsonArray realisateursArr = filmObj.get("realisateurs").getAsJsonArray();
                    for (int j = 0; j < realisateursArr.size(); j++) {
                        //循环插入数据库 realisateurs
                        if (!realisateursArr.get(j).isJsonNull()) {
                            Realisateur realisateur = new Realisateur();
                            JsonObject realisateursObj = realisateursArr.get(j).getAsJsonObject();
                            String reaIdentite = realisateursObj.get("identite").isJsonNull() ? "" : realisateursObj.get("identite").getAsString();
                            realisateur.setReaIdentite(reaIdentite);
                            String reaUrl = realisateursObj.get("url").isJsonNull() ? "" : realisateursObj.get("url").getAsString();
                            realisateur.setReaUrl(reaUrl);
                            Query query = em.createQuery("select r from Realisateur r where r.reaIdentite = :identite");
                            query.setParameter("identite", reaIdentite);
                            List<Realisateur> realisateurList = query.getResultList();
                            if (realisateurList.size() == 0) {

                                realisateur = em.merge(realisateur);
                            } else realisateur = realisateurList.get(0);
                            realisateurSet.add(realisateur);
                        }
                    }
                }

                Set<Acteur> acteurSet = new HashSet<>();
                if (!filmObj.get("castingPrincipal").isJsonNull()) {
                    JsonArray castingPrArr = filmObj.get("castingPrincipal").getAsJsonArray();
                    for (int j = 0; j < castingPrArr.size(); j++) {
                        //循环插入数据库  castingPrincipal
                        if (!castingPrArr.get(j).isJsonNull()) {
                            Acteur acteur = new Acteur();
                            JsonObject castingPrObj = castingPrArr.get(j).getAsJsonObject();
                            String actId = castingPrObj.get("id").isJsonNull() ? "" : castingPrObj.get("id").getAsString();
                            Query query = em.createQuery("select a from Acteur a where a.actId = :actId");
                            query.setParameter("actId", actId);
                            List<Acteur> acteurList = query.getResultList();
                            if (acteurList.size() == 0) {
                                acteur.setActId(actId);
                                String actIdentite = castingPrObj.get("identite").isJsonNull() ? "" : castingPrObj.get("identite").getAsString();
                                acteur.setActIdentite(actIdentite);
                                Naissance naissance = new Naissance();
                                if (!castingPrObj.get("naissance").isJsonNull()) {
                                    JsonObject naissObj = castingPrObj.get("naissance").getAsJsonObject();
                                    String str = naissObj.get("dateNaissance").isJsonNull() ? "" : naissObj.get("dateNaissance").getAsString();
                                    Date dateNai = null;
                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    dateNai = str.equals("") ? null : ft.parse(str);
                                    naissance.setDateNaissance(dateNai);
                                    String liueNai = naissObj.get("lieuNaissance").isJsonNull() ? "" : naissObj.get("lieuNaissance").getAsString();
                                    naissance.setLieuNaissance(liueNai);
                                }
                                acteur.setNaissance(naissance);
                                String actUrl = castingPrObj.get("url").isJsonNull() ? "" : castingPrObj.get("url").getAsString();
                                acteur.setActUrl(actUrl);
                                String actHei = castingPrObj.get("height").isJsonNull() ? "" : castingPrObj.get("height").getAsString();
                                acteur.setActHeight(actHei);
                                acteur = em.merge(acteur);
                            } else acteur = acteurList.get(0);
                            acteurSet.add(acteur);
                        }

                    }
                }


                String anneeSortie = filmObj.get("anneeSortie").isJsonNull() ? "" : filmObj.get("anneeSortie").getAsString();
                cinema.setCineAnneeSortie(anneeSortie);


                if (!filmObj.get("roles").isJsonNull()) {
                    JsonArray roles = filmObj.get("roles").getAsJsonArray();
                    //循环录入
                }

                if (!filmObj.get("genres").isJsonNull()) {
                    JsonArray genres = filmObj.get("genres").getAsJsonArray();
                    //循环录入
                }
                tx.commit();

            }


        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);

        }
    }
}
