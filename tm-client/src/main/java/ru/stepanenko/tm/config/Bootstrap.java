package ru.stepanenko.tm.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IEndpointServiceLocator;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;
import ru.stepanenko.tm.service.EndpointServiceLocator;

import java.util.HashMap;
import java.util.Map;

public class Bootstrap {
    @NotNull
    final IEndpointServiceLocator endpointServiceLocator = new EndpointServiceLocator();

    public void init(Class[] arrayCommands) {
        menu(initializeCommands(endpointServiceLocator, arrayCommands));
    }

    private Map<String, AbstractCommand> initializeCommands(@NotNull final IEndpointServiceLocator endpointServiceLocator, @NotNull final Class[] arrayCommands) {
        @NotNull final Map<String, AbstractCommand> mapCommand = new HashMap<>();

        for (Class command : arrayCommands) {
            if (command.getSuperclass().equals(AbstractCommand.class)) {
                try {
                    AbstractCommand abstractCommand = (AbstractCommand) command.newInstance();
                    abstractCommand.setEndpointServiceLocator(endpointServiceLocator);
                    registryCommand(abstractCommand, mapCommand);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return mapCommand;
    }

    private void registryCommand(
            @NotNull final AbstractCommand abstractCommand,
            @NotNull final Map<String, AbstractCommand> command) {
        command.put(abstractCommand.getName(), abstractCommand);
    }

    private void menu(
            @NotNull final Map<String, AbstractCommand> commands) {
        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");

        while (true) {
            System.out.println("Please input your command:");
            @NotNull
            String commandName = endpointServiceLocator.getTerminalService().nextLine();
            @Nullable
            AbstractCommand command = commands.get(commandName);
            if (command != null) {
                try {
                    command.execute();
                } catch (AuthenticationSecurityException_Exception | DataValidateException_Exception e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("Incorrect input, please try again!");
            }
        }
    }
}
