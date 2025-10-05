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
package com.framework.components;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.framework.selenium.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.data.AccessDatabase;
import com.framework.data.CsvReader;
import com.framework.data.FrameworkDataTable;
import com.framework.data.ExcelDataAccess;
import com.framework.data.JsonData;
import com.framework.report.ExtentReport;
import com.framework.report.ReportSettings;
import com.framework.report.ReportTheme;
import com.framework.report.ReportThemeFactory;
import com.framework.report.ReportThemeFactory.Theme;
import com.framework.report.Status;
import com.framework.report.TimeStamp;
import com.framework.report.Util;
import com.framework.reusable.WebReusableComponents;

import io.appium.java_client.AppiumDriver;


/**
 * Driver script class which encapsulates the core logic of the framework
 * 
 * @author Qualitest
 */
public class DriverScript {
	private List<String> businessFlowData;
	private int currentIteration;
	private Date startTime, endTime;
	private String executionTime;

	private FrameworkDataTable dataTable;
	private ReportSettings reportSettings;
	private SeleniumReport report;
	private ModularTestCase testCase;
	private CustomDriver driver;
	private ScriptHelper scriptHelper;
	private Properties properties;
	private Properties mobileProperties;
	private final FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
	private int  currentSubIteration;
	private Boolean linkScreenshotsToTestLog = true;
	private final SeleniumTestParameters testParameters;
	private String reportPath;
	public ApplitoolsOperations appli=new ApplitoolsOperations();
	public ExtentReport rpt = new ExtentReport();
	private RestAssuredUtils apiDriver = new RestAssuredUtils();



	/**
	 * DriverScript constructor
	 * 
	 * @param testParameters A {@link SeleniumTestParameters} object
	 */
	public DriverScript(SeleniumTestParameters testParameters) {
		this.testParameters = testParameters;
	}

	/**
	 * Function to configure the linking of screenshots to the corresponding test
	 * log
	 * 
	 * @param linkScreenshotsToTestLog Boolean variable indicating whether
	 *                                 screenshots should be linked to the
	 *                                 corresponding test log
	 */
	public void setLinkScreenshotsToTestLog(Boolean linkScreenshotsToTestLog) {
		this.linkScreenshotsToTestLog = linkScreenshotsToTestLog;
	}

	/**
	 * Function to get the name of the test report
	 * 
	 * @return The test report name
	 */
	public String getReportName() {
		return reportSettings.getReportName();
	}

	/**
	 * Function to get the status of the test case executed
	 * 
	 * @return The test status
	 */
	public String getTestStatus() {
		return report.getTestStatus();
	}

	/**
	 * Function to get the description of any failure that may occur during the
	 * script execution
	 * 
	 * @return The failure description (relevant only if the test fails)
	 */
	public String getFailureDescription() {
		return report.getFailureDescription();
	}

	/**
	 * Function to get the execution time for the test case
	 * 
	 * @return The test execution time
	 */
	public String getExecutionTime() {
		return executionTime;
	}

	/**
	 * Function to execute the given test case
	 */
	public void driveTestExecution() {
		startUp();
		initializeTestIterations();
		initializeWebDriver();
		initializeTestReport();
		initializeDatatable();
		executeFramework();
		quitWebDriver();
		wrapUp();
	}
	/**
	 * Function to execute the Framework based on the defined Execution Approach
	 */
	@SuppressWarnings("static-access")
	private void executeFramework() {
		if(properties.getProperty("AppliTools").equalsIgnoreCase("TRUE")&&
				!testParameters.getExecutionMode().toString().equalsIgnoreCase("SAUCELABS"))
		{
			appli.invokeAppliTools(testParameters.getCurrentTestcase());
//			appli.openEyes(driver.getWebDriver());
			
		}
		if(properties.getProperty("ExecutionApproach").equalsIgnoreCase("KEYWORD"))
			initializeTestScript();
		else
		{
			initializeTestCase();
			rpt.createTestcase(testParameters.getCurrentTestcase());
			testCase.setUp();
		}
		try {
			executeTestIterations();
		} catch (FrameworkException fx) {
			exceptionHandler(fx, fx.getErrorName());
		} catch (Exception ex) {
			exceptionHandler(ex, "Error");
		} finally {
			try {
				if(properties.getProperty("ExecutionApproach").equalsIgnoreCase("MODULAR"))
					testCase.tearDown(); // tearDown will ALWAYS be called
			} catch (Exception e) {
				exceptionHandler(e, "Error");
			}
		}

	}

