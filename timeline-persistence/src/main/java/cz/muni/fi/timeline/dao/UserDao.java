package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for entity User
 * @author Matej Macák
 */
public interface UserDao {

    /**
     * Adds User into database
     * @param user entity that is added to the database
     */
    void create(User user);

    /**
     * Returns all Users from database
     * @return all users from database
     */
    List<User> findAll();

    /**
     * Finds User based on ID
     * @param id id of User
     * @return User with id
     */
    Optional<User> findById(Long id);

    /**
     * Finds User based on ID
     * @param username username of looking User
     * @return User with given username
     */
    Optional<User> findByUserName(String username);

    /**
     * Updates User in the database
     * @param user is the updated entity
     */
    void update(User user);

    /**
     * Removes User from the database
     * @param user that is removed
     */
    void remove(User user);

    /**
     * Returns all Users that are students (they are not teachers)
     * @return all users that are students (they are not teachers)
     */
    List<User> findAllStudents();

    /**
     * Returns all Users that are teachers (they are not students)
     * @return all users that are teachers (they are not students)
     */
    List<User> findAllTeachers();
}
