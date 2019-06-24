package ru.stepanenko.tm.face;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@URLMappings(mappings = {
        @URLMapping(
                id = "main",
                pattern = "/",
                viewId = "/WEB-INF/jsf/index.xhtml"),
        @URLMapping(
                id = "error",
                pattern = "/error",
                viewId = "/WEB-INF/jsf/errorPage.xhtml"),
        @URLMapping(
                id = "projectList",
                pattern = "/project/list",
                viewId = "/WEB-INF/jsf/project/projectList.xhtml"),
        @URLMapping(
                id = "projectEdit",
                pattern = "/project/edit",
                viewId = "/WEB-INF/jsf/project/projectEdit.xhtml"),
        @URLMapping(
                id = "taskList",
                pattern = "/task/list",
                viewId = "/WEB-INF/jsf/task/taskList.xhtml"),
        @URLMapping(
                id = "taskEdit",
                pattern = "/task/edit",
                viewId = "/WEB-INF/jsf/task/taskEdit.xhtml"),
        @URLMapping(
                id = "userList",
                pattern = "/user/list",
                viewId = "/WEB-INF/jsf/user/userList.xhtml"),
        @URLMapping(
                id = "userEdit",
                pattern = "/user/edit",
                viewId = "/WEB-INF/jsf/user/userEdit.xhtml")})
public class AppViewController {

}


