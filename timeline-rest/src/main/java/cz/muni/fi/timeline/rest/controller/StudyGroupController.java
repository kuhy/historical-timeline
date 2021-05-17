package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.StudyGroupFacade;
import cz.muni.fi.timeline.api.dto.HistoricalTimelineCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.rest.assembler.StudyGroupAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;


/**
 * @author Matej Macák
 */
@RestController
@RequestMapping("/groups")
public class StudyGroupController {

    final static Logger logger = LoggerFactory.getLogger(StudyGroupController.class);

    private StudyGroupFacade studyGroupFacade;
    private StudyGroupAssembler studyGroupAssembler;

    @Autowired
    public StudyGroupController(StudyGroupFacade groupFacade, StudyGroupAssembler studyGroupAssembler){
        this.studyGroupFacade = groupFacade;
        this.studyGroupAssembler = studyGroupAssembler;
    }

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
    }
}
