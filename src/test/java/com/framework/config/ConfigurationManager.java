/*
 *  © [2022] Qualitest. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.config;

import com.framework.components.FrameworkException;
import com.framework.components.WhitelistingPath;
import com.framework.report.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Unified configuration manager for all framework properties
 * Handles Global Settings, Mobile Settings, and API Endpoints
 * 
 * @author Qualitest
 * @version 2.0
 */
public class ConfigurationManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigurationManager.class);
    private static ConfigurationManager instance;
    
    private final Properties globalProperties;
    private final Properties mobileProperties;
    private final Properties apiProperties;
    
    private static final String GLOBAL_SETTINGS_FILE = "Global Settings.properties";
    private static final String MOBILE_SETTINGS_FILE = "Mobile Automation Settings.properties";
    private static final String API_ENDPOINTS_FILE = "API Automation Endpoints.properties";
    
    private ConfigurationManager() {
        this.globalProperties = loadPropertiesFile(GLOBAL_SETTINGS_FILE);
        this.mobileProperties = loadMobilePropertiesIfNeeded();
        this.apiProperties = loadApiPropertiesIfNeeded();
        
        logger.info("Configuration Manager initialized with conditional property files");
    }
    
    /**
     * Get singleton instance of ConfigurationManager
     * 
     * @return ConfigurationManager instance
     */
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    /**
     * Get property value from global settings
     * 
     * @param key Property key
     * @return Property value or null if not found
     */
    public String getProperty(String key) {
        return globalProperties.getProperty(key);
    }
    
    /**
     * Get property value from global settings with default value
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return globalProperties.getProperty(key, defaultValue);
    }
    
    /**
     * Get property value from mobile settings
     * 
     * @param key Property key
     * @return Property value or null if not found
     */
    public String getMobileProperty(String key) {
        if (mobileProperties == null) {
            logger.debug("Mobile properties not loaded - mobile execution not detected");
            return null;
        }
        return mobileProperties.getProperty(key);
    }
    
    /**
     * Get property value from mobile settings with default value
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public String getMobileProperty(String key, String defaultValue) {
        if (mobileProperties == null) {
            logger.debug("Mobile properties not loaded - returning default value: {}", defaultValue);
            return defaultValue;
        }
        return mobileProperties.getProperty(key, defaultValue);
    }
    
    /**
     * Get property value from API settings
     * 
     * @param key Property key
     * @return Property value or null if not found
     */
    public String getApiProperty(String key) {
        if (apiProperties == null) {
            logger.debug("API properties not loaded - API execution not detected");
            return null;
        }
        return apiProperties.getProperty(key);
    }
    
    /**
     * Get property value from API settings with default value
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public String getApiProperty(String key, String defaultValue) {
        if (apiProperties == null) {
            logger.debug("API properties not loaded - returning default value: {}", defaultValue);
            return defaultValue;
        }
        return apiProperties.getProperty(key, defaultValue);
    }
    
    /**
     * Set property value in global settings
     * 
     * @param key Property key
     * @param value Property value
     */
    public void setProperty(String key, String value) {
        globalProperties.setProperty(key, value);
        logger.debug("Set property: {} = {}", key, value);
    }
    
    /**
     * Get boolean property value from global settings
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Boolean property value
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
    
    /**
     * Get integer property value from global settings
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Integer property value
     */
    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer value for property {}: {}. Using default: {}", key, value, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Get all global properties
     * 
     * @return Properties object
     */
    public Properties getGlobalProperties() {
        return new Properties(globalProperties);
    }
    
    /**
     * Get all mobile properties
     * 
     * @return Properties object or empty properties if not loaded
     */
    public Properties getMobileProperties() {
        if (mobileProperties == null) {
            logger.debug("Mobile properties not loaded - returning empty properties");
            return new Properties();
        }
        return new Properties(mobileProperties);
    }
    
    /**
     * Get all API properties
     * 
     * @return Properties object or empty properties if not loaded
     */
    public Properties getApiProperties() {
        if (apiProperties == null) {
            logger.debug("API properties not loaded - returning empty properties");
            return new Properties();
        }
        return new Properties(apiProperties);
    }
    
    /**
     * Load properties file from the standard properties directory
     * 
     * @param fileName Properties file name
     * @return Properties object
     */
    private Properties loadPropertiesFile(String fileName) {
        Properties properties = new Properties();
        String propertiesPath = getPropertiesDirectoryPath() + Util.getFileSeparator() + fileName;
        
        try {
            String encryptedPath = WhitelistingPath.cleanStringForFilePath(propertiesPath);
            FileInputStream fileInputStream = new FileInputStream(new File(encryptedPath));
            properties.load(fileInputStream);
            fileInputStream.close();
            
            logger.info("Successfully loaded properties file: {}", fileName);
            
        } catch (IOException e) {
            logger.error("Failed to load properties file: {}", fileName, e);
            throw new FrameworkException("Failed to load properties file: " + fileName +  e);
        }
        
        return properties;
    }
    
    /**
     * Get the properties directory path
     * 
     * @return Properties directory path
     */
    private String getPropertiesDirectoryPath() {
        String userDir = System.getProperty("user.dir");
        String encryptedPath = WhitelistingPath.cleanStringForFilePath(userDir);
        
        return encryptedPath + Util.getFileSeparator() + "src" + Util.getFileSeparator() + "test" 
               + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties";
    }
    
    /**
     * Get application URL based on environment
     * 
     * @return Application URL
     */
    public String getApplicationUrl() {
        String environment = getProperty("defaultEnvironment", "qa");
        String urlKey = environment.equals("prod") ? "prodAppUrl" : "qaAppUrl";
        return getProperty(urlKey);
    }
    
    /**
     * Check if screenshots should be taken for passed steps
     * 
     * @return true if screenshots should be taken for passed steps
     */
    public boolean shouldTakeScreenshotForPassedSteps() {
        return getBooleanProperty("TakeScreenshotPassedStep", false);
    }
    
    /**
     * Check if screenshots should be taken for failed steps
     * 
     * @return true if screenshots should be taken for failed steps
     */
    public boolean shouldTakeScreenshotForFailedSteps() {
        return getBooleanProperty("TakeScreenshotFailedStep", true);
    }
    
    /**
     * Get timeout value in seconds
     * 
     * @return Timeout value
     */
    public int getTimeoutInSeconds() {
        return getIntProperty("Timeout", 30);
    }
    
    /**
     * Load mobile properties only if mobile execution is detected
     * 
     * @return Properties object or null if not needed
     */
    private Properties loadMobilePropertiesIfNeeded() {
        try {
            // Check if mobile execution is configured
            String testConfigId = globalProperties.getProperty("TestConfigurationID", "");
            
            if (isMobileExecution(testConfigId)) {
                logger.info("Mobile execution detected - loading mobile properties");
                return loadPropertiesFile(MOBILE_SETTINGS_FILE);
            } else {
                logger.info("Mobile execution not detected - skipping mobile properties");
                return null;
            }
        } catch (Exception e) {
            logger.warn("Failed to determine mobile execution need - skipping mobile properties", e);
            return null;
        }
    }
    
    /**
     * Load API properties only if API execution is detected
     * 
     * @return Properties object or null if not needed
     */
    private Properties loadApiPropertiesIfNeeded() {
        try {
            // Check if API execution is configured
            String testConfigId = globalProperties.getProperty("TestConfigurationID", "");
            
            if (isApiExecution(testConfigId)) {
                logger.info("API execution detected - loading API properties");
                return loadPropertiesFile(API_ENDPOINTS_FILE);
            } else {
                logger.info("API execution not detected - skipping API properties");
                return null;
            }
        } catch (Exception e) {
            logger.warn("Failed to determine API execution need - skipping API properties", e);
            return null;
        }
    }
    
    /**
     * Check if mobile execution is configured
     * 
     * @param testConfigId Test configuration ID
     * @return true if mobile execution is detected
     */
    private boolean isMobileExecution(String testConfigId) {
        return testConfigId != null && 
               (testConfigId.contains("Appium") || 
                testConfigId.contains("Mobile") ||
                testConfigId.contains("MOBILE") ||
                testConfigId.contains("Android") ||
                testConfigId.contains("iOS"));
    }
    
    /**
     * Check if API execution is configured
     * 
     * @param testConfigId Test configuration ID
     * @return true if API execution is detected
     */
    private boolean isApiExecution(String testConfigId) {
        return "API".equalsIgnoreCase(testConfigId);
    }
    
    /**
     * Get test configuration by name from Java configuration
     */
    public TestConfiguration getTestConfiguration(String configName) {
        return ConfigurationFactory.getConfiguration(configName);
    }
    
    /**
     * Check if mobile properties are loaded
     * 
     * @return true if mobile properties are available
     */
    public boolean isMobilePropertiesLoaded() {
        return mobileProperties != null;
    }
    
    /**
     * Check if API properties are loaded
     * 
     * @return true if API properties are available
     */
    public boolean isApiPropertiesLoaded() {
        return apiProperties != null;
    }
}