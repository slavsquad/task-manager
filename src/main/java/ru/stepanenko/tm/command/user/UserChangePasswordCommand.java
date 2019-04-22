package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.enumerate.Role;

@NoArgsConstructor
public final class UserChangePasswordCommand extends AbstractCommand {
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
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || Role.USER.equals(currentUser.getRole())) {
            System.out.println("This command available only admin, please login!");
            return;
        }

        System.out.println("Please input user name:");
        @NotNull
        String login = terminalService.nextLine();
        @Nullable
        User user = userService.findByLogin(login);
        if (user != null) {
            System.out.println("Please input password:");
            @NotNull
            String password = terminalService.nextLine();
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
