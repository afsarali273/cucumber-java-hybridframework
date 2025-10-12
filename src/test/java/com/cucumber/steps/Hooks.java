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
package com.cucumber.steps;

import java.io.IOException;

import com.framework.components.ApplitoolsOperations;
import com.framework.core.FrameworkCore;
import com.framework.cucumber.DriverManager;
import com.framework.cucumber.TestHarness;
import com.framework.selenium.CloudPlatformWebDriverFactory;
import com.framework.utils.ScreenshotManager;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cucumber Hooks class that manages test lifecycle events
 * Handles driver initialization, screenshot capture, and cleanup operations
 * 
 * Features:
 * - Unified driver management for Selenium, Playwright, Appium, and WinAppDriver
 * - Intelligent screenshot capture based on configuration
 * - Applitools visual testing integration
 * - SauceLabs integration support
 * - Comprehensive error handling and logging
 * 
 * @author Qualitest
 * @version 2.0
 */
public class Hooks {
    
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    
    // Framework components
    private final FrameworkCore frameworkCore;
    private final ScreenshotManager screenshotManager;
    private final ApplitoolsOperations applitoolsOperations;
    
    // Legacy components for backward compatibility
    private TestHarness testHarness;
    private static Integer screenshotCounter = 0;
    
    /**
     * Constructor initializes framework components
     */
    public Hooks() {
        this.frameworkCore = FrameworkCore.getInstance();
        this.screenshotManager = ScreenshotManager.getInstance();
        this.applitoolsOperations = new ApplitoolsOperations();
        
        logger.debug("Hooks initialized with framework components");
    }

    /**
     * Pre-test setup hook that initializes the framework and drivers
     * 
     * This method is executed before each scenario and performs:
     * 1. Framework core initialization
     * 2. Driver setup based on configuration (Selenium/Playwright/Appium/Windows)
     * 3. Applitools visual testing setup (if enabled and not SauceLabs)
     * 4. Test execution approach configuration
     * 
     * @param scenario The Cucumber scenario being executed
     */
    @Before(order = 1)
    public void initializeFramework(Scenario scenario) {
        logger.info("=== Starting test setup for scenario: {} ===", scenario.getName());
        
        try {
            // Initialize legacy test harness for backward compatibility
            testHarness = new TestHarness();
            
            // Get test parameters and set scenario
            var testParameters = DriverManager.getTestParameters();
            if (testParameters != null) {
                testParameters.setScenario(scenario);
                
                // Initialize framework with unified approach
                frameworkCore.initializeFramework(scenario, testParameters);
                
                // Setup Applitools if enabled and not running on SauceLabs
                setupApplitoolsIfEnabled(scenario, testParameters);
                
            } else {
                // Fallback to legacy initialization
                logger.warn("Test parameters not found, using legacy initialization");
                testHarness.invokeDriver(scenario);
            }
            
            logger.info("Framework initialization completed for scenario: {}", scenario.getName());
            
        } catch (Exception e) {
            logger.error("Failed to initialize framework for scenario: {}", scenario.getName(), e);
            throw new RuntimeException("Framework initialization failed", e);
        }
    }

    /**
     * Set execution approach to CUCUMBER in configuration
     * 
     * This ensures that the framework knows it's running in Cucumber mode
     * which affects reporting and other framework behaviors
     */
    @Before(order = 0)
    public void setExecutionApproach() {
        logger.debug("Setting execution approach to CUCUMBER");
        frameworkCore.getConfigManager().setProperty("ExecutionApproach", "CUCUMBER");
    }

    /**
     * Capture screenshot after each test step
     * 
     * This method handles screenshot capture for all supported driver types:
     * - Selenium WebDriver (Chrome, Firefox, Edge, etc.)
     * - Microsoft Playwright
     * - Appium (Mobile testing)
     * - WinAppDriver (Windows applications)
     * 
     * Screenshots are taken based on configuration settings:
     * - TakeScreenshotPassedStep: Capture on passed steps
     * - TakeScreenshotFailedStep: Capture on failed steps
     * 
     * @param scenario The Cucumber scenario being executed
     */
    @AfterStep(order = 1)
    public void captureScreenshot(Scenario scenario) {
        logger.debug("Processing screenshot capture for step in scenario: {}", scenario.getName());
        
        try {
            // Determine if step passed or failed
            boolean isStepPassed = !scenario.isFailed();
            
            // Use centralized screenshot manager for intelligent capture
            screenshotManager.takeScreenshot(scenario, isStepPassed);
            
        } catch (Exception e) {
            logger.error("Error during screenshot capture for scenario: {}", scenario.getName(), e);
            // Don't fail the test due to screenshot issues
        }
    }
	


