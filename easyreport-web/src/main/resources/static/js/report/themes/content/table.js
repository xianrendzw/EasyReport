var TableReport = {
    init: function () {
        TableReportMVC.View.initControl();
        TableReportMVC.View.bindEvent();
        TableReportMVC.View.bindValidate();
        TableReportMVC.View.initData();
    }
};

var TableReportCommon = {
    baseUrl: EasyReport.ctxPath + '/report'
};

var TableReportMVC = {
    URLs: {
        getData: {
            url: TableReportCommon.baseUrl + '/table/getData.json',
            method: 'POST'
        },
        exportExcel: {
            url: TableReportCommon.baseUrl + '/table/exportExcel',
            method: 'POST'
        }
    },
    View: {
        initControl: function () {
            $.parser.parse('#table-report-div');
        },
        bindEvent: function () {
            $('#btn-generate').click(TableReportMVC.Controller.generate);
            $('#btn-export-excel').click(TableReportMVC.Controller.exportToExcel);
            $("#table-report-columns input[name='checkAllStatColumn']").click(function (e) {
                var checked = $("#table-report-columns input[name='checkAllStatColumn']").prop("checked");
                $("#table-report-columns input[name='statColumns']").prop("checked", checked);
            });
        },
        bindValidate: function () {
        },
        initData: function () {
            TableReportMVC.Controller.generate(TableReportMVC.Model.Mode.classic, null);
        }
    },
    Model: {
        Mode: {
            classic: 'classic',// 经典表格模式
            datatables: 'dt'// datatables控件表格模式
        }
    },
    Controller: {
        generate: function (mode, callback) {
            $('#table-report-isRowSpan').val($('#table-report-isMergeRow').prop('checked'));
            $.ajax({
                type: "POST",
                url: TableReportMVC.URLs.getData.url,
                data: $("#table-report-form").serialize(),
                dataType: "json",
                beforeSend: function () {
                    $.messager.progress({
                        title: '请稍后...',
                        text: '报表正在生成中...',
                    });
                },
                success: function (result) {
                    if (!result.code) {
                        $('#table-report-htmltext-div').html(result.data.htmlTable);
                        TableReportMVC.Util.render(mode || TableReportMVC.Model.Mode.classic);
                        TableReportMVC.Util.filterTable = TableReportMVC.Util.renderFilterTable(result.data);
                        if (callback instanceof Function) {
                            callback();
                        }
                    } else {
                        $.messager.alert('操作提示', result.data.msg, 'error');
                    }
                },
                complete: function () {
                    $.messager.progress("close");
                }
            });
        },
        exportToExcel: function (e) {
            var htmlText = '';
            htmlText += (TableReportMVC.Util.filterTable || '');
            htmlText += '<table>' + $('#easyreport').html() + '</table>';

            var bytes = TableReportMVC.Util.getExcelBytes(htmlText);
            if (bytes > 2000000) {
                htmlText = "large";
            }

            var url = TableReportMVC.URLs.exportExcel.url;
            var data = $('#table-report-form').serializeObject();
            data["htmlText"] = htmlText;

            $.messager.progress({
                title: '请稍后...',
                text: '报表正在生成中...',
            });
            $.fileDownload(url, {
                httpMethod: "POST",
                data: data
            }).done(function () {
                $.messager.progress("close");
            }).fail(function () {
                $.messager.progress("close");
            });
            e.preventDefault();
        }
    },
    Util: {
        // 表格中是否跨行
        hasRowSpan: function () {
            var rowspans = $("#easyreport>tbody>tr>td[rowspan]");
            return (rowspans && rowspans.length);
        },
        render: function (mode) {
            var table = $("#easyreport");
            return TableReportMVC.Util.renderClassicTable(table);

            /*if (mode == TableReportMVC.Model.Mode.classic) {
             return TableReportMVC.renderClassicTable(table);
             }
             // 如果为dt模式但是表格存在跨行
             // 则转为经典表格模式,因为datatables控件不支持跨行
             if (TableReportMVC.hasRowSpan()) {
             return TableReportMVC.renderClassicTable(table);
             }
             return TableReportMVC.renderDatatables(table);*/
        },
        renderClassicTable: function (table) {
            $("#easyreport>tbody>tr").click(function () {
                $('#easyreport .selected').removeClass('selected').removeAttr('title');
                $(this).addClass('selected');
            });
            $('#easyreport>tbody>tr').mouseover(function (e) {
                $(this).addClass('selected');
            });
            $('#easyreport>tbody>tr').mouseleave(function (e) {
                $('#easyreport .selected').removeClass('selected').removeAttr('title');
            });

            var noRowSpan = !TableReportMVC.Util.hasRowSpan();
            table.data('isSort', noRowSpan).fixScroll();

            //如果表格中没有跨行rowspan(暂不支持跨行)
            if (noRowSpan) {
                table.tablesorter({
                    sortInitialOrder: 'desc'
                });
                table.find('easyreport>thead>tr').attr({
                    title: "点击可以排序"
                }).css({
                    cursor: "pointer"
                });
            }
        },
        renderDatatables: function (table) {
            $('#easyreport').removeClass("easyreport");
            $('#easyreport').addClass('table table-striped table-bordered');
            var dt = $('#easyreport').dataTable({
                "scrollY": "758",
                "scrollX": true,
                "scrollCollapse": true,
                "searching": false,
                "pageLength": 100,
                "lengthMenu": [50, 100, 200, 500, 1000],
                "language": {
                    processing: "数据正在加载中...",
                    search: "查询:",
                    lengthMenu: "每页显示 _MENU_ 条记录",
                    info: "从 _START_ 到 _END_ /共 _TOTAL_ 条记录",
                    infoEmpty: "从 0 到  0  共 0  条记录",
                    infoFiltered: "(从 _MAX_ 条数据中检索)",
                    infoPostFix: "",
                    thousands: ",",
                    loadingRecords: "数据加载中...",
                    zeroRecords: "没有检索到数据",
                    emptyTable: "没有数据",
                    paginate: {
                        first: "首页",
                        previous: "前一页",
                        next: "后一页",
                        last: "尾页"
                    },
                    aria: {
                        sortAscending: ": 升序",
                        sortDescending: ": 降序"
                    }
                }
            });
            $('#easyreport tbody').on('click', 'tr', function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                }
                else {
                    dt.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
            });
        },
        // 将报表上面的过滤信息拼成table，用于写入excel中
        renderFilterTable: function (result) {
            var html = '<table>';
            html += '<tr><td align="center" colspan="' + result.metaDataColumnCount + '"><h3>' + $('#table-report-name').text() + '</h3></td></tr>';
            html += '<tr><td align="right" colspan="' + result.metaDataColumnCount + '"><h3>导出时间:' + TableReportMVC.Util.getCurrentTime() + '</h3></td></tr>';
            $('#table-report-form .j-item').each(function () {
                var type = $(this).attr('data-type');
                if (type === 'date-range') {
                    var input = $(this).find('.combo-text');
                    html += '<tr><td align="right" colspan="' + result.metaDataColumnCount + '"><strong>时间范围:</strong>' + input.eq(0).val() + '~' + input.eq(1).val() + '</td></tr>';
                } else if (type === 'checkbox') {
                    html += '<tr><td align="right" colspan="' + result.metaDataColumnCount + '"><strong>筛选统计列:</strong>';
                    var rowChoose = [];
                    $(this).find('input[type="checkbox"]:checked').each(function () {
                        rowChoose.push($(this).attr('data-name'));
                    })
                    html += rowChoose.join('、');
                    html += '</td></tr>';
                }
                else if (new RegExp('datebox').test($(this).find("input").attr("class"))) {
                    var label = $(this).find('label').text().replace(':', '');
                    var val = $(this).find("input").attr("value");
                    if (!val) {
                        val = $(this).find('.combo-text').val();
                    }
                    html += '<tr><td><strong>' + label + '</strong></td><td>' + val + '</td></tr>';
                }
                else {
                    var label = $(this).find('label').text().replace(':', '');
                    var val = $(this).find('.combo-text').val();
                    if (!val) {
                        val = $(this).find("input").attr("value");
                    }
                    html += '<tr><td><strong>' + label + '</strong></td><td>' + val + '</td></tr>';
                }
            })
            html += '<tr></tr><tr></tr><tr></tr></table>';
            return html;
        },
        getExcelBytes: function (str) {
            var totalLength = 0;
            var i;
            var charCode;
            for (i = 0; i < str.length; i++) {
                charCode = str.charCodeAt(i);
                if (charCode < 0x007f) {
                    totalLength = totalLength + 1;
                } else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) {
                    totalLength += 2;
                } else if ((0x0800 <= charCode) && (charCode <= 0xffff)) {
                    totalLength += 3;
                }
            }
            return totalLength;
        },
        getCurrentTime: function currentTime() {
            var d = new Date(), str = '';
            str += d.getFullYear() + '-';
            str += d.getMonth() + 1 + '-';
            str += d.getDate() + ' ';
            str += d.getHours() + ':';
            str += d.getMinutes() + ':';
            str += d.getSeconds() + '';
            return str;
        },
        filterTable: null
    }
};
