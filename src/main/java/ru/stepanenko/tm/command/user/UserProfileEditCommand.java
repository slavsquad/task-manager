package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.UserNoLoginException;

@NoArgsConstructor
public final class UserProfileEditCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "user-profile-edit";
    }

    @Override
    public String getDescription() {
        return "Edit user's profile.";
    }

    @Override
    public void execute() throws UserNoLoginException {
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) throw new UserNoLoginException();
        System.out.println(currentUser);//print user profile
        System.out.println("Please input login: ");
        @NotNull
        String login = terminalService.nextLine();
        @Nullable
        User user = userService.findByLogin(login);
        if (user == null) {
            System.out.println("Please input password:");
            @NotNull
            String password = terminalService.nextLine();
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
