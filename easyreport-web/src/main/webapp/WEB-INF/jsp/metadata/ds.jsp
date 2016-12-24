<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Datasource</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/metadata/ds.js?v=${version}"></script>
</head>
<body ng-app="i18n" ng-controller="translate" class="easyui-layout">
<div id="toolbar" class="toolbar">
    {{info.datasource_property}}<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 100px">
    <option value="name">{{info.datasource_name}}</option>
    <option value="driver_class">{{info.datasource_driver_class}}</option>
    <option value="dbpool_class">{{info.datasource_dbpool_class}}</option>
    <option value="comment">{{info.datasource_comment}}</option>
</select>{{info.datasource_keyword}}<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
    <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search">{{info.datasource_search}}</a>
    <input id="modal-action" type="hidden" name="action" value=""/>
</div>
<div style="height: 93%; padding: 2px">
    <div id="ds-datagrid"></div>
</div>
<div id="ds-dlg">
    <form id="ds-form" name="ds-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr>
                    <td>{{info.datasource_name}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="name" name="name"
                                           data-options="required:true" style="width: 433px"/>

                    </td>
                </tr>
                <tr>
                    <td>{{info.datasource_user}}</td>
                    <td><input class="easyui-textbox" type="text" id="user" name="user"
                               data-options="required:true"
                               style="width: 180px"/></td>
                    <td>{{info.datasource_password}}</td>
                    <td><input class="easyui-textbox" type="password" id="password" name="password"
                               data-options="required:true"
                               style="width: 180px"/></td>
                </tr>
                <tr>
                    <td>{{info.datasource_dbtype}}</td>
                    <td>
                        <select class="easyui-combobox" id="dbType" name="dbType"
                                data-options="required:true,valueField:'value',textField:'name'"
                                style="width: 180px"></select>
                    </td>
                    <td>{{info.datasource_dbpooltype}}</td>
                    <td colspan="3"><select class="easyui-combobox" id="dbPoolType" name="dbPoolType"
                                            data-options="required:true,valueField:'value',textField:'name'"
                                            style="width: 180px"></select>
                    </td>
                </tr>
                <tr>
                    <td>{{info.datasource_jdbcurl}}</td>
                    <td colspan="3">
                        <input class="easyui-textbox" type="text" id="jdbcUrl" name="jdbcUrl"
                               data-options="required:true"
                               style="width: 433px"/></td>
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align: top">{{info.datasource_dsoption}}</td>
                    <td colspan="3">
                        <div class="easyui-propertygrid" id="ds-options-pg"></div>
                    </td>
                </tr>
                <tr>
                    <td>{{info.datasource_comment}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="comment"
                                           style="width: 433px"/>
                        <input id="dsId" type="hidden" name="id"/>
                        <input id="driverClass" type="hidden" name="driverClass"/>
                        <input id="queryerClass" type="hidden" name="queryerClass"/>
                        <input id="poolClass" type="hidden" name="poolClass"/>
                        <input id="options" type="hidden" name="options"/>
                    </td>
                </tr>
            </table>
        </center>
    </form>
</div>
</body>
</html>