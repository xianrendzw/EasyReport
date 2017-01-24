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
    baseReportUrl: EasyReport.ctxPath + '/rest/metadata/report/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/'
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
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('task.failed'), src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('task.id'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'reportIds',
                    title: jQuery.i18n.prop('task.reportids'),
                    width: 200,
                    sortable: true
                }, {
                    field: 'cronExpr',
                    title: jQuery.i18n.prop('task.cronexpr'),
                    width: 150,
                    sortable: true,
                }, {
                    field: 'type',
                    title: jQuery.i18n.prop('task.type'),
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 1) return jQuery.i18n.prop('task.email');
                        if (value === 2) return jQuery.i18n.prop('task.sms');
                        return jQuery.i18n.prop('task.other');
                    }
                }, {
                    field: 'comment',
                    title: jQuery.i18n.prop('task.comment'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: jQuery.i18n.prop('task.gmtcreated'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'options',
                    title: jQuery.i18n.prop('task.operation'),
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": jQuery.i18n.prop('task.edit')
                        }, {
                            "name": "remove",
                            "title": jQuery.i18n.prop('task.remove')
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
                    text: jQuery.i18n.prop('task.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#task-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('task.save'),
                    iconCls: 'icon-save',
                    handler: TaskMVC.Controller.save
                }]
            });

            $('#type').combobox({
                onChange: function (newValue, oldValue) {
                	if(oldValue!=""){
                		TaskMVC.Util.getJsonOptions(newValue);
                	}
                }
            });

            $('#cronExprDiv').cron({
                initial: "0 0/1 * * * ?",
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
            options.title = jQuery.i18n.prop('task.add.task');
            EasyUIUtils.openAddDlg(options);
            $('#type').combobox('setValue', "1");
            $('#cronExpr').textbox('setValue', '0 0/1 * * * ?');
            TaskMVC.Util.fillReportCombox("add", []);
            TaskMVC.Util.getJsonOptions("1");
            
            var template_html_end = "</body></html>";
            $('#template').textbox('setValue', result.data);
        },
        edit: function () {
            var row = $('#task-datagrid').datagrid('getSelected');
            if (row) {
                var options = TaskMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = jQuery.i18n.prop('task.edit.task',options.data.name);
                EasyUIUtils.openEditDlg(options);
                TaskMVC.Util.fillReportCombox("edit", row.reportIds.split(','));
            } else {
                $.messager.alert(jQuery.i18n.prop('task.warn'), jQuery.i18n.prop('task.please.select.record'), 'info');
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
                if (result.success) {
                    $('#options').textbox('setValue', result.data);
                }
            });
        }
    }
};