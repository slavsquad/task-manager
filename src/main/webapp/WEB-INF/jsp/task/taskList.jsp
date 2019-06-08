<%@ page import="org.jetbrains.annotations.NotNull" %>
<%@ page import="ru.stepanenko.tm.model.entity.Project" %>
<%@ page import="ru.stepanenko.tm.model.entity.Task" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="ru.stepanenko.tm.util.DateFormatter" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 04.06.2019
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="header">
        <h3 class="text-muted"><br/></h3>
        <h4 class="text-muted">TASK MANAGEMENT:</h4>
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
                    <%
                        @NotNull final String projectId = (String) request.getAttribute(FieldConst.PROJECT_ID);
                        @NotNull int i = 0;
                        for (Task task : (Collection<Task>) request.getAttribute(FieldConst.TASKS)) {
                            i++;
                    %>
                    <tr>
                        <td><%=i%>
                        </td>
                        <td><%=task.getName()%>
                        </td>
                        <td><%=task.getDescription()%>
                        </td>
                        <td><%=DateFormatter.dateToString(task.getDateBegin())%>
                        </td>
                        <td><%=DateFormatter.dateToString(task.getDateEnd())%>
                        </td>
                        <td><%=task.getStatus()%>
                        <td>
                            <button class="btn btn-primary btn-xs"
                                    onclick="postToUrl(
                                            '${pageContext.request.contextPath}/task/edit',
                                            {'<%=FieldConst.TASK_ID%>':'<%=task.getId()%>'},
                                            'GET');">
                                EDIT
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-danger btn-xs"
                                    onclick="postToUrl(
                                            '${pageContext.request.contextPath}/task/delete',
                                            {'<%=FieldConst.TASK_ID%>':'<%=task.getId()%>',
                                            '<%=FieldConst.PROJECT_ID%>':'<%=projectId%>'},
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
                                '${pageContext.request.contextPath}/task/create',
                                {'<%=FieldConst.PROJECT_ID%>':'<%=projectId%>'},
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
