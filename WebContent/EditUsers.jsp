<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="CommonHead.jsp" />
</head>
<body>
	<div id="container">
		<jsp:include page="Header.jsp" />
		<div id="main">
			<div id="text">
				<form id="form1" name="form1" method="post" action="">
					<div id="tabs-1">
						<table align="center" style="">
							<tr style="padding: 10px;">
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
									<td style="padding: 10px;"><input name="account"
										type="text" value="${user.account}" size="10%" maxlength="50" /></td>
									<td style="padding: 10px;"><input name="name" type="text"
										value="${user.name}" size="15%" maxlength="50" /></td>
									<td style="padding: 10px;"><input name="division"
										type="text" value="${user.division}" size="10%" maxlength="50" /></td>
                                    <td style="padding: 10px;"><input name="ROLE"
                                        type="text" value="${user.role}" size="10%" maxlength="50" /></td>
									<td style="padding: 10px;"><input name="email" type="text"
										value="${user.email}" size="20%" maxlength="50" /></td>
									<td style="padding: 10px;"><input name="headline"
										type="text" value="${user.headline}" size="5%" maxlength="50" /></td>
									<td style="padding: 10px;"><input name="visible"
										type="text" value="${user.visible}" size="5%" maxlength="50" /></td>
									<td style="padding: 10px;">管理 | 刪除</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<input type="submit" value="送出" />
				</form>
			</div>
		</div>
		<jsp:include page="Footer.jsp" />
	</div>
</body>
</html>
