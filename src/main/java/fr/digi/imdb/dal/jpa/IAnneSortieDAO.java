package fr.digi.imdb.dal.jpa;

import fr.digi.imdb.bo.entity.AnneeSortie;

import java.util.List;

public interface IAnneSortieDAO {
    List<AnneeSortie> getYearsBtw(Integer begin,Integer after);
}
