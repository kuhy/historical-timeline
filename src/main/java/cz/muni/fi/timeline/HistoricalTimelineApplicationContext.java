package cz.muni.fi.timeline;

import cz.muni.fi.timeline.api.BeanMappingService;
import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.HistoricalTimelineService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;


/**
 * Simple persistence application context based on the seminar-05.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScan(basePackageClasses = {HistoricalTimelineDao.class, HistoricalTimelineService.class, BeanMappingService.class})
public class HistoricalTimelineApplicationContext {

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean();
        jpaFactoryBean.setDataSource(db());
        jpaFactoryBean.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
        jpaFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return jpaFactoryBean;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public LoadTimeWeaver instrumentationLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public DataSource db() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.DERBY).build();
    }

    @Bean
    public Mapper dozer(){
        return new DozerBeanMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
