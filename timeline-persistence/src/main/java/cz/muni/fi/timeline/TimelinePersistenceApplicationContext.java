package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories
@Import(TimelineDatabaseConfiguration.class)
@ComponentScan(basePackageClasses = {HistoricalTimelineDao.class})
public class TimelinePersistenceApplicationContext {

}
