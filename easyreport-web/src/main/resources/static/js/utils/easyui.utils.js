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
    /**
     *
     * @param options
     *  options = {
     *      gridId:'#gridId',
     *      gridUrl:'gridUrl',
     *      rows: [row,...],
     *      url: ModuleMVC.URLs.remove.url,
     *      data: data,
     *      callback: function (row) {}
     *  }
     * @returns {*}
     */
    remove: function (options) {
        if (!options || !options.rows || options.rows.length == 0) {
            return $.messager.alert('警告', '请选中至少一条记录!', 'info');
        }

        if (options.gridId && options.gridUrl) {
            options.callback = function (rows) {
                EasyUIUtils.loadToDatagrid(options.gridId, options.gridUrl);
            };
        }

        return $.messager.confirm('删除', '您确定要删除记录吗?', function (r) {
            if (r) {
                $.post(options.url, options.data, function (result) {
                    if (result.code) {
                        $.messager.show({
                            title: '错误',
                            msg: result.msg
                        });
                    } else {
                        EasyUIUtils.showMsg(result.msg || "操作成功");
                        options.callback(options.rows);

                    }
                }, 'json');
            }
        });
    },
    batchRemove: function (options) {
        var rows = $(options.gridId).datagrid('getChecked');
        var ids = $.map(rows, function (row) {
            return row.id;
        }).join();

        options.rows = rows;
        options.data = {
            id: ids
        };
        EasyUIUtils.remove(options);
    },
    /**
     *
     * @param options
     * var options = {
     *        dlgId: '#dlgId',
     *        formId: '#formId',
     *        url: 'add',
     *        gridId: '#gridId',
     *        gridUrl: 'list?id=',
     *        callback:function(){}
     *  };
     */
    save: function (options) {
        if (!options) {
            return $.messager.alert('警告', '参数不正确!', 'info');
        }

        if (options.gridId && options.gridUrl) {
            options.callback = function (data) {
                EasyUIUtils.loadToDatagrid(options.gridId, options.gridUrl);
            };
        }
        $(options.formId).form('submit', {
            url: options.url,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (data) {
                var result = $.toJSON(data);
                if (result.code) {
                    $.messager.show({
                        title: '错误',
                        msg: result.msg
                    });
                } else {
                    EasyUIUtils.showMsg(result.msg || "操作成功");
                    options.callback(result.data);
                    $(options.dlgId).dialog('close');
                }
            }
        });
    },
    loadDataWithUrl: function (gridId, href) {
        $(gridId).datagrid({url: href});
    },
    loadDataWithCallback: function (id, href, fn) {
        var grid = $(id);
        $.getJSON(href, function (data) {
            grid.datagrid('loadData', data);
            if (fn instanceof Function) {
                fn();
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
    /**
     * 移动datagrid行
     * @param gridId grid元素id
     * @param type 取值只能为:'up'(上移) or 'down'(下移)
     */
    move: function (gridId, type) {
        var $grid = $(gridId);
        var row = $grid.datagrid('getSelected');
        var index = $grid.datagrid('getRowIndex', row);
        EasyUIUtils.resort(index, type, $grid);
    },
    /**
     * 选中datagrid的行
     * @param gridId grid元素id
     * @param indexId 当前grid选中的行号所在的元素id
     * @param type 取值只能为:'prev'(上一条) or 'next'(下一条)
     * @param fn 回调函数
     */
    cursor: function (gridId, indexId, type, fn) {
        var index = parseInt($(indexId).val()) - 1;
        if (type === 'next') {
            index = parseInt($(indexId).val()) + 1;
        }

        $(gridId).datagrid('selectRow', index);
        var row = $(gridId).datagrid('getSelected');
        if (row) {
            $(indexId).val(index);
            if (fn instanceof Function) {
                fn(row);
            }
            return;
        }

        if (type === 'next') {
            $(gridId).datagrid('selectRow', index - 1);
            return $.messager.alert('失败', '当前已到最后一条记录', 'error');
        }
        $(gridId).datagrid('selectRow', index + 1);
        return $.messager.alert('失败', '当前已到第一条记录!', 'error');
    },
    //
    //datagrid行重排序
    resort: function (index, type, $grid) {
        var maxIndex = $grid.datagrid('getRows').length - 1;
        var moveIndex = ("up" == type) ? index - 1 : index + 1;
        if (moveIndex >= 0 && moveIndex <= maxIndex) {
            var currRow = $grid.datagrid('getData').rows[index];
            var moveRow = $grid.datagrid('getData').rows[moveIndex];
            $grid.datagrid('getData').rows[index] = moveRow;
            $grid.datagrid('getData').rows[moveIndex] = currRow;
            $grid.datagrid('refreshRow', index);
            $grid.datagrid('refreshRow', moveIndex);
            $grid.datagrid('selectRow', moveIndex);
        }
    },
    fillCombox: function (id, act, list, defaultValue) {
        $(id).combobox('clear');
        for (var i = 0; i < list.length; i++) {
            var item = list[i];
            item["selected"] = (i == 0);
        }
        $(id).combobox('loadData', list);
        if (act == "edit") {
            $(id).combobox('setValue', defaultValue);
        }
    },
    toPropertygridRows: function (options) {
        var rows = [];
        for (var key in options) {
            rows.push({
                "name": key,
                "value": options[key],
                "editor": "text"
            });
        }

        return {
            "total": rows.length,
            "rows": rows
        };
    },
    toPropertygridMap: function (rows) {
        var options = {};
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            options[row.name] = row.value;
        }
        return options;
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