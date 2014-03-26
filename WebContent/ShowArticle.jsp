<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="article" uri="/WEB-INF/article.tld"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${initParam.TITLE}</title>
<jsp:include page="CommonHead.jsp" />
<script language="javascript">
	jQuery(document).ready(function() {

		jQuery("#touch").bind('click', function() {
			// jQuery("#postdate").text(mytime(parseInt(${now.time})));

			jQuery.ajax({
				type : "GET",
				url : "./Touch.api",
				data : "articleid=" + $(this).attr("articleid"),
				async : true,
				timeout : 5000,
				success : function(result) {
					if (result == "" || result == null) {
						result = "";
					}
					//jQuery("#postdate").text(result);
					alert("順序調整成功！ (" + result + ")");
				}
			}); // jQusery ajax
		});

	});

	function mytime(nowtime) {
		var nowdate = new Date();
		nowdate.setTime(nowtime);
		return formatDate(nowdate, "y-MM-dd HH:mm:ss");
		//jQuery("#postdate").text( formatDate( nowdate, "y-MM-dd HH:mm:ss") );
	}
</script>
</head>
<%-- <jsp:useBean id="articleBean" class="jiangsir.zerobb.Beans.ArticleBean" />
<jsp:setProperty name="articleBean" property="id" value="${article.id}" />
<jsp:setProperty name="articleBean" property="session_account"
	value="${sessionScope.session_account}" />
 --%><jsp:useBean id="now" class="java.util.Date" />

<body>
	<div id="container">
		<!-- header -->
		<jsp:include page="Header.jsp" />
		<!--end header -->
		<!-- main -->
		<div id="main">
			<div id="text">
				<p style="text-align: right; margin-right: 100px;">
					瀏覽次數： ${article.hitnum}<br /> 發布自： <span id="postdate"><fmt:formatDate
							value="${article.postdate}" pattern="yyyy-MM-dd HH:mm:ss" /></span><br />
					至： <span id="outdate"><fmt:formatDate
							value="${article.outdate}" pattern="yyyy-MM-dd HH:mm:ss" /></span><br />
					<c:forEach var="tag" items="${tags}">
						<span title="${tag.descript}">${tag.tagtitle} | </span>
					</c:forEach>
					<span title="${userBean.user.account}">${userBean.user.divisionName}
					</span>
					<c:if
						test="${article:isUpdatable(article, sessionScope.currentUser)}"> | <img
							src="images/touch.png" id="touch" articleid="${article.id}"
							border="0" title="碰一下，將排序在最上方" style="cursor: pointer;" /> | <a
							href="./UpdateArticle?id=${article.id}"><img
							src="images/edit18.png" alt="編輯" title="編輯" border="0" /></a> | <a
							href="./DeleteArticle?articleid=${article.id}"><img
							src="images/delete18.png" alt="強制過期" title="強制過期" border="0" /></a>
					</c:if>
				</p>
				<h2>[${article.info}] ${article.title}</h2>
				<div id="text">${article.content}</div>
				<hr />
				<c:if test="${fn:length(article.upfiles)>0}">
					<h3>附件：</h3>
				</c:if>
				<c:forEach var="upfile" items="${article.upfiles}">
					<h3>
						<img src="images/paperclip.png" /> ${upfile.filename} <a
							href="./Download?upfileid=${upfile.id}">下載</a>
						<c:if
							test="${fn:endsWith(fn:toLowerCase(upfile.filename), '.doc') || fn:endsWith(fn:toLowerCase(upfile.filename), '.docx') || fn:endsWith(fn:toLowerCase(upfile.filename), '.pdf')}">
							<a
								href="https://docs.google.com/viewer?url=http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/Download?upfileid=${upfile.id}"
								target="_blank">檢視</a>
							<!--		
		<a href="http://docs.google.com/viewer?url=http://${pageContext.request.serverName}${pageContext.request.contextPath}/upfiles/${article.id}_${upfile.id}_${article.account}.${fn:split(upfile.filename, '.')[1]}">檢視</a>
<a href="http://${pageContext.request.serverName}${pageContext.request.contextPath}/upfiles/${article.id}_${upfile.id}_${article.account}.${fn:split(upfile.filename, '.')[1]}">檢視</a>
-->

						</c:if>
						<span style="font-size: 10px">(${upfile.hitnum}次)</span>
					</h3>
				</c:forEach>
				<p></p>
			</div>
		</div>
		<!-- end main -->
		<!-- footer -->
		<jsp:include page="Footer.jsp" />
		<!-- end footer -->
	</div>
</body>
</html>
