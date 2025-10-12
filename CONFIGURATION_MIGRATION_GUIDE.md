# Configuration Migration Guide: JSON to Java

## Overview
Successfully migrated browser and test configuration logic from `properties.json` file to Java-based configuration classes. This provides better type safety, IDE support, and maintainability.

## New Configuration Structure

### 1. TestConfiguration.java
- **Purpose**: Data class to hold test configuration parameters
- **Fields**: ExecutionMode, ToolName, Browser, BrowserVersion, Platform, DeviceName, MobileExecutionPlatform, MobileOSVersion
- **Benefits**: Type-safe configuration with proper getters/setters

### 2. ConfigurationFactory.java
- **Purpose**: Factory class that creates all test configurations programmatically
- **Replaces**: properties.json file
- **Contains**: All 30+ test configurations from the original JSON file

### 3. ConfigurationHelper.java
- **Purpose**: Bridge between old JSON-based and new Java-based configuration
- **Provides**: Backward compatibility during migration
- **Features**: Fallback mechanism to properties files if Java config not found

## Migration Benefits

### ✅ **Type Safety**
- Enum-based configuration (ExecutionMode, Browser, ToolName, MobileExecutionPlatform)
- Compile-time validation of configuration values
- No more runtime errors from typos in JSON

### ✅ **IDE Support**
- Auto-completion for configuration names
- Refactoring support across the codebase
- Better debugging and navigation

### ✅ **Maintainability**
- Single source of truth in Java code
- Version control friendly (no external JSON files)
- Easy to add new configurations programmatically

### ✅ **Performance**
- No JSON parsing overhead at runtime
- Configurations loaded once during class initialization
- Faster test startup times

## Configuration Examples

### Local Browser Configurations
```java
// Chrome
TestConfiguration config = ConfigurationFactory.getConfiguration("LOCALChrome");
// ExecutionMode: LOCAL, ToolName: SELENIUM, Browser: CHROME

// Chrome Headless
TestConfiguration config = ConfigurationFactory.getConfiguration("LOCALChrome_HEADLESS");
// ExecutionMode: LOCAL, ToolName: SELENIUM, Browser: CHROME_HEADLESS

// Firefox
TestConfiguration config = ConfigurationFactory.getConfiguration("LOCALFirefox");
// ExecutionMode: LOCAL, ToolName: SELENIUM, Browser: FIREFOX
```

### Mobile Configurations
```java
// Android Native
TestConfiguration config = ConfigurationFactory.getConfiguration("AppiumNativeAndroid");
// ExecutionMode: MOBILE, ToolName: APPIUM, MobileExecutionPlatform: ANDROID

// iOS Web
TestConfiguration config = ConfigurationFactory.getConfiguration("AppiumWebIOS");
// ExecutionMode: MOBILE, ToolName: APPIUM, MobileExecutionPlatform: WEB_IOS, Browser: SAFARI
```

### Cloud Provider Configurations
```java
// SauceLabs
TestConfiguration config = ConfigurationFactory.getConfiguration("SaucelabsWebChrome");
// ExecutionMode: SAUCELABS, ToolName: REMOTE_WEBDRIVER, Browser: CHROME

// LambdaTest
TestConfiguration config = ConfigurationFactory.getConfiguration("LAMBDATESTWebFirefox");
// ExecutionMode: LAMBDATEST, ToolName: REMOTE_WEBDRIVER, Browser: FIREFOX

// BrowserStack
TestConfiguration config = ConfigurationFactory.getConfiguration("BROWSERSTACKWebEdge");
// ExecutionMode: BROWSERSTACK, ToolName: REMOTE_WEBDRIVER, Browser: EDGE
```

## Usage in Code

### Direct Usage
```java
import com.framework.config.ConfigurationFactory;
import com.framework.config.TestConfiguration;

// Get configuration
TestConfiguration config = ConfigurationFactory.getConfiguration("LOCALChrome");

// Access properties
ExecutionMode mode = config.getExecutionMode();
Browser browser = config.getBrowser();
String platform = config.getPlatform();
```

### Helper Usage (Backward Compatibility)
```java
import com.framework.config.ConfigurationHelper;

// Get configuration with fallback support
ExecutionMode mode = ConfigurationHelper.getExecutionMode("LOCALChrome");
Browser browser = ConfigurationHelper.getBrowser("LOCALChrome");
String deviceName = ConfigurationHelper.getDeviceName("AppiumNativeAndroid");
```

