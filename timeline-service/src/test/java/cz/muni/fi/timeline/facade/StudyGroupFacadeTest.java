package cz.muni.fi.timeline.facade;

import cz.muni.fi.timeline.TimelineServiceApplicationContext;
import cz.muni.fi.timeline.mapper.BeanMappingService;
import cz.muni.fi.timeline.api.StudyGroupFacade;
import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.StudyGroupService;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;
import cz.muni.fi.timeline.service.UserService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for StudyGroup Facade
 * @author Tri Le Mau
 */
@ContextConfiguration(classes = TimelineServiceApplicationContext.class)
public class StudyGroupFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private StudyGroupService studyGroupService;

    @Mock
    private UserService userService;

    @Inject
    private BeanMappingService beanMappingService;

    private StudyGroupFacade studyGroupFacade;

    private AutoCloseable autoCloseable;

    private User user;
    private StudyGroup studyGroup;

    @BeforeMethod
    public void prepareEntities() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Jožko");
        user.setLastName("Pročko");
        user.setUsername("JozkoProcko");
        user.setHashedPassword("passHash1");
        user.setIsTeacher(true);

        studyGroup = new StudyGroup();
        studyGroup.setId(1L);
        studyGroup.setName("English group");
    }

    @BeforeMethod
    public void openMocks() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        studyGroupFacade = new StudyGroupFacadeImpl(studyGroupService, userService, beanMappingService);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testCreateStudyGroup() {
        StudyGroupCreateDTO studyGroupDTO = beanMappingService.mapTo(studyGroup, StudyGroupCreateDTO.class);
        studyGroupFacade.createStudyGroup(studyGroupDTO);

        verify(studyGroupService, times(1)).createStudyGroup(any(StudyGroup.class));
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(userService);
    }

    @Test
    public void testGetStudyGroupWithExistingId() {
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        Optional<StudyGroupDTO> get = studyGroupFacade.getStudyGroupWithId(studyGroup.getId());

        Assert.assertTrue(get.isPresent(), "Study group should return valid object");
        StudyGroupDTO mappedStudyGroup = beanMappingService.mapTo(studyGroup, StudyGroupDTO.class);
        Assert.assertEquals(get.get(), mappedStudyGroup);

        verify(studyGroupService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(userService);
    }

    @Test
    public void testGetStudyGroupWithNonExistingId() {
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.empty());
        Optional<StudyGroupDTO> get = studyGroupFacade.getStudyGroupWithId(studyGroup.getId());

        Assert.assertFalse(get.isPresent());

        verify(studyGroupService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(userService);
    }

    @Test
    public void testUpdateStudyGroup() {
        StudyGroupDTO studyGroupDTO = beanMappingService.mapTo(studyGroup, StudyGroupDTO.class);
        Long result = studyGroupFacade.updateStudyGroup(studyGroupDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, studyGroup.getId());

        verify(studyGroupService, times(1)).updateStudyGroup(studyGroup);
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(userService);
    }

    @Test
    public void testRemoveStudyGroup() {
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        studyGroupFacade.removeStudyGroup(studyGroup.getId());

        verify(studyGroupService, times(1)).findById(studyGroup.getId());
        verify(studyGroupService, times(1)).removeStudyGroup(any(StudyGroup.class));
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(userService);
    }

    @Test
    public void testAddUserToStudyGroup() throws UserAlreadyInStudyGroupException {
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        studyGroupFacade.addUserToStudyGroup(studyGroup.getId(), user.getId());

        verify(studyGroupService, times(1)).findById(studyGroup.getId());
        verify(userService, times(1)).findById(user.getId());
        verify(studyGroupService, times(1)).addUserToStudyGroup(any(StudyGroup.class), any(User.class));
        verifyNoMoreInteractions(studyGroupService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testRemoveUserFromStudyGroup() throws UserNotInStudyGroupException {
        studyGroup.addUser(user);
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        studyGroupFacade.removeUserFromStudyGroup(studyGroup.getId(), user.getId());

        verify(studyGroupService, times(1)).findById(studyGroup.getId());
        verify(userService, times(1)).findById(user.getId());
        verify(studyGroupService, times(1)).removeUserFromStudyGroup(any(StudyGroup.class), any(User.class));
        verifyNoMoreInteractions(studyGroupService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testGetAllStudyGroups() {
        when(studyGroupService.findAllStudyGroups()).thenReturn(List.of(studyGroup));
        List<StudyGroupDTO> get = studyGroupFacade.getAllStudyGroups();

        List<StudyGroupDTO> mappedList = beanMappingService.mapTo(List.of(studyGroup), StudyGroupDTO.class);
        Assert.assertEquals(get, mappedList);

        verify(studyGroupService, times(1)).findAllStudyGroups();
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(userService);
    }
}
