<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${initParam.TITLE}</title>
<jsp:include page="CommonHead.jsp" />

<script language="javascript" type="text/javascript"
	src="./jscripts/tiny_mce/tiny_mce.js"></script>

<script language="javascript" type="text/javascript">
	tinyMCE
			.init({
				mode : "exact",
				elements : "content",
				theme : "advanced",
				plugins : "table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,zoom,flash,searchreplace,print,contextmenu",
				//theme_advanced_buttons1_add_before : "save,separator",
				theme_advanced_buttons1_add_before : "separator",
				theme_advanced_buttons1_add : "fontselect,fontsizeselect",
				theme_advanced_buttons2_add : "separator,insertdate,inserttime,preview,zoom,separator,forecolor,backcolor",
				theme_advanced_buttons2_add_before : "cut,copy,paste,separator,search,replace,separator",
				theme_advanced_buttons3_add_before : "tablecontrols,separator",
				theme_advanced_buttons3_add : "emotions,iespell,flash,advhr,separator,print",
				theme_advanced_toolbar_location : "top",
				theme_advanced_toolbar_align : "left",
				theme_advanced_path_location : "bottom",
				plugin_insertdate_dateFormat : "%Y-%m-%d",
				plugin_insertdate_timeFormat : "%H:%M:%S",
				extended_valid_elements : "a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]",
				external_link_list_url : "example_data/example_link_list.js",
				external_image_list_url : "example_data/example_image_list.js",
				flash_external_list_url : "example_data/example_flash_list.js",
				language : "zh_tw_utf8"
			})
</script>

<link href="jscripts/datetimepicker/jquery-ui-timepicker-addon.css"
	rel="stylesheet" />
<script src="jscripts/datetimepicker/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="InsertArticle.js"></script>
</head>

<body>
	<div id="container">
		<!-- header -->
		<jsp:include page="Header.jsp" />
		<!--end header -->
		<!-- main -->
		<div id="main">
			<div id="text">
				<form id="form" action="" enctype="multipart/form-data"
					method="post">
					<p>重要性：</p>
					<c:if test="${sessionScope.currentUser.headline==true }">
						<input name="info" type="radio" value="頭條" /> 頭條
					※若設定成頭條，該公告將以顯著字體出現在「頭條消息」區塊中，直到有效期限為止。為免頭條消息過多而雜亂，請小心使用，謝謝。  <br />
					</c:if>
					<input name="info" type="radio" value="重要" /> 重要
					※若設定成重要，該公告就會直接固定顯示在上層，直到有效期限為止。  <br /> <input name="info"
						type="radio" value="一般" checked="checked" /> 一般 ※一般性的公告訊息 <br />
					<br /> 請選擇文章分類：(可多選) <br /> <br /> <span>文章分類說明：為了使校外人士，比如家長。能更容易找到學校公告訊息，因此增加了文章分類的選項。<br />請於公告時選取文章分類，可多選。<br />若有新的分類需求請告知資訊組
						555。謝謝。另外，系統右上方能夠對公告標題及內容進行關鍵字搜尋，可多加利用。
					</span><br /> <br />

					<c:forEach var="tag" items="${tags}">
						<c:set var="checkedTag" value="" />
						<c:forEach var="article_tag" items="${article_tags}">
							<c:if test="${tag.tagname == article_tag}">
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
						有效日期： 從 <input name="postdate" type="text" id="postdate"
							value="${article.postdate}" /> 到 <input name="outdate"
							type="text" id="outdate" value="${article.outdate}" />
					</div>
					<div>
						內容： <br />
						<textarea name="content" cols="80" rows="20" id="content">
                            ${article.content}
                          </textarea>
					</div>
					<br />
					<jsp:useBean id="upfileBean"
						class="jiangsir.zerobb.Beans.UpfileBean" />
					* 檔案上載上限 ${upfileBean.MAX_FILESIZE/1000000} MB <br />
					<c:forEach var="upfile" items="${article.upfiles}">
						<h3 id="upfiles">
							<img src="images/paperclip.png" /> <a
								href="./Download?upfileid=${upfile.id}"> ${upfile.filename}
							</a> <span style="font-size: 10px"> <input name="upfileid"
								type="hidden" value="${upfile.id}" /> -- <span
								id="deleteupfile" name="deleteupfile" upfileid="${upfile.id}"
								style="text-decoration: underline; cursor: pointer;"> <img
									src="images/delete18.png" title="刪除" />
							</span>
							</span>
						</h3>
					</c:forEach>

					<div id="upfilelist" name="upfilelist" style="display:${display};">
						附加檔案： <input id="upfile" name="upfile" type="file" /> <span
							id="cancelupfile" name="cancelupfile"
							style="text-decoration: underline; cursor: pointer;"> 移除 </span>
						<br />
					</div>
					<span id="addupfile" style="text-decoration: underline;">
						更多... </span> <input name="articleid" type="hidden" value="${article.id}" />
					<br /> <br /> <br /> <input type="submit" value="送出" /> <br />
				</form>
			</div>
		</div>
		<!-- end main -->
		<!-- footer -->
		<jsp:include page="Footer.jsp" />
		<!-- end footer -->
	</div>
</body>
</html>