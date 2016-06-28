<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="CommonHead.jsp" />
<script type="text/javascript"
	src="EditAppConfig.js?${applicationScope.built}"></script>
<script type="text/javascript" src="jscripts/tinymce/tinymce.min.js"></script>
<!-- 使用 TinyMCE  -->
<script type="text/javascript">
	tinymce
			.init({
				language : "zh_TW",
				selector : ".mceAdvanced",
				theme : "modern",
				plugins : [
						"advlist autolink link image lists charmap print preview hr anchor pagebreak spellchecker",
						"searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
						"save table contextmenu directionality emoticons template paste textcolor" ],
				toolbar : "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons",

			});
	tinymce
			.init({
				language : "zh_TW",
				selector : ".mceSimple",
				plugins : "colorpicker textcolor",
				toolbar : "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | forecolor backcolor emoticons",
			});
</script>

</head>
<body>
	<div id="container">
		<jsp:include page="Header.jsp" />
		<div id="main">
			<div id="text">
				<div>
					<a href="./EditUsers">管理公告人員</a>
				</div>
				<form id="form1" name="form1" method="post" action="">
					<div id="tabs-1">
						<table align="center" style="margin: 10px;">
							<tr style="padding: 10px;">
								<th>站名</th>
								<td style="padding: 10px;"><input name="Title" type="text"
									id="Title" value="${appConfig.title}" size="50" maxlength="50" /></td>
							</tr>
							<tr style="padding: 10px;">
								<th>Header</th>
								<td style="padding: 10px;"><input name="Header" type="text"
									value="${appConfig.header}" size="50" maxlength="50" /></td>
							</tr>
							<tr style="padding: 10px;">
								<th>Page Size</th>
								<td style="padding: 10px;"><input name="Pagesize"
									type="text" value="${appConfig.pagesize}" size="50"
									maxlength="50" /></td>
							</tr>
							<tr style="padding: 10px;">
								<th>預設 Login 的路徑</th>
								<td style="padding: 10px;"><input name="Defaultlogin"
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
									Console: <a
									href="https://console.developers.google.com/project">https://console.developers.google.com/project</a>
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
								<td style="padding: 10px;"><input name="signinip"
									type="text" id="SigninIp" value="${appConfig.signinip}"
									size="50" maxlength="50" /></td>
							</tr>
							<tr style="padding: 10px;">
								<th>首頁說明</th>
								<td style="padding: 10px;"><textarea
										style="width: 80%; height: 20em;" name="announcement"
										class="mceSimple">${appConfig.announcement}</textarea></td>
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
					<input type="submit" value="送出" />
				</form>
			</div>
		</div>
		<jsp:include page="Footer.jsp" />
	</div>
</body>
</html>
