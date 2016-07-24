var ReportCommon = function() {
};

ReportCommon.clearDataGrid = function(id) {
	$(id).datagrid('loadData', {
		total : 0,
		rows : []
	});
};

// 通用增删改操作
ReportCommon.add = function(dlgId, formId, actId, rowId, title) {
	$(formId).form('clear');
	$(actId).val("add");
	$(rowId).val(0);
	$(dlgId).dialog('open').dialog('setTitle', title);
	$(dlgId).dialog('center');
};

ReportCommon.edit = function(dlgId, formId, actId, gridId, rowId, title) {
	var row = $(gridId).datagrid('getSelected');
	ReportCommon.editWithData(dlgId, formId, actId, rowId, title, row);
};

ReportCommon.editWithData = function(dlgId, formId, actId, rowId, title, data) {
	ReportCommon.editWithCallback(dlgId, formId, actId, rowId, title, data, function(data) {
	});
};

ReportCommon.editWithCallback = function(dlgId, formId, actId, rowId, title, data, callback) {
	if (data) {
		$(formId).form('clear');
		$(actId).val("edit");
		$(rowId).val(data.id);
		$(dlgId).dialog('open').dialog('setTitle', title);
		$(dlgId).dialog('center');
		$(formId).form('load', data);
		callback(data);
	} else {
		ReportCommon.showMsg("请您先选择一个选项!");
	}
};

ReportCommon.remove = function(gridId, gridUrl) {
	var row = $(gridId).datagrid('getSelected');
	ReportCommon.removeWithCallback(row, 'remove', {
		id : row.id
	}, function(data) {
		ReportCommon.loadDataToGrid(gridId, gridUrl);
	});
};

ReportCommon.removeWithActUrl = function(gridId, gridUrl,actUrl) {
	var row = $(gridId).datagrid('getSelected');
	ReportCommon.removeWithCallback(row, actUrl, {
		id : row.id
	}, function(data) {
		ReportCommon.loadDataToGrid(gridId, gridUrl);
	});
};

ReportCommon.removeWithCallback = function(data, postUrl, postData, callback) {
	if (data) {
		$.messager.confirm('删除', '您确定要删除记录吗?', function(r) {
			if (r) {
				$.post(postUrl, postData, function(result) {
					if (result.success) {
						ReportCommon.showMsg(result.msg);
						callback(data);
					} else {
						$.messager.show({
							title : '错误',
							msg : result.msg
						});
					}
				}, 'json');
			}
		});
	} else {
		ReportCommon.showMsg("请您先选择要删除的记录!");
	}
};

ReportCommon.batchRemove = function(gridId, gridUrl) {
	var rows = $(gridId).datagrid('getChecked');
	var ids = $.map(rows, function(row) {
		return row.id;
	}).join();
	ReportCommon.removeWithCallback(rows, 'batchRemove', {
		id : ids
	}, function(data) {
		ReportCommon.loadDataToGrid(gridId, gridUrl);
	});
};

ReportCommon.batchRemoveWithActUrl = function(gridId, gridUrl,actUrl) {
	var rows = $(gridId).datagrid('getChecked');
	var ids = $.map(rows, function(row) {
		return row.id;
	}).join();
	ReportCommon.removeWithCallback(rows, actUrl, {
		id : ids
	}, function(data) {
		ReportCommon.loadDataToGrid(gridId, gridUrl);
	});
};

ReportCommon.save = function(dlgId, formId, actId, gridId, gridUrl) {
	var action = $(actId).val();
	ReportCommon.saveWithCallback(dlgId, formId, action, function() {
		ReportCommon.loadDataToGrid(gridId, gridUrl);
	});
};

ReportCommon.saveWithActUrl = function(dlgId, formId, actId, gridId, gridUrl,actRootUrl) {
	var action = actRootUrl + $(actId).val();
	ReportCommon.saveWithCallback(dlgId, formId, action, function() {
		ReportCommon.loadDataToGrid(gridId, gridUrl);
	});
};


ReportCommon.saveWithCallback = function(dlgId, formId, actUrl, callback) {
	$(formId).form('submit', {
		url : actUrl,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			var result = eval('(' + data + ')');
			if (result.success) {
				ReportCommon.showMsg(result.msg);
				callback();
				$(dlgId).dialog('close');
			} else {
				$.messager.show({
					title : '错误',
					msg : result.msg
				});
			}
		}
	});
};

ReportCommon.loadDataToGrid = function(selector, href) {
	var grid = $(selector);
	grid.datagrid('clearSelections');
	grid.datagrid({
		url : href
	});
};

ReportCommon.reloadDatagird = function(selector) {
	$(selector).datagrid('reload');
};

ReportCommon.parseJSON = function(jsonStr) {
	if (jsonStr == null || jsonStr == "") {
		return null;
	}

	return $.parseJSON(jsonStr);
};

ReportCommon.showMsg = function(msg) {
	$.messager.show({
		title : '提示',
		msg : msg,
		timeout : 3000,
		showType : 'slide'
	});
};

ReportCommon.showLongMsg = function(msg, time) {
	$.messager.show({
		title : '提示',
		msg : msg,
		timeout : time,
		showType : 'slide'
	});
};

ReportCommon.numberFormatter = function(value) {
	return format("#,###.", value);
};