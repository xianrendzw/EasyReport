<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Task</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <link rel="stylesheet"
          href="${ctxPath}/assets/vendor/jquery-cron/gentleSelect/jquery-gentleSelect.css?v=${version}"/>
    <link rel="stylesheet" href="${ctxPath}/assets/vendor/jquery-cron/cron/jquery-cron.css?v=${version}"/>
    <script src="${ctxPath}/assets/vendor/jquery-cron/gentleSelect/jquery-gentleSelect-min.js?v=${version}"></script>
    <script src="${ctxPath}/assets/vendor/jquery-cron/cron/jquery-cron.js?v=${version}"></script>
    <script src="${ctxPath}/assets/js/schedule/task.js?v=${version}"></script>
</head>
<body  ng-app="i18n" ng-controller="translate" class="easyui-layout">
<div id="toolbar" class="toolbar">
    {{info.task_property}}<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 100px">
    <option value="comment">{{info.task_comment}}</option>
</select>{{info.task_keyword}}<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
    <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search">{{info.task_search}}</a>
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
                    <td>{{info.task_sel_report}}</td>
                    <td colspan="3">
                        <select class="easyui-combobox" id="combox-reports" name="comboxReports"
                                data-options="required:true,valueField:'value',textField:'name',multiple:true"
                                style="width: 380px"></select>
                        <input id="reportIds" type="hidden" name="reportIds"/>
                        <input id="taskId" type="hidden" name="id"/>
                    </td>
                </tr>
                <tr>
                    <td>{{info.task_trigger_time}}</td>
                    <td colspan="3">
                        <div id='cronExprDiv'></div>
                    </td>
                </tr>
                <tr>
                    <td>{{info.task_cronexp}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="cronExpr" name="cronExpr"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.task_comment}}</td>
                    <td><input class="easyui-textbox" type="text" name="comment" id="comment"
                               style="width: 200px"/></td>
                    <td>{{info.task_type}}</td>
                    <td>
                        <select class="easyui-combobox" id="type" name="type" style="width: 100px">
                            <option selected="selected" value="1">{{info.task_email}}</option>
                            <option value="2">{{info.task_sms}}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>{{info.task_json}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="options" name="options"
                                           data-options="required:true,multiline:true"
                                           style="width: 380px;height:150px"/></td>
                </tr>
                <tr>
                    <td>{{info.task_template}}</td>
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