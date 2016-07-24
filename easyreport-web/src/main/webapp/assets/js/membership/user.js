var membershipUserPageUrl = XFrame.getContextPath() + '/membership/user/';

$(function () {
    $('#user-datagrid').datagrid({
        method: 'get',
        fit: true,
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        rownumbers: true,
        pageSize: 50,
        url: membershipUserPageUrl + 'list',
        toolbar: [{
                iconCls: 'icon-add',
                handler: function () {
                    MembershipUser.add();
                }
            }, '-', {
                iconCls: 'icon-edit1',
                handler: function () {
                    MembershipUser.edit();
                }
            }, '-', {
                iconCls: 'icon-pwd',
                handler: function () {
                    MembershipUser.resetPwd();
                }
            }, '-', {
                iconCls: 'icon-remove1',
                handler: function () {
                    MembershipUser.remove();
                }
            }, '-', {
                iconCls: 'icon-reload',
                handler: function () {
                    EasyUIUtils.reloadDatagrid('#user-datagrid');
                }
            }],
        loadFilter: function (src) {
            if (src.success) {
                return src.data;
            }
            return $.messager.alert('失败', src.msg, 'error');
        },
        columns: [[{
                    field: 'userId',
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
                    field: 'gmtCreate',
                    title: '创建时间',
                    width: 50,
                    sortable: true
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var imgPath = XFrame.getContextPath() + '/assets/custom/easyui/themes/icons/';
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
                            var tmpl = '<a href="#" title ="{{title}}" '
                                    + 'onclick="MembershipUser.doAction(\'{{index}}\',\'{{name}}\')">'
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
            return MembershipUser.edit(rowIndex, rowData);
        }
    });

    // dialogs
    $('#add-user-dlg').dialog({
        closed: true,
        modal: false,
        width: 560,
        height: 450,
        iconCls: 'icon-add',
        buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#add-user-dlg").dialog('close');
                }
            }, {
                text: '保存',
                iconCls: 'icon-save',
                handler: MembershipUser.save
            }]
    });

    $('#edit-user-dlg').dialog({
        closed: true,
        modal: false,
        width: 560,
        height: 400,
        iconCls: 'icon-edit',
        buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#edit-user-dlg").dialog('close');
                }
            }, {
                text: '保存',
                iconCls: 'icon-save',
                handler: MembershipUser.save
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
                handler: MembershipUser.save
            }]
    });

    MembershipUser.init();
    // end
});

var MembershipUser = {
    roleDict: {},
    init: function () {
        this.initEvent();
        MembershipUser.loadRoleList();
    },
    initEvent: function () {
        $('#btn-search').bind('click', MembershipUser.find);
    },
    loadRoleList: function () {
        $.getJSON(XFrame.getContextPath() + '/membership/role/getrolelist', function (src) {
            MembershipUser.roleDict = src.data;
        });
    },
    doAction: function (index, name) {
        $('#user-datagrid').datagrid('selectRow', index);
        if (name == "edit") {
            return MembershipUser.edit();
        }
        if (name == "remove") {
            return MembershipUser.remove();
        }
        if (name == "pwd") {
            return MembershipUser.resetPwd();
        }
    },
    fillRoleCombox: function (act, values) {
        var id = act == "add" ? "#add-combox-roles" : "#edit-combox-roles";
        $(id).combobox('clear');
        var data = [];
        var items = MembershipUser.roleDict;
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
    add: function () {
        $('#add-user-dlg').dialog('open').dialog('center');
        $("#modal-action").val("add");
        $("#add-form").form('reset');
        MembershipUser.fillRoleCombox("add", []);
    },
    edit: function () {
        var row = $('#user-datagrid').datagrid('getSelected');
        if (row) {
            $('#edit-user-dlg').dialog('open').dialog('center');
            $("#modal-action").val("edit");
            $("#edit-form").form('reset');
            $("#edit-account").text(row.account);
            var roleIds = row.roles || "";
            MembershipUser.fillRoleCombox("edit", roleIds.split(','));
            $("#edit-form").form('load', row);
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
        var url = membershipUserPageUrl + 'find?fieldName=' + fieldName + '&keyword=' + keyword;
        EasyUIUtils.loadToDatagrid('#user-datagrid', url)
    },
    remove: function () {
        var gridUrl = membershipUserPageUrl + 'list';
        var actUrl = membershipUserPageUrl + 'remove';
        return EasyUIUtils.removeWithIdFieldName('#user-datagrid', gridUrl, actUrl, "userId");
    },
    save: function () {
        var action = $('#modal-action').val();
        if (action == "resetPwd") {
            var url = membershipUserPageUrl + 'editpassword';
            EasyUIUtils.saveWithCallback('#reset-pwd-dlg', '#reset-pwd-form', url, function () {
            });
        } else {
            var formId = action == "edit" ? "#edit-form" : "#add-form";
            var dlgId = action == "edit" ? "#edit-user-dlg" : "#add-user-dlg";
            var comboxRoleId = action == "edit" ? "#edit-combox-roles" : "#add-combox-roles";
            var roleId = action == "edit" ? "#edit-roles" : "#add-roles";
            var roles = $(comboxRoleId).combobox('getValues');
            $(roleId).val(roles);
            var gridUrl = membershipUserPageUrl + 'list';
            return EasyUIUtils.saveWithActUrl(dlgId, formId, '#modal-action', '#user-datagrid', gridUrl,
                    membershipUserPageUrl);
        }
    },
};
