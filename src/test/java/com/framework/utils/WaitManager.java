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
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Centralized wait management for all driver types
 * Provides intelligent waiting strategies for Selenium, Playwright, and mobile drivers
 * 
 * @author Qualitest
 * @version 2.0
 */
public class WaitManager {
    
    private static final Logger logger = LogManager.getLogger(WaitManager.class);
    private static WaitManager instance;
    
    private final ConfigurationManager configManager;
    private final DriverManager driverManager;
    
    private WaitManager() {
        this.configManager = ConfigurationManager.getInstance();
        this.driverManager = DriverManager.getInstance();
    }
    
    /**
     * Get singleton instance of WaitManager
     * 
     * @return WaitManager instance
     */
    public static synchronized WaitManager getInstance() {
        if (instance == null) {
            instance = new WaitManager();
        }
        return instance;
    }
    
    /**
     * Wait for element to be visible using appropriate driver
     * 
     * @param locator Element locator (By for Selenium, String for Playwright)
     * @return true if element becomes visible, false otherwise
     */
    public boolean waitForElementVisible(Object locator) {
        return waitForElementVisible(locator, getDefaultTimeoutInSeconds());
    }
    
    /**
     * Wait for element to be visible with custom timeout
     * 
     * @param locator Element locator
     * @param timeoutInSeconds Custom timeout in seconds
     * @return true if element becomes visible, false otherwise
     */
    public boolean waitForElementVisible(Object locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be visible: {}", locator);
        
        try {
            if (driverManager.getPage() != null) {
                return waitForPlaywrightElementVisible((String) locator, timeoutInSeconds);
            } else if (driverManager.getWebDriver() != null) {
                return waitForSeleniumElementVisible((By) locator, timeoutInSeconds);
            } else if (driverManager.getAppiumDriver() != null) {
                return waitForMobileElementVisible((By) locator, timeoutInSeconds);
            } else {
                logger.warn("No active driver found for element wait");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error waiting for element visibility: {}", locator, e);
            return false;
        }
    }
    
    /**
     * Wait for element to be clickable
     * 
     * @param locator Element locator
     * @return true if element becomes clickable, false otherwise
     */
    public boolean waitForElementClickable(Object locator) {
        return waitForElementClickable(locator, getDefaultTimeoutInSeconds());
    }
    
    /**
     * Wait for element to be clickable with custom timeout
     * 
     * @param locator Element locator
     * @param timeoutInSeconds Custom timeout in seconds
     * @return true if element becomes clickable, false otherwise
     */
    public boolean waitForElementClickable(Object locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        
        try {
            if (driverManager.getPage() != null) {
                return waitForPlaywrightElementClickable((String) locator, timeoutInSeconds);
            } else if (driverManager.getWebDriver() != null) {
                return waitForSeleniumElementClickable((By) locator, timeoutInSeconds);
            } else if (driverManager.getAppiumDriver() != null) {
                return waitForMobileElementClickable((By) locator, timeoutInSeconds);
            } else {
                logger.warn("No active driver found for element wait");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error waiting for element clickability: {}", locator, e);
            return false;
        }
    }
    
    /**
     * Wait for page to load completely
     * 
     * @return true if page loads successfully, false otherwise
     */
    public boolean waitForPageLoad() {
        return waitForPageLoad(getDefaultTimeoutInSeconds());
    }
    
    /**
     * Wait for page to load completely with custom timeout
     * 
     * @param timeoutInSeconds Custom timeout in seconds
     * @return true if page loads successfully, false otherwise
     */
    public boolean waitForPageLoad(int timeoutInSeconds) {
        logger.debug("Waiting for page to load completely");
        
        try {
            if (driverManager.getPage() != null) {
                return waitForPlaywrightPageLoad(timeoutInSeconds);
            } else if (driverManager.getWebDriver() != null) {
                return waitForSeleniumPageLoad(timeoutInSeconds);
            } else {
                logger.warn("No active driver found for page load wait");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error waiting for page load", e);
            return false;
        }
    }
    
    /**
     * Wait for Selenium element to be visible
     * 
     * @param locator By locator
     * @param timeoutInSeconds Timeout in seconds
     * @return true if element becomes visible
     */
    private boolean waitForSeleniumElementVisible(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driverManager.getWebDriver(), Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element != null;
        } catch (Exception e) {
            logger.debug("Selenium element not visible within timeout: {}", locator);
            return false;
        }
    }
    
    /**
     * Wait for Selenium element to be clickable
     * 
     * @param locator By locator
     * @param timeoutInSeconds Timeout in seconds
     * @return true if element becomes clickable
     */
    private boolean waitForSeleniumElementClickable(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driverManager.getWebDriver(), Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            return element != null;
        } catch (Exception e) {
            logger.debug("Selenium element not clickable within timeout: {}", locator);
            return false;
        }
    }
    
    /**
     * Wait for Selenium page to load
     * 
     * @param timeoutInSeconds Timeout in seconds
     * @return true if page loads
     */
    private boolean waitForSeleniumPageLoad(int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driverManager.getWebDriver(), Duration.ofSeconds(timeoutInSeconds));
            return wait.until(webDriver -> 
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.debug("Selenium page not loaded within timeout");
            return false;
        }
    }
    
