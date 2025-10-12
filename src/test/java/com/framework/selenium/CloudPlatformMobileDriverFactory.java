package com.framework.selenium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;

import com.framework.report.Status;
import com.framework.reusable.GenericResuableComponents;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.components.FrameworkException;
import com.framework.components.Settings;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class CloudPlatformMobileDriverFactory {

	private static Properties mobileProperties;

	private static Properties properties = Settings.getInstance();

	private CloudPlatformMobileDriverFactory() {
		// To prevent external instantiation of this class
	}

	/**
	 * Function to return the object for AppiumDriver {@link AppiumDriver} object
	 *
	 * @param executionPlatform executionPlatform{@link MobileExecutionPlatform}
	 * @param deviceName        The deviceName
	 * @param testParameters    Test Parameters {@link SeleniumTestParameters}
	 *
	 * @return Instance of the {@link AppiumDriver} object
	 */
	@SuppressWarnings("rawtypes")
	public static AppiumDriver getSauceAppiumDriver(MobileExecutionPlatform executionPlatform, String deviceName, SeleniumTestParameters testParameters, ExecutionMode executionMode) {

		AppiumDriver driver = null;
		mobileProperties = Settings.getMobilePropertiesInstance();
		MutableCapabilities capabilities = new MutableCapabilities();
		String lambdaTestURL= properties.getProperty("LambdaMobileHost");
		String sauceURL = properties.getProperty("SauceHost");
		String browserStackURL = properties.getProperty("BrowserStackHost");
		System.out.println("The execution mode is " + executionMode);

		switch (executionMode) {
			case SAUCELABS: {
				try {
					switch (executionPlatform) {
						case ANDROID:
							if (Boolean.parseBoolean(mobileProperties.getProperty("UploadMobileAppInSauceLab"))) {
								uploadAPPUsingAPI(executionPlatform);
							}
							System.setProperty("SAUCE_USERNAME", properties.getProperty("SauceUserName"));
							System.setProperty("SAUCE_ACCESS_KEY", properties.getProperty("SauceAccessKey"));
							capabilities.setCapability("platformName", "Android");
							capabilities.setCapability("appium:platformVersion", testParameters.getMobileOSVersion());
							capabilities.setCapability("appium:deviceName", testParameters.getDeviceName());
							capabilities.setCapability("app", "storage:filename=" + mobileProperties.getProperty("SauceAndroidIdentifier"));
							capabilities.setCapability("appPackage",mobileProperties.getProperty("Application_Package_Name"));
							capabilities.setCapability("appActivity",mobileProperties.getProperty("Application_MainActivity_Name"));
							capabilities.setCapability("orientation", properties.getProperty("SauceAppOrientation"));
							capabilities.setCapability("resigningEnabled", properties.getProperty("SauceResigningEnabled"));
							capabilities.setCapability("sauceLabsNetworkCaptureEnabled", properties.getProperty("SauceLabsNetworkCaptureEnabled"));
							capabilities.setCapability("autoGrantPermissions", properties.getProperty("SauceautoGrantPermissions"));
							MutableCapabilities sauceAndroidOptions = new MutableCapabilities();
							sauceAndroidOptions.setCapability("build", properties.getProperty("SauceBuildName"));
							if (!(testParameters.getCurrentTestcase() == ""))
								sauceAndroidOptions.setCapability("name", testParameters.getCurrentTestcase());
							else
								sauceAndroidOptions.setCapability("name", testParameters.getScenario().getName());
							capabilities.setCapability("sauce:options", sauceAndroidOptions);
							try {
								driver = new AndroidDriver(new URL(sauceURL), capabilities);
							} catch (MalformedURLException e) {
								throw new FrameworkException("The android driver invocation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
							}
							System.out.println("Test App launched in Saucelabs Android Real device");
							break;
						case IOS:
							if (Boolean.parseBoolean(mobileProperties.getProperty("UploadMobileAppInSauceLab"))) {
								uploadAPPUsingAPI(executionPlatform);
							}
							capabilities.setCapability("app", "storage:filename=" + mobileProperties.get("appNameSauceLabs"));
							capabilities.setCapability("deviceName", mobileProperties.getProperty("deviceNameSauceLabs"));
							capabilities.setCapability("platformName", "iOS");
							capabilities.setCapability("automationName", mobileProperties.getProperty("automationname"));
							capabilities.setCapability("noReset", mobileProperties.getProperty("ios_appreset"));
							capabilities.setCapability("cacheId", "1234");
							MutableCapabilities sauceIOSOptions = new MutableCapabilities();
							sauceIOSOptions.setCapability("build", properties.getProperty("SauceBuildName"));
							if (!(testParameters.getCurrentTestcase() == ""))
								sauceIOSOptions.setCapability("name", testParameters.getCurrentTestcase());
							else
								sauceIOSOptions.setCapability("name", testParameters.getScenario().getName());
							capabilities.setCapability("sauce:options", sauceIOSOptions);
							try {
								driver = new IOSDriver(new URL(sauceURL), capabilities);
							} catch (Exception e) {
								System.out.println("*** Problem to create the iOS driver " + e.getMessage());
								throw new RuntimeException(e);
							}
							System.out.println("Test App launched in Saucelabs IOS Real device");
							break;
						case WEB_ANDROID:
							capabilities.setCapability("appiumVersion", mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
							capabilities.setCapability("deviceName", testParameters.getDeviceName());
							capabilities.setCapability("browserName", testParameters.getBrowser());
							capabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
							capabilities.setCapability("platformName", "Android");
							capabilities.setCapability("automationName", "UiAutomator2");
							if (!(testParameters.getCurrentTestcase() == ""))
								capabilities.setCapability("name", testParameters.getCurrentTestcase());
							else
								capabilities.setCapability("name", testParameters.getScenario().getName());
							try {
								driver = new AndroidDriver(new URL(sauceURL), capabilities);
							} catch (MalformedURLException e) {
								throw new FrameworkException("The android driver/browser invocation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
							}
							break;
						case WEB_IOS:
							capabilities.setCapability("appiumVersion", mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
							capabilities.setCapability("platformName", "ios");
							capabilities.setCapability("deviceName", testParameters.getDeviceName());
							if (!(testParameters.getCurrentTestcase() == ""))
								capabilities.setCapability("name", testParameters.getCurrentTestcase());
							else
								capabilities.setCapability("name", testParameters.getScenario().getName());
							capabilities.setCapability("browserName", testParameters.getBrowser());
							capabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
							try {
								driver = new IOSDriver(new URL(sauceURL), capabilities);
							} catch (MalformedURLException e) {
								throw new FrameworkException("The IOS driver/browser invocation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
							}
							break;
						default:
							throw new FrameworkException("Unhandled ExecutionMode!");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new FrameworkException("The Sauce appium driver invocation created a problem, please check the capabilities");
				}
			}
			break;
			case BROWSERSTACK: {

				try {
					switch (executionPlatform) {

						case ANDROID:
							if (Boolean.parseBoolean(mobileProperties.getProperty("UploadMobileAppInSauceLab"))) {
								uploadAPPUsingAPI(executionPlatform);
							}

							DesiredCapabilities BSAndriodCap = new DesiredCapabilities();
							HashMap<String, Object> BSAndroidOptions = new HashMap<String, Object>();

							if (!(testParameters.getCurrentTestcase() == ""))
								BSAndroidOptions.put("sessionName", testParameters.getCurrentTestcase());
							else
								BSAndroidOptions.put("sessionName", testParameters.getScenario().getName());
							BSAndriodCap.setCapability("bstack:options", BSAndroidOptions);
							BSAndriodCap.setCapability("platformName", "android");
							BSAndriodCap.setCapability("platformVersion", testParameters.getMobileOSVersion());
							BSAndriodCap.setCapability("deviceName", testParameters.getDeviceName());
							BSAndriodCap.setCapability("app", mobileProperties.get("BrowserStackAndroidAppID"));
							try {
								driver = new AndroidDriver(new URL(browserStackURL), BSAndriodCap);

							} catch (MalformedURLException e) {
								throw new FrameworkException(
										"The android driver invokation has problem, please re-check the capabilities and check the BrowserStack details URL, Username and accessKey ");
							}
							break;

						case IOS:
							if (Boolean.parseBoolean(mobileProperties.getProperty("UploadMobileAppInSauceLab"))) {
								uploadAPPUsingAPI(executionPlatform);
							}

							// Use Java Client v6.0.0 or above
							DesiredCapabilities BSIOSCap = new DesiredCapabilities();
							HashMap<String, Object> BSIOSOptions = new HashMap<String, Object>();

							if (!(testParameters.getCurrentTestcase() == ""))
								BSIOSOptions.put("sessionName", testParameters.getCurrentTestcase());
							else
								BSIOSOptions.put("sessionName", testParameters.getScenario().getName());
							BSIOSCap.setCapability("bstack:options", BSIOSOptions);
							BSIOSCap.setCapability("platformName", "ios");
							BSIOSCap.setCapability("platformVersion", testParameters.getMobileOSVersion());
							BSIOSCap.setCapability("deviceName", testParameters.getDeviceName());
							BSIOSCap.setCapability("app", mobileProperties.get("BrowserStackIOSAppID"));

							try {
								driver = new IOSDriver(new URL(browserStackURL), BSIOSCap);

							} catch (Exception e) {
								System.out.println("*** Problem to create the iOS driver " + e.getMessage());
								throw new RuntimeException(e);
							}
							System.out.println("Test App launched in BrowserStack Real device");
							break;

						case WEB_ANDROID:
							capabilities.setCapability("appiumVersion", mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
							capabilities.setCapability("deviceName", testParameters.getDeviceName());
							capabilities.setCapability("browserName", testParameters.getBrowser());
							capabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
							capabilities.setCapability("platformName", "Android");
							capabilities.setCapability("automationName", "UiAutomator2");
							if (!(testParameters.getCurrentTestcase() == ""))
								capabilities.setCapability("name", testParameters.getCurrentTestcase());
							else
								capabilities.setCapability("name", testParameters.getScenario().getName());

							try {
								driver = new AndroidDriver(new URL(browserStackURL), capabilities);
							} catch (MalformedURLException e) {
								throw new FrameworkException(
										"The android driver/browser invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
							}
							break;

						case WEB_IOS:
							capabilities.setCapability("appiumVersion", mobileProperties.getProperty("SaucelabAppiumDriverVersion"));
							capabilities.setCapability("platformName", "ios");
							capabilities.setCapability("deviceName", testParameters.getDeviceName());
							if (!(testParameters.getCurrentTestcase() == ""))
								capabilities.setCapability("name", testParameters.getCurrentTestcase());
							else
								capabilities.setCapability("name", testParameters.getScenario().getName());
							capabilities.setCapability("browserName", testParameters.getBrowser());
							capabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());

							try {
								driver = new IOSDriver(new URL(browserStackURL), capabilities);

							} catch (MalformedURLException e) {
								throw new FrameworkException(
										"The IOS driver invokation/browser has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
							}
							break;

						default:
							throw new FrameworkException("Unhandled ExecutionMode!");

					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new FrameworkException(
							"The BrowserStack appium driver invocation created a problem , please check the capabilities");
				}

			}
			break;
			case LAMBDATEST: {
				try {
					switch (executionPlatform) {

						case ANDROID:
							if (Boolean.parseBoolean(mobileProperties.getProperty("UploadMobileAppInLambdaTest"))) {
								uploadAPPUsingAPI(executionPlatform);
							}
							DesiredCapabilities LTAndriodCap = new DesiredCapabilities();
							HashMap<String, Object> ltAndroidOptions = new HashMap<String, Object>();
							ltAndroidOptions.put("w3c", true);
							ltAndroidOptions.put("platformName", "android");
							ltAndroidOptions.put("deviceName", testParameters.getDeviceName());
							ltAndroidOptions.put("platformVersion", testParameters.getMobileOSVersion());
							ltAndroidOptions.put("app", mobileProperties.get("LambdaTestAndroidAppID"));
							ltAndroidOptions.put("deviceOrientation", mobileProperties.get("LambdaTestdeviceOrientation"));
							ltAndroidOptions.put("isRealMobile", mobileProperties.get("LambdaTestisRealMobile"));

							if (!(testParameters.getCurrentTestcase() == ""))
								ltAndroidOptions.put("name", testParameters.getCurrentTestcase());
							else
								ltAndroidOptions.put("name", testParameters.getScenario().getName());
							LTAndriodCap.setCapability("lt:options", ltAndroidOptions);
							try {
								driver = new AppiumDriver(new URL(lambdaTestURL), LTAndriodCap);
							} catch (MalformedURLException e) {
								throw new FrameworkException(
										"The android driver invokation has problem, please re-check the capabilities and check the LambdaTest details URL, Username and accessKey ");
							}
							break;

						case IOS:
							if (Boolean.parseBoolean(mobileProperties.getProperty("UploadMobileAppInLambdaTest"))) {
								uploadAPPUsingAPI(executionPlatform);
							}
							DesiredCapabilities LTIOSCap = new DesiredCapabilities();
							HashMap<String, Object> ltIOSOptions = new HashMap<String, Object>();
							ltIOSOptions.put("w3c", true);
							ltIOSOptions.put("platformName", "ios");
							ltIOSOptions.put("deviceName", testParameters.getDeviceName());
							ltIOSOptions.put("platformVersion", testParameters.getMobileOSVersion());
							ltIOSOptions.put("app", mobileProperties.get("LambdaTestIOSAppID"));
							ltIOSOptions.put("deviceOrientation", mobileProperties.get("LambdaTestdeviceOrientation"));
							ltIOSOptions.put("isRealMobile", mobileProperties.get("LambdaTestisRealMobile"));

							if (!(testParameters.getCurrentTestcase() == ""))
								ltIOSOptions.put("name", testParameters.getCurrentTestcase());
							else
								ltIOSOptions.put("name", testParameters.getScenario().getName());
							LTIOSCap.setCapability("lt:options", ltIOSOptions);
							try {
								driver = new IOSDriver(new URL(lambdaTestURL), LTIOSCap);
							} catch (Exception e) {
								System.out.println("*** Problem to create the iOS driver for LambdaTest " + e.getMessage());
								throw new RuntimeException(e);
							}
							System.out.println("Test App launched in LambdaTest Real device");
							break;

						case WEB_ANDROID:
							capabilities.setCapability("deviceName", testParameters.getDeviceName());
							capabilities.setCapability("browserName", testParameters.getBrowser());
							capabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
							capabilities.setCapability("platformName", "Android");
							if (!(testParameters.getCurrentTestcase() == ""))
								capabilities.setCapability("name", testParameters.getCurrentTestcase());
							else
								capabilities.setCapability("name", testParameters.getScenario().getName());
							capabilities.setCapability("nativeWebScreenshot", mobileProperties.get("LambdaTestnativeWebScreenshot"));
							capabilities.setCapability("isRealMobile", mobileProperties.get("LambdaTestisRealMobile"));
							capabilities.setCapability("console", mobileProperties.get("LambdaTestconsole"));
							capabilities.setCapability("network", mobileProperties.get("LambdaTestnetwork"));
							capabilities.setCapability("visual", mobileProperties.get("LambdaTestvisual"));
							capabilities.setCapability("tunnel", mobileProperties.get("LambdaTesttunnel"));
							capabilities.setCapability("newCommandTimeout", mobileProperties.get("LambdaTestnewCommandTimeout"));
							try {
								driver = new AndroidDriver(new URL(lambdaTestURL), capabilities);
							} catch (MalformedURLException e) {
								throw new FrameworkException(
										"The android driver/browser invokation has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
							}
							break;

						case WEB_IOS:
							capabilities.setCapability("platformName", "iOS");
							capabilities.setCapability("deviceName", testParameters.getDeviceName());
							if (!(testParameters.getCurrentTestcase() == ""))
								capabilities.setCapability("name", testParameters.getCurrentTestcase());
							else
								capabilities.setCapability("name", testParameters.getScenario().getName());
							capabilities.setCapability("browserName", testParameters.getBrowser());
							capabilities.setCapability("platformVersion", testParameters.getMobileOSVersion());
							capabilities.setCapability("nativeWebScreenshot", mobileProperties.get("LambdaTestnativeWebScreenshot"));
							capabilities.setCapability("isRealMobile", mobileProperties.get("LambdaTestisRealMobile"));
							capabilities.setCapability("console", mobileProperties.get("LambdaTestconsole"));
							capabilities.setCapability("network", mobileProperties.get("LambdaTestnetwork"));
							capabilities.setCapability("visual", mobileProperties.get("LambdaTestvisual"));
							capabilities.setCapability("tunnel", mobileProperties.get("LambdaTesttunnel"));
							capabilities.setCapability("newCommandTimeout", mobileProperties.get("LambdaTestnewCommandTimeout"));
							try {
								driver = new IOSDriver(new URL(lambdaTestURL), capabilities);
							} catch (MalformedURLException e) {
								throw new FrameworkException(
										"The IOS driver invokation/browser has problem, please re-check the capabilities and check the SauceLabs details URL, Username and accessKey ");
							}
							break;

						default:
							throw new FrameworkException("Unhandled ExecutionMode!");

					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new FrameworkException(
							"The LambdaTest appium driver invocation created a problem , please check the capabilities");
				}

			}
			break;

		}
		return driver;


	}

	/**
	 * Function to upload app in Sauce Mobile using API
	 *
	 * @param executionPlatform executionPlatform{@link MobileExecutionPlatform}
	 */

	public static void uploadAPPUsingAPI(MobileExecutionPlatform executionPlatform) {
		File theFile = new File(mobileProperties.getProperty("SauceLabsMobileUploadPath"));
		String parentPathFile = theFile.getParent();
		String destinationPathFile = theFile.getName();
		System.out.println("Have the Parent path file-->"+parentPathFile);
		System.out.println("Have the Destination path file-->"+destinationPathFile);
		String encodedString = Base64.getEncoder().encodeToString((properties.get("SauceUserName").toString() + ":"
				+ properties.getProperty("SauceAccessKey").toString()).getBytes());
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("payload",parentPathFile)
				.addFormDataPart("name",destinationPathFile)
				.build();
		Request request = new Request.Builder()
				.url("https://api.us-west-1.saucelabs.com/v1/storage/upload")
				.method("POST", body)
				.addHeader("Authorization", "Basic "+ encodedString)
				.build();
		try {
			Response response = client.newCall(request).execute();
			String jsonData = response.body().string();
			System.out.println("Test app is uploaded in Sauce Labs: \n" + jsonData);
//			JSONObject Jobject = new JSONObject(jsonData);
//			JSONObject getSth = Jobject.getJSONObject("item");
//			String value = getSth.get("id").toString();
//			if(String.valueOf(executionPlatform).equals("IOS")) {
//				mobileProperties.setProperty("appNameSauceLabs", value);
//			} else {
//				mobileProperties.setProperty("SauceAndroidIdentifier", value);
//			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to return the sharable link from Saucelabs
	 *
	 * @param driver
	 *            Instance of the {@link RemoteWebDriver} object
	 */
//	public static String getShareableLink(AppiumDriver driver) throws NoSuchAlgorithmException, InvalidKeyException {
//		GenericResuableComponents reusable = new GenericResuableComponents();
//		String KEY = String.format("%s:%s", properties.getProperty("UserName"), properties.getProperty("AccessKey"));
//		String SAUCE_TESTS_URL = "https://app.saucelabs.com/tests";
//		String sauceJobId=driver.getSessionId().toString();
//		SecretKeySpec sks = new SecretKeySpec(KEY.getBytes(US_ASCII), "HmacMD5");
//		Mac mac = Mac.getInstance("HmacMD5");
//		mac.init(sks);
//		byte[] result = mac.doFinal(sauceJobId.getBytes(US_ASCII));
//		StringBuilder hash = new StringBuilder();
//		for (byte b : result) {
//			String hex = Integer.toHexString(0xFF & b);
//			if (hex.length() == 1) {
//				hash.append('0');
//			}
//			hash.append(hex);
//		}
//		String digest = hash.toString();
//		System.out.println(String.format("%s/%s?auth=%s", SAUCE_TESTS_URL, sauceJobId, digest));
//		reusable.addTestLog("SauceLab Execution Job Link ", String.format("%s/%s?auth=%s", SAUCE_TESTS_URL, sauceJobId, digest), Status.PASS);
//		return String.format("%s/%s?auth=%s", SAUCE_TESTS_URL, sauceJobId, digest);
//	}

}
