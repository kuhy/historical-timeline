package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
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

    @Inject
    public HistoricalTimelineServiceImpl(HistoricalTimelineDao historicalTimelineDao) {
        this.historicalTimelineDao = historicalTimelineDao;
    }

    @Override
    public void createTimeline(HistoricalTimeline timeline) {
        historicalTimelineDao.create(timeline);
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
