package test;

import classEmbeddable.LieuTournage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.JpaUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


public class ImdbTest {
    private EntityManager em;


    @Before
    public void init() {
        em = JpaUtils.getEntityManager();
    }

    @Test
    public void testDate() {
        Date date;
        String str = "2023-6-5";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = ft.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ft.format(date));
    }

    @Test
    public void JpaUtilsTest() {
        System.out.println(em);
    }




        @After
        public void close () {
            em.close();
        }
    }
