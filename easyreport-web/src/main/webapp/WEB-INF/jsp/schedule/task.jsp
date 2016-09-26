<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>任务管理</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <link rel="stylesheet"
          href="${ctxPath}/assets/vendor/jquery-cron/gentleSelect/jquery-gentleSelect.css?v=${version}"/>
    <link rel="stylesheet" href="${ctxPath}/assets/vendor/jquery-cron/cron/jquery-cron.css?v=${version}"/>
    <script src="${ctxPath}/assets/vendor/jquery-cron/gentleSelect/jquery-gentleSelect-min.js?v=${version}"></script>
    <script src="${ctxPath}/assets/vendor/jquery-cron/cron/jquery-cron-min.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/schedule/task.js?v=${version}"></script>
</head>
<body class="easyui-layout">
<div id="toolbar" class="toolbar">
    属性:<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 100px">
    <option value="comment">说明</option>
</select> 关键字:<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
    <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search"> 搜索 </a>
    <input id="modal-action" type="hidden" name="action" value=""/>
</div>
<div style="height: 93%; padding: 2px">
    <div id="task-datagrid"></div>
</div>
<div id="task-dlg">
    <form id="task-form" name="task-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr>
                    <td>选择报表:</td>
                    <td colspan="3">
                        <select class="easyui-combobox" id="combox-reports" name="comboxReports"
                                data-options="required:true,valueField:'value',textField:'name',multiple:true"
                                style="width: 380px"></select>
                        <input id="reportIds" type="hidden" name="reportIds"/>
                        <input id="taskId" type="hidden" name="id"/>
                    </td>
                </tr>
                <tr>
                    <td>触发时间:</td>
                    <td colspan="3">
                        <div id='cronExprDiv'></div>
                    </td>
                </tr>
                <tr>
                    <td>Cron表达式:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="cronExpr" name="cronExpr"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>说明:</td>
                    <td><input class="easyui-textbox" type="text" name="comment" id="comment"
                               style="width: 200px"/></td>
                    <td>类型:</td>
                    <td>
                        <select class="easyui-combobox" id="type" name="type" style="width: 100px">
                            <option selected="selected" value="1">电子邮件</option>
                            <option value="2">手机短信</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>配置项(JSON):</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="options" name="options"
                                           data-options="required:true,multiline:true"
                                           style="width: 380px;height:150px"/></td>
                </tr>
                <tr>
                    <td>内容模板:</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="template" id="template"
                                           data-options="required:true,multiline:true"
                                           style="width: 380px;height:200px"/></td>
                </tr>
            </table>
        </center>
    </form>
</div>
</body>
</html>