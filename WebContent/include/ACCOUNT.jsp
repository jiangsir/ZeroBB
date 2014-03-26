<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<c:forEach var="article" items="${articles}">
	<div class="article" style="height: auto">
		[${article.info}] <a
			href="http://${pageContext.request.localAddr}:${pageContext.request.localPort}${pageContext.servletContext.contextPath}/ShowArticle?id=${article.id}"
			target="_blank">${article.title}</a>
		<c:forEach var="upfile" items="${article.upfiles}">
			<img src="images/paperclip.png" />
		</c:forEach>
		(${article.user.division})<br />
		<fmt:formatDate value="${article.postdate}" pattern="yyyy-MM-dd HH:mm" />
	</div>
</c:forEach>
<div style="text-align: right">
	<a
		href="http://${pageContext.request.localAddr}:${pageContext.request.localPort}${pageContext.servletContext.contextPath}/?${pageContext.request.queryString}"
		target="_blank">更多...</a>
</div>
