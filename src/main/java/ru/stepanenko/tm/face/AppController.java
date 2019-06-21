package ru.stepanenko.tm.face;

import com.ocpsoft.pretty.faces.annotation.URLMapping;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
@URLMapping(
        id = "main",
        pattern = "/",
        viewId = "/WEB-INF/jsf/index.xhtml"
)
public class AppController {

}
