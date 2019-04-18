package ru.stepanenko.tm.command.user;

import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

public final class UserProfileViewCommand extends AbstractCommand {
    private final IUserService userService;

    public UserProfileViewCommand(final IUserService userService) {
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
