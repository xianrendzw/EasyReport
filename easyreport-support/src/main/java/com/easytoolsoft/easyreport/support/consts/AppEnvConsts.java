package com.easytoolsoft.easyreport.support.consts;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用环境常量
 *
 * @author Tom Deng
 * @date 2017-03-28
 **/
public class AppEnvConsts {
    /**
     * http context 路径境配置项
     */
    public final static String CONTEXT_PATH = "ctxPath";

    /**
     * 应用名配置项
     */
    public final static String APP_NAME_ITEM = "appName";

    /**
     * 系统运行环境配置项
     */
    public final static String ENV_NAME_ITEM = "env";

    /**
     * 系统统一版本名称境配置项
     */
    public final static String VERSION_ITEM = "version";

    /**
     * 应用名配置项默认值
     */
    public static String APP_NAME = "unknow";

    /**
     * 系统统一版本名称境配置项默认值
     */
    public static String VERSION = "1.0";

    /**
     * 系统运行环境配置项默认值
     */
    public static String ENV_NAME = "prod";

    /**
     * 设置应用名
     *
     * @param appName
     */
    public static void setAppName(final String appName) {
        AppEnvConsts.APP_NAME = appName;
    }

    /**
     * 设置应用发布版本
     *
     * @param version
     */
    public static void setVersion(final String version) {
        AppEnvConsts.VERSION = version;
    }

    /**
     * 设置应用部署环境名，取值范围为[dev,test,pre,test]
     *
     * @param envName
     */
    public static void setEnvName(final String envName) {
        AppEnvConsts.ENV_NAME = envName;
    }

    /**
     * 应用部署环境名是否为生产环境
     *
     * @return true|false
     */
    public static boolean isProductionMode() {
        return StringUtils.equalsAnyIgnoreCase(ENV_NAME, "prod");
    }
}
