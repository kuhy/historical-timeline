package cz.muni.fi.timeline.facade;

import cz.muni.fi.timeline.api.UserFacade;
import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.mapper.BeanMappingService;
import cz.muni.fi.timeline.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * implementation of user facade
 * @author Karolína Veselá
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final BeanMappingService beanMappingService;

    @Inject
    public UserFacadeImpl(UserService userService, BeanMappingService beanMappingService){
        this.userService = userService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Optional<UserDTO> findUserById(Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(beanMappingService.mapTo(user.get(), UserDTO.class));
    }

    @Override
    public Optional <UserDTO> findUserByUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(beanMappingService.mapTo(user.get(), UserDTO.class));
    }

    @Override
    public Long updateUser(UserDTO userDTO) {
        User user = beanMappingService.mapTo(userDTO, User.class);
        userService.updateUser(user);
        return user.getId();

    }

    @Override
    public Long registerUser(UserCreateDTO userCreateDTO, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(userCreateDTO, User.class);
        userService.registerUser(userEntity, unencryptedPassword);
        return userEntity.getId();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllTeachers() {
        return beanMappingService.mapTo(userService.getAllTeachers(), UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllStudents() {
        return beanMappingService.mapTo(userService.getAllStudents(), UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO userAuthenticateDTO) {

        return userService.authenticateUser(
                userService.findByUsername(userAuthenticateDTO.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with given username does not exist.")), userAuthenticateDTO.getPassword());
    }

    @Override
    public boolean isTeacher(UserDTO userDTO) {
        return userService.isTeacher(beanMappingService.mapTo(userDTO, User.class));

    }

    @Override
    public void removeUser(Long id) {
        User user = userService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with given id does not exist.")
        );

        userService.removeUser(user);
    }
}
