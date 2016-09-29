var conf = {
    baseUrl: EasyReport.ctxPath + '/assets/',
    paths: {
        'jquery': 'vendor/jquery.min',
        'jquery-form': 'vendor/jquery-form/jquery.form.min',
        'jquery-form-autofill': 'vendor/jquery-form/jquery.formautofill.min',
        'jquery-validate': 'vendor/jquery-validate/jquery.validate.min',
        'jquery-validate-msg-zhcn': 'vendor/jquery-validate/jquery.validate.message.zh-cn',
        'jquery-metadata': 'vendor/jquery-validate/jquery.metadata.2.1',
        'jquery-extension': 'vendor/jquery.extension',
        'jquery-filedownload': 'vendor/jquery.fileDownload',
        'jquery-fixtableheader': 'vendor/jquery.fixtableheader',
        'jquery-number': 'vendor/jquery.number.min',
        'jquery-tablesorter': 'vendor/tablesorter/jquery.tablesorter.min',
        'select2': 'vendor/select2/select2.min',
        'select2-zhcn': 'vendor/select2/js/i18n/zh-CN',
        'echarts': 'vendor/echarts.min',
        'moment': 'vendor/moment.min',
        'juicer': 'vendor/juicer/juicer-min',
        'datatables': 'vendor/datatables/media/js/jquery.dataTables.min',
        'require-css': 'vendor/require-css/css.min',
        'easyui-utils': 'js/utils/easyui.utils.amd'
    },
    packages: [{
        name: "codemirror",
        location: "vendor/codemirror",
        main: "vendor/codemirror"
    }],
    config: {
        moment: {
            noGlobal: true
        }
    },
    map: {
        '*': {
            'css': 'require-css'
        }
    },
    shim: {
        'jquery-form': {
            deps: ['jquery'],
            exports: "$"
        },
        'jquery-form-autofill': {
            deps: ['jquery', 'jquery-form'],
            exports: "$"
        },
        'jquery-metadata': {
            deps: ['jquery'],
            exports: "$"
        },
        'jquery-validate': {
            deps: ['jquery', 'jquery-metadata'],
            exports: "$"
        },
        'jquery-validate-msg-zhcn': {
            deps: ['jquery', 'jquery-validate'],
            exports: "$"
        },
        'jquery-extension': {
            deps: ['jquery'],
            exports: "$"
        },
        'jquery-filedownload': {
            deps: ['jquery'],
            exports: "$"
        },
        'jquery-fixtableheader': {
            deps: ['jquery'],
            exports: "$"
        },
        'jquery-number': {
            deps: ['jquery'],
            exports: "$"
        },
        'jquery-tablesorter': {
            deps: ["jquery", 'css!vendor/tablesorter/css/theme.default'],
            exports: "$"
        },
        'datatables': {
            deps: ["jquery", 'css!vendor/datatables/media/css/jquery.dataTables.min'],
            exports: "$"
        },
        'juicer': {
            exports: 'juicer'
        },
        'select2': {
            deps: ['jquery'],
            exports: "$"
        },
        'select2-zhcn': {
            deps: ['jquery', 'select2'],
            exports: "$"
        }
    },
    enforceDefine: true,
    urlArgs: 'v=' + EasyReport.version
};

require.config(conf);