<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="org.jetbrains.annotations.NotNull" %>
<%@ page import="ru.stepanenko.tm.model.entity.User" %>
<%@ page import="ru.stepanenko.tm.enumerate.Role" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 03.06.2019
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <% @NotNull final User loggedUser = (User) request.getSession().getAttribute(FieldConst.USER);%>
            <a class="navbar-brand"
               href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span></a>
            <a <%if (loggedUser == null) out.print("hidden");%> class="navbar-brand"
                                                                href="${pageContext.request.contextPath}/project/list">Projects</a>
            <a <%if (loggedUser == null) out.print("hidden");%> class="navbar-brand"
                                                                href="${pageContext.request.contextPath}/task/list">Tasks</a>
            <a <%if (loggedUser == null || loggedUser.getRole() == Role.USER) out.print("hidden");%>
                    class="navbar-brand" href="${pageContext.request.contextPath}/user/list">Users</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">

            <div <%if (request.getSession().getAttribute(FieldConst.USER) == null) out.print("hidden");%>
                    class="navbar-form navbar-right">
                <button class="btn btn-primary"
                        onclick="postToUrl(
                                '${pageContext.request.contextPath}/user/edit',
                                {'<%=FieldConst.USER_ID%>':'<%if(loggedUser != null) out.print(loggedUser.getId());%>'},
                                'GET');">
                    PROFILE
                </button>
                <button class="btn btn-danger"
                        onclick="postToUrl(
                                '${pageContext.request.contextPath}/user/logout',
                                '',
                                'POST');">

                    LOG OUT
                </button>
            </div>

            <form <%if (request.getSession().getAttribute(FieldConst.USER) != null) out.print("hidden");%> method="POST"
                                                                                                           action="${pageContext.request.contextPath}/user/login"
                                                                                                           class="navbar-form navbar-right">
                <div class="form-group">
                    <input type="text" placeholder="Login" name="<%=FieldConst.LOGIN%>" class="form-control">
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Password" name="<%=FieldConst.PASSWORD%>" class="form-control">
                </div>
                <button type="submit" class="btn btn-success">Sign in</button>
            </form>
        </div><!--/.navbar-collapse -->
    </div>
</nav>
