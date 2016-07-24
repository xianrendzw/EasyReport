var sysEventRootPageUrl = XFrame.getContextPath() + '/sys/event/';
$(function() {
    $('#event-datagrid').datagrid({
        method: 'get',
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        url: sysEventRootPageUrl + 'list',
        toolbar: [{
            iconCls: 'icon-info',
            handler: function() {
                SysEvent.showDetail();
            }
        }, '-', {
            iconCls: 'icon-remove1',
            handler: function() {
                SysEvent.remove();
            }
        }, '-', {
            iconCls: 'icon-reload',
            handler: function() {
                EasyUIUtils.reloadDatagrid('#event-datagrid');
            }
        }, '-', {
            iconCls: 'icon-clear1',
            handler: function() {
                SysEvent.clear();
            }
        }],
        loadFilter: function(src) {
            if (src.success) {
                return src.data;
            }
            return $.messager.alert('失败', src.msg, 'error');
        },
        columns: [
            [{
                field: 'eventId',
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
                field: 'gmtCreate',
                title: '发生时间',
                width: 50,
                sortable: true
            }, {
                field: 'options',
                title: '操作',
                width: 100,
                formatter: function(value, row, index) {
                    var imgPath = XFrame.getContextPath() + '/assets/custom/easyui/themes/icons/';
                    var tmpl = '<a href="#" title ="{{title}}" onclick="SysEvent.doAction(\'{{index}}\',\'{{name}}\')"><img src="{{imgSrc}}" alt="{{title}}"/"></a>';
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
                            imgSrc: imgPath + icons[i].name + ".png"
                        };
                        buttons.push(template.compile(tmpl)(data));
                    }
                    return buttons.join(' ');
                }
            }]
        ],
        onDblClickRow: function(rowIndex, rowData) {
            return SysEvent.detailInfoDlg.open(rowIndex, rowData);
        }
    });

    // dialogs
    // 查询详细信息
    $('#detail-info-dlg').dialog({
        closed: true,
        modal: false,
        maximizable: true,
        width: window.screen.width - 550,
        height: window.screen.height - 350,
        iconCls: 'icon-info',
        buttons: [{
            text: '上一条',
            iconCls: 'icon-prev',
            handler: function() {
                SysEvent.detailInfoDlg.prev();
            }
        }, {
            text: '下一条',
            iconCls: 'icon-next',
            handler: function() {
                SysEvent.detailInfoDlg.next();
            }
        }, {
            text: '关闭',
            iconCls: 'icon-no',
            handler: function() {
                $("#detail-info-dlg").dialog('close');
            }
        }]
    });
    SysEvent.init();
});

var SysEvent = {
    init: function() {
        this.initEvent();
    },
    initEvent: function() {
        $('#btn-search').bind('click', SysEvent.find);
    },
    doAction: function(index, name) {
        $('#event-datagrid').datagrid('selectRow', index);
        if (name == "info") {
            return SysEvent.showDetail();
        }
        if (name == "remove") {
            return SysEvent.remove();
        }
    },
    find: function() {
        //var fieldName = $("#field-name").combobox('getValue');
        var keyword = $("#keyword").val();
        var url = sysEventRootPageUrl + 'find?keyword=' + keyword;
        EasyUIUtils.loadToDatagrid('#event-datagrid', url)
    },
    remove: function() {
        var gridUrl = sysEventRootPageUrl + 'list';
        var actUrl = sysEventRootPageUrl + 'remove';
        return EasyUIUtils.removeWithIdFieldName('#event-datagrid', gridUrl, actUrl, "eventId");
    },
    clear: function() {
        var url = sysEventRootPageUrl + 'clear';
        $.post(url, function(result) {
            if (result.success) {
                EasyUIUtils.reloadDatagrid('#event-datagrid');
            } else {
                EasyUIUtils.showMsg(result.msg);
            }
        }, 'json');
    },
    showDetail: function() {
        var row = $('#event-datagrid').datagrid('getSelected');
        if (row) {
            var index = $('#event-datagrid').datagrid('getRowIndex', row);
            return SysEvent.detailInfoDlg.open(index, row);
        }
        return $.messager.alert('警告', '请选中一条记录!', 'info');
    },
    detailInfoDlg: {
        open: function(index, row) {
            $('#detail-info-dlg').dialog('open').dialog('center');
            $('#detail-info').text(row.message);
        },
        prev: function() {
            var index = parseInt($('#current-row-index').val()) - 1;
            $('#event-datagrid').datagrid('selectRow', index);
            var row = $('#event-datagrid').datagrid('getSelected');
            if (row) {
                $('#current-row-index').val(index);
                $('#detail-info').text(row.message);
            } else {
                $('#event-datagrid').datagrid('selectRow', index + 1);
                $.messager.alert('失败', '当前已到第一条记录!', 'error');
            }
        },
        next: function() {
            var index = parseInt($('#current-row-index').val()) + 1;
            $('#event-datagrid').datagrid('selectRow', index);
            var row = $('#event-datagrid').datagrid('getSelected');
            if (row) {
                $('#current-row-index').val(index);
                $('#detail-info').text(row.message);
            } else {
                $('#event-datagrid').datagrid('selectRow', index - 1);
                $.messager.alert('失败', '当前已到最后一条记录', 'error');
            }
        }
    }
};
