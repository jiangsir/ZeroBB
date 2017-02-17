jQuery(document).ready(function() {
	$('button#modal_EditUser_save').on('click', function() {
		var modal = $(this).closest('.modal');
		var action = $(this).data('action');
		console.log("action=" + action);
		console.log("form=" + modal.find("form").serialize());

		jQuery.ajax({
			type : "POST",
			url : "./EditUser?action=" + action,
			data : modal.find("form").serialize(),
			async : false,
			timeout : 5000,
			success : function(result) {
				modal.modal('hide');
				//location.reload();
			},
			error : function(jqXHR, textStatus, errorThrown) {
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