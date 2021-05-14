package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.TimelineCommentDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * @author Karolína Veselá
 */
public class TimelineCommentAssembler implements RepresentationModelAssembler<TimelineCommentDTO, EntityModel<TimelineCommentDTO>> {
    @Override
    public EntityModel<TimelineCommentDTO> toModel(TimelineCommentDTO entity) {
        return EntityModel.of(entity);
    }
}
