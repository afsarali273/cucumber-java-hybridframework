# Playwright Page Object Model Guide

## Overview
This guide demonstrates how to implement Page Object Model (POM) pattern with Playwright in the QualiFrameV2 framework, providing maintainable and reusable test automation code.

## 🏗️ Architecture Components

### 1. BasePage
Abstract base class that all page objects extend:
```java
public abstract class BasePage extends WebPWReusableComponents {
    protected Page page;
    
    public abstract void navigateToPage();
    public abstract boolean isPageLoaded();
}
```

### 2. PlaywrightElement
Enhanced wrapper for Playwright Locator with additional functionality:
```java
public class PlaywrightElement {
    public void click();
    public void fill(String text);
    public String getText();
    public boolean isVisible();
    // ... more methods
}
```

### 3. @FindBy Annotation
Annotation for defining element locators:
```java
@FindBy(id = "username")
private PlaywrightElement usernameField;

@FindBy(css = ".login-button")
private PlaywrightElement loginButton;
```

### 4. ElementInitializer
Automatically initializes annotated elements in page objects.

### 5. PageFactory
Factory class for creating and managing page object instances.

## 📝 Creating Page Objects

### Step 1: Define Page Class
```java
public class LoginPage extends BasePage {
    
    // Define elements using @FindBy annotations
    @FindBy(id = "user-name")
    private PlaywrightElement usernameField;
    
    @FindBy(id = "password")
    private PlaywrightElement passwordField;
    
    @FindBy(id = "login-button")
    private PlaywrightElement loginButton;
    
    // Constructors
    public LoginPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    public LoginPage(Page page) {
        super(page);
    }
}
```

### Step 2: Implement Abstract Methods
```java
@Override
public void navigateToPage() {
    String loginUrl = properties.getProperty("qaAppUrl");
    navigateTo(loginUrl);
    waitForPageLoad();
}

@Override
public boolean isPageLoaded() {
    return usernameField.isVisible() && 
           passwordField.isVisible() && 
           loginButton.isVisible();
}
```

### Step 3: Add Page Actions
```java
public void login(String username, String password) {
    enterUsername(username);
    enterPassword(password);
    clickLoginButton();
}

public void enterUsername(String username) {
    usernameField.waitForVisible(10);
    usernameField.fill(username);
    addTestLog("Enter Username", "Username entered: " + username, Status.PASS);
}

public void enterPassword(String password) {
    passwordField.fill(password);
    addTestLog("Enter Password", "Password entered", Status.PASS);
}

public void clickLoginButton() {
    loginButton.click();
    addTestLog("Click Login", "Login button clicked", Status.PASS);
}
```

## 🎯 Locator Strategies

### @FindBy Annotation Options
```java
// CSS Selector
@FindBy(css = ".login-form input[type='text']")
private PlaywrightElement usernameField;

// ID
@FindBy(id = "login-button")
private PlaywrightElement loginButton;

// XPath
@FindBy(xpath = "//button[contains(text(), 'Login')]")
private PlaywrightElement submitButton;

// Text Content
@FindBy(text = "Sign In")
private PlaywrightElement signInButton;

// Role
@FindBy(role = "button")
private PlaywrightElement roleButton;

// Test ID (recommended)
@FindBy(testId = "login-submit")
private PlaywrightElement submitButton;

// Placeholder
@FindBy(placeholder = "Enter username")
private PlaywrightElement usernameInput;

// Label
@FindBy(label = "Username")
private PlaywrightElement usernameField;
```

## 🔧 Using Page Objects in Tests

### Method 1: Using PageFactory
```java
public class LoginTest extends WebPWReusableComponents {
    
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    
    public LoginTest(ScriptHelper scriptHelper) {
        super(scriptHelper);
        initializePages();
    }
    
    private void initializePages() {
        loginPage = PageFactory.createPage(LoginPage.class, scriptHelper);
        inventoryPage = PageFactory.createPage(InventoryPage.class, scriptHelper);
    }
    
    public void testLogin() {
        loginPage.navigateToPage();
        loginPage.login("standard_user", "secret_sauce");
        
        if (inventoryPage.isPageLoaded()) {
            addTestLog("Login", "Login successful", Status.PASS);
        }
    }
}
```

### Method 2: Direct Instantiation
```java
public void testLogin() {
    LoginPage loginPage = new LoginPage(scriptHelper);
    loginPage.navigateToPage();
    loginPage.login("username", "password");
}
```

## 🚀 Advanced Features

### Working with Lists and Dynamic Elements
```java
public class InventoryPage extends BasePage {
    
    @FindBy(css = ".inventory_item")
    private PlaywrightElement inventoryItems;
    
    public void addProductToCart(int index) {
        PlaywrightElement product = inventoryItems.nth(index);
        PlaywrightElement addButton = new PlaywrightElement(
            product.getLocator().locator(".btn_inventory"), 
            "Add to cart button"
        );
        addButton.click();
    }
    
    public int getProductCount() {
        return inventoryItems.count();
    }
    
    public String getProductName(int index) {
        return inventoryItems.nth(index)
                .getLocator()
                .locator(".inventory_item_name")
                .textContent();
    }
}
```

