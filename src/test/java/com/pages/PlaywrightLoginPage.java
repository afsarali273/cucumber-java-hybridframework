package com.pages;

import com.framework.components.ScriptHelper;
import com.framework.playwright.BasePage;
import com.framework.playwright.PlaywrightElement;
import com.framework.playwright.annotations.FindBy;
import com.framework.report.Status;
import com.microsoft.playwright.Page;

/**
 * Dedicated Playwright Login Page Object
 */
public class PlaywrightLoginPage extends BasePage {
    
    @FindBy(css = ".login_logo")
    private PlaywrightElement loginLogo;
    
    @FindBy(id = "user-name")
    private PlaywrightElement usernameField;
    
    @FindBy(id = "password")
    private PlaywrightElement passwordField;
    
    @FindBy(id = "login-button")
    private PlaywrightElement loginButton;
    
    @FindBy(css = "[data-test='error']")
    private PlaywrightElement errorMessage;
    
    @FindBy(css = ".error-message-container")
    private PlaywrightElement errorContainer;
    
    // Constructors
    public PlaywrightLoginPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    public PlaywrightLoginPage(Page page) {
        super(page);
    }
    
    @Override
    public void navigateToPage() {
        String loginUrl = properties.getProperty("qaAppUrl");
        navigateTo(loginUrl);
        waitForPageLoad();
    }
    
    @Override
    public boolean isPageLoaded() {
        try {
            loginLogo.assertVisible();
            usernameField.assertVisible();
            passwordField.assertVisible();
            loginButton.assertVisible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Login actions
    public void enterUsername(String username) {
        usernameField.assertVisible();
        usernameField.assertEnabled();
        usernameField.clear();
        usernameField.fill(username);
        usernameField.assertValueEquals(username);
        addTestLog("Enter Username", "Username entered: " + username, Status.PASS);
    }
    
    public void enterPassword(String password) {
        passwordField.assertVisible();
        passwordField.assertEnabled();
        passwordField.clear();
        passwordField.fill(password);
        addTestLog("Enter Password", "Password entered", Status.PASS);
    }
    
    public void clickLoginButton() {
        loginButton.assertVisible();
        loginButton.assertEnabled();
        loginButton.click();
        addTestLog("Click Login", "Login button clicked", Status.PASS);
    }
    
    public PlaywrightInventoryPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        
        // Wait for navigation and return inventory page
        waitFor(2);
        return new PlaywrightInventoryPage(page);
    }
    
    public void enterCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
    }
    
    public void loginWithInvalidCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitFor(1); // Wait for error message
    }
    
    // Validation methods
    public boolean isLoginLogoVisible() {
        try {
            loginLogo.assertVisible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isUsernameFieldVisible() {
        return usernameField.isVisible();
    }
    
    public boolean isPasswordFieldVisible() {
        return passwordField.isVisible();
    }
    
    public boolean isLoginButtonVisible() {
        return loginButton.isVisible();
    }
    
    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            errorMessage.assertVisible();
            return true;
        } catch (Exception e) {
            try {
                errorContainer.assertVisible();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public String getErrorMessage() {
        if (errorMessage.isVisible()) {
            return errorMessage.getText();
        } else if (errorContainer.isVisible()) {
            return errorContainer.getText();
        }
        return "";
    }
    
    // Form operations
    public void clearLoginForm() {
        usernameField.clear();
        passwordField.clear();
        addTestLog("Clear Form", "Login form cleared", Status.PASS);
    }
    
    public String getUsernameValue() {
        return usernameField.getValue();
    }
    
    public String getPasswordValue() {
        return passwordField.getValue();
    }
    
    // Screenshot methods
    public void takeLoginPageScreenshot() {
        takeScreenshot("playwright_login_page.png");
        addTestLog("Screenshot", "Login page screenshot taken", Status.PASS);
    }
    
    public void takeErrorScreenshot() {
        takeScreenshot("playwright_login_error.png");
        addTestLog("Screenshot", "Login error screenshot taken", Status.PASS);
    }
    
    // Wait methods
    public void waitForLoginPageLoad() {
        loginLogo.assertVisible();
        usernameField.assertVisible();
        passwordField.assertVisible();
        loginButton.assertVisible();
    }
    
    public void waitForErrorMessage(int timeoutSeconds) {
        try {
            errorMessage.waitForVisible(timeoutSeconds);
        } catch (Exception e) {
            // Error message might not appear for valid logins
        }
    }
}