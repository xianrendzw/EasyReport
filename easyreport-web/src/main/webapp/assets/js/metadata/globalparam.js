$(function () {
    MetaDataGlobalParam.init();
});

var MetaDataGlobalParam = {
    init: function () {
        GlobalParamMVC.View.initControl();
        GlobalParamMVC.View.bindEvent();
        GlobalParamMVC.View.bindValidate();
        GlobalParamMVC.View.initData();
    }
};

var GlobalParamCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/metadata/GlobalParam/',
    baseConfUrl: EasyReport.ctxPath + '/rest/metadata/conf/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/',
};

var GlobalParamMVC = {
    URLs: {
        add: {
            url: GlobalParamCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: GlobalParamCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: GlobalParamCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: GlobalParamCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getConfItems: {
            url: GlobalParamCommon.baseConfUrl + 'getConfItems',
            method: 'GET'
        }
    },
    View: {
        initControl: function () {
            $('#globalparam-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: GlobalParamMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        GlobalParamMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        GlobalParamMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        GlobalParamMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#globalparam-datagrid');
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
                                + 'onclick="GlobalParamMVC.Controller.doOption(\'${index}\',\'${name}\')">'
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
                    return GlobalParamMVC.Controller.edit();
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', GlobalParamMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            GlobalParamMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#globalparam-datagrid').datagrid('selectRow', index);
            if (name == "edit") {
                return GlobalParamMVC.Controller.edit();
            }
            if (name == "remove") {
                return GlobalParamMVC.Controller.remove();
            }
            if (name == "connect") {
                return GlobalParamMVC.Controller.testConnectionById(index);
            }
        },
        add: function () {
            var options = GlobalParamMVC.Util.getOptions();
            options.title = jQuery.i18n.prop('ds.add.ds');
            EasyUIUtils.openAddDlg(options);
            GlobalParamMVC.Util.fillCombox("#dbType", "add", GlobalParamMVC.Model.dbTypes, "driverClass", "");
            GlobalParamMVC.Util.fillCombox("#dbPoolType", "add", GlobalParamMVC.Model.dbPoolTypes, "poolClass", "");
        },
        edit: function () {
            var row = $('#globalparam-datagrid').datagrid('getSelected');
            if (row) {
                var options = GlobalParamMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = jQuery.i18n.prop('ds.edit.ds',options.data.name);
                EasyUIUtils.openEditDlg(options);
                GlobalParamMVC.Util.fillCombox("#dbType", "edit", GlobalParamMVC.Model.dbTypes, "driverClass", row.driverClass);
                GlobalParamMVC.Util.fillCombox("#dbPoolType", "edit", GlobalParamMVC.Model.dbPoolTypes, "poolClass", row.poolClass);
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
            var url = GlobalParamMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#globalparam-datagrid', url)
        },
        remove: function () {
            var row = $('#globalparam-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: GlobalParamMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#globalparam-datagrid',
                    gridUrl: GlobalParamMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        save: function () {
            var rows = $('#ds-options-pg').propertygrid('getRows');
            $('#options').val(JSON.stringify(EasyUIUtils.toPropertygridMap(rows)));
            EasyReport.utils.debug($('#options').val());

            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: GlobalParamMVC.URLs.list.url,
                dlgId: "#ds-dlg",
                formId: "#ds-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? GlobalParamMVC.URLs.edit.url : GlobalParamMVC.URLs.add.url);
            options.gridId = '#globalparam-datagrid';
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
                var key = GlobalParamMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(GlobalParamMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                GlobalParamMVC.Util.toMap(GlobalParamMVC.Model.dbTypes, result.data);
            });
            $.getJSON(GlobalParamMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                GlobalParamMVC.Util.toMap(GlobalParamMVC.Model.dbPoolTypes, result.data);
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
