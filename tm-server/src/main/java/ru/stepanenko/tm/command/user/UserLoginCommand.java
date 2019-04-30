package ru.stepanenko.tm.command.user;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.LoginEmptyException;
import ru.stepanenko.tm.exception.PasswordEmptyException;
import ru.stepanenko.tm.exception.WrongLoginOrPasswordException;
import ru.stepanenko.tm.util.StringValidator;

@NoArgsConstructor
public final class UserLoginCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "user-login";
    }

    @Override
    public String getDescription() {
        return "User login";
    }

    @Override
    public void execute() throws AuthenticationSecurityException {
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ISessionService sessionService = serviceLocator.getSessionService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        System.out.println("Please input user name:");
        @NotNull
        String login = terminalService.nextLine();
        if (!StringValidator.validate(login)) throw new LoginEmptyException();
        System.out.println("Please input password:");
        @NotNull
        String password = terminalService.nextLine();
        if (!StringValidator.validate(password)) throw new PasswordEmptyException();
        User user = userService.authenticationUser(login, password);
        if (user == null) throw new WrongLoginOrPasswordException();
        serviceLocator.setSession(sessionService.create(user.getId()));
        System.out.println("User '" + login + "' login in application!");
    }
}
