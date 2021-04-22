package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.dao.TimelineCommentDao;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.TimelineComment;
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
        timelineCommentDao.create(comment);

        HistoricalTimeline historicalTimeline = timelineDao.findById(timeline.getId()).orElseThrow(() ->
            new IllegalArgumentException("Timeline with the given id does not exist.")
        );

        historicalTimeline.addTimelineComment(comment);

        timelineDao.update(timeline);
    }

    @Override
    public void removeTimelineComment(TimelineComment comment) {
        timelineCommentDao.remove(comment);
    }

    @Override
    public void updateTimelineComment(TimelineComment comment) {
        timelineCommentDao.update(comment);
    }

    @Override
    public Optional<TimelineComment> findTimelineCommentById(Long id) {
        return timelineCommentDao.findById(id);
    }

    @Override
    public List<TimelineComment> findAllComments() {
        return timelineCommentDao.findAll();
    }
}
