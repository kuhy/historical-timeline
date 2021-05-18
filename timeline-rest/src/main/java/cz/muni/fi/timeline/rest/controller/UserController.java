package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.UserFacade;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.api.dto.UserLoginDTO;
import cz.muni.fi.timeline.rest.assembler.UserModelAssembler;
import cz.muni.fi.timeline.rest.exception.ResourceAlreadyExistsException;
import cz.muni.fi.timeline.rest.exception.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

// TODO javadoc
// TODO roles
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllUsers() {
        List<UserDTO> allUsers = userFacade.getAllUsers();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    @GetMapping(value = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllTeachers() {
        List<UserDTO> allUsers = userFacade.getAllTeachers();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    @GetMapping(value = "/students",produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllStudent() {
        List<UserDTO> allUsers = userFacade.getAllStudents();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<UserDTO>> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userFacade.findUserById(id);

        if (!userDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<UserDTO>> getUserByUsername(@PathVariable String username) {
        Optional<UserDTO> userDTO = userFacade.findUserByUsername(username);

        if (!userDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }

    @PutMapping(value = "/{user}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Long> updateUser(@PathVariable UserDTO user) {
        return new ResponseEntity<>(userFacade.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public HttpEntity<Void> deleteUser(@PathVariable Long id) {
        userFacade.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/isteacher/{teacher}")
    public HttpEntity<Boolean> isTeacher(@PathVariable UserDTO teacher) {
        return new ResponseEntity<>(userFacade.isTeacher(teacher), HttpStatus.OK);
    }

    @PostMapping(value = "/user/{user}/{unencryptedPassword}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Long> registerUser(@PathVariable UserCreateDTO user, @PathVariable String unencryptedPassword) {
        if (userFacade.findUserByUsername(user.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException();
        }

        return new ResponseEntity<>(userFacade.registerUser(user, unencryptedPassword), HttpStatus.OK);
    }

    @GetMapping(value = "/login/{username}/{unencryptedPassword}")
    public HttpEntity<Boolean> loginUser(@PathVariable String username, @PathVariable String unencryptedPassword) {
        if (userFacade.isLoggedInUser()) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        UserLoginDTO user = new UserLoginDTO();
        user.setUsername(username);
        user.setPassword(unencryptedPassword);
        return new ResponseEntity<>(userFacade.loginUser(user), HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public HttpEntity<Void> logoutUser() {
        userFacade.logoutUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/loggedinuser")
    public HttpEntity<EntityModel<UserDTO>> getLoggedInUser() {
        Optional<UserDTO> userDTO = userFacade.getLoggedInUser();

        if (!userDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }
}
