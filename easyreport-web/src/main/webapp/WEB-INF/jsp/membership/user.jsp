<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户管理</title>
#parse("layout/header.vm")
<script src="$!{ctx.path}/assets/js/membership/user.js?ver=$!{ctx.ver}"></script>
</head>
<body class="easyui-layout">
	<div id="toolbar" class="toolbar">
		属性:<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 100px">
			<option value="account">账号</option>
			<option value="name">姓名</option>
		</select> 关键字:<input class="easyui-textbox" type="text" id="keyword" name="keyword" /> 
		<a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search"> 搜索 </a> 
		<input id="modal-action" type="hidden" name="action" value="" />
	</div>
	<div style="height: 93%; padding: 2px">
		<div id="user-datagrid"></div>
	</div>
	<div id="add-user-dlg" title="添加用户">
		<form id="add-form" name="add-form" method="post">
			<center>
				<table cellpadding="5" style="margin: 30px auto" class="form-table">
					<tr>
						<td>用户账号:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" id="account" name="account"
							data-options="required:true" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>登陆密码:</td>
						<td colspan="3"><input class="easyui-textbox" type="password" id="password" name="password"
							data-options="required:true,validType:{length:[6,20]}" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>确认密码:</td>
						<td colspan="3"><input class="easyui-textbox" type="password" id="repassword" name="repassword"
							data-options="required:true,validType:{
							length:[6,20],
							equals:'#password'}" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>真实姓名:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" id="name" name="name" data-options="required:true"
							style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>电话:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" name="telephone" id="telephone"
							data-options="required:true" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" name="email" id="email"
							data-options="required:true" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>状态:</td>
						<td colspan="3">
						<select class="easyui-combobox" id="status" name="status" style="width: 100px">
								<option selected="selected" value="1">启用</option>
								<option value="0">禁用</option>
							</select>
							</td>
					</tr>
					<tr>
						<td>所属角色:</td>
						<td colspan="3"><select class="easyui-combobox"  id="add-combox-roles" name="comboxRoles" 
						data-options="valueField:'value',textField:'name',multiple:true" style="width: 380px"></select>
							<input id="add-roles" type="hidden" name="roles" />
						</td>
					</tr>
					<tr>
						<td>备注:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="comment"  style="width: 380px"></input></td>
					</tr>
				</table>
			</center>
		</form>
	</div>
	<div id="edit-user-dlg"  title="编辑用户信息">
		<form id="edit-form" name="edit-form" method="post">
			<center>
				<table cellpadding="5" style="margin: 30px auto" class="form-table">
					<tr>
						<td>用户账号:</td>
						<td colspan="3"><span id="edit-account" class="name"></span>
							<input id="edit-userId" type="hidden" name="userId" />
						</td>
					</tr>
					<tr>
						<td>真实姓名:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" id="edit-name" name="name" data-options="required:true"
							style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>电话:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" name="telephone" id="edit-telephone"
							data-options="required:true" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" name="email" id="edit-email"
							data-options="required:true" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>状态:</td>
						<td colspan="3"><label class="input"> <select class="easyui-combobox" id="edit-status" name="status" style="width: 100px">
								<option selected="selected" value="1">启用</option>
								<option value="0">禁用</option>
							</select>
						</label></td>
					</tr>
					<tr>
						<td>所属角色:</td>
						<td colspan="3"><label class="input"> <select class="easyui-combobox"  id="edit-combox-roles" name="comboxRoles" 
						 data-options="valueField:'value',textField:'name',multiple:true" style="width: 380px"></select>
							<input id="edit-roles" type="hidden" name="roles" />
						</label></td>
					</tr>
					<tr>
						<td>备注:</td>
						<td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="edit-comment" style="width: 380px"></input></td>
					</tr>
				</table>
			</center>
		</form>
	</div>
	<div id="reset-pwd-dlg" title="修改用户密码">
		<form id="reset-pwd-form" name="reset-pwd-form" method="post">
			<center>
				<table cellpadding="5" style="margin: 30px auto" class="form-table">
					<tr>
						<td>用户账号:</td>
						<td colspan="3">
						<span id="reset-account" class="name"></span>
						<input id="reset-userId" type="hidden" name="userId" /></td>
					</tr>
					<tr>
						<td>新密码:</td>
						<td colspan="3"><input class="easyui-textbox" type="password" id="reset-password" name="password"
							data-options="required:true,validType:{length:[6,20]}" style="width: 380px"></input></td>
					</tr>
					<tr>
						<td>确认新密码:</td>
						<td colspan="3"><input class="easyui-textbox" type="password" id="reset-repassword" name="repassword"
							data-options="required:true,validType:{
							length:[6,20],
							equals:'#reset-password'}" style="width: 380px"></input></td>
					</tr>
				</table>
			</center>
		</form>
	</div>
</body>
</html>