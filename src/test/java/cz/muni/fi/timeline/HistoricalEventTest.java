package cz.muni.fi.timeline;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
public class HistoricalEventTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void sampleTest() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // TODO

            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }
}