    /**
     * Wait for Playwright element to be visible
     * 
     * @param selector CSS selector
     * @param timeoutInSeconds Timeout in seconds
     * @return true if element becomes visible
     */
    private boolean waitForPlaywrightElementVisible(String selector, int timeoutInSeconds) {
        try {
            Page page = driverManager.getPage();
            page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutInSeconds * 1000));
            return true;
        } catch (Exception e) {
            logger.debug("Playwright element not visible within timeout: {}", selector);
            return false;
        }
    }
    
    /**
     * Wait for Playwright element to be clickable
     * 
     * @param selector CSS selector
     * @param timeoutInSeconds Timeout in seconds
     * @return true if element becomes clickable
     */
    private boolean waitForPlaywrightElementClickable(String selector, int timeoutInSeconds) {
        try {
            Page page = driverManager.getPage();
            Locator locator = page.locator(selector);
            locator.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutInSeconds * 1000));
            return true;
        } catch (Exception e) {
            logger.debug("Playwright element not clickable within timeout: {}", selector);
            return false;
        }
    }
    
    /**
     * Wait for Playwright page to load
     * 
     * @param timeoutInSeconds Timeout in seconds
     * @return true if page loads
     */
    private boolean waitForPlaywrightPageLoad(int timeoutInSeconds) {
        try {
            Page page = driverManager.getPage();
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE, 
                new Page.WaitForLoadStateOptions().setTimeout(timeoutInSeconds * 1000));
            return true;
        } catch (Exception e) {
            logger.debug("Playwright page not loaded within timeout");
            return false;
        }
    }
    
    /**
     * Wait for mobile element to be visible
     * 
     * @param locator By locator
     * @param timeoutInSeconds Timeout in seconds
     * @return true if element becomes visible
     */
    private boolean waitForMobileElementVisible(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driverManager.getAppiumDriver(), Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element != null;
        } catch (Exception e) {
            logger.debug("Mobile element not visible within timeout: {}", locator);
            return false;
        }
    }
    
    /**
     * Wait for mobile element to be clickable
     * 
     * @param locator By locator
     * @param timeoutInSeconds Timeout in seconds
     * @return true if element becomes clickable
     */
    private boolean waitForMobileElementClickable(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driverManager.getAppiumDriver(), Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            return element != null;
        } catch (Exception e) {
            logger.debug("Mobile element not clickable within timeout: {}", locator);
            return false;
        }
    }
    
    /**
     * Get default timeout from configuration
     * 
     * @return Default timeout in seconds
     */
    private int getDefaultTimeoutInSeconds() {
        return configManager.getIntProperty("Timeout", 30);
    }
    
    /**
     * Simple sleep utility
     * 
     * @param milliseconds Time to sleep in milliseconds
     */
    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Sleep interrupted", e);
        }
    }
}