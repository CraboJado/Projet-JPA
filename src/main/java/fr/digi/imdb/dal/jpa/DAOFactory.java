package fr.digi.imdb.dal.jpa;

public class DAOFactory {
    private static final String MODE_JPA = "JPA";
    private static final String MODE_JDBC = "JDBC";
    private static final String MODE_XML = "XML";
    private static final String MODE_COURANT = "JPA";

    private DAOFactory(){}

    public static IFilmDAO getFilmDAO(){
        IFilmDAO dao = null;
        switch (MODE_COURANT) {
            case MODE_JPA -> dao = new FilmDAO();
            default -> throw new RuntimeException("Mode non implémenté !!!");
        }
        return dao;
    }

    public static IActeurDAO getActeurDAO(){
        IActeurDAO dao = null;
        switch (MODE_COURANT) {
            case MODE_JPA -> dao = new ActeurDAO();
            default -> throw new RuntimeException("Mode non implémenté !!!");
        }
        return dao;
    }

    public static IAnneSortieDAO getAnneDAO(){
        IAnneSortieDAO dao = null;
        switch (MODE_COURANT) {
            case MODE_JPA -> dao = new AnneSortieDAO();
            default -> throw new RuntimeException("Mode non implémenté !!!");
        }
        return dao;
    }
}
