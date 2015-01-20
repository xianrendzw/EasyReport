package com.easyreport.engine.util;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.easyreport.engine.exception.TemplatePraseException;

public class VelocityUtils {
	/**
	 * 替换的模板中的参数
	 * 
	 * @param template
	 * @param parameters
	 * @return 替换后的文本
	 */
	public static String prase(String template, Map<String, Object> parameters) {
		return prase(template, parameters, "report");
	}

	/**
	 * 替换的模板中的参数
	 * 
	 * @param template
	 * @return 替换后的文本
	 */
	public static String prase(String template) {
		return prase(template, "report");
	}

	/**
	 * 替换的模板中的参数
	 * 
	 * @param template
	 * @param logTag
	 * @return 替换后的文本
	 */
	public static String prase(String template, String logTag) {
		return prase(template, new HashMap<String, Object>(0), logTag);
	}

	/**
	 * 替换的模板中的参数
	 * 
	 * @param template
	 * @param parameters
	 * @param logTag
	 * @return 替换后的文本
	 */
	public static String prase(String template, Map<String, Object> parameters, String logTag) {
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
