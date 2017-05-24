$(function () {
    MetaDataDesigner.init();
});

var MetaDataDesigner = {
    init: function () {
        DesignerMVC.View.initControl();
        DesignerMVC.View.resizeDesignerElments();
        DesignerMVC.View.initSqlEditor();
        DesignerMVC.View.initHistorySqlEditor();
        DesignerMVC.View.initPreviewSqlEditor();
        DesignerMVC.View.bindEvent();
        DesignerMVC.View.bindValidate();
        DesignerMVC.View.initData();
    },
    listReports: function (category) {
        DesignerMVC.Controller.listReports(category.id);
    },
    addReport: function () {
        DesignerMVC.Controller.add();
    },
    showMetaColumnOption: function (index, name) {
        DesignerMVC.Controller.showMetaColumnOption(index, name);
    },
    deleteQueryParam: function (index) {
        DesignerMVC.Controller.deleteQueryParam(index);
    }
};

var DesignerCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/report/designer/',
    baseHistoryUrl: EasyReport.ctxPath + '/rest/report/history/',
    baseDsUrl: EasyReport.ctxPath + '/rest/report/ds/',
    baseIconUrl: EasyReport.ctxPath + '/custom/easyui/themes/icons/',
    baseReportUrl: EasyReport.ctxPath + '/report/'
};

