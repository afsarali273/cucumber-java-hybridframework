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
package com.framework.utils;

import com.framework.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * Utility class to safely access properties and handle null pointer exceptions
 * Provides safe methods to access mobile and API properties
 * 
 * @author Qualitest
 * @version 2.0
 */
public class PropertyValidator {
    
    private static final Logger logger = LogManager.getLogger(PropertyValidator.class);
    private static PropertyValidator instance;
    
    private final ConfigurationManager configManager;
    
    private PropertyValidator() {
        this.configManager = ConfigurationManager.getInstance();
    }
    
    /**
     * Get singleton instance of PropertyValidator
     * 
     * @return PropertyValidator instance
     */
    public static synchronized PropertyValidator getInstance() {
        if (instance == null) {
            instance = new PropertyValidator();
        }
        return instance;
    }
    
    /**
     * Safely get mobile property with null check
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found or mobile properties not loaded
     * @return Property value or default value
     */
    public String getMobilePropertySafe(String key, String defaultValue) {
        try {
            if (!configManager.isMobilePropertiesLoaded()) {
                logger.debug("Mobile properties not loaded, returning default for key: {}", key);
                return defaultValue;
            }
            
            String value = configManager.getMobileProperty(key);
            return value != null ? value : defaultValue;
            
        } catch (Exception e) {
            logger.warn("Error accessing mobile property '{}', returning default: {}", key, defaultValue, e);
            return defaultValue;
        }
    }
    
    /**
     * Safely get API property with null check
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found or API properties not loaded
     * @return Property value or default value
     */
    public String getApiPropertySafe(String key, String defaultValue) {
        try {
            if (!configManager.isApiPropertiesLoaded()) {
                logger.debug("API properties not loaded, returning default for key: {}", key);
                return defaultValue;
            }
            
            String value = configManager.getApiProperty(key);
            return value != null ? value : defaultValue;
            
        } catch (Exception e) {
            logger.warn("Error accessing API property '{}', returning default: {}", key, defaultValue, e);
            return defaultValue;
        }
    }
    
    /**
     * Safely get global property with null check
     * 
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public String getGlobalPropertySafe(String key, String defaultValue) {
        try {
            String value = configManager.getProperty(key);
            return value != null ? value : defaultValue;
            
        } catch (Exception e) {
            logger.warn("Error accessing global property '{}', returning default: {}", key, defaultValue, e);
            return defaultValue;
        }
    }
    
    /**
     * Check if mobile properties are available and loaded
     * 
     * @return true if mobile properties are loaded
     */
    public boolean isMobilePropertiesAvailable() {
        return configManager.isMobilePropertiesLoaded();
    }
    
    /**
     * Check if API properties are available and loaded
     * 
     * @return true if API properties are loaded
     */
    public boolean isApiPropertiesAvailable() {
        return configManager.isApiPropertiesLoaded();
    }
    
    /**
     * Safely get boolean mobile property
     * 
     * @param key Property key
     * @param defaultValue Default boolean value
     * @return Boolean property value or default
     */
    public boolean getMobileBooleanPropertySafe(String key, boolean defaultValue) {
        String value = getMobilePropertySafe(key, String.valueOf(defaultValue));
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            logger.warn("Error parsing boolean mobile property '{}', returning default: {}", key, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Safely get boolean API property
     * 
     * @param key Property key
     * @param defaultValue Default boolean value
     * @return Boolean property value or default
     */
    public boolean getApiBooleanPropertySafe(String key, boolean defaultValue) {
        String value = getApiPropertySafe(key, String.valueOf(defaultValue));
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            logger.warn("Error parsing boolean API property '{}', returning default: {}", key, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Safely get integer mobile property
     * 
     * @param key Property key
     * @param defaultValue Default integer value
     * @return Integer property value or default
     */
    public int getMobileIntPropertySafe(String key, int defaultValue) {
        String value = getMobilePropertySafe(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            logger.warn("Error parsing integer mobile property '{}', returning default: {}", key, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Safely get integer API property
     * 
     * @param key Property key
     * @param defaultValue Default integer value
     * @return Integer property value or default
     */
    public int getApiIntPropertySafe(String key, int defaultValue) {
        String value = getApiPropertySafe(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            logger.warn("Error parsing integer API property '{}', returning default: {}", key, defaultValue);
            return defaultValue;
        }
    }
}