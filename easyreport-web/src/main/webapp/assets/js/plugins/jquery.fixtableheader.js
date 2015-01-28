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
	var $e = $(this), offset = $e.offset(), $scroll = scroll ? $(scroll) : $(window), iswindow = "setInterval" in $scroll[0], top = iswindow ? 0
			: ($scroll.offset().top), newDiv = $("<div />").css({
		position : "fixed",
		width : $e.width() + 1
	}), scrollWdith = iswindow ? offset.top : offset.top - $scroll.offset().top;
	left = offset.left;
	var $thList = $e.find("thead");
	$thList.find("th").each(function() {
		$(this).css("width", $(this).width());
	});
	var cloneTh = $thList.clone(true);
	newDiv.append($("<table>").addClass("easyreport").append(cloneTh));
	$e.before(newDiv);

	$scroll.bind("scroll", function() {
		if ($scroll.scrollTop() > scrollWdith) {
			newDiv.show();
			newDiv.css({
				"top" : top,
				left : -($scroll.scrollLeft() - left)
			})
		} else {
			newDiv.hide();
		}
	});
};