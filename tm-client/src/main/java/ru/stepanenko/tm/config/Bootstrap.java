package ru.stepanenko.tm.config;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
@NoArgsConstructor
public class Bootstrap {

    @Inject
    @NotNull
    Map<String, AbstractCommand> commandsMap;

    @Inject
    @NotNull
    ITerminalService terminalService;

    public void init() {
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
            AbstractCommand command = commandsMap.get(commandName);
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
