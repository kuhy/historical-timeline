package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.User;

public interface HistoricalEventService {
    void createEventInTimeline(HistoricalEvent event, HistoricalTimeline timeline);
    void updateEvent(HistoricalEvent historicalEvent);
    void removeEvent(HistoricalEvent historicalEvent);
    void getAllEventsForStudent(User student);
    // find by id
}
