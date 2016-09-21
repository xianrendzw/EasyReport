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
    loadData: function (cateId) {
    },
    openDesigner: function () {
        ReportMVC.Controller.add();
    }
}

var ReportCommon = {
    baseUrl: EasyReport.ctxPath + '/metadata/report/',
    baseHistoryUrl: EasyReport.ctxPath + '/metadata/history/',
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
        remove: {
            url: ReportCommon.baseUrl + 'remove',
            method: 'POST'
        },
        historyList: {
            url: ReportCommon.baseHistoryUrl + 'list',
            method: 'GET'
        },
    },
    Model: {
        sqlColumnOptions: [{
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
        sqlColumnTypeOptions: [{
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
        sqlColumnSortTypeOptions: [{
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
    SqlEditor: null,
    HistorySqlEditor: null,
    View: {
        initControl: function () {
            $('#report-datagrid').datagrid({
                url: ReportMVC.URLs.list.url,
                method: 'get',
                pageSize: 30,
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
                    text: '查看SQL历史记录',
                    iconCls: 'icon-history',
                    handler: ReportMVC.Controller.viewSqlHistory
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
                            "name": "history",
                            "title": "查看SQL历史记录"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" ' +
                                'onclick="ReportMVC.Controller.doAction(\'${index}\',\'${name}\')">' +
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
                    return ReportMVC.Controller.edit();
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
                    return EasyUIUtils.getEmptyDatagrid();
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
                onClickRow: function (index, row) {
                    DseApi.historySqlEditor.setValue(row.sqlText || "");
                }
            });

            $('#report-designer-dlg').dialog({
                closed: true,
                modal: false,
                width: window.screen.width - 350,
                height: window.screen.height - 350,
                maximizable: true,
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
                    DseApi.resizeSqlEditor();
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
        },
        initSqlEditorDivHeight: function () {
            var paramDivHeight = (window.screen.height - 720);
            paramDivHeight = paramDivHeight <= 180 ? 180 : paramDivHeight;
            var sqlhistoryDivHeight = window.screen.height - 420 - 205;
            sqlhistoryDivHeight = sqlhistoryDivHeight <= 180 ? 180 : sqlhistoryDivHeight;

            $('#param-grid-div').css({
                "height": paramDivHeight
            });
            $('#report-history-sql-grid-div').css({
                "height": sqlhistoryDivHeight
            });
        },
        initSqlEditor: function () {
            DseApi.sqlEditor = CodeMirror.fromTextArea(document.getElementById("api-sqlText"), {
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
            DseApi.sqlEditor.on("change", function (cm, obj) {
                if (obj.origin == "setValue") {
                    $('#sqlTextIsChanged').val(0);
                } else {
                    $('#sqlTextIsChanged').val(1);
                }
            });
        },
        initHistorySqlEditor: function () {
            DseApi.historySqlEditor = CodeMirror.fromTextArea(document.getElementById("api-history-sqltext"), {
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
            $('#btn-search').bind('click', ReportMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doAction: function (index, name) {
            $('#report-datagrid').datagrid('selectRow', index);
            if (name == "edit") {
                return ReportMVC.Controller.edit();
            }
            if (name == "copy") {
                return ReportMVC.Controller.copy();
            }
            if (name == "remove") {
                return ReportMVC.Controller.remove();
            }
            if (name == "history") {
                return ReportMVC.Controller.viewSqlHistory();
            }
        },
        initCategoryComboxtree: function (value) {
            var defaultValue = (DseApi.categories && DseApi.categories.length > 0) ? DseApi.categories[0].id : 0;
            $('#api-categoryId').combotree('setValue', value || defaultValue);
        },
        getCategoryId: function () {
            var node = $('#apicate-tree').tree('getSelected');
            return node ? node.id : null;
        },
        add: function () {
            $('#report-designer-dlg').dialog({
                iconCls: 'icon-add',
                title: '添加指标'
            });

            $('#report-designer-dlg').dialog('open').dialog('center');
            $("#modal-action").val("add");
            $("#api-form").form('reset');

            DseApi.clearSqlEditor();
            ReportMVC.Controller.initCategoryComboxtree(ReportMVC.Controller.getCategoryId());
            EasyUIUtils.clearDatagrid('#param-grid');
        },
        edit: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                $('#report-designer-dlg').dialog({
                    iconCls: 'icon-edit',
                    title: '编辑指标'
                });
                $('#report-designer-dlg').dialog('open').dialog('center');
                $("#modal-action").val("edit");
                $("#api-form").form('reset');
                DseApi.clearSqlEditor();
                ReportMVC.Controller.initCategoryComboxtree(row.categoryId);
                $("#api-form").form('load', row);
                ReportMVC.Controller.loadParams(row.params)
                DseApi.sqlEditor.setValue(row.sqlText || "");
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        viewSqlHistory: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                $('#report-history-sql-dlg').dialog('open').dialog('center');
                DseApi.historySqlEditor.setValue('');
                DseApi.historySqlEditor.refresh();
                var url = apiSqlPageUrl + 'list?apiId=' + row.id;
                EasyUIUtils.loadToDatagrid('#report-history-sql-grid', url)
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                var gridUrl = apiPageUrl + 'list';
                var actUrl = apiPageUrl + 'remove';
                EasyUIUtils.removeWithActUrl('#report-datagrid', gridUrl, actUrl);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        copy: function () {
            var row = $('#report-datagrid').datagrid('getSelected');
            if (row) {
                $('#report-designer-dlg').dialog({
                    iconCls: 'icon-copy',
                    title: '复制指标'
                });
                $('#report-designer-dlg').dialog('open').dialog('center');
                $("#modal-action").val("add");
                $("#api-form").form('reset');
                DseApi.clearSqlEditor();
                ReportMVC.Controller.initCategoryComboxtree(row.categoryId);
                $("#api-form").form('load', row);
                ReportMVC.Controller.loadParams(row.params)
                DseApi.sqlEditor.setValue(row.sqlText || "");
                $('#api-name').textbox('setValue', '');
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var keyword = $("#api-keyword").val();
            //var categoryId = ReportMVC.Controller.getCategoryId();
            //var url = apiPageUrl + 'find?categoryId='+ categoryId +'&keyword=' + keyword;
            var url = apiPageUrl + 'find?keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#report-datagrid', url)
        },
        save: function () {
            var action = $('#modal-action').val();
            var formId = "#api-form";
            var dlgId = "#report-designer-dlg";
            var gridUrl = apiPageUrl + 'list';
            $('#api-params').val(JSON.stringify(ReportMVC.Controller.getParams()));
            EasyUIUtils.saveWithActUrl(dlgId, formId, '#modal-action', '#report-datagrid', gridUrl, apiPageUrl);
        },
        loadParams: function (params) {
            EasyUIUtils.clearDatagrid('#param-grid');
            if ((params || '').length > 0) {
                $("#param-grid").datagrid('loadData', eval(params));
            }
        },
        getParams: function () {
            var rows = $("#param-grid").datagrid('getRows');
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
            DseApi.sqlEditor.setOption("fullScreen", !DseApi.sqlEditor.getOption("fullScreen"));
        },

        //////
        addRoot: function () {
            var name = "根配置项";
            var id = "0";
            ReportMVC.Util.initAdd(id, name);
        },
        add: function () {
            var node = $('#dict-tree').tree('getSelected');
            if (node) {
                var name = node.attributes.name;
                var id = node.id;
                ReportMVC.Util.initAdd(id, name);
            } else {
                $.messager.alert('警告', '请选中一个父配置项!', 'info');
            }
        },
        editNode: function () {
            var node = $('#dict-tree').tree('getSelected');
            if (node) {
                ReportMVC.Util.edit(node.attributes);
            }
        },
        edit: function () {
            var row = $('#dict-datagrid').datagrid('getSelected');
            ReportMVC.Util.edit(row);
        },
        copy: function () {
            $("#confParentNameDiv").hide();
            var row = $('#dict-datagrid').datagrid('getSelected');
            if (row) {
                ReportMVC.Util.edit(row);
                $('#modal-action').val("copy");
            }
        },
        remove: function () {
            var row = $('#dict-datagrid').datagrid('getSelected');
            var node = $('#dict-tree').tree('getSelected');
            node = node ? node.attributes : null;
            row = row || node;

            var options = {
                rows: [row],
                url: ReportMVC.URLs.remove.url,
                data: {
                    id: row.id
                },
                callback: function (rows) {
                    var row = rows[0];
                    ReportMVC.Controller.reloadTree();
                    EasyUIUtils.loadToDatagrid('#dict-datagrid', ReportMVC.URLs.list.url + '?id=' + row.parentId);
                }
            };
            EasyUIUtils.remove(options);
        },
        save: function () {
            var action = $('#modal-action').val();
            var gridUrl = ReportMVC.URLs.list.url + '?id=' + $("#confPid").val();
            var actUrl = action === "edit" ? ReportMVC.URLs.edit.url : ReportMVC.URLs.add.url;
            if (action === "copy") {
                actUrl = ReportMVC.URLs.copy.url;
            }

            var options = {
                dlgId: "#dict-dlg",
                formId: "#dict-form",
                url: actUrl,
                callback: function () {
                    ReportMVC.Controller.reloadTree();
                    EasyUIUtils.loadToDatagrid('#dict-datagrid', gridUrl);
                }
            };
            EasyUIUtils.save(options);
        },
        refreshNode: function (pid) {
            if (pid == "0") {
                this.reloadTree();
            } else {
                var node = $('#dict-tree').tree('find', pid);
                if (node) {
                    $('#dict-tree').tree('select', node.target);
                    $('#dict-tree').tree('reload', node.target);
                }
            }
        },
        reloadTree: function () {
            $('#dict-tree').tree('reload');
        },
        openSearchDlg: function () {
            $('#search-node-dlg').dialog('open').dialog('center');
            EasyUIUtils.clearDatagrid('#search-node-result');
        },
        find: function () {
            var fieldName = $('#field-name').combobox('getValue');
            var keyword = $('#keyword').val();
            var url = ReportMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            return EasyUIUtils.loadToDatagrid('#search-node-result', url);
        }
    },
    Util: {
        initAdd: function (id, name) {
            var options = ReportMVC.Util.getOptions();
            options.title = '新增[' + name + ']的子配置项';
            EasyUIUtils.openAddDlg(options);

            $("#confPid").val(id);
            $("#confParentNameDiv").show();
            $("#confParentName").html(name);
            $("#sequence").textbox('setValue', 10);
        },
        edit: function (data) {
            $("#confParentNameDiv").hide();
            var options = ReportMVC.Util.getOptions();
            options.iconCls = 'icon-edit1';
            options.data = data;
            options.title = '修改[' + options.data.name + ']配置项';
            EasyUIUtils.openEditDlg(options);
        },
        getOptions: function () {
            return {
                dlgId: '#dict-dlg',
                formId: '#dict-form',
                actId: '#modal-action',
                rowId: '#confId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        clearSqlEditor: function () {
            DseApi.sqlEditor.setValue('');
            DseApi.sqlEditor.refresh();
        },
        resizeSqlEditor: function () {
            var width = $('#report-designer-dlg').panel('options').width - 180;
            $('#api-sqltest-td>.CodeMirror').css({"width": width});
        },
    }
};
