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

import org.openqa.selenium.WebDriver;

import com.framework.data.FrameworkDataTable;
import com.framework.selenium.CustomDriver;
import com.framework.selenium.SeleniumReport;

/**
 * Wrapper class for common framework objects, to be used across the entire test
 * case and dependent libraries
 * 
 * @author Qualitest
 */
public class ScriptHelper {

	private final FrameworkDataTable dataTable;
	protected final SeleniumReport report;
	private CustomDriver customDriver;
	private RestAssuredUtils apiDriver;
	/**
	 * Constructor to initialize all the objects wrapped by the {@link ScriptHelper}
	 * class
	 * 
	 * @param dataTable      The {@link FrameworkDataTable} object
	 * @param report         The {@link SeleniumReport} object
	 * @param apiDriver 	 The reusable API driver {@link RestAssuredUtils }
	 * @param customDriver   The {@link CustomDriver} object

	 */

	public ScriptHelper(FrameworkDataTable dataTable, SeleniumReport report, CustomDriver customDriver, RestAssuredUtils apiDriver) {
		this.dataTable = dataTable;
		this.report = report;
		this.customDriver = customDriver;
		this.apiDriver = apiDriver;
	}
	
//	public ScriptHelper(DataTable dataTable, SeleniumReport report, RstAssuredUtils apiDriver) {
//		this.dataTable = dataTable;
//		this.report = report;
//		//this.customDriver = customDriver;
//		this.apiDriver = apiDriver;
//	}

	/**
	 * Function to get the {@link FrameworkDataTable} object
	 * 
	 * @return The {@link FrameworkDataTable} object
	 */
	public FrameworkDataTable getDataTable() {
		return dataTable;
	}

	/**
	 * Function to get the {@link SeleniumReport} object
	 * 
	 * @return The {@link SeleniumReport} object
	 */
	public SeleniumReport getReport() {
		return report;
	}

	/**
	 * Function to get the {@link CustomDriver} object
	 * 
	 * @return The {@link CustomDriver} object
	 */
	public CustomDriver getcustomDriver() {
		return customDriver;
	}

	/**
	 * Function to get the {@link RestAssuredUtils} object
	 * 
	 * @return The {@link apiDriver} object

	 */
	public RestAssuredUtils getApiDriver() {
		return apiDriver;
	}




}