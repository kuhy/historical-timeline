package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.TimelineCommentDTO;
import cz.muni.fi.timeline.rest.controller.TimelineCommentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Converts TimelineCommentDTO instance into commentEntityModel
 *
 * @author Karolína Veselá
 */

@Component
public class TimelineCommentAssembler implements RepresentationModelAssembler<TimelineCommentDTO, EntityModel<TimelineCommentDTO>> {
    @Override
    public EntityModel<TimelineCommentDTO> toModel(TimelineCommentDTO entity) {
        EntityModel<TimelineCommentDTO> commentResource = EntityModel.of(entity);

            commentResource.add(linkTo(TimelineCommentController.class).slash(entity.getId()).withSelfRel());
            commentResource.add(linkTo(TimelineCommentController.class).slash(entity.getId()).withRel("DELETE"));

        return commentResource;
    }
}
