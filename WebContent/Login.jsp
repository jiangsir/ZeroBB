<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="CommonHead.jsp" />
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div class="container">

		<%-- <p style="font-size: large; font-weight: bold; color: red;">${sessionScope.LoginMessage}</p> --%>
		<form method="post" action="Login">
			<div class="form-group">
				<label for="Select">身分</label> <select class="form-control"
					name="account">
					<c:forEach var="user" items="${users}">
						<option value="${user.account}">${user.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1">密碼</label> <input type="password"
					class="form-control" id="exampleInputPassword1"
					placeholder="Password" name="passwd">
			</div>
			<!-- 			<button type="submit" class="btn btn-default">登入</button>
 -->
			<button type="submit" value="Send" class="btn btn-success"
				id="submit">登入</button>
		</form>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>
