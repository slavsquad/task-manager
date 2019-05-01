package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.ForbiddenActionException;
import ru.stepanenko.tm.exception.UserNoLoginException;

@NoArgsConstructor
public final class UserRegisterCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "user-register";
    }

    @Override
    public String getDescription() {
        return "Registration new user.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException {
        @NotNull final IUserService userService = endpointServiceLocator.getUserService();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) throw new UserNoLoginException();
        if(!currentUser.getRole().equals(Role.ADMINISTRATOR)) throw new ForbiddenActionException();
        System.out.println("Please input user name:");
        @NotNull
        String login = terminalService.nextLine();
        if (userService.findByLogin(login) == null) {
            System.out.println("Please input password:");
            @NotNull
            String password = terminalService.nextLine();
            System.out.println("Please input user role(admin or user):");
            @NotNull
            String role = terminalService.nextLine();
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
