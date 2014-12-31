var ReportTemplate = {
	Mode : {
		classic : 'classic',
		datatables : 'dt'
	},
	URL : {
		generate : XFrame.getContextPath() + '/report/generate',
		exportToExcel : XFrame.getContextPath() + '/report/exportexcel'
	},
	init : function() {
		$('#genarate').click(ReportTemplate.generate);
		$('#checkAllStatColumn').change(function() {
			if ($(this).is(":checked")) {
				$('.checkbox-item').prop('checked', true);
			} else {
				$('.checkbox-item').prop('checked', false);
			}
		});
		$('#startTime, #endTime').datepicker({
			dateFormat : 'yy-mm-dd',
			maxDate : 0,
			prevText : '<i class="fa fa-chevron-left"></i>',
			nextText : '<i class="fa fa-chevron-right"></i>',
			onSelect : function(dateText, inst) {
			}
		});
		$('.select2AutoComplete').select2();

		$('#btnExportToExcel').click(ReportTemplate.exportToExcel);
		$('#btnShowChart').click(ReportTemplate.showChart);
		$('#btnShowComment').click(ReportTemplate.showComment);
	},
	generate : function(mode,callback) {
		var loading = $.loading('数据正在加载中，请稍后...').show();
		$.ajax({
			type : "POST",
			url : ReportTemplate.URL.generate,
			data : $("#queryParamsForm").serialize(),
			dataType : "json",
			beforeSend : function() {
			},
			success : function(result) {
				$('#reportDiv').html(result.htmlTable);
				ReportTemplate.initTable(mode || ReportTemplate.Mode.classic);
				ReportTemplate.filterTable = ReportTemplate.renderFilterTable();
				if (callback instanceof Function) {
					callback();
				}
			},
			complete : function() {
				loading.destroy();
			}
		});
	},
	initTable : function(mode) {
		var table = $("#report");
		if (mode == ReportTemplate.Mode.classic) {
			return ReportTemplate.classicMode(table);
		}
		// 如果为dt模式但是表格存在跨行
		// 则转为经典表格模式
		if (ReportTemplate.hasRowSpan()) {
			return ReportTemplate.classicMode(table);
		}
		return ReportTemplate.datatablesMode(table);
	},
	// 表格中是否跨行
	hasRowSpan : function() {
		return $("#report>tbody>tr>td[rowspan][rowspan != '1']")
	},
	classicMode : function(table) {
		$("#report>tbody>tr").click(function() {
			$('#report .active').removeClass('active').removeAttr('title');
			$(this).addClass('active').attr('title', 'selected');
		});
		// 暂不支持跨行
		if (!ReportTemplate.hasRowSpan()) {
			table.tablesorter({
				sortInitialOrder : 'desc'
			});
			table.find('>thead>tr').attr({
				title : "点击可以排序"
			}).css({
				cursor : "pointer"
			});
		}
	},
	datatablesMode : function(table) {
		$('#report').addClass('table table-striped table-bordered');
		$('#report').dataTable({
			"scrollY" : "400",
			"scrollX" : true,
			"scrollCollapse" : true,
			"searching" : false,
			"language" : {
				processing : "数据正在加载中...",
				search : "查询:",
				lengthMenu : "每页显示 _MENU_ 条记录",
				info : "从 _START_ 到 _END_ /共 _TOTAL_ 条记录",
				infoEmpty : "从 0 到  0  共 0  条记录",
				infoFiltered : "(从 _MAX_ 条数据中检索)",
				infoPostFix : "",
				thousands : ",",
				loadingRecords : "数据加载中...",
				zeroRecords : "没有检索到数据",
				emptyTable : "没有数据",
				paginate : {
					first : "首页",
					previous : "前一页",
					next : "后一页",
					last : "尾页"
				},
				aria : {
					sortAscending : ": 升序",
					sortDescending : ": 降序"
				}
			}
		});
	},
	renderFilterTable : function() {
		var html = '<table>';
		html += '<tr><td><h3>表报名称</h3></td><td><h3>' + $('#rpTitle').text() + '</h3></td></tr>';
		$('#queryParamsForm').each(function() {
			var type = $(this).attr('data-type');
			if (type === 'date-range') {
				var input = $(this).find('.combo-text');
				html += '<tr><td><strong>时间范围</strong></td><td>' + input.eq(0).val() + '~' + input.eq(1).val() + '</td></tr>';
			} else if (type === 'checkbox') {
				html += '<tr><td><strong>筛选统计列</strong></td><td>';
				var rowChoose = [];
				$(this).find('input[type="checkbox"]:checked').each(function() {
					rowChoose.push($(this).attr('data-name'));
				});
				html += rowChoose.join('、');
				html += '</td></tr>';
			} else {
				var label = $(this).find('label').text().replace(':', '');
				var val = $(this).find('.combo-text').val();
				html += '<tr><td><strong>' + label + '</strong></td><td>' + val + '</td></tr>';
			}
		})
		html += '<tr></tr><tr></tr><tr></tr></table>';
		return html;
	},
	exportToExcel : function(e) {
		var htmlText = '';
		htmlText += (ReportTemplate.filterTable || '');
		htmlText += '<table>' + $('#report').html() + '</table>';
		var bytes = ReportTemplate.getTableBytes(htmlText);
		if (bytes > 2000000) {
			htmlText = "large";
		}
		var postData = $('#queryParamsForm').serializeObject();
		postData["htmlText"] = htmlText;
		var loading = $.loading('正在导出Excel中，请稍后...').show();
		$.fileDownload(ReportTemplate.URL.exportToExcel, {
			httpMethod : "POST",
			data : postData
		}).done(function() {
			loading.destroy();
		}).fail(function() {
			loading.destroy();
		});
	},
	showChart : function() {
	},
	showComment : function() {
	},
	getTableBytes : function(str) {
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
	}
};