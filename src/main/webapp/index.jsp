<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<jsp:include page="WEB-INF/jsp/fragment/header.jsp"/>
<body>

<jsp:include page="WEB-INF/jsp/fragment/navigableBar.jsp"/>
<div class="container theme-showcase" role="main">
    <h3 class="text-muted"><br/></h3>
    <h4 class="text-muted">WELCOME PAGE</h4>
    <div class="jumbotron">
        <div class="row">
            <div class="col-md-12">
                <div class="center-block">
                    <img src="${pageContext.request.contextPath}/resources/images/logo.png"
                         class="img-responsive center-block"
                         alt="Responsive image">
                </div>
            </div>
        </div>
        <!-- Footer -->
    </div>
    <jsp:include page="WEB-INF/jsp/fragment/footer.jsp"/>
</body>
</html>