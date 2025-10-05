package com.framework.playwright;

import com.framework.components.ScriptHelper;
import com.framework.reusable.WebPWReusableComponents;
import com.microsoft.playwright.Page;

/**
 * Base Page class for Playwright Page Object Model
 */
public abstract class BasePage extends WebPWReusableComponents {
    
    protected Page page;
    
    public BasePage(ScriptHelper scriptHelper) {
        super(scriptHelper);
        this.page = scriptHelper.getPlaywrightPage();
        ElementInitializer.initElements(this.page, this);
    }
    
    public BasePage(Page page) {
        super();
        this.page = page;
        super.page = page; // Set page in parent class
        ElementInitializer.initElements(this.page, this);
    }
    
    /**
     * Navigate to page URL
     */
    public abstract void navigateToPage();
    
    /**
     * Verify page is loaded
     */
    public abstract boolean isPageLoaded();
    
    /**
     * Get page title
     */
    public String getPageTitle() {
        return page.title();
    }
    
    /**
     * Get current URL
     */
    public String getCurrentPageUrl() {
        return page.url();
    }
    
    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        waitForLoad();
        waitForNetworkIdle();
    }
}