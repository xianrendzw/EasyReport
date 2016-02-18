var designerPageRootUrl = WebAppRequest.getContextPath() + '/report/designer/';

$(function() {
	editing = undefined;

	sqlEditor = CodeMirror.fromTextArea(document.getElementById("reportSqlText"), {
		mode : 'text/x-mysql',
		theme : 'rubyblue',
		indentWithTabs : true,
		smartIndent : true,
		lineNumbers : true,
		styleActiveLine : true,
		matchBrackets : true,
		autofocus : true,
		extraKeys : {
			"F11" : function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc" : function(cm) {
				if (cm.getOption("fullScreen")) {
					cm.setOption("fullScreen", false);
				}
			},
			"Tab" : "autocomplete"
		}
	});
	sqlEditor.on("change", function(cm, obj) {
		if (obj.origin == "setValue") {
			$('#reportIsChange').val(0);
		} else {
			$('#reportIsChange').val(1);
		}
	});

	ReportDesigner.setSqlEditorStatus();

	viewSqlEditor = CodeMirror.fromTextArea(document.getElementById("viewSqlText"), {
		mode : 'text/x-mysql',
		theme : 'rubyblue',
		indentWithTabs : true,
		smartIndent : true,
		lineNumbers : true,
		matchBrackets : true,
		autofocus : true
	});

	viewHistorySqlEditor = CodeMirror.fromTextArea(document.getElementById("viewSqlTextHistory"), {
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
				if (cm.getOption("fullScreen")) {
					cm.setOption("fullScreen", false);
				}
			}
		}
	});

	sqlColumnOptions = [ {
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
	}, /*{
		name : "footings",
		text : "合计",
		type : 1
	}, {
		name : "extensions",
		text : "小计",
		type : 3
	},*/ {
		name : "expression",
		text : "表达式",
		type : 4
	}, {
		name : "comment",
		text : "备注",
		type : 2
	}, {
		name : "format",
		text : "格式",
		type : 2
	} ];

	sqlColumnTypeOptions = [ {
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

	sqlColumnSortTypeOptions = [ {
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

	ReportDesigner.init();

	$('#west').panel({
		tools : [ {
			iconCls : 'icon-search',
			handler : ReportDesigner.openSearchReportDlg
		}, {
			iconCls : 'icon-add',
			handler : ReportDesigner.addRootTreeNode
		}, {
			iconCls : 'icon-reload',
			handler : ReportDesigner.reloadTree
		} ]
	});

	$('#reportTree').tree({
		checkbox : false,
		method : 'get',
		animate : true,
		dnd : true,
		url : designerPageRootUrl + 'listChildNodes',
		onLoadSuccess : function(node, data) {
			if (ReportDesigner.treeNodePathIds && ReportDesigner.treeNodePathIds.length > 0) {
				var id = ReportDesigner.treeNodePathIds.shift();
				ReportDesigner.selectAndExpandTreeNode(id);
			}
		},
		onClick : ReportDesigner.clickTreeNodeHandler,
		onDrop : ReportDesigner.treeNodeOnDrop,
		onDblClick : function(node) {
			if (node.attributes.flag == 1) {
				ReportDesigner.previewInNewTab();
			}
		},
		onContextMenu : function(e, node) {
			e.preventDefault();
			$('#reportTree').tree('select', node.target);
			var copyNodeId = $('#copyNodeId').val();
			var item = $('#reportTreeCtxMenu').menu('findItem', '粘贴');
			$('#reportTreeCtxMenu').menu(copyNodeId == 0 ? 'disableItem' : 'enableItem', item.target);
			$('#reportTreeCtxMenu').menu('show', {
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
			handler : function() {
				var grid = $("#sqlColumnGrid");
				var row = grid.datagrid('getSelected');
				var index = grid.datagrid('getRowIndex', row);
				ReportDesigner.resortDatagrid(index, 'up', grid);
			}
		}, '-', {
			iconCls : 'icon-down',
			handler : function() {
				var grid = $("#sqlColumnGrid");
				var row = grid.datagrid('getSelected');
				var index = grid.datagrid('getRowIndex', row);
				ReportDesigner.resortDatagrid(index, 'down', grid);
			}
		}, '-', {
			iconCls : 'icon-add',
			handler : function() {
				var index = 1;
				var rows = $("#sqlColumnGrid").datagrid('getRows');
				if (rows && rows.length > 0) {
					for (var i = 0; i < rows.length; i++) {
						var type = $("#type" + i).val();
						if (type == 4) {
							index++;
						}
					}
				}
				$.post(designerPageRootUrl + 'getSqlColumn', function(row) {
					row.name = row.name + index;
					row.text = row.name;
					row.type = 4;
					row.sortType = 0;
					$('#sqlColumnGrid').datagrid('appendRow', row);
				}, 'json');
			}
		}, '-', {
			iconCls : 'icon-cancel',
			handler : function() {
				var row = $("#sqlColumnGrid").datagrid('getSelected');
				if (row) {
					var index = $("#sqlColumnGrid").datagrid('getRowIndex', row);
					$("#sqlColumnGrid").datagrid('deleteRow', index);
					$("#sqlColumnGrid").datagrid('loadData', $("#sqlColumnGrid").datagrid('getRows'));
				}
			}
		} ],
		columns : [ [
				{
					field : 'name',
					title : '列名',
					width : 100,
					formatter : function(value, row, index) {
						var id = "name" + index;
						var tmpl = '<input style="width:98%;" type="text" id="{{id}}" name="name" value="{{value}}" />';
						return template.compile(tmpl)({
							id : id,
							value : row.name
						});
					}
				},
				{
					field : 'text',
					title : '标题',
					width : 100,
					formatter : function(value, row, index) {
						var id = "text" + index;
						var tmpl = '<input style="width:98%;" type="text" id="{{id}}" name="text" value="{{value}}" />';
						return template.compile(tmpl)({
							id : id,
							value : row.text
						});
					}
				},
				{
					field : 'type',
					title : '类型',
					width : 75,
					formatter : function(value, row, index) {
						var id = "type" + index;
						var tmpl = '\
								<select id="{{id}}" name=\"type\">\
								{{each list}}\
									<option value="{{$value.value}}" {{if $value.value == currValue}}selected{{/if}}>{{$value.text}}</option>\
								{{/each}}\
								</select>';
						return template.compile(tmpl)({
							id : id,
							currValue : value,
							list : sqlColumnTypeOptions
						});
					}
				},
				{
					field : 'dataType',
					title : '数据类型',
					width : 75
				},
				{
					field : 'width',
					title : '宽度',
					width : 40
				},
				{
					field : 'decimals',
					title : '精度',
					width : 50,
					formatter : function(value, row, index) {
						var id = "decimals" + index;
						if (!row.decimals) {
							row.decimals = 0;
						}
						var tmpl = '<input style="width:42px;" type="text" id="{{id}}" name="decimals" value="{{value}}" />';
						return template.compile(tmpl)({
							id : id,
							value : row.decimals
						});
					}
				},
				{
					field : 'sortType',
					title : '排序类型',
					width : 100,
					formatter : function(value, row, index) {
						var id = "sortType" + index;
						var tmpl = '\
								<select id="{{id}}" name=\"sortType\">\
								{{each list}}\
									<option value="{{$value.value}}" {{if $value.value == currValue}}selected{{/if}}>{{$value.text}}</option>\
								{{/each}}\
								</select>';
						return template.compile(tmpl)({
							id : id,
							currValue : value,
							list : sqlColumnSortTypeOptions 
						});
					}
				}, {
					field : 'options',
					title : '配置',
					width : 300,
					formatter : function(value, row, index) {
						var subOptions = [];
						// 4:计算列,3:统计列,2:维度列,1:布局列
						if (row.type == 4) {
							subOptions = $.grep(sqlColumnOptions, function(option, i) {
								return option.type == 1 || option.type == 2 || option.type == 4;
							});
						} else if (row.type == 3) {
							subOptions = $.grep(sqlColumnOptions, function(option, i) {
								return option.type == 1 || option.type == 2;
							});
						} else {
							subOptions = $.grep(sqlColumnOptions, function(option, i) {
								return option.type == 2 || option.type == 3;
							});
						}

						var htmlOptions = [];
						for (var i = 0; i < subOptions.length; i++) {
							var name = subOptions[i].name;
							var data = {
								id : name + index,
								name : name,
								text : subOptions[i].text,
								checked : row[name] ? " checked=\"checked\"" : "",
								imgSrc : "",
								onClick : ""
							};
							var tmpl = "";
							if (name == "expression" || name == "comment" || name == "format") {
								data.imgSrc = WebAppRequest.getContextPath() + "/assets/modules/report/icons/" + name + ".png";
								data.onClick = "ReportDesigner.showSqlColumnGridOptionDialog('" + index + "','"+ name +"')";
								tmpl = '<img style="cursor: pointer;" id="{{id}}" title="{{text}}" src="{{imgSrc}}" onclick="{{onClick}}" />';
							} else {
								tmpl = '<input type="checkbox" id="{{id}}" name="{{name}}" {{checked}}>{{text}}</input>'
							}
							htmlOptions.push(template.compile(tmpl)(data));
						}
						return htmlOptions.join(" ");
					}
				} ] ]
	});

	$('#queryParamGrid').datagrid({
		method : 'get',
		fit : true,
		singleSelect : true,
		rownumbers : true,
		tools : [ {
			iconCls : 'icon-up',
			handler : function() {
				var grid = $("#queryParamGrid");
				var row = grid.datagrid('getSelected');
				var index = grid.datagrid('getRowIndex', row);
				ReportDesigner.resortDatagrid(index, 'up', grid);
			}
		}, '-', {
			iconCls : 'icon-down',
			handler : function() {
				var grid = $("#queryParamGrid");
				var row = grid.datagrid('getSelected');
				var index = grid.datagrid('getRowIndex', row);
				ReportDesigner.resortDatagrid(index, 'down', grid);
			}
		} ],
		columns : [ [ {
			field : 'name',
			title : '参数名',
			width : 100
		}, {
			field : 'text',
			title : '标题',
			width : 100
		}, {
			field : 'defaultValue',
			title : '默认值',
			width : 100
		}, {
			field : 'defaultText',
			title : '默认标题',
			width : 100
		}, {
			field : 'formElement',
			title : '表单控件',
			width : 100
		}, {
			field : 'dataSource',
			title : '来源类型',
			width : 100,
			formatter : function(value, row, index) {
				if (value == "sql") {
					return "SQL语句";
				}
				if (value == "text") {
					return "文本字符串";
				}
				return "无内容";
			}
		}, {
			field : 'dataType',
			title : '数据类型',
			width : 100
		}, {
			field : 'width',
			title : '数据长度',
			width : 100
		}, {
			field : 'required',
			title : '是否必选',
			width : 80
		}, {
			field : 'autoComplete',
			title : '是否自动提示',
			width : 80
		}, {
			field : 'options',
			title : '操作',
			width : 50,
			formatter : function(value, row, index) {
				var imgPath = WebAppRequest.getContextPath() + '/assets/modules/report/icons/remove.png';
				var tmpl = '<a href="#" title ="删除" onclick="ReportDesigner.deleteQueryParam(\'{{index}}\')"><img src="{{imgPath}}" alt="删除"/"></a>';
				return template.compile(tmpl)({
					index : index,
					imgPath : imgPath
				});
			}
		} ] ],
		onDblClickRow : function(index, row) {
			$('#queryParamForm').autofill(row);
			$("#queryParamIsRequired").prop("checked",row.required);
			$("#queryParamIsAutoComplete").prop("checked",row.autoComplete);
			$("#queryParamGridIndex").val(index);
		}
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
		onDblClickRow : function(index, row) {
			ReportDesigner.treeNodePathIds = row.path.split(',');
			var ids = row.path.split(',');
			for (var i = 0; i < ids.length; i++) {
				var id = ids[i];
				var node = ReportDesigner.getTreeNodeById(id);
				if (node) {
					ReportDesigner.treeNodePathIds.shift();
				}
				ReportDesigner.selectAndExpandTreeNode(id);
			}
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
			width : 80
		} ] ],
		onDblClickRow : function(index, row) {
			viewHistorySqlEditor.setValue(row.sqlText);
		}
	});

	$('#tabs').tabs({
		onContextMenu : function(e, title, index) {
			e.preventDefault();
			$('#tabsCtxMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		},
		onSelect : ReportDesigner.tabSelectedHandlder
	});

	$('#settingsDlg').dialog({
		title : "报表属性",
		closed : true,
		modal : true,
		top:150,
		left:150,
		width : window.screen.width - 300,
		height : window.screen.height - 300,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#settingsDlg").dialog('close');
			}
		} ]
	});

	$('#setTreeNodeDlg').dialog({
		closed : true,
		modal : true,
		top:(screen.height-500)/2,
		left:(screen.width-360)/2,
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
			handler : ReportDesigner.saveTreeNode
		} ]
	});
	//$("#setTreeNodeDlg").panel("move",{top:$(document).scrollTop() + ($(window).height()-250) * 0.5});

	$('#viewSqlTextDlg').dialog({
		closed : true,
		modal : true,
		maximizable : true,
		top:150,
		left:150,
		width : window.screen.width - 500,
		height : window.screen.height - 300,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#viewSqlTextDlg").dialog('close');
			}
		} ]
	});

	$('#viewHistorySqlTextDlg').dialog({
		closed : true,
		modal : true,
		maximizable : true,
		top:200/2,
		left:300/2,
		width : window.screen.width - 200,
		height : window.screen.height - 300,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#viewHistorySqlTextDlg").dialog('close');
			}
		} ]
	});

	$('#searchReportDlg').dialog({
		closed : true,
		modal : true,
		top:(screen.height-600)/2,
		left:(screen.width-400)/2,
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

	$('#columnExpressionDlg').dialog({
		closed : true,
		modal : true,
		top:(screen.height-500)/2,
		left:(screen.width-300)/2,
		width : 500,
		height : 300,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#columnExpressionDlg").dialog('close');
			}
		}, {
			text : '保存',
			iconCls : 'icon-save',
			handler : ReportDesigner.saveSqlColumnGridOptionDialog
		} ]
	});

	$('#columnCommentDlg').dialog({
		closed : true,
		modal : true,
		top:(screen.height-600)/2,
		left:(screen.width-400)/2,
		width : 600,
		height : 400,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#columnCommentDlg").dialog('close');
			}
		}, {
			text : '保存',
			iconCls : 'icon-save',
			handler : ReportDesigner.saveSqlColumnGridOptionDialog
		} ]
	});

	$('#columnFormatDlg').dialog({
		closed : true,
		modal : true,
		top:(screen.height-600)/2,
		left:(screen.width-400)/2,
		width : 600,
		height : 400,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#columnFormatDlg").dialog('close');
			}
		}, {
			text : '保存',
			iconCls : 'icon-save',
			handler : ReportDesigner.saveSqlColumnGridOptionDialog
		} ]
	});
});

