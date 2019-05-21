package ru.stepanenko.tm.config;

import lombok.NoArgsConstructor;
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
        settings.put(Environment.DIALECT,
                "org.hibernate.dialect.MySQL5InnoDBDialect");
        settings.put(Environment.HBM2DDL_AUTO, "update");
        settings.put(Environment.SHOW_SQL, "true");
        final StandardServiceRegistryBuilder registryBuilder
                = new StandardServiceRegistryBuilder();
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
