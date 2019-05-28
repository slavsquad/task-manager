package ru.stepanenko.tm.config;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IPropertyService;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class EntityManagerFactoryProducer {

    @NotNull
    private final IPropertyService propertyService;

    @Inject
    public EntityManagerFactoryProducer(
            @NotNull final IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Produces
    public EntityManagerFactory getFactory() {
        final Map<String, String> settings = new HashMap<>();
        settings.put(Environment.DRIVER, propertyService.getJdbcDriver());
        settings.put(Environment.URL, propertyService.getJdbcURL());
        settings.put(Environment.USER, propertyService.getJdbcUser());
        settings.put(Environment.PASS, propertyService.getJdbcPassword());
        settings.put(Environment.DIALECT, propertyService.getDialect());
        settings.put(Environment.HBM2DDL_AUTO, propertyService.getHBM2DDL_AUTO());
        settings.put(Environment.SHOW_SQL, propertyService.getShowSQL());
        settings.put(Environment.USE_SECOND_LEVEL_CACHE, "true");
        settings.put(Environment.USE_QUERY_CACHE, "true");
        settings.put(Environment.USE_MINIMAL_PUTS, "true");
        settings.put("hibernate.cache.hazelcast.use_lite_member", "true");
        settings.put(Environment.CACHE_REGION_PREFIX, "task-manager");
        settings.put(Environment.CACHE_PROVIDER_CONFIG, "hazelcast.xml");
        settings.put(Environment.CACHE_REGION_FACTORY, "com.hazelcast.hibernate.HazelcastLocalCacheRegionFactory");

        final StandardServiceRegistryBuilder registryBuilder
                = new StandardServiceRegistryBuilder();
        System.out.println(settings);
        registryBuilder.applySettings(settings);
        final StandardServiceRegistry registry = registryBuilder.build();
        final MetadataSources sources = new MetadataSources(registry);
        sources.addAnnotatedClass(Task.class);
        sources.addAnnotatedClass(Project.class);
        sources.addAnnotatedClass(User.class);
        sources.addAnnotatedClass(Session.class);
        final Metadata metadata = sources.getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }
}
