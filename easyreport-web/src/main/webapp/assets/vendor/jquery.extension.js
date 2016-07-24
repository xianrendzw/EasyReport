$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push($.trim(this.value) || '');
		} else {
			o[this.name] = $.trim(this.value) || '';
		}
	});
	return o;
};