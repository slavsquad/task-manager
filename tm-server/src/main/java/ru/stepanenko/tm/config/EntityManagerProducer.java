package ru.stepanenko.tm.config;

import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;
import org.jetbrains.annotations.NotNull;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ApplicationScoped
public class EntityManagerProducer {

    @NotNull
    private EntityManagerFactory entityManagerFactory;

    @Inject
    public EntityManagerProducer(@NotNull EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Produces
    @TransactionScoped
    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
