$(function() {
	$('#btnGenarate').click(ReportTemplate.generate);
	$('#btnShowChart').click(ReportTemplate.showChart);
	$('#btnToggleSkins').click(ReportTemplate.toggleSkin);
	$('#btnShowComment').mouseover(ReportTemplate.showComment);
	$('#btnShowComment').mouseleave(ReportTemplate.closeComment);
	$('#checkAllStatColumn').click(ReportTemplate.checkedAllStatColumn);
	$('#btnExportToExcel').click(ReportTemplate.exportToExcel);
	$('#btnFullScreen').click(ReportTemplate.fullScreen);

	ReportTemplate.generate(ReportTemplate.Mode.classic,null);
});

var ReportTemplate = function() {
};

ReportTemplate.Mode = {
	classic : 'classic',// 经典表格模式
	datatables : 'dt'// datatables控件表格模式
};

ReportTemplate.URL = {
	generate : WebAppRequest.getContextPath() + '/report/generate',
	exportToExcel : WebAppRequest.getContextPath() + '/report/exportExcel'
};

ReportTemplate.generate = function(mode, callback) {
	$('#isRowSpan').val($('#isMergeRow').prop('checked'));
	$.ajax({
		type : "POST",
		url : ReportTemplate.URL.generate,
		data : $("#templateFrom").serialize(),
		dataType : "json",
		beforeSend : function() {
			$('#loadingText').html("报表正在生成中, 请稍等...");
			$('#loading').show();
		},
		success : function(result) {
			$('#reportDiv').html(result.htmlTable);
			ReportTemplate.initTable(mode || ReportTemplate.Mode.classic);
			ReportTemplate.filterTable = ReportTemplate.renderFilterTable(result);
			if (callback instanceof Function) {
				callback();
			}
		},
		complete : function() {
			$('#loading').hide();
		}
	});
};

ReportTemplate.initTable = function(mode) {
	var table = $("#easyreport");
	return ReportTemplate.renderClassicReport(table);
	
	/*if (mode == ReportTemplate.Mode.classic) {
		return ReportTemplate.renderClassicReport(table);
	}
	// 如果为dt模式但是表格存在跨行
	// 则转为经典表格模式,因为datatables控件不支持跨行
	if (ReportTemplate.hasRowSpan()) {
		return ReportTemplate.renderClassicReport(table);
	}
	return ReportTemplate.renderDatatablesReport(table);*/
};

// 表格中是否跨行
ReportTemplate.hasRowSpan = function() {
	var rowspans = $("#easyreport>tbody>tr>td[rowspan]");
	return (rowspans && rowspans.length > 0);
};

ReportTemplate.renderClassicReport = function(table) {
	$("#easyreport>tbody>tr").click(function() {
		$('#easyreport .selected').removeClass('selected').removeAttr('title');
		$(this).addClass('selected');
	});
	$('#easyreport>tbody>tr').mouseover(function(e) {
		$(this).addClass('selected');
	});
	$('#easyreport>tbody>tr').mouseleave(function(e) {
		$('#easyreport .selected').removeClass('selected').removeAttr('title');
	});

	var noRowSpan = !ReportTemplate.hasRowSpan();
	table.data('isSort', noRowSpan).fixScroll();
	
	//如果表格中没有跨行rowspan(暂不支持跨行)
	if (noRowSpan) {
		table.tablesorter({
			sortInitialOrder : 'desc'
		});
		table.find('>thead>tr').attr({
			title : "点击可以排序"
		}).css({
			cursor : "pointer"
		});
	}
};

