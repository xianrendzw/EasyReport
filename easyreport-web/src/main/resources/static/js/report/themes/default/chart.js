$(function() {
	$('#btnGenarate').click(ReportChart.generate);
	$('#btnViewChart').click(ReportChart.viewChart);
	$('#btnAddChart').click(ReportChart.addChart);
	$('#btnResetChart').click(ReportChart.resetChart);
	$('#btnToggleChart').click(ReportChart.toggleChart);
	$('#checkAllStatColumn').click(ReportChart.checkedAllStatColumn);

	ReportChart.generate();
});

var ReportChart = function() {
};

ReportChart.metaData = null;

ReportChart.generate = function(e) {
	$.ajax({
		type : "POST",
		url : WebAppRequest.getContextPath() + '/report/chart/getData',
		data : $("#templateFrom").serialize(),
		dataType : "json",
		beforeSend : function() {
			$('#loadingText').html("图表正在生成中, 请稍等...");
			$('#loading').show();
		},
		success : function(metaData) {
			ReportChart.metaData = metaData;
			ReportChart.initDimOptions();
			ReportChart.clear();
			ReportChart.setDimDefaultValue();
			if (ReportChart.checkStatColumn()) {
				ReportChart.show("chart1");
			}
		},
		complete : function() {
			$('#loading').hide();
		}
	});
};

ReportChart.initDimOptions = function() {
	for(var key in ReportChart.metaData.dimColumnMap){
		var id = "#dim_"+key;
		$(id).combobox({
			textField:'text',
		    valueField:'value',
		    data:ReportChart.metaData.dimColumnMap[key],
		    onSelect:function(option){
		    	if(option.value == "all"){
		    		ReportChart.setXAxisDimOptions();
		    	}
		    }
		});
	}
};

ReportChart.viewChart = function(e) {
	if (ReportChart.metaData && ReportChart.checkStatColumn()) {
		ReportChart.show('chart1');
	}
};

ReportChart.addChart = function(e) {
	if (ReportChart.metaData && ReportChart.checkStatColumn()) {
		var count = $("div[id*='chart']").size() + 1;
		var id = 'chart' + count;
		$("#chart1").after("<div id=\"" + id + "\" style=\"height: 345px; border: 1px solid #ccc;\"></div>");
		ReportChart.show(id);
	}
};

ReportChart.clear = function() {
	$("div[id*='chart']").each(function() {
		if ($(this).attr("id") != "chart1") {
			$(this).remove();
		}
	});
};

ReportChart.resetChart = function(e) {
	if (ReportChart.metaData) {
		ReportChart.setDimDefaultValue();
		$("div[id*='chart']").each(function() {
			if ($(this).attr("id") != "chart1") {
				$(this).remove();
			}
		});
		ReportChart.show('chart1');
	}
};

ReportChart.toggleChart = function(e) {
	if (ReportChart.metaData) {
		$("div[id*='chart']").each(function() {
			var dom = document.getElementById($(this).attr("id"));
			var currChart = require('echarts').init(dom);
			var xAxis = currChart.getOption().xAxis;
			var yAxis = currChart.getOption().yAxis;
			currChart.setOption({
				xAxis : yAxis,
				yAxis : xAxis
			});
		});
	}
};

ReportChart.checkStatColumn = function() {
	return ReportChart.checkStatColumnCount() && ReportChart.checkCanSelectAllDimCount();
};

ReportChart.checkStatColumnCount = function() {
	var checkedStatColumnCount = ReportChart.getCheckedStatColumnCount();
	if (checkedStatColumnCount < 1) {
		$.messager.alert('失败', "您没有选择统计列!", 'error');
		return false;
	}
	return true;
};

ReportChart.checkCanSelectAllDimCount = function() {
	var count = ReportChart.getCanSelectAllDimCount();
	if (ReportChart.isSelectMutiAll()) {
		$.messager.alert('失败', "只能选择" + count + "个维度的[全部]项!", 'error');
		return false;
	}
	return true;
};

ReportChart.setDimDefaultValue = function() {
	var dimColumns = ReportChart.metaData.dimColumns;
	var canSelectAllDimCount = ReportChart.getCanSelectAllDimCount();
	for (var i = 0; i < dimColumns.length; i++) {
		if (i < canSelectAllDimCount) {
			continue;
		}
		var id = "#dim_" + dimColumns[i].name;
		var values = ReportChart.getComoboxValues(id);
		if (values.length > 1) {
			$(id).combobox('select',values[1]);
		}
	}
	ReportChart.setXAxisDimOptions();
};

