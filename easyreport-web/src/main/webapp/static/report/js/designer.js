var designerPageRootUrl = XFrame.getContextPath() + '/report/designer/';
$(function() {
	Reporting.init();
	
	$('#west').panel({
		tools : [ {
			iconCls : 'icon-search',
			handler : Reporting.openSearchReportDlg
		}, {
			iconCls : 'icon-add',
			handler : Reporting.addRootTreeNode
		}, {
			iconCls : 'icon-reload',
			handler : Reporting.reloadTree
		} ]
	});

	$('#reportingTree').tree({
		checkbox : false,
		method : 'get',
		animate : true,
		dnd : true,
		url : designerPageRootUrl + 'listchildnodes',
		onLoadSuccess : function(node, data) {
			if (Reporting.treeNodePathIds && Reporting.treeNodePathIds.length > 0) {
				var id = Reporting.treeNodePathIds.shift();
				Reporting.selectAndExpandTreeNode(id);
			}
		},
		onClick : Reporting.clickTreeNodeHandler,
		onDrop : Reporting.treeNodeOnDrop,
		onDblClick : function(node) {
			if (node.attributes.flag == 1) {
				Reporting.previewInNewTab();
			}
		},
		onContextMenu : function(e, node) {
			e.preventDefault();
			$('#reportingTree').tree('select', node.target);
			var copyNodeId = $('#copyNodeId').val();
			var item = $('#reportingTreeCtxMenu').menu('findItem', '粘贴');
			$('#reportingTreeCtxMenu').menu(copyNodeId == 0 ? 'disableItem' : 'enableItem', item.target);
			$('#reportingTreeCtxMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});

	$('#sqlColumnGrid').datagrid({
		method : 'post',
		fit : true,
		fitColumns : true,
		singleSelect : true,
		rownumbers : true,
		tools : [ {
			iconCls : 'icon-up',
			handler : Reporting.upSqlColumnRow
		}, '-', {
			iconCls : 'icon-down',
			handler : Reporting.downSqlColumnRow
		}, '-', {
			iconCls : 'icon-add',
			handler : Reporting.addSqlColumnRow
		}, '-', {
			iconCls : 'icon-cancel',
			handler : Reporting.deleteSqlColumnRow
		} ],
		columns : [ [ {
			field : 'name',
			title : '列名',
			width : 100,
			formatter : function(value, row, index) {
				return Reporting.sqlColumnNameFormatter(value, row, index);
			}
		}, {
			field : 'text',
			title : '标题',
			width : 100,
			formatter : function(value, row, index) {
				return Reporting.textFormatter(value, row, index);
			}
		}, {
			field : 'type',
			title : '类型',
			width : 75,
			formatter : function(value, row, index) {
				return Reporting.typeFormatter(value, row, index);
			}
		}, {
			field : 'dataType',
			title : '数据类型',
			width : 75
		}, {
			field : 'width',
			title : '宽度',
			width : 40
		}, {
			field : 'decimals',
			title : '精度',
			width : 50,
			formatter : function(value, row, index) {
				return Reporting.decimalsFormatter(value, row, index);
			}
		}, {
			field : 'sortType',
			title : '排序类型',
			width : 75,
			formatter : function(value, row, index) {
				return Reporting.sortTypeFormatter(value, row, index);
			}
		}, {
			field : 'options',
			title : '配置',
			width : 300,
			formatter : function(value, row, index) {
				return Reporting.optionsFormatter(value, row, index);
			}
		}, {
			field : 'expression',
			title : '表达式',
			hidden : true,
			formatter : function(value, row, index) {
				return Reporting.expressionFormatter(value, row, index);
			}
		} ] ],
	});

	$('#queryParamGrid').datagrid({
		method : 'get',
		fit : true,
		singleSelect : true,
		columns : [ [ {
			field : 'name',
			title : '参数名',
			width : 80
		}, {
			field : 'text',
			title : '标题',
			width : 80
		}, {
			field : 'defaultValue',
			title : '默认值',
			width : 80
		}, {
			field : 'defaultText',
			title : '默认标题',
			width : 80
		}, {
			field : 'formElement',
			title : '表单控件',
			width : 80
		}, {
			field : 'dataSource',
			title : '来源类型',
			width : 80,
			formatter : function(value, row, index) {
				if (value == "sql")
					return "SQL语句";
				if (value == "text")
					return "文本字符串";
				return "无内容";
			}
		}, {
			field : 'content',
			title : '内容',
			width : 360
		}, {
			field : 'options',
			title : '操作',
			width : 50,
			formatter : function(value, row, index) {
				return Reporting.queryParamOptionFormatter(value, row, index);
			}
		} ] ],
		onDblClickRow : function(rowIndex, rowData) {
			Reporting.editQueryParam(rowIndex, rowData);
		}
	});

	$('#expressionSettingsDlg').dialog({
		closed : true,
		modal : true,
		width : 500,
		height : 300,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#expressionSettingsDlg").dialog('close');
			}
		}, {
			text : '保存',
			iconCls : 'icon-save',
			handler : Reporting.saveExpression
		} ]
	});

	// 初始化查找报表dialog
	$('#searchReportDlg').dialog({
		closed : true,
		modal : true,
		width : 600,
		height : 400,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#searchReportDlg").dialog('close');
			}
		} ]
	});

	$('#searchReportGrid').datagrid({
		method : 'get',
		fit : true,
		pagination : true,
		rownumbers : true,
		fitColumns : true,
		singleSelect : true,
		pageSize : 10,
		columns : [ [ {
			field : 'id',
			title : '标识',
			width : 50
		}, {
			field : 'name',
			title : '名称',
			width : 230
		}, {
			field : 'uid',
			title : '代号',
			width : 70
		}, {
			field : 'createUser',
			title : '创建者',
			width : 80
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 100
		}, {
			field : 'path',
			title : '路径',
			hidden : true
		}, {
			field : 'pid',
			title : '父标识',
			hidden : true
		} ] ],
		onDblClickRow : function(rowIndex, rowData) {
			Reporting.locateTreeNode(rowIndex, rowData);
		}
	});

	$('#viewSqlTextHistoryGrid').datagrid({
		method : 'get',
		fit : true,
		pagination : true,
		rownumbers : true,
		fitColumns : true,
		singleSelect : true,
		pageSize : 10,
		columns : [ [ {
			field : 'createTime',
			title : '日期',
			width : 100
		}, {
			field : 'author',
			title : '作者',
			width : 100
		} ] ],
		onDblClickRow : function(rowIndex, rowData) {
			Reporting.showHistorySqlText(rowIndex, rowData);
		}
	});

	editing = undefined;

	// Sql列配置项
	configOptions = [ {
		name : "optional",
		text : "可选",
		type : 1
	}, {
		name : "percent",
		text : "百分比",
		type : 1
	}, {
		name : "displayInMail",
		text : "邮件显示",
		type : 1
	}, {
		name : "footings",
		text : "合计",
		type : 1
	}, {
		name : "dayHuanRatio",
		text : "日环比",
		type : 2
	}, {
		name : "weekTongRatio",
		text : "周同比",
		type : 2
	}, {
		name : "monthTongRatio",
		text : "月同比",
		type : 2
	}, {
		name : "extensions",
		text : "小计",
		type : 3
	}, {
		name : "expression",
		text : "表达式",
		type : 4
	} ];

	// 初始化tab相关事件
	$('#tabs').tabs({
		onContextMenu : function(e, title, index) {
			e.preventDefault();
			$('#tabCtxMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		},
		onSelect : Reporting.tabSelectedHandlder
	});

	// 初始化报表属性dialog
	$('#reportingProperties').dialog({
		title : "报表属性",
		closed : true,
		modal : true,
		width : window.screen.width - 300,
		height : window.screen.height - 300,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#reportingProperties").dialog('close');
			}
		} ]
	});

	// 初始化增加与修改树节点dialog
	$('#setTreeNodeDlg').dialog({
		closed : true,
		modal : true,
		width : 500,
		height : 360,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#setTreeNodeDlg").dialog('close');
			}
		}, {
			text : '保存',
			iconCls : 'icon-save',
			handler : Reporting.saveTreeNode
		} ]
	});

	// 初始化查看报表SQL的dialog
	$('#viewSqlTextDlg').dialog({
		closed : true,
		modal : true,
		maximizable : true,
		width : window.screen.width - 500,
		height : window.screen.height - 200,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#viewSqlTextDlg").dialog('close');
			}
		} ]
	});

	// 初始化查看报表SQL历史记录的dialog
	$('#viewSqlTextHistoryDlg').dialog({
		closed : true,
		modal : true,
		maximizable : true,
		width : window.screen.width - 200,
		height : window.screen.height - 200,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#viewSqlTextHistoryDlg").dialog('close');
			}
		} ]
	});

	viewSqlCmEditor = CodeMirror.fromTextArea(document.getElementById("viewSqlText"), {
		mode : 'text/x-mysql',
		theme : 'rubyblue',
		indentWithTabs : true,
		smartIndent : true,
		lineNumbers : true,
		matchBrackets : true,
		autofocus : true
	});

	exceSqlCmEditor = CodeMirror.fromTextArea(document.getElementById("reportingSqlText"), {
		mode : 'text/x-mysql',
		theme : 'rubyblue',
		indentWithTabs : true,
		smartIndent : true,
		lineNumbers : true,
		matchBrackets : true,
		autofocus : true,
		extraKeys : {
			"F11" : function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc" : function(cm) {
				if (cm.getOption("fullScreen"))
					cm.setOption("fullScreen", false);
			}
		}
	});
	exceSqlCmEditor.on("change", function(cm, obj) {
		if (obj.origin == "setValue") {
			$('#reportingIsChange').val(0);
		} else {
			$('#reportingIsChange').val(1);
		}
	});
	$("#reportingSqlTextTd>div[class='CodeMirror cm-s-rubyblue']").css({
		"height" : 192,
		"width" : $('#reportingSqlTextTd').css('width')
	});
	Reporting.setExceSqlCmEditorStatus();

	viewSqlHistoryCmEditor = CodeMirror.fromTextArea(document.getElementById("viewSqlTextHistory"), {
		mode : 'text/x-mysql',
		theme : 'rubyblue',
		indentWithTabs : true,
		smartIndent : true,
		lineNumbers : true,
		matchBrackets : true,
		autofocus : true,
		extraKeys : {
			"F11" : function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc" : function(cm) {
				if (cm.getOption("fullScreen"))
					cm.setOption("fullScreen", false);
			}
		}
	});

	Reporting.initValidateOptions();
});

