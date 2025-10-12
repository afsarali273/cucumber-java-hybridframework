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
package com.framework.selenium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.framework.config.ConfigurationManager;
import com.framework.report.Status;
import com.framework.utils.WaitManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.SessionId;
import java.io.File;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.util.Arrays;

/**
 * Enhanced CustomDriver wrapper that provides additional functionality over standard WebDriver operations
 * 
 * Key Features:
 * - Intelligent waiting strategies with configurable timeouts
 * - Enhanced error handling and comprehensive logging
 * - Integration with framework reporting system
 * - Support for multiple driver types (Web, Mobile, Windows)
 * - Advanced element interaction methods
 * - Built-in screenshot and reporting capabilities
 * 
 * @author Qualitest
 * @version 2.0
 */
public class CustomDriver {

	private static final Logger logger = LogManager.getLogger(CustomDriver.class);
	
	private SeleniumTestParameters testParameters;
	private SeleniumReport report;
	private final WebDriver driver;
	private final WaitManager waitManager;
	private final ConfigurationManager configManager;
	private final Actions actions;

	/**
	 * Constructor for CustomDriver with enhanced framework integration
	 * 
	 * @param driver The WebDriver instance to wrap
	 */
	public CustomDriver(WebDriver driver) {
		this.driver = driver;
		this.waitManager = WaitManager.getInstance();
		this.configManager = ConfigurationManager.getInstance();
		this.actions = new Actions(driver);
		
		logger.info("CustomDriver initialized with driver: {}", driver.getClass().getSimpleName());
	}

	/**
	 * Function to return the object for SeleniumTestParameters
	 * {@link SeleniumTestParameters} object
	 * 
	 * @return Instance of the {@link SeleniumTestParameters} object
	 */
	public SeleniumTestParameters getTestParameters() {
		return testParameters;
	}

	/**
	 * Function to set the SeleniumTestParameters
	 * Object{@link SeleniumTestParameters} object
	 * 
	 * @param testParameters The Parameters
	 */
	public void setTestParameters(SeleniumTestParameters testParameters) {
		this.testParameters = testParameters;
	}

	/**
	 * Function to set the SeleniumReport Object{@link SeleniumReport} object
	 * 
	 * @param report The Report
	 */
	public void setReport(SeleniumReport report) {
		this.report = report;
	}

	/**
	 * Function to return the object for WebDriver {@link WebDriver} object
	 * 
	 * @return Instance of the {@link WebDriver} object
	 */
	public WebDriver getWebDriver() {
		return (WebDriver) driver;
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver} object
	 * 
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public AppiumDriver getAppiumDriver() {
		return (AppiumDriver) driver;
	}

	/**
	 * Function to return the object for RemoteWebDriver {@link RemoteWebDriver}
	 * object
	 * 
	 * @return Instance of the {@link RemoteWebDriver} object
	 */
	public RemoteWebDriver getRemoteWebDriver() {
		return (RemoteWebDriver) driver;
	}

	/**
	 * Enhanced method to check if current execution is Appium-based
	 * 
	 * @return true if Appium execution, false otherwise
	 */
	private boolean isAppium() {
		if (testParameters != null && testParameters.getMobileToolName() != null) {
			return "APPIUM".equals(testParameters.getMobileToolName().toString());
		}
		return false;
	}
	
	/**
	 * Check if current execution is mobile-based
	 * 
	 * @return true if mobile execution, false otherwise
	 */
	public boolean isMobileExecution() {
		return testParameters != null && testParameters.isMobileExecution();
	}
	
	/**
	 * Check if current execution is Windows app-based
	 * 
	 * @return true if Windows execution, false otherwise
	 */
	public boolean isWindowsExecution() {
		return testParameters != null && testParameters.isWindowsExecution();
	}

	// WebDriver Methods
	/**
	 * Function to close the driver Object {@link WebDriver}
	 */
	public void close() {
		driver.close();
	}

