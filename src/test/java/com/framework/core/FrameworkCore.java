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
package com.framework.core;

import com.framework.config.ConfigurationManager;
import com.framework.driver.DriverManager;
import com.framework.driver.DriverType;
import com.framework.selenium.SeleniumTestParameters;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Central framework core class that manages all framework operations
 * Provides unified access to configuration, driver management, and core functionality
 * 
 * @author Qualitest
 * @version 2.0
 */
public class FrameworkCore {
    
    private static final Logger logger = LogManager.getLogger(FrameworkCore.class);
    private static FrameworkCore instance;
    
    private final ConfigurationManager configManager;
    private final DriverManager driverManager;
    
    private FrameworkCore() {
        this.configManager = ConfigurationManager.getInstance();
        this.driverManager = DriverManager.getInstance();
        logger.info("Framework Core initialized successfully");
    }
    
    /**
     * Get singleton instance of FrameworkCore
     * 
     * @return FrameworkCore instance
     */
    public static synchronized FrameworkCore getInstance() {
        if (instance == null) {
            instance = new FrameworkCore();
        }
        return instance;
    }
    
    /**
     * Initialize framework for test execution
     * 
     * @param scenario Cucumber scenario
     * @param testParameters Test parameters
     */
    public void initializeFramework(Scenario scenario, SeleniumTestParameters testParameters) {
        logger.info("Initializing framework for scenario: {}", scenario.getName());
        
        try {
            // Set scenario in test parameters
            testParameters.setScenario(scenario);
            driverManager.setTestParameters(testParameters);
            
            // Determine driver type based on configuration
            DriverType driverType = determineDriverType();
            
            // Initialize appropriate driver
            driverManager.initializeDriver(driverType, testParameters);
            
            logger.info("Framework initialized successfully for driver type: {}", driverType);
            
        } catch (Exception e) {
            logger.error("Failed to initialize framework: {}", e.getMessage(), e);
            throw new RuntimeException("Framework initialization failed", e);
        }
    }
    
    /**
     * Cleanup framework resources
     * 
     * @param scenario Cucumber scenario
     */
    public void cleanupFramework(Scenario scenario) {
        logger.info("Cleaning up framework resources for scenario: {}", scenario.getName());
        
        try {
            driverManager.closeAllDrivers();
            logger.info("Framework cleanup completed successfully");
            
        } catch (Exception e) {
            logger.error("Error during framework cleanup: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Get configuration manager instance
     * 
     * @return ConfigurationManager instance
     */
    public ConfigurationManager getConfigManager() {
        return configManager;
    }
    
    /**
     * Get driver manager instance
     * 
     * @return DriverManager instance
     */
    public DriverManager getDriverManager() {
        return driverManager;
    }
    
    /**
     * Determine driver type based on configuration
     * 
     * @return DriverType enum value
     */
    private DriverType determineDriverType() {
        String automationFramework = configManager.getProperty("AutomationFramework", "SELENIUM");
        String testConfigId = configManager.getProperty("TestConfigurationID", "LOCALChrome");
        
        if ("PLAYWRIGHT".equalsIgnoreCase(automationFramework)) {
            return DriverType.PLAYWRIGHT;
        } else if (testConfigId.contains("Mobile") || testConfigId.contains("APPIUM")) {
            return DriverType.MOBILE;
        } else if (testConfigId.contains("WINDOWS")) {
            return DriverType.WINDOWS;
        } else if ("API".equalsIgnoreCase(testConfigId)) {
            return DriverType.API;
        } else {
            return DriverType.WEB;
        }
    }
    
    /**
     * Check if current execution is API-only
     * 
     * @return true if API execution, false otherwise
     */
    public boolean isApiExecution() {
        return determineDriverType() == DriverType.API;
    }
    
    /**
     * Check if current execution uses Playwright
     * 
     * @return true if Playwright execution, false otherwise
     */
    public boolean isPlaywrightExecution() {
        return determineDriverType() == DriverType.PLAYWRIGHT;
    }
    
    /**
     * Check if current execution is mobile
     * 
     * @return true if mobile execution, false otherwise
     */
    public boolean isMobileExecution() {
        return determineDriverType() == DriverType.MOBILE;
    }
    
    /**
     * Check if current execution is Windows app
     * 
     * @return true if Windows execution, false otherwise
     */
    public boolean isWindowsExecution() {
        return determineDriverType() == DriverType.WINDOWS;
    }
}