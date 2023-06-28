package fr.digi.imdb.dal.jpa;

import fr.digi.imdb.bo.entity.AnneeSortie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class AnneSortieDAO implements IAnneSortieDAO{
    private static EntityManager em = JpaUtils.getEntityManager();
    private static final String EXTRAIRE_REQ ="select a from AnneeSortie a where a.annee>= :yearBegin and a.annee <= :yearEnd order by a.annee";
    @Override
    public List<AnneeSortie> getYearsBtw(Integer begin,Integer after) {
        Query query = em.createQuery(EXTRAIRE_REQ);
        query.setParameter("yearBegin", begin);
        query.setParameter("yearEnd", after);
        return query.getResultList();
    }
}
