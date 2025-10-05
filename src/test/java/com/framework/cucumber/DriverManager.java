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
package com.framework.cucumber;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.framework.selenium.*;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;

import com.framework.components.Settings;
import com.framework.components.ToolName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.appium.java_client.AppiumDriver;

/**
 * A generic WebDriver manager, which handles multiple instances of WebDriver.
 * 
 * @author Qualitest
 */
public class DriverManager {

	/*
	 * Used for Multithreading of WebDriver Object
	 */
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	private static ThreadLocal<WindowsDriver> windowsDriver = new ThreadLocal<WindowsDriver>();
	private static ThreadLocal<SeleniumTestParameters> testParameters = new ThreadLocal<SeleniumTestParameters>();
	@SuppressWarnings("rawtypes")
	private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<AppiumDriver>();
	private static Properties properties = Settings.getInstance();
	private static Properties mobileProperties = Settings.getMobilePropertiesInstance();
	static Logger log;

	static {
		log = LogManager.getLogger(DriverManager.class);
	}

	/**
	 * Function to return the object for WebDriver {@link WebDriver} object
	 * 
	 * @return Instance of the {@link WebDriver} object
	 */
	public static WebDriver getWebDriver() {
		if (webDriver.get() == null) {
			// this is need when running tests from IDE
			log.error(
					"WebDriver Object didnot initialize properly, please check the capabilities/parameters in TestNG.xml");
			// setWebDriver(DriverFactory.createWebDriverInstance(null));
		}
		return webDriver.get();
	}

	/**
	 * Function to set the WebDriver Object{@link WebDriver} object
	 * 
	 * @param driver
	 */
	public static void setWebDriver(WebDriver driver) {
//		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
		DriverManager.webDriver.set(driver);
	}

	/**
	 * Function to return the object for WindowsDriver {@link WindowsDriver} object
	 *
	 * @return Instance of the {@link WindowsDriver} object
	 */
	public static WindowsDriver getWindowsDriver() {
		if (windowsDriver.get() == null) {
			// this is need when running tests from IDE
			log.error(
					"WindowsDriver Object did not initialize properly, please check the capabilities/parameters in TestNG.xml");
			// setWebDriver(DriverFactory.createWebDriverInstance(null));
		}
		return windowsDriver.get();
	}

	/**
	 * Function to set the WindowsDriver Object{@link WindowsDriver} object
	 *
	 * @param driver
	 */
	public static void setWindowsDriver(WindowsDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		DriverManager.windowsDriver.set(driver);
	}
	/**
	 * Function to set the SeleniumTestParameters
	 * Object{@link SeleniumTestParameters} object
	 * 
	 * @param testParameters
	 */
	public static void setTestParameters(SeleniumTestParameters testParameters) {
		DriverManager.testParameters.set(testParameters);
	}

	/**
	 * Function to return the object for SeleniumTestParameters
	 * {@link SeleniumTestParameters} object
	 * 
	 * @return Instance of the {@link SeleniumTestParameters} object
	 */
	public static SeleniumTestParameters getTestParameters() {
		return testParameters.get();
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver} object
	 * 
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static AppiumDriver getAppiumDriver() {
		if (appiumDriver.get() == null) {
			// this is need when running tests from IDE
			log.error(
					"AppiumDriver Object didnot initialize properly, please check the capabilities/parameters/Appium Setup");
			// setAppiumDriver(DriverFactory.createAppiumInstance(null));
		}
		return appiumDriver.get();
	}

	/**
	 * Function to set the AppiumDriver Object{@link AppiumDriver} object
	 * 
	 * @param driver
	 */
	@SuppressWarnings("rawtypes")
	public static void setAppiumDriver(AppiumDriver driver) {
//		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		DriverManager.appiumDriver.set(driver);
	}

	/**
	 * Function to return the object for WebDriver {@link WebDriver} object
	 * 
	 * @param testParameters
	 * 
	 * @return Instance of the {@link WebDriver} object
	 */
	public static WebDriver createWebDriverInstance(SeleniumTestParameters testParameters, String scenarioName) {
		WebDriver driver = null;
		try {
			switch (testParameters.getExecutionMode()) {

			case LOCAL:
				driver = WebDriverFactory.getWebDriver(testParameters);
				break;

			case GRID:
				driver = WebDriverFactory.getRemoteWebDriver(testParameters.getBrowser(),
						testParameters.getBrowserVersion(), testParameters.getPlatform(),
						properties.getProperty("RemoteUrl"));
				break;
			case SAUCELABS:

				if (properties.getProperty("SauceVisualRunner").equalsIgnoreCase("TRUE")) {
					driver = CloudPlatformWebDriverFactory.getRemoteVisualWebDriver(testParameters.getBrowser().toString(),testParameters.getBrowserVersion(),
							testParameters.getPlatform().toString(),testParameters.getScenario().getName());
					CloudPlatformWebDriverFactory.intializeTestscreener(testParameters.getScenario().getName()+testParameters.getScenario().getLine());
				} else if (testParameters.getMobileToolName().equals(ToolName.REMOTE_WEBDRIVER)) {
					driver = CloudPlatformWebDriverFactory.getRemoteWebDriver(testParameters.getBrowser().toString(),testParameters.getBrowserVersion(),
							testParameters.getPlatform().toString(),testParameters.getScenario().getName(),testParameters.getExecutionMode(),testParameters);
				}
				break;

				case BROWSERSTACK:
				case AWSDEVICEFARM:
				case LAMBDATEST:
				case PERFECTO:
				case EXPERITEST:
					if (testParameters.getMobileToolName().equals(ToolName.REMOTE_WEBDRIVER)) {
						driver = CloudPlatformWebDriverFactory.getRemoteWebDriver(testParameters.getBrowser().toString(),testParameters.getBrowserVersion(),
								testParameters.getPlatform().toString(),testParameters.getScenario().getName(),testParameters.getExecutionMode(),testParameters);
					}
					break;
					// TO DO - Satish to add visual testing scope for Browserstack (Percy) , Lambda Test (Smart UI), etc. 
			default:
				throw new Exception("Unhandled Execution Mode!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return driver;
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver} object
	 * 
	 * @param testParameters
	 * 
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static AppiumDriver createAppiumInstance(SeleniumTestParameters testParameters) {

		AppiumDriver driver = null;
		try {
			switch (testParameters.getExecutionMode()) {
			case MOBILE:
				driver = AppiumDriverFactory.getAppiumDriver(testParameters.getMobileExecutionPlatform(),
						testParameters.getDeviceName(), testParameters.getMobileOSVersion(),
						testParameters.shouldInstallApplication(), mobileProperties.getProperty("AppiumURL"));
				break;
				case BROWSERSTACK:
				case SAUCELABS:
				case LAMBDATEST:
					driver = CloudPlatformMobileDriverFactory.getSauceAppiumDriver(testParameters.getMobileExecutionPlatform(),
							testParameters.getDeviceName(),testParameters,testParameters.getExecutionMode());
					break;


			default:
				throw new Exception("Unhandled Execution Mode!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return driver;
	}


	/**
	 * Function to return the object for WindowsDriver {@link WindowsDriver} object
	 *
	 * @param testParameters
	 *
	 * @return Instance of the {@link WindowsDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static WindowsDriver createWindowsInstance(SeleniumTestParameters testParameters, String scenarioName) {

		WindowsDriver driver = null;
		try {
			switch (testParameters.getExecutionMode()) {
				case WINDOWS:
					driver = WindowsDriverFactory.getWindowsDriver(testParameters.getExecutionMode());
					break;

				default:
					throw new Exception("Unhandled Execution Mode!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return driver;
	}

}
