define(function (require, exports, module) {
    var $ = require('jquery');
    var Ezrp = require('easyreport');
    var EasyUIUtils = require('easyui-utils');

    var urls = {
        changeMyPassword: {
            url: Ezrp.ctxPath + '/rest/membership/user/changeMyPassword',
            method: 'POST'
        }
    };

    function Controller(model) {
    }

    Controller.prototype = {
        addTab: function (title, url, iconCls) {
            if ($('#main-tabs').tabs('exists', title)) {
                $('#main-tabs').tabs('select', title);
            } else {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
                $('#main-tabs').tabs('add', {
                    title: title,
                    content: content,
                    closable: true,
                    iconCls: iconCls
                });
            }
        },
        onTabCtxMenuClick: function (item) {
            if (item.name == "current") {
                return EasyUIUtils.closeCurrentTab('#main-tabs');
            }
            if (item.name == "others") {
                return EasyUIUtils.closeOthersTab('#main-tabs');
            }
            if (item.name == "all") {
                return EasyUIUtils.closeAllTab('#main-tabs');
            }
            return;
        },
        openMyProfileDlg: function () {
            $("#my-profile-dlg").dialog('open').dialog('center');
        },
        save: function () {
            var pwd = $('#password').val();
            var pwdRepeat = $('#passwordRepeat').val();
            if (pwd === pwdRepeat) {
                $('#my-profile-form').form('submit', {
                    url: urls.changeMyPassword,
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (data) {
                        var result = $.parseJSON(data)
                        if (result.success) {
                            EasyUIUtils.showMsg("密码修改成功");
                            $("#my-profile-dlg").dialog('close');
                        } else {
                            $.messager.alert('错误', result.msg, 'error');
                        }
                    }
                });
            } else {
                $.messager.alert('错误', '两次输入的密码不一致!', 'error');
                $('#password').focus();
            }
        }
    };

    function HomeIndex() {
        // 1.初始化变量
        this.controller = new Controller(this);
        this.dt = null;
        // 2.绑定事件
        $('#btnSearch').bind('click', this.controller.find);
        $('#btnNew').bind('click', this.controller.editDialog);
        $('#btnSave').bind('click', this.controller.add);
        // 3.绑定验证模型
        $("#editForm").validate({
            rules: {
                accountTypeId: {
                    required: true,
                    maxlength: 3,
                    min: 1,
                    max: 127,
                    digits: true
                },
                accountTypeName: {
                    required: true,
                    maxlength: 50
                },
                remark: {
                    required: true,
                    maxlength: 150
                }
            }
        });
    }

    HomeIndex.prototype = {
        constructor: HomeIndex,
        init: function () {
            $('#btnSearch').click();
        },
        edit: function (rowIdx) {
            this.controller.edit(rowIdx);
        },
        del: function (rowIdx) {
            this.controller.del(rowIdx);
        }
    };

    module.exports = HomeIndex;
});