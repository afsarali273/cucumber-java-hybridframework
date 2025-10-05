# Playwright Ultra-Simple Integration Guide

## Overview
This guide demonstrates the ultra-simple implementation of Playwright with Cucumber BDD in QualiFrameV2. The approach focuses on direct page object creation without complex framework overhead, making it easy to understand and maintain.

## 🏗️ Ultra-Simple Architecture

```
src/test/java/
├── com/cucumber/steps/
│   └── UltraSimplePlaywrightSteps.java  # Direct page object creation
├── com/pages/
│   ├── PlaywrightLoginPage.java         # Login page with simple methods
│   └── PlaywrightInventoryPage.java     # Inventory page with simple methods
└── com/framework/
    ├── reusable/WebPWReusableComponents.java  # Provides protected 'page' instance
    └── playwright/PlaywrightElement.java      # Element wrapper with assertions

src/test/resources/features/
└── PlaywrightWeb.feature                # Simple Playwright scenarios
```

## 🎯 Ultra-Simple Approach

### Key Principles:
1. **No Complex Framework Code** - Direct page object creation
2. **Browser Auto-Initialized** - Hooks handles all browser setup
3. **Protected Page Instance** - Available from WebPWReusableComponents
4. **Simple Constructor Pattern** - `new PlaywrightLoginPage(page)`

## 🚀 Ultra-Simple Step Definitions

### Step Definition Pattern:
```java
@When("User adds {string} to cart using playwright")
public void add_product_by_name_playwright(String productName) {
    PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
    inventoryPage.addProductToCartByName(productName);
}
```

### Key Features:
- **Direct Object Creation** - `new PlaywrightLoginPage(page)`
- **No Caching/Management** - Create when needed
- **Protected Page Available** - From WebPWReusableComponents
- **Simple Method Calls** - Direct page object methods

## 📋 Available Actions & Assertions

### 🔐 Login Actions
```java
// Step Definitions Available:
@When("User enters {string} and {string} using playwright")
@When("User logs in with {string} and {string} using playwright")
@Then("verify login result is {string} using playwright")
@Then("verify login page is displayed using playwright")

// Page Object Methods:
loginPage.enterCredentials(username, password);
loginPage.login(username, password);
loginPage.isPageLoaded();
loginPage.isErrorMessageDisplayed();
loginPage.getErrorMessage();
```

### 🛒 Cart Actions
```java
// Step Definitions Available:
@When("User adds first product to cart using playwright")
@When("User adds {string} to cart using playwright")
@When("User adds multiple products to cart using playwright:")
@Then("verify cart count is {int} using playwright")
@When("User goes to cart using playwright")
@Then("verify cart page is displayed using playwright")
@Then("verify all added products are in cart using playwright")

// Page Object Methods:
inventoryPage.addProductToCart(0);
inventoryPage.addProductToCartByName("Product Name");
inventoryPage.getCartItemCount();
inventoryPage.goToCart();
```

### 📄 Page Navigation & Validation
```java
// Step Definitions Available:
@Then("verify inventory page is displayed using playwright")
@When("User logs out using playwright")

// Page Object Methods:
inventoryPage.isPageLoaded();
inventoryPage.logout();
loginPage.navigateToPage();
```

### 🔍 Element Assertions (PlaywrightElement)
```java
// Visibility Assertions
element.assertVisible();
element.assertHidden();

// State Assertions
element.assertEnabled();
element.assertDisabled();
element.assertChecked();
element.assertUnchecked();

// Content Assertions
element.assertTextEquals("Expected Text");
element.assertTextContains("Partial Text");
element.assertValueEquals("Expected Value");

// Attribute Assertions
element.assertAttributeEquals("class", "expected-class");
element.assertHasClass("my-class");

// Count Assertions
element.assertCountEquals(5);
element.assertCountGreaterThan(3);

// Advanced Assertions
element.assertFocused();
element.assertEditable();
element.assertInViewport();
element.assertTextMatches("regex-pattern");
```

