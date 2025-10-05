package com.pages;

import java.util.List;
import com.framework.components.ScriptHelper;
import com.framework.playwright.BasePage;
import com.framework.playwright.PlaywrightElement;
import com.framework.playwright.annotations.FindBy;
import com.framework.report.Status;
import com.microsoft.playwright.Page;

/**
 * Dedicated Playwright Inventory Page Object
 */
public class PlaywrightInventoryPage extends BasePage {
    
    @FindBy(css = ".app_logo")
    private PlaywrightElement appLogo;
    
    @FindBy(css = ".title")
    private PlaywrightElement pageTitle;
    
    @FindBy(css = ".inventory_list")
    private PlaywrightElement inventoryContainer;
    
    @FindBy(css = ".inventory_item")
    private PlaywrightElement inventoryItems;
    
    @FindBy(css = ".product_sort_container")
    private PlaywrightElement sortDropdown;
    
    @FindBy(css = ".shopping_cart_link")
    private PlaywrightElement cartLink;
    
    @FindBy(css = ".shopping_cart_badge")
    private PlaywrightElement cartBadge;
    
    @FindBy(css = "#react-burger-menu-btn")
    private PlaywrightElement menuButton;
    
    @FindBy(css = "#logout_sidebar_link")
    private PlaywrightElement logoutLink;
    
    @FindBy(css = ".bm-menu")
    private PlaywrightElement sideMenu;
    
    // Constructors
    public PlaywrightInventoryPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    public PlaywrightInventoryPage(Page page) {
        super(page);
    }
    
    @Override
    public void navigateToPage() {
        // This page is typically reached after login
        addTestLog("Navigate", "Inventory page should be loaded after login", Status.INFO);
    }
    
