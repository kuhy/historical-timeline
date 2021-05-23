package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.HistoricalTimelineDTO;
import cz.muni.fi.timeline.rest.controller.HistoricalTimelineController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Converts HistoricalTimelineDTO instance into timelineEntityModel
 *
 * @author Karolína Veselá
 */

@Component
public class HistoricalTimelineAssembler implements RepresentationModelAssembler<HistoricalTimelineDTO, EntityModel<HistoricalTimelineDTO>> {
    @Override
    public EntityModel<HistoricalTimelineDTO> toModel(HistoricalTimelineDTO entity) {
        EntityModel<HistoricalTimelineDTO> timelineResource = EntityModel.of(entity);


            timelineResource.add(linkTo(HistoricalTimelineController.class).slash(entity.getId()).withSelfRel());
            timelineResource.add(linkTo(HistoricalTimelineController.class).slash(entity.getId()).withRel("DELETE"));

        return timelineResource;

    }
}
