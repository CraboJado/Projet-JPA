package fr.digi.imdb.utils;

import fr.digi.imdb.bo.entity.Cinema;
import fr.digi.imdb.dal.jpa.JpaUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class DatasBinderTest {
    private EntityManager em;
    @BeforeEach
    void setUp() {
        em = JpaUtils.getEntityManager();
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @Test
    void getInstance() {
        Cinema cinema = DatasBinder.getInstance("cinema");
        assertInstanceOf(Cinema.class,cinema);
    }


    @Test
    void getList() {
    }


}