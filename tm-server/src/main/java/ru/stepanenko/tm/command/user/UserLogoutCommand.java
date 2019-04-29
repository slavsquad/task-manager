package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.UserNoLoginException;

@NoArgsConstructor
public final class UserLogoutCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "user-logout";
    }

    @Override
    public String getDescription() {
        return "Logout user";
    }

    @Override
    public void execute() throws UserNoLoginException {
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) throw new UserNoLoginException();
        userService.setCurrentUser(null);
        System.out.println("User: '" + currentUser.getLogin() + "' logout!");
    }
}
