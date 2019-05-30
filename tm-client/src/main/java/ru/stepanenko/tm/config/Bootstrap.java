package ru.stepanenko.tm.config;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import java.util.HashMap;
import java.util.Map;

@Component
@NoArgsConstructor
public class Bootstrap {

    @NotNull
    @Autowired
    private ApplicationContext applicationContext;

    @NotNull
    Map<String, ICommand> commandsMap = new HashMap<>();

    @NotNull
    @Autowired
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
        Map<String, ICommand> commands = applicationContext.getBeansOfType(ICommand.class);
        for (final ICommand iCommand : commands.values()) {
            commandsMap.put(iCommand.getName(), iCommand);
        }
    }
}