var Reporting = function() {
};

//
// 初始化相关操作
//
Reporting.init = function() {
	$('#sqlColumnGridTd').css({
		"height" : $('#tabs').tabs('options').height - 360
	});
	$('#queryParamGridTd').css({
		"height" : $('#tabs').tabs('options').height - 400
	});

	$('#reportingEditBtn').linkbutton('disable');
	$('#reportingPreviewBtn').linkbutton('disable');

	// 绑定查询参数列设置的选择表单select的onchange事件
	$('#queryParamFormElement').change(function() {
		var formElement = $("#queryParamFormElement").val();
		if (formElement == "select" || formElement == "selectMul") {
			$("#queryParamContent").removeAttr("disabled");
			$("#queryParamDataSource").val("0");
			return;
		}

		$("#queryParamContent").val('');
		$("#queryParamContent").attr({
			"disabled" : "disabled"
		});
		$("#queryParamDataSource").val("2");
	});

	// 绑定报表状态select的onchange事件
	$('#reportingStatus').change(function() {
		return Reporting.setExceSqlCmEditorStatus();
	});

	Reporting.initDsCombox();
};

Reporting.initValidateOptions = function() {
	ReportingValidate.configOptions();
	ReportingValidate.queryParamOptions();
};

Reporting.initDsCombox = function() {
	$('#reportingDsId').combobox({
		url : XFrame.getContextPath() + '/report/ds/getall',
		valueField : 'id',
		textField : 'name',
		editable : false
	});
};

Reporting.setExceSqlCmEditorStatus = function() {
	var status = $("#reportingStatus").val();
	if (status > 0) {
		return exceSqlCmEditor.setOption("readOnly", "nocursor");
	}
	return exceSqlCmEditor.setOption("readOnly", false);
};

