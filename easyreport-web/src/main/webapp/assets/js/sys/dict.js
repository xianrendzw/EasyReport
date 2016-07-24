var configDictPageUrl = XFrame.getContextPath() + '/sys/dict/';
$(function () {
    // 左边字典树
    $('#west').panel({
        tools: [{
                iconCls: 'icon-search',
                handler: ConfigDict.searchNodeDlg.open
            }, {
                iconCls: 'icon-add',
                handler: ConfigDict.add
            }, {
                iconCls: 'icon-reload',
                handler: function () {
                    $('#dict-tree').tree('reload');
                    EasyUIUtils.loadToDatagrid('#dict-datagrid', configDictPageUrl + 'list?id=0');
                }
            }]
    });

    $('#dict-tree').tree({
        checkbox: false,
        method: 'get',
        url: configDictPageUrl + 'listchildren',
        onClick: function (node) {
            $('#dict-tree').tree('expand', node.target);
            $('#dict-tree').tree('options').url = configDictPageUrl + 'listchildren';
            EasyUIUtils.loadToDatagrid('#dict-datagrid', configDictPageUrl + 'list?id=' + node.id);
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
            return $.messager.alert('失败', src.msg, 'error');
        }
    });

    // 配置字典grid
    $('#dict-datagrid').datagrid({
        url: configDictPageUrl + 'list',
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
                handler: ConfigDict.add
            }, '-', {
                text: '修改',
                iconCls: 'icon-edit1',
                handler: ConfigDict.edit
            }, '-', {
                text: '复制',
                iconCls: 'icon-copy',
                handler: ConfigDict.copy
            }, '-', {
                text: '删除',
                iconCls: 'icon-remove',
                handler: ConfigDict.remove
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
                    width: 100
                }, {
                    field: 'k',
                    title: '键',
                    width: 100
                }, {
                    field: 'v',
                    title: '值',
                    width: 100
                }, {
                    field: 'sequence',
                    title: '顺序',
                    width: 50
                }, {
                    field: 'gmtCreated',
                    title: '创建时间',
                    width: 100
                }, {
                    field: 'gmtModified',
                    title: '更新时间',
                    width: 100
                }]],
        onDblClickRow: function (index, row) {
            ConfigDict.edit();
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
                    title: '标识',
                    width: 50
                }, {
                    field: 'pid',
                    title: '父标识',
                    hidden: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 150
                }, {
                    field: 'k',
                    title: '对应键',
                    width: 100
                }, {
                    field: 'v',
                    title: '对应值',
                    width: 100
                }]],
        onDblClickRow: function (index, row) {
        }
    });

    $('#dict-dlg').dialog({
        closed: true,
        modal: true,
        width: 500,
        height: 350,
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
                handler: ConfigDict.save
            }]
    });

    $('#search-node-dlg').dialog({
        closed: true,
        modal: true,
        width: window.screen.width - 550,
        height: window.screen.height - 350,
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

    $('#btn-search').bind('click', ConfigDict.searchNodeDlg.find);
});

var ConfigDict = {
    treeContextMenu: function (item) {
        if (item.name == "add") {
            return ConfigDict.add();
        }
        if (item.name == "edit") {
            return ConfigDict.editNode();
        }
        if (item.name == "remove") {
            return ConfigDict.remove();
        }
        if (item.name == "find") {
            return ConfigDict.searchNodeDlg.open();
        }
        return;
    },
    add: function () {
        var name = "根节点";
        var id = "0";
        var node = $('#dict-tree').tree('getSelected');

        if (node) {
            var configNode = node.attributes;
            name = configNode.name;
            id = configNode.id;
        }
        EasyUIUtils.add('#dict-dlg', '#dict-form', '#configDictAction', '#configDictId', '新增[' + name + ']配置字典的子项');
        $("#configDictPid").val(id);
        $("#configDictPNameDiv").show();
        $("#configDictPName").html(name);
        $("#sequence").textbox('setValue', 10);
    },
    editNode: function () {
        $("#configDictPNameDiv").hide();
        var node = $('#dict-tree').tree('getSelected');
        if (node) {
            var row = node.attributes;
            EasyUIUtils.editWithData('#dict-dlg', '#dict-form', '#configDictAction', '#configDictId', '修改[' + row.name
                    + ']配置字典项', row);
        }
    },
    edit: function () {
        $("#configDictPNameDiv").hide();
        var row = $('#dict-datagrid').datagrid('getSelected');
        if (row) {
            EasyUIUtils.editWithData('#dict-dlg', '#dict-form', '#configDictAction', '#configDictId', '修改[' + row.name
                    + ']配置字典项', row);
        }
    },
    copy: function () {
        $("#configDictPNameDiv").hide();
        var row = $('#dict-datagrid').datagrid('getSelected');
        if (row) {
            EasyUIUtils.editWithData('#dict-dlg', '#dict-form', '#configDictAction', '#configDictId', '复制[' + row.name
                    + ']配置字典项', row);
            $('#configDictAction').val("copy");
        }
    },
    remove: function () {
        var row = $('#dict-datagrid').datagrid('getSelected');
        var node = $('#dict-tree').tree('getSelected');
        node = node ? node.attributes : null;
        row = row || node;

        EasyUIUtils.removeWithCallback(row, configDictPageUrl + 'remove', {
            id: row ? row.id : 0
        }, function (data) {
            ConfigDict.refreshNode(row.pid);
            EasyUIUtils.loadToDatagrid('#dict-datagrid', configDictPageUrl + 'list?id=' + row.pid);
        });
    },
    batchRemove: function () {
        var rows = $('#dict-datagrid').datagrid('getChecked');
        var ids = $.map(rows, function (row) {
            return row.id;
        }).join();
        EasyUIUtils.removeWithCallback(rows, configDictPageUrl + 'batchRemove', {
            id: ids
        }, function (data) {
            EasyUIUtils.loadToDatagrid('#dict-datagrid', configDictPageUrl + 'list?id=' + data[0].pid);
        });
    },
    save: function () {
        var pid = $("#configDictPid").val();
        var url = configDictPageUrl + 'list?id=' + pid;
        var actUrl = configDictPageUrl + $('#configDictAction').val();
        EasyUIUtils.saveWithCallback('#dict-dlg', '#dict-form', actUrl, function () {
            ConfigDict.refreshNode(pid);
            EasyUIUtils.loadToDatagrid('#dict-datagrid', url);
        });
    },
    refreshNode: function (pid) {
        if (pid == "0") {
            $('#dict-tree').tree('reload');
        } else {
            var node = $('#dict-tree').tree('find', pid);
            $('#dict-tree').tree('select', node.target);
            $('#dict-tree').tree('reload', node.target);
        }
    },
    searchNodeDlg: {
        open: function () {
            $('#search-node-dlg').dialog('open').dialog('center');
            EasyUIUtils.clearDatagrid('#search-node-result');
        },
        find: function () {
            var fieldName = $('#field-name').combobox('getValue');
            var keyword = $('#keyword').val();
            var url = configDictPageUrl + 'find?fieldName=' + fieldName + '&keyword=' + keyword;
            return EasyUIUtils.loadToDatagrid('#search-node-result', url);
        },
    }
};