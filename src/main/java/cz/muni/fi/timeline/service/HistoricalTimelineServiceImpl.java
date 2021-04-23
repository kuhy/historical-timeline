package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.StudyGroup;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of the HistoricalTimelineService.
 *
 * @author Ondřej Kuhejda
 */
@Service
public class HistoricalTimelineServiceImpl implements HistoricalTimelineService {

    private final HistoricalTimelineDao historicalTimelineDao;

    private final StudyGroupDao studyGroupDao;

    @Inject
    public HistoricalTimelineServiceImpl(HistoricalTimelineDao historicalTimelineDao, StudyGroupDao studyGroupDao) {
        this.historicalTimelineDao = historicalTimelineDao;
        this.studyGroupDao = studyGroupDao;
    }

    @Override
    public void createTimelineInStudyGroup(HistoricalTimeline timeline, StudyGroup studyGroup) {
        historicalTimelineDao.create(timeline);

        StudyGroup group = studyGroupDao.findById(studyGroup.getId()).orElseThrow(() ->
            new IllegalArgumentException("Study group with the given id does not exist.")
        );

        group.addHistoricalTimeline(timeline);

        studyGroupDao.update(group);
    }

    @Override
    public void updateTimeline(HistoricalTimeline timeline) {
        historicalTimelineDao.update(timeline);
    }

    @Override
    public void removeTimeline(HistoricalTimeline timeline) {
        historicalTimelineDao.remove(timeline);
    }

    @Override
    public Optional<HistoricalTimeline> findTimelineById(Long id) {
        return historicalTimelineDao.findById(id);
    }

    @Override
    public List<HistoricalTimeline> findTimelineByName(String name) {
        return historicalTimelineDao.findByName(name);
    }

    @Override
    public List<HistoricalTimeline> findAllTimelines() {
        return historicalTimelineDao.findAll();
    }
}
