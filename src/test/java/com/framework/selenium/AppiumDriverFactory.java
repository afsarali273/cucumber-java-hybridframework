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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.MutableCapabilities;

import com.framework.components.FrameworkException;
import com.framework.components.Settings;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class AppiumDriverFactory {

	private static Properties mobileProperties;

	private AppiumDriverFactory() {
		// To prevent external instantiation of this class
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver} object
	 * 
	 * @param executionPlatform executionPlatform{@link MobileExecutionPlatform}
	 * @param deviceName        The deviceName
	 * @param version           The Mobile Device OS Version
	 * @param installApp        Boolean to install App
	 * @param appiumURL         URL of the Appium
	 * 
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static AppiumDriver getAppiumDriver(MobileExecutionPlatform executionPlatform, String deviceName,
			String version, Boolean installApp, String appiumURL) {

		AppiumDriver driver = null;
		mobileProperties = Settings.getMobilePropertiesInstance();
		MutableCapabilities capabilities = new MutableCapabilities();
		try {
			switch (executionPlatform) {

			case ANDROID:
				if (Boolean.parseBoolean(mobileProperties.getProperty("InstallApplicationInDevice"))) {
					capabilities.setCapability("app", mobileProperties.getProperty("AndroidApplicationPath"));
				}
				if (!Boolean.parseBoolean(mobileProperties.getProperty("ResetApp"))) {
					capabilities.setCapability("noReset", "true");
				}
				capabilities.setCapability("platformName", "Android");
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability("platformVersion", version);
				capabilities.setCapability("appPackage", mobileProperties.getProperty("Application_Package_Name"));
				capabilities.setCapability("appActivity", mobileProperties.getProperty("Application_MainActivity_Name"));
				try {
					driver = new AndroidDriver(new URL(appiumURL), capabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException("The android driver invocation has problem, please re-check the capabilities or Start Appium");
				}
				break;

			case IOS:
				if (Boolean.parseBoolean(mobileProperties.getProperty("InstallApplicationInDevice"))) {
					capabilities.setCapability("app", mobileProperties.getProperty("ios_apppath"));
				}
				if (!Boolean.parseBoolean(mobileProperties.getProperty("ResetApp"))) {
					capabilities.setCapability("noReset", "true");
				}
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability("platformName", "iOS");
				capabilities.setCapability("udid", mobileProperties.getProperty("im_udid_1"));
				capabilities.setCapability("updatedWDABundleId", mobileProperties.getProperty("ios_bundleid"));
				capabilities.setCapability("App", mobileProperties.getProperty("ios_apppath"));
				capabilities.setCapability("noReset", mobileProperties.getProperty("ios_appreset"));
				capabilities.setCapability("automationName", mobileProperties.getProperty("automationname"));
				capabilities.setCapability("useNewWDA", mobileProperties.getProperty("false"));
				try {
					driver = new IOSDriver(new URL(appiumURL), capabilities);
				} catch (Exception e) {
					throw new FrameworkException("The IOS driver invocation has problem, please re-check the capabilities or Start Appium");
				}
				break;

			case WEB_ANDROID:
				capabilities.setCapability("platformName", "Android");
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability("platformVersion", version);
				capabilities.setCapability("browserName", "Chrome");
				try {
					driver = new AndroidDriver(new URL(appiumURL), capabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException("The android driver invocation has problem, please check the capabilities or Start Appium");
				}
				break;

			case WEB_IOS:
				capabilities.setCapability("platformName", "iOS");
				capabilities.setCapability("platformVersion", version);
				capabilities.setCapability("deviceName", deviceName);
				capabilities.setCapability("udid", deviceName);
				capabilities.setCapability("automationName", "XCUITest");
				capabilities.setCapability("browserName", "Safari");
				try {
					driver = new IOSDriver(new URL(appiumURL), capabilities);
				} catch (MalformedURLException e) {
					throw new FrameworkException("The IOS driver invocation has problem, please check the capabilities or Start Appium");
				}
				break;

			default:
				throw new FrameworkException("Unhandled ExecutionMode!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new FrameworkException("The appium driver invocation created a problem, please check the capabilities");
		}
		return driver;
	}

}