// 树右键菜单相关操作
Reporting.treeContextMenu = function(item) {
	if (item.name == "addRp")
		return Reporting.add();
	if (item.name == "add")
		return Reporting.addChildTreeNode();
	if (item.name == "edit")
		return Reporting.editTreeNode();
	if (item.name == "comment")
		return Reporting.setTreeNodeComment();
	if (item.name == "remove")
		return Reporting.removeTreeNode();
	if (item.name == "search")
		return Reporting.openSearchReportDlg();
	if (item.name == "refresh")
		return Reporting.reloadTree();
	if (item.name == "copy")
		return Reporting.copyTreeNode();
	if (item.name == "paste")
		return Reporting.pasteTreeNode();
	if (item.name == "privilage")
		return Reporting.privilegeSettings();
	if (item.name == "contacts")
		return Reporting.contactsSettings();
	if (item.name == "info")
		return Reporting.showProperties();
	if (item.name == "chown")
		return Reporting.changeOwner();
	return;
};

//
// tabs相关操作
//
Reporting.getSelectedTabIndex = function() {
	var tab = $('#tabs').tabs('getSelected');
	return $('#tabs').tabs('getTabIndex', tab);
};

Reporting.selectTab = function(index) {
	$('#tabs').tabs('select', index);
};

Reporting.tabSelectedHandlder = function(title, currIndex) {
	Reporting.saveChanged(currIndex, function(index) {
		if (index == 0) {
			exceSqlCmEditor.refresh();
			viewSqlCmEditor.refresh();
			viewSqlHistoryCmEditor.refresh();
			return;
		}
		if (index > 1) {
			var tab = $('#tabs').tabs('getTab', index);
			var id = tab.panel('options').id;
			Reporting.selectAndExpandTreeNode(id);
		}
	});
};

Reporting.loadDataToTab = function(index) {
	// 非固定tab
	if (index > 1) {
		return;
	}

	var node = Reporting.getSelectedTreeNode();
	if (node) {
		// 报表配置
		if (index == 0) {
			return Reporting.edit(node.attributes);
		}
		// 参数设置
		if (index == 1) {
			return Reporting.loadDataToQueryParamTab(node.attributes);
		}
	}
};

Reporting.clearTab = function(index) {
	if (index == 0) {
		Reporting.clearCodeMirrorEditor();
		Reporting.resetForm();
		return ReportCommon.clearDataGrid('#sqlColumnGrid');
	}
	if (index == 1) {
		$('#queryParamForm').form('reset');
		return ReportCommon.clearDataGrid('#queryParamGrid');
	}
};

Reporting.resetForm = function() {
	$("#reportingDsId").val("");
	$("#reportingLayout").val("1");
	$("#reportingDataRange").val("7");
	$("#reportingName").val("");
	$("#reportingStatus").val("0");
	$("#reportingSequence").val("100");
	$("#reportingId").val("0");
	$("#reportingAction").val("add");
	$("#reportingUid").val("");
	$("#reportingPid").val("0");
	$("#reportingIsChange").val("0");
};

Reporting.clearAllCheckedNode = function(target) {
	var nodes = $(target).tree('getChecked');
	if (nodes) {
		for ( var i = 0; i < nodes.length; i++) {
			$(target).tree('uncheck', nodes[i].target);
		}
	}
};

Reporting.clearAllTab = function() {
	Reporting.clearTab(0);
	Reporting.clearTab(1);
};

Reporting.loadAllTabData = function() {
	Reporting.loadDataToTab(0);
	Reporting.loadDataToTab(1);
};

Reporting.reloadSelectedTab = function() {
	var index = Reporting.getSelectedTabIndex();
	Reporting.clearTab(index);
	Reporting.loadDataToTab(index);
};

Reporting.tabContextMenu = function(item) {
	if (item.name == "current")
		return Reporting.closeCurrentTab();
	if (item.name == "others")
		return Reporting.closeOthersTab();
	if (item.name == "all")
		return Reporting.closeAllTab();
	return;
};

Reporting.closeCurrentTab = function() {
	var tab = $('#tabs').tabs('getSelected');
	var options = tab.panel('options');
	if (options.closable) {
		$('#tabs').tabs('close', tab.panel('options').title);
	}
};

Reporting.closeOthersTab = function() {
	var currentTab = $('#tabs').tabs('getSelected');
	var currTitle = currentTab.panel('options').title;
	$('.tabs-inner span').each(function(i, n) {
		var title = $(n).text();
		var tab = $('#tabs').tabs('getTab', title);
		if (tab) {
			var options = tab.panel('options');
			if (title != currTitle && options.closable) {
				$('#tabs').tabs('close', title);
			}
		}
	});
};

Reporting.closeAllTab = function() {
	$('.tabs-inner span').each(function(i, n) {
		var title = $(n).text();
		var tab = $('#tabs').tabs('getTab', title);
		if (tab) {
			var options = tab.panel('options');
			if (options.closable) {
				$('#tabs').tabs('close', title);
			}
		}
	});
};

//
// 报表树相关操作
//
Reporting.addRootTreeNode = function() {
	var data = {
		"id" : 0,
		"name" : "根节点"
	};
	Reporting.addTreeNode(data);
};

Reporting.addChildTreeNode = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		Reporting.addTreeNode(node.attributes);
	}
};

Reporting.addTreeNode = function(data) {
	$('#setTreeNodeDlg').dialog('open').dialog('setTitle', '新增节点');
	$('#setTreeNodeComment').validatebox({
		required : false
	});
	$('#setTreeNodeComment').css({
		"height" : 168
	});
	$('#setTreeNodeParentNodeNameTr').show();
	$('#setTreeNodeFlagTr').hide();
	$('#setTreeNodeHasChildTr').hide();
	$('#setTreeNodeStatusTr').hide();
	$('#setTreeNodeForm').form('reset');
	$("#parentNodeName").html(data.name);
	$("#treeNodePid").val(data.id);
	$("#treeNodeId").val(0);
	$("#treeNodeAction").val("add");
};

