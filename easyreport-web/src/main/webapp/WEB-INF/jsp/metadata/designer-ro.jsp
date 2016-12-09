<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>报表设计</title>
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
<body class="easyui-layout" id="body-layout">
<!-- 左边报表分类tree -->
<!-- div id="west" data-options="region:'west',split:true" title="报表分类" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="category-tree"></ul>
        <input type="hidden" id="copyNodeId" name="copyNodeId" value="0"/>
        <input id="modal-action" type="hidden" name="action" value=""/>
        <input type="hidden" id="current-row-index" name="current-row-index" value="0"/>
    </div>
</div-->
<!-- 右边报表列表-->
<div region="center" data-options="region:'center'">
    <div id="toolbar1" class="toolbar">
        名称:<input class="easyui-textbox" type="text" id="report-search-keyword" name="keyword" style="width:250px"/>
        <a id="btn-report-search" href="#" class="easyui-linkbutton" iconCls="icon-search"> 查找 </a>
    </div>
    <div style="width: 100%; height: 94%;padding-top: 1px">
        <div id="report-datagrid"></div>
    </div>
</div>
<!-- 报表分类增加与编辑对话框 -->
<div id="category-dlg">
    <form id="category-form" name="category-form" method="post">
        <center>
            <table cellpadding="0" class="form-table" cellspacing="0" style="width: 100%;">
                <tr>
                    <td>父报表分类:</td>
                    <td colspan="3"><label id="category-parentName"></label></td>
                </tr>
                <tr>
                    <td>名称:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="category-name" name="name"
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
                    <table class="designer-table" style="width: 95%">
                        <tr>
                            <td>名称:</td>
                            <td><input class="easyui-textbox" type="text" id="report-name" name="name"
                                       data-options="required:true" style="width:200px"/>
                            </td>
                            <td>数据源:</td>
                            <td><select class="easyui-combobox" id="report-dsId" name="dsId"
                                        data-options="required:true,valueField:'id',textField:'name'"
                                        style="width:150px">
                            </select>
                            </td>
                            <td>布局列:</td>
                            <td><select class="easyui-combobox" id="report-layout" name="layout"
                                        data-options="required:true" style="width:100px">
                                <option value="1">横向展示</option>
                                <option value="2">纵向展示</option>
                            </select>
                            </td>
                            <td>统计列:</td>
                            <td><select class="easyui-combobox" id="report-statColumnLayout" name="statColumnLayout"
                                        data-options="required:true" style="width:100px">
                                <option value="1">横向展示</option>
                                <option value="2">纵向展示</option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td>显示几天数据:</td>
                            <td><input class="easyui-textbox" type="text" id="report-dataRange" name="dataRange"
                                       value="7"
                                       data-options="required:true,validType:'digit'" style="width:200px"/></td>
                            <td>状态:</td>
                            <td><select class="easyui-combobox" id="report-status" name="status"
                                        data-options="required:true" style="width:150px">
                                <option value="0">禁用</option>
                                <option value="1" selected="selected">启用</option>
                            </select>
                            </td>
                            <td>显示顺序:</td>
                            <td>
                                <input class="easyui-textbox" type="text" id="report-sequence" name="sequence"
                                       value="100"
                                       data-options="required:true,validType:'digit'" style="width:100px"/>
                            </td>
                            <td>所属分类:</td>
                            <td><label id="report-category-name"/></td>
                        </tr>
                        <tr>
                            <td>SQL语句:</td>
                            <td colspan="7" id="report-sqlText-td">
                                <textarea id="report-sqlText" name="sqlText"></textarea>
                                <input type="hidden" id="report-id" name="id" value=""/>
                                <input type="hidden" id="report-uid" name="uid"/>
                                <input type="hidden" id="report-categoryId" name="categoryId" value="0"/>
                                <input type="hidden" id="report-sqlTextIsChange" name="isChange" value="0"/>
                                <input type="hidden" id="report-queryParams" name="queryParams"/>
                                <input type="hidden" id="report-comment" name="comment" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="8" style="text-align: center;">
                                <a id="btn-report-exec-sql" href="#" class="easyui-linkbutton"
                                   iconCls="icon-ok">执行SQL</a>&nbsp;&nbsp;
                                <a id="btn-report-preview-sql" href="#" class="easyui-linkbutton"
                                   iconCls="icon-sql">预览SQL</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="report-meta-column-div" style="height:180px;">
                <div id="report-meta-column-grid" title="元数据列配置"></div>
            </div>
        </div>
        <div id="report-query-param-tab" title="查询参数" style="padding: 5px;">
            <div id="report-query-param-form-div">
                <form id="report-query-param-form" method="post">
                    <table class="designer-table">
                        <tr>
                            <td>参数名:</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-name" name="name"
                                       data-options="required:true" style="width:150px"/></td>
                            <td>标题:</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-text" name="text"
                                       data-options="required:true" style="width:150px"/></td>
                            <td>默认值:</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-defaultValue"
                                       name="defaultValue" style="width:150px"/></td>
                            <td>默认标题:</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-defaultText"
                                       name="defaultText" style="width:150px"/></td>
                        </tr>
                        <tr>
                            <td>数据类型:</td>
                            <td><select class="easyui-combobox" id="report-query-param-dataType" name="dataType"
                                        data-options="required:true" style="width:150px">
                                <option value="string" selected="selected">字符串</option>
                                <option value="float">浮点数（包括双精度、浮点数)</option>
                                <option value="integer">整数</option>
                                <option value="date">日期</option>
                            </select></td>
                            <td>数据长度:</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-width" name="width"
                                       value="100" data-options="validType:'digit'" style="width:150px"/></td>
                            <td>是否必选:</td>
                            <td><input class="easyui-textbox" type="checkbox" id="report-query-param-required"
                                       name="required"/></td>
                            <td>是否自动提示:</td>
                            <td><input type="checkbox" id="report-query-param-autoComplete" name="autoComplete"/></td>
                        </tr>
                        <tr>
                            <td>表单控件:</td>
                            <td><select class="easyui-combobox" id="report-query-param-formElement" name="formElement"
                                        data-options="required:true" style="width:150px">
                                <option value="select">下拉单选</option>
                                <option value="selectMul">下拉多选</option>
                                <option value="checkbox">复选框</option>
                                <option value="text">文本框</option>
                                <option value="date">日期</option>
                            </select></td>
                            <td>内容来源类型</td>
                            <td colspan="5"><select class="easyui-combobox" id="report-query-param-dataSource"
                                                    name="dataSource" data-options="required:true" style="width:150px">
                                <option value="sql">SQL语句</option>
                                <option value="text">文本字符串</option>
                                <option value="none">无内容</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td>内容:</td>
                            <td colspan="7">
                            <textarea id="report-query-param-content" name="content" style="width: 95%; height: 100px;"
                                      placeholder="(select col1 as name,col2 as text from table ...) or (name1,text1|name2,text2|...) or (name1|name2|...)"></textarea>
                                <input type="hidden" id="report-query-param-gridIndex" value="0"/>
                                <input type="hidden" id="report-query-param-json"/>
                        </tr>
                        <tr>
                            <td colspan="8" style="text-align: center;">
                                <a id="btn-report-query-param-add" href="#" class="easyui-linkbutton"
                                   iconCls="icon-add">增加</a>&nbsp;&nbsp;
                                <a id="btn-report-query-param-edit" href="#" class="easyui-linkbutton"
                                   iconCls="icon-edit">修改</a>
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
<!-- 报表详细信息弹框 -->
<div id="report-detail-dlg" title="详细信息">
    <table class="designer-table detail">
        <tr>
            <td>名称:</td>
            <td><label id="report-detail-name"/></td>
            <td>ID:</td>
            <td><label id="report-detail-id"/></td>
            <td>UID:</td>
            <td><label id="report-detail-uid"/></td>
        </tr>
        <tr>
            <td>分类ID:</td>
            <td><label id="report-detail-categoryId"/></td>
            <td>分类名称:</td>
            <td><label id="report-detail-categoryName"/></td>
            <td>状态:</td>
            <td><label id="report-detail-status"/></td>
        </tr>
        <tr>
            <td>数据源ID:</td>
            <td><label id="report-detail-dsId"/></td>
            <td>数据源名称:</td>
            <td><label id="report-detail-dsName"/></td>
            <td>显示顺序:</td>
            <td><label id="report-detail-sequence"/></td>
        </tr>
        <tr>
            <td>布局形式:</td>
            <td><label id="report-detail-layout"/></td>
            <td>统计列布局形式:</td>
            <td><label id="report-detail-statColumnLayout"/></td>
            <td>显示几天数据:</td>
            <td><label id="report-detail-dataRange"/></td>
        </tr>
        <tr>
            <td>创建用户:</td>
            <td><label id="report-detail-createUser"/></td>
            <td>创建时间:</td>
            <td><label id="report-detail-gmtCreated"/></td>
            <td>更新时间:</td>
            <td><label id="report-detail-gmtModified"/></td>
        </tr>
        <tr>
            <td>SQL语句:</td>
            <td colspan="5" class="code"><label id="report-detail-sqlText"/></td>
        </tr>
        <tr>
            <td>SQL列配置:</td>
            <td colspan="5" class="code"><label id="report-detail-metaColumns"/></td>
        </tr>
        <tr>
            <td>查询参数:</td>
            <td colspan="5" class="code"><label id="report-detail-queryParams"/></td>
        </tr>
        <tr>
            <td>说明:</td>
            <td colspan="5">
                <label id="report-detail-comment"/>
            </td>
        </tr>
    </table>
