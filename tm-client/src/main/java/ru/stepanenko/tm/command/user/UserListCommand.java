package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

@Component
@NoArgsConstructor
public class UserListCommand implements ICommand {

    @NotNull
    @Autowired
    UserEndpoint userEndpoint;

    @NotNull
    @Autowired
    SessionEndpoint sessionEndpoint;

    @NotNull
    @Autowired
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