var ReportDesigner = function() {
};


ReportDesigner.SqlColumnGridOptionDialogName = "";
ReportDesigner.treeNodePathIds = [];

ReportDesigner.init = function() {
	$('#btnEditReport').linkbutton('disable');
	$('#btnViewReport').linkbutton('disable');
	$('#queryParamFormElement').change(ReportDesigner.queryParamFormElementChange);
	$('#reportStatus').change(function() {
		return ReportDesigner.setSqlEditorStatus();
	});
	ReportDesigner.loadDataSource();
};

ReportDesigner.loadDataSource = function() {
	$('#reportDsId').combobox({
		url : WebAppRequest.getContextPath() + '/report/ds/list',
		valueField : 'id',
		textField : 'name',
		editable : false
	});
};

//
// tabs相关操作
//
ReportDesigner.getSelectedTabIndex = function() {
	var tab = $('#tabs').tabs('getSelected');
	return $('#tabs').tabs('getTabIndex', tab);
};

ReportDesigner.selectTab = function(index) {
	$('#tabs').tabs('select', index);
};

ReportDesigner.tabSelectedHandlder = function(title, currIndex) {
	ReportDesigner.saveChanged(currIndex, function(index) {
		if (index == 0) {
			sqlEditor.refresh();
			viewSqlEditor.refresh();
			return viewHistorySqlEditor.refresh();
		}
		if (index > 1) {
			var tab = $('#tabs').tabs('getTab', index);
			var id = tab.panel('options').id;
			ReportDesigner.selectAndExpandTreeNode(id);
		}
	});
};

