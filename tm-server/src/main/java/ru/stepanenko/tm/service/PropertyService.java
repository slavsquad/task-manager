package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.AppServer;
import ru.stepanenko.tm.api.service.IPropertyService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class PropertyService implements IPropertyService {

    @NotNull
    private final Properties properties = new Properties();

    public PropertyService() {
        init();
    }

    public void init() {
        try (InputStream resourceStream = AppServer.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        @Nullable final String port = System.getProperty("server.port");
        if (port != null && !port.isEmpty()) {
            properties.setProperty("server.port", port);
        }
        @Nullable final String host = System.getProperty("server.host");
        if (host != null && !host.isEmpty()) {
            properties.setProperty("server.host", host);
        }
    }

    @Override
    public String getJdbcPassword() {
        return properties.getProperty("password");
    }

    @Override
    public String getJdbcUser() {
        return properties.getProperty("user");
    }

    @Override
    public String getJdbcURL() {
        return properties.getProperty("url");
    }

    @Override
    public String getJdbcDriver() {
        return properties.getProperty("driver");
    }

    @Override
    public String getDialect() {
        return properties.getProperty("dialect");
    }

    @Override
    public String getShowSQL() {
        return properties.getProperty("showSQL");
    }

    @Override
    public String getHBM2DDL_AUTO() {
        return properties.getProperty("HBM2DDL_AUTO");
    }

    @Override
    public String getServerPort() {
        return properties.getProperty("server.port");
    }

    @Override
    public String getServerHost() {
        return properties.getProperty("server.host");
    }

    @Override
    public String getSalt() {
        return properties.getProperty("salt");
    }

    @Override
    public String getCycle() {
        return properties.getProperty("cycle");
    }
}
