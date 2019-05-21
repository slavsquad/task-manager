package ru.stepanenko.tm.api.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface IPropertyService {

    String getJdbcPassword();

    String getJdbcUser();

    String getJdbcURL();

    String getJdbcDriver();

    String getSalt();

    String getCycle();
}
