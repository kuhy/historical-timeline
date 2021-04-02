package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.TimelineComment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TimelineCommentDaoImpl implements TimelineCommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(TimelineComment timelineComment) {
        em.persist(timelineComment);
    }

    @Override
    public Optional<TimelineComment> findById(Long id) {
        return Optional.ofNullable(em.find(TimelineComment.class, id));
    }

    @Override
    public void update(TimelineComment timelineComment) {
        em.merge(timelineComment);
    }

    @Override
    public void remove(TimelineComment timelineComment) {
        em.remove(timelineComment);
    }

    @Override
    public List<TimelineComment> FindAll() {
        return em.createQuery("select tm from TimelineComment tm", TimelineComment.class).getResultList();
    }
}
