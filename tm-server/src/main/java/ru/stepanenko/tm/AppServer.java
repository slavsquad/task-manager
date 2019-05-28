package ru.stepanenko.tm;

import ru.stepanenko.tm.config.Bootstrap;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

public class AppServer {
    public static void main(String[] args) {
       /* SeContainerInitializer.newInstance()
                .addPackages(AppServer.class).initialize()
                .select(Bootstrap.class)
                .get()
                .init();*/
        SeContainerInitializer.newInstance().initialize();
        Bootstrap bootstrap = CDI.current().select(Bootstrap.class).get();
        bootstrap.init();
    }
}