	/**
	 * Function to identity the Element
	 * 
	 * @param obj The locator used to identify the element {@link WebDriver}
	 * @return boolean Equals
	 */
	public boolean equals(Object obj) {
		return driver.equals(obj);
	}

	/**
	 * Function to Find the first {@link WebElement} using the given method.
	 * 
	 * @param arg0 The locator used to identify the element {@link WebDriver}
	 * @return the WebElement object
	 */
	public WebElement findElement(By arg0) {
		return driver.findElement(arg0);
	}

	/**
	 * Enhanced element finder with intelligent waiting and reporting
	 * 
	 * This method:
	 * 1. Waits for element visibility using WaitManager
	 * 2. Updates test report with action status
	 * 3. Returns element if found, null otherwise
	 * 
	 * @param locator The locator used to identify the element
	 * @return WebElement if found and visible, null otherwise
	 */
	public WebElement findElementWithWaitAndReport(By locator) {
		logger.debug("Finding element with wait and report: {}", locator);
		
		try {
			if (waitManager.waitForElementVisible(locator)) {
				WebElement element = driver.findElement(locator);
				
				if (report != null) {
					report.updateTestLog("Element Found", 
						"Element located successfully: " + locator.toString(), Status.PASS);
				}
				
				logger.debug("Element found successfully: {}", locator);
				return element;
			} else {
				if (report != null) {
					report.updateTestLog("Element Not Found", 
						"Element not visible within timeout: " + locator.toString(), Status.FAIL);
				}
				
				logger.warn("Element not found or not visible: {}", locator);
				return null;
			}
		} catch (Exception e) {
			logger.error("Error finding element: {}", locator, e);
			
			if (report != null) {
				report.updateTestLog("Element Error", 
					"Error occurred while finding element: " + e.getMessage(), Status.FAIL);
			}
			
			return null;
		}
	}
	
	/**
	 * Legacy method for backward compatibility
	 * @deprecated Use findElementWithWaitAndReport instead
	 */
	@Deprecated
	public WebElement findElementnTakescreenShot(By locator) {
		return findElementWithWaitAndReport(locator);
	}

	/**
	 * Enhanced element visibility check with configurable timeout
	 * 
	 * @param locator The locator used to identify the element
	 * @return true if element is visible, false otherwise
	 */
	public boolean isElementVisible(By locator) {
		return waitManager.waitForElementVisible(locator);
	}
	
	/**
	 * Check if element is visible with custom timeout
	 * 
	 * @param locator The locator used to identify the element
	 * @param timeoutInSeconds Custom timeout in seconds
	 * @return true if element is visible, false otherwise
	 */
	public boolean isElementVisible(By locator, int timeoutInSeconds) {
		return waitManager.waitForElementVisible(locator, timeoutInSeconds);
	}
	
	/**
	 * Check if element is clickable
	 * 
	 * @param locator The locator used to identify the element
	 * @return true if element is clickable, false otherwise
	 */
	public boolean isElementClickable(By locator) {
		return waitManager.waitForElementClickable(locator);
	}
	
	/**
	 * Check if element is present in DOM (may not be visible)
	 * 
	 * @param locator The locator used to identify the element
	 * @return true if element is present, false otherwise
	 */
	public boolean isElementPresent(By locator) {
		try {
			return !driver.findElements(locator).isEmpty();
		} catch (Exception e) {
			logger.debug("Element not present: {}", locator);
			return false;
		}
	}

	/**
	 * Function to Find all elements within the current page using the given
	 * mechanism
	 * 
	 * @param arg0 The locator used to identify the list of elements {@link WebDriver}
	 */
	public List<WebElement> findElements(By arg0) {
		return driver.findElements(arg0);
	}

	/**
	 * Function to Load a new web page in the current browser window.
	 * {@link WebDriver}
	 */
	public void get(String arg0) {
		driver.get(arg0);
	}

	public Class<?> getClass_Driver() {
		return driver.getClass();
	}

