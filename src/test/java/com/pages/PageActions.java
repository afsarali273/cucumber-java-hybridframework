package com.pages;

import com.framework.playwright.PageManager;

/**
 * Application-specific page action wrapper for SauceDemo
 */
public class PageActions {
    
    // Direct page access methods
    public static PlaywrightLoginPage loginPage() {
        return PageManager.getPage(PlaywrightLoginPage.class);
    }
    
    public static PlaywrightInventoryPage inventoryPage() {
        return PageManager.getPage(PlaywrightInventoryPage.class);
    }
    
    // Common action shortcuts
    public static void login(String username, String password) {
        loginPage().login(username, password);
    }
    
    public static void enterCredentials(String username, String password) {
        loginPage().enterCredentials(username, password);
    }
    
    public static void addProductToCart(int index) {
        inventoryPage().addProductToCart(index);
    }
    
    public static void addProductToCart(String productName) {
        inventoryPage().addProductToCartByName(productName);
    }
    
    public static void goToCart() {
        inventoryPage().goToCart();
    }
    
    public static void logout() {
        inventoryPage().logout();
    }
    
    public static int getCartCount() {
        return inventoryPage().getCartItemCount();
    }
    
    public static boolean isLoginPageLoaded() {
        return loginPage().isPageLoaded();
    }
    
    public static boolean isInventoryPageLoaded() {
        return inventoryPage().isPageLoaded();
    }
    
    public static boolean hasLoginError() {
        return loginPage().isErrorMessageDisplayed();
    }
    
    public static String getLoginError() {
        return loginPage().getErrorMessage();
    }
}