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
package com.keyword.steps;

import com.framework.components.DriverScript;
import com.framework.components.ScriptHelper;
import com.pageobjects.HybridWebPage;

import static com.framework.selenium.SeleniumTestParameters.getAppUrl;

public class WebKeyword extends HybridWebPage {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object passed from the
	 *                     {@link DriverScript}
	 */
	public WebKeyword(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void launchSeleniumUrl() {
//		String url = properties.getProperty("ApplicationUrl");
		launchApp();
	}

	public void verifyHome() {
		clickAcceptCookies();
		verifyHomeScreen();
		clickOkButton();
	}

	public void enterDetails() {	
		String firstName = dataTable.getData("General_Data", "FirstName");
		String lastName = dataTable.getData("General_Data", "LastName");
		String country = dataTable.getData("General_Data", "Country");
		enterFistName(firstName);
		enterLastName(lastName);
		clickGender();
		clickExperience();
		clickProfession();
		clickProfession();
		clickToolProtractor();
		clickToolSelenium();
		selectCountry(country);
	}

	public void submitDetails() {
		clickSubmit();
	}
}
