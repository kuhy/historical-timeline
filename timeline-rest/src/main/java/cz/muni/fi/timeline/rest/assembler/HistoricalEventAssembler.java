package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.HistoricalEventDTO;
import cz.muni.fi.timeline.rest.controller.HistoricalEventController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Converts HistoricalEventDTO instance into eventEntityModel
 *
 * @author Karolína Veselá
 */

@Component
public class HistoricalEventAssembler implements RepresentationModelAssembler<HistoricalEventDTO, EntityModel<HistoricalEventDTO>> {
    @Override
    public EntityModel<HistoricalEventDTO> toModel(HistoricalEventDTO entity) {

        EntityModel<HistoricalEventDTO> eventResource = EntityModel.of(entity);

            eventResource.add(linkTo(HistoricalEventController.class).slash(entity.getId()).withSelfRel());
            eventResource.add(linkTo(HistoricalEventController.class).slash(entity.getId()).withRel("DELETE"));

        return eventResource;
    }
}
