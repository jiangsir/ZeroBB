<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="user" uri="/WEB-INF/user.tld"%>
<%@ page isELIgnored="false"%>
<div class=container>
	<div class=jumbotron>
		<h1>
			<a href="./">${applicationScope.appConfig.title}</a>
		</h1>
	</div>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="./InsertArticle">發布公告</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="./">有效公告</a></li>
					<li><a href="./History">歷史公告</a></li>
					<li><a href="http://www.nknush.kh.edu.tw/">學校首頁</a></li>
				</ul>
				<form name="form1" class="navbar-form navbar-left" role="search"
					method="get" action="./Search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="搜尋關鍵字"
							name="keyword">
					</div>
					<button type="submit" class="btn btn-default">查詢</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${!user:isNullUser(sessionScope.currentUser)}">
						<c:if test="${user:isAdmin(sessionScope.currentUser)}">
							<li><a href="./EditAppConfig">管理頁</a></li>
						</c:if>
						<li><a href="./Logout">離開</a></li>
					</c:if>
				</ul>
				<ul class="nav navbar-nav navbar-right">

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">分類公告
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<c:forEach var="tag" items="${applicationScope.tags}">
								<li><a href="?tagname=${tag.tagname}"
									title="${tag.descript}">${tag.tagtitle }</a></li>
							</c:forEach>
						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">查詢公告單位
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<c:forEach var="division" items="${applicationScope.divisions}">
								<li><a href="./?division=${division.key}">${division.value}</a></li>
							</c:forEach>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

</div>



