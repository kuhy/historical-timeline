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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


/**
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

    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<EntityModel<TimelineCommentDTO>> getComment(@PathVariable("id") long id) {
        Optional<TimelineCommentDTO> timelineCommentDTO =  historicalTimelineFacade.getTimelineCommentWithId(id);
        if (!timelineCommentDTO.isPresent()){
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(timelineCommentAssembler.toModel(timelineCommentDTO.get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> removeComment(@PathVariable("id") long id) {
        historicalTimelineFacade.removeHistoricalTimeline(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> updateComment(@PathVariable("id") long id) {
        TimelineCommentDTO timelineCommentDTO = historicalTimelineFacade.getTimelineCommentWithId(id).get();
        return new ResponseEntity<>(historicalTimelineFacade.updateTimelineComment(timelineCommentDTO),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CollectionModel<EntityModel<TimelineCommentDTO>>> getComments() {
        List<TimelineCommentDTO> timelineCommentDTOS =  historicalTimelineFacade.getAllTimelineComments();
        CollectionModel<EntityModel<TimelineCommentDTO>> commentCollectionModel = timelineCommentAssembler.toCollectionModel(timelineCommentDTOS);
        return new ResponseEntity<>(commentCollectionModel,HttpStatus.OK);
    }
}
