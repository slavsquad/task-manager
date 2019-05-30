package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.stepanenko.tm.config.AppConfiguration;
import ru.stepanenko.tm.config.Bootstrap;

public class AppClient {
    public static void main(String[] args) {
        @NotNull final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        @NotNull final Bootstrap bootstrap = context.getBean(Bootstrap.class);
        bootstrap.init();
    }
}
