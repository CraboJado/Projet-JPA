package fr.digi.imdb.utils;


import com.google.gson.JsonArray;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.digi.imdb.bo.entity.*;
import fr.digi.imdb.dal.jpa.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


import java.io.FileReader;

import static fr.digi.imdb.utils.DatasBinder.myIteratorJsonObj;

public class InitMysql {

    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        try (EntityManager em = JpaUtils.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            JsonArray films = (JsonArray) parser.parse(new FileReader("films.json"));

            for (int i = 0; i < 2; i++) {
                Cinema cinema = new Cinema();
                tx.begin();
                if(films.get(i).isJsonObject()){
                    JsonObject filmJsonObj = films.get(i).getAsJsonObject();
                    myIteratorJsonObj(filmJsonObj,cinema,em);
                }
                em.persist(cinema);
                tx.commit();
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
