package com.cucumber.steps;

import java.util.ArrayList;
import java.util.HashMap;
import com.framework.cucumber.TestHarness;
import com.pageobjects.MobilePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MobileSteps extends MobilePage {

	TestHarness th = new TestHarness();
	
//	private WebDriverWait wait;
	ArrayList<Double> prodRate = new ArrayList<Double>();
	ArrayList<String> wishlist = new ArrayList<String>();
	double lowestPriceProduct=0;
	String cartHeader = null;

	@Given("enter email address and password for new user using {string}")
	public void enter_email_password(String tcid) {
	//	AndroidDriver Andrioddriver = (AndroidDriver)driver;
		th.initializeTestData(tcid);
		String email = th.getData("General_Data", "Email");
		String password = th.getData("General_Data", "Password");
		enterEmail(email);
		enterPassword(password);
	    enterCnfrmPaswd(password);
	}
	
	@When("enter user details")
	public void enter_user_details() {
		String firstName = th.getData("General_Data", "Firstname");
		String surName = th.getData("General_Data", "Surname");
		enterfirstname(firstName);
		entersurname(surName);
		clickDOB();
		seleectDOB();
		clickOK();
	//	Andrioddriver.hideKeyboard();
		String zipCode = th.getData("General_Data", "Zipcode");
		enterpostcode(zipCode);
	//	Andrioddriver.hideKeyboard();
	}
	
	@Then("user click on agree button")
	public void user_agree_details() {
		clickAgree();
	}

//	Methods for IOS Mobile App
	@Given("Launch the mobile app for the registered user for the hotel management app")
	public void launch_Application() {
		launchMobileAppRealDevice();
	}

	@Then("I should see home screen page with title {string}")
	public void verifyhome(String title) {
		verifyHomeScreenRestaurantApp(title);
	}

	@When("I search and select the {string} in the near by location")
	public void serachAndSelectRestaurant(String hotelName) {
		selectRestaurant(hotelName);
	}

	@When("I select the {string} available in the menu")
	public void selectFoodInMenu(String foodList) {
		selectFoodInMenuList(foodList);
	}

	@Then("I should able to add the food in to the cart")
	public void addFoodMenuToCart() {
		verifyFoodInCart();
	}
	
	@Given("I select the food available in the menu")
	public void selectFoodInMenu() {
		selectFoodInMenuList();
	}
	
	@When("I able to enters the Credit card details {string}, {string}, {string}, {string}")
	public void enterCardPaymentDetails(String CreditCardNumber, String CardName, String IssueOn, String ExpiresOn) {
		enterCrediCardNumber(CreditCardNumber);
		enterCrediCardName(CardName);
		enterCrediCardIssueOn(IssueOn);
		enterCrediCardExpiresOn(ExpiresOn);
		
	}

	@Then("I should able to make the order successful")
	public void verifyOrderSuccessMessage() {
		orderSuccessfulConfirmationMessage();
	}

	@Given("Launch the shopping retail Application {string} and {string} in mobile Safari browser")
	public void launchApplicationSafariBrowser(String tcid, String url) {
		launcMobileBrowserURL(tcid,url);
	}
	
	@When("I search a {string} in the home screen")
	public void searchProductInHomeScreen(String product) {
		searchProduct(product);
	}
	
	@Then("Product should display based on the relevant search")
	public void addProductInHomeScreen() {
		verifyProductSearch();
	}
	
	@Then("I am able to add a product in wishlist")
	public void verifyProductInWishListScreen() {
		addProductInWishList();
	}
}