	/**
	 * Function to initialize the test script under execution
	 */
	private void initializeTestScript() {

		scriptHelper = new ScriptHelper(dataTable, report,driver,apiDriver);
		if (!isAPITest())
		{
			driver.setReport(report);

		}
		apiDriver.setReport(report);
		initializeBusinessFlow();
	}

	/**
	 * Function to initialize the Business Flow received from test Scripts
	 */
	private void initializeBusinessFlow() {
		String encryptedBusinessFlowAccess = WhitelistingPath.cleanStringForFilePath(
				frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator()
				+ "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "datatables");
		ExcelDataAccess businessFlowAccess = new ExcelDataAccess(encryptedBusinessFlowAccess,
				testParameters.getCurrentScenario());
		businessFlowAccess.setDatasheetName("Business_Flow");

		int rowNum = businessFlowAccess.getRowNum(testParameters.getCurrentTestcase(), 0);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \"" + testParameters.getCurrentTestcase()
			+ "\" is not found in the Business Flow sheet!");
		}

		String dataValue;
		businessFlowData = new ArrayList<String>();
		int currentColumnNum = 1;
		while (true) {
			dataValue = businessFlowAccess.getValue(rowNum, currentColumnNum);
			if ("".equals(dataValue)) {
				break;
			}
			businessFlowData.add(dataValue);
			currentColumnNum++;
		}

