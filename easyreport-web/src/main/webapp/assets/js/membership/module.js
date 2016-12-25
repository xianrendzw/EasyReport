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
        add: {
            url: ModuleCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: ModuleCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: ModuleCommon.baseUrl + 'list',
            method: 'GET'
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
            method: 'GET'
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
                        EasyUIUtils.loadDataWithUrl('#module-datagrid', ModuleMVC.URLs.list.url + '?id=0');
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
                    EasyUIUtils.loadDataWithUrl('#module-datagrid', ModuleMVC.URLs.list.url + '?id=' + node.id);
                },
                onDrop: function (target, source, point) {
                    var targetNode = $('#module-tree').tree('getNode', target);
                    if (targetNode) {
                        $.post(ModuleMVC.URLs.move.url, {
                            sourceId: source.id,
                            targetId: targetNode.id,
                            sourcePid: source.attributes.parentId,
                            sourcePath: source.attributes.path
                        }, function (data) {
                            if (!data.success) {
                                $.messager.alert(jQuery.i18n.prop('module.failed'), data.msg, 'error');
                            }
                        }, 'json');
                    }
                },
                onContextMenu: function (e, node) {
                    e.preventDefault();
                    $('#module-tree').tree('select', node.target);
                    $('#tree_ctx_menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                },
                formatter:function(node){
            		return jQuery.i18n.prop(node.text);
            	}
            });

            $('#tree_ctx_menu').menu({
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
                            EasyUIUtils.loadDataWithUrl('#module-datagrid', ModuleMVC.URLs.list.url + '?id=' + node.id);
                        }
                    }
                }],
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('module.failed'), src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('module.id'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'parentId',
                    title: jQuery.i18n.prop('module.parentId'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: jQuery.i18n.prop('module.name'),
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return jQuery.i18n.prop(row.code);
                    }
                }, {
                    field: 'code',
                    title: jQuery.i18n.prop('module.code'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'icon',
                    title: jQuery.i18n.prop('module.icon'),
                    width: 20,
                    formatter: function (value, row, index) {
                        var fileName = value.replace("icon-", "");
                        var imgSrc = ModuleCommon.baseIconUrl + fileName;
                        return '<img src="' + imgSrc + '.png" alt="'+jQuery.i18n.prop('module.icon')+'"/>'
                    }
                }, {
                    field: 'url',
                    title: jQuery.i18n.prop('module.url'),
                    width: 80,
                    sortable: true
                }, {
                    field: 'status',
                    title: jQuery.i18n.prop('module.status'),
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value == 1 ? jQuery.i18n.prop('module.status.enable') : jQuery.i18n.prop('module.status.disable');
                    }
                }, {
                    field: 'sequence',
                    title: jQuery.i18n.prop('module.sequence'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'comment',
                    title: jQuery.i18n.prop('module.comment'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: jQuery.i18n.prop('module.gmtcreate'),
                    width: 50,
                    sortable: true
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ModuleMVC.Controller.edit(rowIndex, rowData);
                }
            });

            // dialogs
            $('#module-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 480,
                iconCls: 'icon-add',
                buttons: [{
                    text: jQuery.i18n.prop('module.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#module-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('module.save'),
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
            var name = jQuery.i18n.prop('module.rootmodule');
            var id = "0";
            ModuleMVC.Util.initAdd(id, name);
        },
        add: function () {
            var node = $('#module-tree').tree('getSelected');
            if (node) {
                var name = node.attributes.name;
                var id = node.id;
                ModuleMVC.Util.initAdd(id, name);
            } else {
                $.messager.alert(jQuery.i18n.prop('module.warn'), jQuery.i18n.prop('module.please.select.parent'), 'info');
            }
        },
        editNode: function () {
            var node = $('#module-tree').tree('getSelected');
            if (node) {
                ModuleMVC.Util.edit(node.attributes);
            }
        },
        edit: function () {
            var row = $('#module-datagrid').datagrid('getSelected');
            ModuleMVC.Util.edit(row);
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
            var options = {
                rows: [row],
                url: ModuleMVC.URLs.remove.url,
                data: data,
                callback: function (rows) {
                    var row = rows[0];
                    ModuleMVC.Controller.refreshNode(row.parentId);
                    EasyUIUtils.loadDataWithUrl('#module-datagrid', ModuleMVC.URLs.list.url + '?id=' + row.parentId);
                }
            };
            EasyUIUtils.remove(options);
        },
        save: function () {
            var action = $('#modal-action').val();
            var gridUrl = ModuleMVC.URLs.list.url + '?id=' + $("#parentId").val();
            var actUrl = action === "edit" ? ModuleMVC.URLs.edit.url : ModuleMVC.URLs.add.url;
            var options = {
                dlgId: "#module-dlg",
                formId: "#module-form",
                url: actUrl,
                callback: function () {
                    ModuleMVC.Controller.reloadTree();
                    EasyUIUtils.loadDataWithUrl('#module-datagrid', gridUrl);
                }
            };
            EasyUIUtils.save(options);
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
    },
    Util: {
        initAdd: function (id, name) {
            var options = ModuleMVC.Util.getOptions();
            options.title = jQuery.i18n.prop('module.add.submodule',name);
            EasyUIUtils.openAddDlg(options);

            $('#tr-parent-module-name').show();
            $("#parentId").val(id);
            $("#parent-module-name").text(name);
            $("#sequence").textbox('setValue', 10);
            $('#linkType').combobox('setValue', '0');
            $('#target').combobox('setValue', '0');
            $('#status').combobox('setValue', '1');
        },
        edit: function (data) {
            $('#tr-parent-module-name').hide();
            var options = ModuleMVC.Util.getOptions();
            options.iconCls = 'icon-edit1';
            options.data = data;
            options.title = jQuery.i18n.prop('module.change.module',name);
            EasyUIUtils.openEditDlg(options);
        },
        getOptions: function () {
            return {
                dlgId: '#module-dlg',
                formId: '#module-form',
                actId: '#modal-action',
                rowId: '#moduleId',
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
