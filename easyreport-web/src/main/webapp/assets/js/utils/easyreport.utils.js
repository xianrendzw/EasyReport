define(function (require, exports, module) {
    var $ = require('jquery');
    var moment = require('moment');

    EasyReport.utils.debug = function (msg) {
        if (EasyReport.env == "prod") {
            return;
        }
        var time = moment().format("YYYY MM DD HH:mm:ss:SSS")
        console.trace('[%s]:%s', time, msg);
    };

    return EasyReport;
});