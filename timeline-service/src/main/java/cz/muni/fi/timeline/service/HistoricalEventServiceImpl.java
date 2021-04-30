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
        if (historicalEvent == null) {
            throw new IllegalArgumentException("Historical event is null.");
        }

        try {
            historicalEventDao.create(historicalEvent);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void createEventInTimeline(HistoricalEvent event, HistoricalTimeline historicalTimeline) {
        if (event == null) {
            throw new IllegalArgumentException("Historical event is null.");
        }

        if (historicalTimeline == null) {
            throw new IllegalArgumentException("Historical timeline is null.");
        }

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
        if (historicalEvent == null) {
            throw new IllegalArgumentException("Historical event is null.");
        }

        try {
            historicalEventDao.update(historicalEvent);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeEvent(HistoricalEvent historicalEvent) {
        if (historicalEvent == null) {
            throw new IllegalArgumentException("Historical event is null.");
        }

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
        if (name == null) {
            throw new IllegalArgumentException("Name is null.");
        }

        try {
            return historicalEventDao.findByName(name);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<HistoricalEvent> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }

        try {
            return historicalEventDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