Reporting.editTreeNode = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		var data = node.attributes;
		$('#setTreeNodeComment').validatebox({
			required : false
		});
		$('#setTreeNodeComment').css({
			"height" : 130
		});
		$('#setTreeNodeDlg').dialog('open').dialog('setTitle', '修改节点');
		$('#setTreeNodeParentNodeNameTr').hide();
		$('#setTreeNodeFlagTr').show();
		$('#setTreeNodeHasChildTr').show();
		$('#setTreeNodeStatusTr').show();
		$('#setTreeNodeForm').form('reset');
		$("#parentNodeName").html("");
		$("#treeNodePid").val(data.pid);
		$("#treeNodeId").val(data.id);
		$("#treeNodeAction").val("edit");
		$('#setTreeNodeForm').form('load', data);
		$("#setTreeNodeHasChild").val(data.hasChild ? "1" : "0");
	}
};

Reporting.setTreeNodeComment = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		var data = node.attributes;
		$('#setTreeNodeComment').validatebox({
			required : true
		});
		$('#setTreeNodeComment').css({
			"height" : 130
		});
		$('#setTreeNodeDlg').dialog('open').dialog('setTitle', '设置节点');
		$('#setTreeNodeParentNodeNameTr').hide();
		$('#setTreeNodeFlagTr').show();
		$('#setTreeNodeHasChildTr').show();
		$('#setTreeNodeStatusTr').show();
		$('#setTreeNodeForm').form('reset');
		$("#parentNodeName").html("");
		$("#treeNodePid").val(data.pid);
		$("#treeNodeId").val(data.id);
		$("#treeNodeAction").val("edit");
		$('#setTreeNodeForm').form('load', data);
		$("#setTreeNodeHasChild").val(data.hasChild ? "1" : "0");
	}
};

Reporting.removeTreeNode = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		var data = node.attributes;
		$.messager.confirm('删除', '您确定要删除该节点吗?', function(isConfirm) {
			if (!isConfirm) {
				return;
			}
			$.post(designerPageRootUrl + 'remove', {
				id : data.id,
				pid : data.pid
			}, function(data) {
				if (data.success) {
					$('#reportingTree').tree('remove', node.target);
					Reporting.clearAllTab();
				}
				ReportCommon.showMsg(data.msg);
			}, 'json');
		});
	}
};

Reporting.saveTreeNode = function() {
	var act = $("#treeNodeAction").val();
	var actUrl = act == "add" ? designerPageRootUrl + "/addtreenode" :designerPageRootUrl  + "/edittreenode";
	$('#setTreeNodeForm').form('submit', {
		url : actUrl,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			var result = $.parseJSON(data);
			if (result.success) {
				Reporting.updateTreeNode(act, result.data);
				$('#setTreeNodeDlg').dialog('close');
			}
			ReportCommon.showMsg(result.msg);
		}
	});
};

Reporting.saveChanged = function(data, handler) {
	var isChanged = $("#reportingIsChange").val() == 0 ? false : true;
	if (!isChanged) {
		return handler(data);
	}
	$.messager.confirm('确认', '是否保存修改的数据?', function(r) {
		if (r) {
			Reporting.save();
		}
		handler(data);
	});
};

Reporting.clickTreeNodeHandler = function(currNode) {
	Reporting.saveChanged(currNode, function(node) {
		var index = Reporting.getSelectedTabIndex();
		if (index > 1) {
			Reporting.selectTab(0);
		}
		Reporting.selectTreeNodeHandler(node);
	});
};

Reporting.selectTreeNodeHandler = function(node) {
	$('#reportingTree').tree('options').url = designerPageRootUrl + 'listchildnodes';
	$('#reportingTree').tree('expand', node.target);

	Reporting.clearAllTab();
	if (node.attributes.flag != 1) {
		$('#reportingAction').val('add');
		$('#reportingPid').val(node.id);
		Reporting.toggleButton('add');
	} else {
		Reporting.loadAllTabData();
	}
};

Reporting.treeNodeOnDrop = function(target, source, point) {
	var targetNode = $('#reportingTree').tree('getNode', target);
	if (targetNode) {
		$.post(designerPageRootUrl + 'dragtreenode', {
			sourceId : source.id,
			targetId : targetNode.id,
			sourcePid : source.attributes.pid
		}, function(data) {
			if (!data.success) {
				$.messager.alert('失败', data.msg, 'error');
			}
		});
	}
};

Reporting.copyTreeNode = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		$('#copyNodeId').val(node.id);
	} else {
		$.messager.alert('错误', '您没有选择任何节点');
	}
};

Reporting.pasteTreeNode = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		$.post(designerPageRootUrl + 'pastetreenode', {
			sourceId : $('#copyNodeId').val(),
			targetId : node.id
		}, function(result) {
			if (result.success) {
				$('#copyNodeId').val(0);
				return Reporting.updateTreeNode('add', result.data);
			}
			return $.messager.alert('错误', result.msg);
		}, 'json');
	} else {
		$.messager.alert('错误', '您没有选择任何节点');
	}
};

Reporting.selectTreeNode = function(id) {
	var node = $('#reportingTree').tree('find', id);
	if (node) {
		$('#reportingTree').tree('select', node.target);
	}
};

Reporting.getTreeNodeById = function(id) {
	return $('#reportingTree').tree('find', id);
};

Reporting.selectAndExpandTreeNode = function(id) {
	var node = $('#reportingTree').tree('find', id);
	if (node) {
		$('#reportingTree').tree('select', node.target);
		Reporting.selectTreeNodeHandler(node);
		var parentNode = $('#reportingTree').tree('getParent', node.target);
		if (parentNode) {
			$('#reportingTree').tree('expand', parentNode.target);
		}
	}
};

Reporting.updateTreeNode = function(act, nodeData) {
	if (nodeData == null) {
		return;
	}
	if (act == "add") {
		var newNode = nodeData[0];
		var pid = newNode.attributes.pid;
		// 如果是增加根节点
		if (pid == "0") {
			var roots = $('#reportingTree').tree('getRoots');
			if (roots && roots.length > 0) {
				$('#reportingTree').tree('insert', {
					after : roots[roots.length - 1].target,
					data : newNode
				});
			} else {
				$('#reportingTree').tree('reload');
			}
		} else {
			var parentNode = Reporting.getTreeNodeById(pid);
			$('#reportingTree').tree('append', {
				parent : parentNode.target,
				data : nodeData
			});
		}
		Reporting.selectTreeNode(newNode.id);
		var currNode = Reporting.getSelectedTreeNode();
		return currNode ? Reporting.selectTreeNodeHandler(currNode) : null;
	}
	if (act == "edit") {
		var node = Reporting.getTreeNodeById(nodeData.id);
		nodeData["target"] = node.target;
		$('#reportingTree').tree('update', nodeData);
		return Reporting.loadDataToPropertiesTab(nodeData.attributes);
	}
};

