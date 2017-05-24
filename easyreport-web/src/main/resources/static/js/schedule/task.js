$(function () {
    ScheduleTask.init();
});

var ScheduleTask = {
    init: function () {
        TaskMVC.View.initControl();
        TaskMVC.View.bindEvent();
        TaskMVC.View.bindValidate();
        TaskMVC.View.initData();
    }
};

var TaskCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/schedule/task/',
    baseReportUrl: EasyReport.ctxPath + '/rest/schedule/designer/',
    baseIconUrl: EasyReport.ctxPath + '/custom/easyui/themes/icons/'
};

var TaskMVC = {
    URLs: {
        add: {
            url: TaskCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: TaskCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: TaskCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: TaskCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getAllReports: {
            url: TaskCommon.baseReportUrl + 'getAll',
            method: 'POST'
        },
        getJsonOptions: {
            url: TaskCommon.baseUrl + 'getJsonOptions',
            method: 'GET'
        }
    },
    Model: {
        reports: {}
    },
    View: {
        initControl: function () {
            $('#task-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: TaskMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        TaskMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        TaskMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        TaskMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#task-datagrid');
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: '任务ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'reportIds',
                    title: '报表ids',
                    width: 200,
                    sortable: true
                }, {
                    field: 'cronExpr',
                    title: 'Cron表达式',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'type',
                    title: '类型',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 1) return "电子邮件";
                        if (value === 2) return "手机短信";
                        return "其他";
                    }
                }, {
                    field: 'comment',
                    title: '说明',
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: '创建时间',
                    width: 50,
                    sortable: true
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="TaskMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: TaskCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return TaskMVC.Controller.edit();
                }
            });

            // dialogs
            $('#task-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#task-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: TaskMVC.Controller.save
                }]
            });

            $('#type').combobox({
                onChange: function (newValue, oldValue) {
                    TaskMVC.Util.getJsonOptions(newValue);
                }
            });

            $('#cronExprDiv').cron({
                initial: "42 3 1 * *",
                onChange: function () {
                    $('#cronExpr').textbox('setValue', $(this).cron("value"));
                },
                useGentleSelect: true
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', TaskMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            TaskMVC.Util.loadReportList();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#task-datagrid').datagrid('selectRow', index);
            if (name == "edit") {
                return TaskMVC.Controller.edit();
            }
            if (name == "remove") {
                return TaskMVC.Controller.remove();
            }
        },
        add: function () {
            var options = TaskMVC.Util.getOptions();
            options.title = '新增任务';
            EasyUIUtils.openAddDlg(options);
            $('#type').combobox('setValue', "1");
            $('#cronExpr').textbox('setValue', '42 3 1 * *');
            TaskMVC.Util.fillReportCombox("add", []);
        },
        edit: function () {
            var row = $('#task-datagrid').datagrid('getSelected');
            if (row) {
                var options = TaskMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.name + ']任务';
                EasyUIUtils.openEditDlg(options);
                TaskMVC.Util.fillReportCombox("edit", roleIds.split(','));
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var fieldName = 'comment';
            var keyword = $("#keyword").val();
            var url = TaskMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#task-datagrid', url)
        },
        remove: function () {
            var row = $('#task-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: TaskMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#task-datagrid',
                    gridUrl: TaskMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: TaskMVC.URLs.list.url,
                dlgId: "#task-dlg",
                formId: "#task-form",
                url: null,
                callback: function () {
                }
            };

            $('#reportIds').val($('#combox-reports').combobox('getValues'));
            options.url = (action === "edit" ? TaskMVC.URLs.edit.url : TaskMVC.URLs.add.url);
            options.gridId = '#task-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#task-dlg',
                formId: '#task-form',
                actId: '#modal-action',
                rowId: '#taskId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        fillReportCombox: function (act, values) {
            var id = '#combox-reports';
            $(id).combobox('clear');
            var data = [];
            var items = TaskMVC.Model.reports;
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                data.push({
                    "value": item.id,
                    "name": item.name,
                    "selected": i == 0
                });
            }
            $(id).combobox('loadData', data);
            if (act == "edit") {
                $(id).combobox('setValues', values);
            }
        },
        loadReportList: function () {
            $.getJSON(TaskMVC.URLs.getAllReports.url, function (src) {
                TaskMVC.Model.reports = src.data;
            });
        },
        getJsonOptions: function (type) {
            $.getJSON(TaskMVC.URLs.getJsonOptions.url + '?type=' + type, function (result) {
                if (!result.code) {
                    $('#options').textbox('setValue', result.data);
                }
            });
        }
    }
};