package fr.digi.imdb.dal.jpa;

import fr.digi.imdb.bo.entity.Cinema;

import java.util.List;

public interface IFilmDAO {
    List<Cinema> getMoviesLike(String identifier);

}
