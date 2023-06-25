package fr.digi.imdb.utils;

import fr.digi.imdb.bo.classEmbeddable.LieuTournage;
import fr.digi.imdb.bo.classEmbeddable.Naissance;
import com.google.gson.JsonArray;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.digi.imdb.bo.entity.*;
import fr.digi.imdb.dal.jpa.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;


import java.io.FileReader;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class InitMysql {

    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        try (EntityManager em = JpaUtils.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            JsonArray films = (JsonArray) parser.parse(new FileReader("films.json"));
            for (int i = 0; i < films.size(); i++) {
                /**
                 * 获取
                 * @param tx
                 */
                tx.begin();
                System.out.println(i + 1);
                Cinema cinema = new Cinema();
                JsonObject filmObj = films.get(i).getAsJsonObject();
                //添加正则表达式
                /**
                 * 获取
                 * @param cine_id
                 */
                String id = filmObj.get("id").isJsonNull() ? "IdTmpl000" + String.valueOf(i) : filmObj.get("id").getAsString();
                id.replaceAll(" ", "");
                id = id.equals("") ? "IdTmpl000" + String.valueOf(i) : id;
                cinema.setCineId(id);

                /**
                 * 获取
                 * @param cine_pays
                 */
                Pays pays = new Pays();
                if (!filmObj.get("pays").isJsonNull()) {
                    JsonObject paysJson = filmObj.get("pays").getAsJsonObject();
                    pays.setPaysNom(paysJson.get("nom").getAsString());
                    pays.setPaysUrl(paysJson.get("url").getAsString());
                    //判断数据库 录入 pays {nom:   ,  url:}
                    Query query = em.createQuery("select p from Pays p where p.paysNom = :name");
                    query.setParameter("name", pays.getPaysNom());
                    query.setMaxResults(1);
                    List<Pays> paysList = query.getResultList();
                    if (paysList.size() != 0) {
                        pays = paysList.get(0);
                    } else {
                        em.persist(pays);
                        pays = (Pays) query.getResultList().get(0);
                    }

                } else pays = null;
                cinema.setPays(pays);
                /**
                 * 获取
                 * @param cine_nom
                 * @param cine_url
                 * @param cine_plot
                 * @param cine_langue
                 * @param cine_lieuTournage
                 */

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
                    String etatDept = lieuTournageObj.get("etatDept").isJsonNull() ? "" : lieuTournageObj.get("etatDept").getAsString();
                    lieuTournage.setEtatDept(etatDept);
                    String lieuPays = lieuTournageObj.get("pays").isJsonNull() ? "" : lieuTournageObj.get("pays").getAsString();
                    lieuTournage.setPays(lieuPays);
                    String ville = lieuTournageObj.get("ville").isJsonNull() ? "" : lieuTournageObj.get("ville").getAsString();
                    lieuTournage.setVille(ville);
                }
                cinema.setLieuTournage(lieuTournage);

                /**
                 * 获取
                 * @param realisateurSet
                 */
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
                            Query query = em.createQuery("select r from Realisateur r where r.reaIdentite = :identite ");
                            query.setParameter("identite", reaIdentite);
                            query.setMaxResults(1);
                            List<Realisateur> realisateurList = query.getResultList();
                            if (realisateurList.size() != 0) {
                                realisateur = realisateurList.get(0);
                            } else {
                                em.persist(realisateur);
                                realisateur = (Realisateur) query.getSingleResult();
                            }

                            realisateurSet.add(realisateur);
                        }
                    }
                }

                /**
                 * 获取
                 * @param acteurSet
                 */
                Set<Acteur> acteurSet = new HashSet<>();
                if (!filmObj.get("castingPrincipal").isJsonNull()) {
                    JsonArray castingPrArr = filmObj.get("castingPrincipal").getAsJsonArray();
                    for (int j = 0; j < castingPrArr.size(); j++) {
                        //循环插入数据库  castingPrincipal
                        if (!castingPrArr.get(j).isJsonNull()) {

                            JsonObject castingPrObj = castingPrArr.get(j).getAsJsonObject();
                            String actId = castingPrObj.get("id").isJsonNull() ? ("castPrin00" + String.valueOf(i) + String.valueOf(j)) : castingPrObj.get("id").getAsString();
                            Acteur acteur = em.find(Acteur.class, actId);
                            if (acteur == null) {
                                acteur = new Acteur();
                                acteur.setActId(actId);
                                String actIdentite = castingPrObj.get("identite").isJsonNull() ? "" : castingPrObj.get("identite").getAsString();
                                acteur.setActIdentite(actIdentite);
                                Naissance naissance = new Naissance();
                                if (!castingPrObj.get("naissance").isJsonNull()) {
                                    JsonObject naissObj = castingPrObj.get("naissance").getAsJsonObject();
                                    String str = naissObj.get("dateNaissance").isJsonNull() ? "" : naissObj.get("dateNaissance").getAsString();

                                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                                    Date dateNai = str.equals("") ? null : ft.parse(str);
                                    naissance.setDateNaissance(dateNai);
                                    String liueNai = naissObj.get("lieuNaissance").isJsonNull() ? "" : naissObj.get("lieuNaissance").getAsString();
                                    naissance.setLieuNaissance(liueNai);
                                }
                                acteur.setNaissance(naissance);
                                String actUrl = castingPrObj.get("url").isJsonNull() ? "" : castingPrObj.get("url").getAsString();
                                acteur.setActUrl(actUrl);
                                String actHei = castingPrObj.get("height").isJsonNull() ? "" : castingPrObj.get("height").getAsString();
                                acteur.setActHeight(actHei);
                                em.persist(acteur);
                                acteur = em.find(Acteur.class, actId);
                            }
                            acteurSet.add(acteur);
                        }
                    }
                }

                /**
                 * 获取
                 * @param anneeSortie
                 */
                AnneeSortie anneeSortie = new AnneeSortie();
                String str = filmObj.get("anneeSortie").isJsonNull() ? "" : filmObj.get("anneeSortie").getAsString();
                str = str.replaceAll("[^0-9]", "");
                Integer year = str.equals("") ? 0 : Integer.valueOf(str);
                anneeSortie.setAnnee(year);
                AnneeSortie annee = em.find(AnneeSortie.class, year);
                if (annee == null) {
                    em.persist(anneeSortie);
                    anneeSortie = em.find(AnneeSortie.class, year);
                }
                cinema.setAnneeSortie(anneeSortie);

                /**
                 * 获取
                 * @param roleSet
                 */
                Set<Role> roleSet = new HashSet<>();

                if (!filmObj.get("roles").isJsonNull()) {
                    JsonArray rolesArr = filmObj.get("roles").getAsJsonArray();
                    //循环录入
                    for (int j = 0; j < rolesArr.size(); j++) {

                        if (!rolesArr.get(j).isJsonNull()) {
                            Role role = new Role();
                            JsonObject rolesObj = rolesArr.get(j).getAsJsonObject();
                            String characterName = rolesObj.get("characterName").isJsonNull() || rolesObj.get("characterName").getAsString().replaceAll(" ","").equals("")
                                    ? ("charName00" + String.valueOf(i) + String.valueOf(j)) : rolesObj.get("characterName").getAsString();
                            role.setRoleName(characterName);
                            Query query = em.createQuery("select r from Role  r where r.roleName = :name");
                            query.setParameter("name", characterName);
                            query.setMaxResults(1);
                            List<Role> roleList = new ArrayList<>();

                            if (!rolesObj.get("acteur").isJsonNull()) {
                                JsonObject roleActObj = rolesObj.get("acteur").getAsJsonObject();
                                String roleActId = roleActObj.get("id").isJsonNull() || roleActObj.get("id").getAsString().replaceAll(" ", "").equals("")
                                        ? ("tmplRoleAct" + String.valueOf(i) + String.valueOf(j)) : roleActObj.get("id").getAsString();
                                Acteur roleAct = new Acteur();
                                Acteur findAct = roleActObj.get("id").isJsonNull() ? null : em.find(Acteur.class, roleActId);
                                if (findAct == null) {
                                    roleAct.setActId(roleActId);
                                    String actIdentite = roleActObj.get("identite").isJsonNull() ? "" : roleActObj.get("identite").getAsString();
                                    roleAct.setActIdentite(actIdentite);
                                    Naissance naissance = new Naissance();
                                    if (!roleActObj.get("naissance").isJsonNull()) {
                                        JsonObject naissObj = roleActObj.get("naissance").getAsJsonObject();
                                        String str1 = naissObj.get("dateNaissance").isJsonNull() ? "" : naissObj.get("dateNaissance").getAsString();

                                        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd");
                                        Date dateNai = str1.equals("") ? null : ft1.parse(str1);
                                        naissance.setDateNaissance(dateNai);
                                        String liueNai = naissObj.get("lieuNaissance").isJsonNull() ? "" : naissObj.get("lieuNaissance").getAsString();
                                        naissance.setLieuNaissance(liueNai);
                                    }
                                    roleAct.setNaissance(naissance);
                                    String actUrl = roleActObj.get("url").isJsonNull() ? "" : roleActObj.get("url").getAsString();
                                    roleAct.setActUrl(actUrl);
                                    String actHei = roleActObj.get("height").isJsonNull() ? "" : roleActObj.get("height").getAsString();
                                    roleAct.setActHeight(actHei);
                                    em.persist(roleAct);
                                    roleAct = em.find(Acteur.class, roleActId);
                                } else roleAct = findAct;
                                if (rolesObj.get("characterName").isJsonNull()) {
                                    em.persist(role);
                                    role = (Role) query.getSingleResult();
                                } else {
                                    roleList = query.getResultList();
                                    if (roleList.size() != 0) {
                                        role = roleList.get(0);
                                    } else {
                                        em.persist(role);
                                        role = (Role) query.getSingleResult();
                                    }
                                }


                                role.getActeurs().add(roleAct);
                                roleAct.getRoles().add(role);
                                roleSet.add(role);
                                acteurSet.add(roleAct);

                            } else {

                                roleList = query.getResultList();
                                if (roleList.size() != 0) {
                                    role = roleList.get(0);
                                } else {
                                    em.persist(role);
                                    role = (Role) query.getSingleResult();
                                }
                            }
                            roleSet.add(role);
                        }

                    }

                }

                /**
                 * 获取
                 * @param genresSet
                 */

                Set<Genres> genresSet = new HashSet<>();
                if (!filmObj.get("genres").isJsonNull()) {
                    JsonArray genArr = filmObj.get("genres").getAsJsonArray();
                    //循环录入
                    for (int j = 0; j < genArr.size(); j++) {
                        if (!genArr.get(j).isJsonNull()) {
                            Genres genre = new Genres();
                            String genName = genArr.get(j).getAsString();
                            genName.replaceAll(" ", "");
                            if (!genName.equals("")) {
                                genre.setGenName(genName);
                                Query query = em.createQuery("select g from Genres g where g.genName = : name");
                                query.setParameter("name", genName);
                                query.setMaxResults(1);
                                List<Genres> genresList = query.getResultList();
                                if (genresList.size() != 0) {
                                    genre = genresList.get(0);
                                } else {
                                    em.persist(genre);
                                    genre = (Genres) query.getSingleResult();
                                }
                                genresSet.add(genre);
                            }
                        }
                    }
                }
                cinema.setRoles(new HashSet<>(roleSet));
                cinema.setRealisateurs(new HashSet<>(realisateurSet));
                cinema.setGenres(new HashSet<>(genresSet));
                cinema.setActeurs(new HashSet<>(acteurSet));

                if (filmObj.get("id").isJsonNull() || filmObj.get("id").getAsString().equals("")) {
                    em.persist(cinema);
                    cinema = em.find(Cinema.class, id);
                } else if (em.find(Cinema.class, cinema.getCineId()) == null) {
                    em.persist(cinema);
                    cinema = em.find(Cinema.class, id);
                } else {
                    cinema = em.merge(cinema);
                }


                if (pays != null) pays.getCinemas().add(cinema);
                for (Realisateur rea : realisateurSet
                ) {
                    rea.getCinemas().add(cinema);

                }

                for (Acteur a : acteurSet
                ) {
                    a.getCinemas().add(cinema);
                }
                for (Role r : roleSet
                ) {
                    r.getCinemas().add(cinema);
                }
                for (Genres g : genresSet
                ) {
                    g.getCinemas().add(cinema);
                }
                anneeSortie.getCinemas().add(cinema);


                tx.commit();

            }


        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);

        }
    }
}
