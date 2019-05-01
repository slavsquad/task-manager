package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;


import ru.stepanenko.tm.config.Bootstrap;


public class AppServer {

    public static void main(String[] args) {
        @NotNull final Bootstrap bootstrap = new Bootstrap();
        bootstrap.init();
    }
}
