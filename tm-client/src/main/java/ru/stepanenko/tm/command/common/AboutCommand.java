package ru.stepanenko.tm.command.common;

import com.jcabi.manifests.Manifests;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.command.AbstractCommand;

@NoArgsConstructor
public class AboutCommand implements AbstractCommand {

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
