var configSettingsPageUrl = XFrame.getContextPath() + '/sys/settings/';
$(function () {
    $('#settings-datagrid').datagrid({
        method: 'get',
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        url: configSettingsPageUrl + 'list',
        toolbar: [{
                iconCls: 'icon-add',
                handler: function () {
                    ConfigSettings.add();
                }
            }, '-', {
                iconCls: 'icon-edit1',
                handler: function () {
                    ConfigSettings.edit();
                }
            }, '-', {
                iconCls: 'icon-remove1',
                handler: function () {
                    ConfigSettings.remove();
                }
            }, '-', {
                iconCls: 'icon-reload',
                handler: function () {
                    EasyUIUtils.reloadDatagrid('#settings-datagrid');
                }
            }],
        columns: [[
                {
                    field: 'id',
                    title: '标识',
                    width: 50,
                    sortable: true
                },
                {
                    field: 'name',
                    title: '名称',
                    width: 100,
                    sortable: true
                },
                {
                    field: 'key',
                    title: '配置项',
                    width: 100,
                    sortable: true,
                },
                {
                    field: 'value',
                    title: '配置值',
                    width: 100,
                    sortable: true
                },
                {
                    field: 'sequence',
                    title: '顺序',
                    width: 30,
                    sortable: true
                },
                {
                    field: 'comment',
                    title: '备注',
                    width: 100,
                    sortable: true
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 70,
                    sortable: true
                },
                {
                    field: 'options',
                    title: '操作',
                    width: 50,
                    formatter: function (value, row, index) {
                        var imgPath = XFrame.getContextPath() + '/assets/custom/easyui/themes/icons/';
                        var icons = [{
                                "name": "edit",
                                "title": "编辑"
                            }, {
                                "name": "remove",
                                "title": "删除"
                            }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="{{title}}" '
                                    + 'onclick="ConfigSettings.execOptionAction(\'{{index}}\',\'{{name}}\')">'
                                    + '<img src="{{imgSrc}}" alt="{{title}}"/"></a>';
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
                }]],
        onDblClickRow: function (rowIndex, rowData) {
            return ConfigSettings.edit(rowIndex, rowData);
        }
    });

    // dialogs
    $('#add-settings-dlg').dialog({
        closed: true,
        modal: false,
        width: 560,
        height: 320,
        iconCls: 'icon-add',
        buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#add-settings-dlg").dialog('close');
                }
            }, {
                text: '保存',
                iconCls: 'icon-save',
                handler: ConfigSettings.save
            }]
    });

    $('#edit-settings-dlg').dialog({
        closed: true,
        modal: false,
        width: 560,
        height: 320,
        iconCls: 'icon-edit',
        buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#edit-settings-dlg").dialog('close');
                }
            }, {
                text: '保存',
                iconCls: 'icon-save',
                handler: ConfigSettings.save
            }]
    });

    // buttons
    $('#btn-search').bind('click', ConfigSettings.find);

    // end
});

var ConfigSettings = {
    execOptionAction: function (index, name) {
        $('#settings-datagrid').datagrid('selectRow', index);
        if (name == "edit") {
            return ConfigSettings.edit();
        }
        if (name == "remove") {
            return ConfigSettings.remove();
        }
    },
    add: function () {
        $('#add-settings-dlg').dialog('open').dialog('center');
        $("#modal-action").val("add");
        $("#add-form").form('reset');
    },
    edit: function () {
        var row = $('#settings-datagrid').datagrid('getSelected');
        if (row) {
            $('#edit-settings-dlg').dialog('open').dialog('center');
            $("#modal-action").val("edit");
            $("#edit-form").form('reset');
            $("#edit-form").form('load', row);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    find: function () {
        var fieldName = $("#field-name").combobox('getValue');
        var keyword = $("#keyword").val();
        var url = configSettingsPageUrl + 'find?fieldName=' + fieldName + '&keyword=' + keyword;
        EasyUIUtils.loadToDatagrid('#settings-datagrid', url)
    },
    remove: function () {
        var gridUrl = configSettingsPageUrl + 'list';
        var actUrl = configSettingsPageUrl + 'remove';
        return EasyUIUtils.removeWithActUrl('#settings-datagrid', gridUrl, actUrl);
    },
    save: function () {
        var action = $('#modal-action').val();
        var formId = action == "edit" ? "#edit-form" : "#add-form";
        var dlgId = action == "edit" ? "#edit-settings-dlg" : "#add-settings-dlg";
        var gridUrl = configSettingsPageUrl + 'list';
        return EasyUIUtils.saveWithActUrl(dlgId, formId, '#modal-action', '#settings-datagrid', gridUrl,
                configSettingsPageUrl);
    }
};