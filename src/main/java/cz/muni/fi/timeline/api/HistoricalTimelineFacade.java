package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.*;

import java.sql.Time;
import java.util.List;

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
     * @return found timeline or null
     */
    public HistoricalTimelineDTO getHistoricalTimelineWithId(Long id);

    /**
     * renames timeline
     * @param historicalTimelineNewNameDTO new timeline name
     */
    public void newHistoricalTimelineName(HistoricalTimelineNewNameDTO historicalTimelineNewNameDTO);

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
     */
    public void newEventUpdate(HistoricalEventDTO historicalEventDTO);

    /**
     * return event with given id
     * @param id id of looking event
     * @return found event or null
     */
    public HistoricalEventDTO getHistoricalEventWithId(Long id);

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
     * @return found comment or null
     */
    public TimelineCommentDTO getTimelineCommentWithId(Long id);

    /**
     * updates text in comment
     * @param timelineCommentDTO comment with updated text
     */
    public void newTimelineCommentText(TimelineCommentDTO timelineCommentDTO);

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
