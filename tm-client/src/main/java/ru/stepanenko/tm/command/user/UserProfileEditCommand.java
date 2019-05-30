package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.ICommand;
import ru.stepanenko.tm.endpoint.*;
import ru.stepanenko.tm.util.HashUtil;

@Component
@NoArgsConstructor
public final class UserProfileEditCommand implements ICommand {

    @NotNull
    @Autowired
    UserEndpoint userEndpoint;

    @NotNull
    @Autowired
    SessionEndpoint sessionEndpoint;

    @NotNull
    @Autowired
    ISessionService sessionService;

    @NotNull
    @Autowired
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
