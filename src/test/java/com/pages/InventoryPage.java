package com.pages;

import java.util.List;
import com.framework.components.ScriptHelper;
import com.framework.playwright.BasePage;
import com.framework.playwright.PlaywrightElement;
import com.framework.playwright.annotations.FindBy;
import com.framework.report.Status;
import com.microsoft.playwright.Page;

/**
 * Sample Inventory Page using Playwright Page Object Model
 */
public class InventoryPage extends BasePage {
    
    // Page elements
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
    
    // Constructors
    public InventoryPage(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    public InventoryPage(Page page) {
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
            pageTitle.assertVisible();
            inventoryContainer.assertVisible();
            pageTitle.assertTextEquals("Products");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Page actions
    public int getProductCount() {
        return inventoryItems.count();
    }
    
    public void addProductToCart(int productIndex) {
        PlaywrightElement productItem = inventoryItems.nth(productIndex);
        PlaywrightElement addToCartButton = new PlaywrightElement(
                productItem.getLocator().locator(".btn_inventory"), ".btn_inventory");
        
        String productName = productItem.getLocator().locator(".inventory_item_name").textContent();
        
        addToCartButton.click();
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
    
    public void sortProducts(String sortOption) {
        sortDropdown.assertVisible();
        sortDropdown.assertEnabled();
        sortDropdown.selectByValue(sortOption);
        waitFor(1); // Wait for sorting to complete
        addTestLog("Sort Products", "Products sorted by: " + sortOption, Status.PASS);
    }
    
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
    
    public String getProductName(int index) {
        return inventoryItems.nth(index)
                .getLocator().locator(".inventory_item_name").textContent();
    }
    
    public String getProductPrice(int index) {
        return inventoryItems.nth(index)
                .getLocator().locator(".inventory_item_price").textContent();
    }
    
    public void logout() {
        menuButton.assertVisible();
        menuButton.click();
        logoutLink.assertVisible();
        logoutLink.assertEnabled();
        logoutLink.click();
        addTestLog("Logout", "User logged out successfully", Status.PASS);
    }
    
    // Validation methods
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
    
    public boolean isCartEmpty() {
        try {
            cartBadge.assertHidden();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<String> getAllProductNames() {
        return page.locator(".inventory_item_name").allTextContents();
    }
    
    public void takeInventoryScreenshot() {
        inventoryContainer.screenshot("inventory_page.png");
        addTestLog("Screenshot", "Inventory page screenshot taken", Status.PASS);
    }
}