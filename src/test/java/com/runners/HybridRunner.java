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
package com.runners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openqa.selenium.Platform;
import com.framework.components.ApplitoolsOperations;
import com.framework.components.FrameworkParameters;
import com.framework.components.IterationOptions;
import com.framework.components.ParallelExecution;
import com.framework.components.Settings;
import com.framework.components.ToolName;
import com.framework.data.ExcelDataAccessforxlsm;
import com.framework.report.ResultSummaryManager;
import com.framework.report.Util;
import com.framework.selenium.Browser;
import com.framework.selenium.ExecutionMode;
import com.framework.selenium.MobileExecutionPlatform;
import com.framework.selenium.SeleniumTestParameters;
import com.jayway.jsonpath.JsonPath;

/**
 * Class to manage the batch execution of test scripts within the framework
 * 
 * @author Qualitest
 */
public class HybridRunner {

	private FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
	private Properties properties;
	private Properties mobileProperties;
	private ResultSummaryManager resultSummaryManager = ResultSummaryManager.getInstance();
	public ApplitoolsOperations appli=new ApplitoolsOperations();
	
	/**
	 * The entry point of the test batch execution <br>
	 * Exits with a value of 0 if the test passes and 1 if the test fails

	 * @param args Command line arguments to the Allocator (Not applicable)
	 */
	public static void main(String[] args) {
		HybridRunner allocator = new HybridRunner();
		allocator.driveBatchExecution();
	}

	/**
	 * To initialize the Test batch execution
	 * 
	 */
	private void driveBatchExecution() {

		resultSummaryManager.setRelativePath();
		properties = Settings.getInstance();
		mobileProperties = Settings.getMobilePropertiesInstance();
		boolean appliToolsEnabled=Boolean.parseBoolean(properties.getProperty("AppliTools"));
		String runConfiguration;
		if (System.getProperty("RunConfiguration") != null) {
			runConfiguration = System.getProperty("RunConfiguration");
		} else {
			runConfiguration = properties.getProperty("RunConfiguration");
		}
		resultSummaryManager.initializeTestBatch(runConfiguration);

		int nThreads = Integer.parseInt(properties.getProperty("NumberOfThreads"));
		resultSummaryManager.initializeSummaryReport(nThreads);

		resultSummaryManager.setupErrorLog();
		resultSummaryManager.initializeExtentReport();
		if(appliToolsEnabled)
			appli.setUpAppliTools();
		int testBatchStatus = executeTestBatch(nThreads);

		resultSummaryManager.wrapUp(false);
		resultSummaryManager.flushExtentReport();
		resultSummaryManager.launchResultSummary();
		if(appliToolsEnabled)
			appli.getesultsSummary();
		System.exit(testBatchStatus);
	}

	/**
	 * To execute test in batch run
	 * @param nThreads - Number of threads
	 * @return
	 */
	private int executeTestBatch(int nThreads) {
		List<SeleniumTestParameters> testInstancesToRun = getRunInfo(frameworkParameters.getRunConfiguration());
		ExecutorService parallelExecutor = Executors.newFixedThreadPool(nThreads);
		ParallelExecution testRunner = null;

		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun.size(); currentTestInstance++) {
			testRunner = new ParallelExecution(testInstancesToRun.get(currentTestInstance));
			parallelExecutor.execute(testRunner);

			if (frameworkParameters.getStopExecution()) {
				break;
			}
		}
		parallelExecutor.shutdown();

		while (!parallelExecutor.isTerminated()) {
			long endTime = System.currentTimeMillis()+ (3 * 1000);
			while (System.currentTimeMillis()< endTime) {
			}
		}

