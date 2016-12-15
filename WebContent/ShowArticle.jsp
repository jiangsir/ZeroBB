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

<script type="text/javascript">
	function mytime(nowtime) {
		var nowdate = new Date();
		nowdate.setTime(nowtime);
		return formatDate(nowdate, "y-MM-dd HH:mm:ss");
		//jQuery("#postdate").text( formatDate( nowdate, "y-MM-dd HH:mm:ss") );
	}
</script>
 <script type="text/javascript"
	src="include/div/TouchApi.js?${applicationScope.built }"></script>
</head>
<jsp:useBean id="now" class="java.util.Date" />

<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<div style="text-align: right;">
			發佈人員：${article.user.name}，發佈單位:${article.user.division.value}，瀏覽次數：
			${article.hitnum}<br /> 發布自： <span id="postdate"><fmt:formatDate
					value="${article.postdate}" pattern="yyyy-MM-dd HH:mm:ss" /></span><br />
			至： <span id="outdate"><fmt:formatDate
					value="${article.outdate}" pattern="yyyy-MM-dd HH:mm:ss" /></span><br />
			<c:forEach var="tag" items="${article.tags}">
				<button type="button" class="btn btn-default btn-xs">
					<span class="glyphicon glyphicon-tag"></span> ${tag.tagtitle}
				</button>
			</c:forEach>
			<jsp:include page="include/div/ShowArticle_Toolbar.jsp" />

			<c:if test="${article.isUpdatable(sessionScope.currentUser)}">
        請勿使用 IE 操作，會有問題。			
			</c:if>
		</div>
		<h2>[${article.info.value}] ${article.title}</h2>
		<div id="text">${article.content}</div>
		<hr />
		<c:if test="${fn:length(article.upfiles)>0}">
			<h3>附件：</h3>
		</c:if>
		<c:forEach var="upfile" items="${article.upfiles}">
			<h3>
				<c:if test="${upfile.isImage}">
					<img src="./Download?upfileid=${upfile.id}" style="max-width: 90%" />
				</c:if>
				<c:if test="${!upfile.isImage}">
					<span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span> ${upfile.filename} <a
						href="./Download?upfileid=${upfile.id}">下載</a>
					<c:if test="${upfile.isGoogleViewer}">
						<a
							href="https://docs.google.com/viewer?url=http://${pageContext.request.serverName}${pageContext.request.contextPath}/Download?upfileid=${upfile.id}"
							target="_blank">檢視</a>
						<!--		
		<a href="http://docs.google.com/viewer?url=http://${pageContext.request.serverName}${pageContext.request.contextPath}/upfiles/${article.id}_${upfile.id}_${article.account}.${fn:split(upfile.filename, '.')[1]}">檢視</a>
<a href="http://${pageContext.request.serverName}${pageContext.request.contextPath}/upfiles/${article.id}_${upfile.id}_${article.account}.${fn:split(upfile.filename, '.')[1]}">檢視</a>
-->

					</c:if>
				</c:if>
				<span style="font-size: 10px">(${upfile.hitnum}次)</span>
			</h3>
		</c:forEach>
		<p></p>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>
