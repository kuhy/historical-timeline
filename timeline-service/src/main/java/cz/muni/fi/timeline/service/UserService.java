package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a service access to User entity.
 *
 * @author Tri Le Mau
 */
public interface UserService {

    /**
     * Register an User with the given unencrypted password.
     *
     * @param user User to be registered
     * @param unencryptedPassword unencrypted password of the User
     */
    void registerUser(User user, String unencryptedPassword);

    /**
     * Login user with given password.
     *
     * @param user User to be logged in
     * @param password password used for User login
     * @return True if user login was successful, False otherwise
     */
    boolean loginUser(User user, String password);

    /**
     * Logs out current User
     */
    void logoutUser();

    /**
     * Returns current user that is logged in.
     *
     * @return current user if logged in, empty otherwise
     */
    Optional<User> getLoggedInUser();

    /**
     * Checks if an User is currently logged in.
     *
     * @return true if an User is logged in, otherwise false
     */
    boolean isUserLoggedIn();

    /**
     * Updates User information.
     *
     * @param user User with updated information
     */
    void updateUser(User user);

    /**
     * Removes User.
     *
     * @param user User to be removed
     */
    void removeUser(User user);

    /**
     * Finds User based on Id.
     *
     * @param id Id used for finding User
     * @return User
     */
    Optional<User> findById(Long id);

    /**
     * Finds User based on Username.
     *
     * @param username username used for finding User
     * @return User
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if User is a Teacher.
     *
     * @param user User to be checked
     * @return True if User is a Teacher, False otherwise
     */
    boolean isTeacher(User user);

    /**
     * Get all Users.
     *
     * @return All Users
     */
    List<User> getAllUsers();

    /**
     * Get all users which are Students.
     *
     * @return All Students
     */
    List<User> getAllStudents();

    /**
     * Get all users which are Teachers.
     *
     * @return All Teachers
     */
    List<User> getAllTeachers();
}
