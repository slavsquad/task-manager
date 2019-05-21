package ru.stepanenko.tm;

import ru.stepanenko.tm.config.Bootstrap;

import javax.enterprise.inject.se.SeContainerInitializer;

public class AppClient {
    public static void main(String[] args) {
        SeContainerInitializer.newInstance()
                .addPackages(AppClient.class)
                .initialize()
                .select(Bootstrap.class)
                .get()
                .init();
    }
}
