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
    baseUrl: EasyReport.ctxPath + '/rest/sys/event/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/'
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
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('event.failed'), src.msg, 'error');
                },
                columns: [
                    [{
                        field: 'id',
                        title: jQuery.i18n.prop('event.id'),
                        width: 50,
                        sortable: true
                    }, {
                        field: 'source',
                        title: jQuery.i18n.prop('event.source'),
                        width: 200,
                        sortable: true
                    }, {
                        field: 'account',
                        title: jQuery.i18n.prop('event.account'),
                        width: 50,
                        sortable: true,
                    }, {
                        field: 'level',
                        title: jQuery.i18n.prop('event.level'),
                        width: 50,
                        sortable: true
                    }, {
                        field: 'gmtCreated',
                        title: jQuery.i18n.prop('event.gmtcreated'),
                        width: 50,
                        sortable: true
                    }, {
                        field: 'options',
                        title: jQuery.i18n.prop('event.operation'),
                        width: 100,
                        formatter: function (value, row, index) {
                            var tmpl = '<a href="#" title ="${title}" ' +
                                'onclick="EventMVC.Controller.doOption(\'${index}\',\'${name}\')">' +
                                '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var icons = [{
                                "name": "info",
                                "title": jQuery.i18n.prop('event.detail')
                            }, {
                                "name": "remove",
                                "title": jQuery.i18n.prop('event.remove')
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
                    text: jQuery.i18n.prop('event.prev.page'),
                    iconCls: 'icon-prev',
                    handler: function () {
                        EventMVC.Controller.prev();
                    }
                }, {
                    text: jQuery.i18n.prop('event.next.page'),
                    iconCls: 'icon-next',
                    handler: function () {
                        EventMVC.Controller.next();
                    }
                }, {
                    text: jQuery.i18n.prop('event.close'),
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
            return $.messager.confirm(jQuery.i18n.prop('event.remove'), jQuery.i18n.prop('event.confirm.clear'), function (r) {
                if (r) {
                    $.getJSON(EventMVC.URLs.clear.url, function (result) {
                        if (result.success) {
                            EasyUIUtils.reloadDatagrid('#event-datagrid');
                            EasyUIUtils.showMsg(result.msg || jQuery.i18n.prop('event.operate.success'));
                        } else {
                            $.messager.show({
                                title: jQuery.i18n.prop('event.error'),
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
            return $.messager.alert(jQuery.i18n.prop('event.warn'), jQuery.i18n.prop('event.please.select.record'), 'info');
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
