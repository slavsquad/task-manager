package ru.stepanenko.tm.api.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface ITerminalService {
    String nextLine();
}
