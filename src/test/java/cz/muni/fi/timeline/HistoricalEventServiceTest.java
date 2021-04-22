package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.HistoricalEventDao;
import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.service.HistoricalEventService;
import cz.muni.fi.timeline.service.HistoricalEventServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
public class HistoricalEventServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    public HistoricalEventDao historicalEventDao;

    @Mock
    public HistoricalTimeline historicalTimeline;

    @Autowired
    @InjectMocks
    public HistoricalEventService historicalEventService;

    //events
    private HistoricalEvent event1;
    private HistoricalEvent event2;
    List<HistoricalEvent> events;


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
    public void prepareService() {
        historicalEventService = new HistoricalEventServiceImpl(historicalEventDao){
        };
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateHistoricalEventInTimeline(){
        historicalEventService.createEventInTimeline(event1,historicalTimeline);
        verify(historicalEventDao, times(1)).create(event1);
        verify(historicalTimeline, times(1)).addHistoricalEvent(event1);
    }

    @Test
    public void testCreateHistoricalEvent(){
        historicalEventService.createEvent(event2);
        verify(historicalEventDao, times(1)).create(event2);
    }

    @Test
    public void testUpdateHistoricalEvent(){
        historicalEventService.updateEvent(event1);
        verify(historicalEventDao,times(1)).update(any(HistoricalEvent.class));
    }

    @Test
    public void testRemoveHistoricalEvent(){
        historicalEventService.removeEvent(event1);
        verify(historicalEventDao, times(1)).remove(any(HistoricalEvent.class));
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
}
