<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="article" uri="/WEB-INF/article.tld"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="CommonHead.jsp" />
<script type="text/javascript">
	jQuery(document).ready(function() {

		jQuery("#touch").bind('click', function() {
			// jQuery("#postdate").text(mytime(parseInt(${now.time})));

			jQuery.ajax({
				type : "GET",
				url : "./Touch.api",
				data : "articleid=" + $(this).attr("articleid"),
				async : false,
				timeout : 5000,
				success : function(result) {
					if (result == "" || result == null) {
						result = "";
					}
					//jQuery("#postdate").text(result);
					//alert("順序調整成功！ (" + result + ")");
					BootstrapDialog.alert("順序調整成功！ (" + result + ")");
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
	function test() {
		BootstrapDialog.confirm('确认删除当前选中的记录吗?', function(result) {
			if (result) {
				BootstrapDialog.alert('I want 删除!');
			} else {
				BootstrapDialog.alert('I want 不删除!');
			}
		});
	}
</script>
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
			<br /> <br />
			<c:if
				test="${article:isUpdatable(article, sessionScope.currentUser)}">

				<div class="btn-group">
					<button type="button" class="btn btn-default">
						<span class="glyphicon glyphicon-hand-up" id="touch"
							articleid="${article.id}" title="碰一下，將排序在最上方"
							style="cursor: pointer;"></span>
					</button>
					<button type="button" class="btn btn-default">
						<a href="./UpdateArticle?id=${article.id}"><span
							class="glyphicon glyphicon-pencil"></span></a>
					</button>
					<button type="button" class="btn btn-default">
						<a href="./DeleteArticle.api?articleid=${article.id}"><span
							class="glyphicon glyphicon-remove"></span></a>
					</button>
				</div>
				<br />
                    請注意，請勿使用 IE 瀏覽器。可能會有各種問題。
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
