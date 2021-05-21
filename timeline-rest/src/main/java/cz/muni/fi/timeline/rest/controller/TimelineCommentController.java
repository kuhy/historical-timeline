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
 * rest controller for TimelineComment
 *
 * @author Karolína Veselá
 */
@RolesAllowed("ROLE_USER")
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
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<TimelineCommentDTO>> getComment(@PathVariable("id") long id) {
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeComment(@PathVariable("id") long id) {
        historicalTimelineFacade.removeHistoricalTimeline(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * update comment with given id
     * @param id of the updated comment
     * @param timelineCommentDTO comment with new parameters
     * @return id of the updated comment
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Long> updateComment(@PathVariable("id") long id, @RequestBody TimelineCommentDTO timelineCommentDTO) {
        timelineCommentDTO.setId(id);
        return new ResponseEntity<>(historicalTimelineFacade.updateTimelineComment(timelineCommentDTO),HttpStatus.OK);
    }

    /**
     * returns all comments
     * @return all comments
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<TimelineCommentDTO>>> getComments() {
        List<TimelineCommentDTO> timelineCommentDTOS =  historicalTimelineFacade.getAllTimelineComments();
        CollectionModel<EntityModel<TimelineCommentDTO>> commentCollectionModel = timelineCommentAssembler.toCollectionModel(timelineCommentDTOS);
        return new ResponseEntity<>(commentCollectionModel,HttpStatus.OK);
    }
}
