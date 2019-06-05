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
            <form action="create" method="get">
                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputName">Name</label>
                            <input type="text" class="form-control" id="inputName" placeholder="<%=project.getName()%>">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputDescription">Description</label>
                    <textarea class="form-control" id="inputDescription" rows="10"
                              placeholder="<%=project.getDescription()%>"></textarea>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputDateBegin">Date begin</label>
                            <input class="form-control" type="datetime-local" value="2011-08-19T13:45:00"
                                   id="inputDateBegin">
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputDateEnd">Date end</label>
                            <input class="form-control" type="datetime-local" value="2011-08-19T13:45:00"
                                   id="inputDateEnd">
                        </div>
                    </div>
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputStatus">Select status:</label>
                            <select class="form-control" id="inputStatus">
                                <option>PLANNED</option>
                                <option>INPROCESS</option>
                                <option>DONE</option>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary" formenctype="text/plain">Submit</button>
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