Reporting.getSelectedTreeNode = function() {
	return $('#reportingTree').tree('getSelected');
};

Reporting.reloadTree = function() {
	$('#reportingTree').tree('reload');
};

Reporting.openSearchReportDlg = function() {
	$('#searchReportDlg').dialog('open');
	ReportCommon.clearDataGrid('#searchReportGrid');
};

Reporting.search = function() {
	var fieldName = $('#searchReportFieldName').val();
	var keyword = $('#searchReportKeyword').val();
	var url = designerPageRootUrl + 'search?fieldName=' + fieldName + '&keyword=' + keyword;
	return ReportCommon.loadDataToGrid('#searchReportGrid', url);
};

Reporting.treeNodePathIds = [];
Reporting.locateTreeNode = function(index, row) {
	Reporting.treeNodePathIds = row.path.split(',');
	var ids = row.path.split(',');
	for ( var i = 0; i < ids.length; i++) {
		var id = ids[i];
		var node = Reporting.getTreeNodeById(id);
		if (node) {
			Reporting.treeNodePathIds.shift();
		}
		Reporting.selectAndExpandTreeNode(id);
	}
};

//
// 报表配置相关操作
//
Reporting.add = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		$('#reportingAction').val('add');
		Reporting.clearAllTab();
		Reporting.selectTab(0);
		$('#reportingId').val(0);
		$('#reportingPid').val(node.id);
		Reporting.toggleButton('add');
		Reporting.clearCodeMirrorEditor();
	} else {
		$.messager.alert('提示', "请选择一个节点", 'info');
	}
};

Reporting.edit = function(data) {
	$('#reportingAction').val('edit');
	Reporting.toggleButton('edit');
	$('#reportingForm').form('load', data);
	$("#sqlColumnGrid").datagrid('loadData', eval(data.metaColumns));
	exceSqlCmEditor.setValue(data.sqlText);
	$('#reportingIsChange').val(0);
	
};

Reporting.toggleButton = function(action) {
	$('#reportingNewBtn').linkbutton(action == 'add' ? 'enable' : 'disable');
	$('#reportingPowerBtn').linkbutton(action == 'add' ? 'disable' : 'enable');
	$('#reportingContactsBtn').linkbutton(action == 'add' ? 'disable' : 'enable');
	$('#reportingEditBtn').linkbutton(action == 'add' ? 'disable' : 'enable');
	$('#reportingPreviewBtn').linkbutton(action == 'add' ? 'disable' : 'enable');
};

Reporting.clearCodeMirrorEditor = function() {
	exceSqlCmEditor.setValue("");
	viewSqlCmEditor.setValue("");
	viewSqlHistoryCmEditor.setValue("");
};

Reporting.viewSqlText = function() {
	var dsId = $('#reportingDsId').combobox('getValue');
	var dataRange = $('#reportingDataRange').val();
	var sqlText = exceSqlCmEditor.getValue();

	if (dsId == "") {
		return $.messager.alert('失败', "请选择正确的数据源！", 'error');
	}
	if (sqlText == "") {
		return $.messager.alert('失败', "未发现操作的SQL语句！", 'error');
	}

	$.messager.progress({
		title : '请稍后...',
		text : '正在生成预览SQL...',
	});

	var queryParamRows = $("#queryParamGrid").datagrid('getRows');
	var jsonQueryParams = queryParamRows ? JSON.stringify(queryParamRows) : "";
	$.post(designerPageRootUrl + 'viewsqltext', {
		dsId : dsId,
		sqlText : sqlText,
		dataRange : dataRange,
		jsonQueryParams : jsonQueryParams
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			$('#viewSqlTextDlg').dialog('open');
			return viewSqlCmEditor.setValue(result.data);
		}
		return $.messager.alert('错误', result.msg);
	}, 'json');
};

Reporting.viewSqlHistory = function() {
	$('#viewSqlTextHistoryDlg').dialog('open');
	var reportId = $("#reportingId").val();
	var url = designerPageRootUrl + 'getallhistorysql?reportId=' + reportId;
	ReportCommon.loadDataToGrid('#viewSqlTextHistoryGrid', url);
	viewSqlHistoryCmEditor.setValue("");
	viewSqlHistoryCmEditor.refresh();
};

Reporting.showHistorySqlText = function(index, row) {
	viewSqlHistoryCmEditor.setValue(row.sqlText);
};

// 报表sql列配置相关操作
Reporting.executeSql = function() {
	if (!$("#reportingForm").valid()) {
		return;
	}
	var sqlText = exceSqlCmEditor.getValue();
	if (sqlText == "") {
		return $.messager.alert('失败', "未发现操作的SQL语句！", 'error');
	}

	$.messager.progress({
		title : '请稍后...',
		text : '数据正在加载中...',
	});

	var queryParamRows = $("#queryParamGrid").datagrid('getRows');
	var jsonQueryParams = queryParamRows ? JSON.stringify(queryParamRows) : "";
	$.post(designerPageRootUrl + 'loadsqlcolumns', {
		sqlText : sqlText,
		dsId : $('#reportingDsId').combobox('getValue'),
		jsonQueryParams : jsonQueryParams
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			$("#sqlColumnGrid").datagrid('clearChecked');
			return Reporting.loadSqlColumns(result.data);
		}
		return $.messager.alert('错误', result.msg);
	}, 'json');
};

