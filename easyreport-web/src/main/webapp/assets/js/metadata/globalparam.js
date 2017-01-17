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
    baseDsUrl:  EasyReport.ctxPath + '/rest/metadata/ds/',
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
        DataSource: {
            listAll: {
                url: GlobalParamCommon.baseDsUrl + 'listAll',
                method: 'GET'
            }
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
                    return $.messager.alert(jQuery.i18n.prop('globalparam.failed'), src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('globalparam.id'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: jQuery.i18n.prop('globalparam.name'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'dsId',
                    title: jQuery.i18n.prop('globalparam.dsid'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'queryParams',
                    title: jQuery.i18n.prop('globalparam.json'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: jQuery.i18n.prop('globalparam.gmtcreate'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'options',
                    title: jQuery.i18n.prop('globalparam.operation'),
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": jQuery.i18n.prop('globalparam.edit')
                        }, {
                            "name": "remove",
                            "title": jQuery.i18n.prop('globalparam.remove')
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
                                imgSrc: GlobalParamCommon.baseIconUrl + icons[i].name + ".png"
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
            
            $('#report-query-param-dlg').dialog({
                closed: true,
                modal: true,
                width: window.screen.width - 350,
                height: window.screen.height - 350,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: jQuery.i18n.prop('globalparam.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#report-query-param-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('globalparam.save'),
                    iconCls: 'icon-save',
                    handler: function () {
                    	GlobalParamMVC.Controller.save();
                        $("#report-query-param-dlg").dialog('close');
                    }
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', GlobalParamMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
        	GlobalParamMVC.Util.loadDataSourceList();
//            GlobalParamMVC.Util.loadConfigItems();
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
        },
        add: function () {
            var options = GlobalParamMVC.Util.getOptions();
            options.title = jQuery.i18n.prop('globalparam.add.param');
            EasyUIUtils.openAddDlg(options);
            $('#modal-action').val("add");
            
        },
        edit: function () {
            var row = $('#globalparam-datagrid').datagrid('getSelected');
            if (row) {
                var options = GlobalParamMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = $.toJSON(row['queryParams']);
                options.title = jQuery.i18n.prop('globalparam.edit.param',options.data.name);
                EasyUIUtils.openEditDlg(options);
                $("#report-query-param-required").prop('checked', options.data.required);
                $("#report-query-param-autoComplete").prop('checked', options.data.autoComplete);
                $("#report-query-param-id").val(row.id);
            } else {
                $.messager.alert(jQuery.i18n.prop('globalparam.warn'), jQuery.i18n.prop('globalparam.please.select.record'), 'info');
            }
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
        	if ($("#report-query-param-form").form('validate')) {
	        	var row = $('#report-query-param-form').serializeObject();
                row.required = $("#report-query-param-required").prop('checked');
                row.autoComplete = $("#report-query-param-autoComplete").prop('checked');
	            $('#report-query-param-json').val(JSON.stringify(row));
	
	            var action = $('#modal-action').val();
	            
	            var options = {
	                gridId: null,
	                gridUrl: GlobalParamMVC.URLs.list.url,
	                dlgId: "#report-query-param-dlg",
	                formId: "#report-query-param-form",
	                url: null,
	                callback: function () {
	                }
	            };
	
	            options.url = (action === "edit" ? GlobalParamMVC.URLs.edit.url : GlobalParamMVC.URLs.add.url);
	            options.gridId = '#globalparam-datagrid';
	            return EasyUIUtils.save(options);
        	}
        }
    },
    Util: {
    	getOptions: function () {
            return {
                dlgId: '#report-query-param-dlg',
                formId: '#report-query-param-form',
                actId: '#modal-action',
                rowId: '#globalparamId',
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
        loadDataSourceList: function () {
            $.getJSON(GlobalParamMVC.URLs.DataSource.listAll.url, function (result) {
                if (!result.success) {
                    console.info(result.msg);
                }
//                GlobalParamMVC.Model.DataSourceList = result.data;
                EasyUIUtils.fillCombox("#report-dsId", "add", result.data, "");
            });
        }
    }
};
