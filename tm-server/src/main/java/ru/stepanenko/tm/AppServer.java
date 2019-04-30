package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;


import ru.stepanenko.tm.command.common.*;
import ru.stepanenko.tm.command.data.*;
import ru.stepanenko.tm.command.project.*;
import ru.stepanenko.tm.command.task.*;
import ru.stepanenko.tm.command.user.*;
import ru.stepanenko.tm.config.Bootstrap;


public class AppServer {
    private static final Class[] arrayCommandClasses = new Class[]{ProjectClearCommand.class, ProjectCreateCommand.class,
            ProjectListCommand.class, ProjectRemoveCommand.class, ProjectEditCommand.class, ProjectListSortCommand.class,
            ProjectFindCommand.class, TaskClearCommand.class, TaskCreateCommand.class,
            TaskListCommand.class, TaskRemoveCommand.class, TaskEditCommand.class, TaskListSortCommand.class,
            TaskFindCommand.class, UserLoginCommand.class, UserLogoutCommand.class, UserRegisterCommand.class,
            UserProfileViewCommand.class, UserChangePasswordCommand.class, UserProfileEditCommand.class,
            HelpCommand.class, ExitCommand.class, AboutCommand.class,
            DataSaveSerializationCommand.class, DataLoadSerializationCommand.class, DataSaveJaxBXMLCommand.class, DataLoadJaxBXMLCommand.class,
            DataSaveJaxBJSONCommand.class, DataLoadJaxBJSONCommand.class, DataSaveFasterXMLCommand.class,
            DataLoadFasterXMLCommand.class , DataSaveFasterJSONCommand.class, DataLoadFasterJSONCommand.class};

    public static void main(String[] args) {
        @NotNull final Bootstrap bootstrap = new Bootstrap();
        bootstrap.init(arrayCommandClasses);
    }
}