    @Override
    public boolean isPageLoaded() {
        try {
            appLogo.assertVisible();
            pageTitle.assertVisible();
            inventoryContainer.assertVisible();
            pageTitle.assertTextEquals("Products");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Page validation methods
    public boolean isInventoryPageDisplayed() {
        try {
            appLogo.assertVisible();
            inventoryContainer.assertVisible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isAppLogoVisible() {
        return appLogo.isVisible();
    }
    
    public String getPageTitleText() {
        return pageTitle.getText();
    }
    
    // Product operations
    public int getProductCount() {
        return inventoryItems.count();
    }
    
    public List<String> getAllProductNames() {
        return page.locator(".inventory_item_name").allTextContents();
    }
    
    public String getProductName(int index) {
        PlaywrightElement product = inventoryItems.nth(index);
        return product.getLocator().locator(".inventory_item_name").textContent();
    }
    
    public String getProductPrice(int index) {
        PlaywrightElement product = inventoryItems.nth(index);
        return product.getLocator().locator(".inventory_item_price").textContent();
    }
    
    public String getProductDescription(int index) {
        PlaywrightElement product = inventoryItems.nth(index);
        return product.getLocator().locator(".inventory_item_desc").textContent();
    }
    
    public void addProductToCart(int productIndex) {
        String productName = getProductName(productIndex);
        PlaywrightElement product = inventoryItems.nth(productIndex);
        PlaywrightElement addButton = new PlaywrightElement(
                product.getLocator().locator(".btn_inventory"), "Add to cart button");
        addButton.assertVisible();
        addButton.assertEnabled();
        addButton.click();
        addTestLog("Add to Cart", "Added product to cart: " + productName, Status.PASS);
    }
    
    public void addProductToCartByName(String productName) {
        PlaywrightElement product = new PlaywrightElement(page, 
                ".inventory_item:has(.inventory_item_name:text('" + productName + "'))");
        
        PlaywrightElement addButton = new PlaywrightElement(
                product.getLocator().locator(".btn_inventory"), "Add button");
        
        addButton.click();
        addTestLog("Add to Cart", "Added product: " + productName, Status.PASS);
    }
    
    public void removeProductFromCart(int productIndex) {
        String productName = getProductName(productIndex);
        PlaywrightElement product = inventoryItems.nth(productIndex);
        PlaywrightElement removeButton = new PlaywrightElement(
                product.getLocator().locator(".btn_inventory"), "Remove from cart button");
        removeButton.click();
        addTestLog("Remove from Cart", "Removed product from cart: " + productName, Status.PASS);
    }
    
    // Sorting operations
    public void sortProducts(String sortOption) {
        sortDropdown.assertVisible();
        sortDropdown.assertEnabled();
        sortDropdown.selectByValue(sortOption);
        waitFor(1); // Wait for sorting to complete
        addTestLog("Sort Products", "Products sorted by: " + sortOption, Status.PASS);
    }
    
    public void sortProductsByNameAZ() {
        sortProducts("az");
    }
    
    public void sortProductsByNameZA() {
        sortProducts("za");
    }
    
    public void sortProductsByPriceLowHigh() {
        sortProducts("lohi");
    }
    
    public void sortProductsByPriceHighLow() {
        sortProducts("hilo");
    }
    
    // Cart operations
    public void goToCart() {
        cartLink.assertVisible();
        cartLink.assertEnabled();
        cartLink.click();
        addTestLog("Go to Cart", "Navigated to cart page", Status.PASS);
    }
    
    public int getCartItemCount() {
        if (cartBadge.isVisible()) {
            return Integer.parseInt(cartBadge.getText());
        }
        return 0;
    }
    
    public boolean isCartEmpty() {
        return !cartBadge.isVisible();
    }
    
    // Menu operations
    public void openMenu() {
        menuButton.assertVisible();
        menuButton.assertEnabled();
        menuButton.click();
        sideMenu.assertVisible();
        addTestLog("Open Menu", "Side menu opened", Status.PASS);
    }
    
    public void logout() {
        openMenu();
        logoutLink.assertVisible();
        logoutLink.assertEnabled();
        logoutLink.click();
        addTestLog("Logout", "User logged out successfully", Status.PASS);
    }
    
    // Product validation
    public boolean isProductDisplayed(String productName) {
        try {
            PlaywrightElement product = new PlaywrightElement(page, 
                    ".inventory_item_name:text('" + productName + "')");
            product.assertVisible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean areProductsDisplayed() {
        return inventoryItems.count() > 0;
    }
    
    // Screenshot methods
    public void takeInventoryScreenshot() {
        inventoryContainer.screenshot("playwright_inventory_page.png");
        addTestLog("Screenshot", "Inventory page screenshot taken", Status.PASS);
    }
    
    public void takeFullPageScreenshot() {
        takeScreenshot("playwright_full_inventory_page.png");
        addTestLog("Screenshot", "Full inventory page screenshot taken", Status.PASS);
    }
    
    public void takeProductScreenshot(int productIndex) {
        PlaywrightElement product = inventoryItems.nth(productIndex);
        product.screenshot("playwright_product_" + productIndex + ".png");
        addTestLog("Screenshot", "Product " + productIndex + " screenshot taken", Status.PASS);
    }
    
    // Wait methods
    public void waitForInventoryPageLoad() {
        appLogo.waitForVisible(10);
        pageTitle.waitForVisible(10);
        inventoryContainer.waitForVisible(10);
    }
    
    public void waitForProductsToLoad() {
        inventoryItems.waitForVisible(10);
    }
    
    // Advanced operations
    public void addMultipleProductsToCart(String... productNames) {
        for (String productName : productNames) {
            if (isProductDisplayed(productName)) {
                addProductToCartByName(productName);
                waitFor(1); // Small delay between additions
            }
        }
    }
    
    public void addAllProductsToCart() {
        int productCount = getProductCount();
        for (int i = 0; i < productCount; i++) {
            addProductToCart(i);
            waitFor(1);
        }
        addTestLog("Add All Products", "Added all " + productCount + " products to cart", Status.PASS);
    }
}