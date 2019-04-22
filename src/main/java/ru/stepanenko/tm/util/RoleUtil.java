package ru.stepanenko.tm.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Role;

import java.util.HashMap;
import java.util.Map;

public class RoleUtil {
    static public Role stringToRole(@Nullable final String string) {
        @NotNull
        Map<String, Role> userRoles = new HashMap<>(2);
        userRoles.put("admin", Role.ADMINISTRATOR);
        userRoles.put("user", Role.USER);
        return userRoles.get(string);
    }
}