ReportChart.getComoboxValues = function(id){
	return $.map($(id).combobox('getData'),function(rec){
		return rec.value;
	});
};

ReportChart.getCheckedStatColumnCount = function() {
	var statColumns = ReportChart.getCheckedStatColumns();
	return statColumns ? statColumns.length : 0;
};

ReportChart.getCanSelectAllDimCount = function() {
	var checkedStatColumnCount = ReportChart.getCheckedStatColumnCount();
	return checkedStatColumnCount == 1 ? 2 : 1;
};

ReportChart.getCheckedStatColumns = function() {
	return $("input[name='statColumns']:checked").map(function() {
		return $(this).val();
	}).get();
};

ReportChart.getCheckedStatColumn = function() {
	if (ReportChart.hasSecondAllDim()) {
		var name = $("input[name='statColumns']:checked").map(function() {
			return $(this).val();
		}).get()[0];
		var checkedDims = $.grep(ReportChart.metaData.statColumns, function(statColumn) {
			return statColumn.name == name;
		});
		if (checkedDims && checkedDims.length > 0) {
			return checkedDims[0];
		}
	}
	return null;
};

ReportChart.getDisplayStatColumns = function() {
	var checkedStatColumns = ReportChart.getCheckedStatColumns();
	return $.grep(ReportChart.metaData.statColumns, function(statColumn) {
		return $.inArray(statColumn.name, checkedStatColumns) > -1;
	});
};

ReportChart.isExistAllOption = function() {
	var exists = false;
	var dimColumns = ReportChart.metaData.dimColumns;
	for (var i = dimColumns.length - 1; i >= 0; i--) {
		id = "#dim_" + dimColumns[i].name;
		var value = $(id).combobox('getValue');
		if (value == "all") {
			exists = true;
			break;
		}
	}
	return exists;
};

// 是否选择多个维度的all(全部)项，
// 因为图表只支持二维，所以选择多个all项无效
ReportChart.isSelectMutiAll = function() {
	var dimColumns = ReportChart.metaData.dimColumns;
	var canSelectAllDimCount = ReportChart.getCanSelectAllDimCount();
	var count = 0;
	for (var i = 0; i < dimColumns.length; i++) {
		id = "#dim_" + dimColumns[i].name;
		var value = $(id).combobox('getValue');
		if (value == "all") {
			count++;
		}
	}
	return count > canSelectAllDimCount;
};

ReportChart.setXAxisDimOptions = function() {
	var dimColumns = ReportChart.metaData.dimColumns;
	var exist = ReportChart.isExistAllOption();
	$('#xAxisDim').empty();
	for (var i = 0; i < dimColumns.length; i++) {
		var id = "#dim_" + dimColumns[i].name;
		var value = $(id).combobox('getValue');
		if (!exist || (exist && value == "all")) {
			$("#xAxisDim").append("<option value='" + dimColumns[i].name + "'>" + dimColumns[i].text + "</option>");
		}
	}
};

ReportChart.getSecondAllDimName = function() {
	var showDimName = $("#xAxisDim").val();
	if (ReportChart.hasSecondAllDim()) {
		return $("#xAxisDim option").map(function() {
			if ($(this).val() != showDimName) {
				return $(this).val();
			}
		}).get()[0];
	}
	return null;
};

ReportChart.hasSecondAllDim = function() {
	return (ReportChart.getCheckedStatColumnCount() == 1 && ReportChart.isExistAllOption() && $("#xAxisDim option").size() == 2);
};

ReportChart.checkedAllStatColumn = function() {
	var checked = $("input[name='checkAllStatColumn']").prop("checked");
	$("input[name='statColumns']").prop("checked", checked);
};

ReportChart.toChartData = function(chartType) {
	var chartData = {
		"title" : ReportChart.getTitle(),
		"legends" : ReportChart.getLegendData(),
		"categories" : ReportChart.getCategories(),
		"series" : ReportChart.getSeries(chartType)
	};
	return chartData;
};

