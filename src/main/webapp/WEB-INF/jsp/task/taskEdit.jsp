<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 6/7/2019
  Time: 12:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="ru.stepanenko.tm.enumerate.Status" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="header">
        <h3 class="text-muted"><br/></h3>
        <h4 class="text-muted">TASK EDIT</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <form method="POST" action="${pageContext.request.contextPath}/task/edit">
                <input type=hidden name="<%=FieldConst.TASK_ID%>" value="${task.getId()}">
                <input type=hidden name="<%=FieldConst.PROJECT_ID%>" value="${projectId}">
                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputName">Name</label>
                            <input type="text" class="form-control" name="<%=FieldConst.NAME%>" id="inputName"
                                   value="${task.getName()}">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputDescription">Description</label>
                    <textarea class="form-control" name="<%=FieldConst.DESCRIPTION%>" id="inputDescription"
                              rows="10">${task.getDescription()}</textarea>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputDateBegin">Date begin</label>
                            <c:set var="dateBegin">
                                <fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm"
                                                value="${task.getDateBegin()}"/>
                            </c:set>
                            <input class="form-control" type="datetime-local" name="<%=FieldConst.DATE_BEGIN%>"
                                   value="${dateBegin}"
                                   id="inputDateBegin">
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputDateEnd">Date end</label>
                            <c:set var="dateEnd">
                                <fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm"
                                                value="${task.getDateEnd()}"/>
                            </c:set>
                            <input class="form-control" type="datetime-local" name="<%=FieldConst.DATE_END%>"
                                   value="${dateEnd}"
                                   id="inputDateEnd">
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputStatus">Select status:</label>
                            <select class="form-control" name="<%=FieldConst.STATUS%>" id="inputStatus">
                                <c:set var="selected" value=""/>
                                <c:forEach var="status" items="<%=Status.values()%>">
                                    <div hidden>
                                            ${selected=""}
                                        <c:if test="${status==task.getStatus()}">
                                            ${selected="selected"}
                                        </c:if>
                                    </div>
                                    <option ${selected}>${status}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputProject">Select project:</label>
                            <select class="form-control" name="<%=FieldConst.EDIT_PROJECT_ID%>" id="inputProject">
                                <option></option>
                                <c:set var="selected" value=""/>
                                <c:forEach var="project" items="${tasks}">
                                    <div hidden>
                                            ${selected=""}
                                        <c:if test="${project.getId()==project.getProjectId()}">
                                            ${selected="selected"}
                                        </c:if>
                                    </div>
                                    <option ${selected} value="${project.getId()}">${project.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-success">SAVE</button>
            </form>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="/WEB-INF/jsp/fragment/footer.jsp"/>
</div>
</body>
</html>

