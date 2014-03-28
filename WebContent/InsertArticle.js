jQuery(document).ready(
		function() {
			$("input[name='postdate']").datetimepicker({
				dateFormat : 'yy-mm-dd',
				timeFormat : 'HH:mm:ss'
			});
			$("input[name='outdate']").datetimepicker({
				dateFormat : 'yy-mm-dd',
				timeFormat : 'HH:mm:ss'
			});

			// jQuery("input[name=postdate]").val(
			// jQuery.trim(jQuery("input[name=postdate]").val()));
			// jQuery("input[name=outdate]").val(
			// jQuery.trim(jQuery("input[name=outdate]").val()));
			jQuery("#addupfile").click(
					function() {
						jQuery("div[name=upfilelist]:last").clone(true)
								.insertAfter("div[name=upfilelist]:last");
						jQuery("div[name=upfilelist]:last #upfile").val("");
						jQuery("div[name=upfilelist]:last").show();
					}); // #addherb.click

			jQuery("span[id='deleteupfile']").bind('click', function() {
				var upfileid = $(this).attr("upfileid");
				$(this).parent().parent().hide();

				// jQuery("h3[id='upfiles']:eq("+index+")").hide("slow");
				// var index = jQuery("span[id='deleteupfile']").index(this);
				// var upfileid =
				// jQuery(":input[name=upfileid]:eq("+index+")").val();
				// jQuery("h3[id='upfiles']:eq("+index+")").hide("slow");
				// alert("removeupfile upfileid="+upfileid);
				jQuery.ajax({
					type : "GET",
					url : "./DeleteUpfile.api",
					data : "upfileid=" + upfileid,
					async : true,
					timeout : 5000
				}); // jQusery ajax

			});

			jQuery("span[id='cancelupfile']").bind('click', function() {
				var index = jQuery("span[id='cancelupfile']").index(this);
				jQuery("div[id='upfilelist']:eq(" + index + ")").remove();
			});

			jQuery("input[name='info']").each(function() {
				if ($(this).attr("info") == $(this).val()) {
					$(this).attr("checked", true);
				}
			});

			$("input[id='Submit']").bind("click", function() { // 事件發生
				jQuery.ajax({
					type : "GET",
					url : "api/CheckInsertArticle.txt",
					// data:
					// $('#form').serialize()+"&picture="+$('input[type="file"]').val(),
					data : $('#form').serialize(),
					async : false,
					timeout : 5000,
					success : function(result) {
						if (result == "") {
							$("#form").submit();
						} else {
							alert("[" + result + "]");
						}
						// checkUser_Exception = result;
					}
				});
			});
		});