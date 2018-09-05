package com.easytoolsoft.easyreport.engine.util;

import java.util.Map;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

public class ThymeleafUtils {
    private static TemplateEngine TEMPLATE_ENGINE = null;

    /**
     * 替换的模板中的参数
     *
     * @param template
     * @param parameters
     * @return 替换后的文本
     */
    public static String parse(final String template, final Map<String, Object> parameters) {
        final org.thymeleaf.context.Context ctx = new org.thymeleaf.context.Context();
        ctx.setVariables(parameters);
        return getThymeLeafTemplate().process(template, ctx);
    }

    public static TemplateEngine getThymeLeafTemplate() {
        if (TEMPLATE_ENGINE == null) {
            TEMPLATE_ENGINE = new TemplateEngine();
            // Resolver for String
            StringTemplateResolver templateResolver = new StringTemplateResolver();
            templateResolver.setTemplateMode(TemplateMode.TEXT);
            TEMPLATE_ENGINE.addTemplateResolver(templateResolver);
            return TEMPLATE_ENGINE;
        } else {
            return TEMPLATE_ENGINE;
        }
    }
}