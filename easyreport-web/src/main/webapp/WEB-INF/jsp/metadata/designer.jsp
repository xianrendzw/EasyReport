<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>报表设计</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <link rel="stylesheet" href="${ctxPath}/assets/js/plugins/codemirror/codemirror.css?v=${version}"/>
    <link rel="stylesheet" href="${ctxPath}/assets/js/plugins/codemirror/theme/rubyblue.css?v=${version}"/>
    <link rel="stylesheet" href="${ctxPath}/assets/js/plugins/codemirror/addon/hint/show-hint.css?v=${version}"/>
    <script src="${ctxPath}/assets/js/plugins/codemirror/codemirror.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/plugins/codemirror/mode/sql/sql.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/plugins/codemirror/addon/display/fullscreen.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/plugins/codemirror/addon/hint/show-hint.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/plugins/codemirror/addon/hint/sql-hint.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/metadata/category.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/metadata/designer.js?v=${version}"></script>
</head>
<body class="easyui-layout" id="body-layout">
<!-- 左边报表分类tree -->
<div id="west" data-options="region:'west',split:true" title="报表分类" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="category-tree"></ul>
        <input type="hidden" id="copyNodeId" name="copyNodeId" value="0"/>
        <input id="modal-action" type="hidden" name="action" value=""/>
    </div>
</div>
<!-- 右边报表列表-->
<div region="center" data-options="region:'center'">
    <div id="toolbar1" class="toolbar">
        名称:<input class="easyui-textbox" type="text" id="report-search-keyword" name="keyword" style="width:250px"/> <a
            id="btn-report-search" href="#"
            class="easyui-linkbutton" iconCls="icon-search"> 查找 </a>
    </div>
    <div style="width: 100%; height: 94%;padding-top: 1px">
        <div id="report-datagrid"></div>
    </div>
</div>
<!-- 报表分类增加与编辑对话框 -->
<div id="category-dlg">
    <form id="category-form" name="category-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr>
                    <td>父报表分类:</td>
                    <td colspan="3"><label id="category-parentName"></label></td>
                </tr>
                <tr>
                    <td>名称:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="category-name" name="name" value=""
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>状态:</td>
                    <td><select class="easyui-combobox" id="category-status" name="status" style="width: 148px">
                        <option selected="selected" value="1">启用</option>
                        <option value="0">禁用</option>
                    </select></td>
                    <td>排序:</td>
                    <td><input class="easyui-textbox" type="text" id="category-sequence" name="sequence"
                               data-options="required:true,validType:'digit'" style="width: 138px"/></td>
                </tr>
                <tr>
                    <td>备注:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="category-comment" name="comment"
                                           style="width: 380px"/>
                        <input id="category-parentId" type="hidden" name="parentId" value="0"/></td>
                    <input id="category-id" type="hidden" name="id" value=""/></td>
                </tr>
            </table>
        </center>
    </form>
</div>
<!-- 查找树节点弹框  -->
<div id="category-search-dlg" title="查找报表分类">
    <div id="toolbar2" class="toolbar">
        名称：<input class="easyui-textbox" type="text" id="category-search-keyword" name="keyword" style="width:250px"/>
        <a id="btn-category-search" href="#"
           class="easyui-linkbutton" iconCls="icon-search"> 查找 </a>
    </div>
    <div style="height: 90%; padding-top: 1px">
        <div id="category-search-result-grid"></div>
    </div>
