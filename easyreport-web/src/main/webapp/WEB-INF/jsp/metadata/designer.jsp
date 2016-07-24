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
    <script src="<%=request.getContextPath()%>/assets/modules/report/js/designer.js?v=<%=Math.random()%>"></script>
</head>
<body id="layout" class="easyui-layout" style="text-align: left">
<!-- 左边树控件 -->
<div id="west" region="west" border="false" split="true" title=" " style="width: 250px; padding: 5px;">
    <ul id="reportTree"></ul>
    <input type="hidden" id="copyNodeId" name="copyNodeId" value="0"/>
</div>
<!-- 右边tab控件 -->
<div region="center" border="false">
    <div id="tabs" class="easyui-tabs" fit="true" border="false" plain="true">
        <div id="settingsTab" title="基本设置" style="padding: 5px;height:40%">
            <form id="settingsForm" method="post">
                <table cellpadding="0" class="form-table" cellspacing="0" style="width: 100%;">
                    <tr>
                        <td class="text_r blueside" width="70">名称:</td>
                        <td><input type="text" id="reportName" name="name" required/></td>
                        <td class="text_r blueside" width="60">数据源:</td>
                        <td><input id="reportDsId" name="dsId" required/></td>
                        <td class="text_r blueside">状态:</td>
                        <td><select id="reportStatus" name="status">
                            <option value="0">编辑</option>
                            <option value="1">锁定</option>
                            <option value="2">隐藏</option>
                        </select>
                        </td>
                        <td class="text_r blueside">显示顺序:</td>
                        <td>
                            <input type="text" id="reportSequence" name="sequence" value="100" required/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_r blueside" width="70">布局列:</td>
                        <td><select id="reportLayout" name="layout">
                            <option value="2">纵向展示</option>
                            <option value="1">横向展示</option>
                        </select>
                        </td>
                        <td class="text_r blueside">统计列:</td>
                        <td><select id="reportStatColumnLayout" name="statColumnLayout">
                            <option value="1">横向展示</option>
                            <option value="2">纵向展示</option>
                        </select>
                        </td>
                        <td class="text_r blueside">显示几天数据:</td>
                        <td><input type="text" id="reportDataRange" name="dataRange" value="7"/></td>
                        <td class="text_r blueside"></td>
                        <td>
                            <input type="hidden" id="reportId" name="id" value="0"/>
                            <input type="hidden" id="reportAction" name="action" value="add"/>
                            <input type="hidden" id="reportUid" name="uid"/>
                            <input type="hidden" id="reportPid" name="pid" value="0"/>
                            <input type="hidden" id="reportIsChange" name="isChange" value="0"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text_r blueside top">SQL语句:</td>
                        <td id="reportSqlTextTd" colspan="7"><textarea id="reportSqlText" name="sqlText"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8" style="text-align: center;">
                            <a id="btnExecSql" href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok"
                               onclick="ReportDesigner.executeSql()">执行SQL</a>&nbsp;&nbsp;
                            <a id="btnViewSqlText" href="javascript:void(0)" class="easyui-linkbutton" icon="icon-sql"
                               onclick="ReportDesigner.viewSqlText()">预览SQL</a>&nbsp;&nbsp;
                            <a id="btnViewHistorySqlText" href="javascript:void(0)" class="easyui-linkbutton"
                               icon="icon-history" onclick="ReportDesigner.viewSqlHistory()">查看SQL历史记录</a>&nbsp;&nbsp;
                            <a id="btnNewReport" href="javascript:void(0)" class="easyui-linkbutton" icon="icon-add"
                               onclick="ReportDesigner.save()">新增</a>&nbsp;&nbsp;
                            <a id="btnEditReport" href="javascript:void(0)" class="easyui-linkbutton" icon="icon-edit"
                               onclick="ReportDesigner.save()">修改</a>&nbsp;&nbsp;
                            <a id="btnViewReport" href="javascript:void(0)" class="easyui-linkbutton"
                               icon="icon-preview" onclick="ReportDesigner.previewInNewTab()">报表预览</a>&nbsp;&nbsp;
                            <a id="btnFullScreenEditSql" href="javascript:void(0)" class="easyui-linkbutton"
                               icon="icon-fullscreen" onclick="ReportDesigner.fullScreenSqlEditor()">全屏编辑</a></td>
                    </tr>
                </table>
            </form>
            <div style="height:55%">
                <div id="sqlColumnGrid" title="元数据列配置"></div>
            </div>
        </div>
        <div id="queryParamTab" title="查询参数" style="padding: 5px;">
            <form id="queryParamForm" method="post">
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
            <div id="queryParamGrid" title="查询参数列表"></div>
        </div>
    </div>
