// JavaScript Document
jQuery(document).ready(function() {
	jQuery("span[id='uploadimage']").click(function() {
		var $dialog = $("#upload_dialog").dialog({
			autoOpen : false,
			width : '60%',
			title : 'Upload Image',
			buttons : {
				"取消" : function() {
					$(this).dialog("close");
				},
				"上傳" : function() {
					$(this).dialog("close");
					UploadImage();
				}
			}
		});
		$dialog.dialog('open');
		return false;
	});

	$("#tabs").tabs({
		select : function(event, ui) {
			var url = $.data(ui.tab, 'load.tabs');
			if (url) {
				location.href = url;
				return false;
			}
			return true;
		}
	});

	jQuery("#SystemClosedMessage").focus(function() {
		$("#SYSTEM_CLOSE").attr("checked", true);
	});

	$("button[type='submit']").bind("click", function(e) {
		e.preventDefault();
		var form = $(this).closest("form");
		jQuery.ajax({
			type : "POST",
			url : "EditAppConfig",
			// data: form.serialize(),
			// data : form.serializeArray(),
			data : new FormData(form[0]),
			cache : false,
			processData : false,
			contentType : false,
			async : true,
			timeout : 5000,
			success : function(result) {
				console.log(result);
				// window.location.href = document.referrer; // 跳轉到前一頁，並 reload
				location.reload(); // 本頁重讀。
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("jqXHR.responseText=" + jqXHR.responseText);
				console.log("errorThrown=" + errorThrown);
				console.log("textStatus=" + textStatus);
				try {
					alert = jQuery.parseJSON(jqXHR.responseText);
					// BootstrapDialog.alert(alert.title);
					BootstrapDialog.show({
						title : alert.type,
						message : alert.title,
						buttons : [ {
							id : 'btn-ok',
							icon : 'glyphicon glyphicon-check',
							label : 'OK',
							cssClass : 'btn-primary',
							autospin : false,
							action : function(dialogRef) {
								dialogRef.close();
							}
						} ]
					});
				} catch (err) {
					BootstrapDialog.alert(errorThrown);
				}
			}
		});
	});

});

function UploadImage() {
	jQuery.ajax({
		type : "POST",
		url : "./UploadImage",
		data : $('#form').serialize(),
		async : false,
		timeout : 5000,
		success : function(result) {
			// alert(result);
			var json = JSON.parse(result);
			jQuery("#Testjudge_dialog3 #Testjudge_htmlstatus").text(
					json.htmlstatus);
			jQuery("#Testjudge_dialog3 #Testjudge_result").text(json.result);
			var $dialog3 = $("#Testjudge_dialog3").dialog({
				autoOpen : true,
				width : '60%',
				title : 'Result',
				close : function(event, ui) {
					location.reload();
				},
				buttons : {
					"返回" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
	});
}
