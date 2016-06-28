<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
				<p>
					maxMemroy:
					<fmt:formatNumber value="${maxMemory}" pattern="###,###,###,###" />
					bytes Xmx 指定的最大可用記憶體<br /> totalMemory:
					<fmt:formatNumber value="${totalMemory}" pattern="###,###,###,###" />
					bytes 目前 JVM 已取得的記憶體<br /> freeMemory:
					<fmt:formatNumber value="${freeMemory}" pattern="###,###,###,###" />
					bytes 目前 JVM 可用的記憶體<br /> 目前系統記憶體用量：
					<fmt:formatNumber value="${(totalMemory-freeMemory)/1024}"
						pattern="###,###,###" />
					KB
				</p>
				<p>管理 tag</p>
				<c:forEach var="tag" items="${tags}">
					<div>${tag.tagname}: ${tag.tagtitle} (${tag.descript })</div>
				</c:forEach>
				<p></p>
				<form id="form2" name="form2" method="post" action="UpfileToBlob">
					處理到 upfileid= <input type="text" name="lastupfileid" value="0" />
					為止！ <input type="submit" value="將 upfiles 轉到 Blob " />
				</form>
				<p>null blob upfils 共有 ${fn:length(upfiles) }</p>
				<table width="100%">
					<tr>
						<td>upfileid</td>
						<td>articleid</td>
						<td>filename</td>
					</tr>
					<c:forEach var="upfile" items="${upfiles}">
						<tr>
							<td>${upfile.id}</td>
							<td><a href="./ShowArticle?id=${upfile.articleid}">${upfile.articleid}</a>
							</td>
							<td>${upfile.filename }</td>
						</tr>
					</c:forEach>
				</table>


				<form id="form1" name="form1" method="post" action="">
					選擇： <select name="select">
						<option>請選擇...</option>
						<c:forEach var="user" items="${users}">
							<option value="${user.id}">${user.division.value}</option>
						</c:forEach>
					</select>
				</form>
				<table width="100%">
					<tr>
						<th>編號</th>
						<th>標題</th>
						<th>發布者</th>
						<th>發布時間</th>
						<th>點閱數</th>
						<th>操作</th>
					</tr>
					<c:forEach var="article" items="${articles}">
						<tr>
							<td>${article.id}</td>
							<td><a href="./ShowArticle?id=${article.id}">${article.title}</a>
								<c:forEach var="upfile" items="${article.upfiles}">
									<img src="images/paperclip.png" />
								</c:forEach></td>
							<td>${article.user.division}</td>
							<td><fmt:formatDate value="${article.postdate}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${article.hitnum}</td>
							<td><a href="./UpdateArticle?id=${article.id}"><img
									src="images/edit18.png" border="0" /></a> | <a
								href="./DeleteArticle?articleid=${article.id}"><img
									src="images/delete18.png" border="0" /></a></td>
						</tr>
					</c:forEach>
				</table>
				<p>
					<c:forEach var="i" begin="1" end="9" step="1">${i}</c:forEach>
				</p>
				<p>
					<a
						href="http://127.0.0.1:8080/ZeroBB7/Include?p=HEADLINE">http://127.0.0.1:8080/ZeroBB7/Include?p=HEADLINE</a>
					// info 0=一般 1=重要 2=頭條
				</p>
				<p>
					<a
						href="http://127.0.0.1:8080/ZeroBB7/Include?p=TAGS&amp;tagname=HONOR">http://127.0.0.1:8080/ZeroBB7/Include?p=TAGS&amp;tagname=HONOR</a>
				</p>
				<p>
					<a href="./UpdateOldfilepath.ajax">更新 /ZeroBB , /ZeroBB_utf8,
						http://www.nknush 等舊 filepath</a>
				</p>
				<p>&nbsp;</p>
			</div>
		</div>
		<!-- end main -->
		<!-- footer -->
		<jsp:include page="Footer.jsp" />
		<!-- end footer -->
	</div>
</body>
</html>
