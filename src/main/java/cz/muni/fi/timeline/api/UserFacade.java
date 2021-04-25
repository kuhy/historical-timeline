package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Facade for user
 * @author Karolína Veselá
 */
public interface UserFacade {

    UserDTO findUserById(Long userId);

    UserDTO findUserByUsername(String username);


    /**
     * Register user with given unencrypted password.
     * @param u user to be registered
     * @param unencryptedPassword password with which will be the user registered
     */
    void registerUser(UserDTO u, String unencryptedPassword);

    /**
     * Get all registered users
     * @return all users
     */
    Collection<UserDTO> getAllUsers();

    /**
     * Get all registered teachers
     * @return all teachers
     */
    public Collection<UserDTO> getAllTeachers();

    /**
     * Get all registered students
     * @return all students
     */
    public Collection<UserDTO> getAllStudents();

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     * @param u user to be authenticated
     * @return boolean
     */
    boolean authenticate(UserAuthenticateDTO u);

    /**
     * Check if the given user is teacher.
     * @param u user to be checked
     * @return boolean
     */
    boolean isTeacher(UserDTO u);

    /**
     * remove user with given id
     * @param id of the removen user
     */
    public void removeUser(Long id);

}
