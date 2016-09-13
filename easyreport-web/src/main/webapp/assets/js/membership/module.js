$(function () {
    MembershipModule.init();
});

var MembershipModule = {
    init: function () {
        ModuleMVC.View.initControl();
        ModuleMVC.View.bindEvent();
        ModuleMVC.View.bindValidate();
        ModuleMVC.Controller.loadModuleData();
    }
};

var ModuleCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/membership/module/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/'
};

var ModuleMVC = {
    URLs: {
        list: {
            url: ModuleCommon.baseUrl + 'list',
            method: 'POST'
        },
        move: {
            url: ModuleCommon.baseUrl + 'move',
            method: 'POST'
        },
        remove: {
            url: ModuleCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getModuleTree: {
            url: ModuleCommon.baseUrl + 'getModuleTree',
            method: 'POST'
        }
    },
    View: {
        initControl: function () {
            // 左边字典树
            $('#west').panel({
                tools: [{
                    iconCls: 'icon-add',
                    handler: ModuleMVC.Controller.addRoot
                }, {
                    iconCls: 'icon-reload',
                    handler: function () {
                        ModuleMVC.Controller.reloadTree();
                        EasyUIUtils.loadToDatagrid('#module-datagrid', ModuleMVC.URLs.list.url + '?id=0');
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
                    EasyUIUtils.loadToDatagrid('#module-datagrid', ModuleMVC.URLs.list.url + '?id=' + node.id);
                },
                onDrop: function (target, source, point) {
                    var targetNode = $('#module-tree').tree('getNode', target);
                    if (targetNode) {
                        $.post(ModuleMVC.URLs.move.url, {
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

            $('tree_ctx_menu').menu({
                onClick: function (item) {
                    if (item.name == "add") {
                        return ModuleMVC.Controller.add();
                    }
                    if (item.name == "edit") {
                        return ModuleMVC.Controller.editNode();
                    }
                    if (item.name == "remove") {
                        return ModuleMVC.Controller.remove();
                    }
                    return;
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
                url: ModuleMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        ModuleMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        ModuleMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        ModuleMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        var node = $('#module-tree').tree('getSelected');
                        if (node) {
                            EasyUIUtils.loadToDatagrid('#module-datagrid', ModuleMVC.URLs.list.url + '?id=' + node.id);
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
                    field: 'id',
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
                        var imgSrc = ModuleCommon.baseIconUrl + fileName;
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
                    return ModuleMVC.Controller.edit(rowIndex, rowData);
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
                    handler: ModuleMVC.Controller.save
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
                    handler: ModuleMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
        },
        bindValidate: function () {
        }
    },
    Controller: {
        addRoot: function () {
            var name = "主模块";
            var id = "0";
            ModuleMVC.Controller._initAdd(id, name);
        },
        add: function () {
            var node = $('#module-tree').tree('getSelected');
            if (node) {
                var name = node.attributes.name;
                var id = node.id;
                ModuleMVC.Controller._initAdd(id, name);
            } else {
                $.messager.alert('警告', '请选中一个父模块!', 'info');
            }
        },
        _initAdd: function (id, name) {
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

            var data = {
                id: row.id,
                pid: row.parentId
            };
            EasyUIUtils.removeWithCallback(row, ModuleMVC.URLs.remove.url, data, function () {
                ModuleMVC.Controller.refreshNode(row.parentId);
                EasyUIUtils.loadToDatagrid('#module-datagrid', ModuleMVC.URLs.list.url + '?id=' + row.parentId);
            });
        },
        save: function () {
            var action = $('#modal-action').val();
            var dlgId = action == "edit" ? "#edit-module-dlg" : "#add-module-dlg";
            var formId = action == "edit" ? "#edit-form" : "#add-form";
            var parentId = action == "edit" ? "#edit-parentId" : "#parentId";
            var pid = $(parentId).val();
            var gridUrl = ModuleMVC.URLs.list.url + '?id=' + pid;
            var actUrl = ModuleCommon.baseUrl + action;
            EasyUIUtils.saveWithCallback(dlgId, formId, actUrl, function () {
                ModuleMVC.Controller.refreshNode(pid);
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
            ModuleMVC.Controller.loadModuleData();
        },
        loadModuleData: function () {
            $.getJSON(ModuleMVC.URLs.getModuleTree.url, function (src) {
                if (src.success) {
                    $('#module-tree').tree('loadData', src.data);
                }
            });
        }
    }
};
