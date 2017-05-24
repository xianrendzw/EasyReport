$(function () {
    MembershipUser.init();
});

var MembershipUser = {
    init: function () {
        UserMVC.View.initControl();
        UserMVC.View.bindEvent();
        UserMVC.View.bindValidate();
        UserMVC.View.initData();
    }
};

var UserCommon = {
    baseUrl: EasyReport.ctxPath + '/rest/member/user/',
    baseRoleUrl: EasyReport.ctxPath + '/rest/member/role/',
    baseIconUrl: EasyReport.ctxPath + '/custom/easyui/themes/icons/'
};

var UserMVC = {
    URLs: {
        add: {
            url: UserCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: UserCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: UserCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: UserCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getRoleList: {
            url: UserCommon.baseRoleUrl + 'getRoleList',
            method: 'GET'
        },
        editPassword: {
            url: UserCommon.baseUrl + 'editPassword',
            method: 'POST'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {
            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: UserMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        UserMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        UserMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-pwd',
                    handler: function () {
                        UserMVC.Controller.resetPwd();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        UserMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#user-datagrid');
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
                    title: '用户ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'account',
                    title: '账号',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '姓名',
                    width: 80,
                    sortable: true,
                }, {
                    field: 'telephone',
                    title: '电话',
                    width: 50,
                    sortable: true
                }, {
                    field: 'email',
                    title: '邮箱',
                    width: 80,
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
                            "name": "pwd",
                            "title": "修改密码"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="UserMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: UserCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return UserMVC.Controller.edit();
                }
            });

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: UserMVC.Controller.save
                }]
            });

            $('#reset-pwd-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 250,
                iconCls: 'icon-pwd',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#reset-pwd-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: UserMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', UserMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            UserMVC.Util.loadRoleList();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name == "edit") {
                return UserMVC.Controller.edit();
            }
            if (name == "remove") {
                return UserMVC.Controller.remove();
            }
            if (name == "pwd") {
                return UserMVC.Controller.resetPwd();
            }
        },
        add: function () {
            var options = UserMVC.Util.getOptions();
            options.title = '新增用户';
            EasyUIUtils.openAddDlg(options);
            UserMVC.Util.fillRoleCombox("add", []);
            $('#status').combobox('setValue', "1");
            $('#account').textbox('readonly', false);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = UserMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.name + ']用户';
                EasyUIUtils.openEditDlg(options);
                $('#password').textbox('setValue', '');
                $('#account').textbox('readonly', true);
                var roleIds = row.roles || "";
                UserMVC.Util.fillRoleCombox("edit", roleIds.split(','));
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        resetPwd: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                $('#reset-pwd-dlg').dialog('open').dialog('center');
                $("#modal-action").val("resetPwd");
                $("#reset-pwd-form").form('clear');
                $("#reset-userId").val(row.userId);
                $("#reset-account").text(row.account);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            var url = UserMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            EasyUIUtils.loadToDatagrid('#user-datagrid', url)
        },
        remove: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: UserMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#user-datagrid',
                    gridUrl: UserMVC.URLs.list.url,
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
                gridUrl: UserMVC.URLs.list.url,
                dlgId: "#user-dlg",
                formId: "#user-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "resetPwd") {
                options.dlgId = '#reset-pwd-dlg';
                options.formId = '#reset-pwd-form';
                options.url = UserMVC.URLs.editPassword.url;
            } else {
                $('#roles').val($('#combox-roles').combobox('getValues'));
                options.url = (action === "edit" ? UserMVC.URLs.edit.url : UserMVC.URLs.add.url);
                options.gridId = '#user-datagrid';
            }
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#user-dlg',
                formId: '#user-form',
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
        fillRoleCombox: function (act, values) {
            var id = '#combox-roles';
            $(id).combobox('clear');
            var data = [];
            var items = UserMVC.Model.roles;
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
        loadRoleList: function () {
            $.getJSON(UserMVC.URLs.getRoleList.url, function (src) {
                UserMVC.Model.roles = src.data;
            });
        }
    }
};