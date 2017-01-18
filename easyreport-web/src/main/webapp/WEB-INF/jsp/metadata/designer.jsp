<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Report Designer</title>
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
    <script src="${ctxPath}/assets/js/metadata/designer.js?v=${version}"></script>
</head>
<body ng-app="i18n" ng-controller="translate" class="easyui-layout" id="body-layout">
<!-- left report class tree -->
<div id="west" data-options="region:'west',split:true" title="{{info.report_category}}" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="category-tree"></ul>
        <input type="hidden" id="copyNodeId" name="copyNodeId" value="0"/>
        <input id="modal-action" type="hidden" name="action" value=""/>
        <input type="hidden" id="current-row-index" name="current-row-index" value="0"/>
    </div>
</div>
<!-- right report list -->
<div region="center" data-options="region:'center'">
    <div id="toolbar1" class="toolbar">
       {{info.report_name}}<input class="easyui-textbox" type="text" id="report-search-keyword" name="keyword" style="width:250px"/>
        <a id="btn-report-search" href="#" class="easyui-linkbutton" iconCls="icon-search">{{info.report_search_act}}</a>
    </div>
    <div style="width: 100%; height: 94%;padding-top: 1px">
        <div id="report-datagrid"></div>
    </div>
</div>
<!-- report category dialog -->
<div id="category-dlg">
    <form id="category-form" name="category-form" method="post">
        <center>
            <table cellpadding="0" class="form-table" cellspacing="0" style="width: 100%;">
                <tr>
                    <td>{{info.report_category_parent}}</td>
                    <td colspan="3"><label id="category-parentName"></label></td>
                </tr>
                <tr>
                    <td>{{info.report_category_name}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="category-name" name="name"
                                           data-options="required:true" style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.report_category_status}}</td>
                    <td><select class="easyui-combobox" id="category-status" name="status" style="width: 148px">
                        <option selected="selected" value="1">{{info.report_class_status_enable}}</option>
                        <option value="0">{{info.report_class_status_disable}}</option>
                    </select></td>
                    <td>{{info.report_category_sequence}}</td>
                    <td><input class="easyui-textbox" type="text" id="category-sequence" name="sequence"
                               data-options="required:true,validType:'digit'" style="width: 138px"/></td>
                </tr>
                <tr>
                    <td>{{info.report_category_description}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" id="category-comment" name="comment"
                                           style="width: 380px"/>
                        <input id="category-parentId" type="hidden" name="parentId" value="0"/></td>
                    <input id="category-id" type="hidden" name="id" value=""/></td>
                </tr>
            </table>
        </center>
    </form>
</div>
<!-- search category tree node dialog  -->
<div id="category-search-dlg" title="{{info.report_category_search}}">
    <div id="toolbar2" class="toolbar">
       {{info.report_category_name}}<input class="easyui-textbox" type="text" id="category-search-keyword" name="keyword" style="width:250px"/>
        <a id="btn-category-search" href="#"
           class="easyui-linkbutton" iconCls="icon-search">{{info.report_category_search_act}} </a>
    </div>
    <div style="height: 90%; padding-top: 1px">
        <div id="category-search-result-grid"></div>
    </div>
</div>
<!-- report designer -->
<div id="report-designer-dlg">
    <div id="tabs" class="easyui-tabs" fit="true" border="false" plain="true">
        <div id="report-basic-conf-tab" title="{{info.report_disign_conf}}" style="padding: 5px;height:40%">
            <div id="report-basic-conf-form-div">
                <form id="report-basic-conf-form" name="report-basic-conf-form" method="post">
                    <table class="designer-table" style="width: 95%">
                        <tr>
                            <td>{{info.report_disign_conf_name}}</td>
                            <td><input class="easyui-textbox" type="text" id="report-name" name="name"
                                       data-options="required:true" style="width:200px"/>
                            </td>
                            <td>{{info.report_disign_conf_datasource}}</td>
                            <td><select class="easyui-combobox" id="report-dsId" name="dsId"
                                        data-options="required:true,valueField:'id',textField:'name'"
                                        style="width:150px">
                            </select>
                            </td>
                            <td>{{info.report_disign_conf_layout_col}}</td>
                            <td><select class="easyui-combobox" id="report-layout" name="layout"
                                        data-options="required:true" style="width:100px">
                                <option value="1">{{info.report_disign_conf_horizental}}</option>
                                <option value="2">{{info.report_disign_conf_virtical}}</option>
                            </select>
                            </td>
                            <td>{{info.report_disign_conf_statistic_col}}</td>
                            <td><select class="easyui-combobox" id="report-statColumnLayout" name="statColumnLayout"
                                        data-options="required:true" style="width:100px">
                                <option value="1">{{info.report_disign_conf_horizental}}</option>
                                <option value="2">{{info.report_disign_conf_virtical}}</option>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td>{{info.report_disign_conf_date_range}}</td>
                            <td><input class="easyui-textbox" type="text" id="report-dataRange" name="dataRange"
                                       value="7"
                                       data-options="required:true,validType:'digit'" style="width:200px"/></td>
                            <td>{{info.report_disign_conf_status}}</td>
                            <td><select class="easyui-combobox" id="report-status" name="status"
                                        data-options="required:true" style="width:150px">
                                <option value="0">{{info.report_disign_conf_status_disable}}</option>
                                <option value="1" selected="selected">{{info.report_disign_conf_status_enable}}</option>
                            </select>
                            </td>
                            <td>{{info.report_disign_conf_sequence}}</td>
                            <td>
                                <input class="easyui-textbox" type="text" id="report-sequence" name="sequence"
                                       value="100"
                                       data-options="required:true,validType:'digit'" style="width:100px"/>
                            </td>
                            <td>{{info.report_disign_conf_category}}</td>
                            <td><label id="report-category-name"/></td>
                        </tr>
                        <tr>
                            <td>{{info.report_disign_conf_sql}}</td>
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
                                   iconCls="icon-ok">{{info.report_disign_conf_sql_exe}}</a>&nbsp;&nbsp;
                                <a id="btn-report-preview-sql" href="#" class="easyui-linkbutton"
                                   iconCls="icon-sql">{{info.report_disign_conf_sql_preview}}</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="report-meta-column-div" style="height:180px;">
                <div id="report-meta-column-grid" title="{{info.report_disign_conf_meta}}"></div>
            </div>
        </div>
        <div id="report-query-param-tab" title="{{info.report_disign_conf_parameter}}" style="padding: 5px;">
            <div id="report-query-param-form-div">
                <form id="report-query-param-form" method="post">
                    <table class="designer-table">
                        <tr>
                            <td>{{info.report_disign_conf_parameter_name}}</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-name" name="name"
                                       data-options="required:true" style="width:150px"/></td>
                            <td>{{info.report_disign_conf_parameter_title}}</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-text" name="text"
                                       data-options="required:true" style="width:150px"/></td>
                            <td>{{info.report_disign_conf_parameter_default_value}}</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-defaultValue"
                                       name="defaultValue" style="width:150px"/></td>
                            <td>{{info.report_disign_conf_parameter_default_title}}</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-defaultText"
                                       name="defaultText" style="width:150px"/></td>
                        </tr>
                        <tr>
                            <td>{{info.report_disign_conf_parameter_datatype}}</td>
                            <td><select class="easyui-combobox" id="report-query-param-dataType" name="dataType"
                                        data-options="required:true" style="width:150px">
                                <option value="string" selected="selected">{{info.report_disign_conf_parameter_datatype_string}}</option>
                                <option value="float">{{info.report_disign_conf_parameter_datatype_float}}</option>
                                <option value="integer">{{info.report_disign_conf_parameter_datatype_integer}}</option>
                                <option value="date">{{info.report_disign_conf_parameter_datatype_date}}</option>
                            </select></td>
                            <td>{{info.report_disign_conf_parameter_length}}</td>
                            <td><input class="easyui-textbox" type="text" id="report-query-param-width" name="width"
                                       value="100" data-options="validType:'digit'" style="width:150px"/></td>
                            <td>{{info.report_disign_conf_parameter_required}}</td>
                            <td><input type="checkbox" id="report-query-param-required"
                                       name="required"/></td>
                            <td>{{info.report_disign_conf_parameter_autocomplete}}</td>
                            <td><input type="checkbox" id="report-query-param-autoComplete" name="autoComplete"/></td>
                        </tr>
                        <tr>
                            <td>{{info.report_disign_conf_parameter_form_element}}</td>
                            <td><select class="easyui-combobox" id="report-query-param-formElement" name="formElement"
                                        data-options="required:true" style="width:150px">
                                <option value="select">{{info.report_disign_conf_parameter_form_element_select}}</option>
                                <option value="selectMul">{{info.report_disign_conf_parameter_form_element_multselect}}</option>
                                <option value="checkbox">{{info.report_disign_conf_parameter_form_element_checkbox}}</option>
                                <option value="text">{{info.report_disign_conf_parameter_form_element_textbox}}</option>
                                <option value="date">{{info.report_disign_conf_parameter_form_element_date}}</option>
                            </select></td>
                            <td>{{info.report_disign_conf_parameter_datasource_type}}</td>
                            <td colspan="5"><select class="easyui-combobox" id="report-query-param-dataSource"
                                                    name="dataSource" data-options="required:true" style="width:150px">
                                <option value="sql">{{info.report_disign_conf_parameter_datasource_type_sql}}</option>
                                <option value="text">{{info.report_disign_conf_parameter_datasource_type_text}}</option>
                                <option value="none">{{info.report_disign_conf_parameter_datasource_type_null}}</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td>{{info.report_disign_conf_parameter_content}}</td>
                            <td colspan="7">
                            <textarea id="report-query-param-content" name="content" style="width: 95%; height: 100px;"
                                      placeholder="(select col1 as name,col2 as text from table ...) or (name1,text1|name2,text2|...) or (name1|name2|...)"></textarea>
                                <input type="hidden" id="report-query-param-gridIndex" value="0"/>
                                <input type="hidden" id="report-query-param-json"/>
                        </tr>
                        <tr>
                            <td colspan="8" style="text-align: center;">
                                <a id="btn-report-query-param-add" href="#" class="easyui-linkbutton"
                                   iconCls="icon-add">{{info.report_disign_conf_parameter_add}}</a>&nbsp;&nbsp;
                                <a id="btn-report-query-param-edit" href="#" class="easyui-linkbutton"
                                   iconCls="icon-edit">{{info.report_disign_conf_parameter_modify}}</a>
                        </tr>
                    </table>
                </form>
            </div>
            <div id="report-query-param-div" style="height:180px;">
                <div id="report-query-param-grid" title="{{info.report_disign_conf_parameter_list}}"></div>
            </div>
        </div>
    </div>
</div>
<!-- report detail dialog -->
<div id="report-detail-dlg" title="{{info.report_disign_detail}}">
    <table class="designer-table detail">
        <tr>
            <td>{{info.report_disign_detail_name}}</td>
            <td><label id="report-detail-name"/></td>
            <td>{{info.report_disign_detail_id}}</td>
            <td><label id="report-detail-id"/></td>
            <td>{{info.report_disign_detail_uid}}</td>
            <td><label id="report-detail-uid"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_categoryid}}</td>
            <td><label id="report-detail-categoryId"/></td>
            <td>{{info.report_disign_detail_categoryname}}</td>
            <td><label id="report-detail-categoryName"/></td>
            <td>{{info.report_disign_detail_status}}</td>
            <td><label id="report-detail-status"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_dsid}}</td>
            <td><label id="report-detail-dsId"/></td>
            <td>{{info.report_disign_detail_dsname}}</td>
            <td><label id="report-detail-dsName"/></td>
            <td>{{info.report_disign_detail_sequence}}</td>
            <td><label id="report-detail-sequence"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_layout}}</td>
            <td><label id="report-detail-layout"/></td>
            <td>{{info.report_disign_detail_statlayout}}</td>
            <td><label id="report-detail-statColumnLayout"/></td>
            <td>{{info.report_disign_detail_daterange}}</td>
            <td><label id="report-detail-dataRange"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_createuser}}</td>
            <td><label id="report-detail-createUser"/></td>
            <td>{{info.report_disign_detail_creategmt}}</td>
            <td><label id="report-detail-gmtCreated"/></td>
            <td>{{info.report_disign_detail_updategmt}}</td>
            <td><label id="report-detail-gmtModified"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_sqltext}}</td>
            <td colspan="5" class="code"><label id="report-detail-sqlText"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_sql_metacolumns}}</td>
            <td colspan="5" class="code"><label id="report-detail-metaColumns"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_queryparameter}}</td>
            <td colspan="5" class="code"><label id="report-detail-queryParams"/></td>
        </tr>
        <tr>
            <td>{{info.report_disign_detail_description}}</td>
            <td colspan="5">
                <label id="report-detail-comment"/>
            </td>
        </tr>
    </table>