	/**
	 * Function to Get a string representing the current URL that the browser is
	 * looking at. {@link WebDriver}
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Function to Get the source of the last loaded page. {@link WebDriver}
	 */
	public String getPageSource() {
		return driver.getPageSource();
	}

	/**
	 * Function to get The title of the current page. {@link WebDriver}
	 */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * Function to Return an opaque handle to this window that uniquely identifies
	 * it within this driver instance {@link WebDriver}
	 */
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * Function to Return a set of window handles which can be used to iterate over
	 * all open windows of this WebDriver instance by passing them to
	 *  {@link WebDriver}
	 */
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public int hashCode() {
		return driver.hashCode();
	}

	/**
	 * Function to GetAn abstraction allowing the driver to access the browser's
	 * history and to navigate to a given URL. {@link WebDriver}
	 */
	public WebDriver.Navigation navigate() {
		return driver.navigate();
	}

	public void notify_Driver() {
		driver.notify();
	}

	public void notifyAll_Driver() {
		driver.notifyAll();
	}

	/**
	 * Function to Quit this driver, closing every associated window..
	 * {@link WebDriver}
	 */
	public void quit() {
		driver.quit();
	}

	/**
	 * Function to Send future commands to a different frame or window.
	 * {@link WebDriver}
	 */
	public void switchTo() {
		driver.switchTo();
	}

	public String toString() {
		return driver.toString();
	}

	public void wait_Driver() throws InterruptedException {
		driver.wait();
	}

	public void wait_Driver(long timeout) throws InterruptedException {
		driver.wait(timeout);
	}

