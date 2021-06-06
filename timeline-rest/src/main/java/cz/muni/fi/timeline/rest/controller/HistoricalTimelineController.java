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

import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<HistoricalTimelineDTO>> getTimeline(@PathVariable("id") long id) {
        Optional<HistoricalTimelineDTO> historicalTimelineDTO = historicalTimelineFacade.getHistoricalTimelineWithId(id);
        if (historicalTimelineDTO.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(historicalTimelineAssembler.toModel(historicalTimelineDTO.get()), HttpStatus.OK);
    }

    /**
     * removen timeline with given id
     * @param id id of removed timeline
     * @return http status
     */
    @RolesAllowed("ROLE_TEACHER")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeTimeline(@PathVariable("id") long id) {
        historicalTimelineFacade.removeHistoricalTimeline(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * update timeline
     * @param id id of updated timeline
     * @param historicalTimelineDTO timeline with new parameters
     * @return id of updated timeline
     */
    @RolesAllowed("ROLE_TEACHER")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Long> updateTimeline(@PathVariable("id") long id, @RequestBody HistoricalTimelineDTO historicalTimelineDTO) {
        historicalTimelineDTO.setId(id);
        return new ResponseEntity<>(historicalTimelineFacade.updateHistoricalTimeline(historicalTimelineDTO), HttpStatus.OK);
    }

    /**
     * returns all timelines
     * @return all timelines
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<HistoricalTimelineDTO>>> getTimelines() {
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
    @RolesAllowed("ROLE_USER")
    @PostMapping(value ="/{id}/comments/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createCommentInTimeline(@RequestBody TimelineCommentCreateDTO timelineCommentCreateDTO, @PathVariable("id") long id) {
        return new ResponseEntity<>(historicalTimelineFacade.createTimelineComment(timelineCommentCreateDTO, id),HttpStatus.OK);
    }

    /**
     * creates event in timeline
     * @param historicalEventCreateDTO event dto to be created
     * @param id id of timeline in which is the event created
     * @return id of created event
     */
    @RolesAllowed("ROLE_TEACHER")
    @PostMapping(value ="/{id}/events/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createEventInTimeline(@RequestBody HistoricalEventCreateDTO historicalEventCreateDTO, @PathVariable("id") long id) {
        return new ResponseEntity<>(historicalTimelineFacade.createEventInTimeline(historicalEventCreateDTO,id), HttpStatus.OK);
    }

}
