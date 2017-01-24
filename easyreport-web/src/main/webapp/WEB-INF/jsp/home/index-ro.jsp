<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>EasyReport-A simple and easy to use Web Report System</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/home/index-ro.js?v=${version}"></script>
</head>
<body ng-app="i18n" ng-controller="translate">
<div class="easyui-layout" fit="true">
    <div region="north" class="bg-header">
        <div class=""></div>
        <div class="menus"></div>
    </div>
    <div region="center">
        <div id="main-tabs" border="false" fit="true">
            <div title="{{info.index_page}}" data-options="iconCls:'icon-home'">
                <p style="font-size: 20px; padding: 20px">
                    {{info.index_hello}}<font color="red">${user.name}</font>
                    <input id="login-user-name" type="hidden" value="${user.name}"/>
                </p>
            </div>
        </div>
    </div>
    <div region="south" class="footer">
        <div id="footer-left">
            <div>EasyReport(v2.0)</div>
        </div>
        <div id="footer-right">Copyright © 2014-2016 easytoolsoft.com inc.</div>
    </div>
</div>
<!-- tabs right click menu  -->
<div id="main-tab-ctx-menu" class="easyui-menu" style="width: 120px;">
    <div id="m-current" data-options="name:'current',iconCls:'icon-cancel'">{{info.index_rkey_shutdown_curPage}}</div>
    <div id="m-others" data-options="name:'others',iconCls:''">{{info.index_rkey_shutdown_otherPage}}</div>
    <div id="m-all" data-options="name:'all',iconCls:''">{{info.index_rkey_shutdown_allPage}}</div>
</div>
<div id="my-profile-dlg" class="easyui-dialog" title="{{info.index_change_personalInfo}}">
    <table cellpadding="5" style="margin: 30px auto" class="form-table">
        <tr>
            <td>{{info.index_userInfo}}</td>
            <td colspan="3"><span class="name">${user.account}(${user.name})</span></td>
        </tr>
        <tr>
            <td>{{info.index_role}}</td>
            <td colspan="3"><span class="name">${roleNames}</span></td>
        </tr>
        <tr>
            <td>{{info.index_email}}</td>
            <td colspan="3"><span class="name">${user.email}</span></td>
        </tr>
        <tr>
            <td>{{info.index_phoneNum}}</td>
            <td colspan="3"><span class="name">${user.telephone}</span></td>
        </tr>
        <tr>
            <td>{{info.index_description}}</td>
            <td colspan="3"><span class="name">${user.comment}</span></td>
        </tr>
    </table>
</div>
<div id="change-my-pwd-dlg" class="easyui-dialog" title="{{info.index_change_password}}">
    <form id="change-my-pwd-form" name="my-profile-form" method="post">
        <table cellpadding="5" style="margin: 30px auto" class="form-table">
            <tr>
                <td>{{info.index_userInfo}}<input id="user-id" type="hidden" name="user-id" value="${user.id}"/></td>
                <td colspan="3"><span class="name">${user.account}(${user.name})</span></td>
            </tr>
            <tr>
                <td>{{info.index_role}}</td>
                <td colspan="3"><span class="name">${roleNames}</span></td>
            </tr>
            <tr>
                <td>{{info.index_originPass}}</td>
                <td colspan="3"><input class="easyui-textbox" type="password" name="oldPassword" id="oldPassword"
                                       data-options="required:true"
                                       style="width: 280px"/></td>
            </tr>
            <tr>
                <td>{{info.index_newPass}}</td>
                <td colspan="3"><input class="easyui-textbox" type="password" name="password" id="password"
                                       data-options="required:true,validType:{length:[6,20]}"
                                       style="width: 280px"/></td>
            </tr>
            <tr>
                <td>{{info.index_newPassConfirm}}</td>
                <td colspan="3"><input class="easyui-textbox" type="password" name="passwordRepeat"
                                       id="passwordRepeat"
                                       data-options="required:true,validType:{
							length:[6,20],
							equals:'#password'}" style="width: 280px"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
