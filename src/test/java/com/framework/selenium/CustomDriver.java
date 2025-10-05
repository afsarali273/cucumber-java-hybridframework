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
import io.appium.java_client.MultiTouchAction;


import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.report.Status;

import org.openqa.selenium.WebElement;

public class CustomDriver {

	private SeleniumTestParameters testParameters;
	private SeleniumReport report;
	private WebDriver driver;

	public CustomDriver(WebDriver driver) {
		this.driver = driver;
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
	 * Function to return the object for WindowsDriver {@link WebDriver} object
	 *
	 * @return Instance of the {@link WindowsDriver} object
	 */
	public WindowsDriver getWinDriver() {
		return (WindowsDriver) driver;
	}
	/**
	 * Function to get the if its Appium based execution
	 * 
	 * @return boolean
	 */
	private boolean isAppium() {
		boolean isAppium = false;
		if (testParameters.getMobileToolName().toString().equals("APPIUM")) {
			isAppium = true;
		}
		return isAppium;
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
	 * Function to Find the first {@link WebElement}, also take screen shot and wait
	 * until the specified element is visible
	 * 
	 * @param arg0 The locator used to identify the element {@link WebDriver}
	 * @return WebElement Object
	 */
	public WebElement findElementnTakescreenShot(By arg0) {
		WebElement element;
		if (driver.findElement(arg0).isDisplayed() || isElementVisible(arg0)) {
			report.updateTestLog("Action", "Action Performed Successfully", Status.PASS);
			element = driver.findElement(arg0);
		} else {
			report.updateTestLog("Action", "Action Failed to Perform, please check the Locators", Status.FAIL);
			element = null;
		}
		return element;
	}

	/**
	 * Function to wait until the specified element is visible
	 * 
	 * @param arg0 The locator used to identify the element {@link WebDriver}
	 */
	public boolean isElementVisible(By arg0) {
		boolean elementVisible = false;
		try {
			(new WebDriverWait(driver, Duration.ofSeconds(60))).until(ExpectedConditions.visibilityOfElementLocated(arg0));
			elementVisible = true;

		} catch (TimeoutException ex) {
			elementVisible = false;
		}
		return elementVisible;
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
	 * Function to Gets the Option interface. {@link WebDriver}
	 */
	public Options manage() {
		return driver.manage();
	}

	/**
	 * Function to GetAn abstraction allowing the driver to access the browser's
	 * history and to navigate to a given URL. {@link WebDriver}
	 */
	public Navigation navigate() {
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
	public TargetLocator switchTo() {
		return driver.switchTo();
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
			return ((AppiumDriver) driver).executeAsyncScript(arg0, arg1);
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public Object executeScript(String arg0, Object... arg1) {
		if (isAppium()) {
			return ((AppiumDriver) driver).executeScript(arg0, arg1);

		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link WebElement}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementById(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.id(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is OR <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link WebElement}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebElement> findElementsById(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.id(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementByName(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.name(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is OR <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebElement> findElementsByName(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.name(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link WebElement}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementByXPath(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.xpath(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebElement> findElementsByXPath(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.xpath(arg0));

		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is < <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementByLinkText(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.linkText(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebElement> findElementsByLinkText(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.linkText(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementByPartialLinkText(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.partialLinkText(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b>SEETEST i.e., {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebElement> findElementsByPartialLinkText(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.partialLinkText(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementByClassName(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.className(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b/>APPIUM i.e.,
	 * {@link AppiumDriver} OR <b/>SEETEST i.e., {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebElement> findElementsByClassName(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.className(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementByTagName(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.tagName(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<WebElement> findElementsByTagName(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.tagName(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public WebElement findElementByCssSelector(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElement(By.cssSelector(arg0));
		} else {
			return null;
		}
	}

	/**
	 * Function Applicable only when the tool used is <b>APPIUM i.e.,
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebElement> findElementsByCssSelector(String arg0) {
		if (isAppium()) {
			return ((AppiumDriver) driver).findElements(By.cssSelector(arg0));
		} else {
			return null;
		}
	}

	// Appium Methods

	/**
	 * Function to get the driver capabilities
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public Capabilities getCapabilities() {
		return ((AppiumDriver) driver).getCapabilities();
	}

	/**
	 * Function to get the command executor
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public CommandExecutor getCommandExecutor() {
		return ((AppiumDriver) driver).getCommandExecutor();
	}

	/**
	 * Function to get the error handler
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public ErrorHandler getErrorHandler() {
		return ((AppiumDriver) driver).getErrorHandler();
	}

	/**
	 * Function to get the execute method
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public ExecuteMethod getExecuteMethod() {
		return ((AppiumDriver) driver).getExecuteMethod();
	}

	/**
	 * Function to get the File Detector
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public FileDetector getFileDetector() {
		return ((AppiumDriver) driver).getFileDetector();
	}

	/**
	 * Function to get the context
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public WebDriver context(String arg0) {
		return ((AndroidDriver) driver).context(arg0);
	}

	/**
	 * Function to execute the driver command
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response execute(String driverCommand, Map<String, ?> parameters) {
		return ((AppiumDriver) driver).execute(driverCommand, parameters);
	}

	/**




	/**
	 * Function to get the driver context
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public String getContext() {
		return ((AndroidDriver) driver).getContext();
	}

	/**
	 * Function to get the Mobile context handles
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<String> getContextHandles() {
		return ((AndroidDriver) driver).getContextHandles();
	}

	/**
	 * Function to get the mobile screen orientation
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public ScreenOrientation getOrientation() {
		return ((AndroidDriver) driver).getOrientation();
	}

	/**
	 * Function to get the remote URL address
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public URL getRemoteAddress() {
		return ((AndroidDriver) driver).getRemoteAddress();
	}


	/**
	 * Function to get the SessionID
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public SessionId getSessionId() {
		return ((AppiumDriver) driver).getSessionId();
	}


	/**
	 * Function to rotate the screen orientation
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void rotate(ScreenOrientation arg0) {
		((AndroidDriver) driver).rotate(arg0);
	}

//	/**
//	 * Function to find the webelement by Accessibility Id
//	 * {@link AppiumDriver}.
//	 */
//	@SuppressWarnings("rawtypes")
//	public WebElement findElementByAccessibilityId(String arg0) {
//
//		return ((AppiumDriver) driver).findElementByAccessibilityId(arg0);
//	}

//	/**
//	 * Function to find the list of the WebElements by Accessibility ID
//	 * {@link AppiumDriver}.
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public List<WebElement> findElementsByAccessibilityId(String arg0) {
//
//		return ((AndroidDriver) driver).findElementsByAccessibilityId(arg0);
//	}

	/**
	 * Function to identify the location of the mobile device
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public Location location() {
		return ((AndroidDriver) driver).location();
	}

	/**
	 * Function to set the location of the mobile app
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void setLocation(Location arg0) {
		((AndroidDriver) driver).setLocation(arg0);
	}

	/**
	 * Function to hide the mobile keyboard
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void hideKeyboard() {
		((AndroidDriver) driver).hideKeyboard();
	}

	/**
	 * Function to set the file detector
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void setFileDetector(FileDetector detector) {
		((AppiumDriver) driver).setFileDetector(detector);
	}

	/**
	 * Function to set the log level
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void setLogLevel(Level level) {
		((AppiumDriver) driver).setLogLevel(level);
	}

	/**
	 * Function to pull the file from the remote path
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public byte[] pullFile(String remotePath) {
		return ((AndroidDriver) driver).pullFile(remotePath);
	}

	/**
	 * Function to pull the folder from the remote path
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public byte[] pullFolder(String remotePath) {
		return ((AndroidDriver) driver).pullFolder(remotePath);
	}

	/**
	 * Function to close the mobile App
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void closeApp(String appId) {
		((AndroidDriver) driver).terminateApp(appId);
	}

	/**
	 * Function to install the mobile from app path
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void installApp(String appPath) {
		((AndroidDriver) driver).installApp(appPath);
	}

	/**
	 * Function to check if the app is installed
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public boolean isAppInstalled(String bundleId) {
		return ((AndroidDriver) driver).isAppInstalled(bundleId);
	}

	/**
	 * Function to launch the mobile app
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void launchApp() {
		((AndroidDriver) driver).launchApp();
	}

	/**
	 * Function to remove the installed mobile App
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void removeApp(String bundleId) {
		((AndroidDriver) driver).removeApp(bundleId);
	}

	/**
	 * Function to reset the mobile App
	 * {@link AppiumDriver}.
	 */
	@SuppressWarnings("rawtypes")
	public void resetApp() {
		((AndroidDriver) driver).resetApp();
	}

}
