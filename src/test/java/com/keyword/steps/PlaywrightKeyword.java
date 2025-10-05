package com.keyword.steps;

import java.util.HashMap;
import java.util.Properties;

import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.reusable.WebPWReusableComponents;

/**
 * Sample Playwright keyword class demonstrating the enhanced capabilities
 */
public class PlaywrightKeyword extends WebPWReusableComponents {
	
	HashMap<String, HashMap<String, String>> datamap = dataTable.commonData;
	Properties properties = Settings.getInstance();
	String baseurl = properties.getProperty("qaAppUrl");

	public PlaywrightKeyword(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	/**
	 * Sample test method using Playwright
	 */
	public void testPlaywrightLogin() {
		// Navigate to application
		navigateTo(baseurl);
		waitForLoad();
		
		// Perform login
		fill("#user-name", "standard_user");
		fill("#password", "secret_sauce");
		click("#login-button");
		
		// Wait for page to load
		waitForVisible(".inventory_list", 10);
		
		// Verify login success
		String currentUrl = getCurrentUrl();
		if (currentUrl.contains("inventory")) {
			addTestLog("Login Test", "Login successful", com.framework.report.Status.PASS);
		} else {
			addTestLog("Login Test", "Login failed", com.framework.report.Status.FAIL);
		}
		
		// Take screenshot
		takeScreenshot("login_success.png");
	}
	
	/**
	 * Sample test method demonstrating various Playwright features
	 */
	public void testPlaywrightFeatures() {
		navigateTo(baseurl);
		
		// Test form interactions
		fill("#user-name", "standard_user");
		type("#password", "secret_sauce", 100); // Type with delay
		
		// Test element state checks
		boolean isUsernameVisible = isVisible("#user-name");
		boolean isLoginEnabled = isEnabled("#login-button");
		
		if (isUsernameVisible && isLoginEnabled) {
			click("#login-button");
		}
		
		// Wait for elements
		waitForVisible(".inventory_list", 10);
		
		// Test text extraction
		String pageTitle = getTitle();
		String headerText = getText(".title");
		
		// Test element count
		int productCount = getElementCount(".inventory_item");
		
		// Test hover action
		hover(".inventory_item:first-child");
		
		// Test keyboard actions
		pressKey("Escape");
		
		// Test JavaScript execution
		Object result = executeScript("return document.title");
		
		// Log results
		addTestLog("Feature Test", "Page title: " + pageTitle, com.framework.report.Status.PASS);
		addTestLog("Feature Test", "Header text: " + headerText, com.framework.report.Status.PASS);
		addTestLog("Feature Test", "Product count: " + productCount, com.framework.report.Status.PASS);
	}
	
	/**
	 * Sample test method for dropdown and form handling
	 */
	public void testFormHandling() {
		navigateTo(baseurl);
		
		// Login first
		fill("#user-name", "standard_user");
		fill("#password", "secret_sauce");
		click("#login-button");
		waitForVisible(".inventory_list", 10);
		
		// Test dropdown selection
		selectByValue(".product_sort_container", "za");
		waitFor(2);
		
		// Test multiple element interactions
		click(".inventory_item:first-child .btn_inventory");
		click(".shopping_cart_link");
		
		// Verify cart
		waitForVisible(".cart_list", 5);
		String cartItemText = getText(".cart_item .inventory_item_name");
		
		if (!cartItemText.isEmpty()) {
			addTestLog("Cart Test", "Item added to cart: " + cartItemText, com.framework.report.Status.PASS);
		} else {
			addTestLog("Cart Test", "Failed to add item to cart", com.framework.report.Status.FAIL);
		}
		
		// Take element screenshot
		takeElementScreenshot(".cart_list", "cart_items.png");
	}
}