<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>日志管理</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/sys/event.js?v=${version}"></script>
</head>
<body class="easyui-layout">
<div id="toolbar" class="toolbar">
    属性:<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 100px">
    <option value="account">账号</option>
    <option value="source">来源</option>
    <option value="level">级别</option>
    <option value="message">内容</option>
</select> 关键字:<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
    <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search"> 搜索 </a>
    <input id="modal-action" type="hidden" name="action" value=""/>
</div>
<div style="height: 93%; padding: 2px">
    <div id="event-datagrid"></div>
    <input id="current-row-index" name="current-row-index" type="hidden" value="0"/>
</div>
<div id="detail-info-dlg" class="easyui-dialog" title="日志详细信息">
    <div id="detail-info" style="padding: 2px"></div>
</div>
</body>
</html>