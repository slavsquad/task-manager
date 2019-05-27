package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;

@NoArgsConstructor
public class UserListCommand implements ICommand {

    @Inject
    @NotNull
    UserEndpoint userEndpoint;

    @Inject
    @NotNull
    SessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    ISessionService sessionService;

    @Override
    public String getName() {
        return "user-list";
    }

    @Override
    public String getDescription() {
        return "Print list of users.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateAdminSession(currentSession);
        userEndpoint.findAllUser(currentSession).forEach(e -> System.out.println("id: " + e.getId() +
                " login: " + e.getLogin() +
                " role: " + e.getRole()));
    }
}
