<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Report Desinger</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <link rel="stylesheet" href="${ctxPath}/assets/vendor/codemirror/lib/codemirror.css?v=${version}"/>
    <link rel="stylesheet" href="${ctxPath}/assets/vendor/codemirror/theme/rubyblue.css?v=${version}"/>
    <link rel="stylesheet" href="${ctxPath}/assets/vendor/codemirror/addon/hint/show-hint.css?v=${version}"/>
    <link rel="stylesheet" href="${ctxPath}/assets/custom/codemirror/lib/codemirror.css?v=${version}"/>
    <script src="${ctxPath}/assets/vendor/codemirror/lib/codemirror.js?v=${version}"></script>
    <script src="${ctxPath}/assets/vendor/codemirror/mode/sql/sql.js?v=${version}"></script>
    <script src="${ctxPath}/assets/vendor/codemirror/addon/display/fullscreen.js?v=${version}"></script>
    <script src="${ctxPath}/assets/vendor/codemirror/addon/hint/show-hint.js?v=${version}"></script>
    <script src="${ctxPath}/assets/vendor/codemirror/addon/hint/sql-hint.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/metadata/category.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/metadata/designer-ro.js?v=${version}"></script>
</head>
<body class="easyui-layout" id="body-layout" ng-app="i18n" ng-controller="translate">
<!-- left report class tree -->
<div id="west" data-options="region:'west',split:true" title="{{info.report_category}}" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="category-tree"></ul>
        <input type="hidden" id="copyNodeId" name="copyNodeId" value="0"/>
        <input id="modal-action" type="hidden" name="action" value=""/>
        <input type="hidden" id="current-row-index" name="current-row-index" value="0"/>
    </div>
</div>
<!-- right report list-->
<div region="center" data-options="region:'center'">
    <div id="toolbar1" class="toolbar">
        {{info.report_name}}<input class="easyui-textbox" type="text" id="report-search-keyword" name="keyword" style="width:250px"/>
        <a id="btn-report-search" href="#" class="easyui-linkbutton" iconCls="icon-search">{{info.report_search_act}}</a>
    </div>
    <div style="width: 100%; height: 94%;padding-top: 1px">
        <div id="report-datagrid"></div>
    </div>
</div>

<!-- report rkey menu  -->
<div id="report-datagrid-ctx-menu" class="easyui-menu" style="width: 150px;">
    <div id="rp-preview" data-options="name:'preview',iconCls:'icon-preview'">{{info.report_disign_report_rkey_preview}}</div>
    <div id="rp-window" data-options="name:'window',iconCls:'icon-window'">{{info.report_disign_report_rkey_previewnewwin}}</div>
    <div class="menu-sep"></div>
    <div id="rp-refresh" data-options="name:'refresh',iconCls:'icon-reload'">{{info.report_disign_report_rkey_refresh}}</div>
</div>
</body>
</html>