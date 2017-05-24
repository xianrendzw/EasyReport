var ChartReport = {
    init: function () {
        ChartReportMVC.View.initControl();
        ChartReportMVC.View.bindEvent();
        ChartReportMVC.View.bindValidate();
        ChartReportMVC.View.initData();
    }
};

var ChartReportCommon = {
    baseUrl: EasyReport.ctxPath + '/report'
};

var ChartReportMVC = {
    URLs: {
        getData: {
            url: ChartReportCommon.baseUrl + '/chart/getData.json',
            method: 'POST'
        }
    },
    View: {
        initControl: function () {
            $.parser.parse('#chart-report-div');
        },
        bindEvent: function () {
            $('#btn-chart-generate').click(ChartReportMVC.Controller.generate);
            $('#btnViewChart').click(ChartReportMVC.Controller.viewChart);
            $('#btnAddChart').click(ChartReportMVC.Controller.addChart);
            $('#btnResetChart').click(ChartReportMVC.Controller.resetChart);
            $('#btnToggleChart').click(ChartReportMVC.Controller.toggleChart);

            $("#chart-report-columns input[name='checkAllStatColumn']").click(function (e) {
                var checked = $("#chart-report-columns input[name='checkAllStatColumn']").prop("checked");
                $("#chart-report-columns input[name='statColumns']").prop("checked", checked);
            });
        },
        bindValidate: function () {
        },
        initData: function () {
            ChartReportMVC.Controller.generate();
        }
    },
    Model: {
        metaData: null
    },
    Controller: {
        generate: function (e) {
            $.ajax({
                type: "POST",
                url: ChartReportMVC.URLs.getData.url,
                data: $("#chart-report-form").serialize(),
                dataType: "json",
                beforeSend: function () {
                    if (e) {
                        $.messager.progress({
                            title: '请稍后...',
                            text: '报表正在生成中...',
                        });
                    }
                },
                success: function (result) {
                    if (!result.code) {
                        ChartReportMVC.Model.metaData = result.data;
                        ChartReportMVC.Util.initDimOptions();
                        ChartReportMVC.Controller.clear();
                        ChartReportMVC.Util.setDimDefaultValue();
                        if (ChartReportMVC.Util.checkStatColumn()) {
                            ChartReportMVC.Controller.show("legend1");
                        }
                    } else {
                        if (e) {
                            $.messager.alert('操作提示', result.data.msg, 'error');
                        }
                    }
                },
                complete: function () {
                    if (e) {
                        $.messager.progress("close");
                    }
                }
            });
        },
        show: function (id, chartType) {
            chartType = chartType || "line";
            var chartData = ChartReportMVC.Util.toChartData(chartType);
            var myChart = echarts.init(document.getElementById(id));
            myChart.clear();
            myChart.setOption({
                title: {
                    text: chartData.title
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: chartData.legends
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    x: 'right',
                    y: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {show: true, type: ['line', 'bar']},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                xAxis: [{
                    type: 'category',
                    data: chartData.categories
                }],
                yAxis: [{
                    type: 'value',
                    splitArea: {
                        show: true
                    }
                }],
                series: chartData.series
            });
        },
        viewChart: function (e) {
            if (ChartReportMVC.Model.metaData && ChartReportMVC.Util.checkStatColumn()) {
                ChartReportMVC.Controller.show('legend1');
            }
        },
        addChart: function (e) {
            if (ChartReportMVC.Model.metaData && ChartReportMVC.Util.checkStatColumn()) {
                var count = $("div[id*='legend']").size() + 1;
                var id = 'legend' + count;
                $("#legend1").after("<div id=\"" + id + "\" style=\"height: 345px; border: 1px solid #ccc;\"></div>");
                ChartReportMVC.Controller.show(id);
            }
        },
        clear: function () {
            $("div[id*='legend']").each(function () {
                if ($(this).attr("id") != "legend1") {
                    $(this).remove();
                }
            });
        },
        resetChart: function (e) {
            if (ChartReportMVC.Model.metaData) {
                ChartReportMVC.Util.setDimDefaultValue();
                $("div[id*='legend']").each(function () {
                    if ($(this).attr("id") != "legend1") {
                        $(this).remove();
                    }
                });
                ChartReportMVC.Controller.show('legend1');
            }
        },
        toggleChart: function (e) {
            if (ChartReportMVC.Model.metaData) {
                $("div[id*='legend']").each(function () {
                    var dom = document.getElementById($(this).attr("id"));
                    var currChart = require('echarts').init(dom);
                    var xAxis = currChart.getOption().xAxis;
                    var yAxis = currChart.getOption().yAxis;
                    currChart.setOption({
                        xAxis: yAxis,
                        yAxis: xAxis
                    });
                });
            }
        }
    },
    Util: {
        initDimOptions: function () {
            for (var key in ChartReportMVC.Model.metaData.dimColumnMap) {
                var id = "#dim_" + key;
                $(id).combobox({
                    textField: 'text',
                    valueField: 'value',
                    data: ChartReportMVC.Model.metaData.dimColumnMap[key],
                    onSelect: function (option) {
                        if (option.value == "all") {
                            ChartReportMVC.Util.setXAxisDimOptions();
                        }
                    }
                });
            }
        },
        checkStatColumn: function () {
            return ChartReportMVC.Util.checkStatColumnCount() && ChartReportMVC.Util.checkCanSelectAllDimCount();
        },
        checkStatColumnCount: function () {
            var checkedStatColumnCount = ChartReportMVC.Util.getCheckedStatColumnCount();
            if (checkedStatColumnCount < 1) {
                $.messager.alert('失败', "您没有选择统计列!", 'error');
                return false;
            }
            return true;
        },
        checkCanSelectAllDimCount: function () {
            var count = ChartReportMVC.Util.getCanSelectAllDimCount();
            if (ChartReportMVC.Util.isSelectMutiAll()) {
                $.messager.alert('失败', "只能选择" + count + "个维度的[全部]项!", 'error');
                return false;
            }
            return true;
        },
        setDimDefaultValue: function () {
            var dimColumns = ChartReportMVC.Model.metaData.dimColumns;
            var canSelectAllDimCount = ChartReportMVC.Util.getCanSelectAllDimCount();
            for (var i = 0; i < dimColumns.length; i++) {
                if (i < canSelectAllDimCount) {
                    continue;
                }
                var id = "#dim_" + dimColumns[i].name;
                var values = ChartReportMVC.Util.getComoboxValues(id);
                if (values.length > 1) {
                    $(id).combobox('select', values[1]);
                }
            }
            ChartReportMVC.Util.setXAxisDimOptions();
        },
        getComoboxValues: function (id) {
            return $.map($(id).combobox('getData'), function (rec) {
                return rec.value;
            });
        },
        getCheckedStatColumnCount: function () {
            var statColumns = ChartReportMVC.Util.getCheckedStatColumns();
            return statColumns ? statColumns.length : 0;
        },
        getCanSelectAllDimCount: function () {
            var checkedStatColumnCount = ChartReportMVC.Util.getCheckedStatColumnCount();
            return checkedStatColumnCount == 1 ? 2 : 1;
        },
        getCheckedStatColumns: function () {
            return $("#chart-report-columns input[name='statColumns']:checked").map(function () {
                return $(this).val();
            }).get();
        },
        getCheckedStatColumn: function () {
            if (ChartReportMVC.Util.hasSecondAllDim()) {
                var name = $("#chart-report-columns input[name='statColumns']:checked").map(function () {
                    return $(this).val();
                }).get()[0];
                var checkedDims = $.grep(ChartReportMVC.Model.metaData.statColumns, function (statColumn) {
                    return statColumn.name == name;
                });
                if (checkedDims && checkedDims.length > 0) {
                    return checkedDims[0];
                }
            }
            return null;
        },
        getDisplayStatColumns: function () {
            var checkedStatColumns = ChartReportMVC.Util.getCheckedStatColumns();
            return $.grep(ChartReportMVC.Model.metaData.statColumns, function (statColumn) {
                return $.inArray(statColumn.name, checkedStatColumns) > -1;
            });
        },
        isExistAllOption: function () {
            var exists = false;
            var dimColumns = ChartReportMVC.Model.metaData.dimColumns;
            for (var i = dimColumns.length - 1; i >= 0; i--) {
                id = "#dim_" + dimColumns[i].name;
                var value = $(id).combobox('getValue');
                if (value == "all") {
                    exists = true;
                    break;
                }
            }
            return exists;
        },
        // 是否选择多个维度的all(全部)项，
        // 因为图表只支持二维，所以选择多个all项无效
        isSelectMutiAll: function () {
            var dimColumns = ChartReportMVC.Model.metaData.dimColumns;
            var canSelectAllDimCount = ChartReportMVC.Util.getCanSelectAllDimCount();
            var count = 0;
            for (var i = 0; i < dimColumns.length; i++) {
                var id = "#dim_" + dimColumns[i].name;
                var value = $(id).combobox('getValue');
                if (value == "all") {
                    count++;
                }
            }
            return count > canSelectAllDimCount;
        },
        setXAxisDimOptions: function () {
            var dimColumns = ChartReportMVC.Model.metaData.dimColumns;
            var exist = ChartReportMVC.Util.isExistAllOption();
            $('#xAxisDim').empty();
            for (var i = 0; i < dimColumns.length; i++) {
                var id = "#dim_" + dimColumns[i].name;
                var value = $(id).combobox('getValue');
                if (!exist || (exist && value == "all")) {
                    $("#xAxisDim").append("<option value='" + dimColumns[i].name + "'>" + dimColumns[i].text + "</option>");
                }
            }
        },
        getSecondAllDimName: function () {
            var showDimName = $("#xAxisDim").val();
            if (ChartReportMVC.Util.hasSecondAllDim()) {
                return $("#xAxisDim option").map(function () {
                    if ($(this).val() != showDimName) {
                        return $(this).val();
                    }
                }).get()[0];
            }
            return null;
        },
        hasSecondAllDim: function () {
            return (ChartReportMVC.Util.getCheckedStatColumnCount() == 1
            && ChartReportMVC.Util.isExistAllOption()
            && $("#xAxisDim option").size() == 2);
        },
        toChartData: function (chartType) {
            var chartData = {
                "title": ChartReportMVC.Util.getTitle(),
                "legends": ChartReportMVC.Util.getLegendData(),
                "categories": ChartReportMVC.Util.getCategories(),
                "series": ChartReportMVC.Util.getSeries(chartType)
            };
            return chartData;
        },
        getTitle: function () {
            var dimColumns = ChartReportMVC.Model.metaData.dimColumns;
            var names = [];
            for (var i = 0; i < dimColumns.length; i++) {
                var id = "#dim_" + dimColumns[i].name;
                var value = $(id).combobox('getValue');
                var text = dimColumns[i].text + ":" + value;
                names.push(text);
            }
            var checkedStatColumn = ChartReportMVC.Util.getCheckedStatColumn();
            if (checkedStatColumn) {
                names.push(checkedStatColumn.text);
            }

            return names.join(' ');
        },
        getLegendData: function () {
            return $.map(ChartReportMVC.Util.getLegends(), function (e) {
                return e.text;
            });
        },
        getLegends: function () {
            var showDimName = ChartReportMVC.Util.getSecondAllDimName();
            if (showDimName) {
                var id = "#dim_" + showDimName;
                var values = ChartReportMVC.Util.getComoboxValues(id);
                return $.map(values, function (value) {
                    if (value != "all") {
                        return {
                            "name": showDimName,
                            "text": value
                        };
                    }
                });
            }
            return ChartReportMVC.Util.getDisplayStatColumns();
        },
        getCategories: function () {
            return ChartReportMVC.Util.getCategory().data;
        },
        getCategory: function () {
            var dimName = $('#xAxisDim').val();
            var id = "#dim_" + dimName;
            var sortType = $("#sortType").val();
            var values = null;

            if (ChartReportMVC.Util.isExistAllOption()) {
                values = $.map(ChartReportMVC.Util.getComoboxValues(id), function (value) {
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
                "name": dimName,
                "data": values
            };
        },
        getSeries: function (chartType) {
            var dimColumns = ChartReportMVC.Model.metaData.dimColumns;
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
            var category = ChartReportMVC.Util.getCategory();
            var legends = ChartReportMVC.Util.getLegends();
            var categoryName = "#{" + category.name + "}";
            var series = [];
            for (var j = 0; j < legends.length; j++) {
                series.push({
                    name: legends[j].text,
                    type: chartType,
                    data: []
                });
            }

            if (ChartReportMVC.Util.hasSecondAllDim()) {
                var secondAllDimName = "#{" + ChartReportMVC.Util.getSecondAllDimName() + "}";
                var statColumnName = ChartReportMVC.Util.getCheckedStatColumn().name;
                for (var i = 0; i < category.data.length; i++) {
                    var tempKey = keyTemplate.replace(categoryName, category.data[i].replace('$', '*'));
                    for (var j = 0; j < legends.length; j++) {
                        var key = tempKey.replace(secondAllDimName, legends[j].text.replace('$', '*'));
                        var row = ChartReportMVC.Model.metaData.dataRows[key];
                        series[j].data.push(row ? row[statColumnName] : 0);
                    }
                }
                return series;
            }

            for (var i = 0; i < category.data.length; i++) {
                var key = keyTemplate.replace(categoryName, category.data[i].replace('$', '*'));
                var row = ChartReportMVC.Model.metaData.dataRows[key];
                for (var j = 0; j < legends.length; j++) {
                    var columnName = legends[j].name;
                    series[j].data.push(row ? row[columnName] : 0);
                }
            }
            return series;
        }
    }
};