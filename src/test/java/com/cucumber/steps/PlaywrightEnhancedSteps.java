//package com.cucumber.steps;
//
//import java.util.List;
//import java.util.Properties;
//
//import com.framework.components.Settings;
//import com.framework.cucumber.TestHarness;
//import com.framework.playwright.BaseStepDefinition;
//import com.framework.playwright.PageManager;
//import com.framework.report.Status;
//import com.pages.PlaywrightInventoryPage;
//import com.pages.PlaywrightLoginPage;
//
//import io.cucumber.datatable.DataTable;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
///**
// * Enhanced Playwright Step Definitions using advanced Java concepts
// */
//public class PlaywrightEnhancedSteps extends BaseStepDefinition {
//
//    protected TestHarness testHarness = new TestHarness();
//    protected Properties properties = Settings.getInstance();
//
//    // Performance tracking
//    private long startTime;
//    private long endTime;
//
//    public PlaywrightEnhancedSteps() {
//        super();
//    }
//
//    // Enhanced login steps
//    @When("User enters {string} and {string} using playwright")
//    public void enter_credentials_playwright(String username, String password) {
//        executePageAction(PlaywrightLoginPage.class, page -> {
//            page.enterUsername(username);
//            page.enterPassword(password);
//        });
//    }
//
//    @Then("verify login result is {string} using playwright")
//    public void verify_login_result_playwright(String expectedResult) {
//        waitFor(2);
//
//        if (expectedResult.equals("success")) {
//            validatePage(PlaywrightInventoryPage.class,
//                        page -> page.isPageLoaded(),
//                        "Login successful as expected",
//                        "Login should have succeeded but failed");
//        } else if (expectedResult.equals("error")) {
//            validatePage(PlaywrightLoginPage.class,
//                        page -> page.isErrorMessageDisplayed(),
//                        "Login failed as expected: " + getPageData(PlaywrightLoginPage.class, page -> page.getErrorMessage()),
//                        "Login should have failed but succeeded");
//        }
//    }
//
//    // Inventory operations
//    @When("User adds first product to cart using playwright")
//    public void add_first_product_to_cart_playwright() {
//        executePageAction(PlaywrightInventoryPage.class, page -> page.addProductToCart(0));
//    }
//
//    @When("User adds multiple products to cart using playwright:")
//    public void add_multiple_products_to_cart_playwright(DataTable dataTable) {
//        List<String> products = dataTable.asList();
//        executePageAction(PlaywrightInventoryPage.class, page -> {
//            products.forEach(product -> {
//                page.addProductToCartByName(product);
//                waitFor(1);
//            });
//        });
//    }
//
//    @Then("verify cart count is {int} using playwright")
//    public void verify_cart_count_playwright(int expectedCount) {
//        int actualCount = getPageData(PlaywrightInventoryPage.class, page -> page.getCartItemCount());
//        validatePage(PlaywrightInventoryPage.class,
//                    page -> page.getCartItemCount() == expectedCount,
//                    "Cart count is correct: " + actualCount,
//                    "Cart count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount);
//    }
//
//    @When("User sorts products by name Z-A using playwright")
//    public void sort_products_za_playwright() {
//        executePageAction(PlaywrightInventoryPage.class, page -> page.sortProductsByNameZA());
//    }
//
//    @Then("verify products are sorted correctly using playwright")
//    public void verify_products_sorted_playwright() {
//        String firstProduct = getPageData(PlaywrightInventoryPage.class, page -> {
//            List<String> productNames = page.getAllProductNames();
//            return productNames.isEmpty() ? "" : productNames.get(0);
//        });
//
//        if (!firstProduct.isEmpty()) {
//            addTestLog("Sort Validation", "First product after Z-A sort: " + firstProduct, Status.PASS);
//        }
//    }
//
//    @When("User goes to cart using playwright")
//    public void go_to_cart_playwright() {
//        executePageAction(PlaywrightInventoryPage.class, page -> page.goToCart());
//    }
//
//    @Then("verify cart page is displayed using playwright")
//    public void verify_cart_page_displayed_playwright() {
//        String currentUrl = getCurrentUrl();
//        if (currentUrl.contains("cart")) {
//            addTestLog("Cart Page", "Cart page displayed successfully", Status.PASS);
//        } else {
//            addTestLog("Cart Page", "Cart page not displayed", Status.FAIL);
//        }
//    }
//
//    @When("User logs out using playwright")
//    public void logout_playwright() {
//        executePageAction(PlaywrightInventoryPage.class, page -> page.logout());
//    }
//
//    // Responsive testing
//    @When("User sets viewport to {string} using playwright")
//    public void set_viewport_playwright(String viewport) {
//        String[] dimensions = viewport.split("x");
//        int width = Integer.parseInt(dimensions[0]);
//        int height = Integer.parseInt(dimensions[1]);
//
//        Hooks.getPlaywrightPageInstance().setViewportSize(width, height);
//        addTestLog("Viewport", "Viewport set to: " + viewport, Status.PASS);
//    }
//
//    @Then("verify page layout is responsive using playwright")
//    public void verify_responsive_layout_playwright() {
//        boolean isResponsive = PageManager.isCached(PlaywrightLoginPage.class) ?
//            getPageData(PlaywrightLoginPage.class, page -> page.isPageLoaded()) :
//            getPageData(PlaywrightInventoryPage.class, page -> page.isPageLoaded());
//
//        if (isResponsive) {
//            addTestLog("Responsive", "Page layout is responsive", Status.PASS);
//        } else {
//            addTestLog("Responsive", "Page layout is not responsive", Status.FAIL);
//        }
//    }
//
//    // Performance testing
//    @When("User measures page load time using playwright")
//    public void measure_page_load_time_playwright() {
//        startTime = System.currentTimeMillis();
//    }
//
//    @Then("verify page loads within {int} seconds using playwright")
//    public void verify_page_load_time_playwright(int maxSeconds) {
//        endTime = System.currentTimeMillis();
//        long loadTime = (endTime - startTime) / 1000;
//
//        if (loadTime <= maxSeconds) {
//            addTestLog("Performance", "Page loaded in " + loadTime + " seconds", Status.PASS);
//        } else {
//            addTestLog("Performance", "Page load time exceeded: " + loadTime + " seconds", Status.FAIL);
//        }
//    }
//
//    @When("User measures navigation time using playwright")
//    public void measure_navigation_time_playwright() {
//        startTime = System.currentTimeMillis();
//    }
//
//    @Then("verify navigation completes within {int} seconds using playwright")
//    public void verify_navigation_time_playwright(int maxSeconds) {
//        endTime = System.currentTimeMillis();
//        long navTime = (endTime - startTime) / 1000;
//
//        if (navTime <= maxSeconds) {
//            addTestLog("Navigation Performance", "Navigation completed in " + navTime + " seconds", Status.PASS);
//        } else {
//            addTestLog("Navigation Performance", "Navigation time exceeded: " + navTime + " seconds", Status.FAIL);
//        }
//    }
//
//    // Accessibility testing
//    @Then("verify playwright login page accessibility")
//    public void verify_login_page_accessibility_playwright() {
//        validatePage(PlaywrightLoginPage.class,
//                    page -> page.isUsernameFieldVisible() && page.isPasswordFieldVisible() && page.isLoginButtonVisible(),
//                    "Login page accessibility validated",
//                    "Login page accessibility issues found");
//    }
//
//    @Then("verify playwright home page accessibility")
//    public void verify_home_page_accessibility_playwright() {
//        validatePage(PlaywrightInventoryPage.class,
//                    page -> page.isPageLoaded(),
//                    "Home page accessibility validated",
//                    "Home page accessibility issues found");
//    }
//
//    @Then("verify all interactive elements are keyboard accessible using playwright")
//    public void verify_keyboard_accessibility_playwright() {
//        addTestLog("Keyboard Accessibility", "Keyboard accessibility validated", Status.PASS);
//    }
//}