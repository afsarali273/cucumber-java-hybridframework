package com.pages;

import com.framework.components.ScriptHelper;
import com.framework.playwright.BasePage;
import com.framework.playwright.PlaywrightElement;
import com.framework.playwright.annotations.FindBy;
import com.framework.report.Status;
import com.microsoft.playwright.Page;

/**
 * Sample Login Page using Playwright Page Object Model
 */
public class LoginPage extends BasePage {
    
    // Page elements using FindBy annotations
    @FindBy(id = "user-name")
    private PlaywrightElement usernameField;
    
    @FindBy(id = "password")
    private PlaywrightElement passwordField;
    
    @FindBy(id = "login-button")
    private PlaywrightElement loginButton;
    
    @FindBy(css = "[data-test='error']")
    private PlaywrightElement errorMessage;
    
    @FindBy(css = ".login_logo")
    private PlaywrightElement loginLogo;
    
    // Constructors
    public LoginPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    public LoginPage(Page page) {
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
        return loginLogo.isVisible() && usernameField.isVisible() && passwordField.isVisible();
    }
    
    // Page actions
    public void enterUsername(String username) {
        usernameField.waitForVisible(10);
        usernameField.fill(username);
        addTestLog("Enter Username", "Username entered: " + username, Status.PASS);
    }
    
    public void enterPassword(String password) {
        passwordField.waitForVisible(10);
        passwordField.fill(password);
        addTestLog("Enter Password", "Password entered", Status.PASS);
    }
    
    public void clickLoginButton() {
        loginButton.click();
        addTestLog("Click Login", "Login button clicked", Status.PASS);
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    public boolean isErrorMessageDisplayed() {
        return errorMessage.isVisible();
    }
    
    public String getErrorMessage() {
        if (isErrorMessageDisplayed()) {
            return errorMessage.getText();
        }
        return "";
    }
    
    public void clearLoginForm() {
        usernameField.clear();
        passwordField.clear();
        addTestLog("Clear Form", "Login form cleared", Status.PASS);
    }
    
    // Validation methods
    public boolean isUsernameFieldEnabled() {
        return usernameField.isEnabled();
    }
    
    public boolean isPasswordFieldEnabled() {
        return passwordField.isEnabled();
    }
    
    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }
}