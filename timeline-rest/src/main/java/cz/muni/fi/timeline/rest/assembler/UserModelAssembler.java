package cz.muni.fi.timeline.rest.assembler;

import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.rest.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


/**
 * Converts UserDTO instance into userEntityModel,
 * which is later rendered into HAL JSON format with _links.
 *
 * @author Ondřej Kuhejda
 */
@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    @Override
    public EntityModel<UserDTO> toModel(UserDTO entity) {
        EntityModel<UserDTO> userEntityModel = EntityModel.of(entity);

        userEntityModel.add(linkTo(UserController.class).slash(entity.getId()).withSelfRel());

        return userEntityModel;
    }
}
