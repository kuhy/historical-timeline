package cz.muni.fi.timeline.dao;
import cz.muni.fi.timeline.entity.HistoricalEvent;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for HistoricalEvent entity.
 *
 * @author Karolína Veselá
 */

public interface HistoricalEventDao {

    /**
     * Adds a HistoricalEvent entity into the database
     *
     * @param historicalEvent HistoricalEvent entity that is added to the database
     */
    void create(HistoricalEvent historicalEvent);

    /**
     * Get all HistoricalEvent entities in the database
     *
     * @return All HistoricalEvent entities in the database
     */
    List<HistoricalEvent> findAll();

    /**
     * Finds a HistoricalEvent entity based on id
     *
     * @param id Id used for finding the HistoricalEvent entity
     * @return A HistoricalEvent based on id
     */
    Optional<HistoricalEvent> findById(Long id);

    /**
     * Find all HistoricalEvent entities with the same name
     *
     * @param name Name used to find HistoricalEvent entities
     * @return list of all HistoricalEvents with the same name
     */
    List<HistoricalEvent> findByName(String name);

    /**
     * Updates a HistoricalEvent entity in the database
     *
     * @param historicalEvent HistoricalEvent entity that is updated in the database
     */
    void update(HistoricalEvent historicalEvent);

    /**
     * Removes a HistoricalEvent entity from the database
     *
     * @param historicalEvent entity that is removed from the database
     */
    void remove(HistoricalEvent historicalEvent);
}
