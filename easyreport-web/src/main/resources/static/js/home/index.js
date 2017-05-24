$(function () {
    HomeIndex.init();
});

var HomeIndex = {
    init: function () {
        HomeIndexMVC.View.initControl();
        HomeIndexMVC.View.bindEvent();
        HomeIndexMVC.View.bindValidate();
        HomeIndexMVC.View.initMenus();
    },
    addTab: function (id, title, url, iconCls, closable) {
        HomeIndexMVC.Controller.addTab(id, title, url, iconCls, closable == undefined ? true : closable)
    },
    selectedTab: function (title) {
        $('#main-tabs').tabs('select', title ? title : '报表设计');
    },
    showUserInfo: function () {
        HomeIndexMVC.Controller.showMyProfileDlg();
    },
    showChangeMyPwd: function () {
        HomeIndexMVC.Controller.showChangeMyPwdDlg();
    }
};

var HomeIndexMVC = {
    URLs: {
        getMenus: {
            url: EasyReport.ctxPath + '/rest/home/getMenus',
            method: 'GET'
        },
        changeMyPassword: {
            url: EasyReport.ctxPath + '/rest/member/user/changeMyPassword',
            method: 'POST'
        },
        designer: {
            url: EasyReport.ctxPath + '/views/report/designer',
            method: 'GET'
        },
        logout: {
            url: EasyReport.ctxPath + '/member/logout',
            method: 'POST'
        }
    },
    View: {
        initControl: function () {
            $('#main-tabs').tabs({
                onContextMenu: function (e, title, index) {
                    e.preventDefault();
                    $('#main-tab-ctx-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                },
                onSelect: function (title, index) {
                    var tab = $('#main-tabs').tabs('getSelected');
                    return tab.panel('refresh');
                }
            });

            $('#main-tab-ctx-menu').menu({
                onClick: function (item) {
                    if (item.name == "current") {
                        return EasyUIUtils.closeCurrentTab('#main-tabs');
                    }
                    if (item.name == "others") {
                        return EasyUIUtils.closeOthersTab('#main-tabs');
                    }
                    if (item.name == "all") {
                        return EasyUIUtils.closeAllTab('#main-tabs');
                    }
                },
            });

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
                }]
            });

            $('#change-my-pwd-dlg').dialog({
                closed: true,
                modal: true,
                width: 450,
                height: 320,
                iconCls: 'icon-pwd',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#change-my-pwd-dlg").dialog('close');
                    }
                }, {
                    text: '确定',
                    iconCls: 'icon-save',
                    handler: HomeIndexMVC.Controller.changeMyPwd
                }]
            });
        },
        bindEvent: function () {
        },
        bindValidate: function () {
        },
        initMenus: function () {
            var loginUser = $('#login-user-name').val();
            HomeIndexMVC.Controller.buildMenu(loginUser);
            HomeIndex.addTab(0, '报表设计', HomeIndexMVC.URLs.designer.url, 'icon-chart', false);
        }
    },
    Controller: {
        addTab: function (id, title, url, iconCls, closable) {
            if ($('#main-tabs').tabs('exists', title)) {
                $('#main-tabs').tabs('select', title);
            } else {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
                    + '" style="width:100%;height:99%;"></iframe>';
                $('#main-tabs').tabs('add', {
                    id: id,
                    title: title,
                    content: content,
                    closable: closable,
                    iconCls: iconCls
                });
            }
        },
        showChangeMyPwdDlg: function () {
            $("#change-my-pwd-dlg").dialog('open').dialog('center');
        },
        showMyProfileDlg: function () {
            $("#my-profile-dlg").dialog('open').dialog('center');
        },
        changeMyPwd: function () {
            var pwd = $('#password').val();
            var pwdRepeat = $('#passwordRepeat').val();
            if (pwd === pwdRepeat) {
                $('#change-my-pwd-form').form('submit', {
                    url: HomeIndexMVC.URLs.changeMyPassword.url,
                    onSubmit: function () {
                        return $(this).form('validate');
                    },
                    success: function (data) {
                        var result = $.toJSON(data);
                        if (!result.code) {
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
        },
        buildMenu: function (loginUser) {
            $.getJSON(HomeIndexMVC.URLs.getMenus.url, function (result) {
                if (result.code) {
                    console.info(result.msg);
                }

                var menuItems = [];
                var roots = result.data;
                var tmpl = "<a href=\"#\" class=\"${buttonCss}\" "
                    + "data-options=\"${subMenu},iconCls:'${iconCls}'\" ${clickEvent}>${name}</a>";

                // main menu items start
                menuItems.push('<div style=\"padding: 2px 5px;\">');
                for (var i = 0; i < roots.length; i++) {
                    var module = roots[i].attributes;
                    var url = module.linkType ? module.url : EasyReport.ctxPath + '/' + module.url;
                    var onClick = juicer("onclick=\"HomeIndex.addTab('${id}}','${name}','${url}','${icon}')\"", {
                        id: 'm_' + module.id,
                        url: url,
                        name: module.name,
                        icon: module.icon
                    });
                    var subMenu = module.hasChild > 0 ? "menu:'#mm" + module.id + "'" : "plain:true";
                    var buttonCss = module.hasChild > 0 ? "easyui-menubutton" : "easyui-linkbutton";
                    var clickEvent = module.hasChild > 0 ? "" : onClick;
                    menuItems.push(juicer(tmpl, {
                        buttonCss: buttonCss,
                        subMenu: subMenu,
                        clickEvent: clickEvent,
                        iconCls: module.icon,
                        name: module.name
                    }));
                }

                // 生成用户信息菜单项
                menuItems.push(juicer(tmpl, {
                    buttonCss: "easyui-menubutton",
                    subMenu: "menu:'#mm0'",
                    clickEvent: "",
                    iconCls: "",
                    name: '欢迎,' + loginUser
                }));

                // main menu items end
                menuItems.push('</div>');

                // 生成动态配置的子菜单项
                for (var i = 0; i < roots.length; i++) {
                    HomeIndexMVC.Controller.buildChildMenu(roots[i], menuItems);
                }
                // 生成用户信息子菜单项
                HomeIndexMVC.Controller.buildUserInfoChildMemu(menuItems);

                $(".menus").html(menuItems.join(''));
                $.parser.parse('.menus');
            });
        },
        buildChildMenu: function (parent, menuItems) {
            if (!parent.children || !parent.children.length)
                return;
            menuItems.push("<div id=\"mm" + parent.id + "\" style=\"width: 150px;\">");
            for (var i = 0; i < parent.children.length; i++) {
                var module = parent.children[i].attributes;
                var url = module.linkType ? module.url : EasyReport.ctxPath + '/' + module.url;
                var tmpl = "<div data-options=\"iconCls:'${iconCls}'\" "
                    + "onclick=\"HomeIndex.addTab('${id}','${name}','${url}','${iconCls}',${closable})\">${name}</div>";
                menuItems.push(juicer(tmpl, {
                    id: 'm_' + module.id,
                    url: url,
                    iconCls: module.icon,
                    name: module.name,
                    closable: module.name != "报表设计"
                }));
                HomeIndexMVC.Controller.buildChildMenu(module, menuItems);
            }
            menuItems.push('</div>');
        },
        buildUserInfoChildMemu: function (menuItems) {
            var items = [{
                url: '#',
                iconCls: 'icon-avatar',
                name: '用户信息',
                clickEvent: 'onclick=HomeIndex.showUserInfo()'
            }, {
                url: '#',
                iconCls: 'icon-pwd',
                name: '修改密码',
                clickEvent: 'onclick=HomeIndex.showChangeMyPwd()'
            }, {
                url: HomeIndexMVC.URLs.logout.url,
                iconCls: 'icon-cancel',
                name: '退出',
                clickEvent: ''
            }];
            var tmpl = "<div href=\"${url}\" data-options=\"iconCls:'${iconCls}'\" ${clickEvent}>${name}</div>";
            menuItems.push("<div id=\"mm0\" style=\"width: 150px;\">");
            for (var i = 0; i < items.length; i++) {
                menuItems.push(juicer(tmpl, items[i]));
            }
            menuItems.push('</div>');
        }
    }
};
