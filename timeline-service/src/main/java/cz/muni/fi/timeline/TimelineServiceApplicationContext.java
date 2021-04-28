package cz.muni.fi.timeline;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import cz.muni.fi.timeline.facade.HistoricalTimelineFacadeImpl;
import cz.muni.fi.timeline.service.HistoricalTimelineServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@ComponentScan(basePackageClasses = {
    TimelinePersistenceApplicationContext.class,
    HistoricalTimelineServiceImpl.class,
    HistoricalTimelineFacadeImpl.class
})
public class TimelineServiceApplicationContext {

    @Bean
    public Mapper dozer(){
        return DozerBeanMapperBuilder.buildDefault();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
