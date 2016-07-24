var membershipModulePageUrl = XFrame.getContextPath() + '/membership/module/';

$(function () {
    // 左边字典树
    $('#west').panel({
        tools: [{
                iconCls: 'icon-add',
                handler: MembershipModule.addRoot
            }, {
                iconCls: 'icon-reload',
                handler: function () {
                    MembershipModule.reloadTree();
                    EasyUIUtils.loadToDatagrid('#module-datagrid', membershipModulePageUrl + 'list?id=0');
                }
            }]
    });

    $('#module-tree').tree({
        checkbox: false,
        method: 'get',
        animate: true,
        dnd: true,
        onClick: function (node) {
            $('#module-tree').tree('expand', node.target);
            EasyUIUtils.loadToDatagrid('#module-datagrid', membershipModulePageUrl + 'list?id=' + node.id);
        },
        onDrop: function (target, source, point) {
            var targetNode = $('#module-tree').tree('getNode', target);
            if (targetNode) {
                $.post(membershipModulePageUrl + 'move', {
                    sourceId: source.id,
                    targetId: targetNode.id,
                    sourcePid: source.attributes.parentId
                }, function (data) {
                    if (!data.success) {
                        $.messager.alert('失败', data.msg, 'error');
                    }
                });
            }
        },
        onContextMenu: function (e, node) {
            e.preventDefault();
            $('#module-tree').tree('select', node.target);
            $('#tree_ctx_menu').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });

    $('#module-datagrid').datagrid({
        method: 'get',
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        url: membershipModulePageUrl + 'list',
        toolbar: [{
                iconCls: 'icon-add',
                handler: function () {
                    MembershipModule.add();
                }
            }, '-', {
                iconCls: 'icon-edit1',
                handler: function () {
                    MembershipModule.edit();
                }
            }, '-', {
                iconCls: 'icon-remove1',
                handler: function () {
                    MembershipModule.remove();
                }
            }, '-', {
                iconCls: 'icon-reload',
                handler: function () {
                    var node = $('#module-tree').tree('getSelected');
                    if (node) {
                        EasyUIUtils.loadToDatagrid('#module-datagrid', membershipModulePageUrl + 'list?id=' + node.id);
                    }
                }
            }],
        loadFilter: function (src) {
            if (src.success) {
                return src.data;
            }
            return $.messager.alert('失败', src.msg, 'error');
        },
        columns: [[{
                    field: 'moduleId',
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'parentId',
                    title: '父ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 50,
                    sortable: true,
                }, {
                    field: 'code',
                    title: '编号',
                    width: 100,
                    sortable: true
                }, {
                    field: 'icon',
                    title: '图标',
                    width: 20,
                    formatter: function (value, row, index) {
                        var fileName = value.replace("icon-", "");
                        var imgSrc = XFrame.getContextPath() + '/assets/custom/easyui/themes/icons/' + fileName;
                        return '<img src="' + imgSrc + '.png" alt="图标"/">'
                    }
                }, {
                    field: 'url',
                    title: 'URL',
                    width: 80,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value == 1 ? "启用" : "禁用";
                    }
                }, {
                    field: 'sequence',
                    title: '顺序',
                    width: 50,
                    sortable: true
                }, {
                    field: 'comment',
                    title: '说明',
                    width: 50,
                    sortable: true
                }, {
                    field: 'gmtCreate',
                    title: '创建时间',
                    width: 50,
                    sortable: true
                }]],
        onDblClickRow: function (rowIndex, rowData) {
            return MembershipModule.edit(rowIndex, rowData);
        }
    });

    // dialogs
    $('#add-module-dlg').dialog({
        closed: true,
        modal: false,
        width: 560,
        height: 450,
        iconCls: 'icon-add',
        buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#add-module-dlg").dialog('close');
                }
            }, {
                text: '保存',
                iconCls: 'icon-save',
                handler: MembershipModule.save
            }]
    });

    $('#edit-module-dlg').dialog({
        closed: true,
        modal: false,
        width: 560,
        height: 420,
        iconCls: 'icon-edit1',
        buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#edit-module-dlg").dialog('close');
                }
            }, {
                text: '保存',
                iconCls: 'icon-save',
                handler: MembershipModule.save
            }]
    });

    MembershipModule.loadModuleData();

    // end
});

