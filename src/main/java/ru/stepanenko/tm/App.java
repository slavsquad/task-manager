package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.config.Bootstrap;

public class App {
    public static void main(String[] args) {
        @NotNull
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.init();
    }
}