Reporting.loadSqlColumns = function(data) {
	var sqlColumnRows = $("#sqlColumnGrid").datagrid('getRows');
	if (sqlColumnRows == null || sqlColumnRows.length == 0) {
		return $("#sqlColumnGrid").datagrid('loadData', data);
	}

	Reporting.setSqlColumnRows(sqlColumnRows);
	var oldColumnMap = {};
	for ( var i = 0; i < sqlColumnRows.length; i++) {
		var name = sqlColumnRows[i].name;
		oldColumnMap[name] = sqlColumnRows[i];
	}
	for ( var i = 0; i < data.length; i++) {
		var name = data[i].name;
		if (oldColumnMap[name]) {
			oldColumnMap[name].dataType = data[i].dataType;
			oldColumnMap[name].width = data[i].width;
			data[i] = oldColumnMap[name];
		}
	}
	return $("#sqlColumnGrid").datagrid('loadData', data);
};

Reporting.setSqlColumnRows = function(rows) {
	for ( var rowIndex = 0; rowIndex < rows.length; rowIndex++) {
		var row = rows[rowIndex];
		var subOptions = Reporting.getSqlColumnOptions(row.type);
		for ( var optIndex = 0; optIndex < subOptions.length; optIndex++) {
			var option = subOptions[optIndex];
			var optionId = "#" + option.name + rowIndex;
			row[option.name] = $(optionId).prop("checked");
		}
		row["name"] = $("#name" + rowIndex).val();
		row["text"] = $("#text" + rowIndex).val();
		row["type"] = $("#type" + rowIndex).val();
		row["sortType"] = $("#sortType" + rowIndex).val();
		row["decimals"] = $("#decimals" + rowIndex).val();
	}
	return rows;
};

Reporting.getSqlColumnOptions = function(type) {
	var subOptions = [];
	if (type == 4) {
		subOptions = $.grep(configOptions, function(option, i) {
			return option.type == 1;
		});
	} else if (type == 3) {
		subOptions = $.grep(configOptions, function(option, i) {
			return option.type == 1 || option.type == 2;
		});
	} else {
		subOptions = $.grep(configOptions, function(option, i) {
			return option.type == 3;
		});
	}
	return subOptions;
};

Reporting.upSqlColumnRow = function() {
	var grid = $("#sqlColumnGrid");
	var row = grid.datagrid('getSelected');
	var index = grid.datagrid('getRowIndex', row);
	Reporting.resortSqlColumnGrid(index, 'up', grid);
};

Reporting.downSqlColumnRow = function() {
	var grid = $("#sqlColumnGrid");
	var row = grid.datagrid('getSelected');
	var index = grid.datagrid('getRowIndex', row);
	Reporting.resortSqlColumnGrid(index, 'down', grid);
};

Reporting.resortSqlColumnGrid = function(index, type, grid) {
	var maxIndex = grid.datagrid('getRows').length - 1;
	var moveIndex = ("up" == type) ? index - 1 : index + 1;
	if (moveIndex >= 0 && moveIndex <= maxIndex) {
		var currRow = grid.datagrid('getData').rows[index];
		var moveRow = grid.datagrid('getData').rows[moveIndex];
		grid.datagrid('getData').rows[index] = moveRow;
		grid.datagrid('getData').rows[moveIndex] = currRow;
		grid.datagrid('refreshRow', index);
		grid.datagrid('refreshRow', moveIndex);
		grid.datagrid('selectRow', moveIndex);
	}
};

Reporting.addSqlColumnRow = function() {
	var index = 1;
	var rows = $("#sqlColumnGrid").datagrid('getRows');
	if (rows && rows.length > 0) {
		for ( var i = 0; i < rows.length; i++) {
			var type = $("#type" + i).val();
			if (type == 4) {
				index++;
			}
		}
	}

	$.post(designerPageRootUrl + 'getsqlcolumn', function(row) {
		row.name = row.name + index;
		row.text = row.name;
		$('#sqlColumnGrid').datagrid('appendRow', row);
	}, 'json');
};

Reporting.deleteSqlColumnRow = function() {
	var row = $("#sqlColumnGrid").datagrid('getSelected');
	if (row) {
		var index = $("#sqlColumnGrid").datagrid('getRowIndex', row);
		$("#sqlColumnGrid").datagrid('deleteRow', index);
		$("#sqlColumnGrid").datagrid('loadData', $("#sqlColumnGrid").datagrid('getRows'));
	}
};

Reporting.expressionSettings = function(id) {
	$('#expressionSettingsDlg').dialog('open');
	var index = id.replace("expression", "");
	$("#sqlColumnGrid").datagrid('selectRow', index);
	var row = $("#sqlColumnGrid").datagrid('getSelected');
	$("#sqlColumnExpression").val(row.expression);
};

Reporting.saveExpression = function() {
	var row = $("#sqlColumnGrid").datagrid('getSelected');
	row.expression = $("#sqlColumnExpression").val();
	$("#expressionSettingsDlg").dialog('close');
};

Reporting.getEmptyExprColumns = function(sqlColumnRows) {
	var emptyColumns = [];
	for ( var i = 0; i < sqlColumnRows.length; i++) {
		var row = sqlColumnRows[i];
		if (row.type == 4 && $.trim(row.expression) == "") {
			emptyColumns.push(row.name);
		}
	}
	return emptyColumns;
};

Reporting.save = function() {
	if (!$("#reportingForm").valid()) {
		return;
	}

	var sqlColumnRows = $("#sqlColumnGrid").datagrid('getRows');
	if (sqlColumnRows == null || sqlColumnRows.length == 0) {
		return $.messager.alert('失败', "没有任何报表SQL配置列选项！", 'error');
	}

	Reporting.setSqlColumnRows(sqlColumnRows);
	var typeColumn = Reporting.getSqlColumnCountByType(sqlColumnRows);
	if (typeColumn.layout == 0 || typeColumn.stat == 0) {
		return $.messager.alert('失败', "您没有设置布局列或者统计列", 'error');
	}

	var emptyExprColumns = Reporting.getEmptyExprColumns(sqlColumnRows);
	if (emptyExprColumns && emptyExprColumns.length > 0) {
		return $.messager.alert('失败', "计算列：[" + emptyExprColumns.join() + "]没有设置表达式！", 'error');
	}

	$.messager.progress({
		title : '请稍后...',
		text : '正在处理中...',
	});

	var reportId = $("#reportingId").val();
	var act = $('#reportingAction').val();
	$.post(designerPageRootUrl + '' + act, {
		id : reportId,
		pid : $('#reportingPid').val(),
		dsId : $('#reportingDsId').combobox('getValue'),
		dataRange : $('#reportingDataRange').val(),
		name : $("#reportingName").val(),
		uid : $("#reportingUid").val(),
		layout : $("#reportingLayout").val(),
		sqlText : exceSqlCmEditor.getValue(),
		metaColumns : JSON.stringify(sqlColumnRows),
		status : $("#reportingStatus").val(),
		sequence : $("#reportingSequence").val(),
		isChange : $('#reportingIsChange').val() == 0 ? false : true
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			Reporting.updateTreeNode(act, result.data);
			$('#reportingIsChange').val(0);
		}
		$.messager.alert('操作提示', result.msg, result.success ? 'info' : 'error');
	}, 'json');
};