		if (businessFlowData.isEmpty()) {
			throw new FrameworkException(
					"No business flow found against the test case \"" + testParameters.getCurrentTestcase() + "\"");
		}
	}

	/**
	 * Function to get current instance and get the mobile properties
	 */
	private void startUp() {
		startTime = WebReusableComponents.getCurrentTime();

		properties = Settings.getInstance();
		mobileProperties = Settings.getMobilePropertiesInstance();

		setDefaultTestParameters();
	}

	/**
	 * Function to set the default Test Parameters
	 */

	private void setDefaultTestParameters() {
		if (testParameters.getIterationMode() == null) {
			testParameters.setIterationMode(IterationOptions.RUN_ALL_ITERATIONS);
		}

		testParameters.setInstallApplication(
				Boolean.parseBoolean(mobileProperties.getProperty("InstallApplicationInDevice")));	 
	}

	/**
	 * Function to initialize the Test Iterations
	 */

	private void initializeTestIterations() {
		switch (testParameters.getIterationMode()) {
		case RUN_ALL_ITERATIONS:
			int nIterations = getNumberOfIterations();
			testParameters.setEndIteration(nIterations);

			currentIteration = 1;
			break;

		case RUN_ONE_ITERATION_ONLY:
			currentIteration = 1;
			break;

		case RUN_RANGE_OF_ITERATIONS:
			if (testParameters.getStartIteration() > testParameters.getEndIteration()) {
				throw new FrameworkException("Error", "StartIteration cannot be greater than EndIteration!");
			}
			currentIteration = testParameters.getStartIteration();
			break;

		default:
			throw new FrameworkException("Unhandled Iteration Mode!");
		}
	}

	/**
	 * Function to get the number of Test Iterations
	 */

	private int getNumberOfIterations() {
		String encryptedDatatablePath = WhitelistingPath.cleanStringForFilePath(
				frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator()
				+ "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "datatables");
		String datatablePath = encryptedDatatablePath;
		ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath, testParameters.getCurrentScenario());
		testDataAccess.setDatasheetName(properties.getProperty("DefaultDataSheet"));

		return testDataAccess.getRowCount(testParameters.getCurrentTestcase(), 0);

	}
	/**
	 * Function to initialize the Custom Driver for Web, Mobile and Windows
	 */
	public CustomDriver initializeWebDriver() {

		switch (testParameters.getExecutionMode()) {

		case LOCAL:
			WebDriver webDriver = WebDriverFactory.getWebDriver(testParameters);
			driver = new CustomDriver(webDriver);
			driver.setTestParameters(testParameters);
			maximizeWindow();
			break;

		case GRID:
			WebDriver remoteGridDriver = WebDriverFactory.getRemoteWebDriver(testParameters.getBrowser(),
					testParameters.getBrowserVersion(), testParameters.getPlatform(),
					properties.getProperty("RemoteUrl"));
			driver = new CustomDriver(remoteGridDriver);
			driver.setTestParameters(testParameters);
			maximizeWindow();
			break;

		case MOBILE:
			WebDriver appiumDriver = AppiumDriverFactory.getAppiumDriver(testParameters.getMobileExecutionPlatform(),
					testParameters.getDeviceName(), testParameters.getMobileOSVersion(),
					testParameters.shouldInstallApplication(), mobileProperties.getProperty("AppiumURL"));
			driver = new CustomDriver(appiumDriver);
			driver.setTestParameters(testParameters);

			break;
		case SAUCELABS:
		case BROWSERSTACK:
		case AWSDEVICEFARM:
		case LAMBDATEST:
		case PERFECTO:
		case EXPERITEST:
			if (testParameters.getMobileToolName().equals(ToolName.APPIUM)) {
				@SuppressWarnings("rawtypes")
				AppiumDriver appiumSauceDriver = CloudPlatformMobileDriverFactory.getSauceAppiumDriver(testParameters.getMobileExecutionPlatform(),
						testParameters.getDeviceName(),testParameters,testParameters.getExecutionMode());
				driver = new CustomDriver(appiumSauceDriver);
				driver.setTestParameters(testParameters);

			} else if (testParameters.getMobileToolName().equals(ToolName.REMOTE_WEBDRIVER)) {
				WebDriver remoteSauceDriver=null;
				if (properties.getProperty("SauceVisualRunner").equalsIgnoreCase("FALSE")) {
					remoteSauceDriver =CloudPlatformWebDriverFactory.getRemoteWebDriver(testParameters.getBrowser().toString(),testParameters.getBrowserVersion(),
							testParameters.getPlatform().toString(),testParameters.getCurrentTestcase(),testParameters.getExecutionMode(),testParameters);
				} else {
					remoteSauceDriver =CloudPlatformWebDriverFactory.getRemoteVisualWebDriver(testParameters.getBrowser().toString(),testParameters.getBrowserVersion(),
							testParameters.getPlatform().toString(),testParameters.getCurrentTestcase());
					CloudPlatformWebDriverFactory.intializeTestscreener(testParameters.getCurrentTestcase().toString());
				}
				driver = new CustomDriver(remoteSauceDriver);
				driver.setTestParameters(testParameters);
			}

			break;

		case API:
			break;

		case WINDOWS:
				WebDriver windowsDriver = WindowsDriverFactory.getWindowsDriver(testParameters.getExecutionMode());
				driver = new CustomDriver(windowsDriver);
				driver.setTestParameters(testParameters);
			break;


		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}
		return driver;
	}

	/**
	 * Function to maximize the application window
	 */

	private void maximizeWindow() {
		driver.manage().window().maximize();
	}

	/**
	 * Function to Initialize the Test Report
	 */
	private void initializeTestReport() {
		initializeReportSettings();
		ReportTheme reportTheme = ReportThemeFactory
				.getReportsTheme(Theme.valueOf(properties.getProperty("ReportsTheme")));

		report = new SeleniumReport(reportSettings, reportTheme, testParameters);

		report.initialize();
		report.setDriver(driver);
		report.initializeTestLog();
		createTestLogHeader();
	}

	/**
	 * Function to Initialize the Report Settings
	 */
	private void initializeReportSettings() {
		if (System.getProperty("ReportPath") != null) {
			reportPath = System.getProperty("ReportPath");
		} else {
			reportPath = TimeStamp.getInstance();
		}

		reportSettings = new ReportSettings(reportPath, testParameters.getCurrentScenario() + "_"
				+ testParameters.getCurrentTestcase() + "_" + testParameters.getCurrentTestInstance());

		reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));
		reportSettings.setLogLevel(Integer.parseInt(properties.getProperty("LogLevel")));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.setGenerateHtmlReports(Boolean.parseBoolean(properties.getProperty("HtmlReport")));
		reportSettings
		.setTakeScreenshotFailedStep(Boolean.parseBoolean(properties.getProperty("TakeScreenshotFailedStep")));
		reportSettings
		.setTakeScreenshotPassedStep(Boolean.parseBoolean(properties.getProperty("TakeScreenshotPassedStep")));
		if (isAPITest()) {
			reportSettings.setTakeScreenshotFailedStep(false);
			reportSettings.setTakeScreenshotPassedStep(false);
		}
		reportSettings.setAPIAutomation(isAPITest());
		reportSettings.setisMobileExecution(isMobileAutomation());

		reportSettings.setLinkScreenshotsToTestLog(this.linkScreenshotsToTestLog);
	}
	/**
	 * Function to create Test Log Header with execution results
	 */
	private void createTestLogHeader() {
		report.addTestLogHeading(reportSettings.getProjectName() + " - " + reportSettings.getReportName()
		+ " Automation Execution Results");
		report.addTestLogSubHeading("Date & Time",
				": " + WebReusableComponents.getFormattedTime(startTime, properties.getProperty("DateFormatString")), "Iteration Mode",
				": " + testParameters.getIterationMode());
		report.addTestLogSubHeading("Start Iteration", ": " + testParameters.getStartIteration(), "End Iteration",
				": " + testParameters.getEndIteration());

		switch (testParameters.getExecutionMode()) {

		case LOCAL:
			report.addTestLogSubHeading("Browser/Platform", ": " + testParameters.getBrowserAndPlatform(),
					"Execution on", ": " + "Local Machine");
			break;

		case GRID:
			report.addTestLogSubHeading("Browser/Platform", ": " + testParameters.getBrowserAndPlatform(),
					"Executed on", ": " + "Grid @ " + properties.getProperty("RemoteUrl"));
			break;

		case MOBILE:
			report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
					"Execution Platform", ": " + testParameters.getMobileExecutionPlatform());
			report.addTestLogSubHeading("Tool Used", ": " + testParameters.getMobileToolName(), "Device Name/ID",
					": " + testParameters.getDeviceName());
			break;


		case BROWSERSTACK:
		case AWSDEVICEFARM:
		case LAMBDATEST:
		case SAUCELABS:
		case PERFECTO:
		case EXPERITEST:
			report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
					"Execution Platform", ": " + testParameters.getBrowserAndPlatform());

			break;
		case API:
		case WINDOWS:
			report.addTestLogSubHeading("Execution Mode", ": " + testParameters.getExecutionMode(),
					"Execution Platform", ": " + "N/A");

			break;

		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}

		report.addTestLogTableHeadings();
	}
	/**
	 * Function to initialize the Data table like Excel, JSON, AccessDB and CSV
	 */
	private synchronized void initializeDatatable() {

		String datatablePath = frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src"
				+ Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator()
				+ "datatables";

		System.setProperty("reportPath", reportPath);

		switch(properties.getProperty("TestData").toString()) {

		case "EXCEL":
			String encryptedDatatablePath = WhitelistingPath.cleanStringForFilePath(
					datatablePath + Util.getFileSeparator() + testParameters.getCurrentScenario() + ".xls");

			String runTimeDatatablePath;
			Boolean includeTestDataInReport = Boolean.parseBoolean(properties.getProperty("IncludeTestDataInReport"));
			if (includeTestDataInReport) {
				runTimeDatatablePath = reportPath + Util.getFileSeparator() + "datatables";
				String encryptedRunTimeDatatablePath = WhitelistingPath.cleanStringForFilePath(
						runTimeDatatablePath + Util.getFileSeparator() + testParameters.getCurrentScenario() + ".xls");

				File runTimeDatatable = new File(encryptedRunTimeDatatablePath);
				if (!runTimeDatatable.exists()) {
					File datatable = new File(encryptedDatatablePath);

					try {
						FileUtils.copyFile(datatable, runTimeDatatable);
					} catch (IOException e) {
						e.printStackTrace();
						throw new FrameworkException(
								"Error in creating run-time datatable: Copying the datatable failed...");
					}
				}

				String encryptedRunTimeCommonDatatable = WhitelistingPath
						.cleanStringForFilePath(runTimeDatatablePath + Util.getFileSeparator() + "Common Testdata.xls");
				File runTimeCommonDatatable = new File(encryptedRunTimeCommonDatatable);
				if (!runTimeCommonDatatable.exists()) {
					String encryptedCommonDatatable = WhitelistingPath
							.cleanStringForFilePath(datatablePath + Util.getFileSeparator() + "Common Testdata.xls");
					File commonDatatable = new File(encryptedCommonDatatable);

					try {
						FileUtils.copyFile(commonDatatable, runTimeCommonDatatable);
					} catch (IOException e) {
						e.printStackTrace();
						throw new FrameworkException(
								"Error in creating run-time datatable: Copying the common datatable failed...");
					}
				}
			} else {
				runTimeDatatablePath = datatablePath;
			}

			dataTable = new FrameworkDataTable(runTimeDatatablePath, testParameters.getCurrentScenario());
			dataTable.setDataReferenceIdentifier(properties.getProperty("DataReferenceIdentifier"));
			dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration);
			dataTable.setCurrentTestname(testParameters.getCurrentTestcase());
			ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath, testParameters.getCurrentScenario());

			dataTable.commonData = testDataAccess.readExcelValue(testParameters.getCurrentTestcase().toString(),currentIteration);

			break;

		case "JSON":
			JsonData jsdata = new JsonData();

			dataTable = new FrameworkDataTable(datatablePath, testParameters.getCurrentScenario());

			dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration);
			dataTable.setCurrentTestname(testParameters.getCurrentTestcase());
			dataTable.commonData = jsdata.readJson(datatablePath, 
					testParameters.getCurrentScenario(),testParameters.getCurrentTestcase());


			break;

		case "CSV":
			CsvReader csvRead = new CsvReader();

			dataTable = new FrameworkDataTable(datatablePath, testParameters.getCurrentScenario());

			dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration);
			dataTable.setCurrentTestname(testParameters.getCurrentTestcase());
			dataTable.csvData = csvRead.readCSVasHashMap(datatablePath, 
					testParameters.getCurrentScenario(),testParameters.getCurrentTestcase());
			break;

		case "ACCESSDB" :

			AccessDatabase accdb = new AccessDatabase();

			dataTable = new FrameworkDataTable(datatablePath, testParameters.getCurrentScenario());

			dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration);
			dataTable.setCurrentTestname(testParameters.getCurrentTestcase());
			try {

				dataTable.commonData = accdb.executeAccessData(datatablePath, 
						testParameters.getCurrentScenario(),testParameters.getCurrentTestcase());

			} catch (ClassNotFoundException | InterruptedException | SQLException e) {
				e.printStackTrace();
			}

			break;

		default:

			throw new FrameworkException("Invalid parameters provided for datasheet in Global Settings.properties file."
					+ " Please provide the valid data types");

		}

	}

	/**
	 * Function to add the exception logs
	 */
	private void addExceptionLogs(String stepName, String stepDescription, Status stepStatus) {
		if(isAPITest()) {
			report.updateTestLog(stepName, stepDescription, "N/A", stepStatus);
		} else {
			report.updateTestLog(stepName, stepDescription, stepStatus);
		}
	}
	/**
	 * Function to handle the exception
	 */
	private void exceptionHandler(Exception ex, String exceptionName) {
		String exceptionDescription = ex.getMessage();
		if (exceptionDescription == null) {
			exceptionDescription = ex.toString();
		}

		if (ex.getCause() != null) {
			addExceptionLogs(exceptionName, exceptionDescription + " <b>Caused by: </b>" + ex.getCause(),
					Status.FAIL);
		} else {
			addExceptionLogs(exceptionName, exceptionDescription, Status.FAIL);
		}

		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		String stackTrace = stringWriter.toString();
		report.updateTestLog("Exception stack trace", stackTrace, Status.DEBUG);

		if (frameworkParameters.getStopExecution()) {
			addExceptionLogs("Framework Info", "Test execution terminated by user! All subsequent tests aborted...",
					Status.FAIL);		
			currentIteration = testParameters.getEndIteration();
		} else {
			OnError onError = OnError.valueOf(properties.getProperty("OnError"));
			switch (onError) {
			case NEXT_ITERATION:
				addExceptionLogs("Framework Info", "Test case iteration terminated by user! Proceeding to next iteration (if applicable)...",
						Status.FAIL);
				break;

			case NEXT_TESTCASE:
				addExceptionLogs("Framework Info",
						"Test case terminated by user! Proceeding to next test case (if applicable)...", Status.FAIL);
				currentIteration = testParameters.getEndIteration();
				break;

			case STOP:
				frameworkParameters.setStopExecution(true);
				addExceptionLogs("Framework Info", "Test execution terminated by user! All subsequent tests aborted...",
						Status.FAIL);
				currentIteration = testParameters.getEndIteration();
				break;

			default:
				throw new FrameworkException("Unhandled OnError option!");
			}
		}
	}
	/**
	 * Function to quit the different driver
	 */
	private void quitWebDriver() {
		switch (testParameters.getExecutionMode()) {

		case BROWSERSTACK:
		case AWSDEVICEFARM:
		case LAMBDATEST:
		case PERFECTO:
		case EXPERITEST:
		case LOCAL:
		case GRID:
		case MOBILE:
		case WINDOWS:
			if(properties.getProperty("AppliTools").equalsIgnoreCase("TRUE")) {
				appli.closeAppliTools();
			}

			driver.quit();
			break;

		case SAUCELABS:
			if(testParameters.getMobileToolName().equals(ToolName.REMOTE_WEBDRIVER) && properties.getProperty("SauceVisualRunner").equalsIgnoreCase("FALSE")) {
				try {
					report.updateTestLog("SAUCE Results Link", CloudPlatformWebDriverFactory.getShareableLink((RemoteWebDriver) driver.getWebDriver()), Status.PASS);
					CloudPlatformWebDriverFactory.quitsession(getTestStatus().toLowerCase());
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			} else  {
				try {
					report.updateTestLog("SAUCE Results Link", CloudPlatformWebDriverFactory.getShareableLink((RemoteWebDriver) driver.getWebDriver()), Status.PASS);
					((JavascriptExecutor) driver.getWebDriver()).executeScript("sauce:job-result=" + getTestStatus().toLowerCase());
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println("Sauce - release driver");
					driver.quit();
				}
			}
			break;

		case API:
			break;

		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}

	}
	/**
	 * Function to wrap up the test
	 */
	private void wrapUp() {
		endTime = WebReusableComponents.getCurrentTime();
		closeTestReport();
	}
	/**
	 * Function to close the test report
	 */
	private void closeTestReport() {
		executionTime = WebReusableComponents.getTimeDifference(startTime, endTime);
		report.addTestLogFooter(executionTime);
	}
	/**
	 * Function to check the mobile automation is Appium
	 */
	private boolean isMobileAutomation() {
		boolean isMobileAutomation = false;
		if(testParameters.getMobileToolName()!=null) {
			if (testParameters.getMobileToolName().equals(ToolName.APPIUM)) {
				isMobileAutomation = true;
			}
		}
		return isMobileAutomation;
	}
	/**
	 * Function to check if the test is API
	 */
	private boolean isAPITest() {
		boolean isAPI = false;
		if (testParameters.getExecutionMode().equals(ExecutionMode.API)) {
			isAPI = true;
		}
		return isAPI;
	}
	/**
	 * Function to check if the test is Windows Application
	 */
	private boolean isWindowsTest() {
		boolean isWindows = false;
		if (testParameters.getExecutionMode().equals(ExecutionMode.WINDOWS)) {
			isWindows = true;
		}
		return isWindows;
	}
	/**
	 * Function to execute Test Iterations
	 */
	private void executeTestIterations() {
		while (currentIteration <= testParameters.getEndIteration()) {
			report.addTestLogSection("Iteration: " + Integer.toString(currentIteration));

			try {
				if(properties.getProperty("ExecutionApproach").equalsIgnoreCase("KEYWORD")) {
					rpt.createTestcase(testParameters.getCurrentTestcase());
					executeTestcase(businessFlowData);
				} else {
					rpt.createNode("Iteration: " + Integer.toString(currentIteration));
					testCase.executeTest();
				}
			} catch (FrameworkException fx) {
				exceptionHandler(fx, fx.getErrorName());
			} catch (Exception ex) {
				exceptionHandler(ex, "Error");
			}

			currentIteration++;
			dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration);
		}
	}
	/**
	 * Function to execute Test case with business flow
	 */
	private void executeTestcase(List<String> businessFlowData)
			throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
		Map<String, Integer> keywordDirectory = new HashMap<String, Integer>();

		for (int currentKeywordNum = 0; currentKeywordNum < businessFlowData.size(); currentKeywordNum++) {
			String[] currentFlowData = businessFlowData.get(currentKeywordNum).split(",");
			String currentKeyword = currentFlowData[0];

			int nKeywordIterations;
			if (currentFlowData.length > 1) {
				nKeywordIterations = Integer.parseInt(currentFlowData[1]);
			} else {
				nKeywordIterations = 1;
			}

			for (int currentKeywordIteration = 0; currentKeywordIteration < nKeywordIterations; currentKeywordIteration++) {
				if (keywordDirectory.containsKey(currentKeyword)) {
					keywordDirectory.put(currentKeyword, keywordDirectory.get(currentKeyword) + 1);
				} else {
					keywordDirectory.put(currentKeyword, 1);
				}
				currentSubIteration = keywordDirectory.get(currentKeyword);

				dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration, currentSubIteration);

				if (currentSubIteration > 1) {
					report.addTestLogSubSection(currentKeyword + " (Sub-Iteration: " + currentSubIteration + ")");
				} else {
					report.addTestLogSubSection(currentKeyword);
				}

				invokeBusinessComponent(currentKeyword);
			}
		}
	}
	/**
	 * Function to invoke the business component with current keyword
	 */
	private void invokeBusinessComponent(String currentKeyword)
			throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {
		Boolean isMethodFound = false;
		final String CLASS_FILE_EXTENSION = ".class";
		String encryptedBusinessComponentsPath = WhitelistingPath
				.cleanStringForFilePath(frameworkParameters.getRelativePath() + Util.getFileSeparator() + "target"
						+ Util.getFileSeparator() + "test-classes" + Util.getFileSeparator() + "com/keyword/steps/");
		File packageDirectory=new File(encryptedBusinessComponentsPath);
		File[] packageFiles = packageDirectory.listFiles();
		String packageName = "com.keyword.steps";
		for (int i = 0; i < packageFiles.length; i++) {
			File packageFile = packageFiles[i];
			String fileName = packageFile.getName();		
			if (fileName.endsWith(CLASS_FILE_EXTENSION)) {
				String className = fileName.substring(0, fileName.length() - CLASS_FILE_EXTENSION.length());

				String encryptedReusableComponents = WhitelistingPath
						.cleanStringForFilePath(packageName + "." + className);
				Class<?> reusableComponents = Class.forName(encryptedReusableComponents);
				Method executeComponent;
				try {
					currentKeyword = currentKeyword.substring(0, 1).toLowerCase() + currentKeyword.substring(1);
					executeComponent = reusableComponents.getMethod(currentKeyword, (Class<?>[]) null);
				} catch (NoSuchMethodException ex) {
					continue;
				}
				isMethodFound = true;
				Constructor<?> ctor = reusableComponents.getDeclaredConstructors()[0];
				Object businessComponent = ctor.newInstance(scriptHelper);
				rpt.createNode(currentKeyword);
				executeComponent.invoke(businessComponent, (Object[]) null);
				break;
			}
		}
		if (!isMethodFound) {
			throw new FrameworkException("Keyword " + currentKeyword + " not found within any class "
					+ "inside the businesscomponents package");
		}
	}
	/**
	 * Function to initialize the test case
	 */
	private void initializeTestCase() {
		scriptHelper = new ScriptHelper(dataTable, report,driver,apiDriver);
		if (!isAPITest()) {
			driver.setReport(report);
		}
		apiDriver.setReport(report);
		testCase = getTestCaseInstance();
		testCase.initialize(scriptHelper);
	}
	/**
	 * Function to get the Test case instance
	 */
	private ModularTestCase getTestCaseInstance() {
		Class<?> testScriptClass;
		try {
			testScriptClass = Class.forName(
					"com.modular.steps."+ testParameters.getCurrentTestcase());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified test case is not found!");
		}

		try {
			return (ModularTestCase) testScriptClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException("Error while instantiating the specified test script");
		}
	}

}