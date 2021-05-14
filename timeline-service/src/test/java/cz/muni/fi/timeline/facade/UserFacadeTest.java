package cz.muni.fi.timeline.facade;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import cz.muni.fi.timeline.mapper.BeanMappingService;
import cz.muni.fi.timeline.api.UserFacade;
import cz.muni.fi.timeline.api.dto.UserAuthenticateDTO;
import cz.muni.fi.timeline.api.dto.UserCreateDTO;
import cz.muni.fi.timeline.api.dto.UserDTO;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.mapper.BeanMappingServiceImpl;
import cz.muni.fi.timeline.service.UserService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for User Facade
 *
 * @author Karolína Veselá
 */
public class UserFacadeTest {
    @Mock
    private UserService userService;

    // TODO @Mock
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl(DozerBeanMapperBuilder.buildDefault());

    private UserFacade userFacade;

    private AutoCloseable autoCloseable;

    private User user;

    private User user2;

    @BeforeMethod
    public void prepareUser() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Jožko");
        user.setLastName("Pročko");
        user.setUsername("JozkoProcko");
        user.setHashedPassword("passHash1");
        user.setIsTeacher(true);

        user2 = new User();
        user2.setId(2L);
        user2.setFirstName("David");
        user2.setLastName("Lister");
        user2.setUsername("DaveLister");
        user2.setHashedPassword("passHash2");
        user2.setIsTeacher(false);

    }
    @BeforeMethod
    public void openMocks() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userFacade = new UserFacadeImpl(userService, beanMappingService);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testRegisterUser() {
        UserCreateDTO userCreateDTO = beanMappingService.mapTo(user, UserCreateDTO.class);
        userFacade.registerUser(userCreateDTO, "134");

        verify(userService, times(1)).registerUser(any(User.class),(anyString()));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetUserWithExistingId() {
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        UserDTO get = userFacade.findUserById(user.getId()).get();

        Assert.assertNotNull(get, "User should return valid object");
        UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
        Assert.assertEquals(get, userDTO);

        verify(userService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userService);
        }

    @Test
    public void testGetUserWithNonExistingId() {
        when(userService.findById(user.getId())).thenReturn(Optional.empty());
        Optional <UserDTO> get = userFacade.findUserById(user.getId());

        Assert.assertFalse(get.isPresent());

        verify(userService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetUserWithExistingUsername() {
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserDTO get = userFacade.findUserByUsername(user.getUsername()).get();

        Assert.assertNotNull(get, "User should return valid object");
        UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
        Assert.assertEquals(get, userDTO);

        verify(userService, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void testGetUserWithNonExistingUsername() {
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        Optional <UserDTO> get = userFacade.findUserByUsername(user.getUsername());

        Assert.assertFalse(get.isPresent());

        verify(userService, times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void testRemoveUser() {
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        userFacade.removeUser(user.getId());

        verify(userService, times(1)).findById(user.getId());
        verify(userService, times(1)).removeUser(any(User.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(List.of(user));
        List<UserDTO> get = userFacade.getAllUsers();

        List<UserDTO> mappedList = beanMappingService.mapTo(List.of(user), UserDTO.class);
        Assert.assertEquals(get, mappedList);

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetAllTeachers() {
        when(userService.getAllTeachers()).thenReturn(List.of(user));
        List<UserDTO> get = userFacade.getAllTeachers();

        List<UserDTO> mappedList = beanMappingService.mapTo(List.of(user), UserDTO.class);
        Assert.assertEquals(get, mappedList);

        verify(userService, times(1)).getAllTeachers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetAllStudents() {
        when(userService.getAllStudents()).thenReturn(List.of(user2));
        List<UserDTO> get = userFacade.getAllStudents();

        List<UserDTO> mappedList = beanMappingService.mapTo(List.of(user2), UserDTO.class);
        Assert.assertEquals(get, mappedList);

        verify(userService, times(1)).getAllStudents();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testAuthenticateUser(){
        when(userService.authenticateUser(user,"134")).thenReturn(true);
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));

        UserAuthenticateDTO userAuthenticateDTO = beanMappingService.mapTo(user, UserAuthenticateDTO.class);
        userAuthenticateDTO.setPassword("134");
        Assert.assertTrue(userFacade.authenticate(userAuthenticateDTO));
        verify(userService, times(1)).findById(anyLong());
        verify(userService, times(1)).authenticateUser(any(User.class),anyString());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testUserIsTeacher() {
        when(userService.isTeacher(user)).thenReturn(true);
        UserDTO userDTO = beanMappingService.mapTo(user,UserDTO.class);
        Assert.assertTrue(userFacade.isTeacher(userDTO));

        verify(userService, times(1)).isTeacher(any(User.class));
        verifyNoMoreInteractions(userService);

    }

    @Test
    public void testUpdateUser(){
        UserDTO userDTO = beanMappingService.mapTo(user, UserDTO.class);
        Long result = userFacade.updateUser(userDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, user.getId());

        verify(userService, times(1)).updateUser(user);
        verifyNoMoreInteractions(userService);
    }

}
