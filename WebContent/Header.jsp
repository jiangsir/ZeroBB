<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="user" uri="/WEB-INF/user.tld"%>
<%@ page isELIgnored="false"%>
<div id="header">
	<div id="logo">
		<a href="./">${applicationScope.appConfig.title}</a>
	</div>
	<div id="slogan"></div>
</div>
<div id="header_menu">
	<a href="./InsertArticle">發布公告</a> &nbsp; | &nbsp; <a href="./">有效公告</a>
	&nbsp; | &nbsp; <a href="./History">歷史公告</a> &nbsp; | &nbsp; <a
		href="http://www.nknush.kh.edu.tw/">學校首頁</a> &nbsp; | &nbsp;
	<form name="form1" method="get" action="./Search"
		style="margin: 0px; display: inline;" onsubmit="checkForm(this);">
		搜尋關鍵字：<input name="keyword" type="text" size="20" />
	</form>
	<c:if test="${!user:isNullUser(sessionScope.currentUser)}">
		<c:if test="${user:isAdmin(sessionScope.currentUser)}"> | <a
				href="./EditAppConfig">管理頁</a>
		</c:if> | &nbsp; <a href="./Logout">離開</a>
	</c:if>
	<br></br>
</div>

