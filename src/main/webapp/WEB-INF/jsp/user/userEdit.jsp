<%@ page import="ru.stepanenko.tm.model.entity.User" %>
<%@ page import="ru.stepanenko.tm.util.FieldConst" %>
<%@ page import="ru.stepanenko.tm.enumerate.Role" %>
<%@ page import="org.jetbrains.annotations.NotNull" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 09.06.2019
  Time: 11:21
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
        <h4 class="text-muted">USER EDIT:</h4>
    </div>
    <div class="jumbotron">
        <div class="row">
            <%
                User user = (User) request.getAttribute(FieldConst.USER);
                User loggedUser = (User) request.getSession().getAttribute(FieldConst.USER);
            %>
            <form method="POST" action="${pageContext.request.contextPath}/user/edit">

                <input type=hidden name="<%=FieldConst.USER_ID%>" value="<%=user.getId()%>">

                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputLogin">Login</label>
                            <input type="text" class="form-control" name="<%=FieldConst.LOGIN%>" id="inputLogin"
                                   value="<%=user.getLogin()%>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputPassword">Password</label>
                            <input type="password" class="form-control" name="<%=FieldConst.PASSWORD%>"
                                   id="inputPassword"
                                   value="<%=user.getPassword()%>">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">
                        <div class="form-group">
                            <label for="inputName">Name</label>
                            <input type="text" class="form-control" name="<%=FieldConst.NAME%>" id="inputName"
                                   value="<%=user.getName()%>">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="inputDescription">Description</label>
                    <textarea class="form-control" name="<%=FieldConst.DESCRIPTION%>" id="inputDescription"
                              rows="10"><%=user.getDescription()%></textarea>
                </div>
                <div class="row">
                    <div class="col-xs-3" <%if (Role.USER == loggedUser.getRole()) out.print("hidden");%>>
                        <div class="form-group">
                            <label for="inputRole">Select role:</label>
                            <select class="form-control" name="<%=FieldConst.ROLE%>" id="inputRole">
                                <%for (@NotNull Role role : Role.values()) {%>
                                <option <%if (role == user.getRole()) out.print("selected");%>><%=role%>
                                </option>
                                <%}%>
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
