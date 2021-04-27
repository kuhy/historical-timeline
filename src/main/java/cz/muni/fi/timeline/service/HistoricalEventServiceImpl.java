package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.HistoricalEventDao;
import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of HistoricalEventService
 * @author Karolína Veselá
 */
@Service
public class HistoricalEventServiceImpl implements HistoricalEventService {

    private final HistoricalEventDao historicalEventDao;
    private final HistoricalTimelineDao historicalTimelineDao;

    @Inject
    public HistoricalEventServiceImpl(HistoricalEventDao historicalEventDao, HistoricalTimelineDao historicalTimelineDao) {
        this.historicalEventDao = historicalEventDao;
        this.historicalTimelineDao = historicalTimelineDao;
    }

    @Override
    public void createEvent(HistoricalEvent historicalEvent){
        try {
            historicalEventDao.create(historicalEvent);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void createEventInTimeline(HistoricalEvent event, HistoricalTimeline historicalTimeline) {
        try {
            historicalEventDao.create(event);

            HistoricalTimeline timeline = historicalTimelineDao.findById(historicalTimeline.getId()).orElseThrow(() ->
                new ServiceLayerException("Historical timeline with the given id does not exist.")
            );

            timeline.addHistoricalEvent(event);

            historicalTimelineDao.update(timeline);
        } catch (ServiceLayerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateEvent(HistoricalEvent historicalEvent) {
        try {
            historicalEventDao.update(historicalEvent);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeEvent(HistoricalEvent historicalEvent) {
        try {
            historicalEventDao.remove(historicalEvent);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<HistoricalEvent> getAllEvents() {
        try {
            return historicalEventDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<HistoricalEvent> findByName(String name){
        try {
            return historicalEventDao.findByName(name);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<HistoricalEvent> findById(long id) {
        try {
            return historicalEventDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