</div>
<!-- 报表基本属性弹框 -->
<div id="settingsDlg">
    <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%; margin: 1px 2px 1px 8px">
        <tr>
            <td class="text_r blueside" width="100">名称:</td>
            <td><label id="reportProp_name"/></td>
            <td class="text_r blueside" width="80">ID:</td>
            <td><label id="reportProp_id"/></td>
            <td class="text_r blueside" width="100">父ID:</td>
            <td><label id="reportProp_pid"/></td>
        </tr>
        <tr>
            <td class="text_r blueside">代号:</td>
            <td><label id="reportProp_uid"/></td>
            <td class="text_r blueside">布局形式:</td>
            <td><label id="reportProp_layout"/></td>
            <td class="text_r blueside">显示几天数据:</td>
            <td><label id="reportProp_dataRange"/></td>
        </tr>
        <tr>
            <td class="text_r blueside">树路径:</td>
            <td><label id="reportProp_path"/></td>
            <td class="text_r blueside">节点类型:</td>
            <td><label id="reportProp_flag"/></td>
            <td class="text_r blueside">是否有子节点:</td>
            <td><label id="reportProp_hasChild"/></td>
        </tr>
        <tr>
            <td class="text_r blueside">创建用户:</td>
            <td><label id="reportProp_createUser"/></td>
            <td class="text_r blueside">创建时间:</td>
            <td><label id="reportProp_createTime"/></td>
            <td class="text_r blueside">更新时间:</td>
            <td><label id="reportProp_updateTime"/></td>
        </tr>
        <tr>
            <td class="text_r blueside">状态:</td>
            <td><label id="reportProp_status"/></td>
            <td class="text_r blueside">显示顺序:</td>
            <td><label id="reportProp_sequence"/></td>
            <td class="text_r blueside"></td>
            <td></td>
        </tr>
        <tr>
            <td class="text_r blueside top">SQL语句:</td>
            <td colspan="5" class="code"><label id="reportProp_sqlText"/></td>
        </tr>
        <tr>
            <td class="text_r blueside top">SQL列配置:</td>
            <td colspan="5" class="code"><label id="reportProp_metaColumns"/></td>
        </tr>
        <tr>
            <td class="text_r blueside top">查询参数:</td>
            <td colspan="5" class="code"><label id="reportProp_queryParams"/></td>
        </tr>
        <tr>
            <td class="text_r blueside">说明:</td>
            <td colspan="5"><label id="reportProp_comment"/></td>
        </tr>
    </table>
</div>
<!-- 增加与修改树节点弹框  -->
<div id="setTreeNodeDlg">
    <form id="setTreeNodeForm" method="post">
        <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%; height: 99%">
            <tr id="setTreeNodeParentNodeNameTr">
                <td class="text_r blueside" width="60">父节点:</td>
                <td><label id="parentNodeName"></label></td>
            </tr>
            <tr id="setTreeNodeFlagTr">
                <td class="text_r blueside" width="60">节点类型:</td>
                <td><select id="setTreeNodeFlag" name="flag">
                    <option value="0">树节点</option>
                    <option value="1">报表节点</option>
                </select></td>
            </tr>
            <tr>
                <td class="text_r blueside" width="60">节点名称:</td>
                <td><input id="setTreeNodeName" name="name" class="easyui-validatebox" required="true"
                           style="width: 99%;"/></td>
            </tr>
            <tr>
                <td class="text_r blueside" width="60">显示顺序:</td>
                <td><input type="text" id="setTreeNodeSequence" name="sequence" value="100"/><input id="hiddenFormId"
                                                                                                    type="hidden"/></td>
            </tr>
            <tr id="setTreeNodeHasChildTr">
                <td class="text_r blueside" width="60">子节点:</td>
                <td><select id="setTreeNodeHasChild" name="hasChild">
                    <option value="0">无</option>
                    <option value="1">有</option>
                </select></td>
            </tr>
            <tr id="setTreeNodeStatusTr">
                <td class="text_r blueside" width="60">状态:</td>
                <td><select id="setTreeNodeStatus" name="status">
                    <option value="0">编辑</option>
                    <option value="1">锁定</option>
                    <option value="2">隐藏</option>
                </select></td>
            </tr>
            <tr>
                <td class="text_r blueside top">备注:</td>
                <td><textarea id="setTreeNodeComment" name="comment" style="width: 99%; height: 130px;"></textarea>
                    <input id="treeNodeAction" type="hidden" name="action" value="add"/>
                    <input type="hidden" id="treeNodeId" name="id" value="0"/>
                    <input type="hidden" id="treeNodePid" name="pid" value="0"/></td>
            </tr>
        </table>
    </form>
