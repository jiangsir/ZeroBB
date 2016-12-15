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
		$("#division").change(function() { //事件發生
			//alert("======");
			$('#form1').submit();
		});
	});
</script>
<script type="text/javascript"
    src="include/div/TouchApi.js?${applicationScope.built }"></script>

</head>
<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<table class="table table-hover">
			<tr>
				<th>編號</th>
				<th width="50%">標題</th>
				<th>發布單位</th>
				<th>發布時間</th>
				<th>有效期限</th>
				<th>點閱數</th>
			</tr>
			<c:forEach var="article" items="${articles}">
				<tr>
					<td>${article.id}</td>
					<td>[${article.info.value}] <a
						href="./ShowArticle?id=${article.id}">${article.title}&nbsp;</a>
					<c:forEach var="upfile" items="${article.upfiles}">
							<a href="./Download?upfileid=${upfile.id}"
								title="${upfile.filename}"><span
								class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></a>
						</c:forEach> 
						<c:set var="article" value="${article}" scope="request" />
						<jsp:include page="include/div/ShowArticle_Toolbar.jsp"/>
							
					</td>
					<td>${article.user.division.value}</td>
					<td><fmt:formatDate value="${article.postdate}"
							pattern="yyyy-MM-dd HH:mm" /></td>
					<td><c:if test="${article.outdate.time<now.time}">
            [已過期]           </c:if> ${article.available}</td>
					<td>${article.hitnum}</td>
				</tr>
			</c:forEach>
		</table>

		<nav>
			<ul class="pagination">
				<li><a href="?${querystring}&page=${page-1}"><span
						aria-hidden="true">&laquo;</span><span class="sr-only">Previous</span></a></li>
				<li><a href="?${querystring}&page=1">1 <span
						class="sr-only">(current)</span></a></li>
				<li><a href="?${querystring}&page=2">2 <span
						class="sr-only">(current)</span></a></li>
				<li><a href="?${querystring}&page=3">3 <span
						class="sr-only">(current)</span></a></li>
				<li><a href="?${querystring}&page=4">4 <span
						class="sr-only">(current)</span></a></li>
				<li><a href="?${querystring}&page=5">5 <span
						class="sr-only">(current)</span></a></li>
				<li><a href="?${querystring}&page=${page+1}"><span
						aria-hidden="true">&raquo;</span><span class="sr-only">Next</span></a></li>
			</ul>
		</nav>
	</div>

	<jsp:include page="Footer.jsp" />

</body>
</html>
