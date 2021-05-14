package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.UserFacade;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.rest.assembler.UserModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;


/**
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
    public HttpEntity<CollectionModel<EntityModel<UserDTO>>> getAllUsers(){
        List<UserDTO> allUsers = userFacade.getAllUsers();
        CollectionModel<EntityModel<UserDTO>> userCollectionModel = userModelAssembler.toCollectionModel(allUsers);
        return new ResponseEntity<>(userCollectionModel, HttpStatus.OK);
    }

    // TODO getUser
    // GET /{id}

    // TODO removeUser
    // DELETE /{id}

    // TODO isTeacher
    // GET /teacher

    // TODO login
    // GET /login/{name}/password/{pass}
    // see https://github.com/445343/PA165-DnDProject/blob/5165d85dfdb9fe9026539d0edf3976f0d37464a2/rest/src/main/java/cz/fi/muni/PA165/rest/controllers/UserController.java

    // TODO logout
    // GET /logout
    // see https://github.com/445343/PA165-DnDProject/blob/5165d85dfdb9fe9026539d0edf3976f0d37464a2/rest/src/main/java/cz/fi/muni/PA165/rest/controllers/UserController.java
}
