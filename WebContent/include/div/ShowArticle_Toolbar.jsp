<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>



<c:if test="${article.isUpdatable(sessionScope.currentUser)}">
	<div class="btn-group">
		<button type="button" class="btn btn-default btn-xs">
			<span class="glyphicon glyphicon-hand-up" id="touch"
				articleid="${article.id}" title="碰一下，將排序在最上方"
				style="cursor: pointer;"></span>
		</button>
		<button type="button" class="btn btn-default btn-xs">
			<a href="./UpdateArticle?id=${article.id}"><span
				class="glyphicon glyphicon-pencil"></span></a>
		</button>
		<button type="button" class="btn btn-default btn-xs">
			<a href="./DeleteArticle.api?articleid=${article.id}"><span
				class="glyphicon glyphicon-remove"></span></a>
		</button>
	</div>
</c:if>

