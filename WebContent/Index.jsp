<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${initParam.TITLE}</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./jscripts/jquery-1.2.6.min.js"></script>
<script language="javascript">
	jQuery(document).ready(function() {
		$("#division").change(function() { //事件發生
			//alert("======");
			$('#form1').submit();
		});
	});
</script>
</head>
<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="userBean" class="jiangsir.zerobb.Beans.UserBean" />
<body>
	<div id="container">
		<!-- header -->
		<jsp:include page="Header.jsp" />
		<!--end header -->
		<!-- main -->
		<div id="main">
			<div id="text">
				<div class="landscape">
					<ul>
						<c:forEach var="tag" items="${tags}">
							<li><a href="?tagname=${tag.tagname}"
								title="${tag.descript}">${tag.tagtitle }</a></li>
						</c:forEach>
					</ul>
				</div>
				<br></br>

				<form id="form1" name="form1" method="get" action="">
					查詢公告單位： <select id="division" name="division">
						<option>請選擇...</option>
						<c:forEach var="division" items="${divisions}">
							<option value="${division.key}">${division.value}</option>
						</c:forEach>
					</select>
				</form>
				<br />
				<c:forEach var="article" items="${articles}">
					<c:if
						test="${article.outdate.time>now.time && (article.info=='重要' || article.info=='頭條')}">
						<div style="color: red; font-size: 1.4em;">
							[${article.info}] <a href="./ShowArticle?id=${article.id}">${article.title}</a>
							<c:forEach var="upfile" items="${article.upfiles}">
								<a href="./Download?upfileid=${upfile.id}"
									title="${upfile.filename}"><img src="images/paperclip.png"
									border="0" /></a>
							</c:forEach>
							<br />
						</div>
					</c:if>
				</c:forEach>
				<br />

				<table style="width: 100%;">
					<tr>
						<th>編號</th>
						<th width="50%">標題</th>
						<th>發布單位</th>
						<th>發布時間</th>
						<th>有效期限</th>
						<th>點閱數</th>
					</tr>
					<c:forEach var="article" items="${articles}">
						<jsp:setProperty name="userBean" property="account"
							value="${article.account}" />
						<tr>
							<td>${article.id}</td>
							<td>[${article.info}] <a
								href="./ShowArticle?id=${article.id}">${article.title}&nbsp;</a>
								<c:forEach var="upfile" items="${article.upfiles}">
									<a href="./Download?upfileid=${upfile.id}"
										title="${upfile.filename}"><img src="images/paperclip.png"
										border="0" /></a>
								</c:forEach></td>
							<td>${userBean.user.divisionName}</td>
							<td style="text-align: right; font-size: 10px;"><fmt:formatDate
									value="${article.postdate}" pattern="yyyy-MM-dd HH:mm" /></td>
							<td style="text-align: left"><c:if
									test="${article.outdate.time<now.time}">
			[已過期]			</c:if> ${article.available}</td>
							<td>${article.hitnum}</td>
						</tr>
					</c:forEach>
				</table>
				<p align="center">
					<a href="?${querystring}&page=1">第一頁</a>&nbsp; | &nbsp;
					<c:if test="${page<=1}">上一頁</c:if>
					<c:if test="${page>1}">
						<a href="?${querystring}&page=${page-1}">上一頁</a>
					</c:if>
					&nbsp; | &nbsp;第 ${page} 頁&nbsp; | &nbsp;
					<c:if test="${fn:length(articles)==0}">下一頁</c:if>
					<c:if test="${fn:length(articles)!=0}">
						<a href="?${querystring}&page=${page+1}">下一頁</a>
					</c:if>
					&nbsp;
				</p>
			</div>
		</div>
		<!-- end main -->
		<!-- footer -->
		<jsp:include page="Footer.jsp" />
		<!-- end footer -->
	</div>
</body>
</html>