Reporting.getSqlColumnCountByType = function(rows) {
	var typeColumn = {
		"layout" : 0,
		"dim" : 0,
		"stat" : 0,
		"computed" : 0
	};
	for ( var i = 0; i < rows.length; i++) {
		if (rows[i].type == 1)
			typeColumn.layout += 1;
		else if (rows[i].type == 2)
			typeColumn.dim += 1;
		else if (rows[i].type == 3)
			typeColumn.stat += 1;
		else if (rows[i].type == 4)
			typeColumn.computed += 1;
	}
	return typeColumn;
};

//
// 以为报表查询条件参数配置相关函数
//
Reporting.loadDataToQueryParamTab = function(data) {
	$("#jsonQueryParams").val(data.queryParams);
	$("#queryParamReportId").val(data.id);
	var jsonQueryParams = $('#jsonQueryParams').val();
	if (jsonQueryParams == null || jsonQueryParams == "") {
		jsonQueryParams = {
			total : 0,
			rows : []
		};
	}
	$("#queryParamGrid").datagrid('loadData', eval(jsonQueryParams));
};

Reporting.saveQueryParam = function() {
	var id = $("#queryParamReportId").val();
	var queryParamRows = $("#queryParamGrid").datagrid('getRows');
	if (queryParamRows == null || queryParamRows.length == 0) {
		$('#jsonQueryParams').val("");
	} else {
		$('#jsonQueryParams').val(JSON.stringify(queryParamRows));
	}

	$.messager.progress({
		title : '请稍后...',
		text : '正在处理中...',
	});

	$.post(designerPageRootUrl + 'setqueryparam', {
		id : id,
		jsonQueryParams : $('#jsonQueryParams').val()
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			Reporting.updateTreeNode('edit', result.data);
		}
		$.messager.alert('操作提示', result.msg, result.success ? 'info' : 'error');
	}, 'json');
};

Reporting.editQueryParam = function(index, row) {
	$("#queryParamGridIndex").val(index);
	$("#queryParamContent").val('');
	$("#queryParamName").val(row.name);
	$("#queryParamText").val(row.text);
	$("#queryParamDefaultValue").val(row.defaultValue);
	$("#queryParamDefaultText").val(row.defaultText);
	$("#queryParamFormElement").val(row.formElement);
	$("#queryParamDataSource").val(row.dataSource);
	if (row.formElement == "select" || row.formElement == "selectMul") {
		$("#queryParamContent").removeAttr("disabled");
		$("#queryParamContent").val(row.content);
		return;
	}
	$("#queryParamContent").attr({
		"disabled" : "disabled"
	});
};

Reporting.deleteQueryParam = function(index) {
	$("#queryParamGrid").datagrid('deleteRow', index);
	$("#queryParamGrid").datagrid('reload', $("#queryParamGrid").datagrid('getRows'));
};

Reporting.setQueryParam = function(act) {
	if (!$("#queryParamForm").valid()) {
		return;
	}

	if (act == "add") {
		$('#queryParamGrid').datagrid('appendRow', $('#queryParamForm').serializeObject());
	} else if (act == "edit") {
		var index = $("#queryParamGridIndex").val();
		$('#queryParamGrid').datagrid('updateRow', {
			index : index,
			row : $('#queryParamForm').serializeObject()
		});
	}
	$('#queryParamForm').form('reset');
};

//
// 报表属性相关操作
//
Reporting.showProperties = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node) {
		$("#reportingProperties").dialog('open');
		Reporting.clearPropertiesTab();
		Reporting.loadDataToPropertiesTab(node.attributes);
	} else {
		$.messager.alert('失败', "您没有选择任何树节点", 'error');
	}
};

Reporting.loadDataToPropertiesTab = function(data) {
	for ( var propName in data) {
		var id = "#reportingProp_" + propName;
		var value = Reporting.getPropertyValue(propName, data);
		$(id).html(value);
	}
};

Reporting.clearPropertiesTab = function() {
	$('#reportingProperties label').each(function(i) {
		$(this).html("");
	});
};

Reporting.getPropertyValue = function(name, object) {
	var value = object[name];
	if (name == "dsId") {
		return Reporting.getDatasource(value);
	}
	if (name == "flag") {
		return Reporting.getFlagName(value);
	}
	if (name == "layout") {
		return Reporting.getLayoutName(value);
	}
	if (name == "hasChild") {
		return value ? "有" : "无";
	}
	if (name == "status") {
		return value == 1 ? "锁定" : "编辑";
	}
	return value;
};

//
// 报表预览相关操作
//
Reporting.loadDataToPreviewTab = function(data) {
	var previewUrl = XFrame.getContextPath() + "/report/uid/" + data.uid;
	var tab = $('#tabs').tabs('getSelected');
	var index = $('#tabs').tabs('getTabIndex', tab);
	if (index != 5)
		return;

	$('#tabs').tabs('update', {
		tab : tab,
		options : {
			href : previewUrl
		}
	});
};

Reporting.previewInNewTab = function() {
	var node = Reporting.getSelectedTreeNode();
	if (node.attributes.flag == 1) {
		var suffix = (node.attributes.path.indexOf('528') != -1) ? "[国内]" : "";
		var title = node.attributes.name + suffix;
		if ($('#tabs').tabs('exists', title)) {
			$('#tabs').tabs('select', title);
			var tab = $('#tabs').tabs('getSelected');
			return tab.panel('refresh');
		}
		var previewUrl = XFrame.getContextPath() + "/report/uid/" + node.attributes.uid;
		$('#tabs').tabs(
				'add',
				{
					id : node.id,
					title : title,
					content : '<iframe scrolling="yes" frameborder="0" src="' + previewUrl
							+ '" style="width:100%;height:100%;"></iframe>',
					closable : true
				});
	}
};

