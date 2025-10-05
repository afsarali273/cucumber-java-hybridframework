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

/**
 * Class for storing general purpose business components
 * 
 * @author Qualitest
 */
public class iOSMobileKeyword extends MobileResuableComponents {

	HashMap<String, String> datamap = dataTable.commonData.get("General_Data");
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	public iOSMobileKeyword(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

//	public void orderFloowDriver() {
//		try {
//
//			MobilePage orderPage = new MobilePage(scriptHelper);
//			orderPage.launchMobileAppRealDevice();
//			orderPage.verifyHomeScreenRestaurantApp(datamap.get("Title"));
//			}catch (Exception e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//			}
//	}
//	public void launchApp() {
//		try {
//
//			MobilePage orderPage = new MobilePage(scriptHelper);
//			orderPage.selectRestaurant(datamap.get("RestaurantName"));
//			orderPage.selectFoodInMenuList(datamap.get("FoodItem"));
//			}catch (Exception e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//			}
//	}
	

	public void processPayment() {
		try {
			
			MobilePage orderPage = new MobilePage(scriptHelper);
			orderPage.enterCrediCardNumber(datamap.get("CCNumber"));
			orderPage.enterCrediCardName(datamap.get("CCName"));
			orderPage.enterCrediCardIssueOn(datamap.get("CCIssueOn"));
			orderPage.enterCrediCardExpiresOn(datamap.get("CCExpiresOn"));
			}catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}

//	public void confirmOrder() {
//		try {
//
//			MobilePage orderPage = new MobilePage(scriptHelper);
//			orderPage.orderSuccessfulConfirmationMessage();
//
//			}catch (Exception e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//			}
//	}
}