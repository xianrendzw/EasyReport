var conf = {
    baseUrl: EasyReport.ctx.path + '/assets/',
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
        'juicer': 'vendor/juicer/juicer-min',
        'require-css': 'vendor/require-css/css.min',
        'select2': 'vendor/select2/select2.full.min',
        'select2-zhcn': 'vendor/select2/js/i18n/zh-CN',
        'echarts': 'vendor/echarts.min',
        'moment': 'vendor/moment.min',
        'datatables': 'vendor/datatables/media/js/jquery.dataTables.min',
        'datatables-utils': 'custom/datatables/media/js/datatable.utils',
        'easyreport-utils': 'js/utils/easyreport.utils'
    },
    packages: [{
        name: "codemirror",
        location: "vendor/codemirror",
        main: "vendor/codemirror"
    }],
    map: {
        '*': {
            'css': 'require-css'
        }
    },
    shim: {
        'bootstrap-datepicker': {
            deps: ['jquery', 'bootstrap', 'bootstrap-datepicker-basic'],
            exports: "$"
        },
        'bootstrap-datepicker-basic': {
            deps: ['jquery', 'bootstrap',
                'css!vendor/bootstrap-datepicker/css/bootstrap-datepicker' + (Biz.Config.prod ? '.min' : '')],
            exports: "$"
        },
        'datatables': {
            deps: ["jquery", 'datatables-utils', 'css!custom/datatables/media/css/jquery.dataTables'],
            exports: "$.fn.popover"
        },
        'jBox': {
            deps: ['jquery', 'css!custom/jBox/jBox'],
            exports: 'jBox'
        },
        'jqueryForm': {
            deps: ['jquery'],
            exports: "$"
        },
        'jqueryFormAutofill': {
            deps: ['jquery', 'jqueryForm'],
            exports: "$"
        },
        'jqueryValidate': {
            deps: ['jquery', 'jqueryValidate-basic'],
            exports: "$"
        },
        'juicer': {
            exports: 'juicer'
        },
        'select2': {
            deps: ['jquery', 'select2-basic'],
            exports: "$"
        }
    }
};

require.config(conf);