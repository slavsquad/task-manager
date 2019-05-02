package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

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
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final UserEndpoint userEndpoint = endpointServiceLocator.getUserEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Welcome to editor user: " + userEndpoint.getUserBySession(currentSession) + "profile: ");
        System.out.println("Please input login: ");
        @NotNull
        String login = terminalService.nextLine();
        @Nullable
        User user = userEndpoint.findUserByLogin(currentSession, login);
        if (user == null) {
            System.out.println("Please input password:");
            @NotNull
            String password = terminalService.nextLine();
            if (userEndpoint.editUserProfile(currentSession, login, password) != null) {
                System.out.println("User profile had been changed!");
            } else {
                System.out.println("Incorrect input login or password!");
            }
        } else {
            System.out.println("Login already exist!");
        }
    }
}
