<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<link rel="stylesheet" type="text/css" media="screen" href="style.css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$("input:first").focus();
		$("input[type=submit], [type='button']").button();
		$("button").button().click(function(event) {
			event.preventDefault(); // 讓預設的動作失效！
		});
		$(".closethick").button({
			icons : {
				primary : "ui-icon-closethick"
			},
			text : false
		});

		$("#menu").menu({
			position : {
				at : "left bottom"
			}
		});
		jQuery("#tabs").tabs({
			collapsible : false
		});
	});
</script>

<meta charset="UTF-8">
<title>${applicationScope.appConfig.title}</title>

