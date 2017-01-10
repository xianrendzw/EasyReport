$(function () {
    MetaDataDs.init();
});

var MetaDataDs = {
    init: function () {
        DsMVC.View.initControl();
        DsMVC.View.bindEvent();
        DsMVC.View.bindValidate();
        DsMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/metadata/ds/',
    baseConfUrl: EasyReport.ctxPath + '/rest/metadata/conf/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var DsMVC = {
    URLs: {
        add: {
            url: DsCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: DsCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: DsCommon.baseUrl + 'remove',
            method: 'POST'
        },
        testConnection: {
            url: DsCommon.baseUrl + 'testConnection',
            method: 'POST'
        },
        testConnectionById: {
            url: DsCommon.baseUrl + 'testConnectionById',
            method: 'POST'
        },
        getConfItems: {
            url: DsCommon.baseConfUrl + 'getConfItems',
            method: 'GET'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {
            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: DsMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        DsMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        DsMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        DsMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#ds-datagrid');
                    }
                }],
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('ds.failed'), src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('ds.id'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: jQuery.i18n.prop('ds.name'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'jdbcUrl',
                    title: jQuery.i18n.prop('ds.jdbcurl'),
                    width: 200,
                    sortable: true
                }, {
                    field: 'driverClass',
                    title: jQuery.i18n.prop('ds.driver'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'queryerClass',
                    title: jQuery.i18n.prop('ds.queryer'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'poolClass',
                    title: jQuery.i18n.prop('ds.dbpool'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: jQuery.i18n.prop('ds.gmtcreate'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'options',
                    title: jQuery.i18n.prop('ds.operation'),
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": jQuery.i18n.prop('ds.edit')
                        }, {
                            "name": "connect",
                            "title": jQuery.i18n.prop('ds.connect')
                        }, {
                            "name": "remove",
                            "title": jQuery.i18n.prop('ds.remove')
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="DsMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: DsCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return DsMVC.Controller.edit();
                }
            });

            // dialogs
            $('#ds-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 450,
                iconCls: 'icon-add',
                buttons: [{
                    text: jQuery.i18n.prop('ds.connection.test'),
                    iconCls: 'icon-connect',
                    handler: DsMVC.Controller.testConnection
                }, {
                    text: jQuery.i18n.prop('ds.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#ds-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('ds.save'),
                    iconCls: 'icon-save',
                    handler: DsMVC.Controller.save
                }]
            });

            $('#dbType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = DsMVC.Model.dbTypes[newValue].value;
                    $('#jdbcUrl').textbox('setValue', item.jdbcUrl);
                    $('#driverClass').val(item.driverClass);
                    $('#queryerClass').val(item.queryerClass);
                }
            });

            $('#dbPoolType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = DsMVC.Model.dbPoolTypes[newValue].value;
                    $('#poolClass').val(item.poolClass);
                    var data = EasyUIUtils.toPropertygridRows(item.options);
                    $('#ds-options-pg').propertygrid('loadData', data);
                }
            });

            $('#ds-options-pg').propertygrid({
                scrollbarSize: 0,
                height: 200,
                columns: [[
                    {field: 'name', title: jQuery.i18n.prop('ds.conf.name'), width: 200, sortable: true},
                    {field: 'value', title: jQuery.i18n.prop('ds.conf.value'), width: 100, resizable: false}
                ]]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', DsMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            DsMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ds-datagrid').datagrid('selectRow', index);
            if (name == "edit") {
                return DsMVC.Controller.edit();
            }
            if (name == "remove") {
                return DsMVC.Controller.remove();
            }
            if (name == "connect") {
                return DsMVC.Controller.testConnectionById(index);
            }
        },
        add: function () {
            var options = DsMVC.Util.getOptions();
            options.title = jQuery.i18n.prop('ds.add.ds');
            EasyUIUtils.openAddDlg(options);
            DsMVC.Util.fillCombox("#dbType", "add", DsMVC.Model.dbTypes, "driverClass", "");
            DsMVC.Util.fillCombox("#dbPoolType", "add", DsMVC.Model.dbPoolTypes, "poolClass", "");
        },
        edit: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = DsMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = jQuery.i18n.prop('ds.edit.ds',options.data.name);
                EasyUIUtils.openEditDlg(options);
                DsMVC.Util.fillCombox("#dbType", "edit", DsMVC.Model.dbTypes, "driverClass", row.driverClass);
                DsMVC.Util.fillCombox("#dbPoolType", "edit", DsMVC.Model.dbPoolTypes, "poolClass", row.poolClass);
                $('#jdbcUrl').textbox('setValue', row.jdbcUrl);
                $('#options').val(row.options || "{}");
                EasyReport.utils.debug(row.options);
                $('#ds-options-pg').propertygrid('loadData', EasyUIUtils.toPropertygridRows($.toJSON(row.options)));
            } else {
                $.messager.alert(jQuery.i18n.prop('ds.warn'), jQuery.i18n.prop('ds.please.select.record'), 'info');
            }
        },
        find: function () {
            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            var url = DsMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#ds-datagrid', url)
        },
        remove: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: DsMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ds-datagrid',
                    gridUrl: DsMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        testConnectionById: function (index) {
            $('#ds-datagrid').datagrid('selectRow', index);
            var row = $('#ds-datagrid').datagrid('getSelected');
            $.post(DsMVC.URLs.testConnectionById.url, {
                id: row.id
            }, function callback(data) {
                if (data.success) {
                    $.messager.alert(jQuery.i18n.prop('ds.success'), jQuery.i18n.prop('ds.connection.success'), 'success');
                } else {
                    $.messager.alert(jQuery.i18n.prop('ds.failed'), jQuery.i18n.prop('ds.connection.failed'), 'error');
                }
            }, 'json');
        },
        testConnection: function () {
            var key = $("#dbType").combobox('getValue');
            var item = DsMVC.Model.dbTypes[key].value;
            var data = {
                driverClass: item.driverClass,
                url: $("#jdbcUrl").val(),
                pass: $("#password").val(),
                user: $("#user").val()
            };
            EasyReport.utils.debug(data);

            $.post(DsMVC.URLs.testConnection.url, data, function callback(data) {
                if (data.success) {
                    $.messager.alert(jQuery.i18n.prop('ds.success'), jQuery.i18n.prop('ds.connection.success'), 'success');
                } else {
                    $.messager.alert(jQuery.i18n.prop('ds.failed'), jQuery.i18n.prop('ds.connection.failed'), 'error');
                }
            }, 'json');
        },
        save: function () {
            var rows = $('#ds-options-pg').propertygrid('getRows');
            $('#options').val(JSON.stringify(EasyUIUtils.toPropertygridMap(rows)));
            EasyReport.utils.debug($('#options').val());

            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: DsMVC.URLs.list.url,
                dlgId: "#ds-dlg",
                formId: "#ds-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? DsMVC.URLs.edit.url : DsMVC.URLs.add.url);
            options.gridId = '#ds-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#ds-dlg',
                formId: '#ds-form',
                actId: '#modal-action',
                rowId: '#dsId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        fillCombox: function (id, act, map, fieldName, value) {
            $(id).combobox('clear');
            var data = [];
            var i = 0;
            for (var key in map) {
                var item = map[key];
                data.push({
                    "value": item.key,
                    "name": item.name,
                    "selected": i == 0
                });
                i++;
            }
            $(id).combobox('loadData', data);
            if (act == "edit") {
                var key = DsMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(DsMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                DsMVC.Util.toMap(DsMVC.Model.dbTypes, result.data);
            });
            $.getJSON(DsMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                DsMVC.Util.toMap(DsMVC.Model.dbPoolTypes, result.data);
            });
        },
        toMap: function (srcMap, data) {
            if (!data || data.length == 0) return {};
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                item.value = $.toJSON(item.value);
                srcMap[item.key] = item;
            }
            return srcMap;
        },
        findKey: function (map, fieldName, value) {
            for (var key in map) {
                if (value === map[key].value[fieldName]) {
                    return key;
                }
            }
            return "";
        }
    }
};
