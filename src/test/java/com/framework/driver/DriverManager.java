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
package com.framework.driver;

import com.framework.config.ConfigurationManager;
import com.framework.selenium.SeleniumTestParameters;
import com.framework.playwright.PlaywrightDriverFactory;
import com.microsoft.playwright.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * Enhanced driver manager that handles all driver types in a unified way
 * Supports Selenium WebDriver, Playwright, Appium, and WinAppDriver
 * 
 * @author Qualitest
 * @version 2.0
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static DriverManager instance;
    
    private final ConfigurationManager configManager;
    
    // Thread-local storage for different driver types
    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static final ThreadLocal<WindowsDriver> windowsDriver = new ThreadLocal<>();
    private static final ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();
    private static final ThreadLocal<SeleniumTestParameters> testParameters = new ThreadLocal<>();
    
    // Playwright objects
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();
    
    private DriverManager() {
        this.configManager = ConfigurationManager.getInstance();
        logger.info("Driver Manager initialized");
    }
    
    /**
     * Get singleton instance of DriverManager
     * 
     * @return DriverManager instance
     */
    public static synchronized DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }
    
    /**
     * Initialize driver based on driver type
     * 
     * @param driverType Type of driver to initialize
     * @param testParams Test parameters
     */
    public void initializeDriver(DriverType driverType, SeleniumTestParameters testParams) {
        logger.info("Initializing driver of type: {}", driverType);
        
        setTestParameters(testParams);
        
        switch (driverType) {
            case WEB:
                initializeWebDriver(testParams);
                break;
            case PLAYWRIGHT:
                initializePlaywrightDriver(testParams);
                break;
            case MOBILE:
                initializeMobileDriver(testParams);
                break;
            case WINDOWS:
                initializeWindowsDriver(testParams);
                break;
            case API:
                // No driver initialization needed for API tests
                logger.info("API execution - no driver initialization required");
                break;
            default:
                throw new IllegalArgumentException("Unsupported driver type: " + driverType);
        }
    }
    
    /**
     * Initialize Selenium WebDriver
     * 
     * @param testParams Test parameters
     */
    private void initializeWebDriver(SeleniumTestParameters testParams) {
        try {
            WebDriver driver = com.framework.cucumber.DriverManager.createWebDriverInstance(testParams, 
                testParams.getScenario().getName());
            setWebDriver(driver);
            logger.info("Web driver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize web driver", e);
            throw new RuntimeException("Web driver initialization failed", e);
        }
    }
    
    /**
     * Initialize Playwright driver
     * 
     * @param testParams Test parameters
     */
    private void initializePlaywrightDriver(SeleniumTestParameters testParams) {
        try {
            if (playwright.get() == null) {
                Playwright playwrightInstance = Playwright.create();
                setPlaywright(playwrightInstance);
                
                Browser browserInstance = PlaywrightDriverFactory.createBrowserInstance(testParams);
                setBrowser(browserInstance);
                
                BrowserContext contextInstance = PlaywrightDriverFactory.createBrowserContext(browserInstance);
                setBrowserContext(contextInstance);
                
                Page pageInstance = PlaywrightDriverFactory.createPage(contextInstance);
                setPage(pageInstance);
                
                logger.info("Playwright driver initialized successfully");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize Playwright driver", e);
            throw new RuntimeException("Playwright driver initialization failed", e);
        }
    }
    
    /**
     * Initialize Appium mobile driver
     * 
     * @param testParams Test parameters
     */
    private void initializeMobileDriver(SeleniumTestParameters testParams) {
        try {
            AppiumDriver driver = com.framework.cucumber.DriverManager.createAppiumInstance(testParams);
            setAppiumDriver(driver);
            logger.info("Mobile driver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize mobile driver", e);
            throw new RuntimeException("Mobile driver initialization failed", e);
        }
    }
    
    /**
     * Initialize Windows application driver
     * 
     * @param testParams Test parameters
     */
    private void initializeWindowsDriver(SeleniumTestParameters testParams) {
        try {
            WindowsDriver driver = com.framework.cucumber.DriverManager.createWindowsInstance(testParams, 
                testParams.getScenario().getName());
            setWindowsDriver(driver);
            logger.info("Windows driver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Windows driver", e);
            throw new RuntimeException("Windows driver initialization failed", e);
        }
    }
    
    /**
     * Close all active drivers and cleanup resources
     */
    public void closeAllDrivers() {
        logger.info("Closing all active drivers");
        
        try {
            // Close Playwright resources
            closePlaywrightResources();
            
            // Close Selenium WebDriver
            if (webDriver.get() != null) {
                webDriver.get().quit();
                webDriver.remove();
                logger.debug("Web driver closed");
            }
            
            // Close Appium driver
            if (appiumDriver.get() != null) {
                appiumDriver.get().quit();
                appiumDriver.remove();
                logger.debug("Mobile driver closed");
            }
            
            // Close Windows driver
            if (windowsDriver.get() != null) {
                windowsDriver.get().quit();
                windowsDriver.remove();
                logger.debug("Windows driver closed");
            }
            
            // Clear test parameters
            testParameters.remove();
            
            logger.info("All drivers closed successfully");
            
        } catch (Exception e) {
            logger.error("Error closing drivers", e);
        }
    }
    
    /**
     * Close Playwright resources
     */
    private void closePlaywrightResources() {
        try {
            if (page.get() != null && !page.get().isClosed()) {
                page.get().close();
            }
            if (context.get() != null) {
                context.get().close();
            }
            if (browser.get() != null) {
                browser.get().close();
            }
            if (playwright.get() != null) {
                playwright.get().close();
            }
            
            // Clear ThreadLocal variables
            page.remove();
            context.remove();
            browser.remove();
            playwright.remove();
            
            logger.debug("Playwright resources closed");
            
        } catch (Exception e) {
            logger.error("Error closing Playwright resources", e);
        }
    }
    
    // Getter and Setter methods for WebDriver
    public WebDriver getWebDriver() {
        return webDriver.get();
    }
    
    public void setWebDriver(WebDriver driver) {
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                configManager.getIntProperty("Timeout", 30)));
        }
        webDriver.set(driver);
    }
    
    // Getter and Setter methods for WindowsDriver
    public WindowsDriver getWindowsDriver() {
        return windowsDriver.get();
    }
    
    public void setWindowsDriver(WindowsDriver driver) {
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        }
        windowsDriver.set(driver);
    }
    
    // Getter and Setter methods for AppiumDriver
    public AppiumDriver getAppiumDriver() {
        return appiumDriver.get();
    }
    
    public void setAppiumDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }
    
    // Getter and Setter methods for TestParameters
    public SeleniumTestParameters getTestParameters() {
        return testParameters.get();
    }
    
    public void setTestParameters(SeleniumTestParameters testParams) {
        testParameters.set(testParams);
    }
    
    // Playwright getter and setter methods
    public Playwright getPlaywright() {
        return playwright.get();
    }
    
    public void setPlaywright(Playwright playwrightInstance) {
        playwright.set(playwrightInstance);
    }
    
    public Browser getBrowser() {
        return browser.get();
    }
    
    public void setBrowser(Browser browserInstance) {
        browser.set(browserInstance);
    }
    
    public BrowserContext getBrowserContext() {
        return context.get();
    }
    
    public void setBrowserContext(BrowserContext contextInstance) {
        context.set(contextInstance);
    }
    
    public Page getPage() {
        return page.get();
    }
    
    public void setPage(Page pageInstance) {
        page.set(pageInstance);
    }
}