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
<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="header">
        <h3 class="text-muted">List of projects:</h3>
        <h4 class="text-muted">PROJECT MANAGEMENT:</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Data begin</th>
                        <th>Data End</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Project project:(Collection<Project>) request.getAttribute(FieldConst.PROJECTS)){%>
						<tr>
							<td><%=project.getId()%></td>
							<td><%=project.getName()%></td>
							<td><%=project.getDescription()%></td>
							<td><%=project.getDateBegin()%></td>
							<td><%=project.getDateEnd()%></td>
							<td><a class="btn btn-primary btn-xs" href="edit?<%=FieldConst.ID%>=<%=project.getId()%>" role="button">edit</a></td>
							<td><a class="btn btn-danger btn-xs" href="delete?<%=FieldConst.ID%>=<%=project.getId()%>" role="button">delete</a></td>
						</tr>
					<%}%>
                    </tbody>
                </table>
				<a class="btn btn-success" href="create" role="button">Create project</a>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <jsp:include page="/WEB-INF/jsp/fragment/footer.jsp"/>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>
