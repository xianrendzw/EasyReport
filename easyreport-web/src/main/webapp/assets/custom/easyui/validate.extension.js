$.extend($.fn.validatebox.defaults.rules, {
	equals : {
		validator : function(value, param) {
			return value == $(param).val();
		},
		message : '两次输入的密码不一致.'
	},
	code : {
		validator : function(value, param) {
			var regx = /^[a-zA-Z]+$/;
			return regx.test(value);
		},
		message : '编号只能英文字符.'
	},
	digit : {
		validator : function(value, param) {
			var regx = /^[\d]+$/;
			return regx.test(value);
		},
		message : '编号只能为数字.'
	}
});