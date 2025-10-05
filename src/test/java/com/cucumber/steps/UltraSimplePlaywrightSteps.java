package com.cucumber.steps;

import java.util.List;

import com.framework.reusable.WebPWReusableComponents;
import com.framework.report.Status;
import com.pages.PlaywrightInventoryPage;
import com.pages.PlaywrightLoginPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Ultra-simple Playwright Step Definitions - Direct page object creation
 */
public class UltraSimplePlaywrightSteps extends WebPWReusableComponents {

    // Login steps
    @When("User enters {string} and {string} using playwright")
    public void enter_credentials_playwright(String username, String password) {
        PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
        loginPage.enterCredentials(username, password);
    }

    @When("User logs in with {string} and {string} using playwright")
    public void login_playwright(String username, String password) {
        PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
        loginPage.login(username, password);
    }

    @Then("verify login result is {string} using playwright")
    public void verify_login_result_playwright(String expectedResult) {
        waitFor(2);
        
        if (expectedResult.equals("success")) {
            PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
            if (inventoryPage.isPageLoaded()) {
                addTestLog("Login", "Login successful", Status.PASS);
            } else {
                addTestLog("Login", "Login failed", Status.FAIL);
            }
        } else {
            PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
            if (loginPage.isErrorMessageDisplayed()) {
                addTestLog("Login", "Login failed as expected: " + loginPage.getErrorMessage(), Status.PASS);
            } else {
                addTestLog("Login", "Expected login failure but succeeded", Status.FAIL);
            }
        }
    }

    // Cart operations
    @When("User adds first product to cart using playwright")
    public void add_first_product_playwright() {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        inventoryPage.addProductToCart(0);
    }

    @When("User adds {string} to cart using playwright")
    public void add_product_by_name_playwright(String productName) {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        inventoryPage.addProductToCartByName(productName);
    }

    @When("User adds multiple products to cart using playwright:")
    public void add_multiple_products_playwright(DataTable dataTable) {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        List<String> products = dataTable.asList();
        for (String product : products) {
            inventoryPage.addProductToCartByName(product);
            waitFor(1);
        }
    }

    @Then("verify cart count is {int} using playwright")
    public void verify_cart_count_playwright(int expectedCount) {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        int actualCount = inventoryPage.getCartItemCount();
        if (actualCount == expectedCount) {
            addTestLog("Cart", "Cart count correct: " + actualCount, Status.PASS);
        } else {
            addTestLog("Cart", "Cart count wrong. Expected: " + expectedCount + ", Actual: " + actualCount, Status.FAIL);
        }
    }

    @When("User goes to cart using playwright")
    public void go_to_cart_playwright() {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        inventoryPage.goToCart();
    }

    @When("User logs out using playwright")
    public void logout_playwright() {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        inventoryPage.logout();
    }

    // Simple validations
    @Then("verify login page is displayed using playwright")
    public void verify_login_page_playwright() {
        PlaywrightLoginPage loginPage = new PlaywrightLoginPage(page);
        if (loginPage.isPageLoaded()) {
            addTestLog("Page", "Login page displayed", Status.PASS);
        } else {
            addTestLog("Page", "Login page not displayed", Status.FAIL);
        }
    }

    @Then("verify inventory page is displayed using playwright")
    public void verify_inventory_page_playwright() {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        if (inventoryPage.isPageLoaded()) {
            addTestLog("Page", "Inventory page displayed", Status.PASS);
        } else {
            addTestLog("Page", "Inventory page not displayed", Status.FAIL);
        }
    }

    @Then("verify cart page is displayed using playwright")
    public void verify_cart_page_is_displayed_using_playwright() {
        String currentUrl = getCurrentUrl();
        if (currentUrl.contains("cart")) {
            addTestLog("Cart Page", "Cart page displayed successfully", Status.PASS);
        } else {
            addTestLog("Cart Page", "Cart page not displayed", Status.FAIL);
        }
    }

    @Then("verify all added products are in cart using playwright")
    public void verify_all_added_products_are_in_cart_using_playwright() {
        PlaywrightInventoryPage inventoryPage = new PlaywrightInventoryPage(page);
        int cartCount = inventoryPage.getCartItemCount();
        if (cartCount > 0) {
            addTestLog("Cart Products", "Cart contains " + cartCount + " products", Status.PASS);
        } else {
            addTestLog("Cart Products", "Cart is empty", Status.FAIL);
        }
    }
}