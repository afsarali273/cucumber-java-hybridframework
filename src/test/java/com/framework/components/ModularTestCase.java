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

import java.util.Properties;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.framework.data.FrameworkDataTable;
import com.framework.report.ResultSummaryManager;
import com.framework.selenium.CustomDriver;
import com.framework.selenium.SeleniumReport;
import com.framework.selenium.SeleniumTestParameters;
/**
 * Abstract base class for all the test cases to be automated
 * 
 * @author Qualitest
 */
public abstract class ModularTestCase {

	/**
	 * The current scenario
	 */
	protected String currentScenario;
	/**
	 * The current test case
	 */
	protected String currentTestcase;

	private ResultSummaryManager resultSummaryManager = ResultSummaryManager.getInstance();

	/**
	 * The {@link FrameworkDataTable} object (passed from the Driver script)
	 */
	protected FrameworkDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the Driver script)
	 */
	protected SeleniumReport report;
	/**
	 * The {@link CustomDriver} object (passed from the Driver script)
	 */
	protected CustomDriver driver;

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library
	 * from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
	
	public ApplitoolsOperations appli=new ApplitoolsOperations();
	
	boolean appliToolsEnabled;
	/**
	 * The {@link } object
	 */
	protected RestAssuredUtils apiDriver;
	/**
	 * Function to initialize the various objects that may need to be used with a
	 * test script
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public void initialize(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		this.driver = scriptHelper.getcustomDriver();
		this.apiDriver = scriptHelper.getApiDriver();
		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
	}

	/**
	 * Function to do the required framework setup activities before executing the
	 * overall test suite
	 * 
	 * @param testContext The TestNG {@link ITestContext} of the current test suite
	 */
	@BeforeSuite
	public void setUpTestSuite(ITestContext testContext) {
		properties = Settings.getInstance();
		 appliToolsEnabled=Boolean.parseBoolean(properties.getProperty("AppliTools"));

		resultSummaryManager.setRelativePath();
		resultSummaryManager.initializeTestBatch(testContext.getSuite().getName());

		int nThreads;
		if ("false".equalsIgnoreCase(testContext.getSuite().getParallel())) {
			nThreads = 1;
		} else {
			nThreads = testContext.getCurrentXmlTest().getThreadCount();
		}

		// Note: Separate threads may be spawned through usage of DataProvider
		// testContext.getSuite().getXmlSuite().getDataProviderThreadCount();
		// This will be at test case level (multiple instances on same test case
		// in parallel)
		// This level of threading will not be reflected in the summary report

		resultSummaryManager.initializeSummaryReport(nThreads);
		resultSummaryManager.initializeExtentReport();
		if(appliToolsEnabled)
			appli.setUpAppliTools();
		resultSummaryManager.setupErrorLog();
	}

	/**
	 * Function to do the required framework setup activities before executing each
	 * test case
	 */
	@BeforeMethod
	public void setUpTestRunner() {
		if (frameworkParameters.getStopExecution()) {
			tearDownTestSuite();
			throw new SkipException("Aborting all subsequent tests!");
		}
	}

	/**
	 * Function to handle any pre-requisite steps required before beginning the test
	 * case execution <br>
	 * <u>Note</u>: This function can be left blank if not applicable
	 */
	public abstract void setUp();

	/**
	 * Function to handle the core test steps required as part of the test case
	 */
	public abstract void executeTest();

	/**
	 * Function to handle any clean-up steps required after completing the test case
	 * execution <br>
	 * <u>Note</u>: This function can be left blank if not applicable
	 */
	public abstract void tearDown();

	/**
	 * Function to do the required framework teardown activities after executing
	 * each test case
	 * 
	 * @param testParameters The {@link SeleniumTestParameters} object passed from
	 *                       the test case
	 * @param driverScript   The {@link DriverScript} object passed from the test
	 *                       case
	 */
	protected synchronized void tearDownTestRunner(SeleniumTestParameters testParameters, DriverScript driverScript) {

		String testReportName = driverScript.getReportName();
		String executionTime = driverScript.getExecutionTime();
		String testStatus = driverScript.getTestStatus();

		resultSummaryManager.updateResultSummary(testParameters, testReportName, executionTime, testStatus);
	
		if ("Failed".equalsIgnoreCase(testStatus)) {
			Assert.fail(driverScript.getFailureDescription());
		}
	}

	/**
	 * Function to do the required framework teardown activities after executing the
	 * overall test suite
	 */
	@AfterSuite
	public void tearDownTestSuite() {
		resultSummaryManager.wrapUp(false);
		resultSummaryManager.flushExtentReport();
		if(appliToolsEnabled)
			appli.getesultsSummary();
		resultSummaryManager.launchResultSummary();
	}

}