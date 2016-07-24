<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>报表设计</title>
    <%@ include file="/WEB-INF/jsp-views/includes/header.jsp" %>
    <%@ include file="/WEB-INF/jsp-views/includes/form_scripts.jsp" %>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/js/plugins/codemirror/codemirror.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/js/plugins/codemirror/theme/rubyblue.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/js/plugins/codemirror/addon/hint/show-hint.css"/>
    <script src="<%=request.getContextPath()%>/assets/js/plugins/codemirror/codemirror.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/plugins/codemirror/mode/sql/sql.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/plugins/codemirror/addon/display/fullscreen.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/plugins/codemirror/addon/hint/show-hint.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/plugins/codemirror/addon/hint/sql-hint.js"></script>
    <script src="<%=request.getContextPath()%>/assets/modules/report/js/common.js"></script>
    <script src="<%=request.getContextPath()%>/assets/modules/report/js/query.js?v=<%=Math.random()%>"></script>
</head>
<body id="layout" class="easyui-layout" style="text-align: left">
<!-- 左边树控件 -->
<div id="west" region="west" border="false" split="true" title=" " style="width: 250px; padding: 5px;">
    <ul id="reportTree"></ul>
</div>
<!-- 右边tab控件 -->
<div region="center" border="false">
    <div id="tabs" class="easyui-tabs" fit="true" border="false" plain="true">

    </div>
</div>

<!-- 查找树节点弹框  -->
<div id="searchReportDlg" title="查找树节点">
    <table cellpadding="0" class="form-table" cellspacing="0" style="width: 100%;">
        <tr>
            <td class="text_r blueside" width="60">选项:</td>
            <td><select id="searchReportFieldName" name="fieldName">
                <option value="name">名称</option>
                <option value="uid">代码</option>
                <option value="create_user">创建者</option>
            </select></td>
            <td class="text_r blueside">关键字:</td>
            <td><input type="text" id="searchReportKeyword" name="keyword"/>
                <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok"
                   onclick="ReportQuery.search();">查找</a></td>
        </tr>
    </table>
    <div id="searchReportGrid" title="报表列表"></div>
</div>


<!-- tabs右键菜单  -->
<div id="tabsCtxMenu" class="easyui-menu" data-options="onClick:ReportQuery.tabContextMenu" style="width: 220px;">
    <div id="m-current" data-options="name:'current',iconCls:'icon-cancel'">关闭当前页</div>
    <div id="m-others" data-options="name:'others',iconCls:''">关闭其他页</div>
    <div id="m-all" data-options="name:'all',iconCls:''">关闭所有页</div>
</div>
</body>
</html>