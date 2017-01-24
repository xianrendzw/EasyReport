$(function () {
    MetaDataConf.init();
});

var MetaDataConf = {
    init: function () {
        ConfMVC.View.initControl();
        ConfMVC.View.bindEvent();
        ConfMVC.View.bindValidate();
    }
};

var ConfCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/metadata/conf/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/'
};

var ConfMVC = {
    URLs: {
        add: {
            url: ConfCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: ConfCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: ConfCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: ConfCommon.baseUrl + 'remove',
            method: 'POST'
        },
        listChildren: {
            url: ConfCommon.baseUrl + 'listChildren',
            method: 'GET'
        }
    },
    View: {
        initControl: function () {
            // 左边字典树
            $('#west').panel({
                tools: [{
                    iconCls: 'icon-search',
                    handler: ConfMVC.Controller.openSearchDlg
                }, {
                    iconCls: 'icon-add',
                    handler: ConfMVC.Controller.addRoot
                }, {
                    iconCls: 'icon-reload',
                    handler: function () {
                        ConfMVC.Controller.reloadTree();
                        EasyUIUtils.loadDataWithUrl('#dict-datagrid', ConfMVC.URLs.list.url + '?id=0');
                    }
                }]
            });

            $('#dict-tree').tree({
                checkbox: false,
                method: 'get',
                animate: true,
                dnd: true,
                url: ConfMVC.URLs.listChildren.url,
                onClick: function (node) {
                    $('#dict-tree').tree('expand', node.target);
                    $('#dict-tree').tree('options').url = ConfMVC.URLs.listChildren.url;
                    EasyUIUtils.loadDataWithUrl('#dict-datagrid', ConfMVC.URLs.list.url + '?id=' + node.id);
                },
                onContextMenu: function (e, node) {
                    e.preventDefault();
                    $('#dict-tree').tree('select', node.target);
                    $('#tree_ctx_menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                },
                loadFilter: function (src, parent) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('conf.failed'), src.msg, 'error');
                }
            });

            $('#tree_ctx_menu').menu({
                onClick: function (item) {
                    if (item.name == "add") {
                        return ConfMVC.Controller.add();
                    }
                    if (item.name == "edit") {
                        return ConfMVC.Controller.editNode();
                    }
                    if (item.name == "remove") {
                        return ConfMVC.Controller.remove();
                    }
                    if (item.name == "find") {
                        return ConfMVC.Controller.openSearchDlg();
                    }
                }
            });

            $('#dict-datagrid').datagrid({
                url: ConfMVC.URLs.list.url,
                method: 'get',
                idField: 'id',
                pageSize: 50,
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                toolbar: [{
                    text: jQuery.i18n.prop('conf.add'),
                    iconCls: 'icon-add',
                    handler: ConfMVC.Controller.add
                }, '-', {
                    text: jQuery.i18n.prop('conf.edit'),
                    iconCls: 'icon-edit1',
                    handler: ConfMVC.Controller.edit
                }, '-', {
                    text: jQuery.i18n.prop('conf.copy'),
                    iconCls: 'icon-copy',
                    handler: ConfMVC.Controller.copy
                }, '-', {
                    text: jQuery.i18n.prop('conf.remove'),
                    iconCls: 'icon-remove',
                    handler: ConfMVC.Controller.remove
                }],
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('conf.failed'), src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('conf.id'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: jQuery.i18n.prop('conf.name'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'key',
                    title: jQuery.i18n.prop('conf.key'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'value',
                    title: jQuery.i18n.prop('conf.value'),
                    width: 100
                }, {
                    field: 'sequence',
                    title: jQuery.i18n.prop('conf.sequence'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: jQuery.i18n.prop('conf.gmtcreate'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtModified',
                    title: jQuery.i18n.prop('conf.gmtmodified'),
                    width: 100,
                    sortable: true
                }]],
                onDblClickRow: function (index, row) {
                    ConfMVC.Controller.edit();
                }
            });

            $('#search-node-result').datagrid({
                method: 'get',
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                pageSize: 10,
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    return {
                        total: 0,
                        rows: []
                    };
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('conf.id'),
                    width: 50
                }, {
                    field: 'parentId',
                    title: jQuery.i18n.prop('conf.parentid'),
                    hidden: true
                }, {
                    field: 'name',
                    title: jQuery.i18n.prop('conf.name'),
                    width: 150
                }, {
                    field: 'key',
                    title: jQuery.i18n.prop('conf.key'),
                    width: 100
                }, {
                    field: 'value',
                    title: jQuery.i18n.prop('conf.value'),
                    width: 100
                }]],
                onDblClickRow: function (index, row) {
                }
            });

            $('#dict-dlg').dialog({
                closed: true,
                modal: true,
                width: 600,
                height: 450,
                iconCls: 'icon-save',
                buttons: [{
                    text: jQuery.i18n.prop('conf.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#dict-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('conf.save'),
                    iconCls: 'icon-save',
                    handler: ConfMVC.Controller.save
                }]
            });

            $('#search-node-dlg').dialog({
                closed: true,
                modal: true,
                width: 750,
                height: 550,
                maximizable: true,
                iconCls: 'icon-search',
                buttons: [{
                    text: jQuery.i18n.prop('conf.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#search-node-dlg").dialog('close');
                    }
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', ConfMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        addRoot: function () {
            var name = jQuery.i18n.prop('conf.root.conf');
            var id = "0";
            ConfMVC.Util.initAdd(id, name);
        },
        add: function () {
            var node = $('#dict-tree').tree('getSelected');
            if (node) {
                var name = node.attributes.name;
                var id = node.id;
                ConfMVC.Util.initAdd(id, name);
            } else {
                $.messager.alert(jQuery.i18n.prop('conf.warn'), jQuery.i18n.prop('conf.please.select.parent.conf'), 'info');
            }
        },
        editNode: function () {
            var node = $('#dict-tree').tree('getSelected');
            if (node) {
                ConfMVC.Util.edit(node.attributes);
            }
        },
        edit: function () {
            var row = $('#dict-datagrid').datagrid('getSelected');
            ConfMVC.Util.edit(row);
        },
        copy: function () {
            $("#confParentNameDiv").hide();
            var row = $('#dict-datagrid').datagrid('getSelected');
            if (row) {
                var options = ConfMVC.Util.getOptions();
                options.iconCls = 'icon-copy';
                options.data = row;
                options.title = jQuery.i18n.prop('conf.copy.conf',options.data.name);
                EasyUIUtils.openEditDlg(options);
                $('#modal-action').val("add");
            }
        },
        remove: function () {
            var row = $('#dict-datagrid').datagrid('getSelected');
            var node = $('#dict-tree').tree('getSelected');
            node = node ? node.attributes : null;
            row = row || node;

            var options = {
                rows: [row],
                url: ConfMVC.URLs.remove.url,
                data: {
                    id: row.id
                },
                callback: function (rows) {
                    var row = rows[0];
                    ConfMVC.Controller.reloadTree();
                    EasyUIUtils.loadDataWithUrl('#dict-datagrid', ConfMVC.URLs.list.url + '?id=' + row.parentId);
                }
            };
            EasyUIUtils.remove(options);
        },
        save: function () {
            var action = $('#modal-action').val();
            var gridUrl = ConfMVC.URLs.list.url + '?id=' + $("#confPid").val();
            var actUrl = action === "edit" ? ConfMVC.URLs.edit.url : ConfMVC.URLs.add.url;

            var options = {
                dlgId: "#dict-dlg",
                formId: "#dict-form",
                url: actUrl,
                callback: function () {
                    ConfMVC.Controller.reloadTree();
                    EasyUIUtils.loadDataWithUrl('#dict-datagrid', gridUrl);
                }
            };
            EasyUIUtils.save(options);
        },
        refreshNode: function (pid) {
            if (pid == "0") {
                this.reloadTree();
            } else {
                var node = $('#dict-tree').tree('find', pid);
                if (node) {
                    $('#dict-tree').tree('select', node.target);
                    $('#dict-tree').tree('reload', node.target);
                }
            }
        },
        reloadTree: function () {
            $('#dict-tree').tree('reload');
        },
        openSearchDlg: function () {
            $('#search-node-dlg').dialog('open').dialog('center');
            EasyUIUtils.clearDatagrid('#search-node-result');
        },
        find: function () {
            var fieldName = $('#field-name').combobox('getValue');
            var keyword = $('#keyword').val();
            var url = ConfMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            return EasyUIUtils.loadDataWithUrl('#search-node-result', url);
        }
    },
    Util: {
        initAdd: function (id, name) {
            var options = ConfMVC.Util.getOptions();
            options.title = jQuery.i18n.prop('conf.add.conf',name);
            EasyUIUtils.openAddDlg(options);

            $("#confPid").val(id);
            $("#confParentNameDiv").show();
            $("#confParentName").html(name);
            $("#sequence").textbox('setValue', 10);
        },
        edit: function (data) {
            $("#confParentNameDiv").hide();
            var options = ConfMVC.Util.getOptions();
            options.iconCls = 'icon-edit1';
            options.data = data;
            options.title = jQuery.i18n.prop('conf.edit.conf',options.data.name);
            EasyUIUtils.openEditDlg(options);
        },
        getOptions: function () {
            return {
                dlgId: '#dict-dlg',
                formId: '#dict-form',
                actId: '#modal-action',
                rowId: '#confId',
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