		if (testRunner == null) {
			return 0;
		} else {
			return testRunner.getTestBatchStatus();
		}
	}

	/**
	 * To get the run information
	 * 
	 * @param sheetName
	 * @return
	 */
	public List<SeleniumTestParameters> getRunInfo(String sheetName) {
		ExcelDataAccessforxlsm runManagerAccess = new ExcelDataAccessforxlsm(
				frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src" + Util.getFileSeparator()
				+ "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties",
				"Run Manager");
		runManagerAccess.setDatasheetName(sheetName);
		List<SeleniumTestParameters> testInstancesToRun = new ArrayList<SeleniumTestParameters>();
		String[] keys = { "Execute", "TestScenario", "TestCase", "TestInstance", "Description", "IterationMode",
				"StartIteration", "EndIteration", "TestConfigurationID" };
		List<Map<String, String>> values = runManagerAccess.getValues(keys);
		for (int currentTestInstance = 0; currentTestInstance < values.size(); currentTestInstance++) {
			Map<String, String> row = values.get(currentTestInstance);
			String executeFlag = row.get("Execute");
			String tags[]=executeFlag.split(",");
			String tagstoExecute[]=properties.getProperty("ExecuteFlag").split(",");
			for (String tag :tags)
			{
				if(Arrays.asList(tagstoExecute)
						.contains(tag)) {
					//if (executeFlag.equalsIgnoreCase("Yes")) {
					String currentScenario = row.get("TestScenario");
					String currentTestcase = row.get("TestCase");
					SeleniumTestParameters testParameters = new SeleniumTestParameters(currentScenario, currentTestcase);
					testParameters.setCurrentTestDescription(row.get("Description"));
					testParameters.setCurrentTestInstance("Instance" + row.get("TestInstance"));

					String iterationMode = row.get("IterationMode");
					if (!iterationMode.equals("")) {
						testParameters.setIterationMode(IterationOptions.valueOf(iterationMode));
					} else {
						testParameters.setIterationMode(IterationOptions.RUN_ALL_ITERATIONS);
					}

					String startIteration = row.get("StartIteration");
					if (!startIteration.equals("")) {
						testParameters.setStartIteration(Integer.parseInt(startIteration));
					}
					String endIteration = row.get("EndIteration");
					if (!endIteration.equals("")) {
						testParameters.setEndIteration(Integer.parseInt(endIteration));
					}
					String testConfig = row.get("TestConfigurationID");
					if ("".equals(testConfig)) {
						testConfig = properties.getProperty("TestConfigurationID").toString();
					}
					setDefaultTestParameters(testParameters,testConfig);
					testInstancesToRun.add(testParameters);
					runManagerAccess.setDatasheetName(sheetName);
				}
			}
		}
		return testInstancesToRun;
	}


	/**
	 * Parse value from json
	 *
	 * @return value of xpath
	 * @throws
	 */
	private void setDefaultTestParameters(SeleniumTestParameters testParameters, String testConfig) {

		String propertiesPath = System.getProperty("user.dir") + Util.getFileSeparator() + "src"
				+ Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator()
				+ "properties" + Util.getFileSeparator();
		String configData = null;

		try {configData = new String(Files.readAllBytes(Paths.get(propertiesPath + "properties.json")));}
		catch (IOException e) {e.printStackTrace();}

		String executionMode = readStringValue(configData, testConfig + ".ExecutionMode");
		String toolName = readStringValue(configData, testConfig + ".ToolName");

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

		} else if (testConfig.contains("Appium") || testConfig.contains("Sauce")) {
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
	 * Parse value from json
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
	/**
	 * To get the Test configuration
	 * 
	 * @param runManagerAccess
	 * @param sheetName
	 * @param testConfigName
	 * @param testParameters
	 */
	@SuppressWarnings("unused")
	private void getTestConfigValues(ExcelDataAccessforxlsm runManagerAccess, String sheetName, String testConfigName,
			SeleniumTestParameters testParameters) {

		runManagerAccess.setDatasheetName(sheetName);
		int rowNum = runManagerAccess.getRowNum(testConfigName, 0, 1);

		String[] keys = { "TestConfigurationID", "ExecutionMode", "ToolName", "MobileExecutionPlatform",
				"MobileOSVersion", "DeviceName", "Browser", "BrowserVersion", "Platform"};
		Map<String, String> values = runManagerAccess.getValuesForSpecificRow(keys, rowNum);

		String executionMode = values.get("ExecutionMode");
		if (!"".equals(executionMode)) {
			testParameters.setExecutionMode(ExecutionMode.valueOf(executionMode));
		} else {
			testParameters.setExecutionMode(ExecutionMode.valueOf(properties.getProperty("DefaultExecutionMode")));
		}

		String toolName = values.get("ToolName");
		if (!"".equals(toolName)) {
			testParameters.setMobileToolName(ToolName.valueOf(toolName));
		} else {
			testParameters.setMobileToolName(ToolName.valueOf(mobileProperties.getProperty("DefaultMobileToolName")));
		}

		String executionPlatform = values.get("MobileExecutionPlatform");
		if (!"".equals(executionPlatform)) {
			testParameters.setMobileExecutionPlatform(MobileExecutionPlatform.valueOf(executionPlatform));
		} else {
			testParameters.setMobileExecutionPlatform(
					MobileExecutionPlatform.valueOf(mobileProperties.getProperty("DefaultMobileExecutionPlatform")));
		}

		String mobileOSVersion = values.get("MobileOSVersion");
		if (!"".equals(mobileOSVersion)) {
			testParameters.setmobileOSVersion(mobileOSVersion);
		}

		String deviceName = values.get("DeviceName");
		if (!"".equals(deviceName)) {
			testParameters.setDeviceName(deviceName);
		}

		String browser = values.get("Browser");
		if (!"".equals(browser)) {
			testParameters.setBrowser(Browser.valueOf(browser));
		} else {
			testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));
		}

		String browserVersion = values.get("BrowserVersion");
		if (!"".equals(browserVersion)) {
			testParameters.setBrowserVersion(browserVersion);
		}

		String platform = values.get("Platform");
		if (!"".equals(platform)) {
			testParameters.setPlatform(Platform.valueOf(platform));
		} else {
			testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));
		}

	}

}