<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>User</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/membership/user.js?v=${version}"></script>
</head>
<body class="easyui-layout" ng-app="i18n" ng-controller="translate">
<div id="toolbar" class="toolbar">
    {{info.user_proerties}}<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 100px">
    <option value="account">{{info.user_account}}</option>
    <option value="name">{{info.user_name}}</option>
</select>{{info.user_keyword}}<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
    <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search">{{info.user_search}}</a>
    <input id="modal-action" type="hidden" name="action" value=""/>
</div>
<div style="height: 93%; padding: 2px">
    <div id="user-datagrid"></div>
</div>
<div id="user-dlg">
    <form id="user-form" name="user-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr>
                    <td>{{info.user_account}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="account" name="account"
                                           data-options="required:true" style="width: 380px"/>
                        <input id="userId" type="hidden" name="id"/>
                    </td>
                </tr>
                <tr>
                    <td>{{info.user_password}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="password" id="password" name="password"
                                           data-options="required:true,validType:{length:[6,20]}"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.user_confirm_password}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="password" id="repassword" name="repassword"
                                           data-options="required:true,validType:{
							length:[6,20],
							equals:'#password'}" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.user_real_name}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="name" name="name"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.user_phonenum}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="telephone" id="telephone"
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.user_email}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="email" id="email"
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.user_status}}</td>
                    <td colspan="3">
                        <select class="easyui-combobox" id="status" name="status" style="width: 100px">
                            <option selected="selected" value="1">{{info.user_status_enable}}</option>
                            <option value="0">{{info.user_status_disable}}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>{{info.user_owned_roles}}</td>
                    <td colspan="3"><select class="easyui-combobox" id="combox-roles" name="comboxRoles"
                                            data-options="valueField:'value',textField:'name',multiple:true"
                                            style="width: 380px"></select>
                        <input id="roles" type="hidden" name="roles"/>
                    </td>
                </tr>
                <tr>
                    <td>{{info.user_description}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="comment"
                                           style="width: 380px"/></td>
                </tr>
            </table>
        </center>
    </form>
</div>
<div id="reset-pwd-dlg" title="{{info.user_change_password}}">
    <form id="reset-pwd-form" name="reset-pwd-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr>
                    <td>{{info.user_account}}</td>
                    <td colspan="3">
                        <span id="reset-account" class="name"></span>
                        <input id="reset-userId" type="hidden" name="userId"/></td>
                </tr>
                <tr>
                    <td>{{info.user_new_password}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="password" id="reset-password" name="password"
                                           data-options="required:true,validType:{length:[6,20]}"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.user_confirm_new_password}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="password" id="reset-repassword"
                                           name="repassword"
                                           data-options="required:true,validType:{
							length:[6,20],
							equals:'#reset-password'}" style="width: 380px"/></td>
                </tr>
            </table>
        </center>
    </form>
</div>
</body>
</html>