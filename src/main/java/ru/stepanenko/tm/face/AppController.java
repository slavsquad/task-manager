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
                id = "projectList",
                pattern = "/project/list",
                viewId = "/WEB-INF/jsf/project/projectList.xhtml"),
        @URLMapping(
                id = "projectEdit",
                pattern = "/project/view",
                viewId = "/WEB-INF/jsf/project/projectEdit.xhtml"),
        @URLMapping(
                id = "main",
                pattern = "/",
                viewId = "/WEB-INF/jsf/index.xhtml"),
        @URLMapping(
                id = "error",
                pattern = "/error",
                viewId = "/WEB-INF/jsf/errorPage.xhtml"
        )})
public class AppController {

}
