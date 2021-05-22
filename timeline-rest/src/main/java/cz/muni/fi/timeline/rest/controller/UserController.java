package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.UserFacade;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.api.dto.UserLoginDTO;
import cz.muni.fi.timeline.rest.assembler.UserModelAssembler;
import cz.muni.fi.timeline.rest.exception.InvalidLoginCredentialsException;
import cz.muni.fi.timeline.rest.exception.ResourceAlreadyExistsException;
import cz.muni.fi.timeline.rest.exception.ResourceNotFoundException;
import cz.muni.fi.timeline.rest.exception.UserAlreadyLoggedInException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Users
 *
 * @author Tri Le Mau
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserFacade userFacade;
    private final UserModelAssembler userModelAssembler;

    @Inject
    public UserController(UserFacade userFacade, UserModelAssembler userModelAssembler) {
        this.userFacade = userFacade;
        this.userModelAssembler = userModelAssembler;
    }

    /**
     * Get all Users.
     *
     * @return all users
     */
    @RolesAllowed("ROLE_TEACHER")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllUsers() {
        List<UserDTO> allUsers = userFacade.getAllUsers();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    /**
     * Get all Teachers.
     *
     * @return all teachers
     */
    @RolesAllowed("ROLE_TEACHER")
    @GetMapping(value = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllTeachers() {
        List<UserDTO> allUsers = userFacade.getAllTeachers();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    /**
     * Get all Students.
     *
     * @return all students
     */
    @RolesAllowed("ROLE_TEACHER")
    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllStudent() {
        List<UserDTO> allUsers = userFacade.getAllStudents();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    /**
     * Get User with given id.
     *
     * @param id User's id
     * @return User with given id
     */
    @RolesAllowed("ROLE_TEACHER")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<UserDTO>> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userFacade.findUserById(id);

        if (userDTO.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }

    /**
     * Get User with given username.
     *
     * @param username User's username
     * @return User with given username
     */
    @RolesAllowed("ROLE_TEACHER")
    @GetMapping(value = "/by_username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<UserDTO>> getUserByUsername(@PathVariable String username) {
        Optional<UserDTO> userDTO = userFacade.findUserByUsername(username);

        if (userDTO.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }

    /**
     * Updates User.
     *
     * @param user User to be updated
     * @return Id of updated User
     */
    @RolesAllowed("ROLE_TEACHER")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Long> updateUser(@RequestBody UserDTO user, @PathVariable Long id) {
        user.setId(id);
        return new ResponseEntity<>(userFacade.updateUser(user), HttpStatus.OK);
    }

    /**
     * Deletes User with given id
     *
     * @param id User's id
     * @return Response of api call, no return value
     */
    @RolesAllowed("ROLE_TEACHER")
    @DeleteMapping(value = "/{id}")
    public HttpEntity<Void> deleteUser(@PathVariable Long id) {
        userFacade.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Checks if User is a teacher
     *
     * @param id id of User to be checked
     * @return true if User is a teacher, false otherwise
     */
    @RolesAllowed("ROLE_TEACHER")
    @GetMapping(value = "/{id}/is_teacher")
    public HttpEntity<Boolean> isTeacher(@PathVariable Long id) {
        UserDTO user = new UserDTO();
        user.setId(id);
        return new ResponseEntity<>(userFacade.isTeacher(user), HttpStatus.OK);
    }

    /**
     * Checks if current user is a teacher
     *
     * @return true if current user is a teacher, false otherwise
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/is_teacher")
    public HttpEntity<Boolean> isTeacher() {
        return new ResponseEntity<>(userFacade.getLoggedInUser().orElseThrow(ResourceNotFoundException::new)
            .getIsTeacher(), HttpStatus.OK);
    }

    /**
     * Registers a new User.
     *
     * @param user User to be registered
     * @param unencryptedPassword User's password
     * @return User's Id
     */
    @RolesAllowed("ROLE_TEACHER")
    @PostMapping(value = "/register/{unencryptedPassword}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Long> registerUser(@RequestBody UserCreateDTO user, @PathVariable String unencryptedPassword) {
        if (userFacade.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException();
        }

        return new ResponseEntity<>(userFacade.registerUser(user, unencryptedPassword), HttpStatus.OK);
    }

    /**
     * Logs User in.
     *
     * @param username User's username
     * @param unencryptedPassword User's password
     * @return true if User was successfully logged in, false otherwise
     */
    @GetMapping(value = "/login/{username}/{unencryptedPassword}")
    public HttpEntity<Boolean> loginUser(@PathVariable String username, @PathVariable String unencryptedPassword) {
        if (userFacade.isLoggedInUser()) {
            throw new UserAlreadyLoggedInException();
        }

        UserLoginDTO user = new UserLoginDTO();
        user.setUsername(username);
        user.setPassword(unencryptedPassword);

        if (!userFacade.loginUser(user)) {
            throw new InvalidLoginCredentialsException();
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Checks if there is some logged in user.
     *
     * @return true if there is some logged in user
     */
    @GetMapping(value = "/is_logged_in")
    public HttpEntity<Boolean> isUserLoggedIn() {
        return new ResponseEntity<>(userFacade.isLoggedInUser(), HttpStatus.OK);
    }

    /**
     * Log out current User.
     *
     * @return Response of api call, no return value
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/logout")
    public HttpEntity<Void> logoutUser() {
        userFacade.logoutUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get current user which is logged in.
     *
     * @return User which is logged in.
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/logged_in_user")
    public HttpEntity<EntityModel<UserDTO>> getLoggedInUser() {
        Optional<UserDTO> userDTO = userFacade.getLoggedInUser();

        if (userDTO.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }
}
