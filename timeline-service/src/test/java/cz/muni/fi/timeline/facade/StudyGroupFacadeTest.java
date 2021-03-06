package cz.muni.fi.timeline.facade;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import cz.muni.fi.timeline.api.dto.HistoricalEventCreateDTO;
import cz.muni.fi.timeline.api.dto.HistoricalTimelineCreateDTO;
import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.mapper.BeanMappingService;
import cz.muni.fi.timeline.api.StudyGroupFacade;
import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.mapper.BeanMappingServiceImpl;
import cz.muni.fi.timeline.service.HistoricalTimelineService;
import cz.muni.fi.timeline.service.StudyGroupService;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;
import cz.muni.fi.timeline.service.UserService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for StudyGroup Facade
 * @author Tri Le Mau
 */
public class StudyGroupFacadeTest {

    @Mock
    private StudyGroupService studyGroupService;

    @Mock
    private UserService userService;

    @Mock
    private HistoricalTimelineService timelineService;

    // TODO @Mock
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl(DozerBeanMapperBuilder.buildDefault());

    private StudyGroupFacade studyGroupFacade;

    private AutoCloseable autoCloseable;

    private User user;
    private StudyGroup studyGroup;
    private HistoricalTimeline timeline;

    @BeforeMethod
    public void prepareEntities() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Jo??ko");
        user.setLastName("Pro??ko");
        user.setUsername("JozkoProcko");
        user.setHashedPassword("passHash1");
        user.setIsTeacher(true);

        studyGroup = new StudyGroup();
        studyGroup.setId(1L);
        studyGroup.setName("English group");

        timeline = new HistoricalTimeline();
        timeline.setName("Ancient Rome");
        timeline.setId(2L);
    }

    @BeforeMethod
    public void openMocks() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        studyGroupFacade = new StudyGroupFacadeImpl(studyGroupService, userService, timelineService, beanMappingService);
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

    @Test
    public void testCreateTimelineInStudyGroup() {
        HistoricalTimelineCreateDTO timelineCreateDTO = beanMappingService.mapTo(timeline, HistoricalTimelineCreateDTO.class);
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        studyGroupFacade.createTimelineInStudyGroup(timelineCreateDTO, studyGroup.getId());

        verify(timelineService, times(1)).createTimelineInStudyGroup(any(HistoricalTimeline.class),any(StudyGroup.class));
        verify(studyGroupService, times(1)).findById(studyGroup.getId());
        verifyNoMoreInteractions(timelineService);
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(userService);
    }
}
