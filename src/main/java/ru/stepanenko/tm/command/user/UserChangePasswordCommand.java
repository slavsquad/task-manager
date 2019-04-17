package ru.stepanenko.tm.command.user;

import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.enumerate.Role;

import java.util.Scanner;

public class UserChangePasswordCommand extends AbstractCommand {
    private IUserService userService;

    public UserChangePasswordCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "user-change-pass";
    }

    @Override
    public String getDescription() {
        return "Change user password.";
    }

    @Override
    public void execute() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || Role.USER.equals(currentUser.getRole())) {
            System.out.println("This command available only admin, please login!");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input user name:");
        String login = scanner.nextLine();
        User user = userService.findByLogin(login);
        if (user != null) {
            System.out.println("Please input password:");
            String password = scanner.nextLine();
            if (userService.edit(user.getId(), user.getLogin(), password, user.getRole().toString()) != null) {
                System.out.println("User " + user.getLogin() + " password changed!");
            } else {
                System.out.println("Incorrect input role!");
            }
        } else {
            System.out.println("User name already exist!");
        }
    }
}