</div>

<div id="report-describe-dlg" title="{{info.report_disign_desc}}">
    <table class="designer-table detail">
        <tr>
            <td>{{info.report_disign_desc_name}}</td>
            <td><label id="report-desc-detail-name"/></td>
            <td>{{info.report_disign_desc_id}}</td>
            <td><label id="report-desc-detail-id"/></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>{{info.report_disign_desc_description}}</td>
            <td colspan="5">
                <textarea id="report-desc-detail-comment" rows="10" cols="100"></textarea>
            </td>
        </tr>
    </table>
</div>

<!-- report sql history-->
<div id="report-history-sql-dlg" title="{{info.report_disign_sqlhistory}}">
    <div style="height: 200px">
        <textarea id="report-history-sqlText" name="{{info.report_disign_sqlhistory_sqltext}}"></textarea>
    </div>
    <div id="report-history-sql-grid-div" class="easyui-layout" style="width:96%;height:60%;margin:5px;">
        <div data-options="region:'west',title:'{{info.report_disign_sqlhistory_history}}'" style="padding:10px;width:500px;">
            <div id="report-history-sql-grid"></div>
        </div>
        <div data-options="region:'center',split:true,title:'{{info.report_disign_sqlhistory_property}}',collapsible:false">
            <div id="report-history-sql-pgrid" class="easyui-propertygrid"></div>
        </div>
    </div>
