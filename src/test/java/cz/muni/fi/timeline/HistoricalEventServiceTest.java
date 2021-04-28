package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.HistoricalEventDao;
import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.service.HistoricalEventService;
import cz.muni.fi.timeline.service.HistoricalEventServiceImpl;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for Historical Event Service
 * @author Karolína Veselá
 */


public class HistoricalEventServiceTest{
    @Mock
    public HistoricalEventDao historicalEventDao;

    @Mock
    public HistoricalTimelineDao historicalTimelineDao;

    private AutoCloseable closeable;

    @Autowired
    public HistoricalEventService historicalEventService;

    //events
    private HistoricalEvent event1;
    private HistoricalEvent event2;
    private List<HistoricalEvent> events;

    //timeline
    private HistoricalTimeline timeline;

    @BeforeMethod
    public void prepareEvents(){

        event1 = new HistoricalEvent();
        event2 = new HistoricalEvent();
        event1.setId(2L);
        event2.setId(5L);
        event1.setName("Funeral of Pope");
        event2.setName("Crucifixion of Jesus");
        events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
    }

    @BeforeMethod
    public void prepareTimeline(){

        timeline = new HistoricalTimeline();
        timeline.setId(1L);
        timeline.setName("Roman Empire");
    }

    @BeforeMethod
    public void prepareService() {
        closeable = MockitoAnnotations.openMocks(this);
        historicalEventService = new HistoricalEventServiceImpl(historicalEventDao, historicalTimelineDao);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testCreateHistoricalEventInTimeline(){
        when(historicalTimelineDao.findById(timeline.getId())).thenReturn(Optional.of(timeline));
        historicalEventService.createEventInTimeline(event1, timeline);

        verify(historicalEventDao, times(1)).create(any(HistoricalEvent.class));
        verifyNoMoreInteractions(historicalEventDao);

        verify(historicalTimelineDao, times(1)).findById(timeline.getId());
        verify(historicalTimelineDao, times(1)).update(any(HistoricalTimeline.class));
        verifyNoMoreInteractions(historicalTimelineDao);
    }

    @Test
    public void testCreateHistoricalEvent(){
        historicalEventService.createEvent(event2);
        verify(historicalEventDao, times(1)).create(any(HistoricalEvent.class));
        verifyNoMoreInteractions(historicalEventDao);
    }

    @Test
    public void testUpdateHistoricalEvent(){
        historicalEventService.updateEvent(event1);
        verify(historicalEventDao,times(1)).update(any(HistoricalEvent.class));
        verifyNoMoreInteractions(historicalEventDao);
    }

    @Test
    public void testRemoveHistoricalEvent(){
        historicalEventService.removeEvent(event1);
        verify(historicalEventDao, times(1)).remove(any(HistoricalEvent.class));
        verifyNoMoreInteractions(historicalEventDao);
    }

    @Test
    public void testFindById() {
        when(historicalEventDao.findById(event1.getId())).thenReturn(Optional.ofNullable(event1));
        Optional<HistoricalEvent> find = historicalEventService.findById(event1.getId());

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get(), event1);
    }

    @Test
    public void testFindByName() {
        when(historicalEventDao.findByName(event1.getName())).thenReturn(List.of(event1));
        List<HistoricalEvent> find = historicalEventService.findByName(event1.getName());

        Assert.assertEquals(find.size(), 1);
        Assert.assertEquals(find.get(0), event1);
    }

    @Test
    public void testGetAllEvents(){
        when(historicalEventDao.findAll()).thenReturn(events);
        Assert.assertEquals(historicalEventService.getAllEvents(), events);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEventNull() {
        historicalEventService.createEvent(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEventInTimelineNullEvent() {
        historicalEventService.createEventInTimeline(null, timeline);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateEventInTimelineNullTimeline() {
        historicalEventService.createEventInTimeline(event1, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateEventNull() {
        historicalEventService.updateEvent(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveEventNull() {
        historicalEventService.removeEvent(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByNameNull() {
        historicalEventService.findByName(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdNull() {
        historicalEventService.findById(null);
    }
}