</div>
<!-- 报表设计器 -->
<div id="report-designer-dlg">
    <div id="tabs" class="easyui-tabs" fit="true" border="false" plain="true">
        <div id="report-basic-conf-tab" title="基本设置" style="padding: 5px;height:40%">
            <div id="report-basic-conf-form-div">
                <form id="report-basic-conf-form" name="report-basic-conf-form" method="post">
                    <center>
                        <table cellpadding="5" style="margin: 5px auto;width:95%" class="form-table">
                            <tr>
                                <td>名称:</td>
                                <td><input class="easyui-textbox" type="text" name="name" id="report-name"
                                           data-options="required:true"/></td>
                                <td>中文名:</td>
                                <td><input class="easyui-textbox" type="text" name="cnName" id="report-cnName"
                                           data-options="required:true"/></td>
                                <td>所属分类：</td>
                                <td><select class="easyui-combotree" id="categorygoryId" name="categoryId"
                                            data-options="required:true" style="width:148px;"></select></td>
                            </tr>
                            <tr>
                                <td>状态:</td>
                                <td><select class="easyui-combobox" id="report-status" name="status"
                                            data-options="valueField:'value',textField:'name'" style="width:148px;">
                                    <option selected="selected" value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select></td>
                                <td>备注:</td>
                                <td><input class="easyui-textbox" type="text" name="comment" id="comment"
                                           data-options="required:false"/></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>SQL语句:</td>
                                <td colspan="5" id="report-sqltext-td"><textarea id="report-sqlText"
                                                                                 name="sqlText"></textarea>
                                    <input type="hidden" id="report-id" name="id"/>
                                    <input type="hidden" id="report-params" name="params"/>
                                    <input type="hidden" id="sqlTextIsChanged" name="sqlTextIsChanged" value="0"/>
                                </td>
                            </tr>
                        </table>
                    </center>
                </form>
            </div>
            <div id="report-sql-column-div" style="height:180px;">
                <div id="report-sql-column-grid" title="参数列表"></div>
            </div>
        </div>
        <div id="report-query-param-tab" title="查询参数" style="padding: 5px;">
            <div id="report-query-param-form-div">
                <form id="report-query-param-form" method="post">
                    <table cellpadding="0" class="form-table" cellspacing="0" style="width: 100%;">
                        <tr>
                            <td class="text_r blueside" width="70">参数名:</td>
                            <td><input type="text" id="queryParamName" name="name" required/></td>
                            <td class="text_r blueside">标题:</td>
                            <td><input type="text" id="queryParamText" name="text" required/></td>
                            <td class="text_r blueside">默认值:</td>
                            <td><input type="text" id="queryParamDefaultValue" name="defaultValue"/></td>
                            <td class="text_r blueside">默认标题:</td>
                            <td><input type="text" id="queryParamDefaultText" name="defaultText"/></td>
                        </tr>
                        <tr>
                            <td class="text_r blueside" width="70">数据类型:</td>
                            <td><select id="queryParamDataType" name="dataType">
                                <option value="string" selected="selected">字符串</option>
                                <option value="float">浮点数（包括双精度、浮点数)</option>
                                <option value="integer">整数</option>
                                <option value="date">日期</option>
                            </select></td>
                            <td class="text_r blueside">数据长度:</td>
                            <td><input type="text" id="queryParamWidth" name="width" value="100"/></td>
                            <td class="text_r blueside">是否必选:</td>
                            <td><input type="checkbox" id="queryParamIsRequired" name="required"/></td>
                            <td class="text_r blueside">是否自动提示:</td>
                            <td><input type="checkbox" id="queryParamIsAutoComplete" name="autoComplete"/></td>
                        </tr>
                        <tr>
                            <td class="text_r blueside" width="70">表单控件:</td>
                            <td><select id="queryParamFormElement" name="formElement">
                                <option value="select">下拉单选</option>
                                <option value="selectMul">下拉多选</option>
                                <option value="checkbox">复选框</option>
                                <option value="text">文本框</option>
                                <option value="date">日期</option>
                            </select></td>
                            <td class="text_r blueside">内容来源类型</td>
                            <td colspan="5"><select id="queryParamDataSource" name="dataSource">
                                <option value="sql">SQL语句</option>
                                <option value="text">文本字符串</option>
                                <option value="none">无内容</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td class="text_r blueside top">内容:</td>
                            <td colspan="7">
                            <textarea id="queryParamContent" name="content" style="width: 99%; height: 140px;"
                                      placeholder="(select col1 as name,col2 as text from table ...) or (name1,text1|name2,text2|...) or (name1|name2|...)"></textarea>
                                <input type="hidden" id="queryParamGridIndex" value=""/>
                                <input type="hidden" id="jsonQueryParams"/>
                                <input type="hidden" id="queryParamReportId" value="0"/></td>
                        </tr>
                        <tr>
                            <td colspan="8" style="text-align: center;">
                                <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-add"
                                   onclick="ReportDesigner.setQueryParam('add')">增加</a>&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-edit"
                                   onclick="ReportDesigner.setQueryParam('edit')">修改</a>&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-save"
                                   onclick="ReportDesigner.saveQueryParam()">保存</a></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="report-query-param-div" style="height:180px;">
                <div id="report-query-param-grid" title="查询参数列表"></div>
            </div>
        </div>
    </div>
</div>
<!-- 查看报表sql历史记录 -->
<div id="report-history-sql-dlg" title="查看报表sql历史记录">
    <div style="height: 200px">
        <textarea id="report-history-sql-text" name="sqlText"></textarea>
    </div>
    <div id="report-history-sql-grid-div">
        <div id="report-history-sql-grid"></div>
    </div>
</div>
<!-- tree右键菜单  -->
<div id="category-tree-ctx-menu" class="easyui-menu" style="width: 150px;">
    <div id="m-add-cate" data-options="name:'addCate',iconCls:'icon-category'">新增分类</div>
    <div id="m-add-report" data-options="name:'addReport',iconCls:'icon-report'">新增报表</div>
    <div class="menu-sep"></div>
    <div id="m-copy" data-options="name:'copy',iconCls:'icon-copy'">复制</div>
    <div id="m-paste" data-options="name:'paste',iconCls:'icon-paste',disabled:'true'">粘贴</div>
    <div id="m-edit" data-options="name:'edit',iconCls:'icon-edit'">修改</div>
    <div id="m-remove" data-options="name:'remove',iconCls:'icon-remove'">删除</div>
    <div id="m-search" data-options="name:'search',iconCls:'icon-search'">查找</div>
    <div class="menu-sep"></div>
    <div id="m-refresh" data-options="name:'refresh',iconCls:'icon-reload'">刷新</div>
</div>
</body>
</html>