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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/features", 
glue = { "com.cucumber.steps" , "com.framework.webcrawler"}, 
//Please fill the needed tag(s)
tags = "@SauceDemoLoginTest",
monochrome = true,
dryRun = false,
plugin = { "pretty",
		"pretty:target/cucumber-report/pretty.txt", "html:target/cucumber-report/report.html",
		"json:target/cucumber-report/cucumber.json", "junit:target/cucumber-report/cucumber-junitreport.xml",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
})

@Listeners(com.framework.cucumber.TestNGListener.class)
public class CucumberRunner extends AbstractTestNGCucumberTests {

	/**
	 * Please enable parallel = true for executing scenarios in Parallel Also
	 * number of Parallel Threads can be controlled in suite-xml file with
	 * parameter data-provider-thread-count="1"
	 */
	
	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
		return super.scenarios();
	}

}
