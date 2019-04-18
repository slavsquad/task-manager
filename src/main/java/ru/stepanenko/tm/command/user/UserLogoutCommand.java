package ru.stepanenko.tm.command.user;

import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

public final class UserLogoutCommand extends AbstractCommand {
    private final IUserService userService;

    public UserLogoutCommand(final IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "user-logout";
    }

    @Override
    public String getDescription() {
        return "Logout user";
    }

    @Override
    public void execute() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("Before user logout, make login!");
            return;
        }
        userService.setCurrentUser(null);
        System.out.println("User: '" + currentUser.getLogin() + "' logout!");
    }
}
