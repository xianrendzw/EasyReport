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
    baseUrl: EasyReport.ctxPath + '/rest/member/permission/',
    baseModuleUrl: EasyReport.ctxPath + '/rest/member/module/',
    baseIconUrl: EasyReport.ctxPath + '/custom/easyui/themes/icons/'
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
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
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
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: PermMVC.Controller.add
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: PermMVC.Controller.edit
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: PermMVC.Controller.remove
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: '标识',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 80,
                    sortable: true
                }, {
                    field: 'code',
                    title: '代号',
                    width: 120,
                    sortable: true,
                }, {
                    field: 'sequence',
                    title: '顺序',
                    width: 50,
                    sortable: true
                }, {
                    field: 'comment',
                    title: '说明',
                    width: 100
                }, {
                    field: 'gmtCreated',
                    title: '创建时间',
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtModified',
                    title: '更新时间',
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
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#perm-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
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
                options.title = '新增[' + node.text + ']模块的权限';
                EasyUIUtils.openAddDlg(options);
                $('#moduleId').val(node.id);
                $('#code').textbox('setValue', node.attributes.code + ":");
                $('#sequence').textbox('setValue', 10);
            } else {
                $.messager.alert('警告', '请选中指定的模块!', 'info');
            }
        },
        edit: function () {
            var row = $('#perm-datagrid').datagrid('getSelected');
            if (row) {
                var options = PermMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.name + ']权限';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
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
