package fr.digi.imdb.dal.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtils {
    private static EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory("imdb");
    }
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}