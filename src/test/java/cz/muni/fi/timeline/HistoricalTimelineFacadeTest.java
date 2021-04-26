package cz.muni.fi.timeline;

import cz.muni.fi.timeline.api.BeanMappingService;
import cz.muni.fi.timeline.api.HistoricalTimelineFacade;
import cz.muni.fi.timeline.api.HistoricalTimelineFacadeImpl;
import cz.muni.fi.timeline.api.dto.*;
import cz.muni.fi.timeline.entity.*;
import cz.muni.fi.timeline.service.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * tests for facad of historical timeline
 * @author Matej Macák
 */
@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
public class HistoricalTimelineFacadeTest  extends AbstractTestNGSpringContextTests {

    @Mock
    private HistoricalTimelineService timelineService;

    @Mock
    private HistoricalEventService eventService;

    @Mock
    private TimelineCommentService commentService;

    @Mock
    private StudyGroupService studyGroupService;

    @Inject
    private BeanMappingService beanMappingService;

    private HistoricalTimelineFacade historicalTimelineFacade;

    private AutoCloseable autoCloseable;

    private HistoricalEvent event;
    private HistoricalTimeline timeline;
    private TimelineComment comment;
    private User user1;
    private User user2;
    private StudyGroup studyGroup;

    @BeforeMethod
    public void prepareEntities() {

        event = new HistoricalEvent();
        event.setId(1L);
        event.setName("Funeral of Pope");
        event.setDescription("John Paul II");
        event.setLocation("Vatican");

        timeline = new HistoricalTimeline();
        timeline.setId(1L);
        timeline.setName("Roman Empire");

        comment = new TimelineComment();
        comment.setId(1L);
        comment.setText("Hello!");

        user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Jožko");
        user1.setLastName("Pročko");
        user1.setUsername("JozkoProcko");
        user1.setHashedPassword("passHash1");
        user1.setIsTeacher(true);

        studyGroup = new StudyGroup();
        studyGroup.setId(1L);
        studyGroup.setName("English group");
    }

