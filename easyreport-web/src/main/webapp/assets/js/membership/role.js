$(function () {
    MembershipRole.init();
});

var MembershipRole = {
    init: function () {
        RoleMVC.View.initControl();
        RoleMVC.View.bindEvent();
        RoleMVC.View.bindValidate();
    }
};

var RoleCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/membership/role/',
    baseIconUrl: EasyReport.ctxPath + '/assets/custom/easyui/themes/icons/'
};

var RoleMVC = {
    URLs: {
        add: {
            url: RoleCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: RoleCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: RoleCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: RoleCommon.baseUrl + 'remove',
            method: 'POST'
        },
        authorize: {
            url: RoleCommon.baseUrl + 'authorize',
            method: 'POST'
        },
        listPermissionTree: {
            url: RoleCommon.baseUrl + 'listPermissionTree',
            method: 'GET'
        },
        isSuperAdmin: {
            url: RoleCommon.baseUrl + 'isSuperAdmin',
            method: 'GET'
        }
    },
    View: {
        initControl: function () {
            $('#role-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: RoleMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        RoleMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        RoleMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-perm',
                    handler: function () {
                        RoleMVC.Controller.authorize();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        RoleMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#role-datagrid');
                    }
                }],
                loadFilter: function (src) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('role.failed'), src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: jQuery.i18n.prop('role.id'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: jQuery.i18n.prop('role.name'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'code',
                    title: jQuery.i18n.prop('role.code'),
                    width: 80,
                    sortable: true,
                }, {
                    field: 'isSystem',
                    title: jQuery.i18n.prop('role.issystem'),
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value == 1 ? jQuery.i18n.prop('role.issystem.yes') : jQuery.i18n.prop('role.issystem.no');
                    }
                }, {
                    field: 'status',
                    title: jQuery.i18n.prop('role.status'),
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value == 1 ? jQuery.i18n.prop('role.status.enable') : jQuery.i18n.prop('role.status.disable');
                    }
                }, {
                    field: 'comment',
                    title: jQuery.i18n.prop('role.comment'),
                    width: 100,
                    sortable: true
                }, {
                    field: 'createUser',
                    title: jQuery.i18n.prop('role.createuser'),
                    width: 80,
                    sortable: true
                }, {
                    field: 'sequence',
                    title: jQuery.i18n.prop('role.sequence'),
                    width: 50,
                    sortable: true
                }, {
                    field: 'gmtCreated',
                    title: jQuery.i18n.prop('role.gmtcreate'),
                    width: 60,
                    sortable: true
                }, {
                    field: 'options',
                    title: jQuery.i18n.prop('role.operation'),
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": jQuery.i18n.prop('role.edit')
                        }, {
                            "name": "perm",
                            "title": jQuery.i18n.prop('role.grant')
                        }, {
                            "name": "remove",
                            "title": jQuery.i18n.prop('role.remove')
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="RoleMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: RoleCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return RoleMVC.Controller.edit();
                }
            });

            // dialogs
            $('#role-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 300,
                iconCls: 'icon-add',
                buttons: [{
                    text: jQuery.i18n.prop('role.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#role-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('role.save'),
                    iconCls: 'icon-save',
                    handler: RoleMVC.Controller.save
                }]
            });

            $('#perm-tree-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 460,
                iconCls: 'icon-perm',
                buttons: [{
                    text: jQuery.i18n.prop('role.close'),
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#perm-tree-dlg").dialog('close');
                    }
                }, {
                    text: jQuery.i18n.prop('role.save'),
                    iconCls: 'icon-save',
                    handler: RoleMVC.Controller.save
                }]
            });

            $('#perm-tree').tree({
                checkbox: true,
                method: 'get',
                cascadeCheck: true,
                onClick: function (node) {
                    $('#perm-tree').tree('expandAll', node.target);
                },
                loadFilter: function (src, parent) {
                    if (src.success) {
                        return src.data;
                    }
                    return $.messager.alert(jQuery.i18n.prop('role.failed'), src.msg, 'error');
                },
                formatter:function(node){
            		return jQuery.i18n.prop(node.text);
            	}
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', RoleMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#role-datagrid').datagrid('selectRow', index);
            if (name == "edit") {
                return RoleMVC.Controller.edit();
            }
            if (name == "remove") {
                return RoleMVC.Controller.remove();
            }
            if (name == "perm") {
                return RoleMVC.Controller.authorize();
            }
        },
        add: function () {
            var options = RoleMVC.Util.getOptions();
            options.title = jQuery.i18n.prop('role.add.role');
            EasyUIUtils.openAddDlg(options);
            $('#sequence').textbox('setValue', "10");
            $('#status').combobox('setValue', "1");
            $('#isSystem').combobox('setValue', "0");
        },
        edit: function () {
            var row = $('#role-datagrid').datagrid('getSelected');
            if (row) {
                var options = RoleMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = jQuery.i18n.prop('role.modify',options.data.name);
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert(jQuery.i18n.prop('role.warn'), jQuery.i18n.prop('role.warn.please.select.record'), 'info');
            }
        },
        authorize: function () {
            var row = $('#role-datagrid').datagrid('getSelected');
            if (row) {
                $('#perm-tree-dlg').dialog('open').dialog('center');
                $('#perm-tree-dlg-layout').css({
                    top: "2px",
                    left: "2px"
                });
                $("#modal-action").val("authorize");
                $('#perm-role-id').val(row.id);
                $('#perm-tree').tree('options').url = RoleMVC.URLs.listPermissionTree.url + '?roleId=' + row.id;
                $("#perm-tree").tree('reload');
            } else {
                $.messager.alert(jQuery.i18n.prop('role.warn'), jQuery.i18n.prop('role.warn.please.select.record'), 'info');
            }
        },
        find: function () {
            var fieldName = 'name';
            var keyword = $("#keyword").val();
            var url = RoleMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#role-datagrid', url)
        },
        remove: function () {
            var row = $('#role-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RoleMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#role-datagrid',
                    gridUrl: RoleMVC.URLs.list.url,
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
                gridUrl: RoleMVC.URLs.list.url,
                dlgId: "#role-dlg",
                formId: "#role-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "authorize") {
                $('#permissions').val(RoleMVC.Util.getPermissionIds());
                options.dlgId = '#perm-tree-dlg';
                options.formId = '#perm-tree-form';
                options.url = RoleMVC.URLs.authorize.url;
            } else {
                options.url = (action === "edit" ? RoleMVC.URLs.edit.url : RoleMVC.URLs.add.url);
                options.gridId = '#role-datagrid';
            }
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#role-dlg',
                formId: '#role-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        getPermissionIds: function () {
            var nodes = $('#perm-tree').tree('getChecked');
            var ids = $.map(nodes, function (node) {
                if (node.id > 0)
                    return node.id;
            });
            return ids.join(',');
        }
    }
};