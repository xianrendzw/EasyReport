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
    baseUrl: EasyReport.ctxPath + '/rest/report/conf/',
    baseIconUrl: EasyReport.ctxPath + '/custom/easyui/themes/icons/'
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
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
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
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: ConfMVC.Controller.add
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: ConfMVC.Controller.edit
                }, '-', {
                    text: '复制',
                    iconCls: 'icon-copy',
                    handler: ConfMVC.Controller.copy
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: ConfMVC.Controller.remove
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
                    width: 100,
                    sortable: true
                }, {
                    field: 'key',
                    title: '键',
                    width: 100,
                    sortable: true
                }, {
                    field: 'value',
                    title: '值',
                    width: 100
                }, {
                    field: 'sequence',
                    title: '顺序',
                    width: 50,
                    sortable: true
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
                    if (!src.code) {
                        return src.data;
                    }
                    return {
                        total: 0,
                        rows: []
                    };
                },
                columns: [[{
                    field: 'id',
                    title: '标识',
                    width: 50
                }, {
                    field: 'parentId',
                    title: '父标识',
                    hidden: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 150
                }, {
                    field: 'key',
                    title: '对应键',
                    width: 100
                }, {
                    field: 'value',
                    title: '对应值',
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
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#dict-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
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
                    text: '关闭',
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
            var name = "根配置项";
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
                $.messager.alert('警告', '请选中一个父配置项!', 'info');
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
                options.title = '复制[' + options.data.name + ']配置项';
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
            options.title = '新增[' + name + ']的子配置项';
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
            options.title = '修改[' + options.data.name + ']配置项';
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