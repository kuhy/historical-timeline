package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.Teacher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TeacherDao.
 *
 * @author Ondřej Kuhejda
 */
@Repository
@Transactional
public class TeacherDaoImpl implements TeacherDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Teacher teacher) {
        em.persist(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return em.createQuery("select t from Teacher t", Teacher.class).getResultList();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return Optional.ofNullable(em.find(Teacher.class, id));
    }

    @Override
    public Optional<Teacher> findByUsername(String username) {
        try {
            return Optional.ofNullable(em.createQuery("select t from Teacher t where t.username = :username",
                    Teacher.class).setParameter("username", username).getSingleResult());
        } catch (NoResultException e) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public void update(Teacher teacher) {
        em.merge(teacher);
    }

    @Override
    public void remove(Teacher teacher) {
        em.remove(teacher);
    }
}
