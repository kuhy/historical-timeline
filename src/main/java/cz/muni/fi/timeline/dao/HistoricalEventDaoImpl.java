package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.HistoricalEvent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the HistoricalEventDao.
 *
 * @author Karolína Veselá
 */
@Repository
public class HistoricalEventDaoImpl implements HistoricalEventDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(HistoricalEvent historicalEvent) {
    em.persist(historicalEvent);
    }

    @Override
    public List<HistoricalEvent> findAll() {
        return em.createQuery("select he from HistoricalEvent he", HistoricalEvent.class).getResultList();
    }

    @Override
    public Optional<HistoricalEvent> findById(Long id) {
        return Optional.ofNullable(em.find(HistoricalEvent.class, id));
    }

    @Override
    public List<HistoricalEvent> findByName(String name) {
        return em.createQuery("select he from HistoricalEvent he where name = :name", HistoricalEvent.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void update(HistoricalEvent historicalEvent) {
        em.merge(historicalEvent);

    }

    @Override
    public void remove(HistoricalEvent historicalEvent) {
        em.remove(historicalEvent);

    }
}
