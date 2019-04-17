package ru.stepanenko.tm.Command;

import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.User;

public class UserProfileViewCommand extends AbstractCommand {
    IUserService userService;

    public UserProfileViewCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "user-profile-view";
    }

    @Override
    public String getDescription() {
        return "View user profile.";
    }

    @Override
    public void execute() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("Before view user profile, make login!");
            return;
        }
        System.out.println(currentUser);
    }
}
