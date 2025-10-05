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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Platform;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.framework.components.ApplitoolsOperations;
import com.framework.components.Settings;
import com.framework.components.ToolName;
import com.framework.components.WhitelistingPath;
import com.framework.report.ExtentReport;
import com.framework.report.ResultSummaryManager;
import com.framework.report.TimeStamp;
import com.framework.report.Util;
import com.framework.selenium.Browser;
import com.framework.selenium.ExecutionMode;
import com.framework.selenium.MobileExecutionPlatform;
import com.framework.selenium.SeleniumTestParameters;
import com.jayway.jsonpath.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.cucumber.java.Before;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestNGListener implements ITestListener, ISuiteListener{

	ExtentReport rpt=new ExtentReport();
	public static String resultFolder = "";
	private ResultSummaryManager resultSummaryManager = ResultSummaryManager.getInstance();
	private Properties properties ;
	static Logger log = LogManager.getLogger(TestNGListener.class);
	public ApplitoolsOperations appli=new ApplitoolsOperations();
	/**
	 * Function to cover onStart functionalities
	 *
	 * @param suite Test Result container summary
	 */
	@Before
	public void onStart(ISuite suite) {
		resultSummaryManager.setRelativePath();
		properties = Settings.getInstance();
		if ((Boolean.parseBoolean(properties.getProperty("SaveReports")))) {
			resultFolder = TimeStamp.getInstance();
			TimeStamp.getScreenShotInstanace();
		}
		appli.setUpAppliTools();
	}
	/**
	 * Function to cover onFinish functionalities
	 *
	 * @param suite Test Result container summary
	 */
	@Before
	public void onFinish(ISuite suite) {
		appli.getesultsSummary();
		if ((Boolean.parseBoolean(properties.getProperty("SaveReports")))) {
			copyReportsFolder();
			allureReport();
			if(Settings.getInstance().get("UploadToJIRA").toString().equalsIgnoreCase("True"))
				zephyrTestresult();
		}
		
	}
	/**
	 * Function to initiate allure report
	 *
	 */
	public void allureReport() {	
		if(Settings.getInstance().get("Allure-Report").toString().equalsIgnoreCase("True"))
			try {
				Runtime.getRuntime().exec("cmd /c \"cd  "+System.getProperty("user.dir")+" && allure serve target/allure-results/ \"");
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
	/**
	 * Function to publish the Zephyr results
	 *
	 */
	public void zephyrTestresult() {
		String encryptedDestPath = resultFolder + Util.getFileSeparator() + "CucumberReport";
		zipFile(encryptedDestPath);
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("file",encryptedDestPath + Util.getFileSeparator() +"cucumber.zip",
						RequestBody.create(MediaType.parse("application/octet-stream"),
								new File(encryptedDestPath + Util.getFileSeparator() +"cucumber.zip"))) .build();
		Request request = new Request.Builder()
				.url("https://api.zephyrscale.smartbear.com/v2/automations/executions/cucumber?projectKey="
						+ ""+Settings.getInstance().get("ProjectKey").toString()+
						"&autoCreateTestCases=true")
				.method("POST", body)
				.addHeader("Authorization", Settings.getInstance().get("Authorization").toString())
				.build();
		try {
			Response response = client.newCall(request).execute();
			System.out.println("\n");
			System.out.println("Test results uploaded in JIRA to the below test cycle: \n" + response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Function to zip the cucumber.json test results file
	 *
	 */
	private static void zipFile(String encryptedDestPath) {

		try {
			File file = new File(encryptedDestPath+ Util.getFileSeparator() + "cucumber.json");
			FileOutputStream fos = new FileOutputStream(encryptedDestPath + Util.getFileSeparator() + "cucumber.zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			zos.putNextEntry(new ZipEntry(file.getName()));
			byte[] bytes = Files.readAllBytes(Paths.get(encryptedDestPath+ Util.getFileSeparator() + "cucumber.json"));
			zos.write(bytes, 0, bytes.length); 
			zos.closeEntry();
			zos.close(); 
		} catch (FileNotFoundException ex) {
			System.err.format("The file %s does not exist", encryptedDestPath+ Util.getFileSeparator() + "cucumber.json");
		} catch (IOException ex) { 
			System.err.println("I/O error: " + ex); 
		} 
	}
	/**
	 * Function to copy the reports folder
	 *
	 */
	private void copyReportsFolder() {

		File sourceCucumber = new File(Util.getCucumberReportPath());
		File sourceExtent = new File(Util.getExtentReportPath());
		File sourceScreenshot = new File(Util.getScreenshotPath());
		File destination = new File(resultFolder);

		String encryptedDestPath = WhitelistingPath
				.cleanStringForFilePath(destination + Util.getFileSeparator() + "CucumberReport");
		File destCucumPath = new File(encryptedDestPath);
		destCucumPath.mkdir();

		String encryptedExtentPath = WhitelistingPath
				.cleanStringForFilePath(destination + Util.getFileSeparator() + "ExtentReport");
		File destExtentPath = new File(encryptedExtentPath);
		destExtentPath.mkdir();


		String encryptedScrPath = WhitelistingPath
				.cleanStringForFilePath(destination + Util.getFileSeparator() + "Screenshots");
		File destScrPath = new File(encryptedScrPath);
		destScrPath.mkdir();

		try {
			FileUtils.copyDirectory(sourceCucumber, destCucumPath);
			if(sourceScreenshot.exists())
				FileUtils.copyDirectory(sourceScreenshot, destScrPath);
			FileUtils.copyDirectory(sourceExtent, destExtentPath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Function to set the Test Parameters on Test Start
	 *
	 */
	@Before
	public void onTestStart(ITestResult result) {
		SeleniumTestParameters testParameters = new SeleniumTestParameters("","");
		try {
			setDefaultTestParameters(testParameters);
			DriverManager.setTestParameters(testParameters);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	private void setDefaultTestParameters(ITestContext testContext, SeleniumTestParameters testParameters) {
		try {
			String executionMode = testContext.getCurrentXmlTest().getLocalParameters().get("ExecutionMode");
			String toolName = testContext.getCurrentXmlTest().getLocalParameters().get("ToolName");
			if(executionMode==null) {
				executionMode=properties.getProperty("DefaultExecutionMode");			
			}

			isMobileExecution(executionMode, toolName, testParameters);
			isAPIExecution(executionMode, testParameters);

			switch (ExecutionMode.valueOf(executionMode)) {

			case API:

				testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));
				break;

			case LOCAL:

				testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));

				if (testContext.getCurrentXmlTest().getLocalParameters().get("BrowserName") == null) {
					testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));

				} else {
					testParameters.setBrowser(
							Browser.valueOf(testContext.getCurrentXmlTest().getLocalParameters().get("BrowserName")));
				}

				break;

			case SAUCELABS:

				testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));

				if (testContext.getCurrentXmlTest().getLocalParameters().get("BrowserName") == null) {
					testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));

				} else {
					testParameters.setBrowser(
							Browser.valueOf(testContext.getCurrentXmlTest().getLocalParameters().get("BrowserName")));
				}
				if (testContext.getCurrentXmlTest().getLocalParameters().get("BrowserVersion") == null) {
					testParameters.setBrowserVersion(properties.getProperty("DefaultBrowserVersion"));

				} else {
					testParameters.setBrowserVersion(
							testContext.getCurrentXmlTest().getLocalParameters().get("BrowserVersion"));
				}

				if (testContext.getCurrentXmlTest().getLocalParameters().get("Platform") == null) {
					testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));

				} else {
					testParameters
					.setBrowserVersion(testContext.getCurrentXmlTest().getLocalParameters().get("Platform"));
				}


				break;

			case GRID:

				testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));

				if (testContext.getCurrentXmlTest().getLocalParameters().get("BrowserName") == null) {
					testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));

				} else {
					testParameters.setBrowser(
							Browser.valueOf(testContext.getCurrentXmlTest().getLocalParameters().get("BrowserName")));
				}

				if (testContext.getCurrentXmlTest().getLocalParameters().get("BrowserVersion") == null) {
					testParameters.setBrowserVersion(properties.getProperty("DefaultBrowserVersion"));

				} else {
					testParameters.setBrowserVersion(
							testContext.getCurrentXmlTest().getLocalParameters().get("BrowserVersion"));
				}

				if (testContext.getCurrentXmlTest().getLocalParameters().get("Platform") == null) {
					testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));

				} else {
					testParameters
					.setBrowserVersion(testContext.getCurrentXmlTest().getLocalParameters().get("Platform"));
				}

				break;

			case MOBILE:

				testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));

				if (testContext.getCurrentXmlTest().getLocalParameters().get("ToolName") == null) {
					testParameters.setMobileToolName(ToolName.valueOf(properties.getProperty("DefaultToolName")));
				} else {
					String mobileToolName = testContext.getCurrentXmlTest().getLocalParameters().get("ToolName");
					testParameters.setMobileToolName((ToolName.valueOf(mobileToolName)));
				}

				if (testContext.getCurrentXmlTest().getLocalParameters().get("MobileExecutionPlatform") == null) {
					testParameters.setMobileExecutionPlatform(
							MobileExecutionPlatform.valueOf(properties.getProperty("DefaultMobileExecutionPlatform")));
				} else {
					String mobileExecutionPlatform = testContext.getCurrentXmlTest().getLocalParameters()
							.get("MobileExecutionPlatform");
					testParameters
					.setMobileExecutionPlatform((MobileExecutionPlatform.valueOf(mobileExecutionPlatform)));
				}

				if (testContext.getCurrentXmlTest().getLocalParameters().get("DeviceName") == null) {
					testParameters.setDeviceName(properties.getProperty("DefaultDeviceName"));

				} else {
					testParameters
					.setDeviceName(testContext.getCurrentXmlTest().getLocalParameters().get("DeviceName"));
				}

				break;

			default:

				testParameters.setExecutionMode(ExecutionMode.valueOf(properties.getProperty("DefaultExecutionMode")));
				testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));
			}
		} catch (Exception ex) {
			log.error(
					"Error at settings TestParameters , please check the TestNG XML suite File and pass valid key & values"
							+ ex.getMessage());
		}
	}
	 */

	/**
	 * Function to check if it is an API Execution
	 *
	 */

	private void isAPIExecution(String executionMode,SeleniumTestParameters testParameters) {
		testParameters.setAPIExecution(false);
		if (executionMode.equals(ExecutionMode.API.toString())) {
			testParameters.setAPIExecution(true);
		}
	}
	/**
	 * Function to check if it is a Mobile Execution
	 *
	 */
	private void isMobileExecution(String executionMode, String toolName, SeleniumTestParameters testParameters) {

		testParameters.setMobileExecution(false);
		if (!(toolName == null)) {
			if (toolName.equals(ToolName.APPIUM.toString())) {
				testParameters.setMobileExecution(true);
			}
		}
	}

	/**
	 * Function to check if it is a Windows Execution
	 *
	 */
	private void isWINDOWSExecution(String executionMode, SeleniumTestParameters testParameters) {
		testParameters.setWindowsExecution(false);
		if (executionMode.equals(ExecutionMode.WINDOWS.toString())) {
			testParameters.setWindowsExecution(true);
		}
	}

	/**
	 * Function to set the default Test parameters
	 *
	 */
	private void setDefaultTestParameters(SeleniumTestParameters testParameters) {

		String propertiesPath = System.getProperty("user.dir") + Util.getFileSeparator() + "src"
				+ Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator()
				+ "properties" + Util.getFileSeparator();
		String configData = null;

		try {configData = new String(Files.readAllBytes(Paths.get(propertiesPath + "properties.json")));}
		catch (IOException e) {e.printStackTrace();}

		String testConfig = null;

		if (System.getProperty("TestConfiguration").equals("Default"))
			testConfig = properties.getProperty("TestConfigurationID");
		else
			testConfig = System.getProperty("TestConfiguration");

		//testConfig = properties.getProperty("TestConfigurationID");

		testParameters.setTimeOut(getTimeoOutParameter());
		testParameters.setAppUrl(getURLParameter());
		String executionMode = readStringValue(configData, testConfig + ".ExecutionMode");
		String toolName = readStringValue(configData, testConfig + ".ToolName");
		isMobileExecution(executionMode, toolName, testParameters);
		isAPIExecution(executionMode, testParameters);
		isWINDOWSExecution(executionMode, testParameters);
		testParameters.setMobileToolName(ToolName.valueOf(toolName));

		if (testConfig.contains("API"))
			testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));

		else if (testConfig.contains("LOCAL")) {
			testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));
			testParameters.setBrowser(Browser.valueOf(readStringValue(configData, testConfig + ".Browser")));

		} else if (testConfig.contains("GRID")) {
			testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));
			testParameters.setBrowser(Browser.valueOf(readStringValue(configData, testConfig + ".Browser")));
			testParameters.setBrowserVersion(readStringValue(configData, testConfig + ".BrowserVersion"));
			testParameters.setPlatform(Platform.valueOf(readStringValue(configData, testConfig + ".Platform")));

		} else if (testConfig.contains("Appium") || testConfig.contains("Sauce")|| testConfig.contains("AWS") || testConfig.contains("LAMBDA")|| testConfig.contains("BROWSERSTACK")|| testConfig.contains("PERFECTO")|| testConfig.contains("EXPERITEST")|| testConfig.contains("WINDOWS")) {
			testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));
			testParameters.setMobileToolName(ToolName.valueOf(toolName));
			String browser = readStringValue(configData, testConfig + ".Browser");

			if (!browser.equals("JsonPathNotExist"))
				testParameters.setBrowser(Browser.valueOf(readStringValue(configData, testConfig + ".Browser")));
			String mobileExecutionPlatform = readStringValue(configData, testConfig + ".MobileExecutionPlatform");

			if (!mobileExecutionPlatform.equals("JsonPathNotExist") || mobileExecutionPlatform.equals(""))
				testParameters.setMobileExecutionPlatform(MobileExecutionPlatform.valueOf(mobileExecutionPlatform));
			String deviceName = readStringValue(configData, testConfig + ".DeviceName");

			if (!deviceName.equals("JsonPathNotExist"))
				testParameters.setDeviceName(deviceName);
			String mobileOsVersion = readStringValue(configData, testConfig + ".MobileOSVersion");

			if (!mobileOsVersion.equals("JsonPathNotExist"))
				testParameters.setmobileOSVersion(mobileOsVersion);
			String platform = readStringValue(configData, testConfig + ".Platform");

			if (!platform.equals("JsonPathNotExist"))
				testParameters.setPlatform(Platform.valueOf(platform));
		} else
			System.out.println("Incorrect TestConfiguration ID");

	}

	/**
	 * Function to get the timeout from properties file
	 *
	 * @return timeOut
	 */
	public long getTimeoOutParameter(){
		long timeOut;
		if(System.getProperty("timeOut")!=null)
			timeOut = Long.parseLong(System.getProperty("timeOut"));
		else
			timeOut = Long.parseLong(properties.getProperty("Timeout"));
		return timeOut;
	}

	/**
	 * Function to get the application URL from properties file
	 *
	 * @return timeOut
	 */
	public String getURLParameter() {
		String env;
		String url=null;
		if(System.getProperty("environment")!=null)
			env = System.getProperty("environment").toUpperCase();
		else
			env = properties.getProperty("defaultEnvironment").toUpperCase();
		switch(env){

			case "QA":
				url = properties.getProperty("qaAppUrl");
				break;
			case "PROD":
				url = properties.getProperty("prodAppUrl");
				break;
			default:
				//throw new Exception("Invalid Environment provided");

		}
		return url;
	}


	/**
	 * Function to Parse value from json
	 *
	 * @return value of xpath
	 * @throws
	 */
	public static String readStringValue(String response, String keyName) {
		try {
			String queriedJson = JsonPath.read(response, keyName).toString();
			return queriedJson;
		} catch (Exception e) {
			return "JsonPathNotExist";
		}
	}

	/***
	 * Function to Parse value from json
	 * Unused Methods
	 * 
	 */
	@Before
	public void onTestSuccess(ITestResult result) {

	}

	@Before
	public void onTestFailure(ITestResult result) {

	}

	@Before
	public void onTestSkipped(ITestResult result) {

	}

	@Before
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Before
	public void onStart(ITestContext context) {

	}

	@Before
	public void onFinish(ITestContext context) {

	}


}
