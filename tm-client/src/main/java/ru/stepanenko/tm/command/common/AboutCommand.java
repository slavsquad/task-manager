package ru.stepanenko.tm.command.common;

import com.jcabi.manifests.Manifests;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;

public class AboutCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getDescription() {
        return "Information about application.";
    }

    @Override
    public void execute() {
        @NotNull final String version = Manifests.read("Implementation-Version");
        @NotNull final String developer = Manifests.read("Created-By");
        System.out.println("Version: " + version);
        System.out.println("Created by: " + developer);
    }
}
