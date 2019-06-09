<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="ru.stepanenko.tm.model.entity.User" %>
<%@ page import="org.jetbrains.annotations.NotNull" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 04.06.2019
  Time: 0:54
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
        <h4 class="text-muted">USER MANAGEMENT:</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Login</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Role</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% @NotNull int i = 0;
                        for (@NotNull User user : (Collection<User>) request.getAttribute(FieldConst.USERS)) {
                            i++;
                    %>
                    <tr>
                        <td><%=i%>
                        </td>
                        <td><%=user.getLogin()%>
                        </td>
                        <td><%=user.getName()%>
                        </td>
                        <td><%=user.getDescription()%>
                        </td>
                        <td><%=user.getRole()%>
                        </td>
                        <td>
                            <button class="btn btn-primary btn-xs"
                                    onclick="postToUrl(
                                            '${pageContext.request.contextPath}/user/edit',
                                            {'<%=FieldConst.USER_ID%>':'<%=user.getId()%>'},
                                            'GET');">
                                EDIT
                            </button>
                        </td>
                        <td>
                            <button class="btn btn-danger btn-xs"
                                    onclick="postToUrl(
                                            '${pageContext.request.contextPath}/user/delete',
                                            {'<%=FieldConst.USER_ID%>':'<%=user.getId()%>'},
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
                                '${pageContext.request.contextPath}/user/create',
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