    /**
     * Capture visual content using Applitools and SauceLabs visual testing
     * 
     * This method handles:
     * 1. Applitools visual checkpoints (if enabled)
     * 2. SauceLabs visual testing integration
     * 
     * @param scenario The Cucumber scenario being executed
     */
    @AfterStep(order = 2)
    public void captureVisualContent(Scenario scenario) {
        logger.debug("Processing visual content capture for scenario: {}", scenario.getName());
        
        try {
            // Increment screenshot counter for unique naming
            screenshotCounter++;
            
            // Capture Applitools visual checkpoint if enabled
            if (isApplitoolsAfterStepEnabled()) {
                String checkpointName = generateCheckpointName(scenario, screenshotCounter);
                applitoolsOperations.captureContent(checkpointName);
                logger.debug("Applitools checkpoint captured: {}", checkpointName);
            }
            
            // Capture SauceLabs visual screenshot
            String screenshotName = generateScreenshotName(scenario, screenshotCounter);
            CloudPlatformWebDriverFactory.captureSaucescreener(screenshotName);
            logger.debug("SauceLabs screenshot captured: {}", screenshotName);
            
        } catch (Exception e) {
            logger.error("Error during visual content capture for scenario: {}", scenario.getName(), e);
            // Don't fail the test due to visual capture issues
        }
    }


    /**
     * Post-test cleanup hook that closes drivers and cleans up resources
     * 
     * This method is executed after each scenario and performs:
     * 1. Applitools session closure
     * 2. Driver cleanup (all types: Selenium, Playwright, Appium, Windows)
     * 3. Framework resource cleanup
     * 4. Error logging and reporting
     * 
     * @param scenario The Cucumber scenario that was executed
     * @throws IOException If there are issues with file operations during cleanup
     */
    @After
    public void cleanupFramework(Scenario scenario) throws IOException {
        logger.info("=== Starting test cleanup for scenario: {} ===", scenario.getName());
        
        try {
            // Close Applitools session if it was opened
            closeApplitoolsSession();
            
            // Use framework core for unified cleanup
            frameworkCore.cleanupFramework(scenario);
            
            // Legacy cleanup for backward compatibility
            if (testHarness != null) {
                testHarness.closeRespectiveDriver(scenario);
            }
            
            // Reset screenshot counter for next test
            screenshotCounter = 0;
            
            logger.info("Test cleanup completed successfully for scenario: {}", scenario.getName());
            
        } catch (Exception e) {
            logger.error("Error during test cleanup for scenario: {}", scenario.getName(), e);
            // Log the error but don't fail the test due to cleanup issues
        }
    }
	
    // ========== PRIVATE HELPER METHODS ==========
    
    /**
     * Setup Applitools visual testing if enabled and conditions are met
     * 
     * @param scenario The Cucumber scenario
     * @param testParameters Test execution parameters
     */
    private void setupApplitoolsIfEnabled(Scenario scenario, 
                                         com.framework.selenium.SeleniumTestParameters testParameters) {
        try {
            // Skip Applitools setup for SauceLabs execution
            if ("SAUCELABS".equalsIgnoreCase(testParameters.getExecutionMode().toString())) {
                logger.debug("Skipping Applitools setup for SauceLabs execution");
                return;
            }
            
            // Check if Applitools is enabled
            if (frameworkCore.getConfigManager().getBooleanProperty("AppliTools", false)) {
                String testName = scenario.getName() + "_" + scenario.getLine();
                applitoolsOperations.invokeAppliTools(testName);
                applitoolsOperations.openEyes(DriverManager.getWebDriver());
                logger.info("Applitools visual testing initialized for: {}", testName);
            }
            
        } catch (Exception e) {
            logger.error("Failed to setup Applitools for scenario: {}", scenario.getName(), e);
            // Don't fail the test due to Applitools setup issues
        }
    }
    
    /**
     * Close Applitools session if it was opened
     */
    private void closeApplitoolsSession() {
        try {
            if (frameworkCore.getConfigManager().getBooleanProperty("AppliTools", false)) {
                applitoolsOperations.closeAppliTools();
                logger.debug("Applitools session closed");
            }
        } catch (Exception e) {
            logger.error("Error closing Applitools session", e);
        }
    }
    
    /**
     * Check if Applitools after-step capture is enabled
     * 
     * @return true if enabled, false otherwise
     */
    private boolean isApplitoolsAfterStepEnabled() {
        return frameworkCore.getConfigManager().getBooleanProperty("AfterStepAppliTools", false);
    }
    
    /**
     * Generate unique checkpoint name for Applitools
     * 
     * @param scenario The Cucumber scenario
     * @param counter Screenshot counter
     * @return Formatted checkpoint name
     */
    private String generateCheckpointName(Scenario scenario, Integer counter) {
        return scenario.getName() + "_" + scenario.getLine() + "_screen_" + counter;
    }
    
    /**
     * Generate unique screenshot name for SauceLabs
     * 
     * @param scenario The Cucumber scenario
     * @param counter Screenshot counter
     * @return Formatted screenshot name
     */
    private String generateScreenshotName(Scenario scenario, Integer counter) {
        return scenario.getLine() + "_screen_" + counter;
    }
}
