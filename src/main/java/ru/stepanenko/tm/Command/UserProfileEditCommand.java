package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public class UserProfileEditCommand extends AbstractCommand {
    IUserService userService;

    public UserProfileEditCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "user-profile-edit";
    }

    @Override
    public String getDescription() {
        return "Edit user's profile.";
    }

    @Override
    public void execute() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("Before view user profile, make login!");
            return;
        }
        System.out.println(currentUser);//print user profile
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input login: ");
        String login = scanner.nextLine();
        User user = userService.findByLogin(login);
        if (user == null) {
            System.out.println("Please input password:");
            String password = scanner.nextLine();
            if (userService.edit(currentUser.getId(), login, password, currentUser.getRole().toString()) != null) {
                System.out.println("User profile had been changed!");
            } else {
                System.out.println("Incorrect input role!");
            }
        } else {
            System.out.println("Login already exist!");
        }
    }
}
