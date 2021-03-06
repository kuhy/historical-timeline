package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.HistoricalTimeline;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of HistoricalTimelineDao.
 *
 * @author Tri Le Mau
 */
@Repository
public class HistoricalTimelineDaoImpl implements HistoricalTimelineDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(HistoricalTimeline historicalTimeline) {
        em.persist(historicalTimeline);
    }

    @Override
    public Optional<HistoricalTimeline> findById(Long id) {
        return Optional.ofNullable(em.find(HistoricalTimeline.class, id));
    }

    @Override
    public void update(HistoricalTimeline historicalTimeline) {
        em.merge(historicalTimeline);
    }

    @Override
    public void remove(HistoricalTimeline historicalTimeline) {
        if (em.contains(historicalTimeline)) {
            em.remove(historicalTimeline);
        } else {
            em.remove(em.merge(historicalTimeline));
        }
    }

    @Override
    public List<HistoricalTimeline> findAll() {
        return em.createQuery("select ht from HistoricalTimeline ht", HistoricalTimeline.class).getResultList();
    }

    @Override
    public List<HistoricalTimeline> findByName(String name) {
        return em.createQuery("select ht from HistoricalTimeline ht where name = :name", HistoricalTimeline.class)
            .setParameter("name", name).getResultList();
    }
}
