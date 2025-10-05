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

import io.cucumber.java.Scenario;

import org.openqa.selenium.Platform;

import com.framework.components.FrameworkException;
import com.framework.components.TestParameters;
import com.framework.components.ToolName;

/**
 * Class to encapsulate various input parameters required for each test script
 * 
 * @author Qualitest
 */
public class SeleniumTestParameters extends TestParameters {
	private ExecutionMode executionMode;
	private Browser browser;
	private String browserVersion;
	private Platform platform;
	private String deviceName;
	private boolean installApplication;
	private MobileExecutionPlatform mobileExecutionPlatform;
	private ToolName toolName;
	private String mobileOsVersion;
	private Scenario scenario;
	private boolean isAPIExecution;
	private boolean isMobileExecution;

	private boolean isWindowsExecution;

	public static String url;

	public static long timeOut;
	public SeleniumTestParameters(String currentScenario, String currentTestcase) {
		super(currentScenario, currentTestcase);
		installApplication = false;
	}

	/**
	 * Function to get the {@link ExecutionMode} for the test being executed
	 * 
	 * @return The {@link ExecutionMode} for the test being executed
	 */
	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	/**
	 * Function to set the {@link ExecutionMode} for the test being executed
	 * 
	 * @param executionMode
	 *            The {@link ExecutionMode} for the test being executed
	 */
	public void setExecutionMode(ExecutionMode executionMode) {
		this.executionMode = executionMode;
	}

	/**
	 * Function to get the {@link MobileExecutionPlatform} for the test being
	 * executed
	 * 
	 * @return The {@link MobileExecutionPlatform} for the test being executed
	 */
	public MobileExecutionPlatform getMobileExecutionPlatform() {
		return mobileExecutionPlatform;
	}

	/**
	 * Function to set the {@link MobileExecutionPlatform} for the test being
	 * executed
	 * 
	 * @param mobileExecutionPlatform
	 *            The {@link MobileExecutionPlatform} for the test being
	 *            executed
	 */
	public void setMobileExecutionPlatform(MobileExecutionPlatform mobileExecutionPlatform) {
		this.mobileExecutionPlatform = mobileExecutionPlatform;
	}

	/**
	 * Function to get the {@link ToolName} for the test being executed
	 * 
	 * @return The {@link ToolName} for the test being executed
	 */
	public ToolName getMobileToolName() {
		return toolName;
	}

	/**
	 * Function to set the {@link ToolName} for the test being executed
	 * 
	 * @param mobileToolName
	 *            The {@link ToolName} for the test being executed
	 */
	public void setMobileToolName(ToolName mobileToolName) {
		this.toolName = mobileToolName;
	}

	/**
	 * Function to get a Boolean value indicating whether to install application
	 * in Device
	 * 
	 * @return Boolean value indicating whether to install Application in device
	 */
	public boolean shouldInstallApplication() {
		return installApplication;
	}

	/**
	 * Function to set a Boolean value indicating whether to install application
	 * in Device
	 * 
	 * @param installApplication
	 *            Boolean value indicating whether to install application in
	 *            Device
	 */
	public void setInstallApplication(boolean installApplication) {
		this.installApplication = installApplication;
	}

	/**
	 * Function to get the {@link Browser} on which the test is to be executed
	 * 
	 * @return The {@link Browser} on which the test is to be executed
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * Function to set the {@link Browser} on which the test is to be executed
	 * 
	 * @param browser
	 *            The {@link Browser} on which the test is to be executed
	 */
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	/**
	 * Function to get the OS Version of device on which the test is to be
	 * executed
	 * 
	 * @return The OS Version of device Version on which the test is to be
	 *         executed
	 */
	public String getMobileOSVersion() {
		return mobileOsVersion;
	}

	/**
	 * Function to set the OS Version of device Version on which the test is to
	 * be executed
	 * 
	 * @param mobileOsVersion
	 *            The OS Version of device Version on which the test is to be
	 *            executed
	 */
	public void setmobileOSVersion(String mobileOsVersion) {
		this.mobileOsVersion = mobileOsVersion;
	}

	/**
	 * Function to get the Browser Version on which the test is to be executed
	 * 
	 * @return The Browser Version on which the test is to be executed
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Function to set the Browser Version on which the test is to be executed
	 * 
	 * @param version
	 *            The Browser Version on which the test is to be executed
	 */
	public void setBrowserVersion(String version) {
		this.browserVersion = version;
	}

