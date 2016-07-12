<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<c:forEach var="article" items="${articles}" varStatus="varstatus">
	<c:set var="info" value="${article.info}" />
	<c:set var="color" value="color:blue;" />
	<c:if test="${varstatus.count%2==1}">
		<c:set var="color" value="color:red;" />
	</c:if>
	<div style="font-size: 1.5em;">
		<a
			href="http://${pageContext.request.serverName}${pageContext.servletContext.contextPath}/ShowArticle?id=${article.id}"
			target="_blank" style="${color}">${article.title}</a>
	</div>
</c:forEach>
<div style="text-align: right">
	<a
		href="http://${pageContext.request.serverName}${pageContext.servletContext.contextPath}/?info=${info}&${pageContext.request.queryString}"
		target="_blank">更多...</a>
</div>
