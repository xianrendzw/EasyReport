jQuery.fn.fixTableHeader = function(gv, scrollHeight) {
	var sizenmu = [];// 放每个td的数组
	var initWidth = $(gv).width();
	$(gv).find("thead th").each(function() {
		$(this).css("width", $(this).width())
	});
	$(gv).find("tbody tr").eq(0).children().each(function(index, value) {

		$(this).css("width", $(this).outerWidth());
	})
	var gvn = $(gv).clone(true).removeAttr("id");
	var gvn = $(gvn).find("thead");
	var table = $("<table>").addClass("easyreport");
	table.append(gvn);
	var $gv = $(gv);
	$gv.before(table);
	$gv.wrap("<div style='height:" + scrollHeight
			+ "px; overflow-y: scroll;overflow-x:hidden;  padding: 0;margin: 0;'></div>");

	$gv.find("tbody tr").eq(0).children().each(function(index, value) {
		sizenmu.push($(this).outerWidth())
		$(this).css("width", $(this).outerWidth() - 21);
	})
	$gv.find("thead").remove();

	var c = $(gvn).find("tr:last").children();
	var firstc = sizenmu.length - c.size();
	sizenmu.pop();
	$gv.parent().width(initWidth)// 设置宽度
	$(gvn).find("tr:first").children().each(function(i) {
		if (i < firstc) {
			$(this).width(sizenmu[i] - 21);
		}

	})
	$(gvn).find("tr:last").children().each(function(index, value) {
		$(this).width(sizenmu[firstc] - 21);
		firstc = firstc + 1;
	});
};
jQuery.fn.fixScroll = function(scroll) {
	var $e = $(this), 
		offset = $e.offset(), 
		$scroll = scroll ? $(scroll) : $(window), 
		iswindow = "setInterval" in $scroll[0], 
		top = iswindow ? 0 : ($scroll.offset().top), 
		newDiv = $("<div />").css({
			position : "fixed",
			"z-index": 2,
			width : $e.width() + 1
		}), 
		scrollWdith = iswindow ? offset.top : offset.top - $scroll.offset().top,
		left = offset.left;

	var first_TH = $(this).find('thead th:first-child');
	var first_TH_width = first_TH.width() + 7;
	var first_TH_height = first_TH.height();

	var $thList = $e.find("thead");
	$thList.find("th").each(function() {
		$(this).css("width", $(this).width()).data('element', $(this));
	});

	var cloneTh = $thList.clone(true);
	if($(this).data('isSort')) {
		cloneTh.css('cursor', 'pointer');
	}
	newDiv.append($("<table>").addClass("easyreport").append(cloneTh));
	$e.before(newDiv);

	cloneTh.on('click', 'th', function() {
		$(this).data('element').trigger('sort');
	})

	$(this).bind('sortEnd', function(i, table) {
		fixedRowInner.find('table tbody').html($(this).find('tbody').html());
	})

	var tableCopy = $(this).clone();
	var copyFirst_TR = tableCopy.find('thead tr:first-child');
	var fixRowCount = tableCopy.find('.c:first .back').length || 1;
	var copyFirst_TH = copyFirst_TR.find('th');
	var fixRowWidth = 0, prev_TH, width;
	for(var i = 0, len = fixRowCount; i < len; i++) {
		width = $thList.find('th').eq(i).width();
		fixRowWidth += width + 7;
	}
	if(fixRowCount === 1) {
		var first_TH_copy = copyFirst_TH.eq(0).clone().css({
			"position": "fixed",
			"top": 0,
			"left": 0,
			"width": width,
			"height": first_TH_height,
			"line-height": first_TH_height + 'px',
			"z-index":5,
			"display": "none"
		});
		copyFirst_TR.prepend(first_TH_copy);
		first_TH_copy.bind('click', function() {
			first_TH.trigger('sort');
		});
		copyFirst_TH.bind('click', function() {
			first_TH.trigger('sort');
		});
	}
 	var fixedRow = $('<div style="position:absolute;top:0;left:0;overflow:hidden;width:' + (fixRowWidth) + 'px;"></div>');
	var fixedRowInner = $('<div style="width:' + ($e.width() + 1) + 'px;"></div>');
	fixedRowInner.append(tableCopy);
	fixedRow.append(fixedRowInner);
	$('#reportDiv').css('position', 'relative').append(fixedRow);

	var fixRow_TH = fixedRow.find('th');
	$scroll.bind("scroll", function() {
		var scrollLeft = $scroll.scrollLeft();
		if ($scroll.scrollTop() > scrollWdith) {
			newDiv.show();
			newDiv.css({
				"top" : top,
				"left" : -(scrollLeft - left)
			});
			if(first_TH_copy){first_TH_copy.show();}
		} else {
			newDiv.hide();
			if(first_TH_copy){first_TH_copy.hide();}
		}
		if(scrollLeft > 5) {
			fixedRow.css('left', (scrollLeft - left) + 'px').show();
		} else {
			fixedRow.hide();
		}
	});
};