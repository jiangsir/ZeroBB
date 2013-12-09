<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<jsp:useBean id="userBean" class="jiangsir.zerobb.Beans.UserBean"/>
<ul>
<c:forEach var="article" items="${articles}" varStatus="varstatus">
  <jsp:setProperty name="userBean" property="account" value="${article.account}" />
  <div style="font-size:1em;">
  <li><a href="http://${pageContext.request.localAddr}:${pageContext.request.localPort}${pageContext.servletContext.contextPath}/ShowArticle?id=${article.id}" target="_blank" style="">${article.title}</a></li>  
  </div>
</c:forEach><br />
<div style="text-align:right"><a href="http://${pageContext.request.localAddr}:${pageContext.request.localPort}${pageContext.servletContext.contextPath}/?${pageContext.request.queryString}" target="_blank">更多...</a></div>
</ul>