</div>
<!-- priview SQL  -->
<div id="report-preview-sql-dlg" title="{{info.report_disign_sqlpreview}}">
    <textarea id="report-preview-sqlText" name="sqlText" style="height:100%"></textarea>
</div>
<!-- column expression dialog  -->
<div id="report-column-expression-dlg" title="{{info.report_disign_colexpression}}">
    <table class="designer-table" style="height: 90%">
        <tr>
            <td class="top">
                <textarea id="report-column-expression" name="expression"
                          style="border:solid 1px; width: 98%; height: 215px;"></textarea></td>
        </tr>
    </table>
</div>
<!-- column comment dialog  -->
<div id="report-column-comment-dlg" title="{{info.report_disign_colcomment}}">
    <table class="designer-table" style="height: 90%">
        <tr>
            <td class="top"><textarea id="report-column-comment" name="comment"
                                      style="border:solid 1px; width: 98%; height: 215px;"></textarea>
            </td>
        </tr>
    </table>
</div>
<!-- tree rkey  -->
<div id="category-tree-ctx-menu" class="easyui-menu" style="width: 150px;">
    <div id="m-add-cate" data-options="name:'addCate',iconCls:'icon-category'">{{info.report_disign_category_rkey_addcategory}}</div>
    <div id="m-add-report" data-options="name:'addReport',iconCls:'icon-report'">{{info.report_disign_category_rkey_addreport}}</div>
    <div class="menu-sep"></div>
    <div id="m-copy" data-options="name:'copy',iconCls:'icon-copy'">{{info.report_disign_category_rkey_copy}}</div>
    <div id="m-paste" data-options="name:'paste',iconCls:'icon-paste',disabled:'true'">{{info.report_disign_category_rkey_paste}}</div>
    <div id="m-edit" data-options="name:'edit',iconCls:'icon-edit'">{{info.report_disign_category_rkey_modify}}</div>
    <div id="m-remove" data-options="name:'remove',iconCls:'icon-remove'">{{info.report_disign_category_rkey_del}}</div>
    <div id="m-search" data-options="name:'search',iconCls:'icon-search'">{{info.report_disign_category_rkey_search}}</div>
    <div class="menu-sep"></div>
    <div id="m-refresh" data-options="name:'refresh',iconCls:'icon-reload'">{{info.report_disign_category_rkey_refresh}}</div>
