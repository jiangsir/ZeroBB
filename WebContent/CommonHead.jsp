<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!-- <link rel="stylesheet" type="text/css" media="screen" href="style.css" />
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
 -->
<!-- 最新編譯和最佳化的 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- 選擇性佈景主題 -->
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
 -->
<link href="jscripts/bootstrap-flat-3.3.4-dist/bootstrap-flat.min.css"
	rel="stylesheet">
<link
	href="jscripts/bootstrap-flat-3.3.4-dist/bootstrap-flat-extras.min.css"
	rel="stylesheet">
<link href="jscripts/bootstrap-flat-3.3.4-dist/docs.min.css"
	rel="stylesheet">
<link href="jscripts/bootstrap-flat-3.3.4-dist/docs-flat.css"
	rel="stylesheet">

<script
	src=https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js></script>

<!-- 最新編譯和最佳化的 JavaScript -->
<!-- <link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
 -->
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/css/bootstrap-dialog.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/js/bootstrap-dialog.min.js"></script>

<script type="text/javascript"
	src="include/Modals/Modal_EditUser.js?${applicationScope.built }"></script>

<!-- 引入 bootstrap-datetimepicker {-->
<script type="text/javascript" src="jscripts/moment/moment.js"></script>
<!-- <script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/locale/zh-tw.js"></script>
 -->
<script type="text/javascript"
	src="jscripts/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet"
	href="jscripts/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css" />
<!-- 引入 bootstrap-datetimepicker }-->

<link href="css/navbar.css" rel=stylesheet>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$("input:first").focus();
	});
	jQuery(document).ajaxError(function(event, jqxhr, settings, thrownError) {
		//BootstrapDialog.alert(thrownError);
		console.log(".ajaxError 進行全域捕捉 error:");
		console.log("event=" + event);
		console.log("jqxhr.responseText=" + jqxhr.responseText);
		console.log("settings.url=" + settings.url);
		console.log("thrownError=" + thrownError);
	});
</script>

<meta charset="UTF-8">
<title>${applicationScope.appConfig.title}</title>

