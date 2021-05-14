package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * @author Matej Macák
 */
public class StudyGroupAssembler implements RepresentationModelAssembler<StudyGroupDTO, EntityModel<StudyGroupDTO>> {
    @Override
    public EntityModel<StudyGroupDTO> toModel(StudyGroupDTO entity) {
        return EntityModel.of(entity);
    }
}
