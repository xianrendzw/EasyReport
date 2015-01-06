jQuery.fn.extend({
	enable : function() {
		return this.each(function() {
			this.disabled = false;
		});
	},
	disable : function() {
		return this.each(function() {
			this.disabled = true;
		});
	}
});
$.fn.paramMaxLength = function(value, length) {
	if (typeof (length) == "number" && typeof (value) != "undefined" && value.length >= length) {
		return true;
	}
	return false;
};
$.fn.paramMinLength = function(value, length) {
	if (typeof (length) == "number" && typeof (value) != "undefined" && value.length < length) {
		return true;
	}
	return false;
};
$.fn.paramMaxMinLength = function(value, param) {
	if ($.fn.paramMaxLength(value, param[0]) || $.fn.paramMinLength(value, param[1])) {
		return true;
	}
	return false;
};

$
		.extend(
				$.fn.validatebox.defaults.rules,
				{
					minLength : {
						validator : function(value, param) {
							return $.trim(value) >= param[0];
						},
						message : '长度最少 {0}.'
					},
					maxLength : {
						validator : function(value, param) {
							return $.trim(value).length <= param[0];
						},
						message : '长度最大 {0}.'
					},
					ip : {
						validator : function(value) {
							var ip = /^(([3-9]\d?)|((1\d?|2[0-1]?)\d?)|22([0-3]?))\.((([3-9]?\d)|((1\d?|2[0-4]?)\d?)|25([0-5]?))\.){2}(([3-9]\d?)|((1\d?|2[0-4]?)\d?)|25([0-4]?))$/;
							return ip.test($.trim(value));
						},
						message : 'IP地址.如：10.10.10.10'
					},
					negativeInteger : {
						validator : function(value, param) {
							if ($.fn.paramMaxMinLength(value, param)) {
								return false;
							}
							var integer = /^[-]?[0-9]+$/;
							return integer.test($.trim(value));
						},
						message : '负整数,长度{0}.如-89'
					},
					positiveInteger : {
						validator : function(value, param) {
							if (value.trim() == '') {
								return false;
							}
							if ($.fn.paramMaxMinLength(value, param)) {
								return false;
							}
							var integer = /^\+?[1-9][0-9]*$/;
							return integer.test($.trim(value));
						},
						message : '正整数,长度{0}. 如+89,89'
					},
					/**
					 * 验证字母、数字和下划线，如ab_12
					 */
					english : {
						validator : function(value, param) {
							if (value.trim() == '') {
								return false;
							}
							if ($.fn.paramMaxMinLength(value, param)) {
								return false;
							}
							var number = /^[A-Za-z0-9_-]+$/;
							return number.test($.trim(value));
						},
						message : '英文和数字组合,长度{0}.'
					},
					/**
					 * 验证一个字符串由数字0－9的组成
					 */
					number : {
						validator : function(value, param) {
							if (value.trim() == '') {
								return false;
							}
							if ($.fn.paramMaxMinLength(value, param)) {
								return false;
							}
							var number = /^[0-9]+$/;
							return number.test($.trim(value));
						},
						message : '数字0－9的组成,长度{0}.'
					},
					phone : {
						validator : function(value) {
							var phone = /^(?:[1-9]|(0[1-9](0|[1-9])\d{0,1})|\((0[1-9](0|[1-9])\d{0,1})\))(?:\d{6,8}|\d{6,8}-\d{2,6})$/;
							return phone.test($.trim(value));
						},
						message : "电话非法.<br/>格式：电话+'-'+分机号、区号+电话+'-'+分机号、(区号)+电话+'-'+分机号"
					},
					mobilePhone : {
						validator : function(value) {
							var phone = /^(?:1[2,3,5,8,9]|20)\d{9}$/;
							return phone.test($.trim(value));
						},
						message : '手机号非法.<br/>适应号段：12,13,15,18,19,20'
					},
					zipCode : {
						validator : function(value) {
							var phone = /^(\d){6}$/;
							return phone.test($.trim(value));
						},
						message : '邮编非法.如：100055'
					},
					url : {
						validator : function(value) {
							var patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
							return patrn.test($.trim(value));
						},
						message : 'url地址非法'
					}
				});
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

$.fn.clearNull = function(row) {
	if (row == undefined || row == null) {
		return;
	}
	if (typeof (row) == "object") {
		for ( var t in row) {
			if (row[t] == null || row[t] == 'null') {
				row[t] = '';
			}
		}
	}
};

