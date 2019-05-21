package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;
import ru.stepanenko.tm.util.HashUtil;

import javax.inject.Inject;

@NoArgsConstructor
public final class UserProfileEditCommand implements AbstractCommand {

    @Inject
    @NotNull
    UserEndpoint userEndpoint;

    @Inject
    @NotNull
    SessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    ISessionService sessionService;

    @Inject
    @NotNull
    ITerminalService terminalService;

    @Override
    public String getName() {
        return "user-profile-edit";
    }

    @Override
    public String getDescription() {
        return "Edit user's profile.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        @Nullable final UserDTO currentUser = userEndpoint.getUserBySession(currentSession);
        System.out.println("Welcome to editor user: " + currentUser.getLogin() + "profile: ");
        System.out.println("Please input login: ");
        @Nullable final String login = terminalService.nextLine();
        System.out.println("Please input password:");
        @Nullable final String password = terminalService.nextLine();
        currentUser.setLogin(login);
        currentUser.setPassword(HashUtil.md5(password));
        userEndpoint.editUserProfile(currentSession, currentUser);
        System.out.println("User profile had been changed!");
    }
}
