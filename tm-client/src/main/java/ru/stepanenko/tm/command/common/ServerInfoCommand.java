package ru.stepanenko.tm.command.common;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.command.ICommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.DataValidateException_Exception;
import ru.stepanenko.tm.endpoint.ServerEndpoint;

@Component
public class ServerInfoCommand implements ICommand {

    @Autowired
    @NotNull
    ServerEndpoint serverEndpoint;

    @Override
    public String getName() {
        return "server-info";
    }

    @Override
    public String getDescription() {
        return "Show server information.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        System.out.println("HOST: " + serverEndpoint.getInfoHost());
        System.out.println("PORT: " + serverEndpoint.getInfoPort());
    }
}
