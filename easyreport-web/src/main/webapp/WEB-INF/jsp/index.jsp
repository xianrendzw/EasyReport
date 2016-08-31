<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>EasyReport-A simple and easy to use Web Report System</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/xframejs.jsp" %>
    <script src="<%=request.getContextPath()%>/assets/js/index.js?v=<%=Math.random()%>"></script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div region="north" class="bg-header">
        <span class="name">当前用户:[<a id="btn-my-profile" title="点击修改个人信息" href="#">${user.name}</a>]</span>
        <div class=""></div>
        <div class="menus">${menus}</div>
    </div>
    <div region="center">
        <div id="main-tabs" class="easyui-tabs" border="false" fit="true">
            <div title="首页">
                <p style="font-size: 20px; padding: 20px">
                    你好,<font color="red">${user.name}</font>
                </p>
            </div>
        </div>
    </div>
    <div region="south" class="footer">
        <div id="footer-left">
            <div>EasyReport(v2.0)</div>
        </div>
        <div id="footer-right">Copyright © 2014-2016 easytoolsoft.com Inc.</div>
    </div>
</div>
<!-- tabs右键菜单  -->
<div id="main-tab-ctx-menu" class="easyui-menu" data-options="onClick:HomeIndex.onTabCtxMenuClick"
     style="width: 120px;">
    <div id="m-current" data-options="name:'current',iconCls:'icon-cancel'">关闭当前页</div>
    <div id="m-others" data-options="name:'others',iconCls:''">关闭其他页</div>
    <div id="m-all" data-options="name:'all',iconCls:''">关闭所有页</div>
</div>
<div id="my-profile-dlg" class="easyui-dialog" title="个人信息修改">
    <form id="my-profile-form" name="my-profile-form" method="post">
        <table cellpadding="5" style="margin: 30px auto" class="form-table">
            <tr>
                <td>用户信息:<input id="user-id" type="hidden" name="user-id" value="${user.id}"/></td>
                <td colspan="3"><span class="name">${user.account}(${user.name})</span></td>
            </tr>
            <tr>
                <td>角色:</td>
                <td colspan="3"><span class="name">${roleNames}</span></td>
            </tr>
            <tr>
                <td>原密码:</td>
                <td colspan="3"><input class="easyui-textbox" type="password" name="oldPassword" id="oldPassword"
                                       data-options="required:true"
                                       style="width: 280px"/></td>
            </tr>
            <tr>
                <td>新密码:</td>
                <td colspan="3"><input class="easyui-textbox" type="password" name="password" id="password"
                                       data-options="required:true,validType:{length:[6,20]}"
                                       style="width: 280px"/></td>
            </tr>
            <tr>
                <td>新密码确认:</td>
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