//
// 以下为formatter方法
//
Reporting.queryParamOptionFormatter = function(value, row, index) {
	var path = XFrame.getContextPath() + '/static/report/icons/';
	return '<a href="#" title ="删除" onclick="javascript:Reporting.deleteQueryParam(' + index + ')"><img src="' + path
			+ 'remove.png" alt="删除"/"></a>';
};

Reporting.optionsFormatter = function(value, row, index) {
	var subOptions = [];

	if (row.type == 4) {
		subOptions = $.grep(configOptions, function(option, i) {
			return option.type == 1 || option.type == 4;
		});
	} else if (row.type == 3) {
		subOptions = $.grep(configOptions, function(option, i) {
			return option.type == 1 || option.type == 2;
		});
	} else {
		subOptions = $.grep(configOptions, function(option, i) {
			return option.type == 3;
		});
	}

	var htmlOptions = [];
	for ( var i = 0; i < subOptions.length; i++) {
		var name = subOptions[i].name;
		var id = name + index;
		var text = subOptions[i].text;
		var checked = row[name] ? " checked=\"checked\"" : "";
		var html = "";
		if (name == "expression") {
			var imgSrc = XFrame.getContextPath() + "/static/report/icons/formula.png";
			var onClick = " onclick =\"Reporting.expressionSettings('" + id + "')\"";
			html = "<img style=\"cursor: pointer;\" id=\"" + id + "\" title=\"" + text + "\" src=\"" + imgSrc + "\""
					+ onClick + "/>";
		} else {
			html = "<input type=\"checkbox\" id=\"" + id + "\" name=\"" + name + "\"" + checked + ">" + text
					+ "</input>";
		}
		htmlOptions.push(html);
	}
	return htmlOptions.join(" ");
};

Reporting.textFormatter = function(value, row, index) {
	var id = "text" + index;
	return "<input style=\"width:98%;\" type=\"text\" id=\"" + id + "\" name=\"text\" value=\"" + row.text + "\" />";
};

Reporting.sqlColumnNameFormatter = function(value, row, index) {
	var id = "name" + index;
	return "<input style=\"width:98%;\" type=\"text\" id=\"" + id + "\" name=\"name\" value=\"" + row.name + "\" />";
};

Reporting.decimalsFormatter = function(value, row, index) {
	var id = "decimals" + index;
	if (!row.decimals) {
		row.decimals = 0;
	}
	return "<input style=\"width:42px;\" type=\"text\" id=\"" + id + "\" name=\"text\" value=\"" + row.decimals
			+ "\" />";
};

Reporting.expressionFormatter = function(value, row, index) {
	if (!row.expression) {
		row.expression = '';
	}
	return row.expression;
};

Reporting.layoutFormatter = function(value, row, index) {
	return Reporting.getLayoutName(row.layout);
};

Reporting.typeFormatter = function(value, row, index) {
	var options = [ {
		text : "布局列",
		value : 1
	}, {
		text : "维度列",
		value : 2
	}, {
		text : "统计列",
		value : 3
	}, {
		text : "计算列",
		value : 4
	} ];

	var id = "type" + index;
	var count = options.length;
	var selectHtmlText = "<select id=\"" + id + "\" name=\"type\">";
	for ( var i = 0; i < count; i++) {
		var selected = options[i].value == value ? 'selected="selected"' : '';
		selectHtmlText += "<option value=\"" + options[i].value + "\" " + selected + ">" + options[i].text
				+ "</option>";
	}
	selectHtmlText += "</select>";

	return selectHtmlText;
};

Reporting.sortTypeFormatter = function(value, row, index) {
	var options = [ {
		text : "默认",
		value : 0
	}, {
		text : "数字优先升序",
		value : 1
	}, {
		text : "数字优先降序",
		value : 2
	}, {
		text : "字符优先升序",
		value : 3
	}, {
		text : "字符优先降序",
		value : 4
	} ];

	var id = "sortType" + index;
	var count = options.length;
	var selectHtmlText = "<select id=\"" + id + "\" name=\"sortType\">";
	for ( var i = 0; i < count; i++) {
		var selected = options[i].value == row.sortType ? 'selected="selected"' : '';
		selectHtmlText += "<option value=\"" + options[i].value + "\" " + selected + ">" + options[i].text
				+ "</option>";
	}
	selectHtmlText += "</select>";
	return selectHtmlText;
};

//
// utils函数
//
Reporting.getFlagName = function(flag) {
	if (flag == 0) {
		return "树节点";
	}
	if (flag == 1) {
		return "报表节点";
	}
	return "其他";
};

Reporting.getLayoutName = function(layout) {
	if (layout == 1) {
		return "横向布局";
	}
	if (layout == 2) {
		return "纵向布局";
	}
	return "无";
};

Reporting.getDatasource = function(dsId) {
	var dataSources = $('#reportingDsId').combobox('getData');
	var currDs = null;

	for ( var i = 0; i < dataSources.length; i++) {
		if (dataSources[i].id == dsId) {
			currDs = dataSources[i];
			break;
		}
	}

	if (currDs == null) {
		return dsId;
	}
	return "<p>UID：" + currDs.uid + "</p><p>名称：" + currDs.name + "</p><p>用户名：" + currDs.user + "</p><p>密码："
			+ currDs.password + "</p><p>数据库连接字符串：" + currDs.jdbcUrl + "</p>";
};

function showChart(title, id, uid) {
	if ($('#tabs').tabs('exists', title)) {
		$('#tabs').tabs('select', title);
		var tab = $('#tabs').tabs('getSelected');
		return tab.panel('refresh');
	}
	var url = XFrame.getContextPath() + '/chart/index?rpUid=' + uid;
	$('#tabs').tabs(
			'add',
			{
				id : id,
				title : title,
				content : '<iframe scrolling="yes" frameborder="0" src="' + url
						+ '" style="width:100%;height:100%;"></iframe>',
				closable : true
			});
}