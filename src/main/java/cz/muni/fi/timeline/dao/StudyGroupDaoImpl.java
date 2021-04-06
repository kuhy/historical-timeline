package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.StudyGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of StudyGroupDao
 * @author Matej Macák
 */
@Repository
@Transactional
public class StudyGroupDaoImpl implements StudyGroupDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(StudyGroup studyGroup) {
        em.persist(studyGroup);
    }

    @Override
    public List<StudyGroup> findAll() {
        return em.createQuery("select sg from StudyGroup sg", StudyGroup.class).getResultList();
    }

    @Override
    public Optional<StudyGroup> findById(Long id) {
        return Optional.ofNullable(em.find(StudyGroup.class, id));
    }

    @Override
    public Optional<StudyGroup> findByName(String name) {
        return Optional.ofNullable(em.createQuery("select sg from StudyGroup sg where sg.name = :name", StudyGroup.class)
        .setParameter("name", name)
        .getSingleResult());
    }

    @Override
    public void update(StudyGroup studyGroup) {
        em.merge(studyGroup);
    }

    @Override
    public void remove(StudyGroup studyGroup) {
        em.remove(studyGroup);
    }
}