$.fn.formatDateHIS = function() {
	this.datebox({
		currentText : '今天',
		closeText : '关闭',
		disabled : false,
		required : false,
		needTime : true,
		formatter : function(date) {
			date = new Date();
			if (date instanceof Date) {
				var h = (new Date()).getHours();
				h = (h < 10) ? ("0" + h) : h;
				var i = (new Date()).getMinutes();
				i = (i < 10) ? ("0" + i) : i;
				var s = (new Date()).getSeconds();
				s = (s < 10) ? ("0" + s) : s;
				return h + ':' + i + ':' + s;
			}
			return '';
		}
	});
};
$.fn.formatDateYMD = function() {
	this.datebox({
		currentText : '今天',
		closeText : '关闭',
		disabled : false,
		required : false,
		needTime : true,
		showTime : true,
		formatter : function(date) {
			if (date instanceof Date) {
				var y = date.getFullYear();
				var m = date.getMonth() + 1;
				m = (m < 10) ? ("0" + m) : m;
				var d = date.getDate();
				d = (d < 10) ? ("0" + d) : d;
				return y + '-' + m + '-' + d;
			}
			return '';
		}
	});
};
$.fn.formatDateYMDHIS = function() {
	this.datebox({
		currentText : '今天',
		closeText : '关闭',
		disabled : false,
		required : false,
		needTime : true,
		formatter : function(date) {
			if (date instanceof Date) {
				var y = date.getFullYear();
				var m = date.getMonth() + 1;
				m = (m < 10) ? ("0" + m) : m;
				var d = date.getDate();
				d = (d < 10) ? ("0" + d) : d;
				var h = (new Date()).getHours();
				h = (h < 10) ? ("0" + h) : h;
				var i = (new Date()).getMinutes();
				i = (i < 10) ? ("0" + i) : i;
				var s = (new Date()).getSeconds();
				s = (s < 10) ? ("0" + s) : s;
				var ms = date.getMilliseconds();
				return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
			}
			return '';
		}
	});
};
$.fn.formatDate = function() {
	this.datebox({
		currentText : '今天',
		closeText : '关闭',
		disabled : false,
		required : false,
		needTime : true,
		formatter : function(date) {
			if (date instanceof Date) {
				var y = date.getFullYear();
				var m = date.getMonth() + 1;
				m = (m < 10) ? ("0" + m) : m;
				var d = date.getDate();
				d = (d < 10) ? ("0" + d) : d;
				return y + "" + m + "" + d;
			}
			return '';
		}
	});
};
$.fn.formatDateYM = function() {
	this.datebox({
		currentText : '今天',
		closeText : '关闭',
		disabled : false,
		required : false,
		needTime : true,
		formatter : function(date) {
			if (date instanceof Date) {
				var y = date.getFullYear();
				var m = date.getMonth() + 1;
				m = (m < 10) ? ("0" + m) : m;
				var d = date.getDate();
				d = (d < 10) ? ("0" + d) : d;
				return y + '' + m;
			}
			return '';
		}
	});
};
$.fn.frameVar = function(callUrl) {
	return '<iframe scrolling="no" frameborder="0"  src="' + callUrl + '" style="width:100%;height:100%;"></iframe>';
};

$.fn.frameHtml = function(callUrl) {
	this.html(this.frameVar(callUrl));
};

$.fn.dialogFrameHtml = function(callUrl) {
	this.html(this.frameVar(callUrl));
	this.window('open');
};
/**
 * 根据传入的对象创建一个Window窗体
 */
$.fn.dialogWindowByObject = function(object) {
	if (object == undefined) {
		object = this.dialogParamObject(undefined, undefined);
	}
	return this.window(object);
};

/**
 * 创建一个关闭回调的窗体
 */
$.fn.dialogWindowByBeforeClose = function(onBeforeClose) {
	var object = this.dialogParamObject();
	object["onBeforeClose"] = onBeforeClose;
	return this.window(object);
};
$.fn.dialogFrameWindow = function(width, height) {
	return this.window(this.dialogParamObject(width, height));
};

$.fn.dialogShowWindow = function(width, height) {
	return this.window(this.dialogParamObject(width, height));
};

$.fn.dialogParamObject = function(width, height, onBeforeClose) {
	widthV = width == undefined ? 600 : width;
	heightV = height == undefined ? 400 : height;
	object = {
		closed : true,
		width : 600,
		height : 400,
		resizable : true,
		collapsible : false,
		minimizable : false,
		draggable : true
	};
	object["width"] = widthV;
	object["height"] = heightV;
	object["onBeforeClose"] = onBeforeClose;
	return object;
};

$.fn.dialogShowFullWindow = function() {
	return this.window({
		fit : true,
		closed : true,
		resizable : false,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		draggable : false
	});
};
