package com.easytoolsoft.easyreport.engine.util;

import com.easytoolsoft.easyreport.engine.exception.TemplatePraseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class VelocityUtils {
    /**
     * 替换的模板中的参数
     *
     * @param template
     * @param parameters
     * @return 替换后的文本
     */
    public static String parse(String template, Map<String, Object> parameters) {
        return parse(template, parameters, "report");
    }

    /**
     * 替换的模板中的参数
     *
     * @param template
     * @return 替换后的文本
     */
    public static String parse(String template) {
        return parse(template, "report");
    }

    /**
     * 替换的模板中的参数
     *
     * @param template
     * @param logTag
     * @return 替换后的文本
     */
    public static String parse(String template, String logTag) {
        return parse(template, new HashMap<>(0), logTag);
    }

    /**
     * 替换的模板中的参数
     *
     * @param template
     * @param parameters
     * @param logTag
     * @return 替换后的文本
     */
    public static String parse(String template, Map<String, Object> parameters, String logTag) {
        try (StringWriter writer = new StringWriter()) {
            Velocity.init();
            VelocityContext context = new VelocityContext();
            for (Entry<String, Object> kvset : parameters.entrySet()) {
                context.put(kvset.getKey(), kvset.getValue());
            }
            context.put("Calendar", Calendar.getInstance());
            context.put("DateUtils", DateUtils.class);
            context.put("StringUtils", StringUtils.class);
            Velocity.evaluate(context, writer, logTag, template);
            return writer.toString();
        } catch (Exception ex) {
            throw new TemplatePraseException(ex);
        }
    }
}
