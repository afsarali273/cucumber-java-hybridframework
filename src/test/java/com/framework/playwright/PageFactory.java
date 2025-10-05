package com.framework.playwright;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.framework.components.FrameworkException;
import com.framework.components.ScriptHelper;
import com.microsoft.playwright.Page;

/**
 * Factory class for creating Page Object instances
 */
public class PageFactory {
    
    private static Map<Class<? extends BasePage>, BasePage> pageCache = new HashMap<>();
    
    /**
     * Create page instance with ScriptHelper
     */
    public static <T extends BasePage> T createPage(Class<T> pageClass, ScriptHelper scriptHelper) {
        try {
            Constructor<T> constructor = pageClass.getConstructor(ScriptHelper.class);
            T pageInstance = constructor.newInstance(scriptHelper);
            pageCache.put(pageClass, pageInstance);
            return pageInstance;
        } catch (Exception e) {
            throw new FrameworkException("Failed to create page instance: " + e.getMessage());
        }
    }
    
    /**
     * Create page instance with Page object
     */
    public static <T extends BasePage> T createPage(Class<T> pageClass, Page page) {
        try {
            Constructor<T> constructor = pageClass.getConstructor(Page.class);
            T pageInstance = constructor.newInstance(page);
            pageCache.put(pageClass, pageInstance);
            return pageInstance;
        } catch (Exception e) {
            throw new FrameworkException("Failed to create page instance: " + e.getMessage());
        }
    }
    
    /**
     * Get cached page instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends BasePage> T getPage(Class<T> pageClass) {
        return (T) pageCache.get(pageClass);
    }
    
    /**
     * Clear page cache
     */
    public static void clearCache() {
        pageCache.clear();
    }
}