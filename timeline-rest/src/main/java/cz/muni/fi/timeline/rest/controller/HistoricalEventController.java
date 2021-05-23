package cz.muni.fi.timeline.rest.controller;

import cz.muni.fi.timeline.api.dto.HistoricalEventDTO;

import cz.muni.fi.timeline.rest.assembler.HistoricalEventAssembler;

import cz.muni.fi.timeline.rest.exception.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cz.muni.fi.timeline.api.HistoricalTimelineFacade;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


/**
 * rest controller for HistoricalEvent
 *
 * @author Karolína Veselá
 */
@RestController
@RequestMapping("/events")
public class HistoricalEventController {

    private final HistoricalTimelineFacade historicalTimelineFacade;
    private final HistoricalEventAssembler historicalEventAssembler;

    @Inject
    public HistoricalEventController(HistoricalTimelineFacade historicalTimelineFacade, HistoricalEventAssembler historicalEventAssembler) {
        this.historicalTimelineFacade = historicalTimelineFacade;
        this.historicalEventAssembler = historicalEventAssembler;
    }

    /**
     * returns event with given id
     * @param id id of returned event
     * @return event with given id
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<EntityModel<HistoricalEventDTO>> getEvent(@PathVariable("id") long id) {
        Optional <HistoricalEventDTO> historicalEventDTO = historicalTimelineFacade.getHistoricalEventWithId(id);
        if (historicalEventDTO.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(historicalEventAssembler.toModel(historicalEventDTO.get()), HttpStatus.OK);
    }

    /**
     * removes event with given id
     * @param id id of the removed event
     * @return http status
     */
    @RolesAllowed("ROLE_TEACHER")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeEvent(@PathVariable("id") long id) {
        historicalTimelineFacade.removeHistoricalEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * updates event with given id
     * @param id id of the updated event
     * @param historicalEventDTO event with new parameters
     * @return id of the updated event
     */
    @RolesAllowed("ROLE_TEACHER")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> updateEvent(@PathVariable("id") long id, @RequestBody HistoricalEventDTO historicalEventDTO) {
        historicalEventDTO.setId(id);
        return new ResponseEntity<>(historicalTimelineFacade.updateHistoricalEvent(historicalEventDTO), HttpStatus.OK);
    }

    /**
     * returns all events
     * @return all events
     */
    @RolesAllowed("ROLE_USER")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<CollectionModel<EntityModel<HistoricalEventDTO>>> getEvents() {
        List<HistoricalEventDTO> eventDTOS = historicalTimelineFacade.getAllHistoricalEvents();

        CollectionModel<EntityModel<HistoricalEventDTO>> eventCollectionModel = historicalEventAssembler.toCollectionModel(eventDTOS);
        return new ResponseEntity<>(eventCollectionModel, HttpStatus.OK);
    }
}
