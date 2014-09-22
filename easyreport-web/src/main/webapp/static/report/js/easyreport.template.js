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
	$('#btnExportToExcel').on("click", ReportTemplate.exportToExcel);
	$('#btnFullScreen').on("click", ReportTemplate.fullScreen);

	ReportTemplate.generate();
});

var ReportTemplate = function() {
};

ReportTemplate.initTable = function() {
	$("#report").fixScroll();
	ReportTemplate.addToggleRowEvents();
	// 暂时不支持跨行排序
	var rowspans = $("#report>tbody>tr>td[rowspan][rowspan != '1']");
	if (rowspans && rowspans.length > 0) {
		return;
	}

	$('#report').tablesorter({
		sortInitialOrder : 'desc'
	});
	$('#report>thead>tr').attr({
		title : "点击可以排序"
	});
	$('#report>thead>tr').css({
		cursor : "pointer"
	});
};

ReportTemplate.addToggleRowEvents = function() {
	$("#report>tbody>tr").click(function() {
		$.each($("#report>tbody>tr"), function(i, n) {
			$(n).css({
				"background" : ""
			});
			$(n).removeAttr("title");
		});
		$(this).css({
			"background" : "#fddc30"
		});
		$(this).attr({
			title : "selected"
		});
	});

	$('#report>tbody>tr').mouseover(function(e) {
		$(this).css({
			"background" : "#fddc30"
		});
	});
	$('#report>tbody>tr').mouseleave(function(e) {
		var title = $(this).attr("title");
		if (title && title == "selected") {
			return;
		}
		$(this).css({
			"background" : ""
		});
	});
};

ReportTemplate.generate = function() {
	$.ajax({
		type : "POST",
		url : XFrame.getContextPath() + '/reporting/generate',
		data : $("#templateFrom").serialize(),
		dataType : "json",
		beforeSend : function() {
			$('#loadingText').html("报表正在生成中, 请稍等...");
			$('#loading').show();
		},
		success : function(result) {
			$('#reportDiv').html(result.htmlTable);
			ReportTemplate.initTable();
		},
		complete : function() {
			$('#loading').hide();
		}
	});
};

ReportTemplate.exportToExcel = function(e) {
	var htmlText = $('#reportDiv').html();
	var bytes = ReportTemplate.getBytes(htmlText);
	if (bytes > 2000000) {
		htmlText = "large";
	}

	var postUrl = XFrame.getContextPath() + '/reporting/exportexcel';
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
	var url = XFrame.getContextPath() + '/reporting/uid/' + uid;
	ReportTemplate.winOpen(url, uid);
};

ReportTemplate.showChart = function(e) {
	var title = $('#rpName').val() + "(图表)";
	var id = $('#rpId').val();
	var uid = $('#rpUid').val();
	parent.showChart(title, id, uid);
};

ReportTemplate.checkedAllStatColumn = function() {
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