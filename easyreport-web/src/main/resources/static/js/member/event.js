$(function () {
    SysEvent.init();
});

var SysEvent = {
    init: function () {
        EventMVC.View.initControl();
        EventMVC.View.bindEvent();
        EventMVC.View.bindValidate();
        EventMVC.View.initData();
    }
};

var EventCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/member/event/',
    baseIconUrl: EasyReport.ctxPath + '/custom/easyui/themes/icons/'
};

var EventMVC = {
    URLs: {
        list: {
            url: EventCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: EventCommon.baseUrl + 'remove',
            method: 'POST'
        },
        clear: {
            url: EventCommon.baseUrl + 'clear',
            method: 'POST'
        },
    },
    View: {
        initControl: function () {
            $('#event-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: EventMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-info',
                    handler: function () {
                        EventMVC.Controller.showDetail();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        EventMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#event-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-clear1',
                    handler: function () {
                        EventMVC.Controller.clear();
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [
                    [{
                        field: 'id',
                        title: '记录ID',
                        width: 50,
                        sortable: true
                    }, {
                        field: 'source',
                        title: '来源',
                        width: 200,
                        sortable: true
                    }, {
                        field: 'account',
                        title: '操作用户',
                        width: 50,
                        sortable: true,
                    }, {
                        field: 'level',
                        title: '级别',
                        width: 50,
                        sortable: true
                    }, {
                        field: 'gmtCreated',
                        title: '发生时间',
                        width: 50,
                        sortable: true
                    }, {
                        field: 'options',
                        title: '操作',
                        width: 100,
                        formatter: function (value, row, index) {
                            var tmpl = '<a href="#" title ="${title}" ' +
                                'onclick="EventMVC.Controller.doOption(\'${index}\',\'${name}\')">' +
                                '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var icons = [{
                                "name": "info",
                                "title": "详细"
                            }, {
                                "name": "remove",
                                "title": "删除"
                            }];
                            var buttons = [];
                            for (var i = 0; i < icons.length; i++) {
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index,
                                    imgSrc: EventCommon.baseIconUrl + icons[i].name + ".png"
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join(' ');
                        }
                    }]
                ],
                onDblClickRow: function (rowIndex, rowData) {
                    return EventMVC.Controller.open(rowIndex, rowData);
                }
            });

            // dialogs
            $('#detail-info-dlg').dialog({
                closed: true,
                modal: false,
                maximizable: true,
                width: 650,
                height: 450,
                iconCls: 'icon-info',
                buttons: [{
                    text: '上一条',
                    iconCls: 'icon-prev',
                    handler: function () {
                        EventMVC.Controller.prev();
                    }
                }, {
                    text: '下一条',
                    iconCls: 'icon-next',
                    handler: function () {
                        EventMVC.Controller.next();
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#detail-info-dlg").dialog('close');
                    }
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', EventMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#event-datagrid').datagrid('selectRow', index);
            if (name == "info") {
                return EventMVC.Controller.showDetail();
            }
            if (name == "remove") {
                return EventMVC.Controller.remove();
            }
        },
        clear: function () {
            return $.messager.confirm('删除', '您确定要清空所有日志吗?', function (r) {
                if (r) {
                    $.getJSON(EventMVC.URLs.clear.url, function (result) {
                        if (!result.code) {
                            EasyUIUtils.reloadDatagrid('#event-datagrid');
                            EasyUIUtils.showMsg(result.msg || "操作成功");
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
        showDetail: function () {
            var row = $('#event-datagrid').datagrid('getSelected');
            if (row) {
                var index = $('#event-datagrid').datagrid('getRowIndex', row);
                return EventMVC.Controller.open(index, row);
            }
            return $.messager.alert('警告', '请选中一条记录!', 'info');
        },
        find: function () {
            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            var url = EventMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#event-datagrid', url)
        },
        remove: function () {
            var row = $('#event-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: EventMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#event-datagrid',
                    gridUrl: EventMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        open: function (index, row) {
            $('#detail-info-dlg').dialog('open').dialog('center');
            $('#detail-info').text(row.message);
        },
        prev: function () {
            EasyUIUtils.cursor('#event-datagrid', '#current-row-index', 'prev', function (row) {
                $('#detail-info').text(row.message);
            });
        },
        next: function () {
            EasyUIUtils.cursor('#event-datagrid', '#current-row-index', 'next', function (row) {
                $('#detail-info').text(row.message);
            });
        }
    }
};
