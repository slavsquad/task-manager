package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.common.AboutCommand;
import ru.stepanenko.tm.command.common.ExitCommand;
import ru.stepanenko.tm.command.common.HelpCommand;
import ru.stepanenko.tm.command.project.*;
import ru.stepanenko.tm.command.task.*;
import ru.stepanenko.tm.command.user.*;
import ru.stepanenko.tm.config.Bootstrap;

public class AppClient {
    public static void main(String[] args) {
        @NotNull final Bootstrap bootstrap = new Bootstrap();
        @NotNull final Class[] commands = new Class[]{
                AboutCommand.class, ExitCommand.class, HelpCommand.class, ProjectClearCommand.class, ProjectCreateCommand.class,
                ProjectEditCommand.class, ProjectFindCommand.class, ProjectListCommand.class, ProjectListSortCommand.class,
                ProjectPrintCommand.class, ProjectRemoveCommand.class, TaskClearCommand.class, TaskCreateCommand.class,
                TaskEditCommand.class, TaskFindCommand.class, TaskListCommand.class, TaskListSortCommand.class,
                TaskPrintCommand.class, TaskRemoveCommand.class, UserChangePasswordCommand.class,
                UserListCommand.class, UserLoginCommand.class, UserLogoutCommand.class,
                UserProfileEditCommand.class, UserProfileViewCommand.class,
                UserRegisterCommand.class, UserRemoveCommand.class};
        bootstrap.init(commands);
    }
}
