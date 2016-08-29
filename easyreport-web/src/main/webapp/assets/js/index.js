$(function () {
    // 初始化tab相关事件
    $('#main-tabs').tabs({
        onContextMenu: function (e, title, index) {
            e.preventDefault();
            $('#main-tab-ctx-menu').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });

    // dialogs
    $('#my-profile-dlg').dialog({
        closed: true,
        modal: true,
        width: 450,
        height: 320,
        iconCls: 'icon-avatar',
        buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#my-profile-dlg").dialog('close');
                }
            }, {
                text: '确定',
                iconCls: 'icon-save',
                handler: HomeIndex.save
            }]
    });

    $('#btn-my-profile').bind('click', HomeIndex.openMyProfileDlg);
});

var HomeIndex = {
    addTab: function (title, url, iconCls) {
        if ($('#main-tabs').tabs('exists', title)) {
            $('#main-tabs').tabs('select', title);
        } else {
            var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
                    + '" style="width:100%;height:100%;"></iframe>';
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
                url: XFrame.getContextPath() + '/rest/membership/user/changeMyPassword',
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