package ru.stepanenko.tm.config;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.command.AbstractCommand;
import ru.stepanenko.tm.command.common.AboutCommand;
import ru.stepanenko.tm.command.common.ExitCommand;
import ru.stepanenko.tm.command.common.HelpCommand;
import ru.stepanenko.tm.command.project.*;
import ru.stepanenko.tm.command.task.*;
import ru.stepanenko.tm.command.user.*;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class CommandInitializer {

    @Inject
    @NotNull
    AboutCommand aboutCommand;

    @Inject
    @NotNull
    ExitCommand exitCommand;

    @Inject
    @NotNull
    HelpCommand helpCommand;

    @Inject
    @NotNull
    ProjectClearCommand projectClearCommand;

    @Inject
    @NotNull
    ProjectCreateCommand projectCreateCommand;

    @Inject
    @NotNull
    ProjectEditCommand projectEditCommand;

    @Inject
    @NotNull
    ProjectFindCommand projectFindCommand;

    @Inject
    @NotNull
    ProjectListCommand projectListCommand;

    @Inject
    @NotNull
    ProjectListSortCommand projectListSortCommand;
    @Inject
    @NotNull
    ProjectPrintCommand projectPrintCommand;

    @Inject
    @NotNull
    ProjectRemoveCommand projectRemoveCommand;

    @Inject
    @NotNull
    TaskClearCommand taskClearCommand;

    @Inject
    @NotNull
    TaskCreateCommand taskCreateCommand;

    @Inject
    @NotNull
    TaskEditCommand taskEditCommand;

    @Inject
    @NotNull
    TaskFindCommand taskFindCommand;

    @Inject
    @NotNull
    TaskListCommand taskListCommand;

    @Inject
    @NotNull
    TaskListSortCommand taskListSortCommand;

    @Inject
    @NotNull
    TaskPrintCommand taskPrintCommand;

    @Inject
    @NotNull
    TaskRemoveCommand taskRemoveCommand;

    @Inject
    @NotNull
    UserChangePasswordCommand userChangePasswordCommand;

    @Inject
    @NotNull
    UserListCommand userListCommand;

    @Inject
    @NotNull
    UserLoginCommand userLoginCommand;

    @Inject
    @NotNull
    UserLogoutCommand userLogoutCommand;

    @Inject
    @NotNull
    UserProfileEditCommand userProfileEditCommand;

    @Inject
    @NotNull
    UserProfileViewCommand userProfileViewCommand;

    @Inject
    @NotNull
    UserRegisterCommand userRegisterCommand;

    @Inject
    @NotNull
    UserRemoveCommand userRemoveCommand;

    @Produces
    private Map<String, AbstractCommand> init() {
        @NotNull final Map<String, AbstractCommand> mapCommand = new HashMap<>();
        mapCommand.put(aboutCommand.getName(), aboutCommand);
        mapCommand.put(exitCommand.getName(), exitCommand);
        mapCommand.put(helpCommand.getName(), helpCommand);
        mapCommand.put(projectClearCommand.getName(), projectClearCommand);
        mapCommand.put(projectCreateCommand.getName(), projectCreateCommand);
        mapCommand.put(projectEditCommand.getName(), projectEditCommand);
        mapCommand.put(projectFindCommand.getName(), projectFindCommand);
        mapCommand.put(projectListCommand.getName(), projectListCommand);
        mapCommand.put(projectListSortCommand.getName(), projectListSortCommand);
        mapCommand.put(projectPrintCommand.getName(), projectPrintCommand);
        mapCommand.put(projectRemoveCommand.getName(), projectRemoveCommand);
        mapCommand.put(taskClearCommand.getName(), taskClearCommand);
        mapCommand.put(taskCreateCommand.getName(), taskCreateCommand);
        mapCommand.put(taskEditCommand.getName(), taskEditCommand);
        mapCommand.put(taskFindCommand.getName(), taskFindCommand);
        mapCommand.put(taskListCommand.getName(), taskListCommand);
        mapCommand.put(taskListSortCommand.getName(), taskListSortCommand);
        mapCommand.put(taskPrintCommand.getName(), taskPrintCommand);
        mapCommand.put(taskRemoveCommand.getName(), taskRemoveCommand);
        mapCommand.put(userChangePasswordCommand.getName(), userChangePasswordCommand);
        mapCommand.put(userListCommand.getName(), userListCommand);
        mapCommand.put(userLoginCommand.getName(), userLoginCommand);
        mapCommand.put(userLogoutCommand.getName(), userLogoutCommand);
        mapCommand.put(userProfileEditCommand.getName(), userProfileEditCommand);
        mapCommand.put(userProfileViewCommand.getName(), userProfileViewCommand);
        mapCommand.put(userRegisterCommand.getName(), userRegisterCommand);
        mapCommand.put(userRemoveCommand.getName(), userRemoveCommand);
        return mapCommand;
    }
}