### Handling Dropdowns
```java
@FindBy(css = ".product_sort_container")
private PlaywrightElement sortDropdown;

public void sortProducts(String sortOption) {
    sortDropdown.selectByValue(sortOption);
    waitFor(1); // Wait for sorting
}
```

### Taking Screenshots
```java
public void takePageScreenshot() {
    takeScreenshot("page_screenshot.png");
}

public void takeElementScreenshot() {
    inventoryContainer.screenshot("inventory_section.png");
}
```

### Working with Frames
```java
public void switchToFrame() {
    Frame frame = switchToFrame("#my-frame");
    // Work with frame elements
}
```

## 📋 Best Practices

### 1. Element Naming
```java
// Good - descriptive names
@FindBy(id = "login-submit")
private PlaywrightElement loginSubmitButton;

@FindBy(css = ".error-message")
private PlaywrightElement loginErrorMessage;

// Avoid - generic names
@FindBy(id = "btn1")
private PlaywrightElement button1;
```

### 2. Page Validation
```java
@Override
public boolean isPageLoaded() {
    return pageTitle.isVisible() && 
           pageTitle.getText().equals("Expected Title") &&
           mainContent.isVisible();
}
```

### 3. Waiting Strategies
```java
public void performAction() {
    element.waitForVisible(10);
    element.click();
    
    // Wait for result
    resultElement.waitForVisible(5);
}
```

### 4. Error Handling
```java
public void safeClick(PlaywrightElement element) {
    try {
        element.waitForVisible(10);
        element.click();
        addTestLog("Click", "Element clicked successfully", Status.PASS);
    } catch (Exception e) {
        takeScreenshot("click_error.png");
        addTestLog("Click", "Failed to click element: " + e.getMessage(), Status.FAIL);
    }
}
```

### 5. Data-Driven Testing
```java
public void testLoginWithMultipleUsers() {
    List<HashMap<String, String>> users = loadCsvData("users.csv");
    
    for (HashMap<String, String> user : users) {
        loginPage.navigateToPage();
        loginPage.login(user.get("username"), user.get("password"));
        
        boolean loginSuccess = inventoryPage.isPageLoaded();
        String expected = user.get("expected_result");
        
        if (expected.equals("success") && loginSuccess) {
            addTestLog("Login", "Login successful for: " + user.get("username"), Status.PASS);
        } else if (expected.equals("failure") && !loginSuccess) {
            addTestLog("Login", "Login correctly failed for: " + user.get("username"), Status.PASS);
        }
        
        if (loginSuccess) {
            inventoryPage.logout();
        }
    }
}
```

## 🔍 Debugging and Maintenance

### 1. Element Debugging
```java
public void debugElement(PlaywrightElement element) {
    System.out.println("Element visible: " + element.isVisible());
    System.out.println("Element enabled: " + element.isEnabled());
    System.out.println("Element text: " + element.getText());
    
    // Highlight element
    element.getLocator().highlight();
}
```

### 2. Page State Debugging
```java
public void debugPageState() {
    System.out.println("Page URL: " + getCurrentPageUrl());
    System.out.println("Page Title: " + getPageTitle());
    System.out.println("Page Loaded: " + isPageLoaded());
    
    takeScreenshot("debug_page_state.png");
}
```

### 3. Maintenance Tips
- **Use data-testid attributes** for stable selectors
- **Keep page methods focused** on single actions
- **Use meaningful method names** that describe the action
- **Add proper logging** for debugging
- **Handle dynamic content** with proper waits
- **Organize pages** in logical packages

## 📊 Complete Example

### LoginPage.java
```java
public class LoginPage extends BasePage {
    @FindBy(id = "user-name")
    private PlaywrightElement usernameField;
    
    @FindBy(id = "password")
    private PlaywrightElement passwordField;
    
    @FindBy(id = "login-button")
    private PlaywrightElement loginButton;
    
    public LoginPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    public void login(String username, String password) {
        usernameField.fill(username);
        passwordField.fill(password);
        loginButton.click();
    }
    
    @Override
    public boolean isPageLoaded() {
        return usernameField.isVisible() && loginButton.isVisible();
    }
}
```

### Test Class
```java
public class LoginTest extends WebPWReusableComponents {
    private LoginPage loginPage;
    
    public LoginTest(ScriptHelper scriptHelper) {
        super(scriptHelper);
        loginPage = PageFactory.createPage(LoginPage.class, scriptHelper);
    }
    
    public void testValidLogin() {
        loginPage.navigateToPage();
        loginPage.login("standard_user", "secret_sauce");
        // Add assertions
    }
}
```

This Page Object Model implementation provides a robust, maintainable, and scalable approach to Playwright test automation within the QualiFrameV2 framework.