/**
 * 所有以“_”开始的方法和变量都属于内部变量，不对外使用。
 */
function XFrame() {
}

/**
 * 设置Context路径，即WebApp的路径。
 */
XFrame.setContextPath = function(path) {
	XFrame._contextPath = path;
}

XFrame.getContextPath = function() {
	return XFrame._contextPath;
}

/**
 * //对Date的扩展，将 Date 转化为指定格式的String //月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2
 * 个占位符， //年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) //例子： //(new
 * Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 //(new
 * Date()).format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

Date.prototype.add = function(days) {
	return new Date(this.valueOf() + days * 24 * 60 * 60 * 1000);
};

/**
 * FileName: qframe_util.js Description: JavaScript工具
 */

function XFrameUtils() {
}

/**
 * 是否包含特殊字符: ' ; < > Return: true 包含、false 不包含
 */
XFrameUtils.hasSpecialChar = function(str) {
	var specialChar = /([\'\;<>])+/;
	return specialChar.test(str);
}
/**
 * * 比较输入的两日期 * inputDate 比较时间 * compareDate 被比较时间 * 比较时间 小于被比较时间 false * 比较时间
 * 大于被比较时间 true * 如 输入 : 2009-09-09,2009-09-10 return true * 时间格式 yyyy-MM-dd
 */
XFrameUtils.compareDate = function(inputDate, compareDate) {
	if (inputDate == undefined || inputDate == "") {
		return false;
	}
	if (compareDate == undefined || compareDate == "") {
		return true;
	}
	inputDate = inputDate.replace(/-0/g, '-');
	compareDate = compareDate.replace(/-0/g, '-');
	var inputDateArray = inputDate.split("-");
	var compareDateArray = compareDate.split("-");
	if (inputDateArray[0] == undefined || compareDateArray[0] == undefined
			|| inputDateArray[1] == undefined
			|| compareDateArray[1] == undefined
			|| inputDateArray[2] == undefined
			|| compareDateArray[2] == undefined) {
		return false;
	}
	if (parseInt(inputDateArray[0]) < parseInt(compareDateArray[0])) {
		return true;
	}
	if (parseInt(inputDateArray[0]) == parseInt(compareDateArray[0])) {
		if (parseInt(inputDateArray[1]) < parseInt(compareDateArray[1])) {
			return true;
		}
		if (parseInt(inputDateArray[1]) == parseInt(compareDateArray[1])
				&& parseInt(inputDateArray[2]) <= parseInt(compareDateArray[2])) {
			return true;
		}
	}
	return false;
}
/**
 * * 输入一个日期与当前日期进行比较 * 大于当前时间放回true * 小于当前时间false * 非法输入返回 false * * 输入格式
 * yyyy-MM-dd
 */
XFrameUtils.isBeforeDate = function(inputDate) {
	var nowDate = new Date().format("yyyy-MM-dd");
	return XFrameUtils.compareDate(inputDate, nowDate);
}

/**
 * 字符串是否为空,长度为0的字符串、全是空格组成的字符串都认为是空。 Return: true 为空、false 不为空
 */
XFrameUtils.isNull = function(str) {
	if (str == null)
		return true;
	else
		return str.trim().isEmpty();
}

/**
 * 字符串是否不为空,长度为0的字符串、全是空格组成的字符串都认为是空。 Return: true 不为空、false 为空
 */
XFrameUtils.isNotNull = function(str) {
	return !XFrameUtils.isNull(str);
}

/**
 * 验证负整数
 * 
 * @param {String}
 *            param
 * @author 张乐雷
 * @since 2008-5-5 下午03:34:47
 */
XFrameUtils.validateNegativeInteger = function(str) {
	if (str.isEmpty()) {
		return false;
	}
	var integer = /^[-]?[0-9]+$/;
	return integer.test(str);
}

/**
 * 验证正整数
 * 
 * @param {String}
 *            str
 * @return true 是正整数 false 不是正整数
 * @author 张乐雷
 * @since 2008-5-5 下午03:34:47
 */
XFrameUtils.validatePositiveInteger = function(str) {
	if (str.isEmpty()) {
		return false;
	}
	var integer = /^\+?[1-9][0-9]*$/;
	return integer.test(str);
}

/**
 * 验证整数，可包含正负号，全部由数字组成 Return: true 是整数、false 不是整数
 */
XFrameUtils.validateInteger = function(str) {
	if (str.isEmpty())
		return false;

	var integer = /^[+-]?[0-9]+$/;
	return integer.test(str);
}
// 校验是否是整数 fengzw
XFrameUtils.isInteger = function(str) {
	var patrn = /^([+-]?)(\d+)$/;
	return patrn.test(str);
};

/**
 * 验证一个字符串由数字0－9的组成 Return: true 是数字、false 不是数字
 */
XFrameUtils.validateNumber = function(str) {
	if (str.isEmpty())
		return false;

	var number = /^[0-9]+$/;
	return number.test(str);
}

/**
 * 验证字母、数字和下划线，例如ab_12
 * 
 * @param {type}
 *            param
 * @return true 格式正确、false 格式错误
 * @author 张乐雷
 * @since 2008-5-9 下午02:17:17
 */
XFrameUtils.validateNumberAndLetter = function(param) {
	var regExp = /^[A-Za-z0-9_-]+$/;
	return regExp.test(param);
}
/**
 * 校验合法时间 fengzw
 */
XFrameUtils.isDate = function(str) {
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var r = str.match(reg);
	if (r == null)
		return false;
	var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
	return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3]
			&& d.getDate() == r[4] && d.getHours() == r[5]
			&& d.getMinutes() == r[6] && d.getSeconds() == r[7]);
}
/**
 * 验证手机号码
 */
XFrameUtils.validatePhoneNumber = function(param) {
	var regExp = /^1[3,5]\d{9}$/;// 手机号码
	return regExp.test(param);
}
/**
 * 验证密码是否正确，并且不能为空
 */
XFrameUtils.validatePassword = function(param1, param2) {
	if (param1.trim() == '') {
		return false;
	}
	if (param2.trim() == '') {
		return false;
	}
	if (param1.trim() != param2.trim()) {
		return false;
	}
	return true;
}

/**
 * 验证邮政编码
 * 
 * @param {String}
 *            postCode
 * @return true 格式正确、false 格式错误
 * @author 张乐雷
 * @since 2008-4-29 下午04:43:52
 */
XFrameUtils.validatePostCode = function(postCode) {
	var regTextPost = /^(\d){6}$/;
	return regTextPost.test(postCode);
}
// 校验邮编
XFrameUtils.isZipCode = function(str) {
	var patrn = /^\d{6}$/;
	return patrn.test(str);
};
/**
 * 验证一个字符串不是0－9的数字 Return: true 不是数字、false 是数字
 */
XFrameUtils.validateNotNumber = function(str) {
	return !XFrameUtils.validateNumber(str);
}

/**
 * 验证英文，可包含字母、数字、下划线、连接线 Return: true 是英文、false不是英文
 */
XFrameUtils.validateEnglish = function(str) {
	if (str.isEmpty())
		return false;

	var english = /^[a-zA-Z0-9_-]+$/;
	return english.test(str);
}

/**
 * 验证URL地址，(fengzw)
 * 
 */
XFrameUtils.isUrl = function(str) {
	var patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
	return patrn.test(str);
};

/**
 * 验证URL地址，可包含字母、数字、下划线、连接线、点、斜线、冒号 Return: true 是英文、false不是英文
 */
XFrameUtils.validateUrlpath = function(str) {
	if (str.isEmpty())
		return false;

	var url = /^[a-zA-Z0-9_.&=\/-]+$/;
	return url.test(str);
}

/**
 * 验证中文，字符串中全部是中文 Return: true 是中文、false 不是中文
 */
XFrameUtils.validateChiness = function(str) {
	var chiness = /^[a-zA-Z\u0391-\uFFE5]+$/;
	return chiness.test(str);
}

/**
 * 验证邮件地址格式 Return: true 格式正确、false 格式错误
 */
XFrameUtils.validateEmail = function(str) {
	var email = /^([A-Za-z0-9])(\w)+@(\w)+(\.)(com|com\.cn|net|cn|net\.cn|org|biz|info|gov|gov\.cn|edu|edu\.cn)/;
	return email.test(str);
}

/**
 * 验证IP地址 Return: true 格式正确、false 格式错误
 */
XFrameUtils.validateIp = function(str) {
	var ip = /^(([3-9]\d?)|((1\d?|2[0-1]?)\d?)|22([0-3]?))\.((([3-9]?\d)|((1\d?|2[0-4]?)\d?)|25([0-5]?))\.){2}(([3-9]\d?)|((1\d?|2[0-4]?)\d?)|25([0-4]?))$/;
	return ip.test(str);
}

/**
 * 验证电话号码格式，可包含区号、分机号 Return: true 格式正确、false 格式错误
 */
XFrameUtils.validatePhone = function(str) {
	var phone = /^(?:[1-9]|(0[1-9](0|[1-9])\d{0,1})|\((0[1-9](0|[1-9])\d{0,1})\))(?:\d{6,8}|\d{6,8}-\d{2,6})$/;
	;
	return phone.test(str);
}

/**
 * 验证电话号码格式，可包含区号、分机号 chenff
 */
XFrameUtils.isPhone = function(str) {
	var partten1 = /^0(([1,2]\d)|([3-9]\d{2}))\d{7,8}$/;// 固定电话
	if (!partten1.test(str)) {
		return false;
	}
	return true;
}
/**
 * 验证带有区号的固定电话号码
 * 
 * @param {var}
 *            phoneWithAreaCode
 * @return true 格式正确、false 格式错误
 * @author 张乐雷
 * @since 2008-4-29 上午10:16:46
 */
XFrameUtils.validatePhoneWithAreaCode = function(phoneWithAreaCode) {
	var mask = /^0\d{11,14}$/;
	return mask.test(phoneWithAreaCode);
}

/**
 * 校验是否为浮点数
 * 
 * @author 冯志文 fengzw
 */
XFrameUtils.isFloat = function(str) {
	var patrn = /^([+-]?)\d*\.\d+$/;
	return patrn.test(str);
};

/**
 * 验证正的小数 Return: true 格式正确、false 格式错误
 * 
 * @author 李巍璐
 */
XFrameUtils.validateDecimal = function(str) {
	var decimal = /^[0-9]+\.?[0-9]*$/;
	return decimal.test(str);
}
/**
 * 验证正的分数 Return: true 格式正确、false 格式错误
 * 
 * @author 李巍璐
 */
XFrameUtils.validateFraction = function(str) {
	var decimal = /^[1-9]+[0-9]*\/{1}[1-9]+[0-9]*$/;
	return decimal.test(str);
}
/**
 * 根据表达式验证字符串格式 Return: true 格式正确、false 格式错误
 */
XFrameUtils.validateMask = function(str, mask) {
	return mask.test(str);
}

/**
 * 搜索条点击响应函数，可以折叠或打开搜索条。 Param objSearch 搜索条的DOM对象。
 */
XFrameUtils.onclickSearchBar = function(objSearch) {
	var id = objSearch.id;
	var imgId = id + "_img";
	var divId = id + "_div";
	if ($("#" + divId).css("display") == 'none') {
		$("#" + divId).show();

		$("#" + imgId).attr("src",
				XFrame.getContextPath() + "/images/closeField.gif");
	} else {
		$("#" + divId).hide();
		$("#" + imgId).attr("src",
				XFrame.getContextPath() + "/images/openField.gif");
	}
}

/**
 * 填充form数据
 */
XFrameUtils.fillForm = function(formId, data) {
	var objForm = document.forms[formId];
	if (objForm == null || objForm == undefined)
		return false;
	var elements = objForm.elements;
	for (var i = 0; i < elements.length; i++) {
		var name = elements[i].name;
		var type = elements[i].type;
		var tag = elements[i].tagName.toLowerCase();

		if (data[name] == null || data[name] == 'null') {
			data[name] = '';
		}

		if (type == "text" || type == "password" || tag == 'textarea'
				|| type == "hidden") {
			elements[i].value = data[name];
		}
		// 当对象为下拉列表时，清除时置为第一个值
		else if (tag == "select") {
			for (var m = 0; m < elements[i].options.length; m++) {
				if (elements[i].options[m].value == data[name]) {
					elements[i].options[m].selected = true;
				}
			}
		} else if (type == "radio" && elements[i].value == data[name]) {
			elements[i].checked = true;
		} else if (type == "checkbox") {
			var a = data[name].split(',');
			for (var j = 0; j < a.length; j++) {
				if (a[j] == elements[i].value) {
					elements[i].checked = true;
				}
			}
		}
	}
	return false;
};

/**
 * 清除Form中文本框的内容，如果是只读属性则不清除。 Param: objForm Fomr对象
 */
XFrameUtils.clearForm = function(formId) {
	var objForm = document.forms[formId];
	if (objForm == null || objForm == undefined)
		return false;
	var elements = objForm.elements;
	for (var i = 0; i < elements.length; i++) {
		var type = elements[i].type;
		var tag = elements[i].tagName.toLowerCase();
		if ((type == "text" || type == "password" || tag == 'textarea' || type == "hidden")
				&& !elements[i].readOnly) {
			elements[i].value = "";
		}
		// 当对象为下拉列表时，清除时置为第一个值
		else if (tag == "select" && !elements[i].disabled) {
			elements[i].options[0].selected = true;
		} else if ((type == "checkbox" || type == "radio")
				&& !elements[i].disabled) {
			elements[i].checked = false;
		}
	}

	return false;
};

/**
 * 把秒钟转换成X天X小时X分X秒
 */
XFrameUtils.getElapsedTime = function(seconds) {
	var second = Math.floor(seconds % 60); // 计算秒
	var minite = Math.floor((seconds / 60) % 60); // 计算分
	var hour = Math.floor((seconds / 3600) % 24); // 计算小时
	var day = Math.floor((seconds / 3600) / 24); // 计算天
	var elapsedTime = "";

	if (second > 0)
		elapsedTime = second + "秒";
	if (minite > 0)
		elapsedTime = minite + "分" + elapsedTime;
	if (hour > 0)
		elapsedTime = hour + "小时" + elapsedTime;
	if (day > 0)
		elapsedTime = day + "天" + elapsedTime;

	return elapsedTime;
};

/**
 * 把yyyyMMdd格式字符串转成yyyy-MM-dd字符串
 */
XFrameUtils.toDateString = function(format) {
	if (format.length != 8)
		return format;
	return format.substr(0, 4) + "-" + format.substr(4, 2) + "-"
			+ format.substr(6, 2);
};