package ru.stepanenko.tm.service;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.api.service.ITerminalService;

import java.util.Scanner;

@Service
@NoArgsConstructor
public class TerminalService implements ITerminalService {

    @NotNull
    final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
