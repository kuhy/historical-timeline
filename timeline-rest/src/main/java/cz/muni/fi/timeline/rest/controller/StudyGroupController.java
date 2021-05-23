package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.StudyGroupFacade;
import cz.muni.fi.timeline.api.dto.HistoricalTimelineCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;
import cz.muni.fi.timeline.rest.assembler.StudyGroupAssembler;
import cz.muni.fi.timeline.rest.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * rest controller for study groups
 * @author Matej Macák
 */
@RestController
@RequestMapping("/groups")
public class StudyGroupController {

    final static Logger logger = LoggerFactory.getLogger(StudyGroupController.class);

    private final StudyGroupFacade studyGroupFacade;
    private final StudyGroupAssembler studyGroupAssembler;

    @Inject
    public StudyGroupController(StudyGroupFacade groupFacade, StudyGroupAssembler studyGroupAssembler){
        this.studyGroupFacade = groupFacade;
        this.studyGroupAssembler = studyGroupAssembler;
    }

    /**
     * return study group with id
     * @param id id of the group
     * @return study group with given id
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<StudyGroupDTO>> getStudyGroup(@PathVariable("id") long id){
        logger.debug("rest getStudyGroup({})", id);

        Optional<StudyGroupDTO> studyGroupDTO = studyGroupFacade.getStudyGroupWithId(id);
        if (studyGroupDTO.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(studyGroupAssembler.toModel(studyGroupDTO.get()), HttpStatus.OK);
    }

    /**
     * removes study group with id
     * @param id of the group to be removed
     * @return http status
     */
    @RolesAllowed("ROLE_TEACHER")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStudyGroup(@PathVariable("id") long id) {
        logger.debug("rest deleteStudyGroup({})", id);

        studyGroupFacade.removeStudyGroup(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * updates study group
     * @param id id of the group to be updated
     * @param updatedGroup study group with new parameters
     * @return http status
     */
    @RolesAllowed("ROLE_TEACHER")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> updateStudyGroup(@PathVariable("id") long id, @RequestBody StudyGroupDTO updatedGroup){
        logger.debug("rest updateStudyGroup({})", id);

        updatedGroup.setId(id);
        return new ResponseEntity<>(studyGroupFacade.updateStudyGroup(updatedGroup), HttpStatus.OK);

    }

    /**
     * return all study group
     * @return all study groups
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<StudyGroupDTO>>> getAllStudyGroups() {
        logger.debug("rest getAllStudyGroups()");

        List<StudyGroupDTO> allGroups = studyGroupFacade.getAllStudyGroups();
        CollectionModel<EntityModel<StudyGroupDTO>> studyGroupCollectionModel = studyGroupAssembler.toCollectionModel(allGroups);
        return new ResponseEntity<>(studyGroupCollectionModel, HttpStatus.OK);
    }

    /**
     * creates new study group
     * @param studyGroupCreateDTO study group to be created
     * @return response entity
     */
    @RolesAllowed("ROLE_TEACHER")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createStudyGroup(@RequestBody StudyGroupCreateDTO studyGroupCreateDTO){
        logger.debug("rest createStudyGroup()");

        return new ResponseEntity<>(studyGroupFacade.createStudyGroup(studyGroupCreateDTO), HttpStatus.OK);
    }

    /**
     * creates new timeline in study group
     * @param historicalTimelineCreateDTO timeline to be created
     * @param groupId id of the group where timeline will be created
     * @return response entity
     */
    @RolesAllowed("ROLE_TEACHER")
    @PostMapping(value = "/{id}/timelines/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createTimelineInStudyGroup(@RequestBody HistoricalTimelineCreateDTO historicalTimelineCreateDTO,@PathVariable("id") long groupId){

        return new ResponseEntity<>(studyGroupFacade.createTimelineInStudyGroup(historicalTimelineCreateDTO,groupId),HttpStatus.OK);
    }

    /**
     * removes user from study group
     * @param studyGroupId id of the group from which user will be removed
     * @param userId id of user to be removed
     * @return response entity
     * @throws UserNotInStudyGroupException
     */
    @RolesAllowed("ROLE_USER")
    @DeleteMapping(value = "/{id}/users/{user_id}")
    public ResponseEntity<Void> removeUserFromStudyGroup(@PathVariable("id") long studyGroupId, @PathVariable("user_id") long userId) throws UserNotInStudyGroupException {
        logger.debug("rest removeUserFromStudyGroup");

        studyGroupFacade.removeUserFromStudyGroup(studyGroupId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * adds user to study group
     * @param studyGroupId id of the study group
     * @param userId id of user to be added
     * @return response entity
     * @throws UserAlreadyInStudyGroupException
     */
    @RolesAllowed("ROLE_TEACHER")
    @PutMapping(value = "/{id}/users/{user_id}")
    public ResponseEntity<Void> addUserToStudyGroup(@PathVariable("id") long studyGroupId, @PathVariable("user_id") long userId) throws UserAlreadyInStudyGroupException {
        logger.debug("rest addUserFromStudyGroup");

        studyGroupFacade.addUserToStudyGroup(studyGroupId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
