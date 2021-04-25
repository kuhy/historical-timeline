package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.*;

import java.sql.Time;
import java.util.List;

public interface HistoricalTimelineFacade {
    // HistoricalTimelineService
    // HistoricalEventService
    // TimelineCommentService

    //Timeline

    public Long createHistoricalTimeline(HistoricalTimelineCreateDTO historicalTimelineCreateDTO, Long studyGroupId);

    public HistoricalTimelineDTO getHistoricalTimelineWithId(Long id);

    public void newHistoricalTimelineName(HistoricalTimelineNewNameDTO historicalTimelineNewNameDTO);

    public void removeHistoricalTimeline(Long id);

    public List<HistoricalTimelineDTO> getAllHistoricalTimelines();

    //Event

    public Long createHistoricalEvent(HistoricalEventCreateDTO historicalEventCreateDTO);

    public Long createEventInTimeline(HistoricalEventCreateDTO historicalEventCreateDTO, Long timelineId);

    public void newEventUpdate(HistoricalEventDTO historicalEventDTO);

    public HistoricalEventDTO getHistoricalEventWithId(Long id);

    public void removeHistoricalEvent(Long id);

    public List<HistoricalEventDTO> getAllHistoricalEvents();

    // Comment

    public Long createTimelineComment(TimelineCommentCreateDTO timelineCommentCreateDTO, Long timelineId);

    public TimelineCommentDTO getTimelineCommentWithId(Long id);

    public void newTimelineCommentText(TimelineCommentDTO timelineCommentDTO);

    public void removeTimelineComment(Long id);

    public List<TimelineCommentDTO> getAllTimelineComments();


}
