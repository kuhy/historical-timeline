package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Facade for user
 * @author Karolína Veselá
 */
public interface UserFacade {
    /**
     * gives user with given id
     * @param userId userId of user
     * @return user with given id
     */

    Optional <UserDTO> findUserById(Long userId);

    /**
     * gives user with given username
     * @param username username of returned user
     * @return user with given username
     */
    Optional <UserDTO> findUserByUsername(String username);


    /**
     * Register user with given unencrypted password.
     * @param userDTO user to be registered
     * @param unencryptedPassword password with which will be the user registered
     */
    void registerUser(UserDTO userDTO, String unencryptedPassword);

    /**
     * Get all registered users
     * @return all users
     */
    List<UserDTO> getAllUsers();

    /**
     * Get all registered teachers
     * @return all teachers
     */
    List<UserDTO> getAllTeachers();

    /**
     * Get all registered students
     * @return all students
     */
    List<UserDTO> getAllStudents();

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     * @param userAuthenticateDTO user to be authenticated
     * @return boolean
     */
    boolean authenticate(UserAuthenticateDTO userAuthenticateDTO);

    /**
     * Check if the given user is teacher.
     * @param userDTO user to be checked
     * @return boolean
     */
    boolean isTeacher(UserDTO userDTO);

    /**
     * remove user with given id
     * @param id of the removen user
     */
    void removeUser(Long id);

}
