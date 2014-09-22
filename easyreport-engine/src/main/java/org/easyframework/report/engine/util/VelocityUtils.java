package org.easyframework.report.engine.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityUtils {

	/**
	 * 替换的模板中的参数
	 * 
	 * @param template
	 * @param parameters
	 * @return 替换后的文本
	 */
	public static String prase(String template, Map<String, Object> parameters) {
		StringWriter writer = null;
		try {
			Velocity.init();
			VelocityContext context = new VelocityContext();
			for (Entry<String, Object> kvset : parameters.entrySet()) {
				context.put(kvset.getKey(), kvset.getValue());
			}
			context.put("Calendar", Calendar.getInstance());
			context.put("DateUtils", DateUtils.class);
			writer = new StringWriter();
			Velocity.evaluate(context, writer, "report_sql_text", template);
			return writer.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}
}
