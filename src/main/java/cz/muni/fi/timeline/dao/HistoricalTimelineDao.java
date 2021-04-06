package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.HistoricalTimeline;

import java.util.List;
import java.util.Optional;

/**
 * Data access interface (DAO) for HistoricalTimeline entity
 *
 * @author Tri Le Mau
 */

public interface HistoricalTimelineDao {

    /**
     * Adds a HistoricalTimeline entity into the database
     *
     * @param historicalTimeline HistoricalTimeline entity that is added to the database
     */
    void create(HistoricalTimeline historicalTimeline);

    /**
     * Finds a HistoricalTimeline entity with id
     *
     * @param id Id used for finding the HistoricalTimeline entity
     * @return A HistoricalTimeline with id
     */
    Optional<HistoricalTimeline> findById(Long id);

    /**
     * Updates a HistoricalTimeline entity in the database
     *
     * @param historicalTimeline HistoricalTimeline entity that is updated in the database
     */
    void update(HistoricalTimeline historicalTimeline);

    /**
     * Removes a HistoricalTimeline entity from the database
     *
     * @param historicalTimeline HistoricalTimeline entity that is removed from the database
     */
    void remove(HistoricalTimeline historicalTimeline);

    /**
     * Get all HistoricalTimeline entities in the database
     *
     * @return All HistoricalTimeline entities in the database
     */
    List<HistoricalTimeline> findAll();

    /**
     * Find all HistoricalTimeline entities with the same name
     *
     * @param name Name used to find HistoricalTimeline entities
     * @return all HistoricalTimeline entities with the same name
     */
    List<HistoricalTimeline> findByName(String name);
}
