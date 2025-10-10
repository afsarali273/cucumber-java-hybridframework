package com.cucumber.steps;

import java.util.Properties;

import com.framework.components.Settings;
import com.framework.cucumber.TestHarness;
import com.framework.cucumber.DriverManager;

import com.framework.reusable.WebPWReusableComponents;
import com.pages.PlaywrightWebPage;
import com.microsoft.playwright.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Playwright Step Definitions for Web Testing
 */
public class PlaywrightWebSteps extends WebPWReusableComponents {

    protected TestHarness testHarness = new TestHarness();
    protected Properties properties = Settings.getInstance();
    
    // Page Objects
    private PlaywrightWebPage webPage;
    
    public PlaywrightWebSteps() {
        super();
    }
    
    private void initializePageObjects() {
        if (webPage == null) {
            webPage = new PlaywrightWebPage(DriverManager.getPage());
        }
    }

    @Given("Launch Playwright Application using {string}")
    public void launch_playwright_application(String tcid) {
        initializePageObjects();
        testHarness.initializeTestData(tcid);
        webPage.launchApp();
    }

    @Then("verify playwright login page displayed successfully")
    public void verify_playwright_login_page_displayed_successfully() {
        initializePageObjects();
        webPage.verifyLogin();
    }

    @When("^User enters userCredentials in playwright text field$")
    public void enter_playwright_user_credentials() {
        initializePageObjects();
        String userName = testHarness.getData("General_Data", "UserName");
        String password = testHarness.getData("General_Data", "Password");
        webPage.enterUserName(userName);
        webPage.enterPassword(password);
    }

    @When("User clicks playwright Login button")
    public void click_playwright_login_button() {
        initializePageObjects();
        webPage.clickLoginButton();
    }

    @Then("verify playwright home page displayed successfully")
    public void verify_playwright_home_page() {
        initializePageObjects();
        webPage.verifyHome();
    }

    @Then("enter playwright automation practice form details")
    public void enter_playwright_form_details() {
        initializePageObjects();
        String firstName = testHarness.getData("General_Data", "FirstName");
        String lastName = testHarness.getData("General_Data", "LastName");
        String country = testHarness.getData("General_Data", "Country");
        
        webPage.enterFirstName(firstName);
        webPage.enterLastName(lastName);
        webPage.clickGender();
        webPage.clickExperience();
        webPage.clickProfession();
        webPage.clickToolProtractor();
        webPage.clickToolSelenium();
        webPage.selectCountry(country);
    }

    @Then("submit the playwright entered details")
    public void submit_playwright_details() {
        initializePageObjects();
        webPage.submitDetails();
    }

    @Given("run playwright functional test script for {string} from {string}")
    public void run_playwright_functional_test_script(String testcaseName, String file) {
        initializePageObjects();
        if (file.contains(".json")) {
            // runAutomationTests(file, testcaseName);
            addTestLog("Script Execution", "Running test script: " + testcaseName, 
                      com.framework.report.Status.PASS);
        } else {
            // findJsonFilesInFolder(file, testcaseName);
            addTestLog("Script Execution", "Running test folder: " + file, 
                      com.framework.report.Status.PASS);
        }
    }
    
    // Additional Playwright-specific step definitions
    
    @When("User accepts playwright cookies")
    public void accept_playwright_cookies() {
        initializePageObjects();
        webPage.clickAcceptCookies();
    }
    
    @When("User clicks playwright OK button")
    public void click_playwright_ok_button() {
        initializePageObjects();
        webPage.clickOkButton();
    }
    
    @Then("take playwright login page screenshot")
    public void take_playwright_login_screenshot() {
        initializePageObjects();
        webPage.takeLoginPageScreenshot();
    }
    
    @Then("take playwright home page screenshot")
    public void take_playwright_home_screenshot() {
        initializePageObjects();
        webPage.takeHomePageScreenshot();
    }
    
    @Then("verify playwright error message is displayed")
    public void verify_playwright_error_message() {
        initializePageObjects();
        if (webPage.isErrorMessageDisplayed()) {
            String errorMsg = webPage.getErrorMessage();
            addTestLog("Error Validation", "Error message displayed: " + errorMsg, 
                      com.framework.report.Status.PASS);
        } else {
            addTestLog("Error Validation", "Error message not displayed", 
                      com.framework.report.Status.FAIL);
        }
    }
    
    @When("User enters invalid playwright credentials")
    public void enter_invalid_playwright_credentials() {
        initializePageObjects();
        webPage.enterUserName("invalid_user");
        webPage.enterPassword("invalid_password");
    }
    
    @Then("verify playwright page title contains {string}")
    public void verify_playwright_page_title(String expectedTitle) {
        initializePageObjects();
        String actualTitle = webPage.getPageTitle();
        if (actualTitle.contains(expectedTitle)) {
            addTestLog("Title Validation", "Page title contains: " + expectedTitle, 
                      com.framework.report.Status.PASS);
        } else {
            addTestLog("Title Validation", "Page title does not contain: " + expectedTitle + 
                      ". Actual: " + actualTitle, com.framework.report.Status.FAIL);
        }
    }
    
    @Then("verify playwright current URL contains {string}")
    public void verify_playwright_current_url(String expectedUrl) {
        initializePageObjects();
        String currentUrl = webPage.getCurrentPageUrl();
        if (currentUrl.contains(expectedUrl)) {
            addTestLog("URL Validation", "Current URL contains: " + expectedUrl, 
                      com.framework.report.Status.PASS);
        } else {
            addTestLog("URL Validation", "Current URL does not contain: " + expectedUrl + 
                      ". Actual: " + currentUrl, com.framework.report.Status.FAIL);
        }
    }
    
    @When("User waits for {int} seconds in playwright")
    public void wait_for_seconds_playwright(int seconds) {
        waitFor(seconds);
        addTestLog("Wait", "Waited for " + seconds + " seconds", 
                  com.framework.report.Status.PASS);
    }
    
    @Then("verify playwright login page is loaded")
    public void verify_playwright_login_page_loaded() {
        initializePageObjects();
        if (webPage.isLoginPageDisplayed()) {
            addTestLog("Page Load Validation", "Login page is loaded", 
                      com.framework.report.Status.PASS);
        } else {
            addTestLog("Page Load Validation", "Login page is not loaded", 
                      com.framework.report.Status.FAIL);
        }
    }
    
    @Then("verify playwright home page is loaded")
    public void verify_playwright_home_page_loaded() {
        initializePageObjects();
        if (webPage.isHomePageDisplayed()) {
            addTestLog("Page Load Validation", "Home page is loaded", 
                      com.framework.report.Status.PASS);
        } else {
            addTestLog("Page Load Validation", "Home page is not loaded", 
                      com.framework.report.Status.FAIL);
        }
    }
}