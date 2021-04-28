package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.mapper.Mapper;
import cz.muni.fi.timeline.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * implementation of user facade
 * @author Karolína Veselá
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade{

    private final UserService userService;
    private final Mapper mapper;

    @Inject
    public UserFacadeImpl(UserService userService,Mapper mapper){
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserDTO> findUserById(Long id) {
        Optional<User> user = userService.findById(id);

        if (!user.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(mapper.userToDTO(user.get()));
    }

    @Override
    public Optional <UserDTO> findUserByUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(mapper.userToDTO(user.get()));
    }

    @Override
    public Long updateUser(UserDTO userDTO) {
        User user = mapper.UserDTOToUser(userDTO);
        userService.updateUser(user);
        return user.getId();

    }

    @Override
    public Long registerUser(UserCreateDTO userCreateDTO, String unencryptedPassword) {
        User userEntity = mapper.UserCreateDTOToUser(userCreateDTO);
        userService.registerUser(userEntity, unencryptedPassword);
        return userEntity.getId();
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.userDTOListToUser(userService.getAllUsers());

    }

    @Override
    public List<UserDTO> getAllTeachers() {
        return mapper.userDTOListToUser(userService.getAllTeachers());
    }

    @Override
    public List<UserDTO> getAllStudents() {
        return mapper.userDTOListToUser(userService.getAllStudents());
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO userAuthenticateDTO) {

        return userService.authenticateUser(
                userService.findById(userAuthenticateDTO.getId()).orElseThrow(() ->
                new IllegalArgumentException("User with given id does not exist.")), userAuthenticateDTO.getPassword());
    }

    @Override
    public boolean isTeacher(UserDTO userDTO) {
        return userService.isTeacher(mapper.UserDTOToUser(userDTO));

    }

    @Override
    public void removeUser(Long id) {
        User user = userService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with given id does not exist.")
        );

        userService.removeUser(user);
    }
}