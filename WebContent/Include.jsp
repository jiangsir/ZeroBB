<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/Include?p=HEADLINE">頭條</a>
	<br />
	<a href="${pageContext.servletContext.contextPath}/?info=頭條">頭條
		more...</a>
	<br />
	<a
		href="${pageContext.servletContext.contextPath}/Include?p=TAGS&tagname=STUDENT">分類公告</a>
	<br />
	<a href="${pageContext.servletContext.contextPath}/?tagname=STUDENT">分類公告
		more...</a>
	<br />
	<a
		href="${pageContext.servletContext.contextPath}/Include?p=IMPORTANT&division=xuewu">學務處重要公告</a>
	<br />

</body>
</html>