ReportDesigner.loadDataToTab = function(index) {
	// 非固定tab
	if (index > 1) {
		return;
	}
	var node = ReportDesigner.getSelectedTreeNode();
	if (node) {
		// 报表配置
		if (index == 0) {
			return ReportDesigner.edit(node.attributes);
		}
		// 参数设置
		if (index == 1) {
			return ReportDesigner.loadDataToQueryParamTab(node.attributes);
		}
	}
};

ReportDesigner.clearTab = function(index) {
	if (index == 0) {
		ReportDesigner.clearAllEditor();
		ReportDesigner.resetSettingsForm();
		return ReportCommon.clearDataGrid('#sqlColumnGrid');
	}
	if (index == 1) {
		$('#queryParamForm').form('reset');
		return ReportCommon.clearDataGrid('#queryParamGrid');
	}
};

ReportDesigner.clearAllCheckedNode = function(target) {
	var nodes = $(target).tree('getChecked');
	if (nodes) {
		for (var i = 0; i < nodes.length; i++) {
			$(target).tree('uncheck', nodes[i].target);
		}
	}
};

ReportDesigner.clearAllTab = function() {
	ReportDesigner.clearTab(0);
	ReportDesigner.clearTab(1);
};

ReportDesigner.loadAllTabData = function() {
	ReportDesigner.loadDataToTab(0);
	ReportDesigner.loadDataToTab(1);
};

ReportDesigner.reloadSelectedTab = function() {
	var index = ReportDesigner.getSelectedTabIndex();
	ReportDesigner.clearTab(index);
	ReportDesigner.loadDataToTab(index);
};

ReportDesigner.tabContextMenu = function(item) {
	if (item.name == "current") {
		return ReportDesigner.closeCurrentTab();
	}
	if (item.name == "others") {
		return ReportDesigner.closeOthersTab();
	}
	if (item.name == "all") {
		return ReportDesigner.closeAllTab();
	}
	return;
};

ReportDesigner.closeCurrentTab = function() {
	var tab = $('#tabs').tabs('getSelected');
	var options = tab.panel('options');
	if (options.closable) {
		$('#tabs').tabs('close', tab.panel('options').title);
	}
};

ReportDesigner.closeOthersTab = function() {
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

ReportDesigner.closeAllTab = function() {
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
ReportDesigner.treeContextMenu = function(item) {
	if (item.name == "addRp") {
		return ReportDesigner.add();
	}
	if (item.name == "add") {
		return ReportDesigner.addChildTreeNode();
	}
	if (item.name == "edit") {
		return ReportDesigner.editTreeNode();
	}
	if (item.name == "remove") {
		return ReportDesigner.removeTreeNode();
	}
	if (item.name == "search") {
		return ReportDesigner.openSearchReportDlg();
	}
	if (item.name == "refresh") {
		return ReportDesigner.reloadTree();
	}
	if (item.name == "copy") {
		return ReportDesigner.copyTreeNode();
	}
	if (item.name == "paste") {
		return ReportDesigner.pasteTreeNode();
	}
	if (item.name == "info") {
		return ReportDesigner.showProperties();
	}
	return;
};

ReportDesigner.addRootTreeNode = function() {
	var data = {
		"id" : 0,
		"name" : "根节点"
	};
	ReportDesigner.addTreeNode(data);
};

ReportDesigner.addChildTreeNode = function() {
	var node = ReportDesigner.getSelectedTreeNode();
	if (node) {
		ReportDesigner.addTreeNode(node.attributes);
	}
};

ReportDesigner.addTreeNode = function(data) {
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

ReportDesigner.editTreeNode = function() {
	var node = ReportDesigner.getSelectedTreeNode();
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

ReportDesigner.setTreeNodeComment = function() {
	var node = ReportDesigner.getSelectedTreeNode();
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

ReportDesigner.removeTreeNode = function() {
	var node = ReportDesigner.getSelectedTreeNode();
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
					$('#reportTree').tree('remove', node.target);
					ReportDesigner.clearAllTab();
				}
				ReportCommon.showMsg(data.msg);
			}, 'json');
		});
	}
};

ReportDesigner.saveTreeNode = function() {
	var act = $("#treeNodeAction").val();
	var actUrl = act == "add" ? designerPageRootUrl + "addTreeNode" : designerPageRootUrl + "editTreeNode";
	$('#setTreeNodeForm').form('submit', {
		url : actUrl,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			var result = $.parseJSON(data);
			if (result.success) {
				ReportDesigner.updateTreeNode(act, result.data);
				$('#setTreeNodeDlg').dialog('close');
			}
			ReportCommon.showMsg(result.msg);
		}
	});
};

ReportDesigner.saveChanged = function(data, handler) {
	var isChanged = $("#reportIsChange").val() == 0 ? false : true;
	if (!isChanged) {
		return handler(data);
	}
	$.messager.confirm('确认', '是否保存修改的数据?', function(r) {
		if (r) {
			ReportDesigner.save();
		}
		handler(data);
	});
};

ReportDesigner.clickTreeNodeHandler = function(currNode) {
	ReportDesigner.saveChanged(currNode, function(node) {
		var index = ReportDesigner.getSelectedTabIndex();
		if (index > 1) {
			ReportDesigner.selectTab(0);
		}
		ReportDesigner.selectTreeNodeHandler(node);
	});
};

ReportDesigner.selectTreeNodeHandler = function(node) {
	$('#reportTree').tree('options').url = designerPageRootUrl + 'listChildNodes';
	$('#reportTree').tree('expand', node.target);

	ReportDesigner.clearAllTab();
	if (node.attributes.flag != 1) {
		$('#reportAction').val('add');
		$('#reportPid').val(node.id);
		ReportDesigner.initButtonStatus('add');
	} else {
		ReportDesigner.loadAllTabData();
	}
};

ReportDesigner.treeNodeOnDrop = function(target, source, point) {
	var targetNode = $('#reportTree').tree('getNode', target);
	if (targetNode) {
		$.post(designerPageRootUrl + 'dragTreeNode', {
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

ReportDesigner.copyTreeNode = function() {
	var node = ReportDesigner.getSelectedTreeNode();
	if (node) {
		$('#copyNodeId').val(node.id);
	} else {
		$.messager.alert('错误', '您没有选择任何节点');
	}
};

ReportDesigner.pasteTreeNode = function() {
	var node = ReportDesigner.getSelectedTreeNode();
	if (node) {
		$.post(designerPageRootUrl + 'pasteTreeNode', {
			sourceId : $('#copyNodeId').val(),
			targetId : node.id
		}, function(result) {
			if (result.success) {
				$('#copyNodeId').val(0);
				return ReportDesigner.updateTreeNode('add', result.data);
			}
			return $.messager.alert('错误', result.msg);
		}, 'json');
	} else {
		$.messager.alert('错误', '您没有选择任何节点');
	}
};

ReportDesigner.selectTreeNode = function(id) {
	var node = $('#reportTree').tree('find', id);
	if (node) {
		$('#reportTree').tree('select', node.target);
	}
};

ReportDesigner.getTreeNodeById = function(id) {
	return $('#reportTree').tree('find', id);
};

ReportDesigner.selectAndExpandTreeNode = function(id) {
	var node = $('#reportTree').tree('find', id);
	if (node) {
		$('#reportTree').tree('select', node.target);
		ReportDesigner.selectTreeNodeHandler(node);
		var parentNode = $('#reportTree').tree('getParent', node.target);
		if (parentNode) {
			$('#reportTree').tree('expand', parentNode.target);
		}
	}
};

ReportDesigner.updateTreeNode = function(act, nodeData) {
	if (nodeData == null) {
		return;
	}
	if (act == "add") {
		var newNode = nodeData[0];
		var pid = newNode.attributes.pid;
		// 如果是增加根节点
		if (pid == "0") {
			var roots = $('#reportTree').tree('getRoots');
			if (roots && roots.length > 0) {
				$('#reportTree').tree('insert', {
					after : roots[roots.length - 1].target,
					data : newNode
				});
			} else {
				$('#reportTree').tree('reload');
			}
		} else {
			var parentNode = ReportDesigner.getTreeNodeById(pid);
			$('#reportTree').tree('append', {
				parent : parentNode.target,
				data : nodeData
			});
		}
		ReportDesigner.selectTreeNode(newNode.id);
		var currNode = ReportDesigner.getSelectedTreeNode();
		return currNode ? ReportDesigner.selectTreeNodeHandler(currNode) : null;
	}
	if (act == "edit") {
		var node = ReportDesigner.getTreeNodeById(nodeData.id);
		nodeData["target"] = node.target;
		$('#reportTree').tree('update', nodeData);
		return ReportDesigner.loadDataToPropertiesTab(nodeData.attributes);
	}
};

ReportDesigner.getSelectedTreeNode = function() {
	return $('#reportTree').tree('getSelected');
};

ReportDesigner.reloadTree = function() {
	$('#reportTree').tree('reload');
};

ReportDesigner.openSearchReportDlg = function() {
	$('#searchReportDlg').dialog('open');
	ReportCommon.clearDataGrid('#searchReportGrid');
};

ReportDesigner.search = function() {
	var fieldName = $('#searchReportFieldName').val();
	var keyword = $('#searchReportKeyword').val();
	var url = designerPageRootUrl + 'search?fieldName=' + fieldName + '&keyword=' + keyword;
	return ReportCommon.loadDataToGrid('#searchReportGrid', url);
};

//
// 报表配置相关操作
//
ReportDesigner.add = function() {
	var node = ReportDesigner.getSelectedTreeNode();
	if (node) {
		$('#reportAction').val('add');
		ReportDesigner.clearAllTab();
		ReportDesigner.selectTab(0);
		$('#reportId').val(0);
		$('#reportPid').val(node.id);
		ReportDesigner.initButtonStatus('add');
		ReportDesigner.clearAllEditor();
	} else {
		$.messager.alert('提示', "请选择一个节点", 'info');
	}
};

ReportDesigner.edit = function(data) {
	$('#reportAction').val('edit');
	$('#settingsForm').form('load', data);
	$("#sqlColumnGrid").datagrid('loadData', eval(data.metaColumns));
	$('#reportIsChange').val(0);
	sqlEditor.setValue(data.sqlText);
	ReportDesigner.initButtonStatus('edit');

};

ReportDesigner.initButtonStatus = function(action) {
	$('#btnNewReport').linkbutton(action == 'add' ? 'enable' : 'disable');
	$('#btnEditReport').linkbutton(action == 'add' ? 'disable' : 'enable');
	$('#btnViewReport').linkbutton(action == 'add' ? 'disable' : 'enable');
};

ReportDesigner.resetSettingsForm = function() {
	$("#reportDsId").val("");
	$("#reportLayout").val("1");
	$("#reportDataRange").val("7");
	$("#reportName").val("");
	$("#reportStatColumnLayout").val("1")
	$("#reportStatus").val("0");
	$("#reportSequence").val("100");
	$("#reportId").val("0");
	$("#reportAction").val("add");
	$("#reportUid").val("");
	$("#reportPid").val("0");
	$("#reportIsChange").val("0");
};

ReportDesigner.clearAllEditor = function() {
	sqlEditor.setValue("");
	viewSqlEditor.setValue("");
	viewHistorySqlEditor.setValue("");
};

ReportDesigner.setSqlEditorStatus = function() {
	var status = $("#reportStatus").val();
	if (status > 0) {
		return sqlEditor.setOption("readOnly", "nocursor");
	}
	return sqlEditor.setOption("readOnly", false);
};

ReportDesigner.fullScreenSqlEditor = function() {
	sqlEditor.setOption("fullScreen", !sqlEditor.getOption("fullScreen"));
};

ReportDesigner.viewSqlText = function() {
	var dsId = $('#reportDsId').combobox('getValue');
	var dataRange = $('#reportDataRange').val();
	var sqlText = sqlEditor.getValue();

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
	$.post(designerPageRootUrl + 'viewSqlText', {
		dsId : dsId,
		sqlText : sqlText,
		dataRange : dataRange,
		jsonQueryParams : jsonQueryParams
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			$('#viewSqlTextDlg').dialog('open');
			$('#viewSqlTextDlg .CodeMirror').css("height","99%");
			return viewSqlEditor.setValue(result.data);
		}
		return $.messager.alert('错误', result.msg);
	}, 'json');
};

ReportDesigner.viewSqlHistory = function() {
	$('#viewHistorySqlTextDlg').dialog('open');
	$('#viewHistorySqlTextDlg .CodeMirror').css("height","98%");
	var reportId = $("#reportId").val();
	var url = designerPageRootUrl + 'getHistorySqlText?reportId=' + reportId;
	ReportCommon.loadDataToGrid('#viewSqlTextHistoryGrid', url);
	viewHistorySqlEditor.setValue("");
	viewHistorySqlEditor.refresh();
};

ReportDesigner.executeSql = function() {
	if (!$("#settingsForm").valid()) {
		return;
	}
	var sqlText = sqlEditor.getValue();
	if (sqlText == "") {
		return $.messager.alert('失败', "未发现操作的SQL语句！", 'error');
	}

	$.messager.progress({
		title : '请稍后...',
		text : '数据正在加载中...',
	});

	var queryParamRows = $("#queryParamGrid").datagrid('getRows');
	var jsonQueryParams = queryParamRows ? JSON.stringify(queryParamRows) : "";
	$.post(designerPageRootUrl + 'loadSqlColumns', {
		sqlText : sqlText,
		dsId : $('#reportDsId').combobox('getValue'),
		dataRange : $('#reportDataRange').val(),
		jsonQueryParams : jsonQueryParams
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			$("#sqlColumnGrid").datagrid('clearChecked');
			var rows = ReportDesigner.eachSqlColumnRows(result.data);
			return ReportDesigner.loadSqlColumns(rows);
		}
		return $.messager.alert('错误', result.msg);
	}, 'json');
};

ReportDesigner.loadSqlColumns = function(newRows) {
	var sqlColumnRows = $("#sqlColumnGrid").datagrid('getRows');
	if (sqlColumnRows == null || sqlColumnRows.length == 0) {
		return $("#sqlColumnGrid").datagrid('loadData', newRows);
	}

	ReportDesigner.setSqlColumnRows(sqlColumnRows);
	var oldRowMap = {};
	for (var i = 0; i < sqlColumnRows.length; i++) {
		var name = sqlColumnRows[i].name;
		oldRowMap[name] = sqlColumnRows[i];
	}

	for (var i = 0; i < newRows.length; i++) {
		var name = newRows[i].name;
		if (oldRowMap[name]) {
			oldRowMap[name].dataType = newRows[i].dataType;
			oldRowMap[name].width = newRows[i].width;
			newRows[i] = oldRowMap[name];
		}
	}

	$.each(sqlColumnRows, function(i, row) {
		if (row.type == 4) {
			newRows.push(row);
		}
	});
	return $("#sqlColumnGrid").datagrid('loadData', newRows);
};

ReportDesigner.setSqlColumnRows = function(rows) {
	for (var rowIndex = 0; rowIndex < rows.length; rowIndex++) {
		var row = rows[rowIndex];
		var subOptions = ReportDesigner.getCheckboxOptions(row.type);
		for (var optIndex = 0; optIndex < subOptions.length; optIndex++) {
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

ReportDesigner.getCheckboxOptions = function(type) {
	var subOptions = [];
	if (type == 4) {
		subOptions = $.grep(sqlColumnOptions, function(option, i) {
			return option.type == 1;
		});
	} else if (type == 3) {
		subOptions = $.grep(sqlColumnOptions, function(option, i) {
			return option.type == 1 || option.type == 2;
		});
	} else {
		subOptions = $.grep(sqlColumnOptions, function(option, i) {
			return option.type == 3;
		});
	}
	return subOptions;
};

ReportDesigner.showSqlColumnGridOptionDialog = function(index, name) {
	ReportDesigner.SqlColumnGridOptionDialogName = name;
	$("#sqlColumnGrid").datagrid('selectRow', index);
	var row = $("#sqlColumnGrid").datagrid('getSelected');
	if (name == "expression"){
		$('#columnExpressionDlg').dialog('open');
		$("#columnExpression").val(row.expression);
	}else if( name == "comment"){
		$('#columnCommentDlg').dialog('open');
		$("#columnComment").val(row.comment);
	}
	else if(name == "format"){
		$('#columnFormatDlg').dialog('open');
		$("#columnFormat").val(row.format);
	}
};

ReportDesigner.saveSqlColumnGridOptionDialog = function() {
	var name = ReportDesigner.SqlColumnGridOptionDialogName;
	var row = $("#sqlColumnGrid").datagrid('getSelected');
	if (name == "expression"){
		row.expression = $("#columnExpression").val();
		$('#columnExpressionDlg').dialog('close');
	}else if( name == "comment"){
		row.comment = $("#columnComment").val();
		$('#columnCommentDlg').dialog('close');
	}
	else if(name == "format"){
		row.format = $("#columnFormat").val();
		$('#columnFormatDlg').dialog('close');
	}
};

ReportDesigner.getEmptyExprColumns = function(sqlColumnRows) {
	var emptyColumns = [];
	for (var i = 0; i < sqlColumnRows.length; i++) {
		var row = sqlColumnRows[i];
		if (row.type == 4 && $.trim(row.expression) == "") {
			emptyColumns.push(row.name);
		}
	}
	return emptyColumns;
};

ReportDesigner.save = function() {
	if (!$("#settingsForm").valid()) {
		return;
	}

	var sqlColumnRows = $("#sqlColumnGrid").datagrid('getRows');
	if (sqlColumnRows == null || sqlColumnRows.length == 0) {
		return $.messager.alert('失败', "没有任何报表SQL配置列选项！", 'error');
	}

	ReportDesigner.setSqlColumnRows(sqlColumnRows);
	var columnTypeMap = ReportDesigner.getColumnTypeMap(sqlColumnRows);
	if (columnTypeMap.layout == 0 || columnTypeMap.stat == 0) {
		return $.messager.alert('失败', "您没有设置布局列或者统计列", 'error');
	}

	var emptyExprColumns = ReportDesigner.getEmptyExprColumns(sqlColumnRows);
	if (emptyExprColumns && emptyExprColumns.length > 0) {
		return $.messager.alert('失败', "计算列：[" + emptyExprColumns.join() + "]没有设置表达式！", 'error');
	}

	$.messager.progress({
		title : '请稍后...',
		text : '正在处理中...',
	});

	var reportId = $("#reportId").val();
	var act = $('#reportAction').val();
	$.post(designerPageRootUrl + act, {
		id : reportId,
		pid : $('#reportPid').val(),
		dsId : $('#reportDsId').combobox('getValue'),
		dataRange : $('#reportDataRange').val(),
		name : $("#reportName").val(),
		uid : $("#reportUid").val(),
		layout : $("#reportLayout").val(),
		statColumnLayout : $("#reportStatColumnLayout").val(),
		sqlText : sqlEditor.getValue(),
		metaColumns : JSON.stringify(sqlColumnRows),
		status : $("#reportStatus").val(),
		sequence : $("#reportSequence").val(),
		isChange : $('#reportIsChange').val() == 0 ? false : true
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			ReportDesigner.updateTreeNode(act, result.data);
			$('#reportIsChange').val(0);
		}
		$.messager.alert('操作提示', result.msg, result.success ? 'info' : 'error');
	}, 'json');
};

ReportDesigner.getColumnTypeMap = function(rows) {
	var typeMap = {
		"layout" : 0,
		"dim" : 0,
		"stat" : 0,
		"computed" : 0
	};
	for (var i = 0; i < rows.length; i++) {
		if (rows[i].type == 1) {
			typeMap.layout += 1;
		} else if (rows[i].type == 2) {
			typeMap.dim += 1;
		} else if (rows[i].type == 3) {
			typeMap.stat += 1;
		} else if (rows[i].type == 4) {
			typeMap.computed += 1;
		}
	}
	return typeMap;
};

ReportDesigner.previewInNewTab = function() {
	var node = ReportDesigner.getSelectedTreeNode();
	if (node.attributes.flag == 1) {
		var title = node.attributes.name;
		if ($('#tabs').tabs('exists', title)) {
			$('#tabs').tabs('select', title);
			var tab = $('#tabs').tabs('getSelected');
			return tab.panel('refresh');
		}
		var previewUrl = WebAppRequest.getContextPath() + "/report/uid/" + node.attributes.uid;
		$('#tabs').tabs('add', {
			id : node.id,
			title : title,
			content : '<iframe scrolling="yes" frameborder="0" src="' + previewUrl + '" style="width:100%;height:100%;"></iframe>',
			closable : true
		});
	}
};

//
// 以为报表查询参数配置相关函数
//

ReportDesigner.queryParamFormElementChange = function() {
	var formElement = $("#queryParamFormElement").val();
	if (formElement == "select" || formElement == "selectMul") {
		$("#queryParamContent").removeAttr("disabled");
		return $("#queryParamDataSource").val("sql");
	}
	$("#queryParamContent").val('');
	$("#queryParamContent").attr({
		"disabled" : "disabled"
	});
	$("#queryParamDataSource").val("none");
};

ReportDesigner.loadDataToQueryParamTab = function(data) {
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

ReportDesigner.saveQueryParam = function() {
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

	$.post(designerPageRootUrl + 'setQueryParam', {
		id : id,
		jsonQueryParams : $('#jsonQueryParams').val()
	}, function(result) {
		$.messager.progress("close");
		if (result.success) {
			ReportDesigner.updateTreeNode('edit', result.data);
		}
		$.messager.alert('操作提示', result.msg, result.success ? 'info' : 'error');
	}, 'json');
};

ReportDesigner.deleteQueryParam = function(index) {
	$("#queryParamGrid").datagrid('deleteRow', index);
	$("#queryParamGrid").datagrid('reload', $("#queryParamGrid").datagrid('getRows'));
};

ReportDesigner.setQueryParam = function(act) {
	if ($("#queryParamForm").valid()) {
		var row = $('#queryParamForm').serializeObject()
		row.required = $("#queryParamIsRequired").prop("checked");
		row.autoComplete = $("#queryParamIsAutoComplete").prop("checked");

		if (act == "add") {
			$('#queryParamGrid').datagrid('appendRow', row);
		} else if (act == "edit") {
			var index = $("#queryParamGridIndex").val();
			$('#queryParamGrid').datagrid('updateRow', {
				index : index,
				row : row
			});
		}
		$('#queryParamForm').form('reset');
	}
};

//
// 报表基本属性相关操作
//
ReportDesigner.showProperties = function() {
	var node = ReportDesigner.getSelectedTreeNode();
	if (node) {
		$("#settingsDlg").dialog('open');
		ReportDesigner.clearPropertiesTab();
		ReportDesigner.loadDataToPropertiesTab(node.attributes);
	} else {
		$.messager.alert('失败', "您没有选择任何树节点", 'error');
	}
};

ReportDesigner.loadDataToPropertiesTab = function(data) {
	for ( var propName in data) {
		var id = "#reportProp_" + propName;
		var value = ReportDesigner.getPropertyValue(propName, data);
		$(id).html(value);
	}
};

ReportDesigner.clearPropertiesTab = function() {
	$('#settingsDlg label').each(function(i) {
		$(this).html("");
	});
};

// Utils函数
ReportDesigner.resortDatagrid = function(index, type, grid) {
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

ReportDesigner.getPropertyValue = function(name, object) {
	var value = object[name];
	if (name == "flag") {
		return ReportDesigner.getFlagName(value);
	}
	if (name == "layout") {
		return ReportDesigner.getLayoutName(value);
	}
	if (name == "hasChild") {
		return value ? "有" : "无";
	}
	if (name == "status") {
		return value == 1 ? "锁定" : "编辑";
	}
	return value;
};

ReportDesigner.getFlagName = function(flag) {
	if (flag == 0) {
		return "树节点";
	}
	if (flag == 1) {
		return "报表节点";
	}
	return "其他";
};

ReportDesigner.getLayoutName = function(layout) {
	if (layout == 1) {
		return "横向布局";
	}
	if (layout == 2) {
		return "纵向布局";
	}
	return "无";
};

ReportDesigner.eachSqlColumnRows = function(rows){
	if(rows && rows.length > 0){
		for(var i=0;i<rows.length;i++){
			var row = rows[i];
			row.type = ReportDesigner.getColumnTypeValue(row.type);
			row.sortType = ReportDesigner.getColumnSortTypeValue(row.sortType);
		}
	}
	return rows;
}

ReportDesigner.getColumnTypeValue = function(name){
	if (name == "LAYOUT") {
		return 1;
	}
	if (name == "DIMENSION") {
		return 2;
	}
	if (name == "STATISTICAL") {
		return 3;
	}
	if (name == "COMPUTED") {
		return 4;
	}
	return 2;
};

ReportDesigner.getColumnSortTypeValue = function(name){
	if (name =="DEFAULT") {
		return  0;
	}
	if (name == "DIGIT_ASCENDING") {
		return 1;
	}
	if (name == "DIGIT_DESCENDING") {
		return 2;
	}
	if (name == "CHAR_ASCENDING") {
		return 3;
	}
	if (name == "CHAR_DESCENDING") {
		return 4;
	}
	return 0;
};


// 在tab里显示报表图表页
function showChart(title, id, uid) {
	if ($('#tabs').tabs('exists', title)) {
		$('#tabs').tabs('select', title);
		var tab = $('#tabs').tabs('getSelected');
		return tab.panel('refresh');
	}
	var url = WebAppRequest.getContextPath() + '/report/chart/' + uid;
	$('#tabs').tabs('add', {
		id : id,
		title : title,
		content : '<iframe scrolling="yes" frameborder="0" src="' + url + '" style="width:100%;height:100%;"></iframe>',
		closable : true
	});
}
