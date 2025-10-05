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
package com.cucumber.steps;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.framework.components.ApplitoolsOperations;
import com.framework.components.Settings;
import com.framework.cucumber.DriverManager;
import com.framework.cucumber.TestHarness;
import com.framework.selenium.CustomDriver;
import com.framework.selenium.CloudPlatformWebDriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks {
	static CustomDriver driver ;
	static Integer i=0;
	private TestHarness testHarness;
	public ApplitoolsOperations appli = new ApplitoolsOperations();
	Properties properties = Settings.getInstance();

	/**
	 * Method to initialize driver
	 * 
	 * @param scenario
	 */
	@Before
	public void setUp(Scenario scenario) {
		testHarness = new TestHarness();
		DriverManager.getTestParameters().setScenario(scenario);
		testHarness.invokeDriver(scenario);
		if(!(DriverManager.getTestParameters().getExecutionMode().toString().equalsIgnoreCase("SAUCELABS")))
		{
			appli.invokeAppliTools(scenario.getName()+scenario.getLine());
			appli.openEyes(DriverManager.getWebDriver());

		}
	}

	/**
	 * To set execution approach as cucumber in property file
	 */
	@Before
	public void setExecutionApproach() {
		properties.setProperty("ExecutionApproach", "CUCUMBER");
	}

	/**
	 * Method to take screenshot after each step
	 * @param scenario
	 */
	@AfterStep
	public void addScreenshot(Scenario scenario) {

		if (!DriverManager.getTestParameters().isAPIExecution()&&!(DriverManager.getTestParameters().getExecutionMode().toString().equalsIgnoreCase("SAUCELABS"))) {
			if (Boolean.parseBoolean(properties.getProperty("TakeScreenshotPassedStep"))) {
				if (!DriverManager.getTestParameters().isMobileExecution()) {
					if (!DriverManager.getTestParameters().isWindowsExecution()) {
					final byte[] screenshot = ((TakesScreenshot) DriverManager.getWebDriver())
							.getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/png", "image");
				} else{
						final byte[] screenshot = ((TakesScreenshot) DriverManager.getWindowsDriver())
								.getScreenshotAs(OutputType.BYTES);
						scenario.attach(screenshot, "image/png", "image");
					}
				}else {
					final byte[] screenshot = ((TakesScreenshot) DriverManager.getAppiumDriver())
							.getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/png", "image");
				}
			} else {
				if (!DriverManager.getTestParameters().isMobileExecution()) {
					if (!DriverManager.getTestParameters().isMobileExecution()) {
					if (scenario.isFailed()) {
						final byte[] screenshot = ((TakesScreenshot) DriverManager.getWebDriver())
								.getScreenshotAs(OutputType.BYTES);
						scenario.attach(screenshot, "image/png", "image");
					}
				} else {

					if (scenario.isFailed()) {
						final byte[] screenshot = ((TakesScreenshot) DriverManager.getWindowsDriver())
								.getScreenshotAs(OutputType.BYTES);
						scenario.attach(screenshot, "image/png", "image");
					}
				}
				} else {
					if (scenario.isFailed()) {
						final byte[] screenshot = ((TakesScreenshot) DriverManager.getAppiumDriver())
								.getScreenshotAs(OutputType.BYTES);
						scenario.attach(screenshot, "image/png", "image");
					}
				}
			}
		}
	}

	/**
	 * Method to capture content/visual screen using appli tools
	 */
	@AfterStep
	public void captureContent(Scenario sc) {
		if(properties.get("AfterStepAppliTools").toString().equalsIgnoreCase("True"))
			appli.captureContent(sc.getName()+sc.getLine()+"screen"+i);
		CloudPlatformWebDriverFactory.captureSaucescreener(sc.getLine()+"screen"+i);
		i++;
	}


	/**
	 * Method to close respective drivers
	 * @param scenario
	 * @throws IOException
	 */
	@After
	public void tearDown(Scenario scenario) throws IOException {
		appli.closeAppliTools();
		testHarness.closeRespectiveDriver(scenario);
	}

}
