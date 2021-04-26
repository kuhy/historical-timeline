package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of StudentDao
 * @author Matej Macák
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> findByUserName(String username) {
        try {
            return Optional.ofNullable(em.createQuery("select u from User u where u.username = :username",
                User.class).setParameter("username", username).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void remove(User user) {
        if (em.contains(user)) {
            em.remove(user);
        } else {
            em.remove(em.merge(user));
        }
    }

    @Override
    public List<User> findAllStudents() {
        return em.createQuery("select u from User u where u.isTeacher = false", User.class).getResultList();
    }

    @Override
    public List<User> findAllTeachers() {
        return em.createQuery("select u from User u where u.isTeacher = true", User.class).getResultList();
    }
}
