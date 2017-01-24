$(function() {
	HomeIndex.init();
});

var HomeIndex = {
	init : function() {
		HomeIndexMVC.View.initControl();
		HomeIndexMVC.View.bindEvent();
		HomeIndexMVC.View.bindValidate();
		HomeIndexMVC.View.initMenus();
	},
	addTab : function(id, title, url, iconCls, closable) {
		HomeIndexMVC.Controller.addTab(id, title, url, iconCls,
				closable == undefined ? true : closable)
	},
	selectedTab : function(title) {
		$('#main-tabs').tabs('select', title ? title : jQuery.i18n.prop('index.report.design'));
	},
	showUserInfo : function() {
		HomeIndexMVC.Controller.showMyProfileDlg();
	},
	showChangeMyPwd : function() {
		HomeIndexMVC.Controller.showChangeMyPwdDlg();
	}
};

var HomeIndexMVC = {
	URLs : {
		getMenus : {
			url : EasyReport.ctxPath + '/rest/home/getMenus',
			method : 'GET'
		},
		changeMyPassword : {
			url : EasyReport.ctxPath + '/rest/membership/user/changeMyPassword',
			method : 'POST'
		},
		designer : {
			url : EasyReport.ctxPath + '/views/metadata/designer-ro',
			method : 'GET'
		}
	},
	View : {
		initControl : function() {
			$('#main-tabs').tabs({
				onContextMenu : function(e, title, index) {
					e.preventDefault();
					$('#main-tab-ctx-menu').menu('show', {
						left : e.pageX,
						top : e.pageY
					});
				},
				onSelect : function(title, index) {
					var tab = $('#main-tabs').tabs('getSelected');
					return tab.panel('refresh');
				}
			});

			$('#main-tab-ctx-menu').menu({
				onClick : function(item) {
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
				closed : true,
				modal : true,
				width : 450,
				height : 320,
				iconCls : 'icon-avatar',
				buttons : [ {
					text : jQuery.i18n.prop('index.close'),
					iconCls : 'icon-no',
					handler : function() {
						$("#my-profile-dlg").dialog('close');
					}
				} ]
			});

			$('#change-my-pwd-dlg').dialog({
				closed : true,
				modal : true,
				width : 450,
				height : 320,
				iconCls : 'icon-pwd',
				buttons : [ {
					text : jQuery.i18n.prop('index.close'),
					iconCls : 'icon-no',
					handler : function() {
						$("#change-my-pwd-dlg").dialog('close');
					}
				}, {
					text : jQuery.i18n.prop('index.ok'),
					iconCls : 'icon-save',
					handler : HomeIndexMVC.Controller.changeMyPwd
				} ]
			});
		},
		bindEvent : function() {
		},
		bindValidate : function() {
		},
		initMenus : function() {
			var loginUser = $('#login-user-name').val();
			HomeIndexMVC.Controller.buildMenu(loginUser);
			HomeIndex.addTab(0, jQuery.i18n.prop('index.report.mine'), HomeIndexMVC.URLs.designer.url,
					'icon-chart', false);
		}
	},
	Controller : {
		addTab : function(id, title, url, iconCls, closable) {
			if ($('#main-tabs').tabs('exists', title)) {
				$('#main-tabs').tabs('select', title);
			} else {
				var content = '<iframe scrolling="auto" frameborder="0"  src="'
						+ url + '" style="width:100%;height:100%;"></iframe>';
				$('#main-tabs').tabs('add', {
					id : id,
					title : title,
					content : content,
					closable : closable,
					iconCls : iconCls
				});
			}
		},
		showChangeMyPwdDlg : function() {
			$("#change-my-pwd-dlg").dialog('open').dialog('center');
		},
		showMyProfileDlg : function() {
			$("#my-profile-dlg").dialog('open').dialog('center');
		},
		changeMyPwd : function() {
			var pwd = $('#password').val();
			var pwdRepeat = $('#passwordRepeat').val();
			if (pwd === pwdRepeat) {
				$('#change-my-pwd-form').form('submit', {
					url : HomeIndexMVC.URLs.changeMyPassword.url,
					onSubmit : function() {
						return $(this).form('validate');
					},
					success : function(data) {
						var result = $.toJSON(data);
						if (result.success) {
							EasyUIUtils.showMsg(jQuery.i18n.prop('index.change.password.success'));
							$("#my-profile-dlg").dialog('close');
						} else {
							$.messager.alert(jQuery.i18n.prop('index.change.password.error'), result.msg, 'error');
						}
					}
				});
			} else {
				$.messager.alert(jQuery.i18n.prop('index.change.password.error'), jQuery.i18n.prop('index.change.password.error.password.different'), 'error');
				$('#password').focus();
			}
		},
		buildMenu : function(loginUser) {
			$
					.getJSON(
							HomeIndexMVC.URLs.getMenus.url,
							function(result) {
								if (!result.success) {
									console.info(result.msg);
								}

								var menuItems = [];
								var roots = result.data;
								var tmpl = "<a href=\"#\" class=\"${buttonCss}\" "
										+ "data-options=\"${subMenu},iconCls:'${iconCls}'\" ${clickEvent}>${name}</a>";

								// main menu items start
								menuItems
										.push('<div style=\"padding: 2px 5px;\">');
								for (var i = 0; i < roots.length; i++) {
									var module = roots[i].attributes;
									var url = module.linkType ? module.url
											: EasyReport.ctxPath + '/'
													+ module.url;
									var onClick = juicer(
											"onclick=\"HomeIndex.addTab('${id}}','${name}','${url}','${icon}')\"",
											{
												id : 'm_' + module.id,
												url : url,
												name : jQuery.i18n.prop(module.code),
												icon : module.icon
											});
									var subMenu = module.hasChild > 0 ? "menu:'#mm"
											+ module.id + "'"
											: "plain:true";
									var buttonCss = module.hasChild > 0 ? "easyui-menubutton"
											: "easyui-linkbutton";
									var clickEvent = module.hasChild > 0 ? ""
											: onClick;
									menuItems.push(juicer(tmpl, {
										buttonCss : buttonCss,
										subMenu : subMenu,
										clickEvent : clickEvent,
										iconCls : module.icon,
										name : jQuery.i18n.prop(module.code)
									}));
								}

								// 生成用户信息菜单项
								menuItems.push(juicer(tmpl, {
									buttonCss : "easyui-menubutton",
									subMenu : "menu:'#mm0'",
									clickEvent : "",
									iconCls : "",
									name : jQuery.i18n.prop('index.welcome') + loginUser
								}));

								// main menu items end
								menuItems.push('</div>');

								// 生成动态配置的子菜单项
								for (var i = 0; i < roots.length; i++) {
									HomeIndexMVC.Controller.buildChildMenu(
											roots[i], menuItems);
								}
								// 生成用户信息子菜单项
								HomeIndexMVC.Controller
										.buildUserInfoChildMemu(menuItems);

								$(".menus").html(menuItems.join(''));
								$.parser.parse('.menus');
							});
		},
		buildChildMenu : function(parent, menuItems) {
			if (!parent.children || !parent.children.length)
				return;
			menuItems.push("<div id=\"mm" + parent.id
					+ "\" style=\"width: 150px;\">");
			for (var i = 0; i < parent.children.length; i++) {
				var module = parent.children[i].attributes;
				var url = module.linkType ? module.url : EasyReport.ctxPath
						+ '/' + module.url;
				var tmpl = "<div data-options=\"iconCls:'${iconCls}'\" "
						+ "onclick=\"HomeIndex.addTab('${id}','${name}','${url}','${iconCls}',${closable})\">${name}</div>";
				menuItems.push(juicer(tmpl, {
					id : 'm_' + module.id,
					url : url,
					iconCls : module.icon,
					name : jQuery.i18n.prop(module.code),
					closable :  module.code != "report.designer"
				}));
				HomeIndexMVC.Controller.buildChildMenu(module, menuItems);
			}
			menuItems.push('</div>');
		},
		buildUserInfoChildMemu : function(menuItems) {
			var items = [ {
				url : '#',
				iconCls : 'icon-avatar',
				name : jQuery.i18n.prop('index.user.info'),
				clickEvent : 'onclick=HomeIndex.showUserInfo()'
			}, {
				url : '#',
				iconCls : 'icon-pwd',
				name : jQuery.i18n.prop('index.change.password'),
				clickEvent : 'onclick=HomeIndex.showChangeMyPwd()'
			}, {
				url : EasyReport.ctxPath + '/membership/logout',
				iconCls : 'icon-cancel',
				name :  jQuery.i18n.prop('index.exit'),
				clickEvent : ''
			} ];
			var tmpl = "<div href=\"${url}\" data-options=\"iconCls:'${iconCls}'\" ${clickEvent}>${name}</div>";
			menuItems.push("<div id=\"mm0\" style=\"width: 150px;\">");
			for (var i = 0; i < items.length; i++) {
				menuItems.push(juicer(tmpl, items[i]));
			}
			menuItems.push('</div>');
		}
	}
};
