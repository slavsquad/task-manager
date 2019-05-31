package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.enumerate.Status;

import java.util.HashMap;
import java.util.Map;

public class EnumUtil {
    static public Role stringToRole(@Nullable final String string) {
        @NotNull
        Map<String, Role> userRoles = new HashMap<>(2);
        userRoles.put("admin", Role.ADMINISTRATOR);
        userRoles.put("user", Role.USER);
        return userRoles.get(string);
    }

    static public Status stringToStatus(@Nullable final String string) {
        @NotNull
        Map<String, Status> statusMap = new HashMap<>(3);
        statusMap.put("in process", Status.INPROCESS);
        statusMap.put("planned", Status.PLANNED);
        statusMap.put("done", Status.DONE);
        return statusMap.get(string);
    }
}
