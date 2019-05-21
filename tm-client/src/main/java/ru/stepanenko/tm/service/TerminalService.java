package ru.stepanenko.tm.service;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;

import javax.enterprise.context.ApplicationScoped;
import java.util.Scanner;

@ApplicationScoped
@NoArgsConstructor
public class TerminalService implements ITerminalService {

    @NotNull
    final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
