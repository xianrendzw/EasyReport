$(function() {
	$('#btnGenarate').click(function(e) {
		ReportTemplate.generate();
	});
	$('#btnShowChart').click(ReportTemplate.showChart);
	$('#btnToggleSkins').click(function(e) {
		ReportTemplate.toggleSkin(e);
	});
	$('#btnShowComment').mouseover(function(e) {
		ReportTemplate.showComment(e);
	});
	$('#btnShowComment').mouseleave(function(e) {
		ReportTemplate.closeComment(e);
	});
	$('#checkAllStatColumn').click(ReportTemplate.checkedAllStatColumn);
	
	$('#btnExportToExcel').on("click", ReportTemplate.exportToExcel);
	$('#btnFullScreen').on("click", ReportTemplate.fullScreen);
	
	ReportTemplate.generate();
});

var ReportTemplate = function() {
};

ReportTemplate.initTable = function() {
	var table = $("#report");
	ReportTemplate.addToggleRowEvents();
	// 暂时不支持跨行排序
	var rowspans = $("#report>tbody>tr>td[rowspan][rowspan != '1']");
	table.data('isSort', rowspans.length === 0).fixScroll();
	if (rowspans && rowspans.length > 0) {
		return;
	}

	table.tablesorter({
		sortInitialOrder : 'desc'
	});

	table.find('>thead>tr').attr({
		title : "点击可以排序"
	}).css({
		cursor : "pointer"
	});
};

ReportTemplate.addToggleRowEvents = function() {
	$("#report>tbody>tr").click(function() {
		$('#report .selected').removeClass('selected').removeAttr('title');
		$(this).addClass('selected').attr('title', 'selected');
	});
};

ReportTemplate.generate = function() {
	$.ajax({
		type : "POST",
		url : XFrame.getContextPath() + '/report/generate',
		data : $("#templateFrom").serialize(),
		dataType : "json",
		beforeSend : function() {
			$('#loadingText').html("报表正在生成中, 请稍等...");
			$('#loading').show();
		},
		success : function(result) {
			$('#reportDiv').html(result.htmlTable);
			ReportTemplate.initTable();
			ReportTemplate.filterTable = ReportTemplate.renderFilterTable();
		},
		complete : function() {
			$('#loading').hide();
		}
	});
};

// 讲报表上面的过滤信息拼成table，用于写入excel中
ReportTemplate.renderFilterTable = function() {
	var html = '<table>';
	html += '<tr><td><h3>表报名称</h3></td><td><h3>' + $('#rpTitle').text() + '</h3></td></tr>';
	$('#templateFrom .j-item').each(
			function() {
				var type = $(this).attr('data-type');
				if (type === 'date-range') {
					var input = $(this).find('.combo-text');
					html += '<tr><td><strong>时间范围</strong></td><td>' + input.eq(0).val() + '~' + input.eq(1).val()
							+ '</td></tr>';
				} else if (type === 'checkbox') {
					html += '<tr><td><strong>筛选统计列</strong></td><td>';
					var rowChoose = [];
					$(this).find('input[type="checkbox"]:checked').each(function() {
						rowChoose.push($(this).attr('data-name'));
					})
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
};

ReportTemplate.exportToExcel = function(e) {
	var htmlText = '';
	htmlText += (ReportTemplate.filterTable || '');
	htmlText += '<table>' + $('#report').html() + '</table>';
	var bytes = ReportTemplate.getBytes(htmlText);
	if (bytes > 2000000) {
		htmlText = "large";
	}
	var postUrl = XFrame.getContextPath() + '/report/exportexcel';
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
	var href = XFrame.getContextPath() + "/static/report/themes/default.css";
	if (regex.test($('#skin').attr("href"))) {
		href = XFrame.getContextPath() + "/static/report/themes/clear.css";
	}
	$('#skin').attr({
		"href" : href
	});
};

ReportTemplate.fullScreen = function() {
	var uid = $('#rpUid').val();
	var url = XFrame.getContextPath() + '/report/uid/' + uid;
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