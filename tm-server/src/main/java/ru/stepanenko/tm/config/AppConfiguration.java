package ru.stepanenko.tm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.stepanenko.tm.api.service.IPropertyService;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("ru.stepanenko.tm")
@EnableJpaRepositories("ru.stepanenko.tm.api.repository")
@EnableTransactionManagement
public class AppConfiguration {

    @Autowired
    private IPropertyService propertyService;
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource =
                new DriverManagerDataSource();
        dataSource.setDriverClassName(propertyService.getJdbcDriver());
        dataSource.setUrl(propertyService.getJdbcURL());
        dataSource.setUsername(propertyService.getJdbcUser());
        dataSource.setPassword(propertyService.getJdbcPassword());
        return dataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            final DataSource dataSource
    ) {
        final LocalContainerEntityManagerFactoryBean factoryBean;
        factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("ru.stepanenko.tm.model");
        final Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.MySQL5Dialect");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }
    @Bean
    public PlatformTransactionManager transactionManager(
            final LocalContainerEntityManagerFactoryBean emf
    ) {
        final JpaTransactionManager transactionManager =
                new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf.getObject());
        return transactionManager;
    }
}