</div>
<!-- 查看报表SQL弹框  -->
<div id="viewSqlTextDlg" title="查看报表SQL">
    <textarea id="viewSqlText" name="sqlText" style="height:100%"></textarea>
</div>
<!-- 查看报表SQL历史记录弹框  -->
<div id="viewHistorySqlTextDlg" title="查看报表SQL历史记录">
    <table cellpadding="0" cellspacing="0" style="width: 100%; height:  100%">
        <tr>
            <td style="width: 410px;text-align: center;background: #F6FAFD;font-weight: bold;">版本历史</td>
            <td style="text-align: center;background: #F6FAFD;font-weight: bold;">版本记录</td>
        </tr>
        <tr>
            <td style="vertical-align: top;">
                <div id="viewSqlTextHistoryGrid"></div>
            </td>
            <td style="vertical-align: top;"><textarea id="viewSqlTextHistory" name="sqlText"
                                                       style="width: 100%; height: 100%;"></textarea></td>
        </tr>
    </table>
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
                   onclick="ReportDesigner.search();">查找</a></td>
        </tr>
    </table>
    <div id="searchReportGrid" title="报表列表"></div>
</div>
<!-- 设置计算列表达式弹框  -->
<div id="columnExpressionDlg" title="设置表达式">
    <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%; height: 99%">
        <tr>
            <td class="top"><textarea id="columnExpression" name="expression"
                                      style="width: 98%; height: 215px;"></textarea></td>
        </tr>
    </table>
</div>
<!-- 设置列备注弹框  -->
<div id="columnCommentDlg" title="列备注">
    <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%; height: 99%">
        <tr>
            <td class="top"><textarea id="columnComment" name="comment" style="width: 98%; height: 215px;"></textarea>
            </td>
        </tr>
    </table>
</div>
<!-- 设置列格式弹框  -->
<div id="columnFormatDlg" title="列格式">
    <table cellpadding="0" class="form-table" cellspacing="0" style="width: 99%; height: 99%">
        <tr>
            <td class="top"><textarea id="columnFormat" name="format" style="width: 98%; height: 215px;"></textarea>
            </td>
        </tr>
    </table>
</div>
<!-- tree右键菜单  -->
<div id="reportTreeCtxMenu" class="easyui-menu" data-options="onClick:ReportDesigner.treeContextMenu"
     style="width: 220px;">
    <div id="m-addRp" data-options="name:'addRp',iconCls:'icon-add'">新增报表</div>
    <div id="m-info" data-options="name:'info',iconCls:'icon-tip'">报表属性</div>
    <div class="menu-sep"></div>
    <div id="m-copy" data-options="name:'copy',iconCls:'icon-copy'">复制</div>
    <div id="m-paste" data-options="name:'paste',iconCls:'icon-paste',disabled:'true'">粘贴</div>
    <div id="m-search" data-options="name:'search',iconCls:'icon-search'">查找</div>
    <div class="menu-sep"></div>
    <div id="m-add" data-options="name:'add',iconCls:'icon-add'">新增节点</div>
    <div id="m-edit" data-options="name:'edit',iconCls:'icon-edit'">修改节点</div>
    <div id="m-remove" data-options="name:'remove',iconCls:'icon-remove'">删除节点</div>
    <div class="menu-sep"></div>
    <div id="m-refresh" data-options="name:'refresh',iconCls:'icon-reload'">刷新</div>
</div>
<!-- tabs右键菜单  -->
<div id="tabsCtxMenu" class="easyui-menu" data-options="onClick:ReportDesigner.tabContextMenu" style="width: 220px;">
    <div id="m-current" data-options="name:'current',iconCls:'icon-cancel'">关闭当前页</div>
    <div id="m-others" data-options="name:'others',iconCls:''">关闭其他页</div>
    <div id="m-all" data-options="name:'all',iconCls:''">关闭所有页</div>
</div>
</body>
</html>