ReportTemplate.renderDatatablesReport = function(table) {
	$('#easyreport').removeClass("easyreport");
	$('#easyreport').addClass('table table-striped table-bordered');
	var dt = $('#easyreport').dataTable({
		"scrollY" : "758",
		"scrollX" : true,
		"scrollCollapse" : true,
		"searching" : false,
		"pageLength": 100,
		"lengthMenu": [ 50, 100, 200, 500, 1000 ],
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
	$('#easyreport tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
        	dt.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
};

// 将报表上面的过滤信息拼成table，用于写入excel中
ReportTemplate.renderFilterTable = function(result) {
	var html = '<table>';
	html += '<tr><td align="center" colspan="'+result.metaDataColumnCount+'"><h3>' + $('#rpTitle').text() + '</h3></td></tr>';
	html += '<tr><td align="right" colspan="'+result.metaDataColumnCount+'"><h3>导出时间:' + currentTime()+ '</h3></td></tr>';
	$('#templateFrom .j-item').each(function() {
		var type = $(this).attr('data-type');
		if (type === 'date-range') {
			var input = $(this).find('.combo-text');
			html += '<tr><td align="right" colspan="'+result.metaDataColumnCount+'"><strong>时间范围:</strong>' + input.eq(0).val() + '~' + input.eq(1).val() + '</td></tr>';
		} else if (type === 'checkbox') {
			html += '<tr><td align="right" colspan="'+result.metaDataColumnCount+'"><strong>筛选统计列:</strong>';
			var rowChoose = [];
			$(this).find('input[type="checkbox"]:checked').each(function() {
				rowChoose.push($(this).attr('data-name'));
			})
			html += rowChoose.join('、');
			html += '</td></tr>';
		}
		else if (new RegExp('datebox').test($(this).find("input").attr("class"))) {
			var label = $(this).find('label').text().replace(':', '');
			var val = $(this).find("input").attr("value");
			if(!val){
				val = $(this).find('.combo-text').val();
			}
			html += '<tr><td><strong>' + label + '</strong></td><td>' + val + '</td></tr>';
		}
		else {
			var label = $(this).find('label').text().replace(':', '');
			var val = $(this).find('.combo-text').val();
			if(!val){
				val = $(this).find("input").attr("value");
			}
			html += '<tr><td><strong>' + label + '</strong></td><td>' + val + '</td></tr>';
		}
	})
	html += '<tr></tr><tr></tr><tr></tr></table>';
	return html;
};

ReportTemplate.exportToExcel = function(e) {
	var htmlText = '';
	htmlText += (ReportTemplate.filterTable || '');
	htmlText += '<table>' + $('#easyreport').html() + '</table>';
	var bytes = ReportTemplate.getBytes(htmlText);
	if (bytes > 2000000) {
		htmlText = "large";
	}
	var postUrl = ReportTemplate.URL.exportToExcel;
	var postData = $('#templateFrom').serializeObject();
	postData["htmlText"] = htmlText;

	$('#loadingText').html("正在导出Excel中, 请稍等...");
	$('#loading').show();
	$.fileDownload(postUrl, {
		httpMethod : "POST",
		data : postData
	}).done(function() {
		$('#loading').hide();
	}).fail(function() {
		$('#loading').hide();
	});
	e.preventDefault();
};

ReportTemplate.showComment = function(e) {
	$('#rpCommentTip').css({
		"display" : "block",
		"backgroundColor" : '#666',
		"borderColor" : '#666'
	});
};

ReportTemplate.closeComment = function(e) {
	$('#rpCommentTip').css({
		"display" : "none"
	});
};

ReportTemplate.toggleSkin = function(e) {
	var regex = /default/g;
	var href = WebAppRequest.getContextPath() + "/assets/modules/report/themes/default.css";
	if (regex.test($('#skin').attr("href"))) {
		href = WebAppRequest.getContextPath() + "/assets/modules/report/themes/clear.css";
	}
	$('#skin').attr({
		"href" : href
	});
};

ReportTemplate.fullScreen = function() {
	var uid = $('#rpUid').val();
	var url = WebAppRequest.getContextPath() + '/report/uid/' + uid;
	ReportTemplate.winOpen(url, uid);
};

ReportTemplate.showChart = function(e) {
	var title = $('#rpName').val() + "(图表)";
	var id = $('#rpId').val();
	var uid = $('#rpUid').val();
	parent.showChart(title, id, uid);
};

ReportTemplate.checkedAllStatColumn = function(e) {
	var checked = $("input[name='checkAllStatColumn']").prop("checked");
	$("input[name='statColumns']").prop("checked", checked);
};

ReportTemplate.winOpen = function(url, name) {
	var width = window.screen.availWidth - 10;
	var height = window.screen.availHeight - 60;
	window.open(url, name, 'width=' + width + ',height=' + height
			+ ',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
};

ReportTemplate.getBytes = function(str) {
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
};

function currentTime(){
	var d = new Date(),str = '';
	str += d.getFullYear()+'-';
	str  += d.getMonth() + 1+'-';
	str  += d.getDate()+' ';
	str += d.getHours()+':';
	str  += d.getMinutes()+':';
	str+= d.getSeconds()+'';
	return str;
};