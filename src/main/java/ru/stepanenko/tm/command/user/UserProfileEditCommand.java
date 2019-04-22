package ru.stepanenko.tm.command.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

import java.util.Scanner;

public final class UserProfileEditCommand extends AbstractCommand {
    @NotNull
    private final IUserService userService;

    public UserProfileEditCommand(@NotNull final IUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "user-profile-edit";
    }

    @Override
    public String getDescription() {
        return "Edit user's profile.";
    }

    @Override
    public void execute() {
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("Before view user profile, make login!");
            return;
        }
        System.out.println(currentUser);//print user profile
        @NotNull
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input login: ");
        @NotNull
        String login = scanner.nextLine();
        @Nullable
        User user = userService.findByLogin(login);
        if (user == null) {
            System.out.println("Please input password:");
            @NotNull
            String password = scanner.nextLine();
            if (userService.edit(currentUser.getId(), login, password, currentUser.getRole().toString()) != null) {
                System.out.println("User profile had been changed!");
            } else {
                System.out.println("Incorrect input role!");
            }
        } else {
            System.out.println("Login already exist!");
        }
    }
}
