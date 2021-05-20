package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.HistoricalTimelineFacade;
import cz.muni.fi.timeline.api.dto.TimelineCommentDTO;
import cz.muni.fi.timeline.rest.exception.ResourceNotFoundException;
import cz.muni.fi.timeline.rest.assembler.TimelineCommentAssembler;
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
 * rest cotroller for TimelineComment
 *
 * @author Karolína Veselá
 */
@RestController
@RequestMapping("/comments")
public class TimelineCommentController {

    private final HistoricalTimelineFacade historicalTimelineFacade;
    private final TimelineCommentAssembler timelineCommentAssembler;

    @Inject
    public TimelineCommentController(HistoricalTimelineFacade historicalTimelineFacade, TimelineCommentAssembler timelineCommentAssembler) {
        this.historicalTimelineFacade = historicalTimelineFacade;
        this.timelineCommentAssembler = timelineCommentAssembler;
    }

    /**
     * get comment with given id
     * @param id if of the returned comment
     * @return comment with given id
     */
    @RolesAllowed("ROLE_USER")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<EntityModel<TimelineCommentDTO>> getComment(@PathVariable("id") long id) {
        Optional<TimelineCommentDTO> timelineCommentDTO =  historicalTimelineFacade.getTimelineCommentWithId(id);
        if (timelineCommentDTO.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(timelineCommentAssembler.toModel(timelineCommentDTO.get()), HttpStatus.OK);
    }

    /**
     * remove comment with given id
     * @param id id of removed comment
     * @return http status
     */
    @RolesAllowed("ROLE_USER")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> removeComment(@PathVariable("id") long id) {
        historicalTimelineFacade.removeHistoricalTimeline(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * update comment with given id
     * @param id of the updated comment
     * @param timelineCommentDTO comment with new parameters
     * @return id of the updated comment
     */
    @RolesAllowed("ROLE_USER")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> updateComment(@PathVariable("id") long id, @RequestBody TimelineCommentDTO timelineCommentDTO) {
        timelineCommentDTO.setId(id);
        return new ResponseEntity<>(historicalTimelineFacade.updateTimelineComment(timelineCommentDTO),HttpStatus.OK);
    }

    /**
     * returns all comments
     * @return all comments
     */
    @RolesAllowed("ROLE_USER")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CollectionModel<EntityModel<TimelineCommentDTO>>> getComments() {
        List<TimelineCommentDTO> timelineCommentDTOS =  historicalTimelineFacade.getAllTimelineComments();
        CollectionModel<EntityModel<TimelineCommentDTO>> commentCollectionModel = timelineCommentAssembler.toCollectionModel(timelineCommentDTOS);
        return new ResponseEntity<>(commentCollectionModel,HttpStatus.OK);
    }
}
