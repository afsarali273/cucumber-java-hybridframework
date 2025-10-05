# Playwright Cucumber Integration Guide

## Overview
This guide demonstrates the complete implementation of Playwright with Cucumber BDD in the QualiFrameV2 framework, providing a modern alternative to Selenium-based testing with enhanced capabilities.

## 🏗️ Project Structure

```
src/test/java/
├── com/cucumber/steps/
│   ├── PlaywrightWebSteps.java          # Basic Playwright step definitions
│   └── PlaywrightEnhancedSteps.java     # Advanced Playwright step definitions
├── com/pages/
│   ├── PlaywrightWebPage.java           # Combined page object (legacy style)
│   ├── PlaywrightLoginPage.java         # Dedicated login page object
│   └── PlaywrightInventoryPage.java     # Dedicated inventory page object
└── com/framework/playwright/
    ├── BasePage.java                     # Base page class
    ├── PlaywrightElement.java            # Enhanced element wrapper
    ├── PageFactory.java                  # Page object factory
    └── annotations/FindBy.java           # Element locator annotation

src/test/resources/features/
├── PlaywrightWeb.feature                # Basic Playwright scenarios
└── PlaywrightWebEnhanced.feature        # Advanced Playwright scenarios
```

## 🚀 Features Implemented

### 1. **Feature Files**

#### Basic Playwright Testing (`PlaywrightWeb.feature`)
```gherkin
@PlaywrightSauceDemoLoginTest
Scenario Outline: Playwright Sauce Demo App Login for iteration 1
  Given Launch Playwright Application using "<tc_id>"
  Then  verify playwright login page displayed successfully
  When  User enters userCredentials in playwright text field
  And   User clicks playwright Login button
  Then  verify playwright home page displayed successfully
```

#### Enhanced Testing (`PlaywrightWebEnhanced.feature`)
- **Cross-browser responsive testing**
- **Performance and load time validation**
- **Accessibility testing**
- **Data-driven testing**
- **Visual regression testing**
- **Complete E2E shopping flows**

### 2. **Page Objects**

#### PlaywrightLoginPage
```java
@FindBy(id = "user-name")
private PlaywrightElement usernameField;

@FindBy(id = "password")
private PlaywrightElement passwordField;

@FindBy(id = "login-button")
private PlaywrightElement loginButton;

public PlaywrightInventoryPage login(String username, String password) {
    enterUsername(username);
    enterPassword(password);
    clickLoginButton();
    return new PlaywrightInventoryPage(page);
}
```

#### PlaywrightInventoryPage
```java
@FindBy(css = ".inventory_item")
private PlaywrightElement inventoryItems;

public void addProductToCartByName(String productName) {
    PlaywrightElement product = new PlaywrightElement(page, 
        ".inventory_item:has(.inventory_item_name:text('" + productName + "'))");
    PlaywrightElement addButton = new PlaywrightElement(
        product.getLocator().locator(".btn_inventory"), "Add button");
    addButton.click();
}
```

### 3. **Step Definitions**

#### Basic Steps (`PlaywrightWebSteps.java`)
- Application launch and navigation
- Login functionality
- Form interactions
- Basic validations

#### Enhanced Steps (`PlaywrightEnhancedSteps.java`)
- Performance testing
- Responsive design testing
- Accessibility validation
- Visual regression testing
- Advanced user interactions

## 💡 Usage Examples

### Basic Login Test
```gherkin
Scenario: Successful login with valid credentials
  Given Launch Playwright Application using "SeleniumPracticeTest4"
  Then verify playwright login page displayed successfully
  When User enters userCredentials in playwright text field
  And User clicks playwright Login button
  Then verify playwright home page displayed successfully
```

### Data-Driven Testing
```gherkin
Scenario Outline: Data-driven login testing
  When User enters "<username>" and "<password>" using playwright
  And User clicks playwright Login button
  Then verify login result is "<expected_result>" using playwright

  Examples:
    | username        | password     | expected_result |
    | standard_user   | secret_sauce | success         |
    | locked_out_user | secret_sauce | error           |
    | invalid_user    | wrong_pass   | error           |
```

### E2E Shopping Flow
```gherkin
Scenario: Complete shopping flow
  When User enters userCredentials in playwright text field
  And User clicks playwright Login button
  When User adds multiple products to cart using playwright:
    | Sauce Labs Backpack    |
    | Sauce Labs Bike Light  |
    | Sauce Labs Bolt T-Shirt|
  Then verify cart count is 3 using playwright
```

### Responsive Testing
```gherkin
Scenario Outline: Cross-browser responsive testing
  When User sets viewport to "<width>x<height>" using playwright
  Then verify playwright login page displayed successfully
  When User enters userCredentials in playwright text field
  And User clicks playwright Login button
  Then verify page layout is responsive using playwright

  Examples:
    | width | height |
    | 1920  | 1080   |
    | 768   | 1024   |
    | 375   | 667    |
```

## 🎯 Key Advantages Over Selenium

### 1. **Auto-Wait Capabilities**
```java
// Playwright - No explicit waits needed
usernameField.fill("testuser");
loginButton.click();

// vs Selenium - Requires explicit waits
WebDriverWait wait = new WebDriverWait(driver, 10);
wait.until(ExpectedConditions.elementToBeClickable(usernameField));
usernameField.sendKeys("testuser");
```

### 2. **Modern Selectors**
```java
// Text-based selectors
@FindBy(text = "Sign In")
private PlaywrightElement signInButton;

// Role-based selectors
@FindBy(role = "button")
private PlaywrightElement submitButton;

// Test ID selectors (recommended)
@FindBy(testId = "login-submit")
private PlaywrightElement loginSubmit;
```

