package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.HistoricalEventDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * @author Karolína Veselá
 */
public class HistoricalEventAssembler implements RepresentationModelAssembler<HistoricalEventDTO, EntityModel<HistoricalEventDTO>> {
    @Override
    public EntityModel<HistoricalEventDTO> toModel(HistoricalEventDTO entity) {
        return EntityModel.of(entity);
    }
}
