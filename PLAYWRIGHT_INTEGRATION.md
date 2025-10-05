# Playwright Integration Guide

## Overview
This document outlines the integration of Microsoft Playwright into the QualiFrameV2 framework, providing modern web automation capabilities alongside existing Selenium support.

## 🚀 What is Playwright?
Playwright is a modern automation library that enables reliable end-to-end testing for modern web apps. It supports:
- **Cross-browser testing** (Chromium, Firefox, WebKit)
- **Fast and reliable** element interactions
- **Auto-wait** for elements to be ready
- **Network interception** and mocking
- **Mobile emulation** and responsive testing
- **Screenshots and videos** for debugging

## 📦 Setup and Configuration

### Dependencies
Add Playwright dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.40.0</version>
</dependency>
```

### Install Playwright Browsers
Run the following command to install browser binaries:
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### Configuration Properties
The following properties are available in `Global Settings.properties`:

```properties
# Playwright Settings
PlaywrightHeadless=false          # Run in headless mode
PlaywrightSlowMo=0               # Slow down operations (ms)
PlaywrightTimeout=30000          # Default timeout (ms)
PlaywrightNavigationTimeout=30000 # Navigation timeout (ms)
PlaywrightViewportWidth=1920     # Browser width
PlaywrightViewportHeight=1080    # Browser height
PlaywrightRecordVideo=false      # Record test videos
PlaywrightTracing=false          # Enable tracing
PlaywrightLocale=en-US           # Browser locale
```

## 🏗️ Framework Components

### WebPWReusableComponents
Base class for Playwright-based test automation with comprehensive methods:

#### Navigation Methods
```java
navigateTo(String url)           // Navigate to URL
reloadPage()                     // Reload current page
goBack()                         // Navigate back
goForward()                      // Navigate forward
```

#### Element Interactions
```java
click(String selector)           // Click element
doubleClick(String selector)     // Double click
rightClick(String selector)      // Right click
fill(String selector, String text) // Fill input field
type(String selector, String text, int delay) // Type with delay
clear(String selector)           // Clear input field
```

#### Element Queries
```java
getText(String selector)         // Get element text
getAttribute(String selector, String attr) // Get attribute
getValue(String selector)        // Get input value
isVisible(String selector)       // Check visibility
isEnabled(String selector)       // Check enabled state
isChecked(String selector)       // Check checkbox state
```

#### Wait Methods
```java
waitForVisible(String selector, int timeout) // Wait for visibility
waitForHidden(String selector, int timeout)  // Wait for hidden
waitForLoad()                    // Wait for page load
waitForNetworkIdle()            // Wait for network idle
```

#### Advanced Features
```java
hover(String selector)           // Hover over element
dragAndDrop(String source, String target) // Drag and drop
selectByValue(String selector, String value) // Select dropdown
pressKey(String key)            // Press keyboard key
executeScript(String script)    // Execute JavaScript
takeScreenshot(String fileName) // Take screenshot
```

## 💡 Usage Examples

### Basic Test Class
```java
public class PlaywrightTest extends WebPWReusableComponents {
    
    public PlaywrightTest(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    @Test
    public void testLogin() {
        // Navigate to application
        navigateTo("https://example.com");
        waitForLoad();
        
        // Perform login
        fill("#username", "testuser");
        fill("#password", "password123");
        click("#login-button");
        
        // Verify success
        waitForVisible(".dashboard", 10);
        String title = getTitle();
        
        if (title.contains("Dashboard")) {
            addTestLog("Login", "Login successful", Status.PASS);
        }
        
        // Take screenshot
        takeScreenshot("login_success.png");
    }
}
```

### Advanced Features Demo
```java
public void testAdvancedFeatures() {
    navigateTo("https://example.com");
    
    // Element state checks
    if (isVisible("#submit-btn") && isEnabled("#submit-btn")) {
        click("#submit-btn");
    }
    
    // Dropdown selection
    selectByValue("#country", "US");
    
    // Hover and drag-drop
    hover(".menu-item");
    dragAndDrop("#source", "#target");
    
    // Keyboard actions
    pressKey("Enter");
    pressKey("Escape");
    
    // JavaScript execution
    Object result = executeScript("return document.readyState");
    
    // Multiple element handling
    int elementCount = getElementCount(".product-item");
    List<Locator> elements = getAllElements(".product-item");
    
    // Network and performance
    waitForNetworkIdle();
    
    // Screenshots and debugging
    takeElementScreenshot(".main-content", "content.png");
}
```

### Form Handling
```java
public void testFormHandling() {
    navigateTo("https://form-example.com");
    
    // Fill form fields
    fill("#firstName", "John");
    fill("#lastName", "Doe");
    fill("#email", generateRandomEmail());
    
    // Select dropdown
    selectByValue("#country", "United States");
    
    // Handle checkboxes
    click("#terms-checkbox");
    
    // Verify checkbox state
    boolean isChecked = isChecked("#terms-checkbox");
    
    // Submit form
    click("#submit-button");
    
    // Wait for success message
    waitForVisible(".success-message", 10);
    String successText = getText(".success-message");
    
    addTestLog("Form Submit", "Success: " + successText, Status.PASS);
}
```

## 🔧 Integration with Existing Framework

### Test Configuration
Update your test configuration to use Playwright:
```properties
TestConfigurationID=PLAYWRIGHTChrome
```

### Cucumber Step Definitions
```java
public class PlaywrightSteps extends WebPWReusableComponents {
    
