<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 6/5/2019
  Time: 8:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.stepanenko.tm.model.entity.Project" %>
<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="ru.stepanenko.tm.util.DateFormatter" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="header">
        <h3 class="text-muted">List of projects:</h3>
        <h4 class="text-muted">PROJECT EDIT:</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <% Project project = (Project) request.getAttribute(FieldConst.PROJECT); %>
            <form method="POST" action="edit">
                <input type=hidden name="<%=FieldConst.ID%>" value="<%=project.getId()%>">
                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputName">Name</label>
                            <input type="text" class="form-control" name = "<%=FieldConst.NAME%>" id="inputName" value="${project.getName()}sdsd">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputDescription">Description</label>
                    <textarea class="form-control" name = "<%=FieldConst.DESCRIPTION%>" id="inputDescription" rows="10"><%=project.getDescription()%></textarea>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputDateBegin">Date begin</label>
                            <input class="form-control" type="datetime-local" name = "<%=FieldConst.DATE_BEGIN%>" value="<%=DateFormatter.dateToInput(project.getDateBegin())%>"
                                   id="inputDateBegin">
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputDateEnd">Date end</label>
                            <input class="form-control" type="datetime-local" name = "<%=FieldConst.DATE_END%>" value="<%=DateFormatter.dateToInput(project.getDateEnd())%>"
                                   id="inputDateEnd">
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputStatus">Select status:</label>
                            <select class="form-control" name = "<%=FieldConst.STATUS%>" id="inputStatus" >
                                <option>PLANNED</option>
                                <option>INPROCESS</option>
                                <option>DONE</option>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="/WEB-INF/jsp/fragment/footer.jsp"/>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>