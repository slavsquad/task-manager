package ru.stepanenko.tm.command.user;

import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.enumerate.Role;

import java.util.Scanner;

public class UserRegisterCommand extends AbstractCommand {
    private IUserService userService;

    public UserRegisterCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "user-register";
    }

    @Override
    public String getDescription() {
        return "Registration new user.";
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
        if (userService.findByLogin(login) == null) {
            System.out.println("Please input password:");
            String password = scanner.nextLine();
            System.out.println("Please input user role(admin or user):");
            String role = scanner.nextLine();
            if (userService.create(login, password, role) != null) {
                System.out.println("User " + login + " created!");
            } else {
                System.out.println("User " + login + " is not created!");
            }
        } else {
            System.out.println("User name already exist!");
        }
    }
}
