package org.dobex.sound_crafters.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvVariables {

    private static final Properties APP_PROPERTIES = new Properties();

    static {
        try {
            InputStream resourceAsStream = EnvVariables.class.getClassLoader().getResourceAsStream("app.properties");
            APP_PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("Application properties file not found!");
        }
    }

    public static String getProperty(String key) {
        return APP_PROPERTIES.getProperty(key);
    }

    public static Properties getAppProperties() {
        return APP_PROPERTIES;
    }
}
