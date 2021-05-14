package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.HistoricalTimelineDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * @author Karolína Veselá
 */
public class HistoricalTimelineAssembler implements RepresentationModelAssembler<HistoricalTimelineDTO, EntityModel<HistoricalTimelineDTO>> {
    @Override
    public EntityModel<HistoricalTimelineDTO> toModel(HistoricalTimelineDTO entity) {
        return EntityModel.of(entity);
    }
}
