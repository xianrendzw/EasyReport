var permPageUrl = XFrame.getContextPath() + '/membership/permission/';
var modulePageUrl = XFrame.getContextPath() + '/membership/module/'
$(function () {
    // 左边模块树
    $('#west').panel({
        tools: [{
                iconCls: 'icon-add',
                handler: MembershipPerm.add
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
        url: modulePageUrl + 'getchildmodules',
        onClick: function (node) {
            $('#module-tree').tree('expand', node.target);
            $('#module-tree').tree('options').url = modulePageUrl + 'getchildmodules';
            EasyUIUtils.loadToDatagrid('#perm-datagrid', permPageUrl + 'list?id=' + node.id);
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
            return $.messager.alert('失败', src.msg, 'error');
        }
    });

    // 权限grid
    $('#perm-datagrid').datagrid({
        url: permPageUrl + 'list',
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
                handler: MembershipPerm.add
            }, '-', {
                text: '修改',
                iconCls: 'icon-edit1',
                handler: MembershipPerm.edit
            }, '-', {
                text: '删除',
                iconCls: 'icon-remove',
                handler: MembershipPerm.remove
            }],
        loadFilter: function (src) {
            if (src.success) {
                return src.data;
            }
            return $.messager.alert('失败', src.msg, 'error');
        },
        columns: [[{
                    field: 'id',
                    title: '标识',
                    width: 50
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
                    width: 50
                }, {
                    field: 'comment',
                    title: '说明',
                    width: 100
                }, {
                    field: 'gmtCreate',
                    title: '创建时间',
                    width: 100
                }, {
                    field: 'gmtModified',
                    title: '更新时间',
                    width: 100
                }]],
        onDblClickRow: function (index, row) {
            MembershipPerm.edit();
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
                handler: MembershipPerm.save
            }]
    });
});

var MembershipPerm = {
    treeContextMenu: function (item) {
        if (item.name == "add") {
            return MembershipPerm.add();
        }
        return;
    },
    add: function () {
        var node = $('#module-tree').tree('getSelected');
        if (node) {
            EasyUIUtils.add('#perm-dlg', '#perm-form', '#permAction', '#permId', '新增[' + node.text + ']模块的权限');
            $('#moduleId').val(node.id);
            $("#code").textbox('setValue', node.attributes.code + ":");
            $("#sequence").textbox('setValue', 10);
        } else {
            $.messager.alert('警告', '请选中指定的模块!', 'info');
        }
    },
    edit: function () {
        var row = $('#perm-datagrid').datagrid('getSelected');
        if (row) {
            EasyUIUtils.editWithData('#perm-dlg', '#perm-form', '#permAction', '#permId', '修改[' + row.name + ']权限', row);
            $("#perm-form").form('reset');
            $("#perm-form").form('load', row);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    remove: function () {
        var row = $('#perm-datagrid').datagrid('getSelected');
        EasyUIUtils.removeWithCallback(row, permPageUrl + 'remove', {
            id: row ? row.permissionId : 0
        }, function (data) {
            MembershipPerm.refreshNode(data.moduleId);
            EasyUIUtils.loadToDatagrid('#perm-datagrid', permPageUrl + 'list?id=' + data.moduleId);
        });
    },
    save: function () {
        var moduleId = $('#moduleId').val();
        var url = permPageUrl + 'list?id=' + moduleId;
        var actUrl = permPageUrl + $('#permAction').val();
        EasyUIUtils.saveWithCallback('#perm-dlg', '#perm-form', actUrl, function () {
            MembershipPerm.refreshNode(moduleId);
            EasyUIUtils.loadToDatagrid('#perm-datagrid', url);
        });
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
};