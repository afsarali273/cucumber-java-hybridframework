/*
 *  © [2022] Qualitest. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.utils;

import com.framework.config.ConfigurationManager;
import com.framework.driver.DriverManager;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Centralized screenshot management for all driver types
 * Handles screenshots for Selenium WebDriver, Playwright, Appium, and WinAppDriver
 * 
 * @author Qualitest
 * @version 2.0
 */
public class ScreenshotManager {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotManager.class);
    private static ScreenshotManager instance;
    
    private final ConfigurationManager configManager;
    private final DriverManager driverManager;
    
    private ScreenshotManager() {
        this.configManager = ConfigurationManager.getInstance();
        this.driverManager = DriverManager.getInstance();
    }
    
    /**
     * Get singleton instance of ScreenshotManager
     * 
     * @return ScreenshotManager instance
     */
    public static synchronized ScreenshotManager getInstance() {
        if (instance == null) {
            instance = new ScreenshotManager();
        }
        return instance;
    }
    
    /**
     * Take screenshot based on current driver type and scenario status
     * 
     * @param scenario Cucumber scenario
     * @param isStepPassed Whether the step passed or failed
     */
    public void takeScreenshot(Scenario scenario, boolean isStepPassed) {
        // Skip screenshot for API tests
        if (isApiExecution()) {
            logger.debug("Skipping screenshot for API execution");
            return;
        }
        
        // Skip screenshot for SauceLabs execution
        if (isSauceLabsExecution()) {
            logger.debug("Skipping screenshot for SauceLabs execution");
            return;
        }
        
        // Check if screenshot should be taken based on configuration
        if (!shouldTakeScreenshot(isStepPassed)) {
            logger.debug("Screenshot not required for step status: {}", isStepPassed ? "PASSED" : "FAILED");
            return;
        }
        
        try {
            byte[] screenshot = captureScreenshot();
            if (screenshot != null) {
                scenario.attach(screenshot, "image/png", "screenshot");
                logger.debug("Screenshot attached to scenario: {}", scenario.getName());
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot for scenario: {}", scenario.getName(), e);
        }
    }
    
    /**
     * Capture screenshot based on current driver type
     * 
     * @return Screenshot as byte array
     */
    private byte[] captureScreenshot() {
        try {
            // Try Playwright first if available
            if (driverManager.getPage() != null) {
                return capturePlaywrightScreenshot();
            }
            
            // Try Selenium WebDriver
            if (driverManager.getWebDriver() != null) {
                return captureSeleniumScreenshot();
            }
            
            // Try Appium driver
            if (driverManager.getAppiumDriver() != null) {
                return captureAppiumScreenshot();
            }
            
            // Try Windows driver
            if (driverManager.getWindowsDriver() != null) {
                return captureWindowsScreenshot();
            }
            
            logger.warn("No active driver found for screenshot capture");
            return null;
            
        } catch (Exception e) {
            logger.error("Error capturing screenshot", e);
            return null;
        }
    }
    
    /**
     * Capture screenshot using Playwright
     * 
     * @return Screenshot as byte array
     */
    private byte[] capturePlaywrightScreenshot() {
        try {
            Page page = driverManager.getPage();
            if (page != null && !page.isClosed()) {
                // Wait for page to be ready
                page.waitForLoadState(LoadState.NETWORKIDLE);
                return page.screenshot();
            }
        } catch (Exception e) {
            logger.debug("Failed to capture Playwright screenshot, trying fallback", e);
            // Fallback to Selenium if available
            if (driverManager.getWebDriver() != null) {
                return captureSeleniumScreenshot();
            }
        }
        return null;
    }
    
    /**
     * Capture screenshot using Selenium WebDriver
     * 
     * @return Screenshot as byte array
     */
    private byte[] captureSeleniumScreenshot() {
        try {
            return ((TakesScreenshot) driverManager.getWebDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture Selenium screenshot", e);
            return null;
        }
    }
    
    /**
     * Capture screenshot using Appium driver
     * 
     * @return Screenshot as byte array
     */
    private byte[] captureAppiumScreenshot() {
        try {
            return ((TakesScreenshot) driverManager.getAppiumDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture Appium screenshot", e);
            return null;
        }
    }
    
    /**
     * Capture screenshot using Windows driver
     * 
     * @return Screenshot as byte array
     */
    private byte[] captureWindowsScreenshot() {
        try {
            return ((TakesScreenshot) driverManager.getWindowsDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture Windows screenshot", e);
            return null;
        }
    }
    
    /**
     * Check if screenshot should be taken based on step status and configuration
     * 
     * @param isStepPassed Whether the step passed
     * @return true if screenshot should be taken
     */
    private boolean shouldTakeScreenshot(boolean isStepPassed) {
        if (isStepPassed) {
            return configManager.shouldTakeScreenshotForPassedSteps();
        } else {
            return configManager.shouldTakeScreenshotForFailedSteps();
        }
    }
    
    /**
     * Check if current execution is API-only
     * 
     * @return true if API execution
     */
    private boolean isApiExecution() {
        return driverManager.getTestParameters() != null && 
               driverManager.getTestParameters().isAPIExecution();
    }
    
    /**
     * Check if current execution is SauceLabs
     * 
     * @return true if SauceLabs execution
     */
    private boolean isSauceLabsExecution() {
        return driverManager.getTestParameters() != null &&
               "SAUCELABS".equalsIgnoreCase(driverManager.getTestParameters().getExecutionMode().toString());
    }
}