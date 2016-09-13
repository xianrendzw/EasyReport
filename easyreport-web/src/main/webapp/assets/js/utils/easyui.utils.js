var EasyUIUtils = {
    /**
     *
     * @param options
     * var options = {
     *        dlgId: '#module-dlg',
     *        formId: '#module-form',
     *        actId: '#modal-action',
     *        rowId: '#moduleId',
     *        title: '新增子模块',
     *        iconCls: 'icon-add',
     *        data: {},
     *        callback: function (arg) {},
     *        gridId:'#gridId'
     *  }
     */
    openAddDlg: function (options) {
        $(options.formId).form('clear');
        $(options.actId).val("add");
        $(options.rowId).val(0);
        $(options.dlgId).dialog({iconCls: options.iconCls})
            .dialog('open')
            .dialog('center')
            .dialog('setTitle', options.title);
    },
    openEditDlg: function (options) {
        if (options.gridId) {
            options.data = $(options.gridId).datagrid('getSelected');
        }
        if (options.data) {
            $(options.formId).form('clear');
            $(options.actId).val("edit");
            $(options.rowId).val(options.data.id);
            $(options.dlgId).dialog({iconCls: options.iconCls})
                .dialog('open')
                .dialog('center')
                .dialog('setTitle', options.title);
            $(options.formId).form('load', options.data);
            options.callback(options.data);
        } else {
            EasyUIUtils.showMsg("请您先选择一个选项!");
        }
    },
    remove: function (gridId, gridUrl) {
        var row = $(gridId).datagrid('getSelected');
        if (!row) {
            return $.messager.alert('警告', '请选中一条记录!', 'info');
        }
        EasyUIUtils.removeWithCallback(row, 'remove', {
            id: row.id
        }, function () {
            EasyUIUtils.loadToDatagrid(gridId, gridUrl);
        });
    },
    removeWithActUrl: function (gridId, gridUrl, actUrl) {
        var row = $(gridId).datagrid('getSelected');
        if (!row) {
            return $.messager.alert('警告', '请选中一条记录!', 'info');
        }
        EasyUIUtils.removeWithCallback(row, actUrl, {
            id: row.id
        }, function () {
            EasyUIUtils.loadToDatagrid(gridId, gridUrl);
        });
    },
    removeWithIdFieldName: function (gridId, gridUrl, actUrl, idFieldName) {
        var row = $(gridId).datagrid('getSelected');
        if (!row) {
            return $.messager.alert('警告', '请选中一条记录!', 'info');
        }
        EasyUIUtils.removeWithCallback(row, actUrl, {
            id: row[idFieldName]
        }, function () {
            EasyUIUtils.loadToDatagrid(gridId, gridUrl);
        });
    },
    removeWithCallback: function (row, postUrl, postData, callback) {
        if (!row) {
            return $.messager.alert('警告', '请选中一条记录!', 'info');
        }
        return $.messager.confirm('删除', '您确定要删除记录吗?', function (r) {
            if (r) {
                $.post(postUrl, postData, function (result) {
                    if (result.success) {
                        EasyUIUtils.showMsg(result.msg);
                        callback(row);
                    } else {
                        $.messager.show({
                            title: '错误',
                            msg: result.msg
                        });
                    }
                }, 'json');
            }
        });
    },
    batchRemove: function (gridId, gridUrl) {
        var rows = $(gridId).datagrid('getChecked');
        var ids = $.map(rows, function (row) {
            return row.id;
        }).join();
        EasyUIUtils.removeWithCallback(rows, 'batchRemove', {
            id: ids
        }, function () {
            EasyUIUtils.loadToDatagrid(gridId, gridUrl);
        });
    },
    batchRemoveWithActUrl: function (gridId, gridUrl, actUrl) {
        var rows = $(gridId).datagrid('getChecked');
        var ids = $.map(rows, function (row) {
            return row.id;
        }).join();
        EasyUIUtils.removeWithCallback(rows, actUrl, {
            id: ids
        }, function () {
            EasyUIUtils.loadToDatagrid(gridId, gridUrl);
        });
    },
    save: function (dlgId, formId, actId, gridId, gridUrl) {
        var action = $(actId).val();
        EasyUIUtils.saveWithCallback(dlgId, formId, action, function () {
            EasyUIUtils.loadToDatagrid(gridId, gridUrl);
        });
    },
    saveWithActUrl: function (dlgId, formId, actId, gridId, gridUrl, actRootUrl) {
        var action = actRootUrl + $(actId).val();
        EasyUIUtils.saveWithCallback(dlgId, formId, action, function () {
            EasyUIUtils.loadToDatagrid(gridId, gridUrl);
        });
    },
    saveWithCallback: function (dlgId, formId, postUrl, callback) {
        $(formId).form('submit', {
            url: postUrl,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var result = $.parseJSON(data)
                if (result.success) {
                    EasyUIUtils.showMsg(result.msg || "操作成功");
                    callback();
                    $(dlgId).dialog('close');
                } else {
                    $.messager.show({
                        title: '错误',
                        msg: result.msg
                    });
                }
            }
        });
    },
    loadToDatagrid: function (id, href) {
        var grid = $(id);
        $.getJSON(href, function (data) {
            grid.datagrid('loadData', data);
        });
    },
    reloadDatagrid: function (id) {
        $(id).datagrid('reload');
    },
    clearDatagrid: function (id) {
        $(id).datagrid('loadData', {
            total: 0,
            rows: []
        });
    },
    clearDatagridWithFilter: function (id) {
        var data = {
            success: true,
            data: {
                total: 0,
                rows: []
            }
        };
        $(id).datagrid('loadData', data);
    },
    getEmptyDatagridRows: function () {
        return {
            total: 0,
            rows: []
        };
    },
    parseJSON: function (json) {
        if (json == null || json == "") {
            return {};
        }
        return $.parseJSON(json);
    },
    showMsg: function (msg) {
        $.messager.show({
            title: '提示',
            msg: msg,
            timeout: 3000,
            showType: 'slide'
        });
    },
    showMsgByTime: function (msg, time) {
        $.messager.show({
            title: '提示',
            msg: msg,
            timeout: time,
            showType: 'slide'
        });
    },
    resortDatagrid: function (index, type, grid) {
        var maxIndex = grid.datagrid('getRows').length - 1;
        var moveIndex = ("up" == type) ? index - 1 : index + 1;
        if (moveIndex >= 0 && moveIndex <= maxIndex) {
            var currRow = grid.datagrid('getData').rows[index];
            var moveRow = grid.datagrid('getData').rows[moveIndex];
            grid.datagrid('getData').rows[index] = moveRow;
            grid.datagrid('getData').rows[moveIndex] = currRow;
            grid.datagrid('refreshRow', index);
            grid.datagrid('refreshRow', moveIndex);
            grid.datagrid('selectRow', moveIndex);
        }
    },
    closeCurrentTab: function (id) {
        var tab = $(id).tabs('getSelected');
        var options = tab.panel('options');
        if (options.closable) {
            $(id).tabs('close', tab.panel('options').title);
        }
    },
    closeOthersTab: function (id) {
        var currentTab = $(id).tabs('getSelected');
        var currTitle = currentTab.panel('options').title;
        $('.tabs-inner span').each(function (i, n) {
            var title = $(n).text();
            var tab = $(id).tabs('getTab', title);
            if (tab) {
                var options = tab.panel('options');
                if (title != currTitle && options.closable) {
                    $(id).tabs('close', title);
                }
            }
        });
    },
    closeAllTab: function (id) {
        $('.tabs-inner span').each(function (i, n) {
            var title = $(n).text();
            var tab = $(id).tabs('getTab', title);
            if (tab) {
                var options = tab.panel('options');
                if (options.closable) {
                    $(id).tabs('close', title);
                }
            }
        });
    },
    loading: function () {
        $.messager.progress({
            title: '请稍后...',
            text: '数据正在加载中...',
            closable: true
        });
    },
    closeProgress: function () {
        $.messager.progress("close");
    }
};