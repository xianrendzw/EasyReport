<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${report.name}</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/report.jsp" %>
</head>
<body class="easyui-layout">
<div region="center" style="border-top-width: 0">
    <div id="report-main-tabs" class="easyui-tabs" border="false" fit="true">
        <div title="表格" data-options="iconCls:'icon-table'"></div>
        <div title="图表" data-options="iconCls:'icon-chart4'"></div>
        <input id="report-main-uid" type="hidden" name="uid" value="${report.uid}"/>
    </div>
</div>
</body>
</html>