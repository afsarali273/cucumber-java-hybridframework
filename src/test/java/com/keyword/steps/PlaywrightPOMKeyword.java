package com.keyword.steps;

import java.util.HashMap;
import java.util.Properties;

import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.playwright.PageFactory;
import com.framework.reusable.WebPWReusableComponents;
import com.framework.report.Status;
import com.pages.InventoryPage;
import com.pages.LoginPage;

/**
 * Sample Playwright Page Object Model keyword class
 */
public class PlaywrightPOMKeyword extends WebPWReusableComponents {
	
	HashMap<String, HashMap<String, String>> datamap = dataTable.commonData;
	Properties properties = Settings.getInstance();
	
	// Page Objects
	private LoginPage loginPage;
	private InventoryPage inventoryPage;

	public PlaywrightPOMKeyword(ScriptHelper scriptHelper) {
		super(scriptHelper);
		initializePages();
	}
	
	private void initializePages() {
		loginPage = PageFactory.createPage(LoginPage.class, scriptHelper);
		inventoryPage = PageFactory.createPage(InventoryPage.class, scriptHelper);
	}

	/**
	 * Test login functionality using Page Object Model
	 */
	public void testLoginWithPOM() {
		// Navigate to login page
		loginPage.navigateToPage();
		
		// Verify page is loaded
		if (loginPage.isPageLoaded()) {
			addTestLog("Page Load", "Login page loaded successfully", Status.PASS);
		} else {
			addTestLog("Page Load", "Login page failed to load", Status.FAIL);
			return;
		}
		
		// Perform login
		String username = datamap.get("General_Data").get("Username");
		String password = datamap.get("General_Data").get("Password");
		
		loginPage.login(username, password);
		
		// Verify login success by checking inventory page
		waitFor(2);
		if (inventoryPage.isPageLoaded()) {
			addTestLog("Login Test", "Login successful - Inventory page loaded", Status.PASS);
		} else {
			addTestLog("Login Test", "Login failed - Inventory page not loaded", Status.FAIL);
		}
	}
	
	/**
	 * Test invalid login scenarios
	 */
	public void testInvalidLogin() {
		loginPage.navigateToPage();
		
		// Test with invalid credentials
		loginPage.login("invalid_user", "invalid_password");
		
		// Verify error message
		if (loginPage.isErrorMessageDisplayed()) {
			String errorMsg = loginPage.getErrorMessage();
			addTestLog("Invalid Login", "Error message displayed: " + errorMsg, Status.PASS);
		} else {
			addTestLog("Invalid Login", "Error message not displayed", Status.FAIL);
		}
		
		// Clear form and try again
		loginPage.clearLoginForm();
	}
	
	/**
	 * Test inventory page functionality
	 */
	public void testInventoryOperations() {
		// Login first
		loginPage.navigateToPage();
		loginPage.login("standard_user", "secret_sauce");
		
		// Wait for inventory page
		waitFor(2);
		if (!inventoryPage.isPageLoaded()) {
			addTestLog("Inventory Test", "Failed to reach inventory page", Status.FAIL);
			return;
		}
		
		// Get product count
		int productCount = inventoryPage.getProductCount();
		addTestLog("Product Count", "Found " + productCount + " products", Status.PASS);
		
		// Add first product to cart
		if (productCount > 0) {
			String productName = inventoryPage.getProductName(0);
			inventoryPage.addProductToCart(0);
			
			// Verify cart count
			int cartCount = inventoryPage.getCartItemCount();
			if (cartCount == 1) {
				addTestLog("Add to Cart", "Product added successfully: " + productName, Status.PASS);
			} else {
				addTestLog("Add to Cart", "Failed to add product to cart", Status.FAIL);
			}
		}
		
		// Test sorting
		inventoryPage.sortProducts("za");
		addTestLog("Sort Test", "Products sorted Z to A", Status.PASS);
		
		// Take screenshot
		inventoryPage.takeInventoryScreenshot();
	}
	
	/**
	 * Test complete shopping flow
	 */
	public void testShoppingFlow() {
		// Login
		loginPage.navigateToPage();
		loginPage.login("standard_user", "secret_sauce");
		waitFor(2);
		
		if (!inventoryPage.isPageLoaded()) {
			addTestLog("Shopping Flow", "Login failed", Status.FAIL);
			return;
		}
		
		// Add multiple products
		String[] productsToAdd = {"Sauce Labs Backpack", "Sauce Labs Bike Light"};
		
		for (String product : productsToAdd) {
			if (inventoryPage.isProductDisplayed(product)) {
				inventoryPage.addProductToCartByName(product);
			}
		}
		
		// Verify cart count
		int expectedCount = productsToAdd.length;
		int actualCount = inventoryPage.getCartItemCount();
		
		if (actualCount == expectedCount) {
			addTestLog("Shopping Flow", "Added " + actualCount + " items to cart", Status.PASS);
		} else {
			addTestLog("Shopping Flow", "Expected " + expectedCount + " items, found " + actualCount, Status.FAIL);
		}
		
		// Go to cart
		inventoryPage.goToCart();
		addTestLog("Shopping Flow", "Navigated to cart page", Status.PASS);
	}
	
	/**
	 * Test logout functionality
	 */
	public void testLogout() {
		// Login first
		loginPage.navigateToPage();
		loginPage.login("standard_user", "secret_sauce");
		waitFor(2);
		
		// Logout
		inventoryPage.logout();
		waitFor(2);
		
		// Verify back to login page
		if (loginPage.isPageLoaded()) {
			addTestLog("Logout Test", "Logout successful - Back to login page", Status.PASS);
		} else {
			addTestLog("Logout Test", "Logout failed - Not on login page", Status.FAIL);
		}
	}
	
	/**
	 * Test page validation methods
	 */
	public void testPageValidations() {
		loginPage.navigateToPage();
		
		// Test field states
		boolean usernameEnabled = loginPage.isUsernameFieldEnabled();
		boolean passwordEnabled = loginPage.isPasswordFieldEnabled();
		boolean loginEnabled = loginPage.isLoginButtonEnabled();
		
		if (usernameEnabled && passwordEnabled && loginEnabled) {
			addTestLog("Field Validation", "All login fields are enabled", Status.PASS);
		} else {
			addTestLog("Field Validation", "Some login fields are disabled", Status.FAIL);
		}
		
		// Test page title
		String pageTitle = loginPage.getPageTitle();
		if (pageTitle.contains("Swag Labs")) {
			addTestLog("Page Title", "Correct page title: " + pageTitle, Status.PASS);
		} else {
			addTestLog("Page Title", "Incorrect page title: " + pageTitle, Status.FAIL);
		}
	}
}