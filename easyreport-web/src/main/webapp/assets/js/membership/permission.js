$(function () {
    MembershipPerm.init();
});

var MembershipPerm = {
    init: function () {
        PermMVC.View.initControl();
        PermMVC.View.bindEvent();
        PermMVC.View.bindValidate();
    }
};

var PermCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/membership/permission/',
    baseModuleUrl: EasyReport.ctxPath + '/rest/membership/module/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/'
};

var PermMVC = {
    URLs: {
        add: {
            url: PermCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: PermCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: PermCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: PermCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getChildModules: {
            url: PermCommon.baseModuleUrl + 'getChildModules',
            method: 'GET'
        }
    },
    View: {
        initControl: function () {
            // 左边模块树
            $('#west').panel({
                tools: [{
                    iconCls: 'icon-add',
                    handler: PermMVC.Controller.add
                }, {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#module-tree').tree('reload');
                        EasyUIUtils.clearDatagridWithFilter('#perm-datagrid');
                    }
                }]
            });

            $('#module-tree').tree({
                checkbox: false,
                method: 'get',
                url: PermMVC.URLs.getChildModules.url,
                onClick: function (node) {
                    $('#module-tree').tree('expand', node.target);
                    $('#module-tree').tree('options').url = PermMVC.URLs.getChildModules.url;
                    EasyUIUtils.loadDataWithUrl('#perm-datagrid', PermMVC.URLs.list.url + '?id=' + node.id);
                },
                onContextMenu: function (e, node) {
                    e.preventDefault();
                    $('#module-tree').tree('select', node.target);
                    $('#tree_ctx_menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                },
                loadFilter: function (src, parent) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('permission.failed'), src.msg, 'error');
                },
                formatter:function(node){
            		return jQuery.i18n.prop(node.text);
            	}
            });

            $('#tree_ctx_menu').menu({
                onClick: function (item) {
                    if (item.name == "add") {
                        return PermMVC.Controller.add();
                    }
                }
            });

            // 权限grid
            $('#perm-datagrid').datagrid({
                url: PermMVC.URLs.list.url,
                method: 'get',
                pageSize: 50,
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                toolbar: [{
                    text: jQuery.i18n.prop('permission.add'),
                    iconCls: 'icon-add',
                    handler: PermMVC.Controller.add
                }, '-', {
                    text: jQuery.i18n.prop('permission.modify'),
                    iconCls: 'icon-edit1',
                    handler: PermMVC.Controller.edit
                }, '-', {
                    text: jQuery.i18n.prop('permission.del'),
                    iconCls: 'icon-remove',
                    handler: PermMVC.Controller.remove
                }],
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('module.failed'), src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('permission.id'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: jQuery.i18n.prop('permission.name'),
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return jQuery.i18n.prop(row.code);
                    }
                }, {
                    field: 'code',
                    title: jQuery.i18n.prop('permission.code'),
                    width: 120,
                    sortable: true,
                }, {
                    field: 'sequence',
                    title: jQuery.i18n.prop('permission.sequence'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'comment',
                    title: jQuery.i18n.prop('permission.comment'),
                    width: 100
                }, {
                    field: 'gmtCreated',
                    title: jQuery.i18n.prop('permission.gmtCreate'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtModified',
                    title: jQuery.i18n.prop('permission.gmtmodify'),
                    width: 100,
                    sortable: true
                }]],
                onDblClickRow: function (index, row) {
                    PermMVC.Controller.edit();
                }
            });

            $('#perm-dlg').dialog({
                closed: true,
                modal: true,
                width: 500,
                height: 300,
                iconCls: 'icon-save',
                buttons: [{
                    text: jQuery.i18n.prop('permission.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#perm-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('permission.save'),
                    iconCls: 'icon-save',
                    handler: PermMVC.Controller.save
                }]
            });
            //end
        },
        bindEvent: function () {
        },
        bindValidate: function () {
        }
    },
    Controller: {
        add: function () {
            var node = $('#module-tree').tree('getSelected');
            if (node) {
                var options = PermMVC.Util.getOptions();
                options.title = jQuery.i18n.prop('permission.add.module.permission',node.text);
                EasyUIUtils.openAddDlg(options);
                $('#moduleId').val(node.id);
                $('#code').textbox('setValue', node.attributes.code + ":");
                $('#sequence').textbox('setValue', 10);
            } else {
                $.messager.alert(jQuery.i18n.prop('permission.warn'), jQuery.i18n.prop('permission.pealse.select.module'), 'info');
            }
        },
        edit: function () {
            var row = $('#perm-datagrid').datagrid('getSelected');
            if (row) {
                var options = PermMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = jQuery.i18n.prop('permission.change.permission',options.data.name);
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert(jQuery.i18n.prop('permission.warn'), jQuery.i18n.prop('permission.please.select.record'), 'info');
            }
        },
        remove: function () {
            var row = $('#perm-datagrid').datagrid('getSelected');
            var options = {
                rows: [row],
                url: PermMVC.URLs.remove.url,
                data: {
                    id: row ? row.id : 0
                },
                callback: function (rows) {
                    var row = rows[0];
                    PermMVC.Controller.refreshNode(row.moduleId);
                    EasyUIUtils.loadDataWithUrl('#perm-datagrid', PermMVC.URLs.list.url + '?id=' + row.moduleId);
                }
            };
            EasyUIUtils.remove(options);
        },
        save: function () {
            var action = $('#modal-action').val();
            var moduleId = $('#moduleId').val();
            var gridUrl = PermMVC.URLs.list.url + '?id=' + moduleId;
            var actUrl = action === "edit" ? PermMVC.URLs.edit.url : PermMVC.URLs.add.url;
            var options = {
                dlgId: "#perm-dlg",
                formId: "#perm-form",
                url: actUrl,
                callback: function () {
                    PermMVC.Controller.refreshNode(0);
                    EasyUIUtils.loadDataWithUrl('#perm-datagrid', gridUrl);
                }
            };
            EasyUIUtils.save(options);
        },
        refreshNode: function (pid) {
            if (pid == "0") {
                $('#module-tree').tree('reload');
            } else {
                var node = $('#module-tree').tree('find', pid);
                $('#module-tree').tree('select', node.target);
                $('#module-tree').tree('reload', node.target);
            }
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#perm-dlg',
                formId: '#perm-form',
                actId: '#modal-action',
                rowId: '#permId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        }
    }
};
