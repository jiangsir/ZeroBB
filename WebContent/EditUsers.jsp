<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
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
		<form id="form1" name="form1" method="post" action="">
			<table class="table table-hover">
				<tr>
					<th>account</th>
					<th>name</th>
					<th>division</th>
					<th>ROLE</th>
					<th>email</th>
					<th>headline</th>
					<th>visible</th>
					<th>管理</th>
				</tr>
				<c:forEach var="user" items="${users}">
					<tr>
						<td><input name="account" type="text" value="${user.account}"
							size="10%" maxlength="50" /></td>
						<td><input name="name" type="text" value="${user.name}"
							size="15%" maxlength="50" /></td>
						<td><input name="division" type="text"
							value="${user.division}" size="10%" maxlength="50" /></td>
						<td><input name="ROLE" type="text" value="${user.role}"
							size="10%" maxlength="50" /></td>
						<td><input name="email" type="text" value="${user.email}"
							size="20%" maxlength="50" /></td>
						<td><input name="headline" type="text"
							value="${user.headline}" size="5%" maxlength="50" /></td>
						<td><input name="visible" type="text" value="${user.visible}"
							size="5%" maxlength="50" /></td>
						<td>管理 | 刪除</td>
					</tr>
				</c:forEach>
			</table>
			<input type="submit" value="送出" class="btn btn-success" />
		</form>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>
