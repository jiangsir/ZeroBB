jQuery(document).ready(function() {
	jQuery("span[id='touch']").bind('click', function() {
		// jQuery("#postdate").text(mytime(parseInt(${now.time})));
		var articleid = $(this).attr("articleid");
		jQuery.ajax({
			type : "GET",
			url : "./Touch.api",
			data : "articleid=" + articleid,
			async : false,
			timeout : 5000,
			success : function(result) {
				if (result == "" || result == null) {
					result = "";
				}
				// jQuery("#postdate").text(result);
				// alert("順序調整成功！ (" + result + ")");
				BootstrapDialog.alert("順序調整成功！ (" + articleid + ")");
			}
		}); // jQusery ajax
	});

});
