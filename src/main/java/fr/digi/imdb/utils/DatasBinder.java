package fr.digi.imdb.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.digi.imdb.bo.classEmbeddable.LieuTournage;
import fr.digi.imdb.bo.classEmbeddable.Naissance;
import fr.digi.imdb.bo.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Set;

public class DatasBinder {

    static Integer count = 0;

    public static<T> T getInstance(String key){
        switch (key){
            case "cinema" -> {return (T) new Cinema();}
            case "pays" -> {return (T) new Pays();}
            case "lieuTournage" -> { return (T) new LieuTournage();}
            case "realisateurs" -> {return (T) new Realisateur();}
            case "castingPrincipal" -> {return (T) new Acteur();}
            case "naissance" -> {return (T) new Naissance();}
            case "anneeSortie" -> {return (T) new AnneeSortie();}
            case "roles" -> {return (T) new Role();}
            case "acteur" -> {return (T) new Acteur();}
            default -> throw new IllegalStateException("Invalid key: " + key);
        }
    }

    public static List getList(String key,JsonObject jsonObj, EntityManager em){

        if(key.equals("realisateurs")){
            String identite = jsonObj.get("identite").getAsString();
            Query reaQuery = em.createQuery("select r from Realisateur r where r.reaIdentite = :name");
            reaQuery.setParameter("name",identite);
            List<Realisateur> realisaterus = reaQuery.getResultList();
            return realisaterus;
        }

        if(key.equals("castingPrincipal")){
            String id = jsonObj.get("id").getAsString();
            Query reaQuery = em.createQuery("select a from Acteur a where a.actId = :id");
            reaQuery.setParameter("id",id);
            List<Acteur> acteurs = reaQuery.getResultList();
            return acteurs;
        }

        if(key.equals("roles")){
            String name = jsonObj.get("characterName").getAsString();
            Query reaQuery = em.createQuery("select r from Role r where r.roleName = :name");
            reaQuery.setParameter("name",name);
            List<Role> roles = reaQuery.getResultList();
            return roles;
        }

        if(key.equals("pays")){
            String name = jsonObj.get("nom").getAsString();
            Query query = em.createQuery("select p from Pays p where p.paysNom = :name");
            query.setParameter("name", name);
            List<Pays> paysList = query.getResultList();
            return paysList;
        }

        if(key.equals("acteur")){
            String id = jsonObj.get("id").getAsString();
            Query acQuery = em.createQuery("select a from Acteur a where a.actId = :id");
            acQuery.setParameter("id",id);
            List<Acteur> acteurs = acQuery.getResultList();
            return acteurs;
        }

        return null;
    }


    public static void myIteratorArr(JsonArray jsonArr, ISetAttribute obj, EntityManager em, String key){
        for (int i = 0; i < jsonArr.size(); i++) {
            if(jsonArr.get(i).isJsonObject()){
                // jsonObject = role Obj ,cinema = obj
                JsonObject jsonObject = jsonArr.get(i).getAsJsonObject();
                Object instance = getInstance(key);
                myIteratorJsonObj(jsonObject, (ISetAttribute) instance,em);
                List list = getList(key, jsonObject, em);
                if(list.size() == 0){
                    obj.setGenericAttribute(key,instance);
                    em.persist(instance);
//                    instance = getList(key, jsonObject, em).get(0);
                }else {
                    obj.setGenericAttribute(key,list.get(0));
                }

            }
        }


    }