## 📝 Complete Step Definition Examples

### Login Flow
```java
@When("User enters {string} and {string} using playwright")
public void enter_credentials_playwright(String username, String password) {
    PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
    loginPage.enterCredentials(username, password);
}

@Then("verify login result is {string} using playwright")
public void verify_login_result_playwright(String expectedResult) {
    waitFor(2);
    if (expectedResult.equals("success")) {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        if (inventoryPage.isPageLoaded()) {
            addTestLog("Login", "Login successful", Status.PASS);
        } else {
            addTestLog("Login", "Login failed", Status.FAIL);
        }
    }
}
```

### Shopping Flow
```java
@When("User adds multiple products to cart using playwright:")
public void add_multiple_products_playwright(DataTable dataTable) {
    PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
    List<String> products = dataTable.asList();
    for (String product : products) {
        inventoryPage.addProductToCartByName(product);
        waitFor(1);
    }
}

@Then("verify cart count is {int} using playwright")
public void verify_cart_count_playwright(int expectedCount) {
    PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
    int actualCount = inventoryPage.getCartItemCount();
    if (actualCount == expectedCount) {
        addTestLog("Cart", "Cart count correct: " + actualCount, Status.PASS);
    } else {
        addTestLog("Cart", "Cart count wrong. Expected: " + expectedCount + ", Actual: " + actualCount, Status.FAIL);
    }
}
```

## 💡 Feature File Examples

### Simple Login Test
```gherkin
Scenario: Login with valid credentials
  Given Launch Playwright Application using "SeleniumPracticeTest4"
  When User enters "standard_user" and "secret_sauce" using playwright
  And User clicks playwright Login button
  Then verify login result is "success" using playwright
```

### Shopping Cart Test
```gherkin
Scenario: Add products to cart
  Given Launch Playwright Application using "SeleniumPracticeTest4"
  When User logs in with "standard_user" and "secret_sauce" using playwright
  When User adds multiple products to cart using playwright:
    | Sauce Labs Backpack    |
    | Sauce Labs Bike Light  |
    | Sauce Labs Bolt T-Shirt|
  Then verify cart count is 3 using playwright
  When User goes to cart using playwright
  Then verify cart page is displayed using playwright
  And verify all added products are in cart using playwright
```

### Error Handling Test
```gherkin
Scenario: Invalid login attempt
  Given Launch Playwright Application using "SeleniumPracticeTest4"
  When User enters "invalid_user" and "wrong_password" using playwright
  And User clicks playwright Login button
  Then verify login result is "error" using playwright
```

## ⚙️ Configuration Setup

### Properties Configuration
```properties
# Enable Playwright execution
AutomationFramework=PLAYWRIGHT

# Application URL
qaAppUrl=https://www.saucedemo.com/

# Execution settings
ExecutionApproach=CUCUMBER
TakeScreenshotPassedStep=True
TakeScreenshotFailedStep=True
```

### Browser Initialization
- **Automatic Setup**: Hooks class handles all browser initialization
- **No Manual Setup**: WebPWReusableComponents provides `page` instance
- **Clean Teardown**: Automatic browser cleanup after tests

## 🎯 Ultra-Simple Benefits

### 1. **No Complex Framework Code**
```java
// Ultra-Simple Approach
PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
loginPage.login(username, password);

// vs Complex Generic Approach
executePageAction(PlaywrightLoginPage.class, page -> {
    page.enterUsername(username);
    page.enterPassword(password);
});
```

### 2. **Direct Method Access**
```java
// Simple and readable
PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
inventoryPage.addProductToCart("Sauce Labs Backpack");
int cartCount = inventoryPage.getCartItemCount();
```

### 3. **Easy Debugging**
```java
// Direct object access for debugging
PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
System.out.println("Login page loaded: " + loginPage.isPageLoaded());
System.out.println("Error displayed: " + loginPage.isErrorMessageDisplayed());
```

## 🔧 How It Works

