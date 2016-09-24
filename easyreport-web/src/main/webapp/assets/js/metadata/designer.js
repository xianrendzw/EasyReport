$(function () {
    MetaDataReport.init();
});

var MetaDataReport = {
    init: function () {
        ReportMVC.View.initControl();
        ReportMVC.View.initSqlEditorDivHeight();
        ReportMVC.View.initSqlEditor();
        ReportMVC.View.initHistorySqlEditor();
        ReportMVC.View.bindEvent();
        ReportMVC.View.bindValidate();
    },
    listReports: function (category) {
        ReportMVC.Controller.listReports(category.id);
    },
    addReport: function () {
        ReportMVC.Controller.add();
    }
}

var ReportCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/metadata/report/',
    baseHistoryUrl: EasyReport.ctxPath + '/rest/metadata/history/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/'
};

var ReportMVC = {
    URLs: {
        add: {
            url: ReportCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: ReportCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: ReportCommon.baseUrl + 'list',
            method: 'GET'
        },
        find: {
            url: ReportCommon.baseUrl + 'find',
            method: 'GET'
        },
        remove: {
            url: ReportCommon.baseUrl + 'remove',
            method: 'POST'
        },
        historyList: {
            url: ReportCommon.baseHistoryUrl + 'list',
            method: 'GET'
        },
        execSqlText: {
            url: ReportCommon.baseUrl + 'execSqlText',
            method: 'POST'
        },
        previewSqlText: {
            url: ReportCommon.baseUrl + 'previewSqlText',
            method: 'POST'
        },
        getSqlColumnScheme: {
            url: ReportCommon.baseUrl + 'getSqlColumnScheme',
            method: 'GET'
        }
    },
    Model: {
        SqlColumnOptions: [{
            name: "optional",
            text: "可选",
            type: 1
        }, {
            name: "percent",
            text: "百分比",
            type: 1
        }, {
            name: "displayInMail",
            text: "邮件显示",
            type: 1
        }, /*{
         name : "footings",
         text : "合计",
         type : 1
         }, {
         name : "extensions",
         text : "小计",
         type : 3
         },*/ {
            name: "expression",
            text: "表达式",
            type: 4
        }, {
            name: "comment",
            text: "备注",
            type: 2
        }, {
            name: "format",
            text: "格式",
            type: 2
        }],
        SqlColumnTypes: [{
            text: "布局列",
            value: 1
        }, {
            text: "维度列",
            value: 2
        }, {
            text: "统计列",
            value: 3
        }, {
            text: "计算列",
            value: 4
        }],
        SqlColumnSortTypes: [{
            text: "默认",
            value: 0
        }, {
            text: "数字优先升序",
            value: 1
        }, {
            text: "数字优先降序",
            value: 2
        }, {
            text: "字符优先升序",
            value: 3
        }, {
            text: "字符优先降序",
            value: 4
        }]
    },
    View: {
        SqlEditor: null,
        HistorySqlEditor: null,
        initControl: function () {
            $('#report-datagrid').datagrid({
                method: 'get',
                pageSize: 50,
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                toolbar: [{
                    iconCls: 'icon-info',
                    handler: function () {
                        ReportMVC.Controller.showDetail();
                    }
                }, '-', {
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: ReportMVC.Controller.add
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: ReportMVC.Controller.edit
                }, '-', {
                    text: '复制',
                    iconCls: 'icon-copy',
                    handler: ReportMVC.Controller.copy
                }, '-', {
                    text: '预览',
                    iconCls: 'icon-preview',
                    handler: ReportMVC.Controller.preview
                }, '-', {
                    text: '版本',
                    iconCls: 'icon-history',
                    handler: ReportMVC.Controller.showHistorySql
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: ReportMVC.Controller.remove
                }],
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    $.messager.alert('失败', src.msg, 'error');
                    return EasyUIUtils.getEmptyDatagridRows();
                },
                columns: [[{
                    field: 'id',
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'dsName',
                    title: '数据源',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value == 1 ? "启用" : "禁用";
                    }
                }, {
                    field: 'createUser',
                    title: '创建者',
                    width: 100,
                    sortable: true
                }, {
                    field: 'comment',
                    title: '说明',
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: '创建时间',
                    width: 80,
                    sortable: true
                }, {
                    field: 'gmtModified',
                    title: '修改时间',
                    width: 80,
                    sortable: true
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "info",
                            "title": "详细信息"
                        }, {
                            "name": "edit",
                            "title": "编辑"
                        }, {
                            "name": "copy",
                            "title": "复制"
                        }, {
                            "name": "preview",
                            "title": "预览"
                        }, {
                            "name": "history",
                            "title": "版本"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" ' +
                                'onclick="ReportMVC.Controller.doOption(\'${index}\',\'${name}\')">' +
                                '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: ReportCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ReportMVC.Controller.preview();
                }
            });

            $('#report-sql-column-grid').datagrid({
                method: 'post',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                rownumbers: true,
                tools: [{
                    iconCls: 'icon-up',
                    handler: function () {
                        EasyUIUtils.move("#report-sql-column-grid", 'up');
                    }
                }, '-', {
                    iconCls: 'icon-down',
                    handler: function () {
                        EasyUIUtils.move("#report-sql-column-grid", 'down');
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        var index = 1;
                        var rows = $("#report-sql-column-grid").datagrid('getRows');
                        if (rows && rows.length) {
                            for (var i = 0; i < rows.length; i++) {
                                var type = $("#type" + i).val();
                                if (type == 4) index++;
                            }
                        }
                        $.getJSON(ReportMVC.URLs.getSqlColumnScheme.url, function (result) {
                            if (result.success) {
                                row.name = row.name + index;
                                row.text = row.name;
                                row.type = 4;
                                row.sortType = 0;
                                $('#report-sql-column-grid').datagrid('appendRow', row);
                            }
                        });
                    }
                }, '-', {
                    iconCls: 'icon-cancel',
                    handler: function () {
                        var row = $("#report-sql-column-grid").datagrid('getSelected');
                        if (row) {
                            var index = $("#report-sql-column-grid").datagrid('getRowIndex', row);
                            $("#report-sql-column-grid").datagrid('deleteRow', index);
                            var rows = $("#report-sql-column-grid").datagrid('getRows');
                            $("#report-sql-column-grid").datagrid('loadData', rows);
                        }
                    }
                }],
                columns: [[{
                    field: 'name',
                    title: '列名',
                    width: 100,
                    formatter: function (value, row, index) {
                        var id = "name" + index;
                        var tmpl = '<input style="width:98%;" type="text" id="${id}" name="name" value="${value}" />';
                        return template.compile(tmpl)({
                            id: id,
                            value: row.name
                        });
                    }
                }, {
                    field: 'text',
                    title: '标题',
                    width: 100,
                    formatter: function (value, row, index) {
                        var id = "text" + index;
                        var tmpl = '<input style="width:98%;" type="text" id="${id}" name="text" value="${value}" />';
                        return template.compile(tmpl)({
                            id: id,
                            value: row.text
                        });
                    }
                }, {
                    field: 'type',
                    title: '类型',
                    width: 75,
                    formatter: function (value, row, index) {
                        var id = "type" + index;
                        var tmpl = '\
								<select id="${id}" name=\"type\">\
								${each list}\
									<option value="${$value.value}" ${if $value.value == currValue}selected${/if}>${$value.text}</option>\
								${/each}\
								</select>';
                        return template.compile(tmpl)({
                            id: id,
                            currValue: value,
                            list: ReportMVC.Model.SqlColumnTypes
                        });
                    }
                }, {
                    field: 'dataType',
                    title: '数据类型',
                    width: 75
                }, {
                    field: 'width',
                    title: '宽度',
                    width: 40
                }, {
                    field: 'decimals',
                    title: '精度',
                    width: 50,
                    formatter: function (value, row, index) {
                        var id = "decimals" + index;
                        if (!row.decimals) {
                            row.decimals = 0;
                        }
                        var tmpl = '<input style="width:42px;" type="text" id="${id}" name="decimals" value="${value}" />';
                        return template.compile(tmpl)({
                            id: id,
                            value: row.decimals
                        });
                    }
                }, {
                    field: 'sortType',
                    title: '排序类型',
                    width: 100,
                    formatter: function (value, row, index) {
                        var id = "sortType" + index;
                        var tmpl = '\
								<select id="${id}" name=\"sortType\">\
								${each list}\
									<option value="${$value.value}" ${if $value.value == currValue}selected${/if}>${$value.text}</option>\
								${/each}\
								</select>';
                        return template.compile(tmpl)({
                            id: id,
                            currValue: value,
                            list: ReportMVC.Model.SqlColumnSortTypes
                        });
                    }
                }, {
                    field: 'options',
                    title: '配置',
                    width: 300,
                    formatter: function (value, row, index) {
                        var subOptions = [];
                        // 4:计算列,3:统计列,2:维度列,1:布局列
                        if (row.type == 4) {
                            subOptions = $.grep(ReportMVC.Model.SqlColumnOptions, function (option, i) {
                                return option.type == 1 || option.type == 2 || option.type == 4;
                            });
                        } else if (row.type == 3) {
                            subOptions = $.grep(ReportMVC.Model.SqlColumnOptions, function (option, i) {
                                return option.type == 1 || option.type == 2;
                            });
                        } else {
                            subOptions = $.grep(ReportMVC.Model.SqlColumnOptions, function (option, i) {
                                return option.type == 2 || option.type == 3;
                            });
                        }

                        var htmlOptions = [];
                        for (var i = 0; i < subOptions.length; i++) {
                            var name = subOptions[i].name;
                            var data = {
                                id: name + index,
                                name: name,
                                text: subOptions[i].text,
                                checked: row[name] ? " checked=\"checked\"" : "",
                                imgSrc: "",
                                onClick: ""
                            };
                            var tmpl = "";
                            if (name == "expression" || name == "comment" || name == "format") {
                                data.imgSrc = ReportCommon.baseIconUrl + name + ".png";
                                data.onClick = "ReportDesigner.showSqlColumnGridOptionDialog('" + index + "','" + name + "')";
                                tmpl = '<img style="cursor: pointer;" id="${id}" title="${text}" src="${imgSrc}" onclick="${onClick}" />';
                            } else {
                                tmpl = '<input type="checkbox" id="${id}" name="${name}" ${checked}>${text}</input>'
                            }
                            htmlOptions.push(template.compile(tmpl)(data));
                        }
                        return htmlOptions.join(" ");
                    }
                }]]
            });

            $('#report-query-param-grid').datagrid({
                method: 'get',
                fit: true,
                singleSelect: true,
                rownumbers: true,
                tools: [{
                    iconCls: 'icon-up',
                    handler: function () {
                        EasyUIUtils.move('#report-query-param-grid', 'up');
                    }
                }, '-', {
                    iconCls: 'icon-down',
                    handler: function () {
                        EasyUIUtils.move('#report-query-param-grid', 'down');
                    }
                }],
                columns: [[{
                    field: 'name',
                    title: '参数名',
                    width: 100
                }, {
                    field: 'text',
                    title: '标题',
                    width: 100
                }, {
                    field: 'defaultValue',
                    title: '默认值',
                    width: 100
                }, {
                    field: 'defaultText',
                    title: '默认标题',
                    width: 100
                }, {
                    field: 'formElement',
                    title: '表单控件',
                    width: 100
                }, {
                    field: 'dataSource',
                    title: '来源类型',
                    width: 100,
                    formatter: function (value, row, index) {
                        if (value == "sql") {
                            return "SQL语句";
                        }
                        if (value == "text") {
                            return "文本字符串";
                        }
                        return "无内容";
                    }
                }, {
                    field: 'dataType',
                    title: '数据类型',
                    width: 100
                }, {
                    field: 'width',
                    title: '数据长度',
                    width: 100
                }, {
                    field: 'required',
                    title: '是否必选',
                    width: 80
                }, {
                    field: 'autoComplete',
                    title: '是否自动提示',
                    width: 80
                }, {
                    field: 'options',
                    title: '操作',
                    width: 50,
                    formatter: function (value, row, index) {
                        var imgPath = ReportCommon.baseIconUrl + 'remove.png';
                        var tmpl = '<a href="#" title ="删除" ' +
                            'onclick="ReportDesigner.deleteQueryParam(\'${index}\')"><img src="${imgPath}" ' +
                            'alt="删除"/"></a>';
                        return template.compile(tmpl)({
                            index: index,
                            imgPath: imgPath
                        });
                    }
                }]],
                onDblClickRow: function (index, row) {
                    $('#report-query-param-form').autofill(row);
                    $("#report-query-param-required").prop("checked", row.required);
                    $("#report-query-param-autoComplete").prop("checked", row.autoComplete);
                    $("#report-query-param-gridIndex").val(index);
                }
            });

            $('#report-history-sql-grid').datagrid({
                method: 'get',
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                pageSize: 30,
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    return EasyUIUtils.getEmptyDatagridRows();
                },
                columns: [[{
                    field: 'gmtCreated',
                    title: '日期',
                    width: 100
                }, {
                    field: 'author',
                    title: '作者',
                    width: 80
                }]],
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    $.messager.alert('失败', src.msg, 'error');
                    return EasyUIUtils.getEmptyDatagridRows();
                },
                onClickRow: function (index, row) {
                    ReportMVC.View.HistorySqlEditor.setValue(row.sqlText || "");
                }
            });

            $('#report-designer-dlg').dialog({
                closed: true,
                modal: false,
                width: window.screen.width - 350,
                height: window.screen.height - 350,
                maximizable: true,
                minimizable: true,
                maximized: true,
                iconCls: 'icon-add',
                buttons: [{
                    text: '编辑器放大/缩小',
                    iconCls: 'icon-fullscreen',
                    handler: ReportMVC.Controller.fullscreenEdit
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-designer-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: ReportMVC.Controller.save
                }],
                onResize: function (width, height) {
                    ReportMVC.Util.resizeSqlEditor();
                }
            });

            $('#report-history-sql-dlg').dialog({
                closed: true,
                modal: false,
                width: window.screen.width - 350,
                height: window.screen.height - 350,
                maximizable: true,
                iconCls: 'icon-history',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-history-sql-dlg").dialog('close');
                    }
                }]
            });

            $('#report-detail-dlg').dialog({
                closed: true,
                modal: true,
                width: window.screen.width - 550,
                height: window.screen.height - 450,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '上一条',
                    iconCls: 'icon-prev',
                    handler: function () {
                        EasyUIUtils.cursor('#report-datagrid',
                            '#current-row-index',
                            'prev', function (row) {
                                ReportMVC.Controller.preview(row);
                            });
                    }
                }, {
                    text: '下一条',
                    iconCls: 'icon-next',
                    handler: function () {
                        EasyUIUtils.cursor('#report-datagrid',
                            '#current-row-index',
                            'next', function (row) {
                                ReportMVC.Controller.preview(row);
                            });
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-detail-dlg").dialog('close');
                    }
                }]
            });
        },
        initSqlEditorDivHeight: function () {
            var paramDivHeight = (window.screen.height - 720);
            paramDivHeight = paramDivHeight <= 180 ? 180 : paramDivHeight;
            var sqlhistoryDivHeight = window.screen.height - 420 - 205;
            sqlhistoryDivHeight = sqlhistoryDivHeight <= 180 ? 180 : sqlhistoryDivHeight;

            $('#report-sql-column-grid-div').css({
                "height": paramDivHeight
            });
            $('#report-history-sql-grid-div').css({
                "height": sqlhistoryDivHeight
            });
        },
        initSqlEditor: function () {
            var dom = document.getElementById("report-sqlText");
            ReportMVC.View.SqlEditor = CodeMirror.fromTextArea(dom, {
                mode: 'text/x-mysql',
                theme: 'rubyblue',
                indentWithTabs: true,
                smartIndent: true,
                lineNumbers: true,
                styleActiveLine: true,
                matchBrackets: true,
                autofocus: true,
                extraKeys: {
                    "F11": function (cm) {
                        cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                    },
                    "Esc": function (cm) {
                        if (cm.getOption("fullScreen")) {
                            cm.setOption("fullScreen", false);
                        }
                    },
                    "Tab": "autocomplete"
                }
            });
            ReportMVC.View.SqlEditor.on("change", function (cm, obj) {
                if (obj.origin == "setValue") {
                    $('#report-sqlTextIsChange').val(0);
                } else {
                    $('#report-sqlTextIsChange').val(1);
                }
            });
        },
        initHistorySqlEditor: function () {
            var dom = document.getElementById("report-history-sqlText");
            ReportMVC.View.HistorySqlEditor = CodeMirror.fromTextArea(dom, {
                mode: 'text/x-mysql',
                theme: 'rubyblue',
                indentWithTabs: true,
                smartIndent: true,
                lineNumbers: true,
                matchBrackets: true,
                autofocus: true,
                extraKeys: {
                    "F11": function (cm) {
                        cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                    },
                    "Esc": function (cm) {
                        if (cm.getOption("fullScreen")) {
                            cm.setOption("fullScreen", false);
                        }
                    }
                }
            });
        },
        bindEvent: function () {
            $('#btn-report-search').bind('click', ReportMVC.Controller.find);
            $('#btn-report-exec-sql').bind('click', ReportMVC.Controller.find);
            $('#btn-report-preview-sql').bind('click', ReportMVC.Controller.find);
            $('#btn-report-query-param-add').bind('click', ReportMVC.Controller.find);
            $('#btn-report-query-param-edit').bind('click', ReportMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#report-datagrid').datagrid('selectRow', index);
            if (name == "info") {
                return ReportMVC.Controller.showDetail();
            }
            if (name == "edit") {
                return ReportMVC.Controller.edit();
            }
            if (name == "copy") {
                return ReportMVC.Controller.copy();
            }
            if (name == "preview") {
                return ReportMVC.Controller.preview();
            }
            if (name == "remove") {
                return ReportMVC.Controller.remove();
            }
            if (name == "history") {
                return ReportMVC.Controller.showHistorySql();
            }
        },
        add: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                var category = node.attributes;
                var options = ReportMVC.Util.getOptions();
                options.title = '报表设计器--新增报表';
                EasyUIUtils.openAddDlg(options);
                ReportMVC.Util.clearSqlEditor();
                EasyUIUtils.clearDatagrid('#report-sql-column-grid');

                $('#report-category-name').text(category.name);
                $('#report-categoryId').text(category.id);
            } else {
                $.messager.alert('警告', '请选中一个报表分类!', 'info');
            }
        },
        edit: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                var options = ReportMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '报表设计器--修改[' + options.data.name + ']报表';

                ReportMVC.Util.clearSqlEditor();
                EasyUIUtils.openEditDlg(options);
                ReportMVC.Controller.loadParams(row.params)
                ReportMVC.View.SqlEditor.setValue(row.sqlText || "");
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ReportMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '##report-datagrid',
                    gridUrl: ReportMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        copy: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                var options = ReportMVC.Util.getOptions();
                options.iconCls = 'icon-copy';
                options.data = row;
                options.title = '报表设计器--复制[' + options.data.name + ']报表';

                ReportMVC.Util.clearSqlEditor();
                EasyUIUtils.openEditDlg(options);
                $('#modal-action').val("add");
                $('#report-name').textbox('setValue', '');
                ReportMVC.Controller.loadParams(row.params)
                ReportMVC.View.SqlEditor.setValue(row.sqlText || "");
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        preview: function (row) {

        },
        showDetail: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                $('#report-detail-dlg').dialog('open').dialog('center');
                ReportMVC.Util.showReportProps(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        showHistorySql: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                $('#report-history-sql-dlg').dialog('open').dialog('center');
                ReportMVC.View.HistorySqlEditor.setValue('');
                ReportMVC.View.HistorySqlEditor.refresh();
                var url = ReportMVC.URLs.historyList.url + 'list?reportId=' + row.id;
                EasyUIUtils.loadToDatagrid('#report-history-sql-grid', url)
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var keyword = $("#report-search-keyword").val();
            var url = ReportMVC.URLs.find.url + '?fieldName=name&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#report-datagrid', url)
        },
        save: function () {
            var action = $('#modal-action').val();
            var actUrl = action === "edit" ? ReportMVC.URLs.edit.url : ReportMVC.URLs.add.url;
            var options = {
                dlgId: "#report-designer-dlg",
                formId: "#report-basic-conf-form",
                url: actUrl,
                callback: function () {
                    var id = $("#report-categoryId").val();
                    ReportMVC.Controller.listReports(id);
                }
            };
            EasyUIUtils.save(options);

            //$('#report-params').val(JSON.stringify(ReportMVC.Controller.getParams()));
        },
        listReports: function (id) {
            var gridUrl = ReportMVC.URLs.list.url + '?id=' + id;
            EasyUIUtils.loadDataWithUrl('#report-datagrid', gridUrl);
        },
        loadParams: function (params) {
            EasyUIUtils.clearDatagrid('#report-sql-column-grid');
            if ((params || '').length > 0) {
                $("#report-sql-column-grid").datagrid('loadData', eval(params));
            }
        },
        getParams: function () {
            var rows = $("#report-sql-column-grid").datagrid('getRows');
            for (var rowIndex = 0; rowIndex < rows.length; rowIndex++) {
                var row = rows[rowIndex];
                row["name"] = $("#name" + rowIndex).val();
                row["cnName"] = $("#cnName" + rowIndex).val();
                row["required"] = $("#required" + rowIndex).val();
                row["dataType"] = $("#dataType" + rowIndex).val();
                row["defaultValue"] = $("#defaultValue" + rowIndex).val();
                row["comment"] = $("#comment" + rowIndex).val();
            }
            return rows;
        },
        fullscreenEdit: function () {
            ReportMVC.View.SqlEditor.setOption("fullScreen", !ReportMVC.View.SqlEditor.getOption("fullScreen"));
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#report-designer-dlg',
                formId: '#report-basic-conf-form',
                actId: '#modal-action',
                rowId: '#report-id',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        clearSqlEditor: function () {
            ReportMVC.View.SqlEditor.setValue('');
            ReportMVC.View.SqlEditor.refresh();
        },
        resizeSqlEditor: function () {
            var width = $('#report-designer-dlg').panel('options').width - 180;
            $('#report-sqlText-td>.CodeMirror').css({"width": width});
        },
        showReportProps: function (data) {
            $('#report-detail-dlg label').each(function (i) {
                $(this).text("");
            });

            for (var name in data) {
                var id = "#report-detail-" + name;
                var value = ReportMVC.Util.getPropertyValue(name, data);
                $(id).text(value);
            }
        },
        eachSqlColumnRows: function (rows) {
            if (rows && rows.length) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    row.type = ReportMVC.Util.getColumnTypeValue(row.type);
                    row.sortType = ReportMVC.Util.getColumnSortTypeValue(row.sortType);
                }
            }
            return rows;
        },
        getPropertyValue: function (name, object) {
            var value = object[name];
            if (name == "layout") {
                return ReportMVC.Util.getLayoutName(value);
            }
            if (name == "status") {
                return value == 1 ? "启用" : "禁用";
            }
            return value;
        },
        getLayoutName: function (layout) {
            if (layout == 1) {
                return "横向布局";
            }
            if (layout == 2) {
                return "纵向布局";
            }
            return "无";
        },
        getColumnTypeValue: function (name) {
            if (name == "LAYOUT") {
                return 1;
            }
            if (name == "DIMENSION") {
                return 2;
            }
            if (name == "STATISTICAL") {
                return 3;
            }
            if (name == "COMPUTED") {
                return 4;
            }
            return 2;
        },
        getColumnSortTypeValue: function (name) {
            if (name == "DEFAULT") {
                return 0;
            }
            if (name == "DIGIT_ASCENDING") {
                return 1;
            }
            if (name == "DIGIT_DESCENDING") {
                return 2;
            }
            if (name == "CHAR_ASCENDING") {
                return 3;
            }
            if (name == "CHAR_DESCENDING") {
                return 4;
            }
            return 0;
        }
    }
};
