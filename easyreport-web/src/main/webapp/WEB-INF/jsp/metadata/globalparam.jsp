<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Report Configuration</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/metadata/globalparam.js?v=${version}"></script>
</head>
<body  ng-app="i18n" ng-controller="translate" class="easyui-layout" id="body-layout">
<div style="height: 93%; padding: 2px">
    <div id="globalparam-datagrid"></div>
    <input id="globalparamId" type="hidden" name="id" value="0"/>
    <input id="modal-action" type="hidden" name="action" value=""/>
</div>
<div id="report-query-param-dlg" title="{{info.report_global_parameter}}" style="padding: 5px;">
    <div id="report-query-param-form-div">
        <form id="report-query-param-form" method="post">
            <table class="designer-table">
                <tr>
                    <td>{{info.report_disign_conf_datasource}}</td>
                    <td><select class="easyui-combobox" id="report-dsId" name="dsId"
                              data-options="required:true,valueField:'id',textField:'name'"
                              style="width:150px">
                         </select>
                     </td>
                </tr>
                <tr>
                    <td>{{info.report_global_parameter_name}}</td>
                    <td><input class="easyui-textbox" type="text" id="report-query-param-name" name="name"
                               data-options="required:true" style="width:150px"/></td>
                    <td>{{info.report_global_parameter_title}}</td>
                    <td><input class="easyui-textbox" type="text" id="report-query-param-text" name="text"
                               data-options="required:true" style="width:150px"/></td>
                    <td>{{info.report_global_parameter_default_value}}</td>
                    <td><input class="easyui-textbox" type="text" id="report-query-param-defaultValue"
                               name="defaultValue" style="width:150px"/></td>
                    <td>{{info.report_global_parameter_default_title}}</td>
                    <td><input class="easyui-textbox" type="text" id="report-query-param-defaultText"
                               name="defaultText" style="width:150px"/></td>
                </tr>
                <tr>
                    <td>{{info.report_global_parameter_datatype}}</td>
                    <td><select class="easyui-combobox" id="report-query-param-dataType" name="dataType"
                                data-options="required:true" style="width:150px">
                        <option value="string" selected="selected">{{info.report_global_parameter_datatype_string}}</option>
                        <option value="float">{{info.report_global_parameter_datatype_float}}</option>
                        <option value="integer">{{info.report_global_parameter_datatype_integer}}</option>
                        <option value="date">{{info.report_global_parameter_datatype_date}}</option>
                    </select></td>
                    <td>{{info.report_global_parameter_length}}</td>
                    <td><input class="easyui-textbox" type="text" id="report-query-param-width" name="width"
                               value="100" data-options="validType:'digit'" style="width:150px"/></td>
                    <td>{{info.report_global_parameter_required}}</td>
                    <td><input type="checkbox" id="report-query-param-required"
                               name="required"/></td>
                    <td>{{info.report_global_parameter_autocomplete}}</td>
                    <td><input type="checkbox" id="report-query-param-autoComplete" name="autoComplete"/></td>
                </tr>
                <tr>
                    <td>{{info.report_global_parameter_form_element}}</td>
                    <td><select class="easyui-combobox" id="report-query-param-formElement" name="formElement"
                                data-options="required:true" style="width:150px">
                        <option value="select">{{info.report_global_parameter_form_element_select}}</option>
                        <option value="selectMul">{{info.report_global_parameter_form_element_multselect}}</option>
                        <option value="checkbox">{{info.report_global_parameter_form_element_checkbox}}</option>
                        <option value="text">{{info.report_global_parameter_form_element_textbox}}</option>
                        <option value="date">{{info.report_global_parameter_form_element_date}}</option>
                    </select></td>
                    <td>{{info.report_global_parameter_datasource_type}}</td>
                    <td colspan="5"><select class="easyui-combobox" id="report-query-param-dataSource"
                                            name="dataSource" data-options="required:true" style="width:150px">
                        <option value="sql">{{info.report_global_parameter_datasource_type_sql}}</option>
                        <option value="text">{{info.report_global_parameter_datasource_type_text}}</option>
                        <option value="none">{{info.report_global_parameter_datasource_type_null}}</option>
                    </select></td>
                </tr>
                <tr>
                    <td>{{info.report_global_parameter_content}}</td>
                    <td colspan="7">
                    <textarea id="report-query-param-content" name="content" style="width: 95%; height: 100px;"
                              placeholder="(select col1 as name,col2 as text from table ...) or (name1,text1|name2,text2|...) or (name1|name2|...)"></textarea>
                        <input type="hidden" id="report-query-param-id" name="id" />
                        <input type="hidden" id="report-query-param-json" name="queryParams"/>
                </tr>
            </table>
        </form>
    </div>

</body>
</html>