### 1. **Browser Initialization (Automatic)**
```java
// In Hooks.java - handles browser setup
public static void initializePlaywright() {
    if (playwright == null) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }
}
```

### 2. **Page Instance Available (Automatic)**
```java
// In WebPWReusableComponents constructor
if (page == null) {
    page = com.cucumber.steps.Hooks.getPlaywrightPageInstance();
}
```

### 3. **Step Definition Usage (Manual)**
```java
// In your step definitions - just create page objects
PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
```

## 🚦 Running Tests

### Prerequisites
1. Set `AutomationFramework=PLAYWRIGHT` in Global Settings.properties
2. Ensure test data is available in Excel files
3. Feature files should use ultra-simple step definitions

### Command Line Execution
```bash
# Run Playwright tests
mvn clean test -Dcucumber.filter.tags="@PlaywrightSauceDemoLoginTest"

# Run with BDD profile
mvn clean install -PrunBDDTests
```

### IDE Execution
1. Right-click on PlaywrightWeb.feature
2. Select "Run as TestNG Test"
3. Or run CucumberRunner.java as TestNG Test

## 📊 Test Reports

### Automatic Screenshot Capture
- Screenshots taken after each step
- Proper Playwright page screenshots (not blank)
- Attached to Cucumber reports
- Available in Results folder

### Report Locations
```
Results/
├── Run_[timestamp]/
│   ├── ExtentReport/
│   │   └── Index.html
│   ├── CucumberReport/
│   └── Screenshots/
└── target/
    └── allure-results/
```

## 🔍 Debugging & Troubleshooting

### Common Issues

#### 1. **Page Object Creation Fails**
```java
// Problem: page is null
PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page); // NullPointerException

// Solution: Ensure AutomationFramework=PLAYWRIGHT in properties
// Hooks will automatically initialize browser and page
```

#### 2. **Element Not Found**
```java
// Use built-in assertions for better error messages
element.assertVisible(); // Clear error if element not found
element.assertEnabled(); // Clear error if element disabled
```

#### 3. **Screenshots Are Blank**
```java
// Framework automatically handles Playwright screenshots
// No manual screenshot code needed in step definitions
```

### Debug Tips
```java
// Add debug logs in step definitions
addTestLog("Debug", "Current URL: " + getCurrentUrl(), Status.INFO);
addTestLog("Debug", "Page title: " + page.title(), Status.INFO);
```

## 🎨 Best Practices

### 1. **Step Definition Pattern**
```java
// Always create page objects directly in step definitions
@When("User performs some action using playwright")
public void perform_action_playwright() {
    PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
    loginPage.performAction();
}
```

### 2. **Page Object Methods**
```java
// Create wrapper methods for common operations
public void enterCredentials(String username, String password) {
    enterUsername(username);
    enterPassword(password);
}

public PlaywrightInventoryPage login(String username, String password) {
    enterCredentials(username, password);
    clickLoginButton();
    return new PlaywrightInventoryPage(page);
}
```

### 3. **Element Locators**
```java
// Use stable locators
@FindBy(id = "user-name")           // Best - ID
@FindBy(css = ".login-button")      // Good - CSS class
@FindBy(text = "Sign In")           // Good - Text content
@FindBy(xpath = "//div[3]/button")  // Avoid - Fragile XPath
```

### 4. **Error Handling**
```java
// Use assertions for validation
element.assertVisible();
element.assertTextEquals("Expected Text");

// Add meaningful test logs
addTestLog("Action", "Successfully performed action", Status.PASS);
addTestLog("Validation", "Element state verified", Status.PASS);
```

## 🚀 Getting Started

1. **Set Configuration**: `AutomationFramework=PLAYWRIGHT`
2. **Create Feature File**: Use ultra-simple step definitions
3. **Write Step Definitions**: Direct page object creation pattern
4. **Run Tests**: `mvn clean test` or IDE execution
5. **View Reports**: Check Results folder for screenshots and reports

That's it! No complex setup, no framework overhead - just simple, direct page object usage.