var DesignerMVC = {
    URLs: {
        add: {
            url: DesignerCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: DesignerCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: DesignerCommon.baseUrl + 'list',
            method: 'GET'
        },
        find: {
            url: DesignerCommon.baseUrl + 'find',
            method: 'GET'
        },
        remove: {
            url: DesignerCommon.baseUrl + 'remove',
            method: 'POST'
        },
        historyList: {
            url: DesignerCommon.baseHistoryUrl + 'list',
            method: 'GET'
        },
        execSqlText: {
            url: DesignerCommon.baseUrl + 'execSqlText',
            method: 'POST'
        },
        previewSqlText: {
            url: DesignerCommon.baseUrl + 'previewSqlText',
            method: 'POST'
        },
        getMetaColumnScheme: {
            url: DesignerCommon.baseUrl + 'getMetaColumnScheme',
            method: 'GET'
        },
        DataSource: {
            listAll: {
                url: DesignerCommon.baseDsUrl + 'listAll',
                method: 'GET'
            }
        },
        Report: {
            url: DesignerCommon.baseReportUrl + 'uid/',
            method: 'GET'
        }
    },
    Model: {
        MetaColumnOptions: [{
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
        }
            /*, {
             name: "format",
             text: "格式",
             type: 2
             }*/],
        MetaColumnTypes: [{
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
        MetaColumnSortTypes: [{
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
        }],
        DataSourceList: []
    },
    View: {
        SqlEditor: null,
        PreviewSqlEditor: null,
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
                    text: '详细信息',
                    iconCls: 'icon-info',
                    handler: function () {
                        DesignerMVC.Controller.showDetail();
                    }
                }, '-', {
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: DesignerMVC.Controller.add
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: DesignerMVC.Controller.edit
                }, '-', {
                    text: '复制',
                    iconCls: 'icon-copy',
                    handler: DesignerMVC.Controller.copy
                }, '-', {
                    text: '预览',
                    iconCls: 'icon-preview',
                    handler: DesignerMVC.Controller.preview
                }, '-', {
                    text: '版本',
                    iconCls: 'icon-history',
                    handler: DesignerMVC.Controller.showHistorySql
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: DesignerMVC.Controller.remove
                }],
                loadFilter: function (src) {
                    if (!src.code) {
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
                    width: 150,
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
                                'onclick="DesignerMVC.Controller.doOption(\'${index}\',\'${name}\')">' +
                                '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: DesignerCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return DesignerMVC.Controller.preview();
                },
                onRowContextMenu: function (e, index, row) {
                    e.preventDefault();
                    $('#report-datagrid-ctx-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            });

            $('#report-datagrid-ctx-menu').menu({
                onClick: function (item) {
                    if (item.name == "preview") {
                        return DesignerMVC.Controller.preview();
                    }
                    if (item.name == "window") {
                        return DesignerMVC.Controller.previewInNewWindow();
                    }
                    if (item.name == "add") {
                        return DesignerMVC.Controller.add();
                    }
                    if (item.name == "edit") {
                        return DesignerMVC.Controller.edit();
                    }
                    if (item.name == "remove") {
                        return DesignerMVC.Controller.remove();
                    }
                    if (item.name == "copy") {
                        return DesignerMVC.Controller.copy();
                    }
                    if (item.name == "info") {
                        return DesignerMVC.Controller.showDetail();
                    }
                    if (item.name == "history") {
                        return DesignerMVC.Controller.showHistorySql();
                    }
                    if (item.name == "refresh") {
                        return DesignerMVC.Controller.reload();
                    }
                }
            });

            $('#report-meta-column-grid').datagrid({
                method: 'post',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                rownumbers: true,
                tools: [{
                    iconCls: 'icon-up',
                    handler: function () {
                        EasyUIUtils.move("#report-meta-column-grid", 'up');
                    }
                }, '-', {
                    iconCls: 'icon-down',
                    handler: function () {
                        EasyUIUtils.move("#report-meta-column-grid", 'down');
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        var index = 1;
                        var rows = $("#report-meta-column-grid").datagrid('getRows');
                        if (rows && rows.length) {
                            for (var i = 0; i < rows.length; i++) {
                                var type = $("#type" + i).val();
                                if (type == 4) index++;
                            }
                        }
                        $.getJSON(DesignerMVC.URLs.getMetaColumnScheme.url, function (result) {
                            if (!result.code) {
                                var row = result.data;
                                row.name = row.name + index;
                                row.text = row.name;
                                row.type = 4;
                                row.sortType = 0;
                                $('#report-meta-column-grid').datagrid('appendRow', row);
                            }
                        });
                    }
                }, '-', {
                    iconCls: 'icon-cancel',
                    handler: function () {
                        var row = $("#report-meta-column-grid").datagrid('getSelected');
                        if (row) {
                            var index = $("#report-meta-column-grid").datagrid('getRowIndex', row);
                            $("#report-meta-column-grid").datagrid('deleteRow', index);
                            var rows = $("#report-meta-column-grid").datagrid('getRows');
                            $("#report-meta-column-grid").datagrid('loadData', rows);
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
                        return juicer(tmpl, {
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
                        return juicer(tmpl, {
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
                        var tmpl =
                            '<select id="${id}" name=\"type\">' +
                            '{@each list as item}' +
                            '<option value="${item.value}" {@if item.value == currValue} selected {@/if}>${item.text}</option>' +
                            '{@/each}' +
                            '</select>';
                        return juicer(tmpl, {
                            id: id,
                            currValue: value,
                            list: DesignerMVC.Model.MetaColumnTypes
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
                        return juicer(tmpl, {
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
                        var tmpl =
                            '<select id="${id}" name=\"sortType\">' +
                            '{@each list as item}' +
                            '<option value="${item.value}" {@if item.value == currValue} selected {@/if}>${item.text}</option>' +
                            '{@/each}' +
                            '</select>';
                        return juicer(tmpl, {
                            id: id,
                            currValue: value,
                            list: DesignerMVC.Model.MetaColumnSortTypes
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
                            subOptions = $.grep(DesignerMVC.Model.MetaColumnOptions, function (option, i) {
                                return option.type == 1 || option.type == 2 || option.type == 4;
                            });
                        } else if (row.type == 3) {
                            subOptions = $.grep(DesignerMVC.Model.MetaColumnOptions, function (option, i) {
                                return option.type == 1 || option.type == 2;
                            });
                        } else {
                            subOptions = $.grep(DesignerMVC.Model.MetaColumnOptions, function (option, i) {
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
                                data.imgSrc = DesignerCommon.baseIconUrl + name + ".png";
                                data.onClick = "MetaDataDesigner.showMetaColumnOption('" + index + "','" + name + "')";
                                tmpl = '<img style="cursor: pointer;" id="${id}" title="${text}" src="${imgSrc}" onclick="${onClick}" />';
                            } else {
                                tmpl = '<input type="checkbox" id="${id}" name="${name}" ${checked}>${text}</input>'
                            }
                            htmlOptions.push(juicer(tmpl, data));
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
                        var imgPath = DesignerCommon.baseIconUrl + 'remove.png';
                        var tmpl = '<a href="#" title ="删除" ' +
                            'onclick="MetaDataDesigner.deleteQueryParam(\'${index}\')"><img src="${imgPath}" ' +
                            'alt="删除"/"></a>';
                        return juicer(tmpl, {
                            index: index,
                            imgPath: imgPath
                        });
                    }
                }]],
                onDblClickRow: function (index, row) {
                    $('#report-query-param-form').form('load', row);
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
                    if (!src.code) {
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
                    width: 100
                }]],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    $.messager.alert('失败', src.msg, 'error');
                    return EasyUIUtils.getEmptyDatagridRows();
                },
                onClickRow: function (index, row) {
                    DesignerMVC.Controller.showHistorySqlDetail(row);
                },
                onSelect: function (index, row) {
                    DesignerMVC.Controller.showHistorySqlDetail(row);
                }
            });

            $('#report-history-sql-pgrid').propertygrid({
                scrollbarSize: 0,
                columns: [[
                    {field: 'name', title: '属性项', width: 80, sortable: true},
                    {field: 'value', title: '属性值', width: 300, resizable: false}
                ]]
            });

            $('#report-designer-dlg').dialog({
                closed: true,
                modal: false,
                width: window.screen.width - 350,
                height: window.screen.height - 350,
                maximizable: true,
                minimizable: true,
                maximized: true,
                iconCls: 'icon-designer',
                buttons: [{
                    text: '编辑器放大/缩小',
                    iconCls: 'icon-fullscreen',
                    handler: DesignerMVC.Util.fullscreenEdit
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-designer-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: DesignerMVC.Controller.save
                }],
                onResize: function (width, height) {
                    DesignerMVC.Util.resizeDesignerDlgElments();
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
                width: window.screen.width - 350,
                height: window.screen.height - 350,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '上一条',
                    iconCls: 'icon-prev',
                    handler: function () {
                        EasyUIUtils.cursor('#report-datagrid',
                            '#current-row-index',
                            'prev', function (row) {
                                DesignerMVC.Controller.showDetail(row);
                            });
                    }
                }, {
                    text: '下一条',
                    iconCls: 'icon-next',
                    handler: function () {
                        EasyUIUtils.cursor('#report-datagrid',
                            '#current-row-index',
                            'next', function (row) {
                                DesignerMVC.Controller.showDetail(row);
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

            $('#report-preview-sql-dlg').dialog({
                closed: true,
                modal: true,
                maximizable: true,
                iconCls: 'icon-sql',
                width: window.screen.width - 350,
                height: window.screen.height - 350,
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-preview-sql-dlg").dialog('close');
                    }
                }]
            });

            $('#report-column-expression-dlg').dialog({
                closed: true,
                modal: true,
                iconCls: 'icon-formula',
                top: (screen.height - 500) / 2,
                left: (screen.width - 300) / 2,
                width: 500,
                height: 310,
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-column-expression-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        DesignerMVC.Controller.saveMetaColumnOption('expression');
                    }
                }]
            });

            $('#report-column-comment-dlg').dialog({
                closed: true,
                modal: true,
                iconCls: 'icon-comment',
                top: (screen.height - 500) / 2,
                left: (screen.width - 300) / 2,
                width: 500,
                height: 310,
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-column-comment-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        DesignerMVC.Controller.saveMetaColumnOption('comment');
                    }
                }]
            });

            $('#report-query-param-formElement').combobox({
                onSelect: function (rec) {
                    var value = "sql";
                    if (rec.value == "text") {
                        value = 'none';
                    }
                    $('#report-query-param-dataSource').combobox('setValue', value);
                }
            });
        },
        resizeDesignerElments: function () {
            DesignerMVC.Util.resizeDesignerDlgElments();
        },
        initSqlEditor: function () {
            var dom = document.getElementById("report-sqlText");
            DesignerMVC.View.SqlEditor = CodeMirror.fromTextArea(dom, {
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
            DesignerMVC.View.SqlEditor.on("change", function (cm, obj) {
                if (obj.origin == "setValue") {
                    $('#report-sqlTextIsChange').val(0);
                } else {
                    $('#report-sqlTextIsChange').val(1);
                }
            });
        },
        initHistorySqlEditor: function () {
            var dom = document.getElementById("report-history-sqlText");
            DesignerMVC.View.HistorySqlEditor = CodeMirror.fromTextArea(dom, {
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
        initPreviewSqlEditor: function () {
            var dom = document.getElementById("report-preview-sqlText");
            DesignerMVC.View.PreviewSqlEditor = CodeMirror.fromTextArea(dom, {
                mode: 'text/x-mysql',
                theme: 'rubyblue',
                indentWithTabs: true,
                smartIndent: true,
                lineNumbers: true,
                matchBrackets: true,
                autofocus: true
            });
        },
        bindEvent: function () {
            $('#btn-report-search').bind('click', DesignerMVC.Controller.find);
            $('#btn-report-exec-sql').bind('click', DesignerMVC.Controller.executeSql);
            $('#btn-report-preview-sql').bind('click', DesignerMVC.Controller.previewSql);
            $('#btn-report-query-param-add').bind('click', function (e) {
                DesignerMVC.Controller.addOrEditQueryParam('add');
            });
            $('#btn-report-query-param-edit').bind('click', function (e) {
                DesignerMVC.Controller.addOrEditQueryParam('edit');
            });
        },
        bindValidate: function () {
        },
        initData: function () {
            DesignerMVC.Data.loadDataSourceList();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#report-datagrid').datagrid('selectRow', index);
            if (name == "info") {
                return DesignerMVC.Controller.showDetail();
            }
            if (name == "edit") {
                return DesignerMVC.Controller.edit();
            }
            if (name == "copy") {
                return DesignerMVC.Controller.copy();
            }
            if (name == "preview") {
                return DesignerMVC.Controller.preview();
            }
            if (name == "remove") {
                return DesignerMVC.Controller.remove();
            }
            if (name == "history") {
                return DesignerMVC.Controller.showHistorySql();
            }
        },
        add: function () {
            var node = $('#category-tree').tree('getSelected');
            if (node) {
                var dsId = $('#report-dsId').combobox('getValue');
                var category = node.attributes;
                var options = DesignerMVC.Util.getOptions();
                options.title = '报表设计器--新增报表';
                EasyUIUtils.openAddDlg(options);
                DesignerMVC.Util.clearSqlEditor();
                EasyUIUtils.clearDatagrid('#report-meta-column-grid');
                var row = {
                    name: "",
                    categoryId: category.id,
                    dsId: dsId,
                    status: 1,
                    sequence: 10,
                    options: {
                        layout: 2,
                        statColumnLayout: 1,
                        dataRange: 7
                    }
                };
                DesignerMVC.Util.fillReportBasicConfForm(row, row.options);
            } else {
                $.messager.alert('警告', '请选中一个报表分类!', 'info');
            }
        },
        edit: function () {
            DesignerMVC.Util.isRowSelected(function (row) {
                var options = DesignerMVC.Util.getOptions();
                options.iconCls = 'icon-designer';
                options.data = row;
                options.title = '报表设计器--修改[' + options.data.name + ']报表';
                DesignerMVC.Util.editOrCopy(options, 'edit');
            });
        },
        copy: function () {
            DesignerMVC.Util.isRowSelected(function (row) {
                var options = DesignerMVC.Util.getOptions();
                options.iconCls = 'icon-designer';
                options.data = row;
                options.title = '报表设计器--复制[' + options.data.name + ']报表';
                DesignerMVC.Util.editOrCopy(options, 'copy');
                $('#modal-action').val("add");
            });
        },
        remove: function () {
            DesignerMVC.Util.isRowSelected(function (row) {
                var options = {
                    rows: [row],
                    url: DesignerMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#report-datagrid',
                    gridUrl: DesignerMVC.URLs.list.url + '?id=' + row.categoryId,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            });
        },
        preview: function () {
            DesignerMVC.Util.isRowSelected(function (row) {
                var url = DesignerMVC.URLs.Report.url + row.uid;
                parent.HomeIndex.addTab(row.id, row.name, url, "");
                //parent.HomeIndex.selectedTab();
            });
        },
        previewInNewWindow: function () {
            DesignerMVC.Util.isRowSelected(function (row) {
                var url = DesignerMVC.URLs.Report.url + row.uid;
                var win = window.open(url, '_blank');
                win.focus();
            });
        },
        showDetail: function () {
            DesignerMVC.Util.isRowSelected(function (row) {
                $('#report-detail-dlg').dialog('open').dialog('center');
                DesignerMVC.Util.fillDetailLabels(row);
            });
        },
        showHistorySql: function () {
            DesignerMVC.Util.isRowSelected(function (row) {
                $('#report-history-sql-dlg').dialog('open').dialog('center');
                DesignerMVC.View.HistorySqlEditor.setValue('');
                DesignerMVC.View.HistorySqlEditor.refresh();
                var url = DesignerMVC.URLs.historyList.url + '?reportId=' + row.id;
                EasyUIUtils.loadDataWithCallback('#report-history-sql-grid', url, function () {
                    $('#report-history-sql-grid').datagrid('selectRow', 0);
                });
            });
        },
        showHistorySqlDetail: function (row) {
            DesignerMVC.View.HistorySqlEditor.setValue(row.sqlText || "");
            var data = EasyUIUtils.toPropertygridRows(row);
            $('#report-history-sql-pgrid').propertygrid('loadData', data);
        },
        find: function () {
            var keyword = $("#report-search-keyword").val();
            var url = DesignerMVC.URLs.find.url + '?fieldName=name&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#report-datagrid', url)
        },
        executeSql: function () {
            if (!DesignerMVC.Util.checkBasicConfParam()) return;

            $.messager.progress({
                title: '请稍后...',
                text: '正在执行中...'
            });

            $.post(DesignerMVC.URLs.execSqlText.url, {
                sqlText: DesignerMVC.View.SqlEditor.getValue(),
                dsId: $('#report-dsId').combobox('getValue'),
                dataRange: $('#report-dataRange').val(),
                queryParams: DesignerMVC.Util.getQueryParams()
            }, function (result) {
                $.messager.progress("close");
                if (!result.code) {
                    $("#report-meta-column-grid").datagrid('clearChecked');
                    var columns = DesignerMVC.Util.eachMetaColumns(result.data);
                    return DesignerMVC.Util.loadMetaColumns(columns);
                }
                return $.messager.alert('错误', result.msg);
            }, 'json');
        },
        previewSql: function () {
            if (!DesignerMVC.Util.checkBasicConfParam()) return;

            $.messager.progress({
                title: '请稍后...',
                text: '正在生成预览SQL...',
            });

            $.post(DesignerMVC.URLs.previewSqlText.url, {
                dsId: $('#report-dsId').combobox('getValue'),
                sqlText: DesignerMVC.View.SqlEditor.getValue(),
                dataRange: $('#report-dataRange').val(),
                queryParams: DesignerMVC.Util.getQueryParams()
            }, function (result) {
                $.messager.progress("close");
                if (!result.code) {
                    $('#report-preview-sql-dlg').dialog('open');
                    $('#report-preview-sql-dlg .CodeMirror').css("height", "99%");
                    return DesignerMVC.View.PreviewSqlEditor.setValue(result.data);
                }
                return $.messager.alert('错误', result.msg);
            }, 'json');
        },
        save: function () {
            if (!DesignerMVC.Util.checkBasicConfParam()) return;

            var rows = $("#report-meta-column-grid").datagrid('getRows');
            if (rows == null || rows.length == 0) {
                return $.messager.alert('失败', "没有任何报表SQL配置列选项！", 'error');
            }

            var metaColumns = DesignerMVC.Util.getMetaColumns(rows);
            var columnTypeMap = DesignerMVC.Util.getColumnTypeMap(metaColumns);
            if (columnTypeMap.layout == 0 || columnTypeMap.stat == 0) {
                return $.messager.alert('失败', "您没有设置布局列或者统计列", 'error');
            }

            var emptyExprColumns = DesignerMVC.Util.getEmptyExprColumns(metaColumns);
            if (emptyExprColumns && emptyExprColumns.length) {
                return $.messager.alert('失败', "计算列：[" + emptyExprColumns.join() + "]没有设置表达式！", 'error');
            }

            $.messager.progress({
                title: '请稍后...',
                text: '正在处理中...',
            });

            $('#report-queryParams').val(DesignerMVC.Util.getQueryParams());

            var action = $('#modal-action').val();
            var actUrl = action === "edit" ? DesignerMVC.URLs.edit.url : DesignerMVC.URLs.add.url;
            var data = $('#report-basic-conf-form').serializeObject();
            data.isChange = $('#report-sqlTextIsChange').val() != 0;
            data.sqlText = DesignerMVC.View.SqlEditor.getValue();
            data["options"] = JSON.stringify({
                layout: data.layout,
                statColumnLayout: data.statColumnLayout,
                dataRange: data.dataRange
            });
            data["metaColumns"] = JSON.stringify(metaColumns);

            $.post(actUrl, data, function (result) {
                $.messager.progress("close");
                if (!result.code) {
                    $('#report-sqlTextIsChange').val('0');
                    var id = $("#report-categoryId").val();
                    return $.messager.alert('操作提示', "操作成功", 'info', function () {
                        $('#report-designer-dlg').dialog('close');
                        DesignerMVC.Controller.listReports(id);
                    });
                }
                $.messager.alert('操作提示', result.msg, 'error');
            }, 'json');
        },
        reload: function () {
            EasyUIUtils.reloadDatagrid('#report-datagrid');
        },
        saveChanged: function (data, handler) {
            var isChanged = $("#report-sqlTextIsChange").val() != 0;
            if (!isChanged) {
                return handler(data);
            }
            $.messager.confirm('确认', '是否保存修改的数据?', function (r) {
                if (r) {
                    //MetaDataDesigner.save();
                }
                //handler(data);
            });
        },
        deleteQueryParam: function (index) {
            $("#report-query-param-grid").datagrid('deleteRow', index);
            $("#report-query-param-grid").datagrid('reload', $("#report-query-param-grid").datagrid('getRows'));
        },
        addOrEditQueryParam: function (act) {
            if ($("#report-query-param-form").form('validate')) {
                var row = $('#report-query-param-form').serializeObject()
                if (row.dataSource != "none" && $.trim(row.content).length == 0) {
                    $("#report-query-param-content").focus();
                    return $.messager.alert('提示', "内容不能为空", 'error');
                }

                row.required = $("#report-query-param-required").prop("checked");
                row.autoComplete = $("#report-query-param-required").prop("checked");

                if (act == "add") {
                    $('#report-query-param-grid').datagrid('appendRow', row);
                } else if (act == "edit") {
                    var index = $("#report-query-param-gridIndex").val();
                    $('#report-query-param-grid').datagrid('updateRow', {
                        index: index,
                        row: row
                    });
                }
                $('#report-query-param-form').form('reset');
            }
        },
        showMetaColumnOption: function (index, name) {
            $("#report-meta-column-grid").datagrid('selectRow', index);
            var row = $("#report-meta-column-grid").datagrid('getSelected');
            if (name == "expression") {
                $('#report-column-expression-dlg').dialog('open');
                return $("#report-column-expression").val(row.expression);
            }
            if (name == "comment") {
                $('#report-column-comment-dlg').dialog('open');
                return $("#report-column-comment").val(row.comment);
            }
        },
        saveMetaColumnOption: function (name) {
            var row = $("#report-meta-column-grid").datagrid('getSelected');
            if (name == "expression") {
                row.expression = $("#report-column-expression").val();
                return $('#report-column-expression-dlg').dialog('close');
            }
            if (name == "comment") {
                row.comment = $("#report-column-comment").val();
                return $('#report-column-comment-dlg').dialog('close');
            }
        },
        listReports: function (id) {
            var gridUrl = DesignerMVC.URLs.list.url + '?id=' + id;
            EasyUIUtils.loadDataWithUrl('#report-datagrid', gridUrl);
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
        isRowSelected: function (func) {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        editOrCopy: function (options, act) {
            DesignerMVC.Util.clearSqlEditor();
            EasyUIUtils.openEditDlg(options);

            var row = options.data;
            if (act === 'copy') {
                row.name = '';
            }
            DesignerMVC.Util.fillReportBasicConfForm(row, $.toJSON(row.options));
            DesignerMVC.View.SqlEditor.setValue(row.sqlText || "");
            DesignerMVC.Util.loadMetaColumns($.toJSON(row.metaColumns));
            DesignerMVC.Util.loadQueryParams($.toJSON(row.queryParams));
        },
        clearSqlEditor: function () {
            DesignerMVC.View.SqlEditor.setValue('');
            DesignerMVC.View.SqlEditor.refresh();

            DesignerMVC.View.PreviewSqlEditor.setValue('');
            DesignerMVC.View.PreviewSqlEditor.refresh();

            DesignerMVC.View.HistorySqlEditor.setValue('');
            DesignerMVC.View.HistorySqlEditor.refresh();
        },
        resizeDesignerDlgElments: function () {
            var panelOptions = $('#report-designer-dlg').panel('options');
            $('#report-sqlText-td>.CodeMirror').css({"width": panelOptions.width - 230});

            var tabHeight = panelOptions.height - 160;
            var confFormDivHeight = $('#report-basic-conf-form-div').height();
            var metaColumnDivHeight = (tabHeight - confFormDivHeight);
            if (metaColumnDivHeight <= 180) metaColumnDivHeight = 180;
            $('#report-meta-column-div').css({
                "height": metaColumnDivHeight
            });

            var queryParamFormDivHeight = $('#report-query-param-form-div').height();
            var queryParamDivHeight = (tabHeight - queryParamFormDivHeight - 200);
            if (queryParamDivHeight <= 180) queryParamDivHeight = 180;
            $('#report-query-param-div').css({
                "height": queryParamDivHeight
            });
        },
        fullscreenEdit: function () {
            DesignerMVC.View.SqlEditor.setOption("fullScreen", !DesignerMVC.View.SqlEditor.getOption("fullScreen"));
        },
        fillReportBasicConfForm: function (row, options) {
            $('#report-basic-conf-form').form('load', row);
            $('#report-basic-conf-form').form('load', options);
            $('#report-category-name').text(row.categoryName);
            $('#report-categoryId').text(row.categoryId);
        },
        fillDetailLabels: function (data) {
            $('#report-detail-dlg label').each(function (i) {
                $(this).text("");
            });

            for (var name in data) {
                var id = "#report-detail-" + name;
                var value = DesignerMVC.Util.getPropertyValue(name, data);
                $(id).text(value);
            }

            var options = $.toJSON(data.options);
            for (var name in options) {
                var id = "#report-detail-" + name;
                var value = DesignerMVC.Util.getPropertyValue(name, options);
                $(id).text(value);
            }
        },
        getPropertyValue: function (name, object) {
            var value = object[name];
            if (name == "layout" ||
                name == "statColumnLayout") {
                return DesignerMVC.Util.getLayoutName(value);
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
        },
        checkBasicConfParam: function () {
            if (DesignerMVC.View.SqlEditor.getValue() == "") {
                $.messager.alert('失败', "未发现操作的SQL语句！", 'error');
                return false;
            }
            return $('#report-basic-conf-form').form('validate');
        },
        loadMetaColumns: function (newColumns) {
            var oldColumns = $("#report-meta-column-grid").datagrid('getRows');
            //如果列表中没有元数据列则直接设置成新的元数据列
            if (oldColumns == null || oldColumns.length == 0) {
                return $("#report-meta-column-grid").datagrid('loadData', newColumns);
            }

            //如果列表中存在旧的列则需要替换相同的列并增加新列
            oldColumns = DesignerMVC.Util.getMetaColumns(oldColumns);
            var oldRowMap = {};
            for (var i = 0; i < oldColumns.length; i++) {
                var name = oldColumns[i].name;
                oldRowMap[name] = oldColumns[i];
            }

            for (var i = 0; i < newColumns.length; i++) {
                var name = newColumns[i].name;
                if (oldRowMap[name]) {
                    oldRowMap[name].dataType = newColumns[i].dataType;
                    oldRowMap[name].width = newColumns[i].width;
                    newColumns[i] = oldRowMap[name];
                }
            }
            return $("#report-meta-column-grid").datagrid('loadData', newColumns);
        },
        getMetaColumns: function (columns) {
            for (var rowIndex = 0; rowIndex < columns.length; rowIndex++) {
                var column = columns[rowIndex];
                var subOptions = DesignerMVC.Util.getCheckboxOptions(column.type);
                for (var optIndex = 0; optIndex < subOptions.length; optIndex++) {
                    var option = subOptions[optIndex];
                    var optionId = "#" + option.name + rowIndex;
                    column[option.name] = $(optionId).prop("checked");
                }
                column["name"] = $("#name" + rowIndex).val();
                column["text"] = $("#text" + rowIndex).val();
                column["type"] = $("#type" + rowIndex).val();
                column["sortType"] = $("#sortType" + rowIndex).val();
                column["decimals"] = $("#decimals" + rowIndex).val();
            }
            return columns;
        },
        eachMetaColumns: function (columns) {
            if (columns && columns.length) {
                for (var i = 0; i < columns.length; i++) {
                    var column = columns[i];
                    column.type = DesignerMVC.Util.getColumnTypeValue(column.type);
                    column.sortType = DesignerMVC.Util.getColumnSortTypeValue(column.sortType);
                }
            }
            return columns;
        },
        getCheckboxOptions: function (type) {
            var subOptions = [];
            if (type == 4) {
                subOptions = $.grep(DesignerMVC.Model.MetaColumnOptions, function (option, i) {
                    return option.type == 1;
                });
            } else if (type == 3) {
                subOptions = $.grep(DesignerMVC.Model.MetaColumnOptions, function (option, i) {
                    return option.type == 1 || option.type == 2;
                });
            } else {
                subOptions = $.grep(DesignerMVC.Model.MetaColumnOptions, function (option, i) {
                    return option.type == 3;
                });
            }
            return subOptions;
        },
        getEmptyExprColumns: function (columns) {
            var emptyColumns = [];
            for (var i = 0; i < columns.length; i++) {
                var column = columns[i];
                if (column.type == 4 && $.trim(column.expression) == "") {
                    emptyColumns.push(column.name);
                }
            }
            return emptyColumns;
        },
        getColumnTypeMap: function (rows) {
            var typeMap = {
                "layout": 0,
                "dim": 0,
                "stat": 0,
                "computed": 0
            };
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].type == 1) {
                    typeMap.layout += 1;
                } else if (rows[i].type == 2) {
                    typeMap.dim += 1;
                } else if (rows[i].type == 3) {
                    typeMap.stat += 1;
                } else if (rows[i].type == 4) {
                    typeMap.computed += 1;
                }
            }
            return typeMap;
        },
        loadQueryParams: function (params) {
            EasyUIUtils.clearDatagrid('#report-query-param-grid');
            var jsonText = JSON.stringify(params);
            if (params instanceof Array) {
                $("#report-query-param-grid").datagrid('loadData', params);
            } else {
                jsonText = "";
            }
            $("#report-query-param-json").val(jsonText);
        },
        getQueryParams: function () {
            var rows = $("#report-query-param-grid").datagrid('getRows');
            return rows ? JSON.stringify(rows) : "";
        }
    },
    Data: {
        loadDataSourceList: function () {
            $.getJSON(DesignerMVC.URLs.DataSource.listAll.url, function (result) {
                if (result.code) {
                    console.info(result.msg);
                }
                DesignerMVC.Model.DataSourceList = result.data;
                EasyUIUtils.fillCombox("#report-dsId", "add", result.data, "");
            });
        }
    }
};
