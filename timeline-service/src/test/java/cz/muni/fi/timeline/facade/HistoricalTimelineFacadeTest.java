package cz.muni.fi.timeline.facade;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import cz.muni.fi.timeline.api.dto.*;
import cz.muni.fi.timeline.entity.*;
import cz.muni.fi.timeline.mapper.BeanMappingService;
import cz.muni.fi.timeline.api.HistoricalTimelineFacade;
import cz.muni.fi.timeline.mapper.BeanMappingServiceImpl;
import cz.muni.fi.timeline.service.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * tests for facade of historical timeline
 * @author Matej Macák
 */
public class HistoricalTimelineFacadeTest {

    @Mock
    private HistoricalTimelineService timelineService;

    @Mock
    private HistoricalEventService eventService;

    @Mock
    private TimelineCommentService commentService;

    @Mock
    private StudyGroupService studyGroupService;

    // TODO @Mock
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl(DozerBeanMapperBuilder.buildDefault());

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
        event.setDate(LocalDate.of(2005,4,8));

        timeline = new HistoricalTimeline();
        timeline.setId(2L);
        timeline.setName("Roman Empire");

        comment = new TimelineComment();
        comment.setId(3L);
        comment.setText("Hello!");

        user1 = new User();
        user1.setId(4L);
        user1.setFirstName("Jožko");
        user1.setLastName("Pročko");
        user1.setUsername("JozkoProcko");
        user1.setHashedPassword("passHash1");
        user1.setIsTeacher(true);

        studyGroup = new StudyGroup();
        studyGroup.setId(5L);
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
    public void testGetHistoricalTimelineWithExistingId() {
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.of(timeline));
        Optional<HistoricalTimelineDTO> get = historicalTimelineFacade.getHistoricalTimelineWithId(timeline.getId());

        Assert.assertTrue(get.isPresent(), "Historical Timeline should return valid object");
        HistoricalTimelineDTO mappedTimeline = beanMappingService.mapTo(timeline, HistoricalTimelineDTO.class);
        Assert.assertEquals(get.get(), mappedTimeline);

        verify(timelineService, times(1)).findTimelineById(anyLong());
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetHistoricalTimelineWithNonExistingId() {
        when(timelineService.findTimelineById(timeline.getId())).thenReturn(Optional.empty());
        Optional<HistoricalTimelineDTO> get = historicalTimelineFacade.getHistoricalTimelineWithId(timeline.getId());

        Assert.assertFalse(get.isPresent());

        verify(timelineService, times(1)).findTimelineById(anyLong());
        verifyNoMoreInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testUpdateHistoricalTimeline() {
        HistoricalTimelineDTO timelineDTO = beanMappingService.mapTo(timeline, HistoricalTimelineDTO.class);
        Long result = historicalTimelineFacade.updateHistoricalTimeline(timelineDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, timeline.getId());

        verify(timelineService, times(1)).updateTimeline(timeline);
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
        HistoricalEventDTO eventDTO = beanMappingService.mapTo(event, HistoricalEventDTO.class);
        Long result = historicalTimelineFacade.updateHistoricalEvent(eventDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, event.getId());

        verify(eventService, times(1)).updateEvent(event);
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetHistoricalEventWithExistingId() {
        when(eventService.findById(event.getId())).thenReturn(Optional.of(event));
        Optional<HistoricalEventDTO> get = historicalTimelineFacade.getHistoricalEventWithId(event.getId());

        Assert.assertTrue(get.isPresent(), "Historical Event should return valid object");
        HistoricalEventDTO mappedEvent = beanMappingService.mapTo(event, HistoricalEventDTO.class);
        Assert.assertEquals(get.get(), mappedEvent);

        verify(eventService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(eventService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(commentService);
    }

    @Test
    public void testGetHistoricalEventWithNonExistingId() {
        when(eventService.findById(event.getId())).thenReturn(Optional.empty());
        Optional<HistoricalEventDTO> get = historicalTimelineFacade.getHistoricalEventWithId(event.getId());

        Assert.assertFalse(get.isPresent());

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
        TimelineCommentDTO timelineCommentDTO = beanMappingService.mapTo(comment, TimelineCommentDTO.class);
        Long result = historicalTimelineFacade.updateTimelineComment(timelineCommentDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, comment.getId());

        verify(commentService, times(1)).updateTimelineComment(comment);
        verifyNoMoreInteractions(commentService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }

    @Test
    public void testGetTimelineCommentWithExistingId() {
        when(commentService.findTimelineCommentById(comment.getId())).thenReturn(Optional.of(comment));
        Optional<TimelineCommentDTO> get = historicalTimelineFacade.getTimelineCommentWithId(comment.getId());

        Assert.assertTrue(get.isPresent(), "Timeline comment should return valid object");
        TimelineCommentDTO mappedComment = beanMappingService.mapTo(comment, TimelineCommentDTO.class);
        Assert.assertEquals(get.get(), mappedComment);

        verify(commentService, times(1)).findTimelineCommentById(anyLong());
        verifyNoMoreInteractions(commentService);
        verifyNoInteractions(timelineService);
        verifyNoInteractions(studyGroupService);
        verifyNoInteractions(eventService);
    }

    @Test
    public void testGetTimelineCommentWithNonExistingId() {
        when(commentService.findTimelineCommentById(comment.getId())).thenReturn(Optional.empty());
        Optional<TimelineCommentDTO> get = historicalTimelineFacade.getTimelineCommentWithId(comment.getId());

        Assert.assertFalse(get.isPresent());

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
