package ru.stepanenko.tm.command.common;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.command.ICommand;

@Component
@NoArgsConstructor
public final class ExitCommand implements ICommand {

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Quit from application.";
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
