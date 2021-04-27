package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.*;

import java.util.List;
import java.util.Optional;

/**
 * Facade for historical timeline
 * @author Matej Macák
 */
public interface HistoricalTimelineFacade {

    //Timeline

    /**
     * creates new timeline in study group
     * @param historicalTimelineCreateDTO historical timeline used for creation
     * @param studyGroupId  id of group where timeline is created
     * @return id of created timeline
     */
    public Long createHistoricalTimeline(HistoricalTimelineCreateDTO historicalTimelineCreateDTO, Long studyGroupId);

    /**
     * return timeline with given id
     * @param id of the looking timeline
     * @return found timeline
     */
    public Optional<HistoricalTimelineDTO> getHistoricalTimelineWithId(Long id);

    /**
     * updates timeline
     * @param historicalTimelineDTO updated timeline
     * @return id of updated timeline
     */
    public Long updateHistoricalTimeline(HistoricalTimelineDTO historicalTimelineDTO);

    /**
     * removes timeline with given id
     * @param id of timeline to be removed
     */
    public void removeHistoricalTimeline(Long id);

    /**
     * returns all timelines
     * @return all timelines
     */
    public List<HistoricalTimelineDTO> getAllHistoricalTimelines();

    //Event

    /**
     * creates new event
     * @param historicalEventCreateDTO event used for creation
     * @return id of new event
     */
    public Long createHistoricalEvent(HistoricalEventCreateDTO historicalEventCreateDTO);

    /**
     * creates new event in timeline
     * @param historicalEventCreateDTO event used for creation
     * @param timelineId id of timeline
     * @return id of new event
     */
    public Long createEventInTimeline(HistoricalEventCreateDTO historicalEventCreateDTO, Long timelineId);

    /**
     * updates parameters in event
     * @param historicalEventDTO event with new parameters
     * @return id of updated Event
     */
    public Long updateHistoricalEvent(HistoricalEventDTO historicalEventDTO);

    /**
     * return event with given id
     * @param id id of looking event
     * @return found event
     */
    public Optional<HistoricalEventDTO> getHistoricalEventWithId(Long id);

    /**
     * removes event with given id
     * @param id id of event to be removed
     */
    public void removeHistoricalEvent(Long id);

    /**
     * returns all events
     * @return all events
     */
    public List<HistoricalEventDTO> getAllHistoricalEvents();

    // Comment

    /**
     * creates comment in timeline
     * @param timelineCommentCreateDTO comment used for creation
     * @param timelineId id of timeline
     * @return id of created comment
     */
    public Long createTimelineComment(TimelineCommentCreateDTO timelineCommentCreateDTO, Long timelineId);

    /**
     * return comment with given id
     * @param id id of looking comment
     * @return found comment
     */
    public Optional<TimelineCommentDTO> getTimelineCommentWithId(Long id);

    /**
     * updates comment
     * @param timelineCommentDTO updated comment
     * @return id of updated comment
     */
    public Long updateTimelineComment(TimelineCommentDTO timelineCommentDTO);

    /**
     * removes comment with given id
     * @param id of comment to be removed
     */
    public void removeTimelineComment(Long id);

    /**
     * return all comments
     * @return all comments
     */
    public List<TimelineCommentDTO> getAllTimelineComments();


}