ReportChart.getTitle = function() {
	var dimColumns = ReportChart.metaData.dimColumns;
	var names = [];
	for (var i = 0; i < dimColumns.length; i++) {
		var id = "#dim_" + dimColumns[i].name;
		var value = $(id).combobox('getValue');
		var text = dimColumns[i].text + ":" + value;
		names.push(text);
	}
	var checkedStatColumn = ReportChart.getCheckedStatColumn();
	if (checkedStatColumn) {
		names.push(checkedStatColumn.text);
	}

	return names.join(' ');
};

ReportChart.getLegendData = function() {
	return $.map(ReportChart.getLegends(), function(e) {
		return e.text;
	});
};

ReportChart.getLegends = function() {
	var showDimName = ReportChart.getSecondAllDimName();
	if (showDimName) {
		var id = "#dim_" + showDimName;
		var values = ReportChart.getComoboxValues(id);
		return $.map(values,function(value){
			if (value != "all") {
				return {
					"name" : showDimName,
					"text" : value
				};
			}
		});
	}
	return ReportChart.getDisplayStatColumns();
};

ReportChart.getCategories = function() {
	return ReportChart.getCategory().data;
};

ReportChart.getCategory = function() {
	var dimName = $('#xAxisDim').val();
	var id = "#dim_" + dimName;
	var sortType = $("#sortType").val();
	var values = null;

	if (ReportChart.isExistAllOption()) {
		values = $.map(ReportChart.getComoboxValues(id),function(value){
			if (value != "all") {
				return value;
			}
		});
	} else {
		values = [];
		values.push($(id).val());
	}

	values = values.sort();
	if (sortType == "desc") {
		values = values.reverse();
	}

	return {
		"name" : dimName,
		"data" : values
	};
};

ReportChart.getSeries = function(chartType) {
	var dimColumns = ReportChart.metaData.dimColumns;
	var keyValues = [];
	for (var i = 0; i < dimColumns.length; i++) {
		var id = "#dim_" + dimColumns[i].name;
		var value = $(id).combobox('getValue');
		if (value == "all") {
			value = "#{" + dimColumns[i].name + "}";
		}
		keyValues.push(value.replace('$', '*'));
	}
	var keyTemplate = keyValues.join('$') + '$';
	var category = ReportChart.getCategory();
	var legends = ReportChart.getLegends();
	var categoryName = "#{" + category.name + "}";
	var series = [];
	for (var j = 0; j < legends.length; j++) {
		series.push({
			name : legends[j].text,
			type : chartType,
			data : []
		});
	}

	if (ReportChart.hasSecondAllDim()) {
		var secondAllDimName = "#{" + ReportChart.getSecondAllDimName() + "}";
		var statColumnName = ReportChart.getCheckedStatColumn().name;
		for (var i = 0; i < category.data.length; i++) {
			var tempKey = keyTemplate.replace(categoryName, category.data[i].replace('$', '*'));
			for (var j = 0; j < legends.length; j++) {
				var key = tempKey.replace(secondAllDimName, legends[j].text.replace('$', '*'));
				var row = ReportChart.metaData.dataRows[key];
				series[j].data.push(row ? row[statColumnName] : 0);
			}
		}
		return series;
	}

	for (var i = 0; i < category.data.length; i++) {
		var key = keyTemplate.replace(categoryName, category.data[i].replace('$', '*'));
		var row = ReportChart.metaData.dataRows[key];
		for (var j = 0; j < legends.length; j++) {
			var columnName = legends[j].name;
			series[j].data.push(row ? row[columnName] : 0);
		}
	}
	return series;
};

ReportChart.show = function(id, chartType) {
	chartType = chartType || "line";
	var chartData = ReportChart.toChartData(chartType);

	require.config({
		paths : {
			'echarts' : '../../js/plugins/echarts',
			'echarts/chart/bar' : '../../js/plugins/echarts/chart/bar',
			'echarts/chart/line' : '../../js/plugins/echarts/chart/line'
		}
	});
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ], function(ec) {
		var myChart = ec.init(document.getElementById(id));
		myChart.clear();
		myChart.setOption({
			title : {
				text : chartData.title
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : chartData.legends
			},
			toolbox : {
				show : true,
				orient : 'vertical',
				x : 'right',
				y : 'center',
				feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			    }
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data : chartData.categories
			} ],
			yAxis : [ {
				type : 'value',
				splitArea : {
					show : true
				}
			} ],
			series : chartData.series
		});
	});
};
