package ru.stepanenko.tm.util;

import ru.stepanenko.tm.enumerate.Role;

import java.util.HashMap;
import java.util.Map;

public class RoleUtil {
    static public Role stringToRole(String string) {
        Map<String, Role> userRoles = new HashMap<>(2);
        userRoles.put("admin", Role.ADMINISTRATOR);
        userRoles.put("user", Role.USER);
        return userRoles.get(string);
    }
}
