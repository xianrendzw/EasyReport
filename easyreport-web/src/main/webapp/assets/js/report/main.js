$(function () {
    ReportPreviewMain.init();
});

var ReportPreviewMain = {
    init: function () {
        PreviewMainMVC.View.initControl();
        PreviewMainMVC.View.bindEvent();
        PreviewMainMVC.View.bindValidate();
        PreviewMainMVC.View.initData();
    }
};

var PreviewCommon = {
    baseUrl: EasyReport.ctxPath + '/report'
};

var PreviewMainMVC = {
    URLs: {
        table: {
            url: PreviewCommon.baseUrl + '/table/uid/${uid}?theme=content',
            method: 'GET'
        },
        chart: {
            url: PreviewCommon.baseUrl + '/chart/uid/${uid}?theme=content',
            method: 'GET'
        }
    },
    View: {
        initControl: function () {
        },
        bindEvent: function () {
        },
        bindValidate: function () {
        },
        initData: function () {
            var uid = $('#report-main-uid').val();
            var tableUrl = juicer(PreviewMainMVC.URLs.table.url, {uid: uid});
            var chartUrl = juicer(PreviewMainMVC.URLs.chart.url, {uid: uid});
            PreviewMainMVC.Controller.updateTab('表格', tableUrl, TableReport.init);
            PreviewMainMVC.Controller.updateTab('图表', chartUrl, ChartReport.init);
        }
    },
    Controller: {
        updateTab: function (which, href, onLoad) {
            var tab = $('#report-main-tabs').tabs('getTab', which);
            $('#report-main-tabs').tabs('update', {
                tab: tab,
                options: {
                    onLoad: function () {
                        onLoad();
                    }
                }
            });
            tab.panel('refresh', href);
        }
    }
};