<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 03.06.2019
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/WEB-INF/jsp/fragment/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <div class="jumbotron">
        <h3>List of projects:</h3>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-condensed">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Username</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>@mdo</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>@fat</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td colspan="2">Larry the Bird</td>
                        <td>@twitter</td>
                    </tr>
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
