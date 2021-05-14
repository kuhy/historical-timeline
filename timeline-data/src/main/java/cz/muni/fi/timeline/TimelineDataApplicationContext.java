package cz.muni.fi.timeline;

import cz.muni.fi.timeline.data.TimelineDataLoader;
import cz.muni.fi.timeline.data.TimelineDataLoaderImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


@Configuration
@Import(TimelineServiceApplicationContext.class)
@ComponentScan(basePackageClasses = TimelineDataLoaderImpl.class)
public class TimelineDataApplicationContext {

    private final TimelineDataLoader timelineDataLoader;

    @Inject
    public TimelineDataApplicationContext(TimelineDataLoader timelineDataLoader) {
        this.timelineDataLoader = timelineDataLoader;
    }

    @PostConstruct
    public void loadData() {
        timelineDataLoader.loadData();
    }
}
