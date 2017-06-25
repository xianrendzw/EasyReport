var AppCtx = {
    env: '[[${env}]]', //dev|prod
    version: '[[${version}]]',
    ctxPath: '[[${ctxPath}]]',
    config: {
        pageSize: 50
    },
    global: {},
    utils: {
        debug: function (msg) {
            if (AppCtx.env == "prod") {
                return;
            }
            var time = moment().format("YYYY MM DD HH:mm:ss:SSS")
            console.trace('[%s]:%s', time, msg);
        }
    },
    validate: {},
    temp: {}
};
