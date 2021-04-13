package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.HistoricalTimeline;
import cz.muni.fi.timeline.entity.StudyGroup;

public interface HistoricalTimelineService {
    void createTimelineInGroup(HistoricalTimeline timeline, StudyGroup group);
    void updateTimeline(HistoricalTimeline timeline);
    void removeTimeline(HistoricalTimeline timeline);
    // void getTimelineById (with events)
}
