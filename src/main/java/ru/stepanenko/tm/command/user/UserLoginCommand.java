package ru.stepanenko.tm.command.user;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;

import java.util.Scanner;

public final class UserLoginCommand extends AbstractCommand {
    @NotNull
    private final IUserService userService;

    public UserLoginCommand(@NotNull IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "user-login";
    }

    @Override
    public String getDescription() {
        return "User login";
    }

    @Override
    public void execute() {
        @NotNull
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input user name:");
        @NotNull
        String login = scanner.nextLine();
        System.out.println("Please input password:");
        @NotNull
        String password = scanner.nextLine();

        if (userService.authenticationUser(login, password)) {
            System.out.println("Welcome to task manager " + login);
        } else {
            System.out.println("User name or password does not correct!");
        }
    }


}
