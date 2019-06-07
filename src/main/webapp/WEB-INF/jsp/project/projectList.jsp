<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 03.06.2019
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.stepanenko.tm.model.entity.Project" %>
<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="ru.stepanenko.tm.util.DateFormatter" %>
<%@ page import="org.jetbrains.annotations.NotNull" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="header">
        <h3 class="text-muted"><br/></h3>
        <h4 class="text-muted">PROJECT MANAGEMENT:</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Data begin</th>
                        <th>Data End</th>
                        <th>Status</th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% @NotNull int i = 0;
                        for (Project project : (Collection<Project>) request.getAttribute(FieldConst.PROJECTS)) {
                            i++;
                    %>
                    <tr>
                        <td><%=i%>
                        </td>
                        <td><%=project.getName()%>
                        </td>
                        <td><%=project.getDescription()%>
                        </td>
                        <td><%=DateFormatter.dateToString(project.getDateBegin())%>
                        </td>
                        <td><%=DateFormatter.dateToString(project.getDateEnd())%>
                        </td>
                        <td><%=project.getStatus()%>
                        </td>
                        <td>
                            <button class="btn btn-primary btn-xs"
                                    onclick="postToUrl(
                                            '${pageContext.request.contextPath}/task/list',
                                            {'<%=FieldConst.ID%>':'<%=project.getId()%>'},
                                            'GET');">
                                TASKS
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-primary btn-xs"
                                    onclick="postToUrl(
                                            '${pageContext.request.contextPath}/project/edit',
                                            {'<%=FieldConst.ID%>':'<%=project.getId()%>'},
                                            'GET');">
                                EDIT
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-danger btn-xs"
                                    onclick="postToUrl(
                                            '${pageContext.request.contextPath}/project/delete',
                                            {'<%=FieldConst.ID%>':'<%=project.getId()%>'},
                                            'POST');">
                                DELETE
                            </button>
                        </td>

                    </tr>
                    <%}%>
                    </tbody>
                </table>
                <button class="btn btn-success"
                        onclick="postToUrl(
                                '${pageContext.request.contextPath}/project/create',
                                '',
                                'POST');">
                    CREATE
                </button>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="/WEB-INF/jsp/fragment/footer.jsp"/>
</div>
</body>
</html>