	/**
	 * Function to get the {@link Platform} on which the test is to be executed
	 * 
	 * @return The {@link Platform} on which the test is to be executed
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * Function to set the {@link Platform} on which the test is to be executed
	 * 
	 * @param platform
	 *            The {@link Platform} on which the test is to be executed
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	/**
	 * Function to get the browser and platform on which the test is to be
	 * executed
	 * 
	 * @return The browser and platform on which the test is to be executed
	 */
	public String getBrowserAndPlatform() {
		
		String browserAndPlatform = null;
		if(executionMode.equals(ExecutionMode.API)) {
			browserAndPlatform = "N/A";
		} else {
			if (this.browser == null) {
				throw new FrameworkException("The browser has not been initialized!");
			}
			browserAndPlatform = this.browser.toString();
			if (this.browserVersion != null) {
				browserAndPlatform += " " + browserVersion;
			}
			if (this.platform != null) {
				browserAndPlatform += " on " + this.platform;
			}
		}
		return browserAndPlatform;
		
	}

	/**
	 * Function to get the name of the mobile device on which the test is to be
	 * executed
	 * 
	 * @return The name of the mobile device on which the test is to be executed
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Function to set the name of the mobile device on which the test is to be
	 * executed<br>
	 * <br>
	 * If the ExecutionMode is PERFECTO_REMOTEWEBDRIVER, this function also sets
	 * the device's Perfecto MobileCloud ID, by accessing the Perfecto Device
	 * List within the Global Settings.properties file
	 * 
	 * @param deviceName
	 *            The name of the mobile device on which the test is to be
	 *            executed
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Function to get Additional mobile device details
	 */
	@Override
	public String getAdditionalDetails() {
		String additionalDetails = super.getAdditionalDetails();

		if ("".equals(additionalDetails)) {
			switch (this.executionMode) {
		
			case MOBILE:
				additionalDetails = this.getMobileDeviceDetails();
				break;

			default:
				additionalDetails = this.getBrowserAndPlatform();
			}
		}

		return additionalDetails;
	}

	/**
	 * Function to get the mobile device details
	 * 
	 * @return The mobile device details from run manager
	 * 
	 */
	private String getMobileDeviceDetails() {
		String details;
		if (this.deviceName == null && this.browser == null) {
			throw new FrameworkException("The Mobile Device ID or Run Manger has not been Set in Run Manager!");
		} else {

			if (ToolName.APPIUM.equals(this.toolName)) {
				details = this.deviceName + "-" + this.toolName;
			} else {
				details = this.browser.toString() + "-" + this.platform.toString();
			}

			return details;
		}
	}
	/**
	 * @return the scenario
	 */
	public Scenario getScenario() {
		return scenario;
	}

	/**
	 * @param scenario the scenario to set
	 */
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	/**
	 * Function to set the if its API based execution
	 * 
	 * @param isAPIExecution
	 */
	public void setAPIExecution(boolean isAPIExecution) {
		this.isAPIExecution = isAPIExecution;
	}
	/**
	 * Function to set the if its Mobile based execution
	 * 
	 * @param isMobileExecution
	 */
	public void setMobileExecution(boolean isMobileExecution) {
		this.isMobileExecution = isMobileExecution;
	}
	
	/**
	 * Function to get the if its Mobile based execution
	 * 
	 * @return The isMobileExecution
	 */
	public boolean isMobileExecution() {
		return isMobileExecution;
	}
	
	/**
	 * Function to get the if its API based execution
	 * 
	 * @return The isAPIExecution
	 */
	public boolean isAPIExecution() {
		return isAPIExecution;
	}

	/**
	 * Function to set the if its Windowsapp based execution
	 *
	 * @param isWindowsExecution
	 */
	public void setWindowsExecution(boolean isWindowsExecution) {
		this.isWindowsExecution = isWindowsExecution;
	}

	public boolean isWindowsExecution() {
		return isWindowsExecution;
	}


	/**
	 * Function to get the timeout set for elements during test execution
	 *
	 * @return timeOut
	 */
	public static long getTimeOut() {
		return timeOut;
	}

	/**
	 * Function to set timeout for elements during test execution
	 * @param timeOut
	 */
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}


	/**
	 * Function to get the application URL
	 *
	 * @return url
	 */
	public static String getAppUrl() {
		return url;
	}

	/**
	 * Function to set application url
	 * @param url
	 */
	public void  setAppUrl(String url) {
		this.url = url;
	}
}