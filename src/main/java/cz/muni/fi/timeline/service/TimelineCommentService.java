package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.TimelineComment;

import java.util.List;
import java.util.Optional;


/**
 * Service for manipulation with timeline comments.
 *
 * @author Ondřej Kuhejda
 */
public interface TimelineCommentService {

    /**
     * Creates new comment in the database.
     * @param comment comment that will be created
     * @param timeline timeline where the comment will be added
     */
    void createTimelineCommentInTimeline(TimelineComment comment, HistoricalTimeline timeline);

    /**
     * Removes comment from the database.
     * @param comment comment that will be removed
     */
    void removeTimelineComment(TimelineComment comment);

    /**
     * Updates comment in the database.
     * @param comment comment that will be updated
     */
    void updateTimelineComment(TimelineComment comment);

    /**
     * Returns comment with the given id.
     * @param id id of the comment that will be returned
     * @return comment with the given id
     */
    Optional<TimelineComment> findTimelineCommentById(Long id);

    /**
     * Retrieves all comments from the database.
     * @return all comments
     */
    List<TimelineComment> findAllComments();
}
