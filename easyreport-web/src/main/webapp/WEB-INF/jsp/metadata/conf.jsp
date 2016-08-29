<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>配置字典</title>
    <%@ include file="/WEB-INF/jsp-views/includes/header.jsp" %>
    <%@ include file="/WEB-INF/jsp-views/includes/form_scripts.jsp" %>
    <script src="<%=request.getContextPath()%>/assets/modules/report/js/common.js"></script>
    <script src="<%=request.getContextPath()%>/assets/modules/report/js/config.js?v=<%=Math.random()%>"></script>
</head>
<body class="easyui-layout" style="text-align: left">
<!-- 左边tree -->
<div id="west" region="west" border="false" split="true" title=" " style="width: 250px; padding: 5px; cursor: pointer;">
    <ul id="configDictTree"></ul>
</div>
<!-- 右边tabs -->
<div region="center" border="false">
    <div id="configDictDiv" style="width: 100%; height: 98%">
        <div id="configDictGrid"></div>
    </div>
</div>
<!-- 新增与修改配置字典dialog  -->
<div id="configDictDlg">
    <form id="configDictForm" method="post" novalidate>
        <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%;">
            <tr id="configDictPNameDiv">
                <td class="text_r blueside" width="80">父节点:</td>
                <td><label id="configDictPName"></label></td>
            </tr>
            <tr>
                <td class="text_r blueside" width="80">名称:</td>
                <td><input name="name" class="easyui-validatebox" required="true" style="width: 200px"/></td>
            </tr>
            <tr>
                <td class="text_r blueside" width="80">键(Key):</td>
                <td><input name="key" class="easyui-validatebox" required="true" style="width: 200px"/></td>
            </tr>
            <tr>
                <td class="text_r blueside" width="80">值(Value):</td>
                <td><input name="value" class="easyui-validatebox" required="true" style="width: 200px"/></td>
            </tr>
            <tr>
                <td class="text_r blueside top" width="80">说明:</td>
                <td><textarea name="comment" style="width: 99%; height: 80px;"></textarea><input id="configDictPid"
                                                                                                 type="hidden"
                                                                                                 name="pid" value="0"/>
                    <input
                            id="configDictId" type="hidden" name="id" value="0"/> <input id="configDictAction"
                                                                                         type="hidden" name="action"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- tree右键菜单  -->
<div id="tree_ctx_menu" class="easyui-menu" data-options="onClick:ConfigDict.treeContextMenu" style="width: 120px;">
    <div id="m-add" data-options="name:'add',iconCls:'icon-add'">增加</div>
    <div id="m-edit" data-options="name:'edit',iconCls:'icon-edit'">修改</div>
    <div id="m-remove" data-options="name:'remove',iconCls:'icon-remove'">删除</div>
</div>
</body>
</html>