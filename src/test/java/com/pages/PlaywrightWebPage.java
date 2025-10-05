package com.pages;

import com.framework.components.ScriptHelper;
import com.framework.playwright.BasePage;
import com.framework.playwright.PlaywrightElement;
import com.framework.playwright.annotations.FindBy;
import com.framework.report.Status;
import com.microsoft.playwright.Page;

/**
 * Playwright version of WebPage for Sauce Demo Application
 */
public class PlaywrightWebPage extends BasePage {
    
    // Login page elements
    @FindBy(css = ".login_logo")
    private PlaywrightElement loginHeader;
    
    @FindBy(id = "user-name")
    private PlaywrightElement txtUserName;
    
    @FindBy(id = "password")
    private PlaywrightElement txtPassword;
    
    @FindBy(id = "login-button")
    private PlaywrightElement btnLogin;
    
    @FindBy(css = "[data-test='error']")
    private PlaywrightElement errorMessage;
    
    // Home page elements
    @FindBy(css = ".app_logo")
    private PlaywrightElement lblHeader;
    
    @FindBy(css = ".title")
    private PlaywrightElement pageTitle;
    
    @FindBy(css = ".inventory_list")
    private PlaywrightElement inventoryContainer;
    
    // Cookie and popup elements
    @FindBy(id = "ez-accept-all")
    private PlaywrightElement btnAcceptCookies;
    
    @FindBy(xpath = "//span[@class='cookie-choices-buttons']/a[text()='Ok']")
    private PlaywrightElement btnOk;
    
    // Form elements (for practice form)
    @FindBy(css = "input[name='firstname']")
    private PlaywrightElement txtFirstName;
    
    @FindBy(css = "input[name='lastname']")
    private PlaywrightElement txtLastName;
    
    @FindBy(id = "sex-0")
    private PlaywrightElement btnGender;
    
    @FindBy(id = "exp-6")
    private PlaywrightElement btnExp;
    
    @FindBy(id = "profession-0")
    private PlaywrightElement btnProf;
    
    @FindBy(id = "tool-1")
    private PlaywrightElement btnToolProtractor;
    
    @FindBy(id = "tool-2")
    private PlaywrightElement btnToolSelenium;
    
    @FindBy(id = "continents")
    private PlaywrightElement selectContinents;
    
    @FindBy(id = "submit")
    private PlaywrightElement btnSubmit;
    
    // Constructors
    public PlaywrightWebPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    public PlaywrightWebPage(Page page) {
        super(page);
    }
    
    @Override
    public void navigateToPage() {
        String appUrl = properties.getProperty("qaAppUrl");
        navigateTo(appUrl);
        waitForPageLoad();
    }
    
    @Override
    public boolean isPageLoaded() {
        return loginHeader.isVisible() || lblHeader.isVisible();
    }
    
    // Application launch
    public void launchApp() {
        navigateToPage();
        addTestLog("Launch App", "Application launched successfully", Status.PASS);
    }
    
    // Login page methods
    public void verifyLogin() {
        try {
            loginHeader.assertVisible();
            addTestLog("Verify Login Page", "Login page displayed successfully", Status.PASS);
        } catch (Exception e) {
            addTestLog("Verify Login Page", "Login page not displayed: " + e.getMessage(), Status.FAIL);
        }
    }
    
    public void enterUserName(String textValue) {
        txtUserName.assertVisible();
        txtUserName.assertEnabled();
        txtUserName.fill(textValue);
        txtUserName.assertValueEquals(textValue);
        addTestLog("Enter Username", "Username entered: " + textValue, Status.PASS);
    }
    
    public void enterPassword(String textValue) {
        txtPassword.assertVisible();
        txtPassword.assertEnabled();
        txtPassword.fill(textValue);
        addTestLog("Enter Password", "Password entered", Status.PASS);
    }
    
    public void clickLoginButton() {
        btnLogin.assertVisible();
        btnLogin.assertEnabled();
        btnLogin.click();
        addTestLog("Click Login", "Login button clicked", Status.PASS);
    }
    
    public void clickAcceptCookies() {
        if (btnAcceptCookies.isVisible()) {
            btnAcceptCookies.click();
            addTestLog("Accept Cookies", "Cookies accepted", Status.PASS);
        }
    }
    
    // Home page methods
    public void verifyHome() {
        try {
            lblHeader.assertVisible();
            inventoryContainer.assertVisible();
            addTestLog("Verify Home Page", "Home page displayed successfully", Status.PASS);
        } catch (Exception e) {
            addTestLog("Verify Home Page", "Home page not displayed: " + e.getMessage(), Status.FAIL);
        }
    }
    
    public void clickOkButton() {
        if (btnOk.isVisible()) {
            btnOk.click();
            addTestLog("Click OK", "OK button clicked", Status.PASS);
        }
    }
    
    // Form methods
    public void enterFirstName(String firstname) {
        txtFirstName.assertVisible();
        txtFirstName.assertEnabled();
        txtFirstName.fill(firstname);
        txtFirstName.assertValueEquals(firstname);
        addTestLog("Enter First Name", "First name entered: " + firstname, Status.PASS);
    }
    
    public void enterLastName(String lastname) {
        txtLastName.assertVisible();
        txtLastName.assertEnabled();
        txtLastName.fill(lastname);
        txtLastName.assertValueEquals(lastname);
        addTestLog("Enter Last Name", "Last name entered: " + lastname, Status.PASS);
    }
    
    public void clickGender() {
        btnGender.assertVisible();
        btnGender.assertEnabled();
        btnGender.click();
        btnGender.assertChecked();
        addTestLog("Select Gender", "Gender selected", Status.PASS);
    }
    
    public void clickExperience() {
        if (btnExp.isVisible()) {
            btnExp.click();
            addTestLog("Select Experience", "Experience selected", Status.PASS);
        }
    }
    
    public void clickProfession() {
        if (btnProf.isVisible()) {
            btnProf.click();
            addTestLog("Select Profession", "Profession selected", Status.PASS);
        }
    }
    
    public void clickToolProtractor() {
        if (btnToolProtractor.isVisible()) {
            btnToolProtractor.click();
            addTestLog("Select Tool", "Protractor tool selected", Status.PASS);
        }
    }
    
    public void clickToolSelenium() {
        if (btnToolSelenium.isVisible()) {
            btnToolSelenium.click();
            addTestLog("Select Tool", "Selenium tool selected", Status.PASS);
        }
    }
    
    public void selectCountry(String value) {
        if (selectContinents.isVisible()) {
            selectContinents.selectByValue(value);
            addTestLog("Select Country", "Country selected: " + value, Status.PASS);
        }
    }
    
    public void submitDetails() {
        if (btnSubmit.isVisible()) {
            btnSubmit.click();
            addTestLog("Submit Details", "Form submitted successfully", Status.PASS);
        }
    }
    
    // Validation methods
    public boolean isLoginPageDisplayed() {
        return loginHeader.isVisible();
    }
    
    public boolean isHomePageDisplayed() {
        return lblHeader.isVisible() && inventoryContainer.isVisible();
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
    
    // Screenshot methods
    public void takeLoginPageScreenshot() {
        takeScreenshot("playwright_login_page.png");
    }
    
    public void takeHomePageScreenshot() {
        takeScreenshot("playwright_home_page.png");
    }
    
    public void takeFormScreenshot() {
        takeScreenshot("playwright_form_page.png");
    }
}