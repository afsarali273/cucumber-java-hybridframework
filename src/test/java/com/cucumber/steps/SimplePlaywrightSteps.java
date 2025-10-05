//package com.cucumber.steps;
//
//import java.util.List;
//
//import com.pages.PageActions;
//import com.framework.reusable.WebPWReusableComponents;
//import com.framework.report.Status;
//
//import io.cucumber.datatable.DataTable;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
///**
// * Ultra-simple Playwright Step Definitions
// */
//public class SimplePlaywrightSteps extends WebPWReusableComponents {
//
//    // Login steps
//    @When("User enters {string} and {string} using playwright")
//    public void enter_credentials_playwright(String username, String password) {
//        PageActions.enterCredentials(username, password);
//    }
//
//    @When("User logs in with {string} and {string} using playwright")
//    public void login_playwright(String username, String password) {
//        PageActions.login(username, password);
//    }
//
//    @Then("verify login result is {string} using playwright")
//    public void verify_login_result_playwright(String expectedResult) {
//        waitFor(2);
//
//        if (expectedResult.equals("success")) {
//            if (PageActions.isInventoryPageLoaded()) {
//                addTestLog("Login", "Login successful", Status.PASS);
//            } else {
//                addTestLog("Login", "Login failed", Status.FAIL);
//            }
//        } else {
//            if (PageActions.hasLoginError()) {
//                addTestLog("Login", "Login failed as expected: " + PageActions.getLoginError(), Status.PASS);
//            } else {
//                addTestLog("Login", "Expected login failure but succeeded", Status.FAIL);
//            }
//        }
//    }
//
//    // Cart operations
//    @When("User adds first product to cart using playwright")
//    public void add_first_product_playwright() {
//        PageActions.addProductToCart(0);
//    }
//
//    @When("User adds {string} to cart using playwright")
//    public void add_product_by_name_playwright(String productName) {
//        PageActions.addProductToCart(productName);
//    }
//
//    @When("User adds multiple products to cart using playwright:")
//    public void add_multiple_products_playwright(DataTable dataTable) {
//        List<String> products = dataTable.asList();
//        products.forEach(PageActions::addProductToCart);
//    }
//
//    @Then("verify cart count is {int} using playwright")
//    public void verify_cart_count_playwright(int expectedCount) {
//        int actualCount = PageActions.getCartCount();
//        if (actualCount == expectedCount) {
//            addTestLog("Cart", "Cart count correct: " + actualCount, Status.PASS);
//        } else {
//            addTestLog("Cart", "Cart count wrong. Expected: " + expectedCount + ", Actual: " + actualCount, Status.FAIL);
//        }
//    }
//
//    @When("User goes to cart using playwright")
//    public void go_to_cart_playwright() {
//        PageActions.goToCart();
//    }
//
//    @When("User logs out using playwright")
//    public void logout_playwright() {
//        PageActions.logout();
//    }
//
//    // Simple validations
//    @Then("verify login page is displayed using playwright")
//    public void verify_login_page_playwright() {
//        if (PageActions.isLoginPageLoaded()) {
//            addTestLog("Page", "Login page displayed", Status.PASS);
//        } else {
//            addTestLog("Page", "Login page not displayed", Status.FAIL);
//        }
//    }
//
//    @Then("verify inventory page is displayed using playwright")
//    public void verify_inventory_page_playwright() {
//        if (PageActions.isInventoryPageLoaded()) {
//            addTestLog("Page", "Inventory page displayed", Status.PASS);
//        } else {
//            addTestLog("Page", "Inventory page not displayed", Status.FAIL);
//        }
//    }
//}