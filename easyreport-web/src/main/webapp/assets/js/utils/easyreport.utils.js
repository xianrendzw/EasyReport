define(function (require, exports, module) {
    var $ = require('jquery');

    EasyReport.utils.debug = function (msg) {
        if (EasyReport.env == "prod") {
            return;
        }
        var t = new Date();
        var timeStr = t.getHours() + ':' + t.getMinutes() + ':' + t.getSeconds() + '_' + t.getMilliseconds();
        console.trace('[' + timeStr + ']: ' + msg);
    };

    return EasyReport;
});