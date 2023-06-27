package fr.digi.imdb.dal.jpa;

import fr.digi.imdb.bo.entity.Cinema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class FilmDAO implements IFilmDAO{

    private static final String EXTRAIRE_REQ_Like = "select c from Cinema c where c.cineNom like :cinNom";
    private static EntityManager em = JpaUtils.getEntityManager();

    @Override
    public List<Cinema> getMoviesLike(String identifier) {
        Query query = em.createQuery(EXTRAIRE_REQ_Like);
        query.setParameter("cinNom", identifier);
        return query.getResultList();
    }
}