### 3. **Built-in Network Control**
```java
// Intercept and mock API calls
page.route("**/api/users", route -> {
    route.fulfill(new Route.FulfillOptions()
        .setStatus(200)
        .setBody("{\"users\": []}"));
});
```

### 4. **Enhanced Debugging**
```java
// Built-in tracing and video recording
context.tracing().start(new Tracing.StartOptions()
    .setScreenshots(true)
    .setSnapshots(true));

// Element highlighting
element.getLocator().highlight();
```

## 🔧 Configuration

### Properties Setup
```properties
# Playwright Settings
PlaywrightHeadless=false
PlaywrightSlowMo=0
PlaywrightTimeout=30000
PlaywrightViewportWidth=1920
PlaywrightViewportHeight=1080
PlaywrightRecordVideo=false
PlaywrightTracing=false
```

### Test Configuration
```properties
TestConfigurationID=PLAYWRIGHTChrome
ExecutionApproach=CUCUMBER
```

## 🚦 Running Tests

### Command Line Execution
```bash
# Run all Playwright tests
mvn test -Dcucumber.filter.tags="@Playwright"

# Run specific test types
mvn test -Dcucumber.filter.tags="@PlaywrightLogin"
mvn test -Dcucumber.filter.tags="@PlaywrightE2E"
mvn test -Dcucumber.filter.tags="@PlaywrightResponsive"

# Run with specific browser
mvn test -DPlaywrightBrowser=firefox -Dcucumber.filter.tags="@Playwright"

# Run in headless mode
mvn test -DPlaywrightHeadless=true -Dcucumber.filter.tags="@Playwright"
```

### IDE Execution
1. Right-click on feature file
2. Select "Run as Cucumber Feature"
3. Or run individual scenarios by right-clicking on scenario

## 📊 Test Reports

### Cucumber Reports
- HTML reports generated in `target/cucumber-reports/`
- JSON reports for CI/CD integration
- Screenshots automatically attached to failed steps

### Playwright Traces
- Detailed execution traces when enabled
- Network activity logs
- Console logs and errors
- Performance metrics

### Visual Reports
- Screenshot comparisons
- Element highlighting
- Page layout validation

## 🔍 Debugging Features

### 1. **Interactive Debugging**
```java
// Pause execution for manual inspection
page.pause();

// Step-by-step debugging
page.setDefaultTimeout(0); // Disable timeouts for debugging
```

### 2. **Element Inspection**
```java
// Highlight elements
element.getLocator().highlight();

// Get element information
System.out.println("Element visible: " + element.isVisible());
System.out.println("Element text: " + element.getText());
```

### 3. **Network Monitoring**
```java
// Monitor network requests
page.onRequest(request -> {
    System.out.println("Request: " + request.url());
});

page.onResponse(response -> {
    System.out.println("Response: " + response.status());
});
```

## 🎨 Best Practices

### 1. **Page Object Design**
```java
// Good - Focused page objects
public class LoginPage extends BasePage {
    // Only login-related elements and methods
}

public class InventoryPage extends BasePage {
    // Only inventory-related elements and methods
}

// Avoid - Monolithic page objects
public class WebPage extends BasePage {
    // All elements from all pages - harder to maintain
}
```

### 2. **Element Locators**
```java
// Preferred - Stable locators
@FindBy(testId = "login-button")
@FindBy(role = "button")
@FindBy(text = "Sign In")

// Avoid - Fragile locators
@FindBy(xpath = "//div[3]/form/button[1]")
@FindBy(css = "div > button:nth-child(2)")
```

### 3. **Wait Strategies**
```java
// Good - Specific waits
element.waitForVisible(10);
waitForNetworkIdle();

// Avoid - Generic delays
Thread.sleep(5000);
waitFor(5);
```

### 4. **Error Handling**
```java
public void safeClick(PlaywrightElement element) {
    try {
        element.waitForVisible(10);
        element.click();
        addTestLog("Click", "Element clicked successfully", Status.PASS);
    } catch (Exception e) {
        takeScreenshot("click_error.png");
        addTestLog("Click", "Failed to click: " + e.getMessage(), Status.FAIL);
        throw e;
    }
}
```

## 🔄 Migration from Selenium

### Step-by-Step Migration
1. **Create Playwright versions** of existing page objects
2. **Update step definitions** to use Playwright methods
3. **Create new feature files** with Playwright-specific tags
4. **Run tests in parallel** during transition period
5. **Gradually phase out** Selenium tests

### Comparison Table
| Feature | Selenium | Playwright |
|---------|----------|------------|
| Auto-wait | Manual | Built-in |
| Speed | Moderate | Fast |
| Browser support | Wide | Modern browsers |
| Network control | Limited | Full control |
| Mobile testing | Appium needed | Built-in emulation |
| Debugging | Basic | Advanced tracing |

## 🚀 Advanced Features

### 1. **Performance Testing**
```gherkin
Scenario: Performance validation
  When User measures page load time using playwright
  Then verify page loads within 3 seconds using playwright
```

### 2. **Visual Testing**
```gherkin
Scenario: Visual regression testing
  Then take visual baseline screenshot for login page using playwright
  And compare visual changes using playwright
```

### 3. **Accessibility Testing**
```gherkin
Scenario: Accessibility validation
  Then verify playwright login page accessibility
  And verify all interactive elements are keyboard accessible using playwright
```

This comprehensive Playwright integration provides a modern, reliable, and feature-rich alternative to Selenium testing while maintaining full compatibility with the existing QualiFrameV2 framework structure.