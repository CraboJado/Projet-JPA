package fr.digi.imdb.dal.jpa;

import fr.digi.imdb.bo.entity.Acteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class ActeurDAO implements IActeurDAO {
    private static EntityManager em = JpaUtils.getEntityManager();
    private static final String EXTRAIRE_REQ_Like ="select a from Acteur a where a.actIdentite like :actIdentite" ;
    @Override
    public List<Acteur> getActorsLike(String identifier) {
        Query query = em.createQuery("select a from Acteur a where a.actIdentite like :actIdentite");
        query.setParameter("actIdentite", "%" + identifier + "%");
        List<Acteur> acteurList = query.getResultList();
        return acteurList;
    }
}
