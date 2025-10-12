package com.framework.config;

import com.framework.selenium.Browser;
import com.framework.selenium.ExecutionMode;
import com.framework.selenium.MobileExecutionPlatform;
import com.framework.components.ToolName;

/**
 * Test configuration class to replace JSON properties
 */
public class TestConfiguration {
    
    private ExecutionMode executionMode;
    private ToolName toolName;
    private Browser browser;
    private String browserVersion;
    private String platform;
    private String deviceName;
    private MobileExecutionPlatform mobileExecutionPlatform;
    private String mobileOSVersion;
    
    public TestConfiguration(ExecutionMode executionMode, ToolName toolName) {
        this.executionMode = executionMode;
        this.toolName = toolName;
    }
    
    // Getters and Setters
    public ExecutionMode getExecutionMode() { return executionMode; }
    public void setExecutionMode(ExecutionMode executionMode) { this.executionMode = executionMode; }
    
    public ToolName getToolName() { return toolName; }
    public void setToolName(ToolName toolName) { this.toolName = toolName; }
    
    public Browser getBrowser() { return browser; }
    public void setBrowser(Browser browser) { this.browser = browser; }
    
    public String getBrowserVersion() { return browserVersion; }
    public void setBrowserVersion(String browserVersion) { this.browserVersion = browserVersion; }
    
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    
    public MobileExecutionPlatform getMobileExecutionPlatform() { return mobileExecutionPlatform; }
    public void setMobileExecutionPlatform(MobileExecutionPlatform mobileExecutionPlatform) { this.mobileExecutionPlatform = mobileExecutionPlatform; }
    
    public String getMobileOSVersion() { return mobileOSVersion; }
    public void setMobileOSVersion(String mobileOSVersion) { this.mobileOSVersion = mobileOSVersion; }
}