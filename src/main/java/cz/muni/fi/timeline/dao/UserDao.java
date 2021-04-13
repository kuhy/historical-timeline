package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for entity Student
 * @author Matej Macák
 *
 * TODO return all teachers, students
 * TODO change javadoc
 */
public interface UserDao {

    /**
     * Adds Student into database
     * @param user entity that is added to the database
     */
    void create(User user);

    /**
     * Returns all Students from database
     * @return all students from database
     */
    List<User> findAll();

    /**
     * Finds Student based on ID
     * @param id id of Student
     * @return Student with id
     */
    Optional<User> findById(Long id);

    /**
     * Finds Student based on ID
     * @param username username of looking Student
     * @return Student with given username
     */
    Optional<User> findByUserName(String username);

    /**
     * Updates Student in the database
     * @param user is the updated entity
     */
    void update(User user);

    /**
     * Removes Student from the database
     * @param user that is removed
     */
    void remove(User user);
}
