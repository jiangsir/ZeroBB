<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<ul>
	<c:forEach var="article" items="${articles}" varStatus="varstatus">
		<li><a
			href="http://${pageContext.request.serverName}${pageContext.servletContext.contextPath}/ShowArticle?id=${article.id}"
			target="_blank" style="">${article.title}</a></li>
	</c:forEach>
</ul>
<br />
<div style="text-align: right">
	<a
		href="http://${pageContext.request.serverName}${pageContext.servletContext.contextPath}/?${pageContext.request.queryString}"
		target="_blank">更多...</a>
</div>


