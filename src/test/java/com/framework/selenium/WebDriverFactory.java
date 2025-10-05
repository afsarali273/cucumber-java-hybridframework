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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.components.FrameworkException;
import com.framework.components.Settings;
import com.framework.cucumber.TestNGListener;
import com.framework.report.ResultSummaryManager;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Factory class for creating the {@link WebDriver} object as required
 * 
 * @author Qualitest
 */
public class WebDriverFactory {
	private static Properties properties;
	private static TestNGListener listener = new TestNGListener();

	private static String downloadPath;
	private WebDriverFactory() {
		// To prevent external instantiation of this class
	}
	
	public static String getDownloadPath() {
		return downloadPath;
	}

	/**
	 * Function to return the appropriate {@link WebDriver} object based on the
	 * parameters passed
	 * 
	 * @param testParameters The {@link Browser} to be used for the test execution
	 * @return The corresponding {@link WebDriver} object
	 */

	public static WebDriver getWebDriver(SeleniumTestParameters testParameters) {
		WebDriver driver = null;
		properties = Settings.getInstance();
		
		String fileSeparator = File.separator;
		
//		downloadPath = TestNGListener.resultFolder.replace("/", "\\");
//		downloadPath +=  fileSeparator + "downloads" + fileSeparator + testParameters.getBrowserAndPlatform() + fileSeparator + testParameters.getScenario().getName()
//				+ fileSeparator + testParameters.getScenario().getId();
//		new File(downloadPath).mkdirs();
		Browser browser = testParameters.getBrowser();
		switch (browser) {
		case CHROME:
			WebDriverManager.chromedriver().setup();

			@SuppressWarnings("rawtypes")
	//		AbstractDriverOptions chrome;
			ChromeOptions chrome = new ChromeOptions();
			chrome.addArguments("--ignore-certificate-errors");
			chrome.addArguments("--test-type");
			chrome.addArguments("--disable-extensions");
			chrome.addArguments("--disable-infobars");
			chrome.addArguments("--disable-save-password-bubble");
			chrome.addArguments("--disable-gpu");
			chrome.addArguments("--enable-geolocation");
			chrome.addArguments("--start-maximized");
			chrome.addArguments("--disable-notifications");
			chrome.addArguments("--disable-component-update");
			chrome.addArguments("--remote-allow-origins=*");
			// option to prevent crash due to DevToolsActivePort
			chrome.addArguments("--no-sandbox");
			chrome.addArguments("--disable-dev-shm-usage");
			// suppress download pop-up
			chrome.addArguments("--safebrowsing-disable-download-protection");
			chrome.addArguments("--safebrowsing-disable-extension-blacklist");
			if (downloadPath != null) {
				// Setting new download directory path and download options
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("download.default_directory", downloadPath);
				prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
				prefs.put("download.prompt_for_download", false);
				prefs.put("safebrowsing.enabled", "false");
			    prefs.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
			    prefs.put("plugins.always_open_pdf_externally", true);
//				prefs.put("safebrowsing.enabled", false);
				chrome.setExperimentalOption("prefs", prefs);
			}

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chrome);
			chrome.merge(capabilities);
//			 driver = new ChromeDriver(chrome);

//			prefs.put("download.default_directory",downloadPath+"\\");
//			prefs.put("safebrowsing.enabled", "false");
//			prefs.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
//			prefs.put("plugins.always_open_pdf_externally", true);
//			prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
//			chrome.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(chrome);
			break;

        case CHROME_MOBILE_EMULATION:
            // Takes the system proxy settings automatically
            WebDriverManager.chromedriver().setup();
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Galaxy S5");
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
            driver = new ChromeDriver(options);
            break;
            
		case CHROME_HEADLESS:

			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			Map<String, Object> prefsHeadless = new HashMap<String, Object>();
			prefsHeadless.put("download.default_directory",downloadPath+"\\");
			prefsHeadless.put("safebrowsing.enabled", "false"); 
			prefsHeadless.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
			prefsHeadless.put("plugins.always_open_pdf_externally", true);
			prefsHeadless.put("profile.default_content_setting_values.automatic_downloads", 1);
			chromeOptions.setExperimentalOption("prefs", prefsHeadless);
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--window-size=1920,1080");
			driver = new ChromeDriver(chromeOptions);
			break;

		case FIREFOX:
			// Takes the system proxy settings automatically
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		case GHOST_DRIVER:
			// Takes the system proxy settings automatically (I think!)

			System.setProperty("phantomjs.binary.path", properties.getProperty("PhantomJSPath"));
			driver = new PhantomJSDriver();
			break;

		case INTERNET_EXPLORER:
			// Takes the system proxy settings automatically

			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;

		case EDGE:
			// Takes the system proxy settings automatically

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		default:
			throw new FrameworkException("Unhandled browser!");
		}

