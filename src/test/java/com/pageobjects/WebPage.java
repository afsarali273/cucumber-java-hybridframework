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
package com.pageobjects;

import org.openqa.selenium.By;
import org.testng.Assert;
import com.framework.components.ApplitoolsOperations;
import com.framework.reusable.WebReusableComponents;
import static com.framework.selenium.SeleniumTestParameters.getAppUrl;

public class WebPage extends WebReusableComponents {

	protected By homeLink = By.linkText("Techlistic");
	protected By loginHeader = By.xpath("//*[@class='login_logo']");

	protected By txtUserName = By.name("user-name");
	protected By txtPassword = By.name("password");

	protected By btnLogin = By.xpath("//*[@id='login-button']");
	protected By btnAcceptCookies = By.id("ez-accept-all");
	protected By lblHeader = By.xpath("//*[@class='app_logo']");


	protected By btnOk = By.xpath("//span[@class='cookie-choices-buttons']/a[text()=\"Ok\"]");
	protected By txtFirstName = By.name("firstname");
	protected By txtLastName = By.name("lastname");

	protected By btnGender = By.id("sex-0");
	protected By btnExp = By.id("exp-6");
	protected By btnProf = By.id("profession-0");

	protected By btnToolUFT = By.id("tool-0");
	protected By btnToolProtractor = By.id("tool-1");
	protected By btnToolSelenium = By.id("tool-2");

	protected By selectContinents = By.id("continents");
	protected By btnSubmit = By.id("submit");
	protected By btnCookie = By.xpath("//a[contains(text(),'Got it')]");

	protected ApplitoolsOperations appliTools = new ApplitoolsOperations();

	protected void launchApp() {
		launchUrl(getAppUrl());
		maximizeWindow();
		appliTools.captureContent("");
	}



	protected void verifyLogin() {
		Assert.assertTrue(elementVisible(loginHeader, 10));
	}
	protected void enterUserName(String textValue) {
		enterText(txtUserName, textValue);
	}

	protected void enterPassword(String textValue) {
		enterText(txtPassword, textValue);
	}
	protected void clickLoginbutton() {
		if(elementVisible(btnLogin)) {
			clickElement(btnLogin);
		}
	}
	protected void clickAcceptCookies() {
		if(elementVisible(btnAcceptCookies,10)) {
			clickElement(btnAcceptCookies);
		}
	}

	protected void verifyHome() {
		Assert.assertTrue(elementVisible(lblHeader, 20));
	}

	protected void clickOkButton() {
		if(elementVisible(btnOk)) {
			clickElement(btnOk);
		}
	}

	protected void enterFistName(String firstname) {
		enterText(txtFirstName, firstname);
	}

	protected void enterLastName(String lastname) {
		enterText(txtLastName, lastname);
	}

	protected void clickGender() {
		clickElement(btnGender);
	}

	protected void clickExperience() {
		clickElement(btnExp);
	}

	protected void clickProfession() {
		clickElement(btnProf);
	}

	protected void clickToolUFT() {
		clickElement(btnToolUFT);
	}

	protected void clickToolProtractor() {
		clickElement(btnToolProtractor);
	}

	protected void clickToolSelenium() {
		clickElement(btnToolSelenium);
	}

	protected void selectCountry(String value) {
		selectByValue(selectContinents,value);
	}

	protected void submitDetails() {
		clickElement(btnSubmit);
		//appliTools.captureContent("");
	}
}
