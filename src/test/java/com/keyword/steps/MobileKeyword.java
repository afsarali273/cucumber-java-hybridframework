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

import java.util.HashMap;

import com.framework.components.DriverScript;
import com.framework.components.ScriptHelper;
import com.framework.reusable.MobileResuableComponents;
import com.pageobjects.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * Class for storing general purpose business components
 * 
 * @author Qualitest
 */
public class MobileKeyword extends MobileResuableComponents {
	AndroidDriver Andrioddriver = (AndroidDriver)driver;
	HashMap<String, String> datamap = dataTable.commonData.get("General_Data");
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public MobileKeyword(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void signinFloowDriver() {
		try {
			
			MobilePage registerPage = new MobilePage(scriptHelper);
			wait(3L);
			registerPage.enterEmail(datamap.get("Email"));
			registerPage.enterPassword(datamap.get("Password"));
			registerPage.enterCnfrmPaswd(datamap.get("Password"));
			//registerPage.swipeScreen(Direction.UP); 
			
			registerPage.enterfirstname(datamap.get("Firstname"));
			registerPage.entersurname(datamap.get("Surname"));
			registerPage.swipeScreen(Direction.UP); 
			registerPage.clickDOB();
			registerPage.seleectDOB();
			registerPage.clickOK();
			Andrioddriver.hideKeyboard();
			swipeScreen(Direction.UP); 
			registerPage.enterpostcode(datamap.get("Zipcode"));
			Andrioddriver.hideKeyboard();
			registerPage.clickAgree();
			}catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}

	

	

}