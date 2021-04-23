package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service for manipulation with historical events.
 * @author Karolína Veselá
 */

public interface HistoricalEventService {
    /**
     * create new event in the database
     * @param historicalEvent event that will be created
     */
    void createEvent(HistoricalEvent historicalEvent);

    /**
     * create event in given timeline
     * @param event event that is created
     * @param timeline timeline to where is the event created
     */
    void createEventInTimeline(HistoricalEvent event, HistoricalTimeline timeline);

    /**
     * update event in the database
     * @param historicalEvent event that will be updated
     */
    void updateEvent(HistoricalEvent historicalEvent);

    /**
     * remove event from the database
     * @param historicalEvent event that will be removed
     */
    void removeEvent(HistoricalEvent historicalEvent);

    /**
     *Gives all events based on given name from the database
     *@param name name of the given events
     *@return all events with the given name
     */
    List<HistoricalEvent> findByName(String name);

    /**
     * Gives all events from the database
     * @return all events from the database
     */
    List<HistoricalEvent> getAllEvents();

    /**
     * Gives event based on given id from the database
     * @param id id of the given event
     * @return event with the given id
     */
    Optional<HistoricalEvent> findById(long id);
}
