package ru.stepanenko.tm.command.common;

import ru.stepanenko.tm.command.AbstractCommand;
import com.jcabi.manifests.Manifests;

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
        String version = Manifests.read("Implementation-Version");
        String developer = Manifests.read("Created-By");
        System.out.println("Version: " + version);
        System.out.println("Created by: " + developer);
    }
}