var MembershipModule = {
    loadModuleData: function () {
        $.getJSON(membershipModulePageUrl + 'getmoduletree', function (src) {
            if (src.success) {
                $('#module-tree').tree('loadData', src.data);
            }
        });
    },
    treeContextMenu: function (item) {
        if (item.name == "add") {
            return MembershipModule.add();
        }
        if (item.name == "edit") {
            return MembershipModule.editNode();
        }
        if (item.name == "remove") {
            return MembershipModule.remove();
        }
        return;
    },
    addRoot: function () {
        var name = "主模块";
        var id = "0";
        MembershipModule.initAdd(id, name);
    },
    add: function () {
        var node = $('#module-tree').tree('getSelected');
        if (node) {
            name = node.attributes.name;
            id = node.id;
            MembershipModule.initAdd(id, name);
        } else {
            $.messager.alert('警告', '请选中一个父模块!', 'info');
        }
    },
    initAdd: function (id, name) {
        EasyUIUtils.add('#add-module-dlg', '#add-form', '#modal-action', '#moduleId', '新增[' + name + ']的子模块');
        $("#parentId").val(id);
        $("#parent-module-name").text(name);
        $("#sequence").textbox('setValue', 10);
        $('#linkType').combobox('setValue', '0');
        $('#target').combobox('setValue', '0');
        $('#status').combobox('setValue', '0');
    },
    editNode: function () {
        var node = $('#module-tree').tree('getSelected');
        if (node) {
            var row = node.attributes;
            EasyUIUtils.editWithData('#edit-module-dlg', '#edit-form', '#modal-action', '#edit-moduleId', '修改['
                    + row.name + ']模块', row);
        }
    },
    edit: function () {
        var row = $('#module-datagrid').datagrid('getSelected');
        if (row) {
            EasyUIUtils.editWithData('#edit-module-dlg', '#edit-form', '#modal-action', '#edit-moduleId', '修改['
                    + row.name + ']模块', row);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    remove: function () {
        var row = $('#module-datagrid').datagrid('getSelected');
        var node = $('#module-tree').tree('getSelected');
        node = node ? node.attributes : null;
        row = row || node;

        var postData = {
            id: row.moduleId,
            pid: row.parentId
        };
        EasyUIUtils.removeWithCallback(row, membershipModulePageUrl + 'remove', postData, function (data) {
            MembershipModule.refreshNode(row.parentId);
            EasyUIUtils.loadToDatagrid('#module-datagrid', membershipModulePageUrl + 'list?id=' + row.parentId);
        });
    },
    save: function () {
        var action = $('#modal-action').val();
        var dlgId = action == "edit" ? "#edit-module-dlg" : "#add-module-dlg";
        var formId = action == "edit" ? "#edit-form" : "#add-form";
        var parentId = action == "edit" ? "#edit-parentId" : "#parentId";
        var pid = $(parentId).val();
        var gridUrl = membershipModulePageUrl + 'list?id=' + pid;
        var actUrl = membershipModulePageUrl + action;
        EasyUIUtils.saveWithCallback(dlgId, formId, actUrl, function () {
            MembershipModule.refreshNode(pid);
            EasyUIUtils.loadToDatagrid('#module-datagrid', gridUrl);
        });
    },
    refreshNode: function (pid) {
        if (pid == "0") {
            this.reloadTree();
        } else {
            var node = $('#module-tree').tree('find', pid);
            if (node) {
                $('#module-tree').tree('select', node.target);
                $('#module-tree').tree('reload', node.target);
            }
        }
    },
    reloadTree: function () {
        $('#module-tree').tree('reload');
        MembershipModule.loadModuleData();
    }
};
