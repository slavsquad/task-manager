package ru.stepanenko.tm;

import ru.stepanenko.tm.config.Bootstrap;
import javax.enterprise.inject.se.SeContainerInitializer;

public class  AppServer {
    public static void main(String[] args) {
        SeContainerInitializer.newInstance()
                .addPackages(AppServer.class).initialize()
                .select(Bootstrap.class)
                .get()
                .init();
    }
}
