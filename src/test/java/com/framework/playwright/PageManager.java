package com.framework.playwright;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.cucumber.steps.Hooks;
import com.microsoft.playwright.Page;

/**
 * Generic Page Manager using advanced Java concepts
 * Manages page object instances with lazy initialization and caching
 */
public class PageManager {
    
    private static final Map<Class<?>, Object> pageCache = new HashMap<>();
    private static final Map<Class<?>, Supplier<?>> pageSuppliers = new HashMap<>();
    
    /**
     * Generic method to get page instance with lazy initialization
     * @param <T> Page type
     * @param pageClass Page class
     * @return Page instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends BasePage> T getPage(Class<T> pageClass) {
        return (T) pageCache.computeIfAbsent(pageClass, clazz -> {
            try {
                Constructor<T> constructor = pageClass.getConstructor(Page.class);
                return constructor.newInstance(Hooks.getPlaywrightPageInstance());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create page instance: " + pageClass.getSimpleName(), e);
            }
        });
    }
    
    /**
     * Register custom page supplier for complex initialization
     * @param <T> Page type
     * @param pageClass Page class
     * @param supplier Page supplier function
     */
    public static <T extends BasePage> void registerPageSupplier(Class<T> pageClass, Supplier<T> supplier) {
        pageSuppliers.put(pageClass, supplier);
    }
    
    /**
     * Get page using registered supplier or default constructor
     * @param <T> Page type
     * @param pageClass Page class
     * @return Page instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends BasePage> T getPageWithSupplier(Class<T> pageClass) {
        return (T) pageCache.computeIfAbsent(pageClass, clazz -> {
            Supplier<?> supplier = pageSuppliers.get(clazz);
            if (supplier != null) {
                return supplier.get();
            }
            return getPage(pageClass);
        });
    }
    
    /**
     * Clear page cache - useful for test cleanup
     */
    public static void clearCache() {
        pageCache.clear();
    }
    
    /**
     * Check if page is cached
     * @param pageClass Page class
     * @return true if cached
     */
    public static boolean isCached(Class<?> pageClass) {
        return pageCache.containsKey(pageClass);
    }
    
    /**
     * Remove specific page from cache
     * @param pageClass Page class to remove
     */
    public static void removePage(Class<?> pageClass) {
        pageCache.remove(pageClass);
    }
}