<%@ page import="org.jetbrains.annotations.NotNull" %>
<%@ page import="ru.stepanenko.tm.model.entity.Task" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="ru.stepanenko.tm.util.DateFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
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
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="i" value="0"/>
                    <c:forEach var="task" items="${tasks}">
                        <tr>
                            <td>${i=i+1}
                            </td>
                            <td>${task.getName()}
                            </td>
                            <td>${task.getDescription()}
                            </td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
                                                value="${task.getDateBegin()}"/>
                            </td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
                                                value="${task.getDateEnd()}"/>
                            </td>
                            <td>${task.getStatus()}
                            </td>
                            <td>
                                <button class="btn btn-primary btn-xs"
                                        onclick="postToUrl(
                                                '${pageContext.request.contextPath}/task/edit',
                                                {'<%=FieldConst.TASK_ID%>':'${task.getId()}',
                                                '<%=FieldConst.PROJECT_ID%>':'${projectId}'},
                                                'GET');">
                                    EDIT
                                </button>
                            </td>
                            <td>
                                <button class="btn btn-danger btn-xs"
                                        onclick="postToUrl(
                                                '${pageContext.request.contextPath}/task/delete',
                                                {'<%=FieldConst.TASK_ID%>':'${task.getId()}',
                                                '<%=FieldConst.PROJECT_ID%>':'${projectId}'},
                                                'POST');">
                                    DELETE
                                </button>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button class="btn btn-success"
                        onclick="postToUrl(
                                '${pageContext.request.contextPath}/task/create',
                                {'<%=FieldConst.PROJECT_ID%>':'${projectId}'},
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
