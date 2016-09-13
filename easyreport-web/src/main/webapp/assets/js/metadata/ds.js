var dsPageRootUrl = WebAppRequest.getContextPath() + '/report/ds/';
$(function() {
	// 数据源grid
	$('#datasourceGrid').datagrid({
		method : 'get',
		url : dsPageRootUrl + 'query',
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
			handler : DataSource.add
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : DataSource.edit
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : DataSource.remove
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
			title : '数据源名称',
			width : 100
		}, {
			field : 'uid',
			title : '数据源唯一ID',
			width : 100
		}, {
			field : 'jdbcUrl',
			title : '数据源连接字符串',
			width : 200
		}, {
			field : 'createTime',
			title : '创建日期',
			width : 100
		}, {
			field : 'options',
			title : '操作',
			width : 300,
			formatter : function(value, row, index) {
				return DataSource.optionsFormatter(value, row, index);
			}
		} ] ],
		onDblClickRow : function(index, row) {
			DataSource.edit();
		}
	});

	// 初始化数据源dialog
	$('#checkDlg').dialog({
		closed : true,
		modal : true,
		buttons : [ {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#checkDlg").dialog('close');
			}
		} ]
	});

	$('#datasourceDlg').dialog({
		closed : true,
		modal : true,
		buttons : [ {
			text : '测试连接',
			iconCls : 'icon-search',
			handler : function() {
				DataSource.testConnection();
			}
		}, {
			text : '关闭',
			iconCls : 'icon-no',
			handler : function() {
				$("#datasourceDlg").dialog('close');
			}
		}, {
			text : '保存',
			iconCls : 'icon-save',
			handler : DataSource.save
		} ]
	});
});

var DataSource = function() {
};

// 数据源增删改操作
DataSource.add = function() {
	ReportCommon.add('#datasourceDlg', '#datasourceForm', '#datasourceAction', '#datasourceId', '新增数据源配置');
};

DataSource.edit = function() {
	ReportCommon.edit('#datasourceDlg', '#datasourceForm', '#datasourceAction', '#datasourceGrid', '#datasourceId', '修改数据源配置');
};

DataSource.remove = function() {
	ReportCommon.removeWithActUrl('#datasourceGrid', dsPageRootUrl + 'query', dsPageRootUrl + 'remove');
};

DataSource.batchRemove = function() {
	ReportCommon.batchRemoveWithActUrl('#datasourceGrid', dsPageRootUrl + 'query', dsPageRootUrl + 'remove');
};

DataSource.save = function() {
	ReportCommon.saveWithActUrl('#datasourceDlg', '#datasourceForm', '#datasourceAction', '#datasourceGrid', dsPageRootUrl + 'query', dsPageRootUrl
			+ '');
};

DataSource.optionsFormatter = function(value, row, index) {
	var path = WebAppRequest.getContextPath() + '/assets/modules/report/icons/connect.png';
	return '<a href="#" title="测试连接" onclick="javascript:DataSource.applyConnection(' + index + ')"><img src="' + path + '" alt="测试连接"/"></a>';
};

DataSource.applyConnection = function(index) {
	$('#datasourceGrid').datagrid('selectRow', index);
	var row = $('#datasourceGrid').datagrid('getSelected');

	$.post(dsPageRootUrl + 'testConnectionById', {
		id : row.id
	}, function callback(data) {
		if (data.success) {
			$.messager.alert('成功', "测试成功", 'success');
		} else {
			$.messager.alert('失败', "测试失败", 'error');
		}
	}, 'json');
};

DataSource.testConnection = function() {
	$.post(dsPageRootUrl + 'testConnection', {
		url : $("#configJdbcUrl").val(),
		pass : $("#configPassword").val(),
		user : $("#configUser").val()
	}, function callback(data) {
		if (data.success) {
			$.messager.alert('成功', "测试成功", 'success');
		} else {
			$.messager.alert('失败', "测试失败", 'error');
		}
	}, 'json');
};