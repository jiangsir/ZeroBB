<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>



<c:if test="${article.isUpdatable(sessionScope.currentUser)}">
	<div class="btn-group">
		<div type="button" class="btn btn-default btn-xs">
			<div class="glyphicon glyphicon-hand-up" title="碰一下，將排序在最上方"
				style="cursor: pointer;" name="touch" articleid="${article.id}"></div>
		</div>
		<div type="button" class="btn btn-default btn-xs">
			<a href="./UpdateArticle?id=${article.id}"><span
				class="glyphicon glyphicon-pencil"></span></a>
		</div>
		<div type="button" class="btn btn-default btn-xs">
			<a href="./DeleteArticle.api?articleid=${article.id}"><span
				class="glyphicon glyphicon-remove"></span></a>
		</div>
	</div>
</c:if>

