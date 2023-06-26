package test;

import fr.digi.imdb.bo.entity.Acteur;
import fr.digi.imdb.bo.entity.Cinema;
import fr.digi.imdb.bo.entity.Genres;
import fr.digi.imdb.bo.entity.Realisateur;
import jakarta.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.digi.imdb.dal.jpa.JpaUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class ImdbTest {
    private EntityManager em;


   @Before
    public void init() {
        em = JpaUtils.getEntityManager();
    }

    @Test
    public void testDate() {
        /*Date date;
        String str = "2023-6-5";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = ft.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ft.format(date));*/
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate);

    }

    @Test
    public void JpaUtilsTest() {
        System.out.println("*******"+1+2+3);
    }


    @Test
    public void ralationTest(){
        Acteur act =em.find(Acteur.class,"nm0000156");
        Cinema cinema = em.find(Cinema.class,"tt4975920");
        Realisateur realisateur =em.find(Realisateur.class,2);
        Genres genres = em.find(Genres.class,1);
        for (Cinema x: realisateur.getCinemas()
             ) {
            System.out.println(x);
        }

    }






       @After
        public void close () {
            em.close();
        }
    }
