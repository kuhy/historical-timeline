package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.TimelineCommentDao;
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

    @Inject
    public TimelineCommentServiceImpl(TimelineCommentDao timelineCommentDao) {
        this.timelineCommentDao = timelineCommentDao;
    }

    @Override
    public void createTimelineComment(TimelineComment comment) {
        timelineCommentDao.create(comment);
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
