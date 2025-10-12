package com.framework.config;

import com.framework.selenium.Browser;
import com.framework.selenium.ExecutionMode;
import com.framework.selenium.MobileExecutionPlatform;
import com.framework.components.ToolName;

/**
 * Helper class to bridge between old JSON configuration and new Java configuration
 * Provides backward compatibility while migrating to Java-based configuration
 */
public class ConfigurationHelper {
    
    private static final ConfigurationManager configManager = ConfigurationManager.getInstance();
    
    /**
     * Get execution mode for test configuration
     * First tries Java configuration, falls back to properties if needed
     */
    public static ExecutionMode getExecutionMode(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null) {
            return config.getExecutionMode();
        }
        
        // Fallback to properties-based detection
        String executionMode = configManager.getProperty("ExecutionMode");
        if (executionMode != null) {
            try {
                return ExecutionMode.valueOf(executionMode);
            } catch (IllegalArgumentException e) {
                // Invalid enum value, return default
            }
        }
        
        return ExecutionMode.LOCAL; // default
    }
    
    /**
     * Get tool name for test configuration
     */
    public static ToolName getToolName(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null) {
            return config.getToolName();
        }
        
        // Fallback to properties-based detection
        String toolName = configManager.getProperty("ToolName");
        if (toolName != null) {
            try {
                return ToolName.valueOf(toolName);
            } catch (IllegalArgumentException e) {
                // Invalid enum value, return default
            }
        }
        
        return ToolName.SELENIUM; // default
    }
    
    /**
     * Get browser for test configuration
     */
    public static Browser getBrowser(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null && config.getBrowser() != null) {
            return config.getBrowser();
        }
        
        // Fallback to properties-based detection
        String browser = configManager.getProperty("Browser");
        if (browser != null) {
            try {
                return Browser.valueOf(browser);
            } catch (IllegalArgumentException e) {
                // Invalid enum value, return default
            }
        }
        
        return Browser.CHROME; // default
    }
    
    /**
     * Get browser version for test configuration
     */
    public static String getBrowserVersion(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null && config.getBrowserVersion() != null) {
            return config.getBrowserVersion();
        }
        
        // Fallback to properties
        return configManager.getProperty("BrowserVersion", "latest");
    }
    
    /**
     * Get platform for test configuration
     */
    public static String getPlatform(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null && config.getPlatform() != null) {
            return config.getPlatform();
        }
        
        // Fallback to properties
        return configManager.getProperty("Platform", "WIN10");
    }
    
    /**
     * Get device name for mobile test configuration
     */
    public static String getDeviceName(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null && config.getDeviceName() != null) {
            return config.getDeviceName();
        }
        
        // Fallback to mobile properties
        return configManager.getMobileProperty("DeviceName");
    }
    
    /**
     * Get mobile execution platform
     */
    public static MobileExecutionPlatform getMobileExecutionPlatform(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null && config.getMobileExecutionPlatform() != null) {
            return config.getMobileExecutionPlatform();
        }
        
        // Fallback to mobile properties
        String platform = configManager.getMobileProperty("MobileExecutionPlatform");
        if (platform != null) {
            try {
                return MobileExecutionPlatform.valueOf(platform);
            } catch (IllegalArgumentException e) {
                // Invalid enum value
            }
        }
        
        return null;
    }
    
    /**
     * Get mobile OS version
     */
    public static String getMobileOSVersion(String testConfiguration) {
        TestConfiguration config = configManager.getTestConfiguration(testConfiguration);
        if (config != null && config.getMobileOSVersion() != null) {
            return config.getMobileOSVersion();
        }
        
        // Fallback to mobile properties
        return configManager.getMobileProperty("MobileOSVersion");
    }
    
    /**
     * Check if configuration exists in Java configuration
     */
    public static boolean hasJavaConfiguration(String testConfiguration) {
        return configManager.getTestConfiguration(testConfiguration) != null;
    }
    
    /**
     * Get all available configuration names
     */
    public static String[] getAvailableConfigurations() {
        return new String[]{
            "LOCALChrome", "LOCALChrome_HEADLESS", "LOCALFirefox", "LOCALEdge", "LOCALChromeEmulation",
            "AppiumNativeAndroid", "AppiumWebAndroid", "AppiumNativeIOS", "AppiumWebIOS",
            "SaucelabsWebChrome", "SaucelabsWebFirefox", "SaucelabsWebEdge", 
            "SaucelabsMobileAndroid", "SaucelabsWebAndroid", "SaucelabsWebIOS", "SaucelabsMobileIOS",
            "LAMBDATESTWebChrome", "LAMBDATESTWebFirefox", "LAMBDATESTWebEdge",
            "LAMBDATESTMobileAndroid", "LAMBDATESTMobileIOS", "LAMBDATESTWebAndroid", "LAMBDATESTWebIOS",
            "BROWSERSTACKWebChrome", "BROWSERSTACKWebFirefox", "BROWSERSTACKWebEdge",
            "BROWSERSTACKMobileAndroid", "BROWSERSTACKMobileIOS", "BROWSERSTACKWebAndroid", "BROWSERSTACKWebIOS",
            "API", "GRID", "WINDOWSEXECUTION"
        };
    }
}