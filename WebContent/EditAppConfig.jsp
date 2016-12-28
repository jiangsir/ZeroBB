<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="CommonHead.jsp" />
<script src="jscripts/tinymce_4.3.13/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
	tinymce
			.init({
				language : "zh_TW",
				selector : 'textarea',
				plugins : [
						'advlist autolink lists link image charmap print preview anchor',
						'searchreplace visualblocks code fullscreen',
						'insertdatetime media table contextmenu paste code textcolor colorpicker' ],
				toolbar : 'insertfile undo redo | styleselect | forecolor backcolor | fontsizeselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
				content_css : [
						'//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
						'//www.tinymce.com/css/codepen.min.css' ]
			});
</script>
<script type="text/javascript"
	src="EditAppConfig.js?${applicationScope.built}"></script>

</head>
<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<ul class="nav nav-tabs">
			<li role="presentation" class="active"><a href="#">Home</a></li>
			<li role="presentation"><a href="#">Profile</a></li>
			<li role="presentation"><a href="#">Messages</a></li>
		</ul>
		<div>
			<a href="./EditUsers">管理公告人員</a>
		</div>
		<form id="form1" name="form1" method="post" action=""
			enctype="multipart/form-data">
			<div id="tabs-1">
				<table style="margin: 10px;">
					<tr style="padding: 10px;">
						<th>站名</th>
						<td style="padding: 10px;"><input name="title" type="text"
							value="${appConfig.title}" size="50" maxlength="50" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>Header</th>
						<td style="padding: 10px;"><input name="header" type="text"
							value="${appConfig.header}" size="50" maxlength="50" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>Page Size</th>
						<td style="padding: 10px;"><input name="pagesize" type="text"
							value="${appConfig.pagesize}" size="50" maxlength="50" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>預設 Login 的路徑</th>
						<td style="padding: 10px;"><input name="defaultlogin"
							type="text" value="${appConfig.defaultlogin}" size="50"
							maxlength="50" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>Google OAuth2 認證主機</th>
						<td style="padding: 10px;"><input name="authdomains"
							type="text" id="authdomains" value="${appConfig.authdomains}"
							size="100" maxlength="255" /></td>
					</tr>

					<tr style="padding: 10px;">
						<th>Google OAuth2 認證資訊</th>
						<td style="padding: 10px;">底下 3項資料請到 Google Developers
							Console: <a href="https://console.developers.google.com/project">https://console.developers.google.com/project</a>
							進行申請及設定。
						</td>
					</tr>
					<tr style="padding: 10px;">
						<th>clientid</th>
						<td style="padding: 10px;"><input name="client_id"
							type="text" size="100" value="${appConfig.client_id}"
							maxlength="255" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>client_secret</th>
						<td style="padding: 10px;"><input name="client_secret"
							type="text" value="${appConfig.client_secret}" size="100"
							maxlength="255" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>redirect_uri</th>
						<td style="padding: 10px;"><input name="redirect_uri"
							type="text" value="${appConfig.redirect_uri}" size="100"
							maxlength="255" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>允許發佈公告的來源 IP<br /> (以 CIDR表示，逗號隔開)
						</th>
						<td style="padding: 10px;"><input name="signinip" type="text"
							id="SigninIp" value="${appConfig.signinip}" size="50"
							maxlength="50" /></td>
					</tr>
					<tr style="padding: 10px;">
						<th>首頁公告</th>
						<td style="padding: 10px;"><textarea
								style="width: 80%; height: 20em;" name="announcement">${appConfig.announcement}</textarea></td>
					</tr>
					<tr style="padding: 10px;">
						<th>管理分類項目</th>
						<td style="padding: 10px;"><c:forEach var="tag"
								items="${appConfig.tags }">
							        ${tag.tagname}: ${tag.tagtitle}: ${tag.descript } : 修改 | 刪除<br />
							</c:forEach></td>
					</tr>
				</table>
			</div>
			<button type="submit" class="btn btn-success">儲存</button>
		</form>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>
