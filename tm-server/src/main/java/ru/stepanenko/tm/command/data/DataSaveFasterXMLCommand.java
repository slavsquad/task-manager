package ru.stepanenko.tm.command.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.util.Domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataSaveFasterXMLCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "data-save-faster-xml";
    }

    @Override
    public String getDescription() {
        return "Save data to xml file provided by fasterXML.";
    }

    @Override
    public void execute(){
        @NotNull final IUserService userService = serviceLocator.getUserService();
        userService.saveFasterXml(new Session());
    }
}
