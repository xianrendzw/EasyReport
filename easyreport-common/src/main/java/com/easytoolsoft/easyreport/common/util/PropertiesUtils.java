package com.easytoolsoft.easyreport.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    private static Properties props = new Properties();

    public PropertiesUtils() {
    }

    public static void configure(String configFilename) {
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(configFilename);
            props.load(fs);
        } catch (IOException e) {
            throw new RuntimeException("Property file load error.", e);
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                throw new RuntimeException("Property file stream close failure.", e);
            }
        }
    }

    public static String getValue(String key) {
        return props.getProperty(key);
    }

    public static void setValue(String key, String value) {
        props.setProperty(key, value);
    }
}
