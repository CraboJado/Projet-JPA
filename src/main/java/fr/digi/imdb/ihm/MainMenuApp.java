package fr.digi.imdb.ihm;

import fr.digi.imdb.bo.entity.Acteur;
import fr.digi.imdb.bo.entity.AnneeSortie;
import fr.digi.imdb.bo.entity.Cinema;
import fr.digi.imdb.dal.jpa.JpaUtils;
import jakarta.persistence.EntityManager;

import jakarta.persistence.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MainMenuApp {
    // l'intération avec le client

    private static Scanner scanner = new Scanner(System.in);
    private static EntityManager em = JpaUtils.getEntityManager();

    public static void main(String[] args) {


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
                    listMoviesOfActor(findActor());
                    break;
                case "2":
                    findActorsByMovie();
                    break;
                case "3":
                    findMovieByTwoYears();
                    break;
                case "4":
                    boolean loop = true;
                    findMovieCoop();
                    break;
                case "7":
                    System.out.println("Sign out!");
                    log = false;
                    scanner.close();
                    em.close();


            }
        }
    }


    public static List<Acteur> findActor() {
        String name = "%";
        String[] words = scanner.nextLine().split(" ");
        for (String w : words
        ) {
            if (!w.equals("")) {
                name = name + w + "%";
            }
        }
        Query query = em.createQuery("select a from Acteur a where a.actIdentite like :actIdentite");
        query.setParameter("actIdentite", "%" + name + "%");
        List<Acteur> acteurList = query.getResultList();
        return acteurList;
    }

    public static void listMoviesOfActor(List<Acteur> acteurList) {
        if (acteurList.size() != 0) {
            int count = 1;
            for (int i = 0; i < acteurList.size(); i++) {
                Acteur acteur = acteurList.get(i);
                Set<Cinema> cinemaSet = acteur.getCinemas();
                if (cinemaSet.size() != 0) {
                    for (Cinema c : cinemaSet
                    ) {
                        System.out.println(count++ + " .  " + "Acteur :" + acteur.getActIdentite() + "(" + acteur.getActId() + ")" + "   Cinema : " + c.getCineNom());
                    }
                    System.out.println("Total :" + cinemaSet.size() + " films");
                } else System.out.println("Cet acteur n'a pas de générique de film !");
            }
        } else System.out.println("L'acteur n'a pas été retrouvé !");
    }

    public static void findActorsByMovie() {
        System.out.println("Veuillez entrer un nom de film : ");
        String name = "%";
        String[] words = scanner.nextLine().split(" ");
        for (String w : words
        ) {
            if (!w.equals("")) {
                name = name + w + "%";
            }
        }
        Query query = em.createQuery("select c from Cinema c where c.cineNom like :cinNom");
        query.setParameter("cinNom", name);
        List<Cinema> cinemaList = query.getResultList();
        if (cinemaList.size() != 0) {
            int count = 1;
            for (int i = 0; i < cinemaList.size(); i++) {
                Cinema cinema = cinemaList.get(i);
                System.out.println(count++ + "   Cinema :" + "(" + cinema.getCineId() + ")" + "Nom :" + cinema.getCineNom());
                Set<Acteur> acteurSet = cinema.getActeurs();
                for (Acteur a : acteurSet
                ) {
                    System.out.println("Acteur : " + "(" + a.getActId() + ")" + "Nom : " + a.getActIdentite());
                }
            }
        } else System.out.println("Film introuvable !");
    }

    public static void findMovieByTwoYears() {
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
        Query query = em.createQuery("select a from AnneeSortie a where a.annee>= :yearBegin and a.annee <= :yearEnd");
        query.setParameter("yearBegin", yearBegin);
        query.setParameter("yearEnd", yearEnd);
        List<AnneeSortie> anneeSortieList = query.getResultList();
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
        } else System.out.println("Aucun film entre : " + yearBegin + " et " + yearEnd);
    }

    public static boolean findMovieCoop() {
        Acteur acteur1 = new Acteur();
        Acteur acteur2 = new Acteur();
        System.out.println("Veuillez entrer le premier acteur");
        List<Acteur> acteurList1 = findActor();
        acteur1 = confirmActor(acteurList1);
        if (acteur1 == null) return false;
        System.out.println("Veuillez entrer le deuxième  acteur");
        List<Acteur> acteurList2 = findActor();
        acteur2 = confirmActor(acteurList2);
        if (acteur2 == null) return false;
        Set<Cinema> cineSet1 = acteur1.getCinemas();
        Set<Cinema> cineSet2 = acteur2.getCinemas();
        cineSet1.retainAll(cineSet2);
        if (cineSet1.size() ==0){
            System.out.println("Deux acteurs n'ont pas joué dans le même film .");
            return false;
        }else{
            int count = 1;
            for (Cinema c:cineSet1
                 ) {
                System.out.println(count++ + "  Cinema : "+c.getCineNom()+" ("+ c.getCineId()+")");
            }
        }

        return true;
    }


    public static Acteur confirmActor(List<Acteur> acteurList) {
        if (acteurList.size() == 0) {
            System.out.println("Acteur non reconnu !");
            return null;
        } else if (acteurList.size() > 1) {
            int count = 1;
            for (Acteur a : acteurList
            ) {
                System.out.println(count++ + "  " + a.getActIdentite() + " (" + a.getActId() + ")");
            }
            System.out.println("Nous avons trouvé plus d'un acteur . \n " +
                    "Veuillez sélectionner le numéro de l'acteur que vous souhaitez rechercher.");
            String number = scanner.nextLine();
            number = number.replaceAll("[^0-9]", "");
            if (number != "") {
                Integer numberInt = Integer.valueOf(number);
                if (numberInt >= 1 && numberInt <= acteurList.size()) {
                    return acteurList.get(numberInt - 1);
                } else {
                    System.out.println("Option non reconnue !");
                    return null;
                }
            } else {
                System.out.println("Option non reconnue !");
                return null;
            }
        } else return acteurList.get(0);


    }

}


