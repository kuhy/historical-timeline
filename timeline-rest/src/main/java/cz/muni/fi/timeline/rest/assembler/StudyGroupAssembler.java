package cz.muni.fi.timeline.rest.assembler;


import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.rest.controller.StudyGroupController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * @author Matej Macák
 */
public class StudyGroupAssembler implements RepresentationModelAssembler<StudyGroupDTO, EntityModel<StudyGroupDTO>> {
    @Override
    public EntityModel<StudyGroupDTO> toModel(StudyGroupDTO entity) {
        EntityModel<StudyGroupDTO> groupModel = new EntityModel<>(entity);
        try{
            groupModel.add(linkTo(StudyGroupController.class).slash(entity.getId()).withSelfRel());
            groupModel.add(linkTo(StudyGroupController.class).slash(entity.getId()).withRel("DELETE"));
        } catch (Exception ex){
            Logger.getLogger(StudyGroupAssembler.class.getName()).log(Level.SEVERE, "could not link resource from StudyGroupController", ex);
        }
        return groupModel;
    }

}
