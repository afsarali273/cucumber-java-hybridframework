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

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.components.Settings;
import com.framework.report.Status;
import com.framework.reusable.GenericResuableComponents;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;



public class CloudPlatformWebDriverFactory {

	private static Properties properties=Settings.getInstance();
	private static ThreadLocal<SauceSession> sauceSession = new ThreadLocal<>();
	protected static RemoteWebDriver driver;

	public CloudPlatformWebDriverFactory() {
		// To prevent external instantiation of this class
	}

	/**
	 * Function to return the object for RemoteWebDriver {@link RemoteWebDriver}
	 * object
	 * 
	 * @param browserName
	 *            name of the browser
	 * @param browserVersion
	 *            version of the browser
	 * @param platformName
	 *             name of the platform
	 * @param testcaseName
	 *            name of the test case
	 * 
	 * @return Instance of the {@link RemoteWebDriver} object
	 */
	public static WebDriver getRemoteWebDriver(String browserName, String browserVersion, String platformName,String testcaseName,ExecutionMode executionMode,SeleniumTestParameters testParameters) {

		System.out.println("Execution Mode - "+executionMode);
		System.out.println("Browser Version - "+browserVersion);
		switch (executionMode) {

			case SAUCELABS:
				System.out.println("Inside SauceLabs");
				System.setProperty("SAUCE_USERNAME", properties.getProperty("SauceUserName"));
				System.setProperty("SAUCE_ACCESS_KEY", properties.getProperty("SauceAccessKey"));
				SauceOptions sauceBrowserOptions = new SauceOptions();
				if(!browserName.equalsIgnoreCase("MicrosoftEdge")) {
					sauceBrowserOptions.setCapability("browserName", browserName.toLowerCase());
				}else {
					sauceBrowserOptions.setCapability("browserName", browserName);
				}
				sauceBrowserOptions.setCapability("acceptInsecureCerts", Boolean.parseBoolean(properties.getProperty("SauceInsecureCerts")));
				sauceBrowserOptions.sauce().setName(testcaseName);
				if (browserVersion != null) {
					sauceBrowserOptions.setCapability("browserVersion",browserVersion);
				}
				if (platformName != null) {
					if(platformName.contains("WINDOWS"))
						sauceBrowserOptions.setPlatformName(SaucePlatform.WINDOWS_10);
				}
				//URL url;
				Map<String, Object> sauceOptions = new HashMap<>();
				sauceOptions.put("build",properties.getProperty("SauceBuildName"));
				sauceOptions.put("name",properties.getProperty("SauceTestName"));
				sauceOptions.put("extendedDebugging",Boolean.parseBoolean(properties.getProperty("SauceExtendedLogging")));
				sauceOptions.put("capturePerformance",Boolean.parseBoolean(properties.getProperty("SauceCapturePerformance")));
				sauceOptions.put("console",properties.getProperty("SauceConsoleLog"));
				sauceOptions.put("timezone",properties.getProperty("SauceTimeZone"));
				sauceOptions.put("public", properties.getProperty("SauceJobVisibility"));
				//sauceBrowserOptions.setCapability("sauce:options", sauceOptions);
				try {
					//url = new URL(mobileproperties.getProperty("RemoteUrl"));
					sauceSession.set(new SauceSession(sauceBrowserOptions));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				driver = sauceSession.get().start();
				break;


			case AWSDEVICEFARM:
				String myProjectARN =  null;
				myProjectARN = properties.getProperty("AWS_ARN");
				DeviceFarmClient client  = DeviceFarmClient.builder().region(Region.US_WEST_2).build();

				CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder()
						.expiresInSeconds(86400) //86400 is a maximum value
						.projectArn(myProjectARN)
						.build();
				CreateTestGridUrlResponse response = client.createTestGridUrl(request);
				URL testGridUrl = null;
				try {
					testGridUrl = new URL(response.url());
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}

//				Map<String, String> mobileEmulation = new HashMap<>();
//				mobileEmulation.put("deviceName", "Nexus 7");
				ChromeOptions options = new ChromeOptions();

				System.out.println(browserName+"--"+browserVersion+"--"+platformName);

				if(!browserName.equalsIgnoreCase("MicrosoftEdge")) {
					options.setCapability("browserName", browserName.toLowerCase());
				}else {
					options.setCapability("browserName", browserName);
//					EdgeOptions Eoptions = new EdgeOptions();

				}

				//options.setCapability("browserName",browserName.toLowerCase());
				options.setCapability("browserVersion","latest");
				options.setCapability("platform","windows");
//				options.setExperimentalOption("mobileEmulation", mobileEmulation);
				driver = new RemoteWebDriver(testGridUrl, options);

				break;

			case LAMBDATEST:

				String lambdaWebGridUrl = properties.getProperty("LambdaWebHost");
				System.out.println("The LambdaTest Grid Url is - "+ lambdaWebGridUrl);
				DesiredCapabilities LTBrowserCap = new DesiredCapabilities();
				LTBrowserCap.setCapability("browserName",browserName);
				LTBrowserCap.setCapability("browserVersion",browserVersion);
				LTBrowserCap.setCapability("platformName",platformName);
				HashMap<String, Object> ltOptions = new HashMap<String, Object>();
				ltOptions.put("build", properties.getProperty("LambdaTestBuildName"));
				ltOptions.put("project", properties.getProperty("LambdaTestProjectName"));
				ltOptions.put("console", properties.getProperty("LambdaTestConsole"));
				ltOptions.put("networkThrottling", properties.getProperty("LambdaTestNetworkThrottling"));
				ltOptions.put("geoLocation", properties.getProperty("LambdaTestGeoLocation"));
				ltOptions.put("timezone", properties.getProperty("LambdaTestTimezone"));
				ltOptions.put("tunnel", Boolean.parseBoolean(properties.getProperty("LambdaTestTunnel")));
				ltOptions.put("visual", Boolean.parseBoolean(properties.getProperty("LambdaTestVisual")));
				ltOptions.put("video", Boolean.parseBoolean(properties.getProperty("LambdaTestVideo")));
				ltOptions.put("network", Boolean.parseBoolean(properties.getProperty("LambdaTestNetwork")));
				ltOptions.put("w3c", Boolean.parseBoolean(properties.getProperty("LambdaTestW3C")));
				if (!(testParameters.getCurrentTestcase() == ""))
					ltOptions.put("name", testParameters.getCurrentTestcase());
				else
					ltOptions.put("name", testParameters.getScenario().getName());
				LTBrowserCap.setCapability("LT:Options", ltOptions);
				try {
					driver = new RemoteWebDriver(new URL(lambdaWebGridUrl), LTBrowserCap);
				} catch (MalformedURLException e) {
					System.out.println("Invalid grid URL");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case BROWSERSTACK:
				String browserStackuserName = properties.getProperty("BrowserStackUserName");
				String browserStackaccessKey = properties.getProperty("BrowserStackAccessKey");
				String browserStackgridURL = properties.getProperty("BrowserStackGridUrl");
				ChromeOptions browserStackOptions = new ChromeOptions();
				browserStackOptions.setCapability("browserName", browserName);
				browserStackOptions.setCapability("browserVersion", browserVersion);
				HashMap<String, Object> bsOptions = new HashMap<String, Object>();
				bsOptions.put("os", platformName);
				bsOptions.put("osVersion", properties.getProperty("BrowserStackOsVersion"));
				bsOptions.put("resolution", properties.getProperty("BrowserStackResolution"));
				bsOptions.put("projectName",properties.getProperty("BrowserStackProjectName"));
				bsOptions.put("buildName", properties.getProperty("BrowserStackBuildName"));
				bsOptions.put("local", Boolean.parseBoolean(properties.getProperty("BrowserStackLocal")));
				bsOptions.put("consoleLogs", properties.getProperty("BrowserStackConsoleLogs"));
				bsOptions.put("debug", Boolean.parseBoolean(properties.getProperty("BrowserStackDebug")));
				bsOptions.put("networkLogs", Boolean.parseBoolean(properties.getProperty("BrowserStackNetworkLogs")));
				bsOptions.put("video", Boolean.parseBoolean(properties.getProperty("BrowserStackRecordVideo")));
				bsOptions.put("seleniumLogs", Boolean.parseBoolean(properties.getProperty("BrowserStackSeleniumLogs")));
				if (!(testParameters.getCurrentTestcase() == ""))
					bsOptions.put("sessionName", testParameters.getCurrentTestcase());
				else
					bsOptions.put("sessionName", testParameters.getScenario().getName());
				browserStackOptions.setCapability("LT:Options", bsOptions);

				try {
					driver = new RemoteWebDriver(new URL("https://" + browserStackuserName + ":" + browserStackaccessKey + browserStackgridURL), browserStackOptions);
				} catch (MalformedURLException e) {
					System.out.println("Invalid grid URL");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;


			case PERFECTO:
				String cloudName = properties.getProperty("PerfectoGridUrl");
				String securityToken = properties.getProperty("PerfectoSecurityToken");

				DesiredCapabilities capabilities = new DesiredCapabilities("mobileChrome", "", Platform.ANY);
				capabilities.setCapability("securityToken", securityToken);
				capabilities.setCapability("platformName", platformName);
				capabilities.setCapability("platformVersion", properties.getProperty("PerfectoOsVersion"));
				capabilities.setCapability("browserName", browserName);
				capabilities.setCapability("browserVersion", browserVersion);
				capabilities.setCapability("resolution", properties.getProperty("PerfectoResolution"));
				capabilities.setCapability("location", properties.getProperty("PerfectoLocation"));
				//capabilities.setCapability("seleniumVersion", properties.getProperty("PerfectoSeleniumVersion"));
				URL url = null;
				try {
					url = new URL("https://" + cloudName + "/nexperience/perfectomobile/wd/hub/fast");
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			//	RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);
				driver = new RemoteWebDriver(url,capabilities);
				break;

		}
		return driver;
	}
	
	/**
	 * Function to return the sharable link from Saucelabs
	 * 
	 * @param driver
	 *            Instance of the {@link RemoteWebDriver} object
	 */           
	public static String getShareableLink(RemoteWebDriver driver) throws NoSuchAlgorithmException, InvalidKeyException {
		GenericResuableComponents reusable = new GenericResuableComponents();
		String KEY = String.format("%s:%s", properties.getProperty("UserName"), properties.getProperty("AccessKey"));
		String SAUCE_TESTS_URL = "https://app.saucelabs.com/tests";
		String sauceJobId=driver.getSessionId().toString();
		SecretKeySpec sks = new SecretKeySpec(KEY.getBytes(US_ASCII), "HmacMD5");
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(sks);
		byte[] result = mac.doFinal(sauceJobId.getBytes(US_ASCII));
		StringBuilder hash = new StringBuilder();
		for (byte b : result) {	
			String hex = Integer.toHexString(0xFF & b);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		String digest = hash.toString();
		System.out.println(String.format("%s/%s?auth=%s", SAUCE_TESTS_URL, sauceJobId, digest));
		reusable.addTestLog("SauceLab Execution Job Link ", String.format("%s/%s?auth=%s", SAUCE_TESTS_URL, sauceJobId, digest), Status.PASS);
		return String.format("%s/%s?auth=%s", SAUCE_TESTS_URL, sauceJobId, digest);
	}
	
	/**
	 * Function to return quit sauce session
	 * 
	 * @param testStatus
	 *            status of the test case
	 */
	
	public static void quitsession(String testStatus) throws InvalidKeyException, NoSuchAlgorithmException {
		if(testStatus.equalsIgnoreCase("Passed"))
			sauceSession.get().stop(true);
		else
			sauceSession.get().stop(false);
	}

	/**
	 * Function to download screenshots based on  sauce job details
	 * 			
	 */
	//Unused Method.Kept for future reference
	public void downloadScreenshot() {
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		Request request = new Request.Builder()
				.url("https://api.us-west-1.saucelabs.com/rest/v1/lamech/jobs/da5d76ddb9b44bbf90103feefb396c23/assets/screenshots.zip")
				.method("GET", null)
				.addHeader("Authorization", "Basic bGFtZWNoOmExMGNhZGI0LWJlM2ItNDE4Ny1iZTI5LTI2OTJkZGMxNjEzMg==")
				.build();
		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.toString());
			InputStream is = (InputStream)response.body().byteStream();
			Files.copy(is, Paths.get("screenshots.zip"), StandardCopyOption.REPLACE_EXISTING);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Function to return the object for RemoteWebDriver {@link RemoteWebDriver}
	 * object
	 * 
	 * @param browserName
	 *            name of the browser
	 * @param browserVersion
	 *            version of the browser
	 * @param platformName
	 *             name of the platform
	 * @param testcaseName
	 *            name of the test case
	 * 
	 * @return Instance of the {@link RemoteWebDriver} object
	 */

	public static WebDriver getRemoteVisualWebDriver(String browserName, String browserVersion, String platformName,String testcaseName) {
		
		MutableCapabilities capabilities = new MutableCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);
		capabilities.setCapability(CapabilityType.BROWSER_VERSION, "latest");
		capabilities.setCapability(CapabilityType.PLATFORM_NAME, platformName);

		MutableCapabilities sauceOptions = new MutableCapabilities();
		sauceOptions.setCapability("username",properties.getProperty("UserName"));
		sauceOptions.setCapability("accesskey", properties.getProperty("AccessKey"));
		sauceOptions.setCapability("name",testcaseName );
		capabilities.setCapability("sauce:options", sauceOptions);

		MutableCapabilities visualOptions = new MutableCapabilities();
		visualOptions.setCapability("apiKey", properties.getProperty("SauceApiKey"));
		visualOptions.setCapability("projectName", properties.getProperty("SauceProjectName"));
		visualOptions.setCapability("viewportSize", "1280x1024");


		capabilities.setCapability("sauce:visual", visualOptions);
		// Visual requires validation when the browser version changes
		capabilities.setCapability(CapabilityType.BROWSER_VERSION, "94");

		URL url = null;
		try {
			url = new URL("https://hub.screener.io/wd/hub");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver = new RemoteWebDriver(url, capabilities);
		return driver;
	}

	
	/**
	 * Function to intialise visual screen 
	 * 			
	 * @param testName  name of the test case
	 */
	public static void intializeTestscreener(String testName) {
		if(driver!=null)
			((JavascriptExecutor) driver).executeScript("/*@visual.init*/", testName);
	}
	
	
	/**
	 * Function to capture screenshots through screener
	 * 			
	 * @param screenName  name of the screenshot
	 */
	public static void captureSaucescreener(String screenName) {
		if(driver!=null)
			((JavascriptExecutor) driver).executeScript("/*@visual.snapshot*/", screenName);
	}
}