    public static void myIteratorJsonObj(JsonObject jsonObj,ISetAttribute obj,EntityManager em){

        Set<String> objKeys = jsonObj.keySet();

        for (String objKey:objKeys
        ) {
            if(jsonObj.get(objKey).isJsonNull() || objKey.equals("film")){
                continue;
            }

            if(jsonObj.get(objKey).isJsonPrimitive()){

                if(jsonObj.get(objKey).getAsString().equals("")){
                    if(objKey.equals("identite") || objKey.equals("id") ||
                            objKey.equals("characterName") || objKey.equals("nom")){

                        String value = jsonObj.get(objKey).getAsString() + "emptyRandom" + count++;
                        obj.setGenericAttribute(objKey,value);
                        em.persist(obj);

                    }

                    if(objKey.equals("anneeSortie")){
                        Integer year = 0;
                        AnneeSortie annee = em.find(AnneeSortie.class, year);
                        if(annee == null){
                            annee = new AnneeSortie(year);
                            em.persist(annee);
                        }
                        obj.setGenericAttribute(objKey,annee);
                    }
                }else if(objKey.equals("anneeSortie")){

                    String str = jsonObj.get(objKey).getAsString().replaceAll("[^0-9]", "");
                    Integer year = Integer.valueOf(str);
                    AnneeSortie annee = em.find(AnneeSortie.class, year);
                    if(annee == null){
                        annee = new AnneeSortie(year);
                        em.persist(annee);
                    }
                    obj.setGenericAttribute(objKey,annee);

                }else if(!objKey.equals("film")){

                    String value = jsonObj.get(objKey).getAsString();
                    obj.setGenericAttribute(objKey,value);

                }

            }

            if(jsonObj.get(objKey).isJsonObject()){

                JsonObject subJsonObj = jsonObj.get(objKey).getAsJsonObject();

                if(objKey.equals("pays")){

                    List list = getList(objKey, subJsonObj, em);
                    Object instance = getInstance(objKey);
                    myIteratorJsonObj(subJsonObj, (ISetAttribute) instance,em);


                    if(list.size() == 0){
                        obj.setGenericAttribute(objKey,instance);
                        em.persist(instance);
                    }else {
                        obj.setGenericAttribute(objKey,list.get(0));
                    }

                }

                if(objKey.equals("lieuTournage")){;

                    LieuTournage lieuTou = new LieuTournage();
                    myIteratorJsonObj(subJsonObj,lieuTou,em);
                    obj.setGenericAttribute(objKey,lieuTou);
                }

                if(objKey.equals("acteur")){

                    List list = getList(objKey, subJsonObj, em);

                    Object instance = getInstance(objKey);
                    myIteratorJsonObj(subJsonObj, (ISetAttribute) instance,em);

                    if(list.size() == 0){

                        obj.setGenericAttribute(objKey,instance);
                        em.persist(instance);
                    }else obj.setGenericAttribute(objKey,list.get(0));

                }

                if(objKey.equals("naissance")){

                    Naissance naissance = new Naissance();
                    myIteratorJsonObj(subJsonObj,naissance,em);
                    obj.setGenericAttribute(objKey,naissance);
                }
            }

            if(jsonObj.get(objKey).isJsonArray()){
                JsonArray subJsonArr = jsonObj.get(objKey).getAsJsonArray();

                if(objKey.equals("realisateurs")){

                    myIteratorArr(subJsonArr,obj,em,objKey);
                }

                if(objKey.equals("castingPrincipal")){

                    myIteratorArr(subJsonArr,obj,em,objKey);
                }

                if(objKey.equals("roles") ){
                    if(subJsonArr.size() == 0){
                        continue;
                    }
                    myIteratorArr(subJsonArr,obj,em,objKey);
                }

                if(objKey.equals("genres")){

                    for (int j = 0; j < subJsonArr.size(); j++) {
                        if(subJsonArr.get(j).isJsonPrimitive()){
                            String name = subJsonArr.get(j).getAsString();
                            Query reaQuery = em.createQuery("select g from Genres g where g.genName = :name");
                            reaQuery.setParameter("name",name);
                            List<Genres> genresList = reaQuery.getResultList();
                            if(genresList.size() == 0) {
                                Genres genres = new Genres(name);
                                obj.setGenericAttribute(objKey,genres);
                                em.persist(genres);
//                                genres = (Genres) reaQuery.getResultList().get(0);
                            }else obj.setGenericAttribute(objKey,genresList.get(0));
                        }
                    }
                }
            }

            if(jsonObj.get(objKey).isJsonNull() && objKey.equals("id")){
                // 2 solution :
                // 1. on cree un id autoincrémenter dans la base de donnés(PK), mais on garde aussi le "id" d'un objet dans le fichier Json
                // 2. on donne un id temporaire et garde le "id" en tant que PK.
            }
        }
    }
}