### Integration with ConfigurationManager
```java
import com.framework.config.ConfigurationManager;

ConfigurationManager configManager = ConfigurationManager.getInstance();
TestConfiguration config = configManager.getTestConfiguration("LOCALChrome");
```

## Available Configurations

### Local Configurations
- `LOCALChrome` - Chrome browser
- `LOCALChrome_HEADLESS` - Chrome headless
- `LOCALFirefox` - Firefox browser
- `LOCALEdge` - Edge browser
- `LOCALChromeEmulation` - Chrome mobile emulation

### Mobile Configurations
- `AppiumNativeAndroid` - Android native app
- `AppiumWebAndroid` - Android web browser
- `AppiumNativeIOS` - iOS native app
- `AppiumWebIOS` - iOS web browser

### SauceLabs Configurations
- `SaucelabsWebChrome` - Chrome on SauceLabs
- `SaucelabsWebFirefox` - Firefox on SauceLabs
- `SaucelabsWebEdge` - Edge on SauceLabs
- `SaucelabsMobileAndroid` - Android on SauceLabs
- `SaucelabsWebAndroid` - Android web on SauceLabs
- `SaucelabsWebIOS` - iOS web on SauceLabs
- `SaucelabsMobileIOS` - iOS native on SauceLabs

### LambdaTest Configurations
- `LAMBDATESTWebChrome` - Chrome on LambdaTest
- `LAMBDATESTWebFirefox` - Firefox on LambdaTest
- `LAMBDATESTWebEdge` - Edge on LambdaTest
- `LAMBDATESTMobileAndroid` - Android on LambdaTest
- `LAMBDATESTMobileIOS` - iOS on LambdaTest
- `LAMBDATESTWebAndroid` - Android web on LambdaTest
- `LAMBDATESTWebIOS` - iOS web on LambdaTest

### BrowserStack Configurations
- `BROWSERSTACKWebChrome` - Chrome on BrowserStack
- `BROWSERSTACKWebFirefox` - Firefox on BrowserStack
- `BROWSERSTACKWebEdge` - Edge on BrowserStack
- `BROWSERSTACKMobileAndroid` - Android on BrowserStack
- `BROWSERSTACKMobileIOS` - iOS on BrowserStack
- `BROWSERSTACKWebAndroid` - Android web on BrowserStack
- `BROWSERSTACKWebIOS` - iOS web on BrowserStack

### Special Configurations
- `API` - API testing configuration
- `GRID` - Selenium Grid configuration
- `WINDOWSEXECUTION` - Windows application testing

## Migration Steps

### 1. Update Test Code
Replace JSON-based configuration access:
```java
// OLD - JSON based
String browser = jsonConfig.get("Browser").asText();

// NEW - Java based
Browser browser = ConfigurationHelper.getBrowser("LOCALChrome");
```

### 2. Update Driver Initialization
```java
// OLD
JsonNode config = configJson.get(testConfiguration);
String browser = config.get("Browser").asText();

// NEW
TestConfiguration config = ConfigurationFactory.getConfiguration(testConfiguration);
Browser browser = config.getBrowser();
```

### 3. Add New Configurations
```java
// Add to ConfigurationFactory.java
configurations.put("MyNewConfig", createMyNewConfig());

private static TestConfiguration createMyNewConfig() {
    TestConfiguration config = new TestConfiguration(ExecutionMode.LOCAL, ToolName.SELENIUM);
    config.setBrowser(Browser.CHROME);
    config.setBrowserVersion("latest");
    return config;
}
```

## Backward Compatibility

The migration maintains full backward compatibility:
- Existing test code continues to work
- ConfigurationHelper provides fallback to properties files
- Gradual migration possible - no big bang changes required

## Files Created
1. `/src/test/java/com/framework/config/TestConfiguration.java`
2. `/src/test/java/com/framework/config/ConfigurationFactory.java`
3. `/src/test/java/com/framework/config/ConfigurationHelper.java`

## Files Modified
1. `/src/test/java/com/framework/config/ConfigurationManager.java` - Added getTestConfiguration() method

## Next Steps
1. Update driver factories to use Java configuration
2. Gradually migrate test code from JSON to Java configuration
3. Remove properties.json file once migration is complete
4. Add unit tests for configuration classes