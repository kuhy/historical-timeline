package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
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
        try {
            historicalTimelineDao.create(timeline);

            StudyGroup group = studyGroupDao.findById(studyGroup.getId()).orElseThrow(() ->
                new ServiceLayerException("Study group with the given id does not exist.")
            );

            group.addHistoricalTimeline(timeline);

            studyGroupDao.update(group);
        } catch (ServiceLayerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateTimeline(HistoricalTimeline timeline) {
        try {
            historicalTimelineDao.update(timeline);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeTimeline(HistoricalTimeline timeline) {
        try {
            historicalTimelineDao.remove(timeline);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<HistoricalTimeline> findTimelineById(Long id) {
        try {
            return historicalTimelineDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<HistoricalTimeline> findTimelineByName(String name) {
        try {
            return historicalTimelineDao.findByName(name);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<HistoricalTimeline> findAllTimelines() {
        try {
            return historicalTimelineDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
