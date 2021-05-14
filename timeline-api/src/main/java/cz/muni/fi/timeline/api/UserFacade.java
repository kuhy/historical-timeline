package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;

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
     * update user
     * @param userDTO updated user
     * @return id of the updated user
     */
    Long updateUser(UserDTO userDTO);

    /**
     * Register user with given unencrypted password.
     * @param userCreateDTO user to be registered
     * @param unencryptedPassword password with which will be the user registered
     * @return id of registered user
     */

    Long registerUser(UserCreateDTO userCreateDTO, String unencryptedPassword);

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
     * @param id of the user to be removed
     */
    void removeUser(Long id);
}
