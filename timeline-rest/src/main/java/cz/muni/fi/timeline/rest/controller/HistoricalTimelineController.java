package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.HistoricalTimelineFacade;
import cz.muni.fi.timeline.api.dto.*;
import cz.muni.fi.timeline.rest.exception.ResourceNotFoundException;
import cz.muni.fi.timeline.rest.assembler.HistoricalTimelineAssembler;
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


/**
 * rest controller for HistoricalTimeline
 *
 * @author Karolína Veselá
 */
@RestController
@RequestMapping("/timelines")
public class HistoricalTimelineController {
    private final HistoricalTimelineFacade historicalTimelineFacade;
    private final HistoricalTimelineAssembler historicalTimelineAssembler;

    @Inject
    public HistoricalTimelineController(HistoricalTimelineFacade historicalTimelineFacade, HistoricalTimelineAssembler historicalTimelineAssembler) {
        this.historicalTimelineFacade = historicalTimelineFacade;
        this.historicalTimelineAssembler = historicalTimelineAssembler;
    }

    /**
     * return timelines with given id
     * @param id id of returned timeline
     * @return timeline with given id
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<EntityModel<HistoricalTimelineDTO>> getTimeline(@PathVariable("id") long id) {
        Optional<HistoricalTimelineDTO> historicalTimelineDTO = historicalTimelineFacade.getHistoricalTimelineWithId(id);
        if (!historicalTimelineDTO.isPresent()){
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(historicalTimelineAssembler.toModel(historicalTimelineDTO.get()), HttpStatus.OK);
    }

    /**
     * removen timeline with given id
     * @param id id of removed timeline
     * @return http status
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> removeTimeline(@PathVariable("id") long id) {

        historicalTimelineFacade.removeHistoricalTimeline(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * update timeline
     * @param id id of updated timeline
     * @param historicalTimelineDTO timeline with new parameters
     * @return id of updated timeline
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> updateTimeline(@PathVariable("id") long id, @RequestBody HistoricalTimelineDTO historicalTimelineDTO) {
        return new ResponseEntity<>(historicalTimelineFacade.updateHistoricalTimeline(historicalTimelineDTO), HttpStatus.OK);
    }

    /**
     * returns all timelines
     * @return all timelines
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CollectionModel<EntityModel<HistoricalTimelineDTO>>> getTimelines() {
        List<HistoricalTimelineDTO> timelines =  historicalTimelineFacade.getAllHistoricalTimelines();
        CollectionModel<EntityModel<HistoricalTimelineDTO>> timelinesCollectionModel = historicalTimelineAssembler.toCollectionModel(timelines);
        return new ResponseEntity<>(timelinesCollectionModel, HttpStatus.OK);
    }

    /**
     * creates timeline comment
     * @param timelineCommentCreateDTO comment dto to be created
     * @param id id of timeline in which is the comment created
     * @return id of created comment
     */
    @RequestMapping(value ="/{id}/comments/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> createCommentInTimeline(@RequestBody() TimelineCommentCreateDTO timelineCommentCreateDTO, @PathVariable("id") long id) {
        return new ResponseEntity<>(historicalTimelineFacade.createTimelineComment(timelineCommentCreateDTO,id),HttpStatus.OK);
    }

    /**
     * creates event in timeline
     * @param historicalEventCreateDTO event dto to be created
     * @param id id of timeline in which is the event created
     * @return id of created event
     */
    @RequestMapping(value ="{/{id}/events/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> createEventInTimeline(@RequestBody() HistoricalEventCreateDTO historicalEventCreateDTO, @PathVariable("id") long id) {
        return new ResponseEntity<>(historicalTimelineFacade.createEventInTimeline(historicalEventCreateDTO,id), HttpStatus.OK);
    }

}
