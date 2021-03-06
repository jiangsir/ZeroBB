<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>

<jsp:include page="CommonHead.jsp" />

<!-- <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
 -->
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

<!-- <link href="jscripts/datetimepicker/jquery-ui-timepicker-addon.css"
	rel="stylesheet" />
<script src="jscripts/datetimepicker/jquery-ui-timepicker-addon.js"></script>
 -->
<script type="text/javascript"
	src="InsertArticle.js?${applicationScope.built}"></script>
</head>

<body>
	<jsp:include page="Header.jsp" />
	<div class="container">
		<form id="form" action="" enctype="multipart/form-data" method="post">
			<p>重要性：</p>
			<div id="info">
				<c:if test="${sessionScope.currentUser.headline==true }">
					<input name="info" type="radio" value="HEADLINE"
						info="${article.info}" /> 頭條
					※若設定成頭條，該公告將以顯著字體出現在「頭條消息」區塊中，直到有效期限為止。為免頭條消息過多而雜亂，請小心使用，謝謝。  <br />
				</c:if>
				<input name="info" type="radio" value="IMPORTANT"
					info="${article.info}" /> 重要 ※若設定成重要，該公告就會直接固定顯示在上層，直到有效期限為止。  <br />
				<input name="info" type="radio" value="STANDARD" checked="checked"
					info="${article.info}" /> 一般 ※一般性的公告訊息 <br /> <br />
				請選擇文章分類：(可多選) <br /> <br /> <span>文章分類說明：為了使校外人士，比如家長。能更容易找到學校公告訊息，因此增加了文章分類的選項。<br />請於公告時選取文章分類，可多選。<br />若有新的分類需求請告知資訊組
					555。謝謝。另外，系統右上方能夠對公告標題及內容進行關鍵字搜尋，可多加利用。
				</span><br /> <br />
			</div>
			<c:forEach var="tag" items="${tags}">
				<c:set var="checkedTag" value="" />
				<c:forEach var="article_tag" items="${article.tags}">
					<c:if test="${tag.tagname == article_tag.tagname}">
						<c:set var="checkedTag" value="checked" />
					</c:if>
				</c:forEach>
				<input type="checkbox" name="tagname" value="${tag.tagname}"
					${checkedTag} />
                            ${tag.tagtitle}: ${tag.descript }
                            <br />
			</c:forEach>
			<br /> <br />
			<div style="display: none;">
				屬性: <input name="type" type="text" value="${article.type}" /> <br />
				超連結: <input type="text" name="hyperlink"
					value="${article.hyperlink}" /> <br />
			</div>

			<div>
				標題: <input name="title" type="text" value="${article.title}"
					size="80" />
			</div>
			<div>
				<%-- 				有效日期： 從 <input name="postdate" type="text" id="postdate"
					value="${article.postdate}" /> 到 <input name="outdate" type="text"
					id="outdate" value="${article.outdate}" />

 --%>

				有效日期：
				<div class="row">
					<div class='col-sm-6'>
						<div class="form-group">
							<div class='input-group date' id='datetimepicker1'>
								<span class="input-group-addon" id="sizing-addon1">從</span> <input
									type='text' class="form-control" name="postdate"
									value="${article.postdate}" /> <span class="input-group-addon">
									<span class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<div class='col-sm-6'>
						<div class="form-group">
							<div class='input-group date' id='datetimepicker2'>
								<span class="input-group-addon" id="sizing-addon1">到</span> <input
									type='text' class="form-control" name="outdate"
									value="${article.outdate}" /> <span class="input-group-addon">
									<span class="glyphicon glyphicon-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<script type="text/javascript">
						$(function() {
							$('#datetimepicker1').datetimepicker({
								format : 'YYYY-MM-DD HH:mm:ss'
							});
							$('#datetimepicker2').datetimepicker({
								format : 'YYYY-MM-DD HH:mm:ss'
							});
						});
					</script>
				</div>

			</div>
			<div>
				內容： <br />
				<textarea rows="20" name="content">${article.content}</textarea>
			</div>
			<br /> * 檔案上載上限 ${ maxFileSize/1024/1024} MB <br />
			<c:forEach var="upfile" items="${article.upfiles}">
				<h3 id="upfiles">
					<span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
					<a href="./Download?upfileid=${upfile.id}"> ${upfile.filename}
					</a> <span style="font-size: 10px"> <input name="upfileid"
						type="hidden" value="${upfile.id}" /> -- <span id="deleteupfile"
						name="deleteupfile" upfileid="${upfile.id}"
						style="text-decoration: underline; cursor: pointer;"> <img
							src="images/delete18.png" title="刪除" />
					</span>
					</span>
				</h3>
			</c:forEach>

			<div class="input-group" id="upfilelist">
				<span class="input-group-addon">附加檔案</span> <input
					class="form-control" name="upfile" type="file" /> <span
					class="input-group-addon" id="cancelupfile"><span
					class="glyphicon glyphicon-remove"></span></span>
			</div>
			<%-- 			<div id="upfilelist" name="upfilelist" style="display:${display};">
				附加檔案： <input id="upfile" name="upfile" type="file" /> <span
					id="cancelupfile" name="cancelupfile"
					style="text-decoration: underline; cursor: pointer;"> 移除 </span> <br />
			</div>
 --%>

			<button type="button" class="btn btn-default" id="addupfile">
				<span class="glyphicon glyphicon-plus"></span> 增加附件
			</button>
			<input type="hidden" name="id" value="${article.id}" /> <br /> <br />
			<br />
			<button type="submit" class="btn btn-success"
				onclick="tinyMCE.triggerSave(true,true);">送出</button>
			<br />
			<!-- submit button 要加上一個 onclick="tinyMCE.triggerSave(true,true) 才會對，否則永遠只會抓到舊 textarea 資料。" -->
		</form>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>