</div>
<!-- report rkey menu  -->
<div id="report-datagrid-ctx-menu" class="easyui-menu" style="width: 150px;">
    <div id="rp-preview" data-options="name:'preview',iconCls:'icon-preview'">{{info.report_disign_report_rkey_preview}}</div>
    <div id="rp-window" data-options="name:'window',iconCls:'icon-window'">{{info.report_disign_report_rkey_previewnewwin}}</div>
    <div class="menu-sep"></div>
    <div id="rp-add" data-options="name:'add',iconCls:'icon-add'">{{info.report_disign_report_rkey_add}}</div>
    <div id="rp-edit" data-options="name:'edit',iconCls:'icon-edit1'">{{info.report_disign_report_rkey_modify}}</div>
    <div id="rp-copy" data-options="name:'copy',iconCls:'icon-copy'">{{info.report_disign_report_rkey_copy}}</div>
    <div id="rp-remove" data-options="name:'remove',iconCls:'icon-remove'">{{info.report_disign_report_rkey_del}}</div>
    <div class="menu-sep"></div>
    <div id="rp-history" data-options="name:'history',iconCls:'icon-history'">{{info.report_disign_report_rkey_history}}</div>
    <div id="rp-info" data-options="name:'info',iconCls:'icon-info'">{{info.report_disign_report_rkey_detail}}</div>
    <div id="rp-desc" data-options="name:'desc',iconCls:'icon-desc'">{{info.report_disign_report_rkey_desc}}</div>
    <div id="rp-refresh" data-options="name:'refresh',iconCls:'icon-reload'">{{info.report_disign_report_rkey_refresh}}</div>
</div>
</body>
</html>