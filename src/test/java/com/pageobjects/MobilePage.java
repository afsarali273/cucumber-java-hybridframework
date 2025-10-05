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

import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.framework.selenium.SeleniumTestParameters;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.cucumber.TestHarness;
import com.framework.reusable.MobileResuableComponents;


public class MobilePage extends MobileResuableComponents {
	//AndroidDriver Andrioddriver = (AndroidDriver)driver;
	//IOSDriver iosDriver = (IOSDriver)driver;

	TestHarness testHarness = new TestHarness();


	//****************************************************************************************************//
	//***************************        Android locators 			     *********************************//
	//****************************************************************************************************//

	String commonTxt = "//android.widget.EditText[contains(@resource-id,'var1')]";
	By selectDOB = By.xpath("//android.view.View[@content-desc='01 January 2004']");
	By btnOK = By.xpath("//*[@text='OK']");
	By btnAgree = By.xpath("//android.widget.CheckBox[contains(@resource-id,'chk_agree')]");
	By btnRegister = By.xpath("//android.widget.Button[contains(@resource-id,'btn_register')]");

	//****************************************************************************************************//
	//***************************        iOS locators 			         *********************************//
	//****************************************************************************************************//

	By homeScreenlbl = By.xpath("//XCUIElementTypeStaticText[@name='Restaurant']");
	By searchRestaurant = By.xpath("//XCUIElementTypeStaticText[@name='Bulrush']");
	By selectRestaurantbtn = By.xpath("(//XCUIElementTypeStaticText[@name='More Info'])[1]");
	By addFoodFromMenu = By.xpath("//XCUIElementTypeButton[@name='Buy Now']");
	By addNewCardbtn = By.xpath("//XCUIElementTypeButton[@name='Add New Card']");
	By creditCardNumber = By.xpath("(//XCUIElementTypeTextField)[1]");
	By creditCardName = By.xpath("(//XCUIElementTypeTextField)[2]");
	By creditCardIssueDate = By.xpath("(//XCUIElementTypeTextField)[3]");
	By creditCardExpireOn = By.xpath("(//XCUIElementTypeTextField)[4]");
	By verifyPurchaseSuccessfulMessage= By.xpath("//XCUIElementTypeStaticText[@name='Purchase was successful']");
	By cardOwnerlabel = By.xpath("//XCUIElementTypeStaticText[@name='Card Owner']");
	By acceptAllCookiesBtn = By.xpath("//*[@class='cc-btn cc-accept-all cc-btn-no-href']");
	By searchProductTxtBox = By.className("header-search-input");
	By searchButton = By.className("header-search-button");
	By wishListButton = By.xpath("//*[text()='Add to wishlist']");