    @Given("I navigate to the application")
    public void navigateToApp() {
        navigateTo(properties.getProperty("qaAppUrl"));
        waitForLoad();
    }
    
    @When("I login with credentials {string} and {string}")
    public void loginWithCredentials(String username, String password) {
        fill("#username", username);
        fill("#password", password);
        click("#login-button");
    }
    
    @Then("I should see the dashboard")
    public void verifyDashboard() {
        waitForVisible(".dashboard", 10);
        boolean isDashboardVisible = isVisible(".dashboard");
        
        if (isDashboardVisible) {
            addTestLog("Dashboard", "Dashboard loaded successfully", Status.PASS);
        } else {
            addTestLog("Dashboard", "Dashboard not found", Status.FAIL);
        }
    }
}
```

### Data-Driven Testing
```java
public void testWithDataProvider() {
    // Load test data
    List<HashMap<String, String>> testData = loadCsvData("login_data.csv");
    
    for (HashMap<String, String> data : testData) {
        navigateTo(baseUrl);
        
        fill("#username", data.get("username"));
        fill("#password", data.get("password"));
        click("#login-button");
        
        // Verify result based on expected outcome
        String expectedResult = data.get("expected");
        boolean loginSuccess = isVisible(".dashboard");
        
        if (expectedResult.equals("success") && loginSuccess) {
            addTestLog("Login Test", "Login successful for: " + data.get("username"), Status.PASS);
        } else if (expectedResult.equals("failure") && !loginSuccess) {
            addTestLog("Login Test", "Login correctly failed for: " + data.get("username"), Status.PASS);
        } else {
            addTestLog("Login Test", "Unexpected result for: " + data.get("username"), Status.FAIL);
        }
        
        // Logout if needed
        if (loginSuccess) {
            click("#logout-button");
            waitForVisible("#login-button", 5);
        }
    }
}
```

## 🎯 Best Practices

### 1. Selector Strategy
- **Use data-testid attributes** for reliable element identification
- **Prefer CSS selectors** over XPath for better performance
- **Use semantic selectors** when possible (role, text content)

```java
// Good practices
click("[data-testid='submit-button']");
click("button:has-text('Submit')");
click("role=button[name='Submit']");

// Avoid if possible
click("//div[@class='container']//button[1]");
```

### 2. Wait Strategies
- **Use explicit waits** instead of fixed delays
- **Wait for specific conditions** rather than generic timeouts
- **Combine multiple wait conditions** for complex scenarios

```java
// Good practices
waitForVisible(".loading-spinner", 5);
waitForHidden(".loading-spinner", 10);
waitForNetworkIdle();

// Avoid
waitFor(5); // Fixed delay
```

### 3. Error Handling
- **Implement proper error handling** for flaky elements
- **Use screenshots** for debugging failures
- **Log meaningful messages** for test results

```java
try {
    click("#submit-button");
    waitForVisible(".success-message", 10);
    addTestLog("Submit", "Form submitted successfully", Status.PASS);
} catch (Exception e) {
    takeScreenshot("submit_error.png");
    addTestLog("Submit", "Form submission failed: " + e.getMessage(), Status.FAIL);
}
```

### 4. Performance Optimization
- **Reuse browser contexts** when possible
- **Use network interception** to mock slow APIs
- **Enable tracing** only for debugging sessions

## 🔍 Debugging and Troubleshooting

### Enable Tracing
```properties
PlaywrightTracing=true
```

### Video Recording
```properties
PlaywrightRecordVideo=true
```

### Common Issues
1. **Element not found**: Use proper wait strategies
2. **Timing issues**: Increase timeouts or use network idle waits
3. **Browser crashes**: Check system resources and browser versions
4. **Selector issues**: Use Playwright inspector for selector validation

### Debug Mode
Run tests with debug mode:
```bash
PWDEBUG=1 mvn test
```

## 🚀 Migration from Selenium

### Key Differences
| Feature | Selenium | Playwright |
|---------|----------|------------|
| Auto-wait | Manual waits needed | Built-in auto-wait |
| Browser support | Chrome, Firefox, Safari, Edge | Chromium, Firefox, WebKit |
| Speed | Moderate | Faster |
| Network control | Limited | Full network interception |
| Mobile testing | Requires Appium | Built-in mobile emulation |

### Migration Steps
1. **Identify test cases** suitable for Playwright
2. **Update selectors** to use Playwright syntax
3. **Replace explicit waits** with Playwright's auto-wait
4. **Update assertions** to use framework's logging
5. **Test thoroughly** with different browsers

This integration provides a modern, fast, and reliable alternative to Selenium while maintaining compatibility with the existing QualiFrameV2 framework structure.