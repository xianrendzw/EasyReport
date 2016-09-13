var configPageRootUrl = WebAppRequest.getContextPath() + '/report/config/';
$(function() {
	// 左边字典树
	$('#west').panel({
		tools : [ {
			iconCls : 'icon-add',
			handler : ConfigDict.add
		}, {
			iconCls : 'icon-reload',
			handler : function() {
				$('#configDictTree').tree('reload');
				ReportCommon.loadDataToGrid('#configDictGrid', configPageRootUrl + 'query?id=0');
			}
		} ]
	});

	$('#configDictTree').tree({
		checkbox : false,
		method : 'get',
		url : configPageRootUrl + 'listchildnodes',
		onClick : function(node) {
			$('#configDictTree').tree('expand', node.target);
			$('#configDictTree').tree('options').url = configPageRootUrl + 'listchildnodes';
			ReportCommon.loadDataToGrid('#configDictGrid', configPageRootUrl + 'query?id=' + node.id);
		},
		onContextMenu : function(e, node) {
			e.preventDefault();
			$('#configDictTree').tree('select', node.target);
			$('#tree_ctx_menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});

	// 配置字典grid
	$('#configDictGrid').datagrid({
		url : configPageRootUrl + 'query',
		method : 'get',
		idField : 'id',
		pageSize : 30,
		fit : true,
		pagination : true,
		rownumbers : true,
		fitColumns : true,
		singleSelect : true,
		toolbar : [ {
			text : '增加',
			iconCls : 'icon-add',
			handler : ConfigDict.add
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : ConfigDict.edit
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : ConfigDict.remove
		} ],
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'id',
			title : '标识',
			width : 50
		}, {
			field : 'name',
			title : '名称',
			width : 100
		}, {
			field : 'key',
			title : '键',
			width : 100
		}, {
			field : 'value',
			title : '值',
			width : 100
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 100
		}, {
			field : 'updateTime',
			title : '更新时间',
			width : 100
		} ] ],
		onDblClickRow : function(index, row) {
			ConfigDict.edit();
		}
	});

	// 初始化配置字典dialog
	$('#configDictDlg').dialog({
		closed : true,
		modal : true,
		width : 500,
		height : 310,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#configDictDlg").dialog('close');
			}
		}, {
			text : '保存',
			iconCls : 'icon-save',
			handler : ConfigDict.save
		} ]
	});
});

var ConfigDict = function() {
};

ConfigDict.treeContextMenu = function(item) {
	if (item.name == "add") {
		return ConfigDict.add();
	}
	if (item.name == "edit") {
		return ConfigDict.edit();
	}
	if (item.name == "remove") {
		return ConfigDict.remove();
	}
	return;
};

// 配置字典增删改操作
ConfigDict.add = function() {
	var name = "根节点";
	var id = "0";
	var node = $('#configDictTree').tree('getSelected');

	if (node) {
		var configNode = node.attributes;
		name = configNode.name;
		id = configNode.id;
	}
	ReportCommon.add('#configDictDlg', '#configDictForm', '#configDictAction', '#configDictId', '新增[' + name + ']配置字典的子项');
	$("#configDictPid").val(id);
	$("#configDictPNameDiv").show();
	$("#configDictPName").html(name);
};

ConfigDict.edit = function() {
	$("#configDictPNameDiv").hide();
	var row = $('#configDictGrid').datagrid('getSelected');
	var node = $('#configDictTree').tree('getSelected');
	node = node ? node.attributes : null;
	row = row || node;
	ReportCommon.editWithData('#configDictDlg', '#configDictForm', '#configDictAction', '#configDictId', '修改ID配置字典项', row);
};

ConfigDict.remove = function() {
	var row = $('#configDictGrid').datagrid('getSelected');
	var node = $('#configDictTree').tree('getSelected');
	node = node ? node.attributes : null;
	row = row || node;

	ReportCommon.removeWithCallback(row, configPageRootUrl + 'remove', {
		id : row ? row.id : 0
	}, function(data) {
		ConfigDict.refreshNode(data.pid);
		ReportCommon.loadDataToGrid('#configDictGrid', configPageRootUrl + 'query?id=' + data.pid);
	});
};

ConfigDict.batchRemove = function() {
	var rows = $('#configDictGrid').datagrid('getChecked');
	var ids = $.map(rows, function(row) {
		return row.id;
	}).join();
	ReportCommon.removeWithCallback(rows, configPageRootUrl + 'batchRemove', {
		id : ids
	}, function(data) {
		ReportCommon.loadDataToGrid('#configDictGrid', configPageRootUrl + 'query?id=' + data[0].pid);
	});
};

ConfigDict.save = function() {
	var pid = $("#configDictPid").val();
	var url = configPageRootUrl + 'query?id=' + pid;
	var actUrl = configPageRootUrl + $('#configDictAction').val();
	ReportCommon.saveWithCallback('#configDictDlg', '#configDictForm', actUrl, function() {
		ConfigDict.refreshNode(pid);
		ReportCommon.loadDataToGrid('#configDictGrid', url);
	});
};

ConfigDict.refreshNode = function(pid) {
	if (pid == "0") {
		$('#configDictTree').tree('reload');
	} else {
		var node = $('#configDictTree').tree('find', pid);
		$('#configDictTree').tree('select', node.target);
		$('#configDictTree').tree('reload', node.target);
	}
};