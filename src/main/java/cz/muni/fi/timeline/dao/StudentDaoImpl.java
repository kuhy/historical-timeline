package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of StudentDao
 * @author Matej Macák
 */
@Repository
public class StudentDaoImpl implements StudentDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Student student) {
        em.persist(student);
    }

    @Override
    public List<Student> findAll() {
        return em.createQuery("select s from Student s", Student.class).getResultList();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    @Override
    public Optional<Student> findByUserName(String username) {
        return Optional.ofNullable(em.createQuery("select s from Student s where s.username = :username", Student.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    @Override
    public void update(Student student) {
        em.merge(student);
    }

    @Override
    public void remove(Student student) {
        em.remove(student);
    }
}
