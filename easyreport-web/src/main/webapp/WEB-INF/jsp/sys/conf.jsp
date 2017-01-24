<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>System Configuration</title>
    <%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
    <script src="${ctxPath}/assets/js/sys/conf.js?v=${version}"></script>
</head>
<body  ng-app="i18n" ng-controller="translate" class="easyui-layout" id="body-layout">
<!-- left tree -->
<div id="west" data-options="region:'west',split:true" title="{{info.sysconf_config_class}}" style="width: 220px;">
    <div class="easyui-panel" style="padding: 5px; border: none">
        <ul id="dict-tree"></ul>
    </div>
</div>
<!-- right -->
<div region="center" data-options="region:'center'">
    <div id="configDictDiv" style="width: 100%; height: 99%">
        <div id="dict-datagrid"></div>
        <input id="modal-action" type="hidden" name="action" value=""/>
    </div>
</div>
<!-- dict dialog  -->
<div id="dict-dlg">
    <form id="dict-form" name="dict-form" method="post">
        <center>
            <table cellpadding="5" style="margin: 30px auto" class="form-table">
                <tr id="confParentNameDiv">
                    <td>{{info.sysconf_dict_parent}}</td>
                    <td colspan="3"><label id="confParentName"></label></td>
                </tr>
                <tr>
                    <td>{{info.sysconf_dict_name}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="name" id="name"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.sysconf_dict_key}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="key" id="key"
                                           data-options="required:true"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.sysconf_dict_value}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="value" id="value"
                                           data-options="required:true,multiline:true"
                                           style="width: 380px;height:150px"/></td>
                </tr>
                <tr>
                    <td>{{info.sysconf_dict_sequence}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="sequence" id="sequence" value="10"
                                           data-options="required:true,validType:'digit'"
                                           style="width: 380px"/></td>
                </tr>
                <tr>
                    <td>{{info.sysconf_dict_comment}}</td>
                    <td colspan="3"><input class="easyui-textbox" type="text" name="comment" id="comment"
                                           style="width: 380px"/>
                        <input id="confPid" type="hidden" name="parentId" value="0"/>
                        <input id="confId" type="hidden" name="id" value="0"/>
                    </td>
                </tr>
            </table>
        </center>
    </form>
</div>
<!-- search tree node dialog  -->
<div id="search-node-dlg" title="{{info.sysconf_treenode_search}}">
    <div id="toolbar" class="toolbar">
        {{info.sysconf_treenode_option}}<select class="easyui-combobox" id="field-name" name="fieldName" style="width: 120px">
        <option value="name">{{info.sysconf_treenode_name}}</option>
        <option value="key">{{info.sysconf_treenode_key}}</option>
        <option value="value">{{info.sysconf_treenode_value}}</option>
    </select>{{info.sysconf_treenode_keyword}}<input class="easyui-textbox" type="text" id="keyword" name="keyword"/>
        <a id="btn-search" href="#" class="easyui-linkbutton" iconCls="icon-search">{{info.sysconf_treenode_search_act}}</a>
    </div>
    <div style="height: 86%; padding: 2px">
        <div id="search-node-result"></div>
    </div>
</div>
<!-- tree rkey menu -->
<div id="tree_ctx_menu" class="easyui-menu" style="width: 120px;">
    <div id="m-add" data-options="name:'add',iconCls:'icon-add'">{{info.sysconf_rkey_add}}</div>
    <div id="m-edit" data-options="name:'edit',iconCls:'icon-edit'">{{info.sysconf_rkey_modify}}</div>
    <div id="m-remove" data-options="name:'remove',iconCls:'icon-remove'">{{info.sysconf_rkey_del}}</div>
    <div class="menu-sep"></div>
    <div id="m-search" data-options="name:'find',iconCls:'icon-search'">{{info.sysconf_rkey_search}}</div>
</div>
</body>
</html>