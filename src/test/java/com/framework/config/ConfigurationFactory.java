package com.framework.config;

import com.framework.selenium.Browser;
import com.framework.selenium.ExecutionMode;
import com.framework.selenium.MobileExecutionPlatform;
import com.framework.components.ToolName;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class to create test configurations programmatically
 * Replaces the properties.json file with Java code
 */
public class ConfigurationFactory {
    
    private static final Map<String, TestConfiguration> configurations = new HashMap<>();
    
    static {
        initializeConfigurations();
    }
    
    /**
     * Initialize all test configurations
     */
    private static void initializeConfigurations() {
        
        // LOCAL CONFIGURATIONS
        configurations.put("LOCALChrome", createLocalConfig(Browser.CHROME));
        configurations.put("LOCALChrome_HEADLESS", createLocalConfig(Browser.CHROME_HEADLESS));
        configurations.put("LOCALFirefox", createLocalConfig(Browser.FIREFOX));
        configurations.put("LOCALEdge", createLocalConfig(Browser.EDGE));
        configurations.put("LOCALChromeEmulation", createLocalChromeEmulation());
        
        // APPIUM CONFIGURATIONS
        configurations.put("AppiumNativeAndroid", createAppiumNativeAndroid());
        configurations.put("AppiumWebAndroid", createAppiumWebAndroid());
        configurations.put("AppiumNativeIOS", createAppiumNativeIOS());
        configurations.put("AppiumWebIOS", createAppiumWebIOS());
        
        // SAUCELABS CONFIGURATIONS
        configurations.put("SaucelabsWebChrome", createSaucelabsWeb(Browser.CHROME));
        configurations.put("SaucelabsWebFirefox", createSaucelabsWeb(Browser.FIREFOX));
        configurations.put("SaucelabsWebEdge", createSaucelabsWeb(Browser.EDGE));
        configurations.put("SaucelabsMobileAndroid", createSaucelabsMobileAndroid());
        configurations.put("SaucelabsWebAndroid", createSaucelabsWebAndroid());
        configurations.put("SaucelabsWebIOS", createSaucelabsWebIOS());
        configurations.put("SaucelabsMobileIOS", createSaucelabsMobileIOS());
        
        // LAMBDATEST CONFIGURATIONS
        configurations.put("LAMBDATESTWebChrome", createLambdaTestWeb(Browser.CHROME));
        configurations.put("LAMBDATESTWebFirefox", createLambdaTestWeb(Browser.FIREFOX));
        configurations.put("LAMBDATESTWebEdge", createLambdaTestWeb(Browser.EDGE));
        configurations.put("LAMBDATESTMobileAndroid", createLambdaTestMobileAndroid());
        configurations.put("LAMBDATESTMobileIOS", createLambdaTestMobileIOS());
        configurations.put("LAMBDATESTWebAndroid", createLambdaTestWebAndroid());
        configurations.put("LAMBDATESTWebIOS", createLambdaTestWebIOS());
        
        // BROWSERSTACK CONFIGURATIONS
        configurations.put("BROWSERSTACKWebChrome", createBrowserStackWeb(Browser.CHROME));
        configurations.put("BROWSERSTACKWebFirefox", createBrowserStackWeb(Browser.FIREFOX));
        configurations.put("BROWSERSTACKWebEdge", createBrowserStackWeb(Browser.EDGE));
        configurations.put("BROWSERSTACKMobileAndroid", createBrowserStackMobileAndroid());
        configurations.put("BROWSERSTACKMobileIOS", createBrowserStackMobileIOS());
        configurations.put("BROWSERSTACKWebAndroid", createBrowserStackWebAndroid());
        configurations.put("BROWSERSTACKWebIOS", createBrowserStackWebIOS());
        
        // OTHER CONFIGURATIONS
        configurations.put("API", createAPIConfig());
        configurations.put("GRID", createGridConfig());
        configurations.put("WINDOWSEXECUTION", createWindowsConfig());
    }
    
    /**
     * Get configuration by name
     */
    public static TestConfiguration getConfiguration(String configName) {
        return configurations.get(configName);
    }
    
    // LOCAL CONFIGURATIONS
    private static TestConfiguration createLocalConfig(Browser browser) {
        TestConfiguration config = new TestConfiguration(ExecutionMode.LOCAL, ToolName.SELENIUM);
        config.setBrowser(browser);
        return config;
    }
    
