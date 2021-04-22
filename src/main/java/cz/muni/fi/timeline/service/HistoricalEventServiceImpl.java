package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.HistoricalEventDao;
import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.User;
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

    @Inject
    public HistoricalEventServiceImpl(HistoricalEventDao historicalEventDao) {
        this.historicalEventDao = historicalEventDao;}

    @Override
    public void createEvent(HistoricalEvent historicalEvent){
      historicalEventDao.create(historicalEvent);
    }

    @Override
    public void createEventInTimeline(HistoricalEvent event, HistoricalTimeline timeline) {
        historicalEventDao.create(event);
        timeline.addHistoricalEvent(event);
    }

    @Override
    public void updateEvent(HistoricalEvent historicalEvent) {
        historicalEventDao.update(historicalEvent);
    }

    @Override
    public void removeEvent(HistoricalEvent historicalEvent) {
        historicalEventDao.remove(historicalEvent);
    }

    @Override
    public List<HistoricalEvent> getAllEvents() {
    return historicalEventDao.findAll();
    }

    @Override
    public List<HistoricalEvent> findByName(String name){
        return historicalEventDao.findByName(name);
    }

    @Override
    public Optional<HistoricalEvent> findById(long id) {
        return historicalEventDao.findById(id);
    }
}
