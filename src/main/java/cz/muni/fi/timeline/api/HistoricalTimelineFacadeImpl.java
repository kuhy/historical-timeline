/**package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.*;
import cz.muni.fi.timeline.entity.HistoricalEvent;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.TimelineComment;
import cz.muni.fi.timeline.mapper.BeanMappingService;
import cz.muni.fi.timeline.service.HistoricalEventService;
import cz.muni.fi.timeline.service.HistoricalTimelineService;
import cz.muni.fi.timeline.service.StudyGroupService;
import cz.muni.fi.timeline.service.TimelineCommentService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of historical timeline facade
 * @author Matej Macák
 */
/*@Service
@Transactional
public class HistoricalTimelineFacadeImpl implements HistoricalTimelineFacade {

    final private HistoricalTimelineService timelineService;
    final private HistoricalEventService eventService;
    final private TimelineCommentService commentService;
    final private StudyGroupService studyGroupService;
    final private BeanMappingService beanMappingService;

    @Inject
    public HistoricalTimelineFacadeImpl(HistoricalTimelineService timelineService, HistoricalEventService eventService, TimelineCommentService commentService, StudyGroupService studyGroupService, BeanMappingService beanMappingService) {
        this.timelineService = timelineService;
        this.eventService = eventService;
        this.commentService = commentService;
        this.studyGroupService = studyGroupService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long createHistoricalTimeline(HistoricalTimelineCreateDTO historicalTimelineCreateDTO, Long studyGroupId) {
        HistoricalTimeline mappedTimeline = beanMappingService.mapTo(historicalTimelineCreateDTO, HistoricalTimeline.class);
        StudyGroup group = studyGroupService.findById(studyGroupId).orElseThrow(() ->
                new IllegalArgumentException("Group with given id does not exist.")
        );

        timelineService.createTimelineInStudyGroup(mappedTimeline, group);
        return mappedTimeline.getId();
    }

    @Override
    public Optional<HistoricalTimelineDTO> getHistoricalTimelineWithId(Long id) {
        Optional<HistoricalTimeline> find = timelineService.findTimelineById(id);
        if (find.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(beanMappingService.mapTo(find.get(), HistoricalTimelineDTO.class));
    }

    @Override
    public Long updateHistoricalTimeline(HistoricalTimelineDTO historicalTimelineDTO) {
        HistoricalTimeline mappedTimeline = beanMappingService.mapTo(historicalTimelineDTO, HistoricalTimeline.class);
        timelineService.updateTimeline(mappedTimeline);
        return mappedTimeline.getId();
    }

    @Override
    public void removeHistoricalTimeline(Long id) {
        HistoricalTimeline historicalTimeline = timelineService.findTimelineById(id).orElseThrow(() ->
                new IllegalArgumentException("Historical timeline with given id does not exist.")
        );

        timelineService.removeTimeline(historicalTimeline);
    }

    @Override
    public List<HistoricalTimelineDTO> getAllHistoricalTimelines() {
        return beanMappingService.mapTo(timelineService.findAllTimelines(), HistoricalTimelineDTO.class);
    }

    /*Event */

 /*   @Override
    public Long createHistoricalEvent(HistoricalEventCreateDTO historicalEventCreateDTO) {
        HistoricalEvent mappedEvent = beanMappingService.mapTo(historicalEventCreateDTO, HistoricalEvent.class);
        eventService.createEvent(mappedEvent);
        return mappedEvent.getId();
    }

    @Override
    public Long createEventInTimeline(HistoricalEventCreateDTO historicalEventCreateDTO, Long timelineId) {
        HistoricalEvent mappedEvent = beanMappingService.mapTo(historicalEventCreateDTO, HistoricalEvent.class);
        HistoricalTimeline timeline = timelineService.findTimelineById(timelineId).orElseThrow(() ->
                new IllegalArgumentException("Timeline with given id does not exist.")
        );

        eventService.createEventInTimeline(mappedEvent, timeline);
        return mappedEvent.getId();
    }

    @Override
    public Long updateHistoricalEvent(HistoricalEventDTO historicalEventDTO) {
        HistoricalEvent mappedEvent = beanMappingService.mapTo(historicalEventDTO, HistoricalEvent.class);
        eventService.updateEvent(mappedEvent);
        return mappedEvent.getId();
    }

    @Override
    public Optional<HistoricalEventDTO> getHistoricalEventWithId(Long id) {
        Optional<HistoricalEvent> find = eventService.findById(id);
        if (find.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(beanMappingService.mapTo(find.get(), HistoricalEventDTO.class));
    }

    @Override
    public void removeHistoricalEvent(Long id) {
        HistoricalEvent historicalEvent = eventService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Historical event with given id does not exist.")
        );

        eventService.removeEvent(historicalEvent);
    }

    @Override
    public List<HistoricalEventDTO> getAllHistoricalEvents() {
        return beanMappingService.mapTo(eventService.getAllEvents(), HistoricalEventDTO.class);
    }

    //Comment

    @Override
    public Long createTimelineComment(TimelineCommentCreateDTO timelineCommentCreateDTO, Long timelineId) {
        TimelineComment mappedComment = beanMappingService.mapTo(timelineCommentCreateDTO, TimelineComment.class);
        HistoricalTimeline timeline = timelineService.findTimelineById(timelineId).orElseThrow(() ->
                new IllegalArgumentException("Timeline with given id does not exist.")
        );

        commentService.createTimelineCommentInTimeline(mappedComment, timeline);
        return mappedComment.getId();
    }

    @Override
    public Optional<TimelineCommentDTO> getTimelineCommentWithId(Long id) {
        Optional<TimelineComment> find = commentService.findTimelineCommentById(id);
        if (find.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(beanMappingService.mapTo(find.get(), TimelineCommentDTO.class));
    }

    @Override
    public Long updateTimelineComment(TimelineCommentDTO timelineCommentDTO) {
        TimelineComment mappedComment = beanMappingService.mapTo(timelineCommentDTO, TimelineComment.class);
        commentService.updateTimelineComment(mappedComment);
        return mappedComment.getId();
    }

    @Override
    public void removeTimelineComment(Long id) {
        TimelineComment timelineComment = commentService.findTimelineCommentById(id).orElseThrow(() ->
                new IllegalArgumentException("Timeline comment with given id does not exist.")
        );

        commentService.removeTimelineComment(timelineComment);
    }

    @Override
    public List<TimelineCommentDTO> getAllTimelineComments() {
        return beanMappingService.mapTo(commentService.findAllComments(), TimelineCommentDTO.class);
    }
}
*/