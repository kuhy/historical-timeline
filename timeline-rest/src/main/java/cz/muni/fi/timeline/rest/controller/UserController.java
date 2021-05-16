package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.UserFacade;
import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.rest.assembler.UserModelAssembler;
import cz.muni.fi.timeline.rest.exceptions.ResourceNotFoundException;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllTeachers() {
        List<UserDTO> allUsers = userFacade.getAllTeachers();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllStudent() {
        List<UserDTO> allUsers = userFacade.getAllStudents();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<UserDTO>> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userFacade.findUserById(id);

        if (!userDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<UserDTO>> getUserByUsername(@PathVariable String username) {
        Optional<UserDTO> userDTO = userFacade.findUserByUsername(username);

        if (!userDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(userModelAssembler.toModel(userDTO.get()), HttpStatus.OK);
    }

    @PutMapping(value = "/{user}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> updateUser(@PathVariable UserDTO user) {
        return new ResponseEntity<>(userFacade.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userFacade.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{teacher}")
    public ResponseEntity<Boolean> isTeacher(@PathVariable UserDTO teacher) {
        return new ResponseEntity<>(userFacade.isTeacher(teacher), HttpStatus.OK);
    }

    @PostMapping(value = "/user/{user}/password/{unencryptedPassword}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> registerUser(@PathVariable UserCreateDTO user, @PathVariable String unencryptedPassword) {
        return new ResponseEntity<>(userFacade.registerUser(user, unencryptedPassword), HttpStatus.OK);
    }

    @GetMapping(value = "/login/{username}/password/{unencryptedPassword}")
    public ResponseEntity<Boolean> authenticate(@PathVariable String username, @PathVariable String unencryptedPassword) {
        UserAuthenticateDTO user = new UserAuthenticateDTO();
        user.setUsername(username);
        user.setPassword(unencryptedPassword);
        return new ResponseEntity<>(userFacade.authenticate(user), HttpStatus.OK);
    }

    // TODO logout
    // GET /logout
    // see https://github.com/445343/PA165-DnDProject/blob/5165d85dfdb9fe9026539d0edf3976f0d37464a2/rest/src/main/java/cz/fi/muni/PA165/rest/controllers/UserController.java
}
