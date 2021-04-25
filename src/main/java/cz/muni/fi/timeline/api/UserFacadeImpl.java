package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

/**
 * implementation of user facade
 * @author Karolína Veselá
 */
@Service
public class UserFacadeImpl implements UserFacade{

    private UserService userService;
    private BeanMappingService beanMappingService;

    @Inject
    public UserFacadeImpl(UserService userService,BeanMappingService beanMappingService){
        this.userService = userService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public UserDTO findUserById(Long id) {
        Optional<User> user = userService.findById(id);
        return user.isPresent() ? beanMappingService.mapTo(user.get(), UserDTO.class) : null;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.isPresent() ? beanMappingService.mapTo(user.get(), UserDTO.class) : null;
    }

    @Override
    public void registerUser(UserDTO userDTO, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userService.registerUser(userEntity, unencryptedPassword);
        userDTO.setID(userEntity.getId());
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }

    @Override
    public Collection<UserDTO> getAllTeachers() {
        return beanMappingService.mapTo(userService.getAllTeachers(), UserDTO.class);
    }

    @Override
    public Collection<UserDTO> getAllStudents() {
        return beanMappingService.mapTo(userService.getAllStudents(), UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        return userService.authenticateUser(
                userService.findById((userAuthenticateDTO.getID())).get(), userAuthenticateDTO.getPassword());
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
