package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.service.HistoricalTimelineService;
import cz.muni.fi.timeline.service.HistoricalTimelineServiceImpl;
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
 * Tests for HistoricalTimelineService.
 *
 * @author Ondřej Kuhejda
 */
public class HistoricalTimelineServiceTest {

    @Mock
    private HistoricalTimelineDao timelineDaoMock;

    @Mock
    private StudyGroupDao studyGroupDaoMock;

    private HistoricalTimelineService timelineService;

    private AutoCloseable closeable;

    private HistoricalTimeline romanEmpire;

    @BeforeMethod
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        timelineService = new HistoricalTimelineServiceImpl(timelineDaoMock, studyGroupDaoMock);

        romanEmpire = new HistoricalTimeline();
        romanEmpire.setId(1L);
        romanEmpire.setName("Roman Empire");
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testCreateHistoricalTimelineInStudyGroup() {
        romanEmpire.setId(null);

        StudyGroup historyGroup = new StudyGroup();
        historyGroup.setId(1L);
        historyGroup.setName("History");

        when(studyGroupDaoMock.findById(historyGroup.getId())).thenReturn(Optional.of(historyGroup));
        timelineService.createTimelineInStudyGroup(romanEmpire, historyGroup);

        Mockito.verify(timelineDaoMock).create(romanEmpire);
        Mockito.verifyNoMoreInteractions(timelineDaoMock);

        Mockito.verify(studyGroupDaoMock).findById(historyGroup.getId());
        Mockito.verify(studyGroupDaoMock).update(historyGroup);
        Mockito.verifyNoMoreInteractions(studyGroupDaoMock);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void testCreateHistoricalTimelineInNonexistentStudyGroup() {
        romanEmpire.setId(null);

        StudyGroup historyGroup = new StudyGroup();
        historyGroup.setId(1L);
        historyGroup.setName("History");

        when(studyGroupDaoMock.findById(historyGroup.getId())).thenReturn(Optional.empty());

        timelineService.createTimelineInStudyGroup(romanEmpire, historyGroup);
    }

    @Test
    public void testUpdateHistoricalTimeline() {
        timelineService.updateTimeline(romanEmpire);

        Mockito.verify(timelineDaoMock).update(romanEmpire);
        Mockito.verifyNoMoreInteractions(timelineDaoMock);
        Mockito.verifyNoInteractions(studyGroupDaoMock);
    }

    @Test
    public void testRemoveHistoricalTimeline() {
        timelineService.removeTimeline(romanEmpire);

        Mockito.verify(timelineDaoMock).remove(romanEmpire);
        Mockito.verifyNoMoreInteractions(timelineDaoMock);
        Mockito.verifyNoInteractions(studyGroupDaoMock);
    }

    @Test
    public void testFindTimelineById() {
        when(timelineDaoMock.findById(romanEmpire.getId())).thenReturn(Optional.of(romanEmpire));

        Optional<HistoricalTimeline> timeline = timelineService.findTimelineById(romanEmpire.getId());

        Assert.assertTrue(timeline.isPresent());
        Assert.assertEquals(timeline.get(), romanEmpire);

        Mockito.verify(timelineDaoMock).findById(romanEmpire.getId());
        Mockito.verifyNoMoreInteractions(timelineDaoMock);
        Mockito.verifyNoInteractions(studyGroupDaoMock);
    }

    @Test
    public void testFindTimelineByName() {
        when(timelineDaoMock.findByName(romanEmpire.getName())).thenReturn(List.of(romanEmpire));

        List<HistoricalTimeline> timeline = timelineService.findTimelineByName(romanEmpire.getName());

        Assert.assertEquals(timeline.size(), 1);
        Assert.assertEquals(timeline.get(0), romanEmpire);

        Mockito.verify(timelineDaoMock).findByName(romanEmpire.getName());
        Mockito.verifyNoMoreInteractions(timelineDaoMock);
        Mockito.verifyNoInteractions(studyGroupDaoMock);
    }

    @Test
    public void testFindAllTimelines() {
        HistoricalTimeline persianEmpire = new HistoricalTimeline();
        persianEmpire.setId(2L);
        persianEmpire.setName("Persian Empire");

        when(timelineDaoMock.findAll()).thenReturn(List.of(romanEmpire, persianEmpire));

        List<HistoricalTimeline> timelines = timelineService.findAllTimelines();

        Assert.assertEquals(timelines.size(), 2);
        Assert.assertTrue(timelines.contains(romanEmpire));
        Assert.assertTrue(timelines.contains(persianEmpire));

        Mockito.verify(timelineDaoMock).findAll();
        Mockito.verifyNoMoreInteractions(timelineDaoMock);
        Mockito.verifyNoInteractions(studyGroupDaoMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTimelineInStudyGroupNullTimeline() {
        StudyGroup historyGroup = new StudyGroup();
        historyGroup.setId(1L);
        historyGroup.setName("History");

        timelineService.createTimelineInStudyGroup(null, historyGroup);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTimelineInStudyGroupNullStudyGroup() {
        timelineService.createTimelineInStudyGroup(romanEmpire, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateTimelineNull() {
        timelineService.updateTimeline(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveTimelineNull() {
        timelineService.removeTimeline(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindTimelineByIdNull() {
        timelineService.findTimelineById(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindTimelineByNameNull() {
        timelineService.findTimelineByName(null);
    }
}
