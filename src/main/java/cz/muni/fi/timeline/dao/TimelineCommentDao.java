package cz.muni.fi.timeline.dao;

import cz.muni.fi.timeline.entity.TimelineComment;

import java.util.List;
import java.util.Optional;

/**
 * Data access interface (DAO) for TimelineComment entity
 *
 * @author Tri Le Mau
 */

public interface TimelineCommentDao {

    /**
     * Adds a TimelineComment entity into the database
     *
     * @param timelineComment TimelineComment entity that is added to the database
     */
    void create(TimelineComment timelineComment);

    /**
     * Finds a TimelineComment entity with id
     *
     * @param id Id used for finding the TimelineComment entity
     * @return A TimelineComment with id
     */
    Optional<TimelineComment> findById(Long id);

    /**
     * Updates a TimelineComment entity in the database
     *
     * @param timelineComment TimelineComment entity that is updated in the database
     */
    void update(TimelineComment timelineComment);

    /**
     * Removes a TimelineComment entity from the database
     *
     * @param timelineComment TimelineComment entity that is removed from the database
     */
    void remove(TimelineComment timelineComment);

    /**
     * Get all TimelineComment entities in the database
     *
     * @return All TimelineComment entities in the database
     */
    List<TimelineComment> findAll();
}
