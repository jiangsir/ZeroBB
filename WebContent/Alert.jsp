<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="user" uri="/WEB-INF/user.tld"%>

<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
</head>
<body>
	<div id="container">
		<!-- header -->
		<jsp:include page="Header.jsp" />
		<!--end header -->
		<!-- main -->
		<div id="main">
			<div id="text">
				<fieldset
					style="text-align: left; padding: 1em; margin: auto; width: 60%;">
					<legend style="font-size: x-large;">${alert.type}</legend>
					<h1>${alert.title}</h1>
					<p></p>
					<h2>${alert.subtitle }</h2>
					<div>${alert.content}</div>
					<hr style="margin-top: 3em;" />
					<ul>
						<c:forEach var="list" items="${alert.list}">
							<li>${list}</li>
						</c:forEach>
					</ul>
					<ul>
						<c:forEach var="map" items="${alert.map}">
							<li>${map.key}:${map.value}</li>
						</c:forEach>
					</ul>
					<hr style="margin-top: 3em;" />
					<div style="text-align: center;">
						<c:forEach var="uri" items="${alert.uris}">
							<a href="${uri.value}" type="button">${uri.key}</a>
						</c:forEach>
					</div>
				</fieldset>
			</div>
			<p></p>
			<c:if test="${user:isAdmin(sessionScope.currentUser)}">
				<fieldset style="text-align: left; background-color: maroon;">
					<legend>Debug: </legend>
					<ul>
						<c:forEach var="debug" items="${alert.debugs}">
							<li>${debug}</li>
						</c:forEach>
					</ul>
					<div>
						<c:if test="${fn:length(alert.stacktrace)>0}">
							<div style="text-align: left; margin-top: 1em;">
								<h3>stacktrace:</h3>
								<div style="font-family: monospace;">
									<c:forEach var="stacktrace" items="${alert.stacktrace}">${stacktrace.className}.${stacktrace.methodName}(${stacktrace.fileName}:${stacktrace.lineNumber})<br />
									</c:forEach>
								</div>
							</div>
						</c:if>
					</div>
				</fieldset>
			</c:if>
			<p></p>
		</div>
		<!-- end main -->
		<!-- footer -->
		<jsp:include page="Footer.jsp" />
		<!-- end footer -->
	</div>
</body>
</html>