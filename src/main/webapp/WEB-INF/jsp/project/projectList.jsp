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
    <div class="jumbotron">
        <div class="row">
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-condensed">
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
							<td><p><%=project.getId()%></p></td>
							<td><p><%=project.getName()%></p></td>
							<td><p><%=project.getDescription()%></p></td>
							<td><p><%=project.getDateBegin()%></p></td>
							<td><p><%=project.getDateEnd()%></p></td>
						</tr>
					<%}%>
                    </tbody>
                </table>
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