    private static TestConfiguration createLocalChromeEmulation() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.LOCAL, ToolName.SELENIUM);
        config.setBrowser(Browser.CHROME_MOBILE_EMULATION);
        config.setDeviceName("Galaxy S5");
        return config;
    }
    
    // APPIUM CONFIGURATIONS
    private static TestConfiguration createAppiumNativeAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.MOBILE, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.ANDROID);
        config.setMobileOSVersion("9.0");
        config.setDeviceName("emulator-5556");
        return config;
    }
    
    private static TestConfiguration createAppiumWebAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.MOBILE, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_ANDROID);
        config.setMobileOSVersion("9.0");
        config.setDeviceName("emulator-5556");
        config.setBrowser(Browser.CHROME);
        return config;
    }
    
    private static TestConfiguration createAppiumNativeIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.MOBILE, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.IOS);
        config.setMobileOSVersion("14.5.1");
        config.setDeviceName("00008101-001578CA28E1401E");
        return config;
    }
    
    private static TestConfiguration createAppiumWebIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.MOBILE, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_IOS);
        config.setMobileOSVersion("15.0");
        config.setDeviceName("40F0AD7A-5694-4ABB-A59E-1749232E0B26");
        config.setBrowser(Browser.SAFARI);
        return config;
    }
    
    // SAUCELABS CONFIGURATIONS
    private static TestConfiguration createSaucelabsWeb(Browser browser) {
        TestConfiguration config = new TestConfiguration(ExecutionMode.SAUCELABS, ToolName.REMOTE_WEBDRIVER);
        config.setBrowser(browser);
        config.setBrowserVersion("latest");
        config.setPlatform("WIN10");
        return config;
    }
    
    private static TestConfiguration createSaucelabsMobileAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.SAUCELABS, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.ANDROID);
        config.setMobileOSVersion("12");
        config.setDeviceName("Samsung Galaxy S21 5G");
        config.setBrowser(Browser.CHROME);
        return config;
    }
    
    private static TestConfiguration createSaucelabsWebAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.SAUCELABS, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_ANDROID);
        config.setMobileOSVersion("8.0");
        config.setDeviceName("Android Emulator");
        config.setBrowser(Browser.CHROME);
        return config;
    }
    
    private static TestConfiguration createSaucelabsWebIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.SAUCELABS, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_IOS);
        config.setMobileOSVersion("13.2");
        config.setDeviceName("iPhone XS Max Simulator");
        config.setBrowser(Browser.SAFARI);
        return config;
    }
    
    private static TestConfiguration createSaucelabsMobileIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.SAUCELABS, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.IOS);
        config.setMobileOSVersion("16");
        config.setDeviceName("iPhone 14 Pro");
        config.setBrowser(Browser.CHROME);
        return config;
    }
    
    // LAMBDATEST CONFIGURATIONS
    private static TestConfiguration createLambdaTestWeb(Browser browser) {
        TestConfiguration config = new TestConfiguration(ExecutionMode.LAMBDATEST, ToolName.REMOTE_WEBDRIVER);
        config.setBrowser(browser);
        config.setBrowserVersion("latest");
        config.setPlatform("WIN10");
        return config;
    }
    
    private static TestConfiguration createLambdaTestMobileAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.LAMBDATEST, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.ANDROID);
        config.setMobileOSVersion("12");
        config.setDeviceName("Galaxy S21 Ultra 5G");
        return config;
    }
    
    private static TestConfiguration createLambdaTestMobileIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.LAMBDATEST, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.IOS);
        config.setMobileOSVersion("16");
        config.setDeviceName("iPhone 14 Pro Max");
        return config;
    }
    
    private static TestConfiguration createLambdaTestWebAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.LAMBDATEST, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_ANDROID);
        config.setMobileOSVersion("11");
        config.setDeviceName("Galaxy S21 Ultra 5G");
        config.setBrowser(Browser.CHROME);
        return config;
    }
    
    private static TestConfiguration createLambdaTestWebIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.LAMBDATEST, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_IOS);
        config.setMobileOSVersion("14");
        config.setDeviceName("iPhone 11");
        config.setBrowser(Browser.SAFARI);
        return config;
    }
    
    // BROWSERSTACK CONFIGURATIONS
    private static TestConfiguration createBrowserStackWeb(Browser browser) {
        TestConfiguration config = new TestConfiguration(ExecutionMode.BROWSERSTACK, ToolName.REMOTE_WEBDRIVER);
        config.setBrowser(browser);
        config.setBrowserVersion("latest");
        config.setPlatform("WIN10");
        return config;
    }
    
    private static TestConfiguration createBrowserStackMobileAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.BROWSERSTACK, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.ANDROID);
        config.setMobileOSVersion("11.0");
        config.setDeviceName("Samsung Galaxy S21 Ultra");
        return config;
    }
    
    private static TestConfiguration createBrowserStackMobileIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.BROWSERSTACK, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.IOS);
        config.setMobileOSVersion("14");
        config.setDeviceName("iPhone 11");
        return config;
    }
    
    private static TestConfiguration createBrowserStackWebAndroid() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.BROWSERSTACK, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_ANDROID);
        config.setMobileOSVersion("10.0");
        config.setDeviceName("Samsung Galaxy S20");
        config.setBrowser(Browser.CHROME);
        return config;
    }
    
    private static TestConfiguration createBrowserStackWebIOS() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.BROWSERSTACK, ToolName.APPIUM);
        config.setMobileExecutionPlatform(MobileExecutionPlatform.WEB_IOS);
        config.setMobileOSVersion("16");
        config.setDeviceName("iPhone 14");
        config.setBrowser(Browser.SAFARI);
        return config;
    }
    
    // OTHER CONFIGURATIONS
    private static TestConfiguration createAPIConfig() {
        return new TestConfiguration(ExecutionMode.API, ToolName.API);
    }
    
    private static TestConfiguration createGridConfig() {
        TestConfiguration config = new TestConfiguration(ExecutionMode.GRID, ToolName.SELENIUM);
        config.setBrowser(Browser.EDGE);
        config.setBrowserVersion("12");
        config.setPlatform("MAC");
        return config;
    }
    
    private static TestConfiguration createWindowsConfig() {
        return new TestConfiguration(ExecutionMode.WINDOWS, ToolName.WINDOWS);
    }
}