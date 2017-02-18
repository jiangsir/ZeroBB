<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<fmt:setLocale value="${sessionScope.session_locale}" />
<fmt:setBundle basename="resource" />

<div class="modal fade" id="Modal_EditUser" data-focus-on="input:first"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">編輯使用者資訊</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<input id="userid" name="userid" type="hidden"
						value="${sessionScope.currentUser.id}" />
					<div class="form-group">
						<label for="tname" class="col-sm-3 control-label">原密碼：</label>
						<div class="col-sm-9">
							<input type="password" class="form-control" id="tname"
								name="oldpasswd" placeholder="請輸入原密碼" value="">
						</div>
					</div>
					<div class="form-group">
						<label for="tname" class="col-sm-3 control-label">新密碼：</label>
						<div class="col-sm-9">
							<input type="password" class="form-control" name="newpasswd"
								placeholder="請輸入新密碼" value="">
						</div>
					</div>
					<div class="form-group">
						<label for="tname" class="col-sm-3 control-label">再一次輸入新密碼：</label>
						<div class="col-sm-9">
							<input type="password" class="form-control" name="newpasswd2"
								placeholder="再次輸入新密碼" value="">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					id="modal_EditUser_save" data-action="${param.action}">儲存</button>
			</div>
		</div>
	</div>
</div>