    @BeforeMethod
    public void openMocks() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        historicalTimelineFacade = new HistoricalTimelineFacadeImpl(timelineService,eventService,commentService,studyGroupService,beanMappingService);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void createHistoricalTimelineTest(){
        HistoricalTimelineCreateDTO timelineCreateDTO = beanMappingService.mapTo(timeline, HistoricalTimelineCreateDTO.class);
        when(studyGroupService.findById(studyGroup.getId())).thenReturn(Optional.of(studyGroup));
        historicalTimelineFacade.createHistoricalTimeline(timelineCreateDTO,studyGroup.getId());

        verify(timelineService, times(1)).createTimelineInStudyGroup(any(HistoricalTimeline.class),any(StudyGroup.class));
        verify(studyGroupService, times(1)).findById(studyGroup.getId());
        verifyNoMoreInteractions(timelineService);
        verifyNoMoreInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetHistoricalTimelineWithExistingId() {
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.of(timeline));
        HistoricalTimelineDTO get = historicalTimelineFacade.getHistoricalTimelineWithId(studyGroup.getId());

        Assert.assertNotNull(get, "Historical Timeline should return valid object");
        HistoricalTimelineDTO mappedTimeline = beanMappingService.mapTo(timeline, HistoricalTimelineDTO.class);
        Assert.assertEquals(get, mappedTimeline);

        verify(timelineService, times(1)).findTimelineById(anyLong());
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetHistoricalTimelineWithNonExistingId() {
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.empty());
        HistoricalTimelineDTO get = historicalTimelineFacade.getHistoricalTimelineWithId(studyGroup.getId());

        Assert.assertNull(get);

        verify(timelineService, times(1)).findTimelineById(anyLong());
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testNewHistoricalTimelineName() {
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.of(timeline));
        HistoricalTimelineNewNameDTO newNameDTO = new HistoricalTimelineNewNameDTO();
        newNameDTO.setId(timeline.getId());
        newNameDTO.setName("Ancient Greece");

        historicalTimelineFacade.newHistoricalTimelineName(newNameDTO);

        verify(timelineService, times(1)).findTimelineById(timeline.getId());
        verify(timelineService, times(1)).updateTimeline(any(HistoricalTimeline.class));
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testRemoveHistoricalTimeline() {
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.of(timeline));
        historicalTimelineFacade.removeHistoricalTimeline(timeline.getId());

        verify(timelineService, times(1)).findTimelineById(timeline.getId());
        verify(timelineService, times(1)).removeTimeline(any(HistoricalTimeline.class));
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetAllHistoricalTimelines() {
        when(timelineService.findAllTimelines()).thenReturn(List.of(timeline));
        List<HistoricalTimelineDTO> get = historicalTimelineFacade.getAllHistoricalTimelines();

        List<HistoricalTimelineDTO> mappedList = beanMappingService.mapTo(List.of(timeline), HistoricalTimelineDTO.class);
        Assert.assertEquals(get, mappedList);

        verify(timelineService, times(1)).findAllTimelines();
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testCreateHistoricalEvent() {
        HistoricalEventCreateDTO historicalEventCreateDTO = beanMappingService.mapTo(event, HistoricalEventCreateDTO.class);
        historicalTimelineFacade.createHistoricalEvent(historicalEventCreateDTO);

        verify(eventService, times(1)).createEvent(any(HistoricalEvent.class));
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void createHistoricalEventInTimelineTest(){
        HistoricalEventCreateDTO eventCreateDTO = beanMappingService.mapTo(event, HistoricalEventCreateDTO.class);
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.of(timeline));
        historicalTimelineFacade.createEventInTimeline(eventCreateDTO,timeline.getId());

        verify(eventService, times(1)).createEventInTimeline(any(HistoricalEvent.class),any(HistoricalTimeline.class));
        verify(timelineService, times(1)).findTimelineById(timeline.getId());
        verifyNoMoreInteractions(eventService);
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testUpdateHistoricalEvent() {
        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        HistoricalEventDTO updateDTO = new HistoricalEventDTO();
        updateDTO.setId(event.getId());
        updateDTO.setName("Battle of Thermopylae");
        updateDTO.setDate(LocalDate.of(480, 8, 20));
        updateDTO.setDescription("Bravery of Sparta");
        updateDTO.setLocation("Thermopylae");

        historicalTimelineFacade.newEventUpdate(updateDTO);

        verify(eventService, times(1)).findById(event.getId());
        verify(eventService, times(1)).updateEvent(any(HistoricalEvent.class));
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetHistoricalEventWithExistingId() {
        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        HistoricalEventDTO get = historicalTimelineFacade.getHistoricalEventWithId(event.getId());

        Assert.assertNotNull(get, "Historical Event should return valid object");
        HistoricalEventDTO mappedEvent = beanMappingService.mapTo(event, HistoricalEventDTO.class);
        Assert.assertEquals(get, mappedEvent);

        verify(eventService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetHistoricalEventWithNonExistingId() {
        when(eventService.findById(event.getId())).thenReturn(Optional.empty());
        HistoricalEventDTO get = historicalTimelineFacade.getHistoricalEventWithId(event.getId());

        Assert.assertNull(get);

        verify(eventService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testRemoveHistoricalEvent() {
        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        historicalTimelineFacade.removeHistoricalEvent(event.getId());

        verify(eventService, times(1)).findById(event.getId());
        verify(eventService, times(1)).removeEvent(any(HistoricalEvent.class));
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetAllHistoricalEvents() {
        when(eventService.getAllEvents()).thenReturn(List.of(event));
        List<HistoricalEventDTO> get = historicalTimelineFacade.getAllHistoricalEvents();

        List<HistoricalEventDTO> mappedList = beanMappingService.mapTo(List.of(event), HistoricalEventDTO.class);
        Assert.assertEquals(get, mappedList);

        verify(eventService, times(1)).getAllEvents();
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void createTimelineComment(){
        TimelineCommentCreateDTO timelineCommentCreateDTO = beanMappingService.mapTo(event, TimelineCommentCreateDTO.class);
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.of(timeline));
        historicalTimelineFacade.createTimelineComment(timelineCommentCreateDTO,timeline.getId());

        verify(commentService, times(1)).createTimelineCommentInTimeline(any(TimelineComment.class),any(HistoricalTimeline.class));
        verify(timelineService, times(1)).findTimelineById(timeline.getId());
        verifyNoMoreInteractions(commentService);
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }

    @Test
    public void testUpdateTimelineComment() {
        when(commentService.findTimelineCommentById(comment.getId())).thenReturn(Optional.of(comment));
        TimelineCommentDTO updateDTO = new TimelineCommentDTO();
        updateDTO.setId(event.getId());
        updateDTO.setText("Pay attention, please");

        historicalTimelineFacade.newTimelineCommentText(updateDTO);

        verify(commentService, times(1)).findTimelineCommentById(comment.getId());
        verify(commentService, times(1)).updateTimelineComment(any(TimelineComment.class));
        verifyNoMoreInteractions(commentService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }

    @Test
    public void testGetTimelineCommentWithExistingId() {
        when(commentService.findTimelineCommentById(comment.getId())).thenReturn(Optional.of(comment));
        TimelineCommentDTO get = historicalTimelineFacade.getTimelineCommentWithId(comment.getId());

        Assert.assertNotNull(get, "Timeline comment should return valid object");
        TimelineCommentDTO mappedComment = beanMappingService.mapTo(comment, TimelineCommentDTO.class);
        Assert.assertEquals(get, mappedComment);

        verify(commentService, times(1)).findTimelineCommentById(anyLong());
        verifyNoMoreInteractions(commentService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }

    @Test
    public void testGetTimelineCommentWithNonExistingId() {
        when(commentService.findTimelineCommentById(comment.getId())).thenReturn(Optional.empty());
        TimelineCommentDTO get = historicalTimelineFacade.getTimelineCommentWithId(comment.getId());

        Assert.assertNull(get);

        verify(commentService, times(1)).findTimelineCommentById(anyLong());
        verifyNoMoreInteractions(commentService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }

    @Test
    public void testRemoveTimelineComment() {
        when(commentService.findTimelineCommentById(comment.getId())).thenReturn(Optional.of(comment));
        historicalTimelineFacade.removeTimelineComment(comment.getId());

        verify(commentService, times(1)).findTimelineCommentById(comment.getId());
        verify(commentService, times(1)).removeTimelineComment(any(TimelineComment.class));
        verifyNoMoreInteractions(commentService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }

    @Test
    public void testGetAllTimelineComments() {
        when(commentService.findAllComments()).thenReturn(List.of(comment));
        List<TimelineCommentDTO> get = historicalTimelineFacade.getAllTimelineComments();

        List<TimelineCommentDTO> mappedList = beanMappingService.mapTo(List.of(comment), TimelineCommentDTO.class);
        Assert.assertEquals(get, mappedList);

        verify(commentService, times(1)).findAllComments();
        verifyNoMoreInteractions(commentService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }
}
