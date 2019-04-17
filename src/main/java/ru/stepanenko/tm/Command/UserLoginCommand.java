package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IUserService;

import java.util.Scanner;

public class UserLoginCommand extends AbstractCommand {
    private IUserService userService;

    public UserLoginCommand(IUserService userService) {
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input user name:");
        String login = scanner.nextLine();
        System.out.println("Please input password:");
        String password = scanner.nextLine();

        if (userService.authenticationUser(login, password)) {
            System.out.println("Welcome to task manager " + login);
        } else {
            System.out.println("User name or password does not correct!");
        }
    }


}
