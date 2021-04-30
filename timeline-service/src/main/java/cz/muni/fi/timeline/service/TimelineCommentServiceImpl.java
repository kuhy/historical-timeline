package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.dao.TimelineCommentDao;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.TimelineComment;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of the TimelineCommentService.
 *
 * @author Ondřej Kuhejda
 */
@Service
public class TimelineCommentServiceImpl implements TimelineCommentService {

    private final TimelineCommentDao timelineCommentDao;

    private final HistoricalTimelineDao timelineDao;

    @Inject
    public TimelineCommentServiceImpl(TimelineCommentDao timelineCommentDao, HistoricalTimelineDao timelineDao) {
        this.timelineCommentDao = timelineCommentDao;
        this.timelineDao = timelineDao;
    }

    @Override
    public void createTimelineCommentInTimeline(TimelineComment comment, HistoricalTimeline timeline) {
        if (comment == null) {
            throw new IllegalArgumentException("Timeline comment is null.");
        }

        if (timeline == null) {
            throw new IllegalArgumentException("Historical timeline is null.");
        }

        try {
            timelineCommentDao.create(comment);

            HistoricalTimeline historicalTimeline = timelineDao.findById(timeline.getId()).orElseThrow(() ->
                new ServiceLayerException("Timeline with the given id does not exist.")
            );

            historicalTimeline.addTimelineComment(comment);

            timelineDao.update(timeline);
        } catch (ServiceLayerException e) {
            throw e;
        }catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeTimelineComment(TimelineComment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Timeline comment is null.");
        }

        try {
            timelineCommentDao.remove(comment);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateTimelineComment(TimelineComment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Timeline comment is null.");
        }

        try {
            timelineCommentDao.update(comment);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<TimelineComment> findTimelineCommentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }

        try {
            return timelineCommentDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<TimelineComment> findAllComments() {
        try {
            return timelineCommentDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
