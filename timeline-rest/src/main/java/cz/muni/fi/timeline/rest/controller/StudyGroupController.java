package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.StudyGroupFacade;
import cz.muni.fi.timeline.api.dto.HistoricalTimelineCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;
<<<<<<< HEAD
import cz.muni.fi.timeline.rest.assembler.StudyGroupAssembler;
import cz.muni.fi.timeline.rest.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
=======
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.rest.assembler.StudyGroupAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 57b9fd1447088fc070e52bc3cbc44d4c9e7d6ebc
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import javax.inject.Inject;
=======
import java.security.InvalidParameterException;
>>>>>>> 57b9fd1447088fc070e52bc3cbc44d4c9e7d6ebc
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

    private StudyGroupFacade studyGroupFacade;
    private StudyGroupAssembler studyGroupAssembler;

<<<<<<< HEAD
    @Inject
=======
    @Autowired
>>>>>>> 57b9fd1447088fc070e52bc3cbc44d4c9e7d6ebc
    public StudyGroupController(StudyGroupFacade groupFacade, StudyGroupAssembler studyGroupAssembler){
        this.studyGroupFacade = groupFacade;
        this.studyGroupAssembler = studyGroupAssembler;
    }

<<<<<<< HEAD
    /**
     * return study group with id
     * @param id id of the group
     * @return study group with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<EntityModel<StudyGroupDTO>> getStudyGroup(@PathVariable("id") long id){
        logger.debug("rest getStudyGroup({})", id);

        Optional<StudyGroupDTO> studyGroupDTO = studyGroupFacade.getStudyGroupWithId(id);
        if (!studyGroupDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return new ResponseEntity<>(studyGroupAssembler.toModel(studyGroupDTO.get()), HttpStatus.OK);
    }

    /**
     * removes study group with id
     * @param id of the group to be removed
     * @return http status
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> deleteStudyGroup(@PathVariable("id") long id) {
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
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> updateStudyGroup(@PathVariable("id") long id, @RequestBody StudyGroupDTO updatedGroup){

        logger.debug("rest updateStudyGroup({})", id);

        return new ResponseEntity<>(studyGroupFacade.updateStudyGroup(updatedGroup), HttpStatus.OK);

    }

    /**
     * return all study group
     * @return all study groups
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CollectionModel<EntityModel<StudyGroupDTO>>> getAllStudyGroups() {
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
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> createStudyGroup(@RequestBody StudyGroupCreateDTO studyGroupCreateDTO){

        logger.debug("rest createStudyGroup()");

        return new ResponseEntity<>(studyGroupFacade.createStudyGroup(studyGroupCreateDTO), HttpStatus.OK);
    }

    /**
     * creates new timeline in study group
     * @param historicalTimelineCreateDTO timeline to be created
     * @param groupId id of the group where timeline will be created
     * @return response entity
     */
     @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> createTimelineInStudyGroup(HistoricalTimelineCreateDTO historicalTimelineCreateDTO,@PathVariable("id") long groupId){
        return new ResponseEntity<>(studyGroupFacade.createTimelineInStudyGroup(historicalTimelineCreateDTO,groupId),HttpStatus.OK);
    }

    /**
     * removes user from study group
     * @param studyGroupId id of the group from which user will be removed
     * @param userId id of user to be removed
     * @return response entity
     * @throws UserNotInStudyGroupException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> removeUserFromStudyGroup(@PathVariable("id") long studyGroupId, @PathVariable("id") long userId) throws UserNotInStudyGroupException {
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
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> addUserToStudyGroup(@PathVariable("id") long studyGroupId, @PathVariable("id") long userId) throws UserAlreadyInStudyGroupException {
        logger.debug("rest addUserFromStudyGroup");

        studyGroupFacade.addUserToStudyGroup(studyGroupId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
=======
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Optional<StudyGroupDTO> getStudyGroup(@PathVariable("id") long id){
        logger.debug("rest getStudyGroup({})", id);
        Optional<StudyGroupDTO> studyGroupDTO = studyGroupFacade.getStudyGroupWithId(id);
        return studyGroupDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteStudyGroup(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteStudyGroup({})", id);
        try {
            studyGroupFacade.removeStudyGroup(id);
        } catch (Exception ex) {
            throw new Exception("resource not found");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Optional<StudyGroupDTO> changePrice(@PathVariable("id") long id, @RequestBody StudyGroupDTO updatedGroup) throws Exception {

        logger.debug("rest updateStudyGroup({})", id);

        try {
            studyGroupFacade.updateStudyGroup(updatedGroup);
            return studyGroupFacade.getStudyGroupWithId(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<StudyGroupDTO> getAllStudyGroups() {
        logger.debug("rest getAllStudyGroups()");
        return studyGroupFacade.getAllStudyGroups();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Optional<StudyGroupDTO> createStudyGroup(@RequestBody StudyGroupCreateDTO studyGroupCreateDTO) throws Exception {

        logger.debug("rest createStudyGroup()");

        try {
            Long id = studyGroupFacade.createStudyGroup(studyGroupCreateDTO);
            return studyGroupFacade.getStudyGroupWithId(id);
        } catch (Exception ex) {
            throw new Exception("Resource already exists");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Optional<StudyGroupDTO> createTimelineInStudyGroup(HistoricalTimelineCreateDTO historicalTimelineCreateDTO,@PathVariable("id") long groupId){
        Long id = studyGroupFacade.createTimelineInStudyGroup(historicalTimelineCreateDTO, groupId);
        return studyGroupFacade.getStudyGroupWithId(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void removeUserFromStudyGroup(@PathVariable("id") long studyGroupId, @PathVariable("id") long userId) throws UserNotInStudyGroupException {
        logger.debug("rest removeUserFromStudyGroup");

        studyGroupFacade.removeUserFromStudyGroup(studyGroupId, userId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void addUserToStudyGroup(@PathVariable("id") long studyGroupId, @PathVariable("id") long userId) throws UserNotInStudyGroupException, UserAlreadyInStudyGroupException {
        logger.debug("rest addUserFromStudyGroup");

        studyGroupFacade.addUserToStudyGroup(studyGroupId, userId);
>>>>>>> 57b9fd1447088fc070e52bc3cbc44d4c9e7d6ebc
    }
}