</div>
<!-- 查看报表sql历史记录 -->
<div id="report-history-sql-dlg" title="报表版本历史">
    <div style="height: 200px">
        <textarea id="report-history-sqlText" name="sqlText"></textarea>
    </div>
    <div id="report-history-sql-grid-div" class="easyui-layout" style="width:96%;height:60%;margin:5px;">
        <div data-options="region:'west',title:'历史记录'" style="padding:10px;width:500px;">
            <div id="report-history-sql-grid"></div>
        </div>
        <div data-options="region:'center',split:true,title:'属性',collapsible:false">
            <div id="report-history-sql-pgrid" class="easyui-propertygrid"></div>
        </div>
    </div>
</div>
<!-- 预览SQ弹框  -->
<div id="report-preview-sql-dlg" title="预览SQL">
    <textarea id="report-preview-sqlText" name="sqlText" style="height:100%"></textarea>
</div>
<!-- 设置计算列表达式弹框  -->
<div id="report-column-expression-dlg" title="列表达式">
    <table class="designer-table" style="height: 90%">
        <tr>
            <td class="top">
                <textarea id="report-column-expression" name="expression"
                          style="border:solid 1px; width: 98%; height: 215px;"></textarea></td>
        </tr>
    </table>
</div>
<!-- 设置列备注弹框  -->
<div id="report-column-comment-dlg" title="列备注">
    <table class="designer-table" style="height: 90%">
        <tr>
            <td class="top"><textarea id="report-column-comment" name="comment"
                                      style="border:solid 1px; width: 98%; height: 215px;"></textarea>
            </td>
        </tr>
    </table>
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
<!-- 报表列表右键菜单  -->
<div id="report-datagrid-ctx-menu" class="easyui-menu" style="width: 150px;">
    <div id="rp-preview" data-options="name:'preview',iconCls:'icon-preview'">预览</div>
    <div id="rp-window" data-options="name:'window',iconCls:'icon-window'">在新窗口预览...</div>
    <div class="menu-sep"></div>
    <div id="rp-add" data-options="name:'add',iconCls:'icon-add'">增加</div>
    <div id="rp-edit" data-options="name:'edit',iconCls:'icon-edit1'">修改</div>
    <div id="rp-copy" data-options="name:'copy',iconCls:'icon-copy'">复制</div>
    <div id="rp-remove" data-options="name:'remove',iconCls:'icon-remove'">删除</div>
    <div class="menu-sep"></div>
    <div id="rp-history" data-options="name:'history',iconCls:'icon-history'">版本</div>
    <div id="rp-info" data-options="name:'info',iconCls:'icon-info'">详细</div>
    <div id="rp-refresh" data-options="name:'refresh',iconCls:'icon-reload'">刷新</div>
</div>
</body>
</html>