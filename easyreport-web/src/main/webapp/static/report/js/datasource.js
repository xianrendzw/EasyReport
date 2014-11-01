var dsPageRootUrl = XFrame.getContextPath() + '/report/ds/';
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
			handler : Datasource.add
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : Datasource.edit
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : Datasource.remove
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
			width : 100
		}, {
			field : 'user',
			title : '用户名',
			width : 50
		}, {
			field : 'password',
			title : '密码',
			width : 50
		}, {
			field : 'createTime',
			title : '创建日期',
			width : 100
		}, {
			field : 'options',
			title : '操作',
			width : 300,
			formatter : function(value, row, index) {
				return Datasource.optionsFormatter(value, row, index);
			}
		} ] ],
		onDblClickRow : function(index, row) {
			Datasource.edit();
		}
	});

	
	// 初始化数据源dialog
	$('#checkDlg').dialog({
		closed : true,
		modal : true,
		buttons : [  {
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
				Datasource.testConnection();
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
			handler : Datasource.save
		} ]
	});
});

var Datasource = function() {
};

// 数据源增删改操作
Datasource.add = function() {
	ReportCommon.add('#datasourceDlg', '#datasourceForm', '#datasourceAction', '#datasourceId', '新增数据源配置');
};

Datasource.edit = function() {
	ReportCommon.edit('#datasourceDlg', '#datasourceForm', '#datasourceAction', '#datasourceGrid', '#datasourceId',
			'修改数据源配置');
};

Datasource.remove = function() {
	ReportCommon.removeWithActUrl('#datasourceGrid', dsPageRootUrl + 'query',dsPageRootUrl + 'remove');
};

Datasource.batchRemove = function() {
	ReportCommon.batchRemoveWithActUrl('#datasourceGrid', dsPageRootUrl + 'query',dsPageRootUrl + 'remove');
};

Datasource.save = function() {
	ReportCommon.saveWithActUrl('#datasourceDlg', '#datasourceForm', '#datasourceAction', '#datasourceGrid', dsPageRootUrl + 'query',
			dsPageRootUrl + '');
};

Datasource.optionsFormatter = function(value, row, index) {
	var path = XFrame.getContextPath() + '/static/report/icons/connect.png';
	return '<a href="#" title="测试连接" onclick="javascript:Datasource.applyConnection(' + index + ')"><img src="' + path
			+ '" alt="测试连接"/"></a>';
};

Datasource.applyConnection = function(index) {
	$('#datasourceGrid').datagrid('selectRow', index);
	var row = $('#datasourceGrid').datagrid('getSelected');

	$.post(dsPageRootUrl + 'testconnection', {
		url : row.jdbcUrl,
		pass : row.password,
		user : row.user
	}, function callback(data) {
		if (data.success) {
			$.messager.alert('成功', "测试成功", 'success');
		} else {
			$.messager.alert('失败', "测试失败", 'error');
		}
	}, 'json');
};

Datasource.testConnection = function() {
	$.post(dsPageRootUrl + 'testconnection', {
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