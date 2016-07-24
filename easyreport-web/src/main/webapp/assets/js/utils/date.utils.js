/**
 * 对Date的扩展，将 Date 转化为指定格式的String 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2个占位符，
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * 
 * <pre>
 * (new Date()).format(&quot;yyyy-MM-dd hh:mm:ss.S&quot;) ==&gt; 2006-07-02 08:09:04.423 
 * (new Date()).format(&quot;yyyy-M-d h:m:s.S&quot;) ==&gt; 2006-7-2 8:9:4.18
 * </pre>
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
                // 毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    return fmt;
};

Date.prototype.add = function (days) {
    return new Date(this.valueOf() + days * 24 * 60 * 60 * 1000);
};

var DateUtils = {
    /**
     * 把秒钟转换成X天X小时X分X秒
     */
    getElapsedTime: function (seconds) {
        var second = Math.floor(seconds % 60); // 计算秒
        var minite = Math.floor((seconds / 60) % 60); // 计算分
        var hour = Math.floor((seconds / 3600) % 24); // 计算小时
        var day = Math.floor((seconds / 3600) / 24); // 计算天
        var elapsedTime = "";
        if (second > 0) {
            elapsedTime = second + "秒";
        }
        if (minite > 0) {
            elapsedTime = minite + "分" + elapsedTime;
        }
        if (hour > 0) {
            elapsedTime = hour + "小时" + elapsedTime;
        }
        if (day > 0) {
            elapsedTime = day + "天" + elapsedTime;
        }
        return elapsedTime;
    },
    /**
     * 把yyyyMMdd格式字符串转成yyyy-MM-dd字符串
     */
    toDateString: function (format) {
        if (format.length != 8) {
            return format;
        }
        return format.substr(0, 4) + "-" + format.substr(4, 2) + "-" + format.substr(6, 2);
    }
}
