// ABOUTME: Reads configuration values from config.properties file.
// ABOUTME: Provides a single method to get any property by its key.
package com.saucedemo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties;

    static {
        properties = new Properties();
        String filePath = "src/test/resources/config.properties";
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties file: " + filePath, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
