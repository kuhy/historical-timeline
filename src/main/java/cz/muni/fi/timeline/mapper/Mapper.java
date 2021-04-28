package cz.muni.fi.timeline.mapper;

import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.entity.User;

import java.util.List;

@org.mapstruct.Mapper
public interface Mapper {
    UserDTO userToDTO(User user);
    UserCreateDTO userToCreateDTO(User user);
    UserAuthenticateDTO userToAuthenticateDTO(User user);
    List<UserDTO> userDTOListToUser(List<User> employees);

    User UserDTOToUser(UserDTO userDTO);
    User UserAuthenticateDTOToUser(UserAuthenticateDTO userDTO);
    User UserCreateDTOToUser(UserCreateDTO userDTO);

}