	public MobilePage(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public MobilePage() {
		super();
	}

	public void enterEmail(String emailaddress) {
		Random rand = new Random();
		int rand_int1 = rand.nextInt(10000);
		By emailAddress = generateLocator("XPATH", replaceLocator(commonTxt, "register_email_address"));
		enterText(emailAddress, rand_int1 + emailaddress);
	}

	public void enterPassword(String pwd) {
		By password = generateLocator("XPATH", replaceLocator(commonTxt, "register_password"));
		enterText(password, pwd);
	}

	public void enterCnfrmPaswd(String pwd) {
		By cnfrmPaswd = generateLocator("XPATH", replaceLocator(commonTxt, "register_confirm_password"));
		enterText(cnfrmPaswd, pwd);
	}

	public void enterfirstname(String fname) {
		By firstName = generateLocator("XPATH", replaceLocator(commonTxt, "register_forename"));
		enterText(firstName, fname);
	}

	public void entersurname(String sname) {
		By surName = generateLocator("XPATH", replaceLocator(commonTxt, "register_surname"));
		if(!elementVisible(surName)) {
			swipeScreen(Direction.UP);
		}
		enterText(surName, sname);
	}

	public void enterpostcode(String postcode) {
		By postCode = generateLocator("XPATH", replaceLocator(commonTxt, "register_postcode"));
		swipeScreen(Direction.UP);
		enterText(postCode, postcode);
	}

	public void clickDOB() {
		By dob = generateLocator("XPATH", replaceLocator(commonTxt, "register_dob"));
		ClickElement(dob);
	}

	public void seleectDOB() {
		ClickElement(selectDOB);
	}

	public void clickOK() {
		ClickElement(btnOK);
	}

	public void clickRegister() {
		ClickElement(btnRegister);
	}

	public void clickAgree() {
		ClickElement(btnAgree);
	}

	// IOS Device APP Methods

	public void launchMobileAppRealDevice() {
		Properties mobileProperties = Settings.getMobilePropertiesInstance();
		IOSDriver iosDriver = (IOSDriver)driver;
		if(mobileProperties.get("ios_appreset").toString().equalsIgnoreCase("true")) {
            iosDriver.terminateApp(mobileProperties.getProperty("ios_bundleid"));

		}
		iosDriver.activateApp(mobileProperties.getProperty("ios_bundleid"));
		System.out.println("App is launched successfully in Real device");
	}

	public void verifyHomeScreenRestaurantApp(String title) {
		IOSDriver iosDriver = (IOSDriver)driver;
		String homescreen = iosDriver.findElement(homeScreenlbl).getAttribute("label");
		System.out.println("Home screen Text" + homescreen);
		if (homescreen.equalsIgnoreCase(title)) {
		}
	}

	public void selectRestaurant(String hotelName) {
		IOSDriver iosDriver = (IOSDriver)driver;
		String searchHotel = iosDriver.findElement(searchRestaurant).getAttribute("label");
		System.out.println("Hotel found in the app " + searchHotel);
		if (searchHotel.equalsIgnoreCase(hotelName)) {
			ClickElement(selectRestaurantbtn);
		}
	}

	public void selectFoodInMenuList(String foodMenu) {
		ClickElement(addFoodFromMenu);
	}

	public void selectFoodInMenuList() {
		System.out.println("click on More info");
		ClickElement(selectRestaurantbtn);
		ClickElement(addFoodFromMenu);
	}

	public void verifyFoodInCart() {
		IOSDriver iosDriver = (IOSDriver)driver;
		if (iosDriver.findElement(addNewCardbtn).isDisplayed()) {
			System.out.println("Food added to the cart");
		}
	}

	public void enterCrediCardNumber(String CreditCardNumber) {
		enterText(creditCardNumber,CreditCardNumber);
	}

	public void enterCrediCardName(String CreditCardName) {
		enterText(creditCardName,CreditCardName);
	}
	public void enterCrediCardIssueOn(String CreditCardissueOn) {
		ClickElement(cardOwnerlabel);
		ClickElement(cardOwnerlabel);
		enterText(creditCardIssueDate,CreditCardissueOn);
	}
	public void enterCrediCardExpiresOn(String CreditCardExpiresOn) {
		ClickElement(cardOwnerlabel);
		ClickElement(cardOwnerlabel);
		enterText(creditCardExpireOn,CreditCardExpiresOn);
	}

	public void orderSuccessfulConfirmationMessage() {
		IOSDriver iosDriver = (IOSDriver)driver;
		ClickElement(cardOwnerlabel);
		ClickElement(cardOwnerlabel);
		ClickElement(addNewCardbtn);
		if (iosDriver.findElement(verifyPurchaseSuccessfulMessage).isDisplayed()) {
			System.out.println("Payments Crad details accepted and Order is Successful");
		}
	}


	public void launcMobileBrowserURL(String tcid,String url) {
		testHarness.initializeTestData(tcid);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		ClickElement(acceptAllCookiesBtn);
	}

	public void searchProduct(String product) {
		ClickElement(searchProductTxtBox);
		enterText(searchProductTxtBox, product);
	}

	public void verifyProductSearch() {
		if(driver.findElement(wishListButton).isDisplayed()){
			System.out.println("Product is displayed");
		}
	}

	public void addProductInWishList() {
		ClickElement(wishListButton);
	}

}