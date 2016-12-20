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
			jQuery("button[id='addupfile']").click(
					function() {
						jQuery("div[id=upfilelist]:last").clone(true)
								.insertAfter("div[id=upfilelist]:last");
						jQuery("div[id=upfilelist]:last #upfile").val("");
						jQuery("div[id=upfilelist]:last").show();
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
				var size = jQuery("div[id='upfilelist']").size();
				if (size > 1) {
					jQuery("div[id='upfilelist']:eq(" + index + ")").remove();
				}
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

			$("input[name='submit']").bind("click", function() {
				console.log($("textarea[name=content]").val());
				console.log($("input[name=title]").val());
//				var myFormData = new FormData($('#form')[0]);
//				myFormData
				jQuery.ajax({
					type : "POST",
					url : "UpdateArticle",
					// data:
					// $('#form').serialize()+"&picture="+$('input[type="file"]').val(),
					cache : false,
					data : new FormData($('#form')[0]),
					processData : false,
					contentType : false,
					async : true,
					timeout : 5000,
					success : function(result) {
						console.log(result);
					},
					error : function(jqXHR, textStatus, errorThrown) {
						if (jqXHR.responseText !== '') {
							errorjson = jQuery.parseJSON(jqXHR.responseText);
						} else {
							errorjson = errorThrown;
						}
						console.log(jqXHR.responseText);
						console.log(errorThrown);
						console.log(textStatus);
						BootstrapDialog.alert(errorjson.list);
					}
				});

			});
		});
