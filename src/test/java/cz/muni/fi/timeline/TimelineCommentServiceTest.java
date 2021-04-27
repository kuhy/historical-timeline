package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.TimelineCommentDao;
import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.entity.TimelineComment;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.service.TimelineCommentService;
import cz.muni.fi.timeline.service.TimelineCommentServiceImpl;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests for TimelineCommentService.
 *
 * @author Ondřej Kuhejda
 */
public class TimelineCommentServiceTest {

    @Mock
    private TimelineCommentDao timelineCommentDaoMock;

    @Mock
    private HistoricalTimelineDao timelineDaoMock;

    private TimelineCommentService timelineCommentService;

    private AutoCloseable closeable;

    private TimelineComment comment;

    @BeforeMethod
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        timelineCommentService = new TimelineCommentServiceImpl(timelineCommentDaoMock, timelineDaoMock);

        comment = new TimelineComment();
        comment.setId(1L);
        comment.setText("Hello!");
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testCreateTimelineCommentInHistoricalTimeline() {
        comment.setId(null);

        HistoricalTimeline romanEmpire = new HistoricalTimeline();
        romanEmpire.setId(1L);
        romanEmpire.setName("Roman Empire");

        when(timelineDaoMock.findById(romanEmpire.getId())).thenReturn(Optional.of(romanEmpire));
        timelineCommentService.createTimelineCommentInTimeline(comment, romanEmpire);

        Mockito.verify(timelineCommentDaoMock).create(comment);
        Mockito.verifyNoMoreInteractions(timelineCommentDaoMock);

        Mockito.verify(timelineDaoMock).findById(romanEmpire.getId());
        Mockito.verify(timelineDaoMock).update(romanEmpire);
        Mockito.verifyNoMoreInteractions(timelineDaoMock);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void testCreateTimelineCommentInNonexistentHistoricalTimeline() {
        comment.setId(null);

        HistoricalTimeline romanEmpire = new HistoricalTimeline();
        romanEmpire.setId(1L);
        romanEmpire.setName("Roman Empire");

        when(timelineDaoMock.findById(romanEmpire.getId())).thenReturn(Optional.empty());
        timelineCommentService.createTimelineCommentInTimeline(comment, romanEmpire);
    }

    @Test
    public void testUpdateTimelineComment() {
        timelineCommentService.updateTimelineComment(comment);

        Mockito.verify(timelineCommentDaoMock).update(comment);
        Mockito.verifyNoMoreInteractions(timelineCommentDaoMock);
        Mockito.verifyNoInteractions(timelineDaoMock);
    }

    @Test
    public void testRemoveTimelineComment() {
        timelineCommentService.removeTimelineComment(comment);

        Mockito.verify(timelineCommentDaoMock).remove(comment);
        Mockito.verifyNoMoreInteractions(timelineCommentDaoMock);
        Mockito.verifyNoInteractions(timelineDaoMock);
    }

    @Test
    public void testFindTimelineById() {
        when(timelineCommentDaoMock.findById(comment.getId())).thenReturn(Optional.of(comment));

        Optional<TimelineComment> timeline = timelineCommentService.findTimelineCommentById(comment.getId());

        Assert.assertTrue(timeline.isPresent());
        Assert.assertEquals(timeline.get(), comment);

        Mockito.verify(timelineCommentDaoMock).findById(comment.getId());
        Mockito.verifyNoMoreInteractions(timelineCommentDaoMock);
        Mockito.verifyNoInteractions(timelineDaoMock);
    }

    @Test
    public void testFindAllTimelineComments() {
        TimelineComment comment2 = new TimelineComment();
        comment2.setId(2L);
        comment2.setText("Hi.");

        when(timelineCommentDaoMock.findAll()).thenReturn(List.of(comment, comment2));

        List<TimelineComment> timelines = timelineCommentService.findAllComments();

        Assert.assertEquals(timelines.size(), 2);
        Assert.assertTrue(timelines.contains(comment));
        Assert.assertTrue(timelines.contains(comment2));

        Mockito.verify(timelineCommentDaoMock).findAll();
        Mockito.verifyNoMoreInteractions(timelineCommentDaoMock);
        Mockito.verifyNoInteractions(timelineDaoMock);
    }
}
