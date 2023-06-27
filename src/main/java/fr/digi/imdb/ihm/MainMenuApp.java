package fr.digi.imdb.ihm;

import fr.digi.imdb.bo.entity.Acteur;
import fr.digi.imdb.bo.entity.AnneeSortie;
import fr.digi.imdb.bo.entity.Cinema;
import fr.digi.imdb.dal.jpa.*;
import jakarta.persistence.EntityManager;

import jakarta.persistence.Query;

import java.util.*;

public class MainMenuApp {
    // l'intération avec le client

    private static Scanner scanner = new Scanner(System.in);
    private static EntityManager em = JpaUtils.getEntityManager();

    public static void main(String[] args) {
        IActeurDAO acteurDao = DAOFactory.getActeurDAO();
        IFilmDAO filmDAO = DAOFactory.getFilmDAO();
        IAnneSortieDAO anneDAO = DAOFactory.getAnneDAO();

        Boolean log = true;
        while (log) {

            System.out.println("**************************************************\n" +
                    "1. Affichage de la filmographie d’un acteur donné\n" +
                    "2. Affichage du casting d’un film donné\n" +
                    "3. Affichage des films sortis entre 2 années données\n" +
                    "4. Affichage des films communs à 2 acteurs/actrices donnés.\n" +
                    "5. Affichage des acteurs communs à 2 films donnés\n" +
                    "6. Affichage des films sortis entre 2 années données et qui ont un acteur/actrice donné au casting\n" +
                    "7. Fin de l’application\n" +
                    "**************************************************");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Veuillez saisir le nom de l'acteur : ");
                    listMoviesOfActor(confirm(findActor(acteurDao)));
                    break;
                case "2":
                    System.out.println("Veuillez entrer un nom de film : ");
                    listActorOfMovie(confirm(findMovie(filmDAO)));
                    break;
                case "3":
                    listMoviesOfTowYear(findMoviesByTwoYears(anneDAO));
                    break;
                case "4":
                    findCommonMovie(acteurDao);
                    break;
                case "5":
                    findCommonActors(filmDAO);
                    break;
                case "6":
                    finMovBetTowYeByActor(acteurDao,anneDAO);
                    break;
                case "7":
                    System.out.println("Sign out!");
                    log = false;
                    scanner.close();
                    em.close();
            }
        }
    }


    public static List<Acteur> findActor(IActeurDAO acteurDao) {
        String name = "%";
        String[] words = scanner.nextLine().split(" ");
        for (String w : words
        ) {
            if (!w.equals("")) {
                name = name + w + "%";
            }
        }
        return acteurDao.getActorsLike(name);
    }


    public static void listMoviesOfActor(Acteur acteur) {
        if (acteur != null) {
            Set<Cinema> cinemaSet = acteur.getCinemas();
            int count = 1;
            if (cinemaSet.size() != 0) {
                for (Cinema c : cinemaSet
                ) {
                    System.out.println(count++ + " .  " + "Acteur :" + acteur.getActIdentite() + "(" + acteur.getActId() + ")" + "   Cinema : " + c.getCineNom());
                }
                System.out.println("Total :" + cinemaSet.size() + " films");
            } else System.out.println("Cet acteur n'a pas de générique de film !");
        }
    }

    public static List<Cinema> findMovie(IFilmDAO filmDAO) {

        String name = "%";
        String[] words = scanner.nextLine().split(" ");
        for (String w : words
        ) {
            if (!w.equals("")) {
                name = name + w + "%";
            }
        }
        return filmDAO.getMoviesLike(name);

    }


    public static void listActorOfMovie(Cinema cinema) {
        if (cinema != null) {
            System.out.println("   Cinema :" + "(" + cinema.getCineId() + ")" + "Nom :" + cinema.getCineNom());
            Set<Acteur> acteurSet = cinema.getActeurs();
            for (Acteur a : acteurSet
            ) {
                System.out.println("Acteur : " + "(" + a.getActId() + ")" + "Nom : " + a.getActIdentite());
            }
        }
    }

    public static List<AnneeSortie> findMoviesByTwoYears(IAnneSortieDAO anneDAO) {
        boolean loop = true;
        String year1 = "";
        String year2 = "";
        while (loop) {
            System.out.println("Veuillez entrer la première année : ");
            year1 = scanner.nextLine();
            year1 = year1.replaceAll("[^0-9]", "");
            if (!year1.equals("")) {
                loop = false;
            } else {
                System.out.println("Ce n'est pas une année, merci de saisir à nouveau !");
            }
        }
        loop = true;
        while (loop) {
            System.out.println("Veuillez entrer la deuxième  année : ");
            year2 = scanner.nextLine();
            year2 = year2.replaceAll("[^0-9]", "");
            if (!year2.equals("")) {
                loop = false;
            } else {
                System.out.println("Ce n'est pas une année, merci de saisir à nouveau !");
            }
        }

        Integer year1Int = Integer.valueOf(year1);
        Integer year2Int = Integer.valueOf(year2);
        Integer yearBegin = year1Int >= year2Int ? year2Int : year1Int;
        Integer yearEnd = yearBegin == year1Int ? year2Int : year1Int;

        List<AnneeSortie> anneeSortieList = anneDAO.getYearsBtw(yearBegin,yearEnd);
        if (anneeSortieList.size() == 0) System.out.println("Aucun film entre : " + yearBegin + " et " + yearEnd);
        return anneeSortieList;
    }

    public static void listMoviesOfTowYear(List<AnneeSortie> anneeSortieList) {
        if (anneeSortieList.size() != 0) {
            int count = 1;
            for (AnneeSortie a : anneeSortieList
            ) {

                Set<Cinema> cinemaSet = a.getCinemas();
                for (Cinema c : cinemaSet
                ) {
                    System.out.println(count++ + "  Nom : " + c.getCineNom() + "(" + c.getCineId() + ")" + "AnneeSortie : " + a.getAnnee());
                }
            }

        }
    }

    public static boolean findCommonMovie(IActeurDAO acteurDao) {
        Acteur acteur1 = new Acteur();
        Acteur acteur2 = new Acteur();
        System.out.println("Veuillez entrer le premier acteur");
        acteur1 = confirm(findActor(acteurDao));
        if (acteur1 == null) return false;
        System.out.println("Veuillez entrer le deuxième  acteur");
        acteur2 = confirm(findActor(acteurDao));
        if (acteur2 == null) return false;
        Set<Cinema> cineSet1 = acteur1.getCinemas();
        Set<Cinema> cineSet2 = acteur2.getCinemas();
        cineSet1.retainAll(cineSet2);
        if (cineSet1.size() == 0) {
            System.out.println("Deux acteurs n'ont pas joué dans le même film .");
            return false;
        } else {
            int count = 1;
            for (Cinema c : cineSet1
            ) {
                System.out.println(count++ + "  Cinema : " + c.getCineNom() + " (" + c.getCineId() + ")");
            }
        }

        return true;
    }

    public static boolean findCommonActors(IFilmDAO filmDAO) {

        Cinema cinema1 = new Cinema();
        Cinema cinema2 = new Cinema();
        System.out.println("Veuillez entrer le premier cinema");
        cinema1 = confirm(findMovie(filmDAO));
        if (cinema1 == null) return false;
        System.out.println("Veuillez entrer le deuxième  cinema");
        cinema2 = confirm(findMovie(filmDAO));
        if (cinema2 == null) return false;
        Set<Acteur> acteurSet1 = cinema1.getActeurs();
        Set<Acteur> acteurSet2 = cinema2.getActeurs();
        acteurSet1.retainAll(acteurSet2);
        if (acteurSet1.size() == 0) {
            System.out.println("Deux cinemas n'ont pas le même acteur .");
            return false;
        } else {
            int count = 1;
            for (Acteur a : acteurSet1
            ) {
                System.out.println(count++ + "  Acteur : " + a.getActIdentite() + " (" + a.getActId() + ")");
            }
        }

        return true;
    }

    public static boolean finMovBetTowYeByActor(IActeurDAO acteurDao,IAnneSortieDAO anneDAO) {
        List<AnneeSortie> anneeSortieList = findMoviesByTwoYears(anneDAO);
        if (anneeSortieList.size() == 0) return false;
        System.out.println("Veuillez saisir le nom de l'acteur : ");
        Acteur acteur = confirm(findActor(acteurDao));
        if (acteur == null) return false;
        Set<Cinema> cinemaSet = new HashSet<>();
        for (AnneeSortie a : anneeSortieList
        ) {
            cinemaSet.addAll(a.getCinemas());
        }
        cinemaSet.retainAll(acteur.getCinemas());
        if (cinemaSet.size() == 0) {
            System.out.println("Aucun film entre : " + anneeSortieList.get(0).getAnnee() +
                    " et " + anneeSortieList.get(anneeSortieList.size() - 1).getAnnee() +
                    " Joué par " + acteur.getActIdentite() + "(" + acteur.getActId() + ")");
        } else {
            int count = 1;
            for (Cinema c : cinemaSet
            ) {
                System.out.println(count++ + "  Nom : " + c.getCineNom() + "(" + c.getCineId() + ")" + "AnneeSortie : " + c.getAnneeSortie().getAnnee());
            }
        }
        return true;
    }


    public static <T> T confirm(List<T> list) {

        if (list.size() == 0) {
            System.out.println("Entrée non reconnu !");
            return null;
        } else if (list.size() > 1) {
            int count = 1;
            for (T t : list
            ) {
                System.out.println(count++ + "  " + t);
            }
            System.out.println("Nous avons trouvé plus d'un " + list.get(0).getClass().getSimpleName() + " . \n " +
                    "Veuillez sélectionner le numéro de l'acteur que vous souhaitez rechercher.");
            String number = scanner.nextLine();
            number = number.replaceAll("[^0-9]", "");
            if (number != "") {
                Integer numberInt = Integer.valueOf(number);
                if (numberInt >= 1 && numberInt <= list.size()) {
                    return list.get(numberInt - 1);
                } else {
                    System.out.println("Option non reconnue !");
                    return null;
                }
            } else {
                System.out.println("Option non reconnue !");
                return null;
            }
        } else return list.get(0);
    }

    public static <T> void showList(Set<T> set) {
        String tClass;
        int count = 1;
        if (set.size() != 0) {
            for (T t : set
            ) {
                System.out.println(count++ + " .  " + t);
            }
            System.out.println("Total :" + set.size()  );
        } else System.out.println("Cet acteur n'a pas de générique de film !");
    }

}


