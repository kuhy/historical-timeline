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
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<EntityModel<HistoricalEventDTO>> getEvent(@PathVariable("id") long id) {
        Optional <HistoricalEventDTO> historicalEventDTO = historicalTimelineFacade.getHistoricalEventWithId(id);
        if (!historicalEventDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(historicalEventAssembler.toModel(historicalEventDTO.get()), HttpStatus.OK);
    }

    /**
     * removes event with given id
     * @param id id of the removed event
     * @return http status
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> removeEvent(@PathVariable("id") long id) {
        historicalTimelineFacade.removeHistoricalEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * updates event with given id
     * @param id id of the updated event
     * @param historicalEventDTO event with new parameters
     * @return id of the updated event
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> updateEvent(@PathVariable("id") long id, @RequestBody HistoricalEventDTO historicalEventDTO) {
        return new ResponseEntity<>(historicalTimelineFacade.updateHistoricalEvent(historicalEventDTO), HttpStatus.OK);
    }

    /**
     * returns all events
     * @return all events
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CollectionModel<EntityModel<HistoricalEventDTO>>> getEvents() {
        List<HistoricalEventDTO> eventDTOS = historicalTimelineFacade.getAllHistoricalEvents();

        CollectionModel<EntityModel<HistoricalEventDTO>> eventCollectionModel = historicalEventAssembler.toCollectionModel(eventDTOS);
        return new ResponseEntity<>(eventCollectionModel, HttpStatus.OK);
    }
}
