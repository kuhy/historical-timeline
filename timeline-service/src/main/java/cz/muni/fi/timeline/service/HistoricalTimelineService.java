package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.StudyGroup;

import java.util.List;
import java.util.Optional;


/**
 * Service for manipulation with historical timelines.
 *
 * @author Ondřej Kuhejda
 */
public interface HistoricalTimelineService {

    /**
     * Creates new timeline in study group.
     * @param timeline timeline that will be created
     * @param studyGroup study group to which the timeline will be added
     */
    void createTimelineInStudyGroup(HistoricalTimeline timeline, StudyGroup studyGroup);

    /**
     * Updates timeline in the database.
     * @param timeline timeline that will be updated
     */
    void updateTimeline(HistoricalTimeline timeline);

    /**
     * Removes timeline from the database.
     * @param timeline timeline that will be removed
     */
    void removeTimeline(HistoricalTimeline timeline);

    /**
     * Retrieves timeline from the database by the id.
     * @param id id of the timeline
     * @return timeline with the given id
     */
    Optional<HistoricalTimeline> findTimelineById(Long id);

    /**
     * Retrieves timelines from the database with the given name.
     * @param name name of the timelines
     * @return timelines with the given name
     */
    List<HistoricalTimeline> findTimelineByName(String name);

    /**
     * Returns all timelines from the database.
     * @return all timelines
     */
    List<HistoricalTimeline> findAllTimelines();
}