	public void wait_Driver(long timeout, int nanos) throws InterruptedException {
		driver.wait(timeout, nanos);
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public Object executeAsyncScript(String arg0, Object... arg1) {
		if (isAppium()) {
			try {
				return ((AppiumDriver) driver).executeAsyncScript(arg0, arg1);
			} catch (Exception e) {
				logger.error("Error executing async script", e);
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Execute JavaScript (Appium only)
	 * 
	 * @param script JavaScript code to execute
	 * @param args Script arguments
	 * @return Script execution result
	 */
	@SuppressWarnings("rawtypes")
	public Object executeScript(String script, Object... args) {
		logger.debug("Executing script: {}", script);
		
		if (isAppium()) {
			try {
				return ((AppiumDriver) driver).executeScript(script, args);
			} catch (Exception e) {
				logger.error("Error executing script", e);
				return null;
			}
		} else {
			logger.warn("Script execution only supported for Appium drivers");
			return null;
		}
	}
	
	// ========== ENHANCED INTERACTION METHODS ==========
	
	/**
	 * Enhanced click method with wait and reporting
	 * 
	 * @param locator The locator used to identify the element
	 * @return true if click was successful, false otherwise
	 */
	public boolean clickWithWait(By locator) {
		logger.debug("Attempting to click element: {}", locator);
		
		try {
			if (waitManager.waitForElementClickable(locator)) {
				WebElement element = driver.findElement(locator);
				element.click();
				
				if (report != null) {
					report.updateTestLog("Click Action", 
						"Successfully clicked element: " + locator.toString(), Status.PASS);
				}
				
				logger.debug("Successfully clicked element: {}", locator);
				return true;
			} else {
				if (report != null) {
					report.updateTestLog("Click Failed", 
						"Element not clickable: " + locator.toString(), Status.FAIL);
				}
				return false;
			}
		} catch (Exception e) {
			logger.error("Error clicking element: {}", locator, e);
			return false;
		}
	}
	
	/**
	 * Enhanced sendKeys method with wait and reporting
	 * 
	 * @param locator The locator used to identify the element
	 * @param text Text to send to the element
	 * @return true if text input was successful, false otherwise
	 */
	public boolean sendKeysWithWait(By locator, String text) {
		logger.debug("Attempting to send keys to element: {} with text: {}", locator, text);
		
		try {
			if (waitManager.waitForElementVisible(locator)) {
				WebElement element = driver.findElement(locator);
				element.clear();
				element.sendKeys(text);
				
				if (report != null) {
					report.updateTestLog("Text Input", 
						"Successfully entered text in element: " + locator.toString(), Status.PASS);
				}
				
				return true;
			} else {
				if (report != null) {
					report.updateTestLog("Text Input Failed", 
						"Element not visible: " + locator.toString(), Status.FAIL);
				}
				return false;
			}
		} catch (Exception e) {
			logger.error("Error sending keys to element: {}", locator, e);
			return false;
		}
	}
	
	/**
	 * Select dropdown option by visible text
	 * 
	 * @param locator Dropdown element locator
	 * @param text Visible text to select
	 * @return true if selection was successful, false otherwise
	 */
	public boolean selectByText(By locator, String text) {
		try {
			if (waitManager.waitForElementVisible(locator)) {
				Select select = new Select(driver.findElement(locator));
				select.selectByVisibleText(text);
				
				if (report != null) {
					report.updateTestLog("Dropdown Selection", 
						"Selected option: " + text, Status.PASS);
				}
				
				return true;
			}
		} catch (Exception e) {
			logger.error("Error selecting dropdown option: {}", text, e);
		}
		return false;
	}
	
	/**
	 * Select dropdown option by value
	 * 
	 * @param locator Dropdown element locator
	 * @param value Value to select
	 * @return true if selection was successful, false otherwise
	 */
	public boolean selectByValue(By locator, String value) {
		try {
			if (waitManager.waitForElementVisible(locator)) {
				Select select = new Select(driver.findElement(locator));
				select.selectByValue(value);
				
				if (report != null) {
					report.updateTestLog("Dropdown Selection", 
						"Selected value: " + value, Status.PASS);
				}
				
				return true;
			}
		} catch (Exception e) {
			logger.error("Error selecting dropdown value: {}", value, e);
		}
		return false;
	}
	
	/**
	 * Hover over an element
	 * 
	 * @param locator Element locator to hover over
	 * @return true if hover was successful, false otherwise
	 */
	public boolean hoverOverElement(By locator) {
		try {
			if (waitManager.waitForElementVisible(locator)) {
				WebElement element = driver.findElement(locator);
				actions.moveToElement(element).perform();
				
				if (report != null) {
					report.updateTestLog("Hover Action", 
						"Successfully hovered over element: " + locator.toString(), Status.PASS);
				}
				
				return true;
			}
		} catch (Exception e) {
			logger.error("Error hovering over element: {}", locator, e);
		}
		return false;
	}
	
	/**
	 * Double click on an element
	 * 
	 * @param locator Element locator to double click
	 * @return true if double click was successful, false otherwise
	 */
	public boolean doubleClick(By locator) {
		try {
			if (waitManager.waitForElementClickable(locator)) {
				WebElement element = driver.findElement(locator);
				actions.doubleClick(element).perform();
				
				if (report != null) {
					report.updateTestLog("Double Click", 
						"Successfully double clicked element: " + locator.toString(), Status.PASS);
				}
				
				return true;
			}
		} catch (Exception e) {
			logger.error("Error double clicking element: {}", locator, e);
		}
		return false;
	}
	
	/**
	 * Right click on an element
	 * 
	 * @param locator Element locator to right click
	 * @return true if right click was successful, false otherwise
	 */
	public boolean rightClick(By locator) {
		try {
			if (waitManager.waitForElementClickable(locator)) {
				WebElement element = driver.findElement(locator);
				actions.contextClick(element).perform();
				
				if (report != null) {
					report.updateTestLog("Right Click", 
						"Successfully right clicked element: " + locator.toString(), Status.PASS);
				}
				
				return true;
			}
		} catch (Exception e) {
			logger.error("Error right clicking element: {}", locator, e);
		}
		return false;
	}
	
	/**
	 * Scroll to element
	 * 
	 * @param locator Element locator to scroll to
	 * @return true if scroll was successful, false otherwise
	 */
	public boolean scrollToElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			actions.moveToElement(element).perform();
			
			if (report != null) {
				report.updateTestLog("Scroll Action", 
					"Successfully scrolled to element: " + locator.toString(), Status.PASS);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("Error scrolling to element: {}", locator, e);
			return false;
		}
	}
	
	/**
	 * Get element text with wait
	 * 
	 * @param locator Element locator
	 * @return Element text or empty string if not found
	 */
	public String getTextWithWait(By locator) {
		try {
			if (waitManager.waitForElementVisible(locator)) {
				return driver.findElement(locator).getText();
			}
		} catch (Exception e) {
			logger.error("Error getting text from element: {}", locator, e);
		}
		return "";
	}
	
	/**
	 * Get element attribute with wait
	 * 
	 * @param locator Element locator
	 * @param attributeName Attribute name to get
	 * @return Attribute value or empty string if not found
	 */
	public String getAttributeWithWait(By locator, String attributeName) {
		try {
			if (waitManager.waitForElementVisible(locator)) {
				return driver.findElement(locator).getAttribute(attributeName);
			}
		} catch (Exception e) {
			logger.error("Error getting attribute '{}' from element: {}", attributeName, locator, e);
		}
		return "";
	}
	
	/**
	 * Press keyboard key
	 * 
	 * @param key Key to press
	 */
	public void pressKey(Keys key) {
		try {
			actions.sendKeys(key).perform();
			logger.debug("Pressed key: {}", key);
		} catch (Exception e) {
			logger.error("Error pressing key: {}", key, e);
		}
	}
	
	/**
	 * Wait for page to load completely
	 * 
	 * @return true if page loaded successfully
	 */
	public boolean waitForPageLoad() {
		return waitManager.waitForPageLoad();
	}



	/**
	 * Function to hide the mobile keyboard
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void hideKeyboard() {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).hideKeyboard();
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).hideKeyboard();
		}
	}

	/**
	 * Function to set the file detector
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void setFileDetector(FileDetector detector) {
		if (driver instanceof AppiumDriver) {
			((AppiumDriver) driver).setFileDetector(detector);
		}
	}



	/**
	 * Function to close the mobile App
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void closeApp(String appId) {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).terminateApp(appId);
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).terminateApp(appId);
		} else {
			throw new UnsupportedOperationException("closeApp() only supported for AndroidDriver/IOSDriver");
		}
	}

	/**
	 * Function to install the mobile from app path
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void installApp(String appPath) {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).installApp(appPath);
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).installApp(appPath);
		} else {
			throw new UnsupportedOperationException("installApp() only supported for AndroidDriver/IOSDriver");
		}
	}

	/**
	 * Function to check if the app is installed
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public boolean isAppInstalled(String bundleId) {
		if (driver instanceof AndroidDriver) {
			return ((AndroidDriver) driver).isAppInstalled(bundleId);
		} else if (driver instanceof IOSDriver) {
			return ((IOSDriver) driver).isAppInstalled(bundleId);
		}
		throw new UnsupportedOperationException("isAppInstalled() only supported for AndroidDriver/IOSDriver");
	}

	/**
	 * Function to launch the mobile app
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void launchApp(String packageId) {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).activateApp(packageId);
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).activateApp(packageId);
		}
	}

	/**
	 * Function to remove the installed mobile App
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void removeApp(String bundleId) {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).removeApp(bundleId);
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).removeApp(bundleId);
		} else {
			throw new UnsupportedOperationException("removeApp() only supported for AndroidDriver/IOSDriver");
		}
	}

	/**
	 * Function to reset the mobile App
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void resetApp() {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).resetInputState();
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).resetInputState();
		}
	}

	/**
	 * Function to get the driver context
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public String getContext() {
		if (driver instanceof AndroidDriver) {
			return ((AndroidDriver) driver).getContext();
		} else if (driver instanceof IOSDriver) {
			return ((IOSDriver) driver).getContext();
		}
		throw new UnsupportedOperationException("getContext() only supported for AndroidDriver/IOSDriver");
	}

	/**
	 * Function to get the Mobile context handles
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<String> getContextHandles() {
		if (driver instanceof AndroidDriver) {
			return ((AndroidDriver) driver).getContextHandles();
		} else if (driver instanceof IOSDriver) {
			return ((IOSDriver) driver).getContextHandles();
		}
		throw new UnsupportedOperationException("getContextHandles() only supported for AndroidDriver/IOSDriver");
	}

	/**
	 * Function to get the mobile screen orientation
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public ScreenOrientation getOrientation() {
		if (driver instanceof AndroidDriver) {
			return ((AndroidDriver) driver).getOrientation();
		} else if (driver instanceof IOSDriver) {
			return ((IOSDriver) driver).getOrientation();
		}
		throw new UnsupportedOperationException("getOrientation() only supported for AndroidDriver/IOSDriver");
	}

	/**
	 * Function to get the remote URL address
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public URL getRemoteAddress() {
		if (driver instanceof AppiumDriver) {
			return ((AppiumDriver) driver).getRemoteAddress();
		}
		throw new UnsupportedOperationException("getRemoteAddress() only supported for AppiumDriver");
	}

	/**
	 * Function to get the SessionID
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public SessionId getSessionId() {
		if (driver instanceof AppiumDriver) {
			return ((AppiumDriver) driver).getSessionId();
		}
		throw new UnsupportedOperationException("getSessionId() only supported for AppiumDriver");
	}

	/**
	 * Function to rotate the screen orientation
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void rotate(ScreenOrientation arg0) {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).rotate(arg0);
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).rotate(arg0);
		} else {
			throw new UnsupportedOperationException("rotate() only supported for AndroidDriver/IOSDriver");
		}
	}

	/**
	 * Take screenshot and return as byte array (Web & Mobile)
	 */
	public byte[] takeScreenshot() {
		try {
			if (driver instanceof TakesScreenshot) {
				return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			} else if (driver instanceof AppiumDriver) {
				return ((AppiumDriver) driver).getScreenshotAs(OutputType.BYTES);
			}
		} catch (WebDriverException e) {
			logger.error("Screenshot failed", e);
		}
		return null;
	}

	/**
	 * Save screenshot to file (Web & Mobile)
	 */
	public void saveScreenshot(String filePath) {
		try {
			if (driver instanceof TakesScreenshot) {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				java.nio.file.Files.copy(scrFile.toPath(), java.nio.file.Paths.get(filePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			} else if (driver instanceof AppiumDriver) {
				File scrFile = ((AppiumDriver) driver).getScreenshotAs(OutputType.FILE);
				java.nio.file.Files.copy(scrFile.toPath(), java.nio.file.Paths.get(filePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			logger.error("Save screenshot failed", e);
		}
	}

	/**
	 * Handle browser alerts (Web)
	 */
	public void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			logger.error("Accept alert failed", e);
		}
	}
	public void dismissAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			logger.error("Dismiss alert failed", e);
		}
	}
	public String getAlertText() {
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (Exception e) {
			logger.error("Get alert text failed", e);
			return "";
		}
	}

	/**
	 * Switch to frame by locator (Web)
	 */
	public void switchToFrame(By locator) {
		try {
			WebElement frame = driver.findElement(locator);
			driver.switchTo().frame(frame);
		} catch (Exception e) {
			logger.error("Switch to frame failed", e);
		}
	}
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	/**
	 * File upload (Web)
	 */
	public void uploadFile(By locator, String filePath) {
		try {
			WebElement uploadElement = driver.findElement(locator);
			uploadElement.sendKeys(filePath);
		} catch (Exception e) {
			logger.error("File upload failed", e);
		}
	}

	/**
	 * Cookie management (Web)
	 */
	public void addCookie(Cookie cookie) {
		driver.manage().addCookie(cookie);
	}
	public void deleteCookie(String name) {
		driver.manage().deleteCookieNamed(name);
	}
	public Cookie getCookie(String name) {
		return driver.manage().getCookieNamed(name);
	}
	public Set<Cookie> getAllCookies() {
		return driver.manage().getCookies();
	}

	/**
	 * Execute JavaScript (Web)
	 */
	public Object executeJavaScript(String script, Object... args) {
		if (driver instanceof JavascriptExecutor) {
			return ((JavascriptExecutor) driver).executeScript(script, args);
		}
		return null;
	}

	/**
	 * Mobile Touch Actions (Appium only)
	 */
//	@SuppressWarnings("rawtypes")
//	public void tap(int x, int y) {
//		if (driver instanceof AppiumDriver) {
//			new io.appium.java_client.TouchAction((AppiumDriver) driver)
//				.tap(new io.appium.java_client.touch.offset.PointOption().withCoordinates(x, y))
//				.perform();
//		} else {
//			throw new UnsupportedOperationException("tap() only supported for AppiumDriver");
//		}
//	}

//	@SuppressWarnings("rawtypes")
//	public void longPress(int x, int y, int durationMs) {
//		if (driver instanceof AppiumDriver) {
//			new io.appium.java_client.TouchAction((AppiumDriver) driver)
//				.longPress(new io.appium.java_client.touch.offset.PointOption().withCoordinates(x, y))
//				.waitAction(new io.appium.java_client.touch.WaitOptions().withDuration(Duration.ofMillis(durationMs)))
//				.release()
//				.perform();
//		} else {
//			throw new UnsupportedOperationException("longPress() only supported for AppiumDriver");
//		}
//	}

//	@SuppressWarnings("rawtypes")
//	public void swipe(int startX, int startY, int endX, int endY, int durationMs) {
//		if (driver instanceof AppiumDriver) {
//			new io.appium.java_client.TouchAction((AppiumDriver) driver)
//				.press(new io.appium.java_client.touch.offset.PointOption().withCoordinates(startX, startY))
//				.waitAction(new io.appium.java_client.touch.WaitOptions().withDuration(Duration.ofMillis(durationMs)))
//				.moveTo(new io.appium.java_client.touch.offset.PointOption().withCoordinates(endX, endY))
//				.release()
//				.perform();
//		} else {
//			throw new UnsupportedOperationException("swipe() only supported for AppiumDriver");
//		}
//	}

	@SuppressWarnings("rawtypes")
	public void pinch(int x, int y) {
		// Pinch gesture can be implemented using MultiTouchAction if needed
		// Placeholder for future implementation
		throw new UnsupportedOperationException("pinch() not implemented");
	}

	@SuppressWarnings("rawtypes")
	public void zoom(int x, int y) {
		// Zoom gesture can be implemented using MultiTouchAction if needed
		// Placeholder for future implementation
		throw new UnsupportedOperationException("zoom() not implemented");
	}

	/**
	 * Get driver capabilities (if RemoteWebDriver)
	 */
	public org.openqa.selenium.Capabilities getCapabilities() {
		if (driver instanceof RemoteWebDriver) {
			return ((RemoteWebDriver) driver).getCapabilities();
		}
		return null;
	}

	/**
	 * Get screenshot as file (WebDriver)
	 */
	public File getScreenshotAsFile() {
		if (driver instanceof TakesScreenshot) {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		}
		return null;
	}

	/**
	 * Get WebDriver.Options for advanced management
	 */
	public WebDriver.Options manage() {
		return driver.manage();
	}

	/**
	 * Get WebDriver.TargetLocator for advanced switching
	 */
	public WebDriver.TargetLocator switchToTarget() {
		return driver.switchTo();
	}

}