		return driver;
	}
	/**
	 * Function to return the Desired Capabilities {@link DesiredCapabilities} 
	 * 
	
	 * @return The corresponding {@link DesiredCapabilities} object
	 */
	private static DesiredCapabilities getProxyCapabilities() {
		properties = Settings.getInstance();
		String proxyUrl = properties.getProperty("ProxyHost") + ":" + properties.getProperty("ProxyPort");

		Proxy proxy = new Proxy();
		proxy.setProxyType(ProxyType.MANUAL);
		proxy.setHttpProxy(proxyUrl);
		proxy.setFtpProxy(proxyUrl);
		proxy.setSslProxy(proxyUrl);

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);

		return desiredCapabilities;
	}

	/**
	 * Function to return the {@link RemoteWebDriver} object based on the parameters
	 * passed
	 * 
	 * @param browser        The {@link Browser} to be used for the test execution
	 * @param browserVersion The browser version to be used for the test execution
	 * @param platform       The {@link Platform} to be used for the test execution
	 * @param remoteUrl      The URL of the remote machine to be used for the test
	 *                       execution
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getRemoteWebDriver(Browser browser, String browserVersion, Platform platform,
			String remoteUrl) {
		// For running RemoteWebDriver tests in Chrome and IE:
		// The ChromeDriver and IEDriver executables needs to be in the PATH of
		// the remote machine
		// To set the executable path manually, use:
		// java -Dwebdriver.chrome.driver=/path/to/driver -jar
		// selenium-server-standalone.jar
		// java -Dwebdriver.ie.driver=/path/to/driver -jar
		// selenium-server-standalone.jar

		properties = Settings.getInstance();

		boolean proxyRequired = Boolean.parseBoolean(properties.getProperty("ProxyRequired"));

		DesiredCapabilities desiredCapabilities = null;
		if (proxyRequired) {
			desiredCapabilities = getProxyCapabilities();
		} else {
			desiredCapabilities = new DesiredCapabilities();
		}

		desiredCapabilities.setBrowserName(browser.getValue());

		if (browserVersion != null) {
			desiredCapabilities.setVersion(browserVersion);
		}
		if (platform != null) {
			desiredCapabilities.setPlatform(platform);
		}

		desiredCapabilities.setJavascriptEnabled(true); // Pre-requisite for
														// remote execution

		URL url = getUrl(remoteUrl);

		return new RemoteWebDriver(url, desiredCapabilities);
	}
	/**
	 * Function to return the URL {@link URL} 
	 * 
	
	 * @return The corresponding {@link URL} object
	 */
	private static URL getUrl(String remoteUrl) {
		URL url;
		try {
			url = new URL(remoteUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified remote URL is malformed");
		}
		return url;
	}

	/**
	 * Function to return the {@link RemoteWebDriver} object based on the parameters
	 * passed
	 * 
	 * @param browser   The {@link Browser} to be used for the test execution
	 * @param remoteUrl The URL of the remote machine to be used for the test
	 *                  execution
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getRemoteWebDriver(Browser browser, String remoteUrl) {
		return getRemoteWebDriver(browser, null, null, remoteUrl);
	}

	/**
	 * Function to return the {@link ChromeDriver} object emulating the device
	 * specified by the user
	 * 
	 * @param deviceName The name of the device to be emulated (check Chrome Dev
	 *                   Tools for a list of available devices)
	 * @return The corresponding {@link ChromeDriver} object
	 */
	@SuppressWarnings("deprecation")
	public static WebDriver getEmulatedWebDriver(String deviceName) {
		ChromeOptions options = getEmulatedChromeDriverCapabilities(deviceName);

		properties = Settings.getInstance();
		System.setProperty("webdriver.chrome.driver", properties.getProperty("ChromeDriverPath"));

		return new ChromeDriver(options);
	}

	/**
	 * Function to return the {@link ChromeDriver} object emulating the device
	 * specified by the user
	 * 
	 * @param deviceName The name of the device to be emulated (check Chrome Dev
	 *                   Tools for a list of available devices)
	 * @return The corresponding {@link ChromeDriver} object
	 */
	private static ChromeOptions getEmulatedChromeDriverCapabilities(String deviceName) {
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", deviceName);

		//Map<String, Object> chromeOptions = new HashMap<String, Object>();
		ChromeOptions options = new ChromeOptions();
		options.setCapability("mobileEmulation", mobileEmulation);




//		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//		desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		return options;
	}

	/**
	 * Function to return the {@link RemoteWebDriver} object emulating the device
	 * specified by the user
	 * 
	 * @param deviceName The name of the device to be emulated (check Chrome Dev
	 *                   Tools for a list of available devices)
	 * @param remoteUrl  The URL of the remote machine to be used for the test
	 *                   execution
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getEmulatedRemoteWebDriver(String deviceName, String remoteUrl) {

		ChromeOptions options = getEmulatedChromeDriverCapabilities(deviceName);

		options.addArguments("--enable-javascript");
	//	desiredCapabilities.setJavascriptEnabled(true); // Pre-requisite for
												// remote execution

		URL url = getUrl(remoteUrl);

		return new RemoteWebDriver(url, options);
	}

	/**
	 * Function to return the {@link ChromeDriver} object emulating the device
	 * attributes specified by the user
	 * 
	 * @param deviceWidth      The width of the device to be emulated (in pixels)
	 * @param deviceHeight     The height of the device to be emulated (in pixels)
	 * @param devicePixelRatio The device's pixel ratio
	 * @param userAgent        The user agent string
	 * @return The corresponding {@link ChromeDriver} object
	 */
	@SuppressWarnings("deprecation")
	public static WebDriver getEmulatedWebDriver(int deviceWidth, int deviceHeight, float devicePixelRatio,
			String userAgent) {

		ChromeOptions options = getEmulatedChromeDriverCapabilities(deviceWidth, deviceHeight,
				devicePixelRatio, userAgent);

//		DesiredCapabilities desiredCapabilities = getEmulatedChromeDriverCapabilities(deviceWidth, deviceHeight,
//				devicePixelRatio, userAgent);

		properties = Settings.getInstance();
		System.setProperty("webdriver.chrome.driver", properties.getProperty("ChromeDriverPath"));

		return new ChromeDriver(options);
	}

	/**
	 * Function to return the {@link ChromeDriver} object emulating the device
	 * attributes specified by the user
	 * 
	 * @param deviceWidth      The width of the device to be emulated (in pixels)
	 * @param deviceHeight     The height of the device to be emulated (in pixels)
	 * @param devicePixelRatio The device's pixel ratio
	 * @param userAgent        The user agent string
	 * @return The corresponding {@link ChromeDriver} object
	 */
	private static ChromeOptions getEmulatedChromeDriverCapabilities(int deviceWidth, int deviceHeight,
			float devicePixelRatio, String userAgent) {
		Map<String, Object> deviceMetrics = new HashMap<String, Object>();
		deviceMetrics.put("width", deviceWidth);
		deviceMetrics.put("height", deviceHeight);
		deviceMetrics.put("pixelRatio", devicePixelRatio);

		Map<String, Object> mobileEmulation = new HashMap<String, Object>();
		mobileEmulation.put("deviceMetrics", deviceMetrics);
		// mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1;
		// en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko)
		// Chrome/18.0.1025.166 Mobile Safari/535.19");
		mobileEmulation.put("userAgent", userAgent);
		ChromeOptions options = new ChromeOptions();
		options.setCapability("mobileEmulation", mobileEmulation);
		//Map<String, Object> chromeOptions = new HashMap<String, Object>();
	//	options.put("mobileEmulation", mobileEmulation);

//		DesiredCapabilities desiredCapabilities = new DesiredCapabilities() ;
//		desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		return options;
	}

	/**
	 * Function to return the {@link RemoteWebDriver} object emulating the device
	 * attributes specified by the user
	 * 
	 * @param deviceWidth      The width of the device to be emulated (in pixels)
	 * @param deviceHeight     The height of the device to be emulated (in pixels)
	 * @param devicePixelRatio The device's pixel ratio
	 * @param userAgent        The user agent string
	 * @param remoteUrl        The URL of the remote machine to be used for the test
	 *                         execution
	 * @return The corresponding {@link RemoteWebDriver} object
	 */
	public static WebDriver getEmulatedRemoteWebDriver(int deviceWidth, int deviceHeight, float devicePixelRatio,
			String userAgent, String remoteUrl) {

		ChromeOptions options = getEmulatedChromeDriverCapabilities(deviceWidth, deviceHeight,
				devicePixelRatio, userAgent);

//		DesiredCapabilities desiredCapabilities = getEmulatedChromeDriverCapabilities(deviceWidth, deviceHeight,
//				devicePixelRatio, userAgent);
	//	desiredCapabilities.setJavascriptEnabled(true); // Pre-requisite for
		options.addArguments("--enable-javascript");											// remote execution

		URL url = getUrl(remoteUrl);

		return new RemoteWebDriver(url, options);
	}

}