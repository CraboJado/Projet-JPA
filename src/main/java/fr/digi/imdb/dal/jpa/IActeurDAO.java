package fr.digi.imdb.dal.jpa;

import fr.digi.imdb.bo.entity.Acteur;

import java.util.List;

public interface IActeurDAO {

    List<Acteur> getActorsLike(String identifier);
}
