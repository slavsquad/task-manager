package ru.stepanenko.tm.config;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@NoArgsConstructor
public class Bootstrap {

    @NotNull
    Map<String, ICommand> commandsMap = new HashMap<>();

    @Inject
    @NotNull
    ITerminalService terminalService;

    public void init() {
        initCommands();
        menu();
    }

    private void menu() {
        System.out.println("==Welcome to Task manager!==\n" +
                "Input help for more information");

        while (true) {
            System.out.println("Please input your command:");
            @NotNull
            String commandName = terminalService.nextLine();
            @Nullable
            ICommand command = commandsMap.get(commandName);
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

    private void initCommands() {
        final Instance<ICommand> commands = CDI.current().select(ICommand.class);
        for (final ICommand iCommand : commands) {
            commandsMap.put(iCommand.getName(), iCommand);
        }
    }
}
