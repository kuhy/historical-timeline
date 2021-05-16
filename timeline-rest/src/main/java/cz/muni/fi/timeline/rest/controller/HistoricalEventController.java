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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cz.muni.fi.timeline.api.HistoricalTimelineFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


/**
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

    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<EntityModel<HistoricalEventDTO>> getEvent(@PathVariable("id") long id) {
        Optional <HistoricalEventDTO> historicalEventDTO = historicalTimelineFacade.getHistoricalEventWithId(id);
        if (!historicalEventDTO.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(historicalEventAssembler.toModel(historicalEventDTO.get()), HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Void> removeEvent(@PathVariable("id") long id) {
        historicalTimelineFacade.removeHistoricalEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<Long> updateEvent(@PathVariable("id") long id) {
        HistoricalEventDTO historicalEventDTO = historicalTimelineFacade.getHistoricalEventWithId(id).get();
        return new ResponseEntity<>(historicalTimelineFacade.updateHistoricalEvent(historicalEventDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HttpEntity<CollectionModel<EntityModel<HistoricalEventDTO>>> getEvents() {
        List<HistoricalEventDTO> eventDTOS = historicalTimelineFacade.getAllHistoricalEvents();

        CollectionModel<EntityModel<HistoricalEventDTO>> eventCollectionModel = historicalEventAssembler.toCollectionModel(eventDTOS);
        return new ResponseEntity<>(eventCollectionModel, HttpStatus.